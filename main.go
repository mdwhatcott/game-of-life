// http://en.wikipedia.org/wiki/Conway's_Game_of_Life

package main

import (
	"fmt"
	"log"
	"net/http"
	"path/filepath"
	"runtime"
	"strings"
	"time"

	"github.com/mdwhatcott/golife/life"
	"github.com/smartystreets/configo"
)

func main() {
	reader := configo.NewReader(
		configo.FromCommandLineFlags().Register("cli", "If 'true', run the simulation in the command line."))

	grid := new(life.Grid)
	grid.Seed(gliderGun)

	if reader.Bool("cli") {
		for {
			fmt.Print("\033[2J\033[H" + grid.String())
			grid.Scan()
			time.Sleep(time.Millisecond * 25)
		}
	}

	_, file, _, _ := runtime.Caller(0)
	here := filepath.Dir(file)
	static := filepath.Join(here, "/client")
	http.Handle("/", http.FileServer(http.Dir(static)))

	printer := NewHtmlStringer(grid)
	http.HandleFunc("/state", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprintf(w, printer.String())
		grid.Scan()
	})

	log.Fatal(http.ListenAndServe(":8081", nil))
}

type HtmlStringer struct {
	inner fmt.Stringer
}

func (self *HtmlStringer) String() string {
	raw := self.inner.String()
	raw = strings.Replace(raw, "\n", "<br>", -1)
	raw = strings.Replace(raw, "-", "&nbsp;", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return raw
}

func NewHtmlStringer(inner fmt.Stringer) *HtmlStringer {
	self := new(HtmlStringer)
	self.inner = inner
	return self
}

const gliderGun = `
------------------------x--------------------
----------------------x-x--------------------
------------xx------xx------------xx---------
-----------x---x----xx------------xx---------
xx--------x-----x---xx-----------------------
xx--------x---x-xx----x-x--------------------
----------x-----x-------x--------------------
-----------x---x-----------------------------
------------xx-------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
---------------------------------------------
`

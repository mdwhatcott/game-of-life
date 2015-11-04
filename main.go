// http://en.wikipedia.org/wiki/Conway's_Game_of_Life

package main

import (
	"fmt"
	"log"
	"net/http"
	"time"

	life "github.com/mdwhatcott/golife/life"
	"github.com/mdwhatcott/golife/ui"
	"github.com/smartystreets/configo"
)

func main() {
	reader := configo.NewReader(configo.FromCommandLineFlags().
		RegisterBool("console", "If 'true', run the simulation in the command line."))

	grid := life.New(gliderGun)

	if reader.Bool("console") {
		console(grid)
	} else {
		html(grid)
	}
}

func console(grid *life.Grid) {
	for {
		fmt.Print(clearScreen)
		fmt.Print(ui.Console(grid))
		grid.Scan()
		time.Sleep(time.Millisecond * 25)
	}
}

func html(grid *life.Grid) {
	http.HandleFunc("/", func(response http.ResponseWriter, _ *http.Request) {
		response.Header().Set("Content-Type", "text/html")
		fmt.Fprint(response, UI)
	})

	http.HandleFunc("/state", func(response http.ResponseWriter, _ *http.Request) {
		fmt.Fprint(response, ui.HTML(grid))
		grid.Scan()
	})

	fmt.Println("Serving http at: ':8081'")
	if err := http.ListenAndServe(":8081", nil); err != nil {
		log.Fatal(err)
	}
}

const clearScreen = "\033[2J\033[H"

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

const UI = `<html>
<head>
  <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
  <script type="text/javascript">
    jQuery(document).ready(function() {
      setInterval(function() {
        $.ajax("/state", {
          success: function(data) {
            $('body').html(data);
          }
        });
      }, 75)
    });
  </script>
</head>
<body style="font-family: monospace;">
</body>
</html>
`

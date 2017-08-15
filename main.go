// http://en.wikipedia.org/wiki/Conway's_Game_of_Life

package main

import (
	"fmt"
	"time"

	"github.com/julienschmidt/httprouter"
	"github.com/mdwhatcott/golife/life"
	"github.com/mdwhatcott/golife/ui"
	"github.com/smartystreets/configo"
	"github.com/smartystreets/httpx"
)

func main() {
	cli := configo.NewReader(
		configo.FromCommandLineFlags().
			RegisterBool("console", "When set, run the simulation in the command line."),
	)

	grid := life.New(gliderGun)

	if cli.Bool("console") {
		runInConsole(grid)
	} else {
		runInBrowser(grid)
	}
}

func runInConsole(grid *life.Grid) {
	for {
		fmt.Print(clearScreen)
		fmt.Print(ui.Console(grid))
		grid.Scan()
		time.Sleep(time.Millisecond * 25)
	}
}

func runInBrowser(grid *life.Grid) {
	router := httprouter.New()
	controller := ui.NewController(grid)
	controller.AddRoutes(router)
	httpx.NewHTTPServer(":8080", router).Listen()
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
        $.ajax("/grid", {
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

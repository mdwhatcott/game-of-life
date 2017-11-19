package ui

import (
	"fmt"
	"net/http"

	"github.com/mdwhatcott/golife/life"
)

type controller struct {
	grid       *life.Grid
	iterations int
}

func newController(grid *life.Grid, iterations int) *controller {
	return &controller{
		grid:       grid,
		iterations: iterations,
	}
}

func (this *controller) AddRoutes(router *http.ServeMux) {
	router.HandleFunc("/", this.HTML)
	router.HandleFunc("/grid", this.Grid)
}

func (this *controller) HTML(response http.ResponseWriter, _ *http.Request) {
	response.Header().Set("ContentType", "text/html")
	fmt.Fprint(response, uiHTML)
}

func (this *controller) Grid(response http.ResponseWriter, _ *http.Request) {
	if this.iterations > 0 {
		defer this.grid.Scan()
	}
	fmt.Fprint(response, html(this.grid))
}

const uiHTML = `<html>
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

package ui

import (
	"github.com/julienschmidt/httprouter"
	"github.com/mdwhatcott/golife/life"
	"github.com/smartystreets/detour"
)

type Controller struct {
	grid *life.Grid
}

func NewController(grid *life.Grid) *Controller {
	return &Controller{grid: grid}
}

func (this *Controller) AddRoutes(router *httprouter.Router) {
	router.Handler("GET", "/", detour.New(this.HTML))
	router.Handler("GET", "/grid", detour.New(this.Grid))
}

func (this *Controller) HTML() detour.Renderer {
	return &detour.ContentResult{
		ContentType: "text/html",
		Content:     UI,
	}
}

func (this *Controller) Grid() detour.Renderer {
	defer this.grid.Scan()
	return &detour.ContentResult{Content: HTML(this.grid).String()}
}

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

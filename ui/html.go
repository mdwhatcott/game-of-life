package ui

import (
	"fmt"
	"log"
	"net/http"
	"strings"

	"github.com/mdwhatcott/golife/life"
)

func runInBrowser(config *Config) {
	grid := life.New(config.GridState)
	router := http.NewServeMux()
	newController(grid, config.Iterations).AddRoutes(router)
	log.Printf("[INFO] Listening for web traffic on %s.", config.Animation)
	http.ListenAndServe(config.Animation, router)
}

func html(grid fmt.Stringer) string {
	raw := grid.String()
	raw = strings.Replace(raw, "\n", "<br>", -1)
	raw = strings.Replace(raw, "-", "&nbsp;", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return raw
}

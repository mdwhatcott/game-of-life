package ui

import (
	"fmt"
	"log"
	"net/http"
	"strings"
)

func animateInBrowser(config *Config) {
	router := http.NewServeMux()
	newController(config.Grid, config.Iterations).AddRoutes(router)
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

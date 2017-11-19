package ui

import (
	"fmt"
	"strings"
	"time"

	"github.com/mdwhatcott/golife/life"
)

func runInConsole(config *Config) {
	grid := life.New(config.GridState)

	for ; config.Iterations > 0; config.Iterations-- {
		if config.Animation == "console" {
			fmt.Print(console(grid))
		}

		grid.Scan()

		if config.Animation == "console" {
			time.Sleep(time.Millisecond * 25)
		}
	}

	fmt.Print(console(grid))
}

func console(inner fmt.Stringer) string {
	raw := inner.String()
	raw = strings.Replace(raw, "-", " ", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return clearScreen + raw
}

const clearScreen = "\033[2J\033[H"

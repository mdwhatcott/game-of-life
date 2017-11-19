package ui

import (
	"fmt"
	"strings"
	"time"
)

func animateInConsole(config *Config) {
	for ; config.Iterations > 0; config.Iterations-- {
		fmt.Print(clearScreen + console(config.Grid))
		config.Grid.Scan()
		time.Sleep(time.Millisecond * 25)
	}

	fmt.Print(console(config.Grid))
}

func printFinalState(config *Config) {
	for ; config.Iterations > 0; config.Iterations-- {
		config.Grid.Scan()
	}

	fmt.Print(console(config.Grid))
}

func console(inner fmt.Stringer) string {
	raw := inner.String()
	raw = strings.Replace(raw, "-", " ", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return raw
}

const clearScreen = "\033[2J\033[H"

package ui

import "github.com/mdwhatcott/golife/life"

type Config struct {
	Grid       *life.Grid
	Iterations int
	Animation  string
}

func (config *Config) RunSimulation() {
	switch config.Animation {
	case "":
		printFinalState(config)
	case "console":
		animateInConsole(config)
	default:
		animateInBrowser(config)
	}
}

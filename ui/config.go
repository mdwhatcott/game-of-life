package ui

type Config struct {
	GridState  string
	Iterations int
	Animation  string
}

func (config *Config) RunSimulation() {
	switch config.Animation {
	case "console", "":
		runInConsole(config)
	default:
		runInBrowser(config)
	}
}

package main

import (
	"testing"
	. "github.com/smartystreets/goconvey/convey"
)

var stillLife = []string{
	`
----
-xx-
-xx-
----
`,
	`
----
-xx-
-xx-
----
`,
	`
----
-xx-
-xx-
----
`,
}

var oscillator = []string{
	`
-----
-----
-xxx-
-----
-----
`,
	`
-----
--x--
--x--
--x--
-----
`,
	`
-----
-----
-xxx-
-----
-----
`,
	`
-----
--x--
--x--
--x--
-----
`,
}

var glider = []string{
	`
--x---
---x--
-xxx--
------
------
------
`,
	`
------
-x-x--
--xx--
--x---
------
------
`,
	`
------
---x--
-x-x--
--xx--
------
------
`,
	`
------
--x---
---xx-
--xx--
------
------
`,
	`
------
---x--
----x-
--xxx-
------
------
`,
}

var cases = [][]string{stillLife, oscillator, glider}

func TestGrid(t *testing.T) {
	var (
		grid    *Grid
		console *ConsolePrinter
	)

	Convey("Given an initial seed", t, func() {
		grid = NewGrid()
		console = NewConsolePrinter(grid)

		Convey("The console should be able to render the grid", func() {
			grid.Seed("---\nxxx\n---")
			So(console.String(), ShouldEqual, "\n---\nxxx\n---\n")
		})

		Convey("Each time the board is updated the state should be correct", func() {
			for _, example := range cases {
				grid.Seed(example[0])
				So(console.String(), ShouldEqual, example[0])

				for x := 1; x < len(example); x++ {
					grid.Scan()

					So(console.String(), ShouldEqual, example[x])
				}
			}
		})
	})
}

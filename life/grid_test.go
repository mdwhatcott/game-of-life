package life

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
	Convey("Given an initial seed, the grid should be able to render", t, func() {
		grid := New("---\nxxx\n---")
		So(grid.String(), ShouldEqual, "\n---\nxxx\n---\n")
	})

	Convey("Each time the board is updated the state should be correct", t, func() {
		for _, example := range cases {
			grid := New(example[0])
			So(grid.String(), ShouldEqual, example[0])

			for x := 1; x < len(example); x++ {
				grid.Scan()

				So(grid.String(), ShouldEqual, example[x])
			}
		}
	})
}

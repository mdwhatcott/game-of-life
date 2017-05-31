package life

import (
	"testing"

	"github.com/smartystreets/assertions"
	"github.com/smartystreets/assertions/should"
)

func TestGrid_InitialSeed_ShouldRenderEqual(t *testing.T) {
	assert := assertions.New(t)

	grid := New("---\nxxx\n---")
	assert.So(grid.String(), should.Equal, "\n---\nxxx\n---\n")
}

func TestGridUpdates_RenderedStateShouldBeCorrect(t *testing.T) {
	assert := assertions.New(t)

	for _, example := range [][]string{stillLife, oscillator, glider} {
		grid := New(example[0])
		assert.So(grid.String(), should.Equal, example[0])

		for x := 1; x < len(example); x++ {
			grid.Scan()

			assert.So(grid.String(), should.Equal, example[x])
		}
	}
}

/**************************************************************************/

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

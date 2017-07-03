package life

import (
	"testing"

	"github.com/smartystreets/assertions/should"
	"github.com/smartystreets/gunit"
)

func TestGridFixture(t *testing.T) {
	gunit.Run(new(GridFixture), t)
}

type GridFixture struct {
	*gunit.Fixture
}

func (this *GridFixture) TestInitialSeed_ShouldRenderEqual() {
	grid := New("---\nxxx\n---")
	this.So(grid.String(), should.Equal, "\n---\nxxx\n---\n")
}

func (this *GridFixture) TestGridUpdatesMatchExpectedStates() {
	this.assertGridProgression(stillLife)
	this.assertGridProgression(oscillator)
	this.assertGridProgression(glider)
}

func (this *GridFixture) assertGridProgression(sequence []string) {
	grid := New(sequence[0])
	for x := 1; x < len(sequence); x++ {
		grid.Scan()
		this.AssertSprintEqual(grid, sequence[x])
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

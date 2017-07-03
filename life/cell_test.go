package life

import (
	"testing"

	"github.com/smartystreets/assertions/should"
	"github.com/smartystreets/gunit"
)

func TestLiveCellFixture(t *testing.T) {
	gunit.Run(new(LiveCellFixture), t)
}

type LiveCellFixture struct {
	*gunit.Fixture
}

func (this *LiveCellFixture) TestSurvivalDependsOnNumberIfLiveNeighbors() {
	this.scanUpdateCheck(newLiveCell(), 0, false)
	this.scanUpdateCheck(newLiveCell(), 1, false)
	this.scanUpdateCheck(newLiveCell(), 2, true)
	this.scanUpdateCheck(newLiveCell(), 3, true)
	this.scanUpdateCheck(newLiveCell(), 4, false)
	this.scanUpdateCheck(newLiveCell(), 5, false)
	this.scanUpdateCheck(newLiveCell(), 6, false)
	this.scanUpdateCheck(newLiveCell(), 7, false)
	this.scanUpdateCheck(newLiveCell(), 8, false)

	this.scanUpdateCheck(newDeadCell(), 0, false)
	this.scanUpdateCheck(newDeadCell(), 1, false)
	this.scanUpdateCheck(newDeadCell(), 2, false)
	this.scanUpdateCheck(newDeadCell(), 3, true)
	this.scanUpdateCheck(newDeadCell(), 4, false)
	this.scanUpdateCheck(newDeadCell(), 5, false)
	this.scanUpdateCheck(newDeadCell(), 6, false)
	this.scanUpdateCheck(newDeadCell(), 7, false)
	this.scanUpdateCheck(newDeadCell(), 8, false)
}

func (this *LiveCellFixture) scanUpdateCheck(c *cell, howMany int, expectedAlive bool) {
	c.scan(liveNeighbors(howMany))
	c.update()
	this.So(c.isAlive(), should.Equal, expectedAlive)
}

func liveNeighbors(live int) []*cell {
	activated := 0
	cells := []*cell{}
	for x := 0; x < NEIGHBORS; x++ {
		if activated < live {
			cells = append(cells, newLiveCell())
			activated++
		} else {
			cells = append(cells, newDeadCell())
		}
	}
	return cells
}

const NEIGHBORS = 8

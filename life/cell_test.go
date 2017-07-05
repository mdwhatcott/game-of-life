package life

import (
	"testing"

	"github.com/smartystreets/gunit"
)

func TestExampleFixture(t *testing.T) {
	gunit.Run(new(CellFixture), t)
}

type CellFixture struct {
	*gunit.Fixture
}

func (this *CellFixture) TestCanNewALiveCell() {
	cell := newLiveCell()

	this.assertAlive(cell)
}
func (this *CellFixture) TestCanNewADeadCell() {
	cell := newDeadCell()

	this.assertDead(cell)
}

func (this *CellFixture) TestLiveCellWithAllDeadNeighborsDies() {
	cell := newLiveCell()
	neighbors := []*Cell{}

	for i := 0; i < 8; i++ {
		neighbors = append(neighbors, newDeadCell())
	}

	cell.scan(neighbors)

	cell.update()

	this.assertDead(cell)
}

func (this *CellFixture) assertDead(cell *Cell) {
	this.Assert(!cell.isAlive())
}

func (this *CellFixture) assertAlive(cell *Cell) {
	this.Assert(cell.isAlive())
}

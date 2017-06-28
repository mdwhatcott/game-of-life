package life

import (
	"testing"

	"github.com/smartystreets/assertions/should"
	"github.com/smartystreets/gunit"
)

func TestCellFixture(t *testing.T) {
	gunit.Run(new(CellFixture), t)
}

type CellFixture struct {
	*gunit.Fixture
}

func (this *CellFixture) TestNewCreatedDeadCellShouldBeDead() {
	cell := NewDeadCell()
	this.So(cell.IsAlive(), should.BeFalse)
}

func (this *CellFixture) TestNewCreatedLiveCellShouldBeAlive() {
	this.So(NewLiveCell().IsAlive(), should.BeTrue)
}

func (this *CellFixture) TestDeadCellUpdates() {
	this.assertDies(NewDeadCell(), createManyCells(0, 8))
	this.assertDies(NewDeadCell(), createManyCells(1, 7))
	this.assertDies(NewDeadCell(), createManyCells(2, 6))
	this.assertDies(NewDeadCell(), createManyCells(3, 5))
	this.assertDies(NewDeadCell(), createManyCells(4, 4))
	this.assertLives(NewDeadCell(), createManyCells(5, 3))
	this.assertDies(NewDeadCell(), createManyCells(6, 2))
	this.assertDies(NewDeadCell(), createManyCells(7, 1))
	this.assertDies(NewDeadCell(), createManyCells(8, 0))
}
func (this *CellFixture) assertLives(cell *Cell, neighbors []*Cell) {
	cell.Scan(neighbors)
	cell.Update()
	this.So(cell.IsAlive(), should.BeTrue)
}
func (this *CellFixture) assertDies(cell *Cell, neighbors []*Cell) {
	cell.Scan(neighbors)
	cell.Update()
	this.So(cell.IsAlive(), should.BeFalse)
}

func (this *CellFixture) TestLiveCellUpdates() {
	this.assertDies(NewLiveCell(), createManyCells(0, 8))
	this.assertDies(NewLiveCell(), createManyCells(1, 7))
	this.assertDies(NewLiveCell(), createManyCells(2, 6))
	this.assertDies(NewLiveCell(), createManyCells(3, 5))
	this.assertDies(NewLiveCell(), createManyCells(4, 4))
	this.assertLives(NewLiveCell(), createManyCells(5, 3))
	this.assertLives(NewLiveCell(), createManyCells(6, 2))
	this.assertDies(NewLiveCell(), createManyCells(7, 1))
	this.assertDies(NewLiveCell(), createManyCells(8, 0))
}

func createManyCells(dead, alive int) []*Cell {
	cells := []*Cell{}
	for count := 0; count < alive; count++ {
		cells = append(cells, NewLiveCell())
	}
	for count := 0; count < dead; count++ {
		cells = append(cells, NewDeadCell())
	}
	return cells
}

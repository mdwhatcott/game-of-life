package life

import (
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

func TestCell(t *testing.T) {
	Convey("Subject: Cell lives or dies based on state of surrounding cells", t, func() {

		Convey("Given a live cell", func() {
			cell := Alive()

			Convey("with no live neighbors", func() {
				cell.Scan(liveNeighbors(0))
				cell.Update()

				Convey("the cell should die", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with one live neighbor", func() {
				cell.Scan(liveNeighbors(1))
				cell.Update()

				Convey("the cell should die", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with two live neighbors", func() {
				cell.Scan(liveNeighbors(2))
				cell.Update()

				Convey("the cell should live", func() {
					So(cell.IsAlive(), ShouldBeTrue)
				})
			})

			Convey("with three live neighbors", func() {
				cell.Scan(liveNeighbors(3))
				cell.Update()

				Convey("the cell should live", func() {
					So(cell.IsAlive(), ShouldBeTrue)
				})
			})

			Convey("with four live neighbors", func() {
				cell.Scan(liveNeighbors(4))
				cell.Update()

				Convey("the cell should die", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with five live neighbors", func() {
				cell.Scan(liveNeighbors(5))
				cell.Update()

				Convey("the cell should die", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with six live neighbors", func() {
				cell.Scan(liveNeighbors(6))
				cell.Update()

				Convey("the cell should die", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with seven live neighbors", func() {
				cell.Scan(liveNeighbors(7))
				cell.Update()

				Convey("the cell should die", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with eight live neighbors", func() {
				cell.Scan(liveNeighbors(8))
				cell.Update()

				Convey("the cell should die", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})
		})

		Convey("Given a dead cell", func() {
			cell := Dead()

			Convey("with no live neighbors", func() {
				cell.Scan(liveNeighbors(0))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with one live neighbor", func() {
				cell.Scan(liveNeighbors(1))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with two live neighbors", func() {
				cell.Scan(liveNeighbors(2))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with three live neighbors", func() {
				cell.Scan(liveNeighbors(3))
				cell.Update()

				Convey("the cell should come alive", func() {
					So(cell.IsAlive(), ShouldBeTrue)
				})
			})

			Convey("with four live neighbors", func() {
				cell.Scan(liveNeighbors(4))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with five live neighbors", func() {
				cell.Scan(liveNeighbors(5))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with six live neighbors", func() {
				cell.Scan(liveNeighbors(6))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with seven live neighbors", func() {
				cell.Scan(liveNeighbors(7))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})

			Convey("with eight live neighbors", func() {
				cell.Scan(liveNeighbors(8))
				cell.Update()

				Convey("the cell should stay dead", func() {
					So(cell.IsAlive(), ShouldBeFalse)
				})
			})
		})
	})
}

func liveNeighbors(live int) []*Cell {
	activated := 0
	cells := []*Cell{}
	for x := 0; x < NEIGHBORS; x++ {
		if activated < live {
			cells = append(cells, Alive())
			activated++
		} else {
			cells = append(cells, Dead())
		}
	}
	return cells
}

const NEIGHBORS = 8

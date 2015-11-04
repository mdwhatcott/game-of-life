package life

import (
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

func TestCell(t *testing.T) {
	Convey("Subject: Cell lives or dies based on state of surrounding cells", t, func() {

		Convey("Given a live cell", func() {
			cell := alive()

			Convey("with no live neighbors", func() {
				cell.scan(liveNeighbors(0))
				cell.update()

				Convey("the cell should die", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with one live neighbor", func() {
				cell.scan(liveNeighbors(1))
				cell.update()

				Convey("the cell should die", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with two live neighbors", func() {
				cell.scan(liveNeighbors(2))
				cell.update()

				Convey("the cell should live", func() {
					So(cell.isAlive(), ShouldBeTrue)
				})
			})

			Convey("with three live neighbors", func() {
				cell.scan(liveNeighbors(3))
				cell.update()

				Convey("the cell should live", func() {
					So(cell.isAlive(), ShouldBeTrue)
				})
			})

			Convey("with four live neighbors", func() {
				cell.scan(liveNeighbors(4))
				cell.update()

				Convey("the cell should die", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with five live neighbors", func() {
				cell.scan(liveNeighbors(5))
				cell.update()

				Convey("the cell should die", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with six live neighbors", func() {
				cell.scan(liveNeighbors(6))
				cell.update()

				Convey("the cell should die", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with seven live neighbors", func() {
				cell.scan(liveNeighbors(7))
				cell.update()

				Convey("the cell should die", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with eight live neighbors", func() {
				cell.scan(liveNeighbors(8))
				cell.update()

				Convey("the cell should die", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})
		})

		Convey("Given a dead cell", func() {
			cell := dead()

			Convey("with no live neighbors", func() {
				cell.scan(liveNeighbors(0))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with one live neighbor", func() {
				cell.scan(liveNeighbors(1))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with two live neighbors", func() {
				cell.scan(liveNeighbors(2))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with three live neighbors", func() {
				cell.scan(liveNeighbors(3))
				cell.update()

				Convey("the cell should come alive", func() {
					So(cell.isAlive(), ShouldBeTrue)
				})
			})

			Convey("with four live neighbors", func() {
				cell.scan(liveNeighbors(4))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with five live neighbors", func() {
				cell.scan(liveNeighbors(5))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with six live neighbors", func() {
				cell.scan(liveNeighbors(6))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with seven live neighbors", func() {
				cell.scan(liveNeighbors(7))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})

			Convey("with eight live neighbors", func() {
				cell.scan(liveNeighbors(8))
				cell.update()

				Convey("the cell should stay dead", func() {
					So(cell.isAlive(), ShouldBeFalse)
				})
			})
		})
	})
}

func liveNeighbors(live int) []*cell {
	activated := 0
	cells := []*cell{}
	for x := 0; x < NEIGHBORS; x++ {
		if activated < live {
			cells = append(cells, alive())
			activated++
		} else {
			cells = append(cells, dead())
		}
	}
	return cells
}

const NEIGHBORS = 8

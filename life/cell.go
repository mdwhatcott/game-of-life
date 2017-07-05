package life

type Cell struct {
	alive bool
}

func newLiveCell() *Cell {
	return &Cell{
		alive: true,
	}
}

func newDeadCell() *Cell {
	return &Cell{}
}

func (cell *Cell) isAlive() bool {
	return cell.alive
}

func (cell *Cell) scan(neighbors []*Cell) {

}

func (cell *Cell) update() {
	cell.alive = false
}

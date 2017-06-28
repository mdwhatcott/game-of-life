package life

type Cell struct {
	isAlive     bool
	willBeAlive bool
}

func (this *Cell) IsAlive() bool {
	return this.isAlive
}
func NewLiveCell() *Cell {
	return &Cell{isAlive: true}
}
func NewDeadCell() *Cell {
	return &Cell{isAlive: false}
}

func (this *Cell) Scan(neighbors []*Cell) {
	liveNeighbors := countAlive(neighbors)
	this.willBeAlive = liveNeighbors == 3 || (this.isAlive && liveNeighbors == 2)
}
func countAlive(cells []*Cell) int {
	alive := 0
	for cnt := 0; cnt < len(cells); cnt++ {
		if cells[cnt].isAlive {
			alive++
		}
	}
	return alive
}
func (this *Cell) Update() {
	this.isAlive = this.willBeAlive
}

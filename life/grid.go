package life

import "bytes"

type Grid struct {
	relations map[*Cell][]*Cell
	cells     [][]*Cell
}

func New(grid string) *Grid {
	self := new(Grid)
	self.cells = initialize(grid)
	self.relations = formRelationships(self.cells)
	return self
}
func initialize(grid string) [][]*Cell {
	rows := [][]*Cell{}
	var row []*Cell

	for _, c := range grid {
		if c == '\n' && len(row) > 0 {
			rows = append(rows, row)
			row = []*Cell{}
		} else if c == '-' {
			row = append(row, Dead())
		} else if c == 'x' {
			row = append(row, Alive())
		}
	}
	if len(row) > 0 {
		rows = append(rows, row)
	}
	return rows
}
func formRelationships(grid [][]*Cell) map[*Cell][]*Cell {
	relations := map[*Cell][]*Cell{}

	for y, row := range grid {
		for x, cell := range row {
			relations[cell] = neighbors(grid, x, y)
		}
	}
	return relations
}
func neighbors(grid [][]*Cell, x, y int) []*Cell {
	yes := []*Cell{}

	for _, option := range adjoining(x, y) {
		if option.isOnGrid(grid) {
			yes = append(yes, grid[option.y][option.x])
		}
	}
	return yes
}
func adjoining(x, y int) []point {
	return []point{
		point{x - 1, y - 1}, // upper left
		point{x, y - 1},     // upper
		point{x + 1, y - 1}, // upper right
		point{x - 1, y},     // left
		point{x + 1, y},     // right
		point{x - 1, y + 1}, // lower left
		point{x, y + 1},     // lower
		point{x + 1, y + 1}, // lower right
	}
}

func (self *Grid) Scan() {
	for cell, neighbors := range self.relations {
		cell.Scan(neighbors)
	}

	for cell, _ := range self.relations {
		cell.Update()
	}
}

func (self *Grid) String() string {
	builder := bytes.NewBufferString("\n")
	for _, row := range self.cells {
		for _, cell := range row {
			if cell.IsAlive() {
				builder.WriteString("x")
			} else {
				builder.WriteString("-")
			}
		}
		builder.WriteString("\n")
	}
	return builder.String()
}

type point struct {
	x int
	y int
}

func (self point) isOnGrid(grid [][]*Cell) bool {
	return self.x >= 0 &&
		self.y >= 0 &&
		self.x < len(grid[0]) &&
		self.y < len(grid)
}

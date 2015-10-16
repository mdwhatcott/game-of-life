package life

type Cell struct {
	updater func()
	alive   bool
}

func Dead() *Cell {
	return new(Cell)
}

func Alive() *Cell {
	cell := Dead()
	cell.Revive()
	return cell
}

func (self *Cell) IsAlive() bool {
	return self.alive
}

func (self *Cell) Revive() {
	self.alive = true
}

func (self *Cell) Kill() {
	self.alive = false
}

func (self *Cell) Scan(neighbors []*Cell) {
	alive := self.scanForLifeSigns(neighbors)
	self.decideFate(alive)
}
func (self *Cell) scanForLifeSigns(neighbors []*Cell) int {
	alive := 0
	for _, neighbor := range neighbors {
		if neighbor.IsAlive() {
			alive++
		}
	}
	return alive
}
func (self *Cell) decideFate(alive int) {
	if self.IsAlive() {
		self.handleLiving(alive)
	} else {
		self.handleDead(alive)
	}
}
func (self *Cell) handleLiving(alive int) {
	if alive < 2 || alive > 3 {
		self.updater = self.Kill
	} else {
		self.updater = self.Revive
	}
}
func (self *Cell) handleDead(alive int) {
	if alive == 3 {
		self.updater = self.Revive
	} else {
		self.updater = self.Kill
	}
}

func (self *Cell) Update() {
	self.updater()
}

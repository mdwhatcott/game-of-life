package life

type cell struct {
	updater func()
	alive   bool
}

func newDeadCell() *cell {
	return new(cell)
}

func newLiveCell() *cell {
	cell := newDeadCell()
	cell.revive()
	return cell
}

func (self *cell) isAlive() bool {
	return self.alive
}

func (self *cell) revive() {
	self.alive = true
}

func (self *cell) kill() {
	self.alive = false
}

func (self *cell) scan(neighbors []*cell) {
	alive := self.scanForLifeSigns(neighbors)
	self.decideFate(alive)
}
func (self *cell) scanForLifeSigns(neighbors []*cell) int {
	alive := 0
	for _, neighbor := range neighbors {
		if neighbor.isAlive() {
			alive++
		}
	}
	return alive
}
func (self *cell) decideFate(alive int) {
	if self.isAlive() {
		self.handleLiving(alive)
	} else {
		self.handleDead(alive)
	}
}
func (self *cell) handleLiving(alive int) {
	if alive < 2 || alive > 3 {
		self.updater = self.kill
	} else {
		self.updater = self.revive
	}
}
func (self *cell) handleDead(alive int) {
	if alive == 3 {
		self.updater = self.revive
	} else {
		self.updater = self.kill
	}
}

func (self *cell) update() {
	self.updater()
}

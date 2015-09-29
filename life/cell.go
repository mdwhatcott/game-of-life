package life

type Cell struct {
	update func()
	alive  bool
}

func (self *Cell) IsAlive() bool {
	return self.alive
}

func (self *Cell) Activate() {
	self.alive = true
}

func (self *Cell) Deactivate() {
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
		self.update = self.Deactivate
	} else {
		self.update = self.Activate
	}
}
func (self *Cell) handleDead(alive int) {
	if alive == 3 {
		self.update = self.Activate
	} else {
		self.update = self.Deactivate
	}
}

func (self *Cell) Update() {
	self.update()
}

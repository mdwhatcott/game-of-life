package ui

import (
	"fmt"
	"strings"
)

func HTML(grid fmt.Stringer) *html {
	self := new(html)
	self.grid = grid
	return self
}

type html struct {
	grid fmt.Stringer
}

func (self *html) String() string {
	raw := self.grid.String()
	raw = strings.Replace(raw, "\n", "<br>", -1)
	raw = strings.Replace(raw, "-", "&nbsp;", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return raw
}

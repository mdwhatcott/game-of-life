package ui

import (
	"fmt"
	"strings"
)

func Console(inner fmt.Stringer) *console {
	self := new(console)
	self.inner = inner
	return self
}

type console struct {
	inner fmt.Stringer
}

func (self *console) String() string {
	raw := self.inner.String()
	raw = strings.Replace(raw, "-", " ", -1)
	return raw
}

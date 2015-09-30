package ui

import (
	"fmt"
	"strings"
)

func HTML(inner fmt.Stringer) *html {
	self := new(html)
	self.inner = inner
	return self
}

type html struct {
	inner fmt.Stringer
}

func (self *html) String() string {
	raw := self.inner.String()
	raw = strings.Replace(raw, "\n", "<br>", -1)
	raw = strings.Replace(raw, "-", "&nbsp;", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return raw
}

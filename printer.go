package main

import (
	"fmt"
	"strings"
)

type HtmlStringer struct {
	inner fmt.Stringer
}

func (self *HtmlStringer) String() string {
	raw := self.inner.String()
	raw = strings.Replace(raw, "\n", "<br>", -1)
	raw = strings.Replace(raw, "-", "&nbsp;", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return raw
}

func NewHtmlStringer(inner fmt.Stringer) *HtmlStringer {
	self := new(HtmlStringer)
	self.inner = inner
	return self
}

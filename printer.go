package main

import (
	"bytes"
	"fmt"
	"strings"
)

type HtmlPrinter struct {
	inner fmt.Stringer
}

func (self *HtmlPrinter) String() string {
	raw := self.inner.String()
	raw = strings.Replace(raw, "\n", "<br>", -1)
	raw = strings.Replace(raw, "-", "&nbsp;", -1)
	raw = strings.Replace(raw, "x", "•", -1)
	return raw
}

func NewHtmlPrinter(inner fmt.Stringer) *HtmlPrinter {
	self := new(HtmlPrinter)
	self.inner = inner
	return self
}

///////////////////////////////////

type ConsolePrinter struct {
	grid *Grid
}

func (self *ConsolePrinter) String() string {
	builder := bytes.NewBufferString("\n")
	for _, row := range self.grid.Cells {
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

func NewConsolePrinter(grid *Grid) *ConsolePrinter {
	self := new(ConsolePrinter)
	self.grid = grid
	return self
}

package ui

import (
	"bytes"
	"testing"

	"github.com/smartystreets/assertions"
	"github.com/smartystreets/assertions/should"
)

func TestConsoleUI(t *testing.T) {
	var (
		input    = "----------x--------------"
		expected = "          •              "
		actual   = console(bytes.NewBufferString(input))
	)
	assertions.New(t).So(actual, should.Equal, expected)
}

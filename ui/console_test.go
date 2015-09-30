package ui

import (
	"bytes"
	"testing"

	"github.com/smartystreets/assertions"
	"github.com/smartystreets/assertions/should"
)

func TestConsoleUI(t *testing.T) {
	var (
		input    = "-------------------------"
		expected = "                         "
		actual   = Console(bytes.NewBufferString(input)).String()
	)
	assertions.New(t).So(actual, should.Equal, expected)
}

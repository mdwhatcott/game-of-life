package ui

import (
	"bytes"
	"testing"

	"github.com/smartystreets/assertions"
	"github.com/smartystreets/assertions/should"
)

func TestHTMLUI(t *testing.T) {
	var (
		input    = "\n\n--xx"
		expected = "<br><br>&nbsp;&nbsp;••"
		actual   = HTML(bytes.NewBufferString(input)).String()
	)
	assertions.New(t).So(actual, should.Equal, expected)
}

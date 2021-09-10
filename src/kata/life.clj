(ns kata.life)

; The Game of Life, devised by John Conway, is a zero-player
; game, meaning that its evolution is determined by its initial
; state, requiring no further input. One interacts with the Game
; of Life by creating an initial configuration and observing how
; it evolves.
;
; The universe of the Game of Life is an infinite,
; two-dimensional grid of square cells, each of which is in one
; of two possible states, active or inactive. Every cell
; interacts with its eight neighbours, which are the cells that
; are horizontally, vertically, or diagonally adjacent.
;
; At each step in time, the following transitions occur:
;
; 1. Any active cell with two or three active neighbours survives.
; 2. Any inactive cell with three active neighbours becomes active.
; 3. All other cells die in the next generation.

; Each generation is a pure function of the preceding one.
; The rules continue to be applied repeatedly to create further
; generations.
;
; (en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

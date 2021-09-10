(ns kata.life)

(defn neighbors-of [[x y]]
  (for [Y (range (dec y) (+ 2 y))
        X (range (dec x) (+ 2 x))
        :when (not= [x y] [X Y])] [X Y]))

(defn count-active-neighbors [cell grid]
  (->> cell neighbors-of set (filter grid) count))

(defn update-cell [cell grid]
  (let [n (count-active-neighbors cell grid)]
    (cond (= n 3) cell
          (not= n 2) nil
          (grid cell) cell)))

(defn evolve [grid]
  (->> (mapcat neighbors-of grid)
       (map #(update-cell % grid))
       (remove nil?) set))

; The Game of Life, devised by John Conway, is a zero-player
; game, meaning that its evolution is determined by its
; initial state, requiring no further input. One interacts
; with the Game of Life by creating an initial configuration
; and observing how it evolves.
;
; The universe of the Game of Life is an infinite,
; two-dimensional grid of square cells, each of which is in
; one of two possible states, active or inactive. Every cell
; interacts with its eight neighbours, which are the cells
; that are horizontally, vertically, or diagonally adjacent.
;
; At each step in time, the following transitions occur:
;
; 1. Any active cell with 2 or 3 active neighbours survives
; 2. Any inactive cell with 3 active neighbours becomes active
; 3. All other cells will be inactive in the next generation

; Each generation is a pure function of the preceding one.
; The rules continue to be applied repeatedly to create
; further generations.
;
; (en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

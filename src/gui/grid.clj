(ns gui.grid)

(defn game-cell->grid-cell [game-cell grid]
  (let [grid-upper-left-x  (get-in grid [:bounds 0 0])
        grid-lower-right-x (get-in grid [:bounds 1 0])
        grid-width         (- grid-lower-right-x grid-upper-left-x)
        cell-width         (/ grid-width (:cell-row-count grid))
        cell-x             (* cell-width (first game-cell))
        cell-y             (* cell-width (second game-cell))]
    [[cell-x cell-y]
     [(+ cell-x cell-width) (+ cell-y cell-width)]]))

(defn grid-cell->game-cell [grid-cell grid]
  (let [[x y] (first grid-cell)
        width (:cell-row-count grid)]
    [(/ x width) (/ y width)]))
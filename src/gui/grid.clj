(ns gui.grid
  (:require
    [gui.bounds :as bounds]))

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
        grid-upper-left-x  (get-in grid [:bounds 0 0])
        grid-lower-right-x (get-in grid [:bounds 1 0])
        grid-width         (- grid-lower-right-x grid-upper-left-x)
        width              (/ grid-width (:cell-row-count grid))]
    [(int (/ x width))
     (int (/ y width))]))


(defn full-square-grid [per-row width]
  (for [y (range per-row)
        x (range per-row)]
    [(* x width) (* y width)]))

(defn setup [state bounds cell-row-count cell-width]
  (assoc state
    :grid {:bounds         bounds
           :cell-row-count cell-row-count
           :live-cells     #{}
           :cell-width     cell-width
           :intro-grid     (full-square-grid cell-row-count
                                             cell-width)}))

(defn update_ [state]
  (if (= :playing (:player state))
    state
    (let [{:keys [x y clicked?]} (:mouse state)
          grid (:grid state)]
      (cond (not clicked?) state
            (not (bounds/bounded? [x y] (:bounds grid))) state
            :else
            (let [
                  grid-cells (get-in state [:grid :live-cells])
                  game-cell  (grid-cell->game-cell [[x y]] grid)
                  grid-cell  (game-cell->grid-cell game-cell grid)
                  is-alive?  (contains? grid-cells grid-cell)
                  action     (if is-alive? disj conj)
                  updated    (action grid-cells grid-cell)]
              (assoc-in state [:grid :live-cells] updated))))))
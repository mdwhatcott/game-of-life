(ns gui.game
  (:require
    [gui.grid :as gui-grid]
    [life.rules :as rules]))

(defn playing? [state]
  (= :playing (:player state)))

(defn initialize-game-from-grid [state]
  (let [grid       (:grid state)
        grid-cells (:live-cells grid)
        game-cells (map #(gui-grid/grid-cell->game-cell % grid) grid-cells)
        updated    (assoc state :game (set game-cells))]
    updated))

(defn is-outlier? [cell row-count]
  (or (< (first cell) 0)
      (< (second cell) 0)
      (>= (first cell) row-count)
      (>= (second cell) row-count)))

(defn remove-outlier-cells [row-count cells]
  (set (remove #(is-outlier? % row-count) cells)))

(defn advance-game [state]
  (let [row-count (get-in state [:grid :cell-row-count])
        game1     (:game state)
        game2     (rules/evolve game1)
        game2     (remove-outlier-cells row-count game2)
        grid1     (:grid state)
        cells     (map #(gui-grid/game-cell->grid-cell % grid1) game2)
        grid2     (assoc grid1 :live-cells cells)]
    (assoc state :game game2
                 :grid grid2)))

(defn ready-to-advance? [state]
  (let [per    (:frames-per-evolution state)
        frames (:frame-count state)]
    (zero? (mod frames per))))

(defn update_ [state]
  (cond (not (playing? state)) state
        (nil? (:game state)) (initialize-game-from-grid state)
        (ready-to-advance? state) (advance-game state)
        :else state))

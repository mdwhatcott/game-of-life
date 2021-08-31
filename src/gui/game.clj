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

(defn advance-game [state]
  (assoc state :game (rules/evolve (:game state))))

(defn ready-to-advance? [state]
  (let [per    (:frames-per-evolution state)
        frames (:frame-count state)]
    (zero? (mod frames per))))

(defn update_ [state]
  (cond (not (playing? state)) state
        (nil? (:game state)) (initialize-game-from-grid state)
        (ready-to-advance? state) (advance-game state)
        :else state))

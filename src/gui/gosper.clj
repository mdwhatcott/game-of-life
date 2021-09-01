(ns gui.gosper
  (:require [gui.grid :as grid]))

; https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_glider_gun.svg
(def gospers-glider-gun
  [
   ; left block
   [1 5]
   [2 5]
   [1 6]
   [2 6]

   ; right block
   [35 3]
   [36 3]
   [35 4]
   [36 4]

   ; central circle
   [13 3]
   [14 3]
   [12 4]
   [16 4]
   [11 5]
   [17 5]
   [11 6]
   [15 6]
   [17 6]
   [18 6]
   [11 7]
   [17 7]
   [12 8]
   [16 8]
   [13 9]
   [14 9]

   ; central squiggle
   [25 1]
   [23 2]
   [25 2]
   [21 3]
   [22 3]
   [21 4]
   [22 4]
   [21 5]
   [22 5]
   [23 6]
   [25 6]
   [25 7]])

(defn setup [state]
  (let [grid (:grid state)]
    (assoc-in state [:grid :live-cells]
              (set (map #(grid/game-cell->grid-cell % grid)
                        gospers-glider-gun)))))


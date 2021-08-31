(ns gui.grid-spec
  (:require
    [speclj.core :refer :all]
    [gui.grid :refer :all]
    [gui.bounds :as bounds]))

(def grid-bounds (bounds/bounding-cube [50 50] 100))

(def grid {:bounds grid-bounds :cell-row-count 10})

(def game-cells [[0 0]
                 [1 1]])

(def grid-cells [[[0 0] [10 10]]
                 [[10 10] [20 20]]])

(describe "Grid Rendering"
  (it "translates cell locations to bounding boxes relative to a square grid"
    (let [actual (map #(game-cell->grid-cell % grid) game-cells)]
      (should= grid-cells actual)))

  (it "translates bounding boxes relative to a grid back to cell locations"
    (let [actual (map #(grid-cell->game-cell % grid) grid-cells)]
      (should= game-cells actual)))
  )

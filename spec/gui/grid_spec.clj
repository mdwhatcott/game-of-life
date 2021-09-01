(ns gui.grid-spec
  (:require
    [speclj.core :refer :all]
    [gui.grid :refer :all]
    [gui.controller-spec :refer :all]
    [gui.bounds :as bounds]))

(def grid-bounds (bounds/bounding-cube [50 50] 100))

(def grid {:bounds         grid-bounds
           :cell-row-count 2
           :cell-width     2
           :live-cells     #{}
           :intro-grid     [[0 0] [2 0]
                            [0 2] [2 2]]})

(def game-cells [[0 0]
                 [1 1]])

(def grid-cells [[[0 0] [50 50]]
                 [[50 50] [100 100]]])

(describe "Grid Translations"
  (it "translates cell locations to bounding boxes relative to a square grid"
    (let [actual (map #(game-cell->grid-cell % grid) game-cells)]
      (should= grid-cells actual)))

  (it "translates bounding boxes relative to a grid back to cell locations"
    (let [actual (map #(grid-cell->game-cell % grid) grid-cells)]
      (should= game-cells actual)))
  )

(describe "Manual Grid Updates"
  (it "starts with an empty grid"
    (should= {:grid grid} (setup {} grid-bounds 2 2)))

  (it "makes dead cells come alive when clicked before start of game"
    (let [input  (setup {:mouse (click-at 1 1)} grid-bounds 10 10)
          result (update_ input)]
      (should= #{[[0 0] [10 10]]} (:live-cells (:grid result)))))

  (it "makes alive cells go dead when clicked before start of game"
    (let [input      (setup {:mouse (click-at 1 1)} grid-bounds 10 10)
          already-on (assoc-in input [:grid :live-cells] #{[[0 0] [10 10]]})
          result     (update_ already-on)]
      (should= #{} (:live-cells (:grid result)))))

  (it "leaves cell state alone when just hovering"
    (let [input  (setup {:mouse (pointing-at 1 1)} grid-bounds 10 10)
          result (update_ input)]
      (should= #{} (:live-cells (:grid result)))))

  (it "ignores mouse clicks that are outside the bounds of the grid"
    (let [input  (setup {:mouse (click-at 1058 1039)} grid-bounds 10 10)
          result (update_ input)]
      (should= #{} (:live-cells (:grid result)))))

  (it "ignores mouse-clicks on cells once the game has started"
    (let [input           (setup {:mouse (click-at 1 1)} grid-bounds 10 10)
          already-playing (assoc input :player :playing)
          result          (update_ already-playing)]
      (should= #{} (:live-cells (:grid result)))))
  )

(describe "Generating Entire Grids (for drawing)"
  (it "calculates all upper-left corners"
    (let [result (full-square-grid 3 10)]
      (should= [[0 0] [10 0] [20 0]
                [0 10] [10 10] [20 10]
                [0 20] [10 20] [20 20]] result))))
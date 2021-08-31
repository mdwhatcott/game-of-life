(ns gui.bounds-spec
  (:require
    [speclj.core :refer :all]
    [gui.bounds :refer :all]))

(describe "Bounding Boxes"

  (it "calculates a bounding box from a center point, width, and height"
    (should= [[0 0] [10 10]] (bounding-cube [5 5] 10)))

  (it "identifies when a containing coordinate"
    (let [box (bounding-cube [5 5] 10)]
      (should= true (bounded? [1 1] box))
      (should= true (bounded? [9 9] box))
      (should= false (bounded? [10 10] box))
      (should= false (bounded? [1 11] box)))
    )
  )

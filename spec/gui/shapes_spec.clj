(ns gui.shapes-spec
  (:require
    [quil.core :as q]
    [speclj.core :refer :all]
    [gui.shapes :refer :all]
    [gui.render :refer :all]))


(def input-drawing-state
  {
   :grid {:cell-width 10
          :intro-grid [[0 0]
                       [1 1]
                       [2 2]
                       [3 3]]
          :live-cells [[[4 4] nil]
                       [[5 5] nil]
                       [[6 6] nil]
                       [[7 7] nil]]}})

(def expected-shapes-before-game-start
  [
   ; background
   {:shape :background, :color 240}

   ; grid lines
   {:shape :fill, :color 240}
   {:shape :stroke, :color 128}
   {:shape :rectangle, :x 0, :y 0, :width 10, :height 10}
   {:shape :rectangle, :x 1, :y 1, :width 10, :height 10}
   {:shape :rectangle, :x 2, :y 2, :width 10, :height 10}
   {:shape :rectangle, :x 3, :y 3, :width 10, :height 10}

   ; intro text
   {:shape :fill, :color 50}
   {:shape :stroke, :color 50}
   {:shape :text, :x-align :center, :y-align :center, :x 250, :y 550,
    :text  "Click a few squares, then click down here to begin!"}

   ; live cells
   {:shape :fill, :color 50}
   {:shape :stroke, :color 50}
   {:shape :rectangle, :x 4, :y 4, :width 10, :height 10}
   {:shape :rectangle, :x 5, :y 5, :width 10, :height 10}
   {:shape :rectangle, :x 6, :y 6, :width 10, :height 10}
   {:shape :rectangle, :x 7, :y 7, :width 10, :height 10}])

(def expected-shapes-after-game-start
  [
   ; background
   {:shape :background, :color 240}

   ; blacked-out intro text
   {:shape :fill, :color 50}
   {:shape :stroke, :color 50}
   {:shape :rectangle, :x 0 :y 500, :width 500, :height 100}

   ; live cells
   {:shape :fill, :color 50}
   {:shape :stroke, :color 50}
   {:shape :rectangle, :x 4, :y 4, :width 10, :height 10}
   {:shape :rectangle, :x 5, :y 5, :width 10, :height 10}
   {:shape :rectangle, :x 6, :y 6, :width 10, :height 10}
   {:shape :rectangle, :x 7, :y 7, :width 10, :height 10}])

(describe "Preparing Shapes"
  (with-stubs)

  (it "draws background, grid lines, intro text, and live cells before the game starts"
    (let [state (assoc input-drawing-state :player :stopped)
          draw  (stub :draw)]
      (with-redefs [shape draw]
        (->> (all state)
             (should= expected-shapes-before-game-start)))))

  (it "draws background and live cells before the game starts"
      (let [state (assoc input-drawing-state :player :playing)
            draw  (stub :draw)]
        (with-redefs [shape draw]
          (->> (all state)
               (should= expected-shapes-after-game-start)))))
  )

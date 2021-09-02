(ns gui.draw-spec
  (:require
    [quil.core :as q]
    [speclj.core :refer :all]
    [gui.draw :refer :all]))

(describe "Drawing Shapes"
  (with-stubs)

  (it "draws a rectangle"
    (let [rectangle {:shape  :rectangle
                     :fill   1
                     :stroke 2
                     :x      3
                     :y      4
                     :width  5
                     :height 6}
          fill      (stub :fill)
          stroke    (stub :stroke)
          rect      (stub :rect)]
      (with-redefs [q/fill   fill
                    q/stroke stroke
                    q/rect   rect]

        (render-shapes [rectangle])

        (should-have-invoked :fill {:with [1] :times 1})
        (should-have-invoked :stroke {:with [2] :times 1})
        (should-have-invoked :rect {:with [3 4 5 6] :times 1}))))

  (it "draws text"
    (let [shape      {:shape   :text
                      :fill    1
                      :x-align :center
                      :y-align :center
                      :text    "message"
                      :x       2
                      :y       3}
          fill       (stub :fill)
          text-align (stub :text-align)
          text       (stub :text)]
      (with-redefs [q/fill       fill
                    q/text-align text-align
                    q/text       text]

        (render-shapes [shape])

        (should-have-invoked :fill {:with [1] :times 1})
        (should-have-invoked :text-align {:with [:center :center] :times 1})
        (should-have-invoked :text {:with ["message" 2 3] :times 1}))))

  (it "draws a background"
    (let [shape      {:shape :background
                      :color 42}
          background (stub :background)]
      (with-redefs [q/background background]

        (render-shapes [shape])

        (should-have-invoked :background {:with [42] :times 1}))))

  )


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
   {:shape :rectangle, :fill 240, :stroke 128, :x 0, :y 0, :width 10, :height 10}
   {:shape :rectangle, :fill 240, :stroke 128, :x 1, :y 1, :width 10, :height 10}
   {:shape :rectangle, :fill 240, :stroke 128, :x 2, :y 2, :width 10, :height 10}
   {:shape :rectangle, :fill 240, :stroke 128, :x 3, :y 3, :width 10, :height 10}

   ; intro text
   {:shape :text, :fill 0, :x-align :center, :y-align :center, :x 250, :y 550,
    :text  "Click a few squares, then click down here to begin!"}

   ; live cells
   {:shape :rectangle, :fill 50, :stroke 50, :x 4, :y 4, :width 10, :height 10}
   {:shape :rectangle, :fill 50, :stroke 50, :x 5, :y 5, :width 10, :height 10}
   {:shape :rectangle, :fill 50, :stroke 50, :x 6, :y 6, :width 10, :height 10}
   {:shape :rectangle, :fill 50, :stroke 50, :x 7, :y 7, :width 10, :height 10}])

(def expected-shapes-after-game-start
  [
   ; background
   {:shape :background, :color 240}

   ; blacked-out intro text
   {:shape :rectangle, :fill 50, :stroke 50, :x 0 :y 500, :width 500, :height 100}

   ; live cells
   {:shape :rectangle, :fill 50, :stroke 50, :x 4, :y 4, :width 10, :height 10}
   {:shape :rectangle, :fill 50, :stroke 50, :x 5, :y 5, :width 10, :height 10}
   {:shape :rectangle, :fill 50, :stroke 50, :x 6, :y 6, :width 10, :height 10}
   {:shape :rectangle, :fill 50, :stroke 50, :x 7, :y 7, :width 10, :height 10}])

(describe "Preparing Shapes"
  (with-stubs)

  (it "draws background, grid lines, intro text, and live cells before the game starts"
    (let [state (assoc input-drawing-state :player :stopped)
          draw  (stub :draw)]
      (with-redefs [render-shape draw]
        (->> (all state)
             (should= expected-shapes-before-game-start)))))

  (it "draws background, grid lines, intro text, and live cells before the game starts"
    (let [state (assoc input-drawing-state :player :playing)
          draw  (stub :draw)]
      (with-redefs [render-shape draw]
        (->> (all state)
             (should= expected-shapes-after-game-start)))))
  )

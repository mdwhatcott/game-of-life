(ns gui.draw-spec
  (:require
    [quil.core :as q]
    [speclj.core :refer :all]
    [gui.draw :refer :all]))

(describe "Rendering Shapes"
  (with-stubs)

  (it "renders fill color"
    (let [shape {:shape :fill
                 :color 42}]
      (with-redefs [q/fill (stub :fill)]

        (render-shapes [shape])

        (should-have-invoked :fill {:with [42] :times 1}))))

  (it "renders stroke color"
    (let [shape {:shape :stroke
                 :color 42}]
      (with-redefs [q/stroke (stub :stroke)]

        (render-shapes [shape])

        (should-have-invoked :stroke {:with [42] :times 1}))))

  (it "renders a rectangle"
    (let [rectangle {:shape  :rectangle
                     :x      1
                     :y      2
                     :width  3
                     :height 4}]
      (with-redefs [q/rect (stub :rect)]

        (render-shapes [rectangle])

        (should-have-invoked :rect {:with [1 2 3 4] :times 1}))))

  (it "renders text"
    (let [shape {:shape   :text
                 :x-align :center
                 :y-align :center
                 :text    "message"
                 :x       1
                 :y       2}]
      (with-redefs [q/text-align (stub :text-align)
                    q/text       (stub :text)]

        (render-shapes [shape])

        (should-have-invoked :text-align {:with [:center :center] :times 1})
        (should-have-invoked :text {:with ["message" 1 2] :times 1}))))

  (it "renders a background"
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
      (with-redefs [render-shape draw]
        (->> (all state)
             (should= expected-shapes-before-game-start)))))

  (it "draws background and live cells before the game starts"
      (let [state (assoc input-drawing-state :player :playing)
            draw  (stub :draw)]
        (with-redefs [render-shape draw]
          (->> (all state)
               (should= expected-shapes-after-game-start)))))
  )

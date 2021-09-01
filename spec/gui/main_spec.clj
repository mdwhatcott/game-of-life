(ns gui.main-spec
  (:require
    [quil.core :as q]
    [speclj.core :refer :all]
    [gui.main :refer :all]))


(describe "Setup"
  (it "initializes all the initial state"
    (let [initial (setup-root)]
      (should= {:mouse                {:ready-to-click? true
                                       :clicked?        false
                                       :x               nil
                                       :y               nil}
                :frame-count          0
                :frames-per-evolution 5
                :player               :stopped
                :play-button          {:bounds    [[0 500] [500 600]]
                                       :hovering? false}
                :grid                 {:bounds         [[0 0] [500 500]]
                                       :cell-row-count 50
                                       :live-cells     #{}
                                       :intro-grid     full-grid
                                       :cell-width     10}
                } initial))))

(def test-drawing-state
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

(defn assert-background-drawn []
  (should-have-invoked :background {:with [240] :times 1}))

(defn assert-intro-grid-drawn [times]
  (should-have-invoked :stroke {:with [128] :times times})
  (should-have-invoked :fill {:with [240] :times times})
  (should-have-invoked :rect {:with [0 0 10 10] :times times})
  (should-have-invoked :rect {:with [1 1 10 10] :times times})
  (should-have-invoked :rect {:with [2 2 10 10] :times times})
  (should-have-invoked :rect {:with [3 3 10 10] :times times}))

(defn assert-intro-text-drawn [times]
  (should-have-invoked :fill {:with [0] :times times})
  (should-have-invoked :text-align {:with [:center :center] :times times})
  (should-have-invoked :text {:with [click-here-text 250 550] :times times}))

(defn assert-live-cells-drawn []
  (should-have-invoked :fill {:with [50]})
  (should-have-invoked :stroke {:with [50]})
  (should-have-invoked :rect {:with [4 4 10 10] :times 1})
  (should-have-invoked :rect {:with [5 5 10 10] :times 1})
  (should-have-invoked :rect {:with [6 6 10 10] :times 1})
  (should-have-invoked :rect {:with [7 7 10 10] :times 1}))

(describe "Drawing"
  (with-stubs)

  (context "Introductory Elements"
    (it "draws grid lines, 'click here' text, and live cells before the game starts"
      (let [state (assoc test-drawing-state :player :stopped)]
        (with-redefs [q/background (stub :background)
                      q/fill       (stub :fill)
                      q/stroke     (stub :stroke)
                      q/rect       (stub :rect)
                      q/text-align (stub :text-align)
                      q/text       (stub :text)]

          (draw-root state)

          (assert-background-drawn)
          (assert-intro-grid-drawn 1)
          (assert-intro-text-drawn 1)
          (assert-live-cells-drawn))))


    (it "only draws live cells after the game has started"
      (let [state (assoc test-drawing-state :player :playing)]
        (with-redefs [q/background (stub :background)
                      q/fill       (stub :fill)
                      q/stroke     (stub :stroke)
                      q/rect       (stub :rect)
                      q/text-align (stub :text-align)
                      q/text       (stub :text)]

          (draw-root state)

          (assert-background-drawn)
          (assert-intro-grid-drawn 0)
          (assert-intro-text-drawn 0)
          (assert-live-cells-drawn))))

    )
  )
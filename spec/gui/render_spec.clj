(ns gui.render-spec
  (:require
    [quil.core :as q]
    [speclj.core :refer :all]
    [gui.render :refer :all]))

(describe "Rendering Shapes"
  (with-stubs)

  (it "renders fill color"
    (let [shape {:shape :fill :color 42}]
      (with-redefs [q/fill (stub :fill)]

        (shapes [shape])

        (should-have-invoked :fill {:with [42] :times 1}))))

  (it "renders stroke color"
    (let [shape {:shape :stroke :color 42}]
      (with-redefs [q/stroke (stub :stroke)]

        (shapes [shape])

        (should-have-invoked :stroke {:with [42] :times 1}))))

  (it "renders a rectangle"
    (let [rectangle {:shape  :rectangle
                     :x      1
                     :y      2
                     :width  3
                     :height 4}]
      (with-redefs [q/rect (stub :rect)]

        (shapes [rectangle])

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

        (shapes [shape])

        (should-have-invoked :text-align {:with [:center :center] :times 1})
        (should-have-invoked :text {:with ["message" 1 2] :times 1}))))

  (it "renders a background"
    (let [shape {:shape :background :color 42}]
      (with-redefs [q/background (stub :background)]

        (shapes [shape])

        (should-have-invoked :background {:with [42] :times 1}))))

  )

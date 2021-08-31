(ns gui.bounds)

(defn bounded? [[x y] [[x1 y1] [x2 y2]]]
  (and (> x x1) (< x x2)
       (> y y1) (< y y2)))

(defn bounding-box [[x y] width height]
  (let [w (/ width 2)
        h (/ height 2)]
    [[(- x w) (- y h)]
     [(+ x w) (+ x h)]]))

(defn bounding-cube [[x y] width]
  (bounding-box [x y] width width))

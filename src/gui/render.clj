(ns gui.render
  (:require
    [quil.core :as q]))

(defn render-fill [s]
  (q/fill (:color s)))

(defn render-stroke [s]
  (q/stroke (:color s)))

(defn render-rectangle [shape]
  (let [{:keys [x y width height]} shape]
    (q/rect x y width height)))

(defn render-text [shape]
  (let [{:keys [x-align y-align text x y]} shape]
    (q/text-align x-align y-align)
    (q/text text x y)))

(defn render-background [shape]
  (q/background (:color shape)))

(defn render-shape [s]
  (case (:shape s)
    :fill (render-fill s)
    :stroke (render-stroke s)
    :background (render-background s)
    :rectangle (render-rectangle s)
    :text (render-text s)))

(defn render-shapes [shapes]
  (doseq [s shapes]
    (render-shape s))
  shapes)

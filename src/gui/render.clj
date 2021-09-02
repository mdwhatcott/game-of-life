(ns gui.render
  (:require
    [quil.core :as q]))

(defn fill [s]
  (q/fill (:color s)))

(defn stroke [s]
  (q/stroke (:color s)))

(defn rectangle [shape]
  (let [{:keys [x y width height]} shape]
    (q/rect x y width height)))

(defn text [shape]
  (let [{:keys [x-align y-align text x y]} shape]
    (q/text-align x-align y-align)
    (q/text text x y)))

(defn background [shape]
  (q/background (:color shape)))

(defn shape [s]
  (case (:shape s)
    :fill (fill s)
    :stroke (stroke s)
    :background (background s)
    :rectangle (rectangle s)
    :text (text s)))

(defn shapes [shapes]
  (doseq [s shapes]
    (shape s))
  shapes)

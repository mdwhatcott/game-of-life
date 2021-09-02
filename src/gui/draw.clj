(ns gui.draw
  (:require
    [quil.core :as q]
    [gui.bounds :as bounds]
    [gui.game :as game]))

(def window-width 500)
(def window-height 600)

(def half-width (int (/ window-width 2)))

(def grid-center [half-width half-width])

(def control-panel-center [half-width 550])
(def control-panel-height (- window-height window-width))
; 100px-tall strip at bottom of window
(def control-panel-bounds
  (bounds/bounding-box control-panel-center
                       window-width
                       control-panel-height))

; 500px square above control panel
(def grid-bounds
  (bounds/bounding-cube grid-center window-width))

(def cells-per-row 50)

(def width-of-each-cell
  (/ window-width cells-per-row))

(def color
  {:background 240
   :grid-lines 128
   :text       0
   :cell       50})

(def click-here-text
  "Click a few squares, then click down here to begin!")

(defn render-rectangle [shape]
  (let [{:keys [fill stroke x y width height]} shape]
    (q/fill fill)
    (q/stroke stroke)
    (q/rect x y width height)))

(defn render-text [shape]
  (let [{:keys [fill x-align y-align text x y]} shape]
    (q/fill fill)
    (q/text-align x-align y-align)
    (q/text text x y)))

(defn render-background [shape]
  (q/background (:color shape)))

(defn render-shape [s]
  (case (:shape s)
    :background (render-background s)
    :rectangle (render-rectangle s)
    :text (render-text s)))

(defn render-shapes [shapes]
  (doseq [s shapes]
    (render-shape s))
  shapes)

(defn intro-grid-shapes [state]
  (let [{:keys [cell-width intro-grid]} (:grid state)]
    (for [[x y] (if (game/playing? state) [] intro-grid)]
      {:shape  :rectangle
       :fill   (:background color)
       :stroke (:grid-lines color)
       :x      x
       :y      y
       :width  cell-width
       :height cell-width})))

(defn intro-text-shapes [state]
  (if (game/playing? state)
    (let [[[x y]] control-panel-bounds]
      [{:shape  :rectangle
        :fill   (:cell color)
        :stroke (:cell color)
        :x      x
        :y      y
        :width  window-width
        :height control-panel-height}])
    (let [[x y] control-panel-center]
      [{:shape   :text
        :fill    (:text color)
        :x-align :center
        :y-align :center
        :text    click-here-text
        :x       x
        :y       y}])))

(defn live-cells-shapes [state]
  (let [{:keys [cell-width live-cells]} (:grid state)]
    (for [[[x y] _upper-right] live-cells]
      {:shape  :rectangle
       :fill   (:cell color)
       :stroke (:cell color)
       :x      x
       :y      y
       :width  cell-width
       :height cell-width})))

(def background-shapes
  [{:shape :background
    :color (:background color)}])

(defn all [state]
  (render-shapes
    (concat background-shapes
            (intro-grid-shapes state)
            (intro-text-shapes state)
            (live-cells-shapes state))))

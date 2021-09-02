(ns gui.shapes
  (:require
    [gui.render :as render]
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
   :text       50
   :cell       50})

(def click-here-text
  "Click a few squares, then click down here to begin!")

(defn intro-grid-shapes [state]
  (if (game/playing? state)
    []
    (concat [{:shape :fill :color (:background color)}
             {:shape :stroke :color (:grid-lines color)}]
            (let [{:keys [cell-width intro-grid]} (:grid state)]
              (for [[x y] (if (game/playing? state) [] intro-grid)]
                {:shape  :rectangle
                 :x      x
                 :y      y
                 :width  cell-width
                 :height cell-width})))
    ))

(defn intro-text [state]
  (if (game/playing? state)
    (let [[[x y]] control-panel-bounds]
      {:shape  :rectangle
       :x      x
       :y      y
       :width  window-width
       :height control-panel-height})
    (let [[x y] control-panel-center]
      {:shape   :text
       :x-align :center
       :y-align :center
       :text    click-here-text
       :x       x
       :y       y})))

(defn intro-text-shapes [state]
  [{:shape :fill :color (:text color)}
   {:shape :stroke :color (:text color)}
   (intro-text state)])

(defn live-cells [state]
  (let [{:keys [cell-width live-cells]} (:grid state)]
    (for [[[x y] _upper-right] live-cells]
      {:shape  :rectangle
       :x      x
       :y      y
       :width  cell-width
       :height cell-width})))

(defn live-cells-shapes [state]
  (concat [{:shape :fill :color (:cell color)}
           {:shape :stroke :color (:cell color)}]
          (live-cells state)))

(def background-shapes
  [{:shape :background
    :color (:background color)}])

(defn all [state]
  (render/shapes
    (concat background-shapes
            (intro-grid-shapes state)
            (intro-text-shapes state)
            (live-cells-shapes state))))

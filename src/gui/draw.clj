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

(defn intro-grid [state]
  (when (not (game/playing? state))
    (let [{:keys [cell-width intro-grid]} (:grid state)]
      (q/fill (:background color))
      (q/stroke (:grid-lines color))
      (doseq [[x y] intro-grid]
        (q/rect x y cell-width cell-width)))))

(defn intro-text [state]
  (let [[x y] control-panel-center]
    (if (game/playing? state)
      (let [[[x y]] control-panel-bounds]
        (q/rect x y window-width control-panel-height))
      (do (q/fill (:text color))
          (q/text-align :center :center)
          (q/text click-here-text x y)))))

(defn live-cells [state]
  (let [{:keys [cell-width live-cells]} (:grid state)]
    (q/fill (:cell color))
    (q/stroke (:cell color))
    (doseq [[[x y] _upper-right] live-cells]
      (q/rect x y cell-width cell-width))))

(defn all [state]
  (q/background (:background color))
  (intro-grid state)
  (intro-text state)
  (live-cells state))

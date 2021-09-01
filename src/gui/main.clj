(ns gui.main
  (:require
    [quil.core :as q]
    [quil.middleware :as m]
    [gui.bounds :as bounds]
    [gui.controller :as controller]
    [gui.frame-count :as frame-count]
    [gui.game :as game]
    [gui.grid :as grid]
    [gui.mouse :as mouse]))

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

(def full-grid
  (grid/full-square-grid cells-per-row width-of-each-cell))

(def frames-per-evolution 5)

(def color
  {
   :background 240
   :grid-lines 128
   :text       0
   :cell       50})

(def click-here-text "Click a few squares, then click down here to begin!")

(defn setup-root []
  (-> (mouse/setup {})
      (frame-count/setup frames-per-evolution)
      (controller/setup control-panel-bounds)
      (grid/setup grid-bounds cells-per-row width-of-each-cell)))

(defn update-root [state]
  (-> state
      mouse/update_
      frame-count/update_
      controller/update_
      grid/update_
      game/update_))

(defn draw-intro-grid [state]
  (when (not (game/playing? state))
    (q/fill (:background color))
    (q/stroke (:grid-lines color))
    (let [{:keys [cell-width intro-grid]} (:grid state)]
      (doseq [[x y] intro-grid]
       (q/rect x y cell-width cell-width)))))

(defn draw-intro-text [state]
  (when (not (game/playing? state))
    (q/fill (:text color))
    (q/text-align :center :center)
    (let [[x y] control-panel-center]
      (q/text click-here-text x y))))

(defn draw-live-cells [state]
  (q/fill (:cell color))
  (q/stroke (:cell color))
  (let [{:keys [cell-width live-cells]} (:grid state)]
    (doseq [[[x y] _upper-right] live-cells]
     (q/rect x y cell-width cell-width))))

(defn draw-root [state]
  (q/background (:background color))
  (draw-intro-grid state)
  (draw-intro-text state)
  (draw-live-cells state))

(declare game-of-life)

(defn -main [& _args]
  (q/defsketch
    game-of-life
    :title "John Conway's Game of Life"
    :size [window-width window-height]
    :setup #'setup-root
    :update #'update-root
    :draw #'draw-root
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
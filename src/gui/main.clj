(ns gui.main
  (:require
    [quil.core :as q]
    [quil.middleware :as m]
    [gui.controller :as controller]
    [gui.frame-count :as frame-count]
    [gui.game :as game]
    [gui.gosper :as gosper]
    [gui.grid :as grid]
    [gui.mouse :as mouse]
    [gui.shapes :as shapes]))

(defn setup-root []
  (-> {}
      mouse/setup
      frame-count/setup
      (controller/setup shapes/control-panel-bounds)
      (grid/setup shapes/grid-bounds
                  shapes/cells-per-row
                  shapes/width-of-each-cell)
      gosper/setup))

(defn update-root [state]
  (-> state
      mouse/update_
      frame-count/update_
      controller/update_
      grid/update_
      game/update_))

(declare game-of-life)

(defn -main [& _args]
  (q/defsketch
    game-of-life
    :title "John Conway's Game of Life"
    :size [shapes/window-width shapes/window-height]
    :setup #'setup-root
    :update #'update-root
    :draw #'shapes/all
    :features [:keep-on-top]
    :middleware [m/fun-mode]))

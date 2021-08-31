(ns gui.main
  (:require
    [quil.core :as q]
    [quil.middleware :as m]
    [gui.frame-count :as frames]
    [gui.controller :as controller]
    [gui.bounds :as bounds]
    [gui.grid :as grid]
    [gui.mouse :as mouse]))

(defn setup-root []
  (-> (mouse/setup {})
      (frames/setup 10)
      (controller/setup (bounds/bounding-box [250 550] 500 100))
      (grid/setup (bounds/bounding-cube [250 250] 500) 50)))

(defn update-root [state]
  )

(defn draw-root [state]
  )

(declare game-of-life)

(defn -main [& _args]
  (q/defsketch
    game-of-life
    :title "John Conway's Game of Life"
    :size [500 600]
    :setup #'setup-root
    :update #'update-root
    :draw #'draw-root
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
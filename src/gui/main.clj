(ns gui.main
  (:require
    [quil.core :as q]
    [quil.middleware :as m]))

(defn setup-root []
  )

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
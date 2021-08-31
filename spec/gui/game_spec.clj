(ns gui.game_spec
  (:require
    [speclj.core :refer :all]
    [gui.game :refer :all]
    [gui.bounds :as bounds]
    [gui.grid :as gui-grid]
    [gui.frame-count :as frames]))

(defn update-all [state]
  (->> state
       frames/update_
       update_))

(describe "Playing the Game"

  (it "literally does nothing until the game starts"
    (let [input  {:player :stopped}
          result (update_ input)]
      (should= result input)))

  (it "initializes the game cells from the gui cells when the game starts"
    (let [input  {:player :playing}
          input  (gui-grid/setup input (bounds/bounding-cube [50 50] 100) 10)
          input  (assoc-in input [:grid :live-cells] #{[[0 0] [10 10]]})
          result (update_ input)]
      (should= #{[0 0]} (:game result))))

  (it "it advances/evolves the game every 'x' frames"
    (let [input  {:frames-per-evolution 2
                  :frame-count          0
                  :player               :playing
                  :game                 #{[1 1] [2 1] [3 1]}}
          states (take 4 (iterate update-all input))
          grids  (map :game states)]
      (should= (nth grids 0) (nth grids 1))
      (should= (nth grids 2) (nth grids 3))))

  )

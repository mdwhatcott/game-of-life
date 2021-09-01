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

; TODO: re-activate
#_(describe "Playing the Game"

  (it "literally does nothing until the game starts"
    (let [input  {:player :stopped}
          result (update_ input)]
      (should= result input)))

  (it "initializes the game cells from the gui cells when the game starts"
    (let [input  {:player :playing}
          input  (gui-grid/setup input (bounds/bounding-cube [50 50] 100) 10 10)
          input  (assoc-in input [:grid :live-cells] #{[[0 0] [10 10]]})
          result (update_ input)]
      (should= #{[0 0]} (:game result))))

  (it "it advances/evolves the game every 'x' frames"
    (let [input  {:frames-per-evolution 2
                  :frame-count          0
                  :player               :playing
                  :game                 #{[1 1] [2 1] [3 1]}}
          input  (gui-grid/setup input (bounds/bounding-cube [50 50] 100) 10 10)
          states (take 4 (iterate update-all input))
          games  (map :game states)
          grids  (map :grid states)
          grids  (map :live-cells grids)]

      (should= (nth games 0) (nth games 1))
      (should= (nth games 2) (nth games 3))
      (should-not= (nth games 0) (nth games 2))
      (should-not= (nth games 1) (nth games 3))

      (should= (nth grids 0) (nth grids 1))
      (should= (nth grids 2) (nth grids 3))
      (should-not= (nth grids 0) (nth grids 2))
      (should-not= (nth grids 1) (nth grids 3))))

  (it "filters cells with y values that are too low for the grid (negative)"
    (let [input  {:frames-per-evolution 1
                  :frame-count          0
                  :player               :playing
                  :game                 #{[1 0] [2 0] [3 0]}} ; top-most row
          input  (gui-grid/setup input (bounds/bounding-cube [50 50] 100) 10 10)
          result (update-all input)]
      (should= #{[2 0]
                 [2 1]} (:game result))
      (should= [[[20 0] [30 10]]
                [[20 10] [30 20]]] (:live-cells (:grid result)))))

  (it "filters cells with x values that are too low for the grid (negative)"
    (let [input  {:frames-per-evolution 1
                  :frame-count          0
                  :player               :playing
                  :game                 #{[0 0] [0 1] [0 2]}} ; left-most column
          input  (gui-grid/setup input (bounds/bounding-cube [50 50] 100) 10 10)
          result (update-all input)]
      (should= #{[0 1]
                 [1 1]} (:game result))
      (should= [[[10 10] [20 20]]
                [[0 10] [10 20]]] (:live-cells (:grid result)))))

  (it "filters cells with x values that are too high for the grid"
    (let [input  {:frames-per-evolution 1
                  :frame-count          0
                  :player               :playing
                  :game                 #{[9 0] [9 1] [9 2]}} ; right-most column
          input  (gui-grid/setup input (bounds/bounding-cube [50 50] 100) 10 10)
          result (update-all input)]
      (should= #{[8 1]
                 [9 1]} (:game result))
      (should= [[[80 10] [90 20]]
                [[90 10] [100 20]]] (:live-cells (:grid result)))))

  (it "filters cells with y values that are too high for the grid"
    (let [input  {:frames-per-evolution 1
                  :frame-count          0
                  :player               :playing
                  :game                 #{[0 9] [1 9] [2 9]}} ; bottom-most row
          input  (gui-grid/setup input (bounds/bounding-cube [50 50] 100) 10 10)
          result (update-all input)]
      (should= #{[1 8]
                 [1 9]} (:game result))
      (should= [[[10 90] [20 100]]
                [[10 80] [20 90]]] (:live-cells (:grid result)))))

  )

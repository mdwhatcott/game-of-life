(ns life.rules-spec
  (:require [speclj.core :refer :all]
            [life.rules :refer :all]
            [clojure.set :as set]))

(def home [0 0])
(def neighbors8 (neighbors-of home))
(defn with-cell [cell neighbors] (conj (set neighbors) cell))

(describe "John Conway's Game of Life"
  (it "sees all 8 neighbors around a cell"
    (->> (neighbors-of home) (should= #{[-1 -1] [0 -1] [1 -1]
                                        [-1 0] #_home [1 0]
                                        [-1 1] [0 1] [1 1]})))
  (for [n (range 9)]
    (it (format "counts %d live neighbors around a cell" n)
      (let [non-neighbor [2 2]
            alive        (with-cell non-neighbor (take n neighbors8))]
        (should= n (count-live-neighbors home alive)))))

  (for [n [2 3]]
    (it (format "retains a live cell with %d live neighbors" n)
      (let [alive    (with-cell home (take n neighbors8))
            retained (update-cell home alive)]
        (should= home retained))))

  (for [n [0 1 4 5 6 7 8]]
    (it (format "discards a live cell with %d live neighbors" n)
      (let [alive     (with-cell home (take n neighbors8))
            discarded (update-cell home alive)]
        (should= nil discarded))))

  (it "revives a dead cell with 3 live neighbors"
    (let [alive   (set (take 3 neighbors8))
          revived (update-cell home alive)]
      (should= home revived)))

  (for [n [0 1 2 4 5 6 7 8]]
    (it (format "ignores dead cells with %d live neighbors" n)
      (let [alive   (set (take n neighbors8))
            ignored (update-cell home alive)]
        (should= nil ignored))))

  (it "sees all cells to check"
    (let [live  #{[0 0]
                  [1 1]}
          cells (cells-in-play live)]
      (should= cells (set/union
                       (set (block9 0 0))
                       (set (block9 1 1))))))

  (it "evolves from an initial state"
    (let [oscillator [#{[1 1] [1 2] [1 3]}
                      #{[0 2] [1 2] [2 2]}]
          expected   (take 8 (cycle oscillator))
          actual     (take 8 (iterate evolve (first oscillator)))]
      (should= expected actual)))

  )

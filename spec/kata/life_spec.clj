(ns kata.life-spec
  (:require [speclj.core :refer :all]
            [kata.life :refer :all]))

(def cell [0 0])
(defn take-neighbors [n] (->> cell neighbors-of shuffle (take n) set))

(describe "Conway's Game of Life"
  (it "has awareness of the neighbors of a cell"
    (should= #{[-1 -1] [0 -1] [1 -1]
               [-1 0] #_cell [1 0]
               [-1 1] [0 1] [1 1]} (take-neighbors 8)))

  (for [n (range 9)]
    (it (format "has awareness of the %d active neighbors of a cell" n)
      (let [grid (conj (take-neighbors n) [42 43])]
        (should= n (count-active-neighbors cell grid)))))

  (for [n [0 1 #_2 #_3 4 5 6 7 8]]
    (it (format "discards active cells with %d active neighbors" n)
      (let [grid (conj (take-neighbors n) cell)]
        (should= nil (update-cell cell grid)))))

  (for [n [2 3]]
    (it (format "retains active cells with %d active neighbors" n)
      (let [grid (conj (take-neighbors n) cell)]
        (should= cell (update-cell cell grid)))))

  (it "revives inactive cells with 3 active neighbors"
    (let [grid (take-neighbors 3)]
      (should= cell (update-cell cell grid))))

  (for [n [0 1 2 #_3 4 5 6 7 8]]
    (it (format "ignores inactive cells with %d active neighbors" n)
      (let [grid (take-neighbors n)]
        (should= nil (update-cell cell grid)))))

  (it "evolves an entire grid of cells through multiple iterations"
    (let [oscillator [#{[0 1] [1 1] [2 1]}
                      #{[1 0] [1 1] [1 2]}]]
      (should= (take 8 (cycle oscillator))
               (take 8 (iterate evolve (first oscillator)))))))

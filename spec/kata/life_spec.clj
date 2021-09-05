(ns kata.life-spec
  (:require [speclj.core :refer :all]
            [kata.life :refer :all]))

(def home [1 11])
(defn take-neighbors [n] (->> home neighbors-of shuffle (take n) set))

(describe "Conway's Game of Life"
  (it "has awareness of the potential active neighbors of a cell"
    (should= #{[0 10] [0 11] [0 12]
               [1 10] #_home [1 12]
               [2 10] [2 11] [2 12]} (take-neighbors 8)))

  (for [n (range 9)]
    (it (format "has awareness of %d active neighbors of a cell" n)
      (let [grid (-> (take-neighbors n) (conj [42 43]))]
        (should= n (count-active-neighbors home grid)))))

  (for [n [0 1 #_2 #_3 4 5 6 7 8]]
    (it (format "discards active cells with %d active neighbors" n)
      (let [grid (-> (take-neighbors n) (conj home))]
        (should= nil (update-cell home grid)))))

  (for [n [2 3]]
    (it (format "retains active cells with %d active neighbors" n)
      (let [grid (-> (take-neighbors n) (conj home))]
        (should= home (update-cell home grid)))))

  (it "revive inactive cells with exactly 3 active neighbors"
    (let [grid (take-neighbors 3)]
      (should= home (update-cell home grid))))

  (for [n [0 1 2 #_3 4 5 6 7 8]]
    (it (format "ignore inactive cells with %d active neighbors" n)
      (let [grid (take-neighbors n)]
        (should= nil (update-cell home grid)))))

  (it "evolves an entire grid of cells"
    (let [oscillator [#{[0 1] [1 1] [2 1]}
                      #{[1 0] [1 1] [1 2]}]]
      (should= (take 8 (cycle oscillator))
               (take 8 (iterate evolve (first oscillator)))))))
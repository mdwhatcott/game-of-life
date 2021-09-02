(ns kata.life-spec
  (:require [speclj.core :refer :all]
            [kata.life :refer :all]))

(def home [1 11])
(def neighbors (neighbors-of home))

(describe "The Game of Life Kata"
  (it "has awareness of potential active neighbor cells"
    (should= [[0 10] [1 10] [2 10]
              [0 11] #_home [2 11]
              [0 12] [1 12] [2 12]] neighbors))

  (for [n (range 9)]
    (it (format "has awareness of exactly %d active neighbor cells" n)
      (let [grid (set (take n neighbors))
            grid (conj grid [42 42])]
        (should= n (count-active-neighbors home grid)))))

  (for [n [0 1 #_"not 2 or 3" 4 5 6 7 8]]
    (it (format "discards active cells with %d active neighbors" n)
      (let [grid (set (take n neighbors))
            grid (conj grid home)]
        (should= nil (update-cell home grid)))))

  (for [n [2 3]]
    (it (format "retains active cells with %d active neighbors" n)
      (let [grid (set (take n neighbors))
            grid (conj grid home)]
        (should= home (update-cell home grid)))))

  (it "revives inactive cells with exactly 3 active neighbors"
    (let [grid (set (take 3 neighbors))]
      (should= home (update-cell home grid))))

  (for [n [0 1 2 #_"not 3" 4 5 6 7 8]]
    (it (format "ignores inactive cells with %d active neighbors" n)
      (let [grid (set (take n neighbors))]
        (should= nil (update-cell home grid)))))

  (it "evolves an entire grid of cells through multiple iterations"
    (let [oscillator [#{[0 1] [1 1] [2 1]}
                      #{[1 0] [1 1] [1 2]}]]
      (should= (take 8 (cycle oscillator))
               (take 8 (iterate evolve (first oscillator)))))))
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

  )

(def oscillator-test
  ["simple oscillator"

   (str "---"
        "xxx"
        "---")

   (str "-x-"
        "-x-"
        "-x-")

   (str "---"
        "xxx"
        "---")

   (str "-x-"
        "-x-"
        "-x-")])

(def glider-test
  ["glider gun"

   (str "-x--"
        "--x-"
        "xxx-"
        "----")

   (str "----"
        "x-x-"
        "-xx-"
        "-x--")

   (str "----"
        "--x-"
        "x-x-"
        "-xx-")

   (str "----"
        "-x--"
        "--xx"
        "-xx-")

   (str "----"
        "--x-"
        "---x"
        "-xxx")])

(defn parse-grid [raw]
  (let [width (int (Math/sqrt (count raw)))]
    (set (for [n (range (count raw))
               :let [x (int (mod n width))
                     y (int (/ n width))]
               :when (= (nth raw n) \x)]
           [x y]))))

(describe "Grid Evolution"
  (for [[title & pattern] [oscillator-test glider-test]]
    (it (str "iterates to create: " title)
      (let [expected (map parse-grid pattern)
            input    (first expected)
            actual   (take (count pattern) (iterate evolve input))]
        (should= expected actual)))))

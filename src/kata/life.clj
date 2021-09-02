(ns kata.life
  (:require [clojure.set :as set]))

(defn neighbors-of [[x y]]
  (for [Y (range (dec y) (inc (inc y)))
        X (range (dec x) (inc (inc x)))
        :when (not= [x y] [X Y])] [X Y]))

(defn count-active-neighbors [cell grid]
  (count (filter grid (set (neighbors-of cell)))))

(defn update-cell [cell grid]
  (let [n (count-active-neighbors cell grid)]
    (cond (= n 3) cell
          (not= n 2) nil
          (grid cell) cell)))

(defn evolve [grid]
  (let [neighbors (mapcat neighbors-of grid)
        in-play   (set/union grid neighbors)
        updated   (map #(update-cell % grid) in-play)]
    (set (remove nil? updated))))
(ns life.rules
  (:require [clojure.set :as set]))

(defn block9 [x y]
  (for [Y (range (dec y) (+ 2 y))
        X (range (dec x) (+ 2 x))] [X Y]))

(defn neighbors-of [[x y]]
  (disj (set (block9 x y)) [x y]))

(defn count-live-neighbors [cell grid]
  (count (filter grid (neighbors-of cell))))

(defn update-cell [cell alive]
  (let [n (count-live-neighbors cell alive)]
    (cond (= 3 n) cell
          (not= 2 n) nil
          (alive cell) cell)))

(defn evolve [grid]
  (let [neighbors (mapcat neighbors-of grid)
        updates   (map #(update-cell % grid) neighbors)]
    (set (remove nil? updates))))

(ns gui.controller
  (:require
    [gui.bounds :as bounds]))

(def initial-play-button
  {:box       (bounds/bounding-box [250 550] 500 100)
   :hovering? false})

(defn setup [state]
  (-> state
      (assoc :player :stopped)
      (assoc :play-button initial-play-button)))

(defn update_ [state]
  (let [{:keys [x y clicked?]} (:mouse state)
        box (get-in state [:play-button :box])]
    (cond (= :playing (:player state)) state
          (not (bounds/bounded? [x y] box)) state
          (not clicked?) (assoc-in state [:play-button :hovering?] true)
          :else (-> state
                    (assoc :player :playing)
                    (assoc-in [:play-button :hovering?] false)))))

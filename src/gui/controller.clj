(ns gui.controller
  (:require
    [gui.bounds :as bounds]))

(defn setup [state box]
  (-> state
      (assoc :player :stopped)
      (assoc :play-button {:box box :hovering? false})))

(defn update_ [state]
  (if (= :playing (:player state))
    state
    (let [{:keys [x y clicked?]} (:mouse state)
          box      (get-in state [:play-button :box])
          bounded? (bounds/bounded? [x y] box)]
      (cond (and clicked? bounded?)
            (-> state
                (assoc :player :playing)
                (assoc-in [:play-button :hovering?] false))

            :else
            (assoc-in state [:play-button :hovering?] bounded?)))))

(ns gui.mouse)

(defn setup [state]
  (assoc state :mouse {:ready-to-click? true
                       :clicked?        false
                       :x               nil
                       :y               nil}))

(defn update_ [state pressed? mouse-x mouse-y]
  (let [ready? (get-in state [:mouse :ready-to-click?])]
    (-> state
        (assoc-in [:mouse :x] mouse-x)
        (assoc-in [:mouse :y] mouse-y)
        (assoc-in [:mouse :clicked?] (boolean (and ready? pressed?)))
        (assoc-in [:mouse :ready-to-click?] (not pressed?)))))

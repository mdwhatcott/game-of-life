(ns gui.updates)

(defn setup-frame-count [state]
  (assoc state :frame-count 0))

(defn update-frame-count [state]
  (update state :frame-count inc))

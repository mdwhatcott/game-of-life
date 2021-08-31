(ns gui.frame_count)

(defn setup [state]
  (assoc state :frame-count 0))

(defn update_ [state]
  (update state :frame-count inc))

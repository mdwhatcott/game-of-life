(ns gui.frame-count)

(def frames-per-evolution 5)

(defn setup [state]
  (assoc state :frame-count 0
               :frames-per-evolution frames-per-evolution))

(defn update_ [state]
  (update state :frame-count inc))

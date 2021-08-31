(ns gui.frame-count)

(defn setup [state frames-per-evolution]
  (assoc state :frame-count 0
               :frames-per-evolution frames-per-evolution))

(defn update_ [state]
  (update state :frame-count inc))

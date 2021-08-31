(ns gui.frame_count_spec
  (:require
    [speclj.core :refer :all]
    [gui.frame-count :refer :all]))

(describe "Updating the Frame Count"
  (it "starts the frame-count at zero"
    (let [state (setup {} 42)]
      (should= 0 (:frame-count state))
      (should= 42 (:frames-per-evolution state))))

  (it "increments the frame count on the state with each invocation"
    (let [input  (setup {} 1)
          result (update_ input)]
      (should= 1 (:frame-count result))))
  )

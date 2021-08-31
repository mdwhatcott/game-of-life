(ns gui.updates-spec
  (:require
    [speclj.core :refer :all]
    [gui.updates :refer :all]))

(describe "Updating the Frame Count"
  (it "starts the frame-count at zero"
    (let [state (setup-frame-count {})]
      (should= 0 (:frame-count state))))

  (it "increments the frame count on the state with each invocation"
    (let [input  (setup-frame-count {})
          result (update-frame-count input)]
      (should= 1 (:frame-count result))))
  )

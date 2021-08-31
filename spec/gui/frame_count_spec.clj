(ns gui.frame_count_spec
  (:require
    [speclj.core :refer :all]
    [gui.frame_count :refer :all]))

(describe "Updating the Frame Count"
  (it "starts the frame-count at zero"
    (let [state (setup {})]
      (should= 0 (:frame-count state))))

  (it "increments the frame count on the state with each invocation"
    (let [input  (setup {})
          result (update_ input)]
      (should= 1 (:frame-count result))))
  )

#_(describe "Updating the 'Playing' State"
  (it "starts in the stopped state"
    (let [state (setup-)])))
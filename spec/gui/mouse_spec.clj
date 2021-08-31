(ns gui.mouse-spec
  (:require
    [speclj.core :refer :all]
    [gui.mouse :refer :all]))

(describe "Mouse Input"

  (context "in the beginning"
    (it "is ready to receive mouse clicks in the very beginning"
      (let [state (setup {})
            {:keys [ready-to-click? clicked?]} (:mouse state)]
        (should= true ready-to-click?)
        (should= false clicked?)))

    (it "doesn't know where the mouse is in the very beginning"
      (let [state (setup {})
            {:keys [x y]} (:mouse state)]
        (should= nil x)
        (should= nil y)))
    )

  (context "after updates"
    (it "always knows where the mouse is"
      (let [state   (setup {})
            updated (update_ state false 42 43)
            {:keys [x y]} (:mouse updated)]
        (should= 42 x)
        (should= 43 y)))

    (it "recognizes an initial click"
      (let [state   (setup {})
            updated (update_ state true 0 0)]
        (should= true (get-in updated [:mouse :clicked?]))))

    (it "only recognizes a click for a single frame/update"
      (let [state  (setup {})
            frame1 (update_ state true 0 0)
            frame2 (update_ frame1 true 0 0)]
        (should= false (get-in frame2 [:mouse :clicked?]))))

    (it "recognizes a subsequent click only after the button has been released"
      (let [state           (setup {})
            initial-click   (update_ state true 0 0)
            still-holding   (update_ initial-click true 0 0)
            button-released (update_ still-holding false 0 0)
            next-click      (update_ button-released true 0 0)]
        (should= true (get-in next-click [:mouse :clicked?]))))
    )
  )
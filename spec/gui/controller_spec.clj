(ns gui.controller-spec
  (:require
    [speclj.core :refer :all]
    [gui.controller :refer :all]))

(describe "Updating the Controller"
  (it "starts in the stopped state"
    (let [state (setup {:mouse {:clicked? false :x 0 :y 0}})]
      (should= :stopped (:player state))
      (should= {:box       [[0 500] [500 600]]
                :hovering? false} (:play-button state))))

  (it "transitions from stopped to playing with a mouse click over the button"
    (let [initial  (setup {:mouse {:clicked? false :x 250 :y 550}})
          hovering (update_ initial)
          hovering (assoc-in hovering [:mouse :clicked?] true)
          clicked  (update_ hovering)]
      (should= :playing (:player clicked))
      (should= false (get-in clicked [:play-button :hovering?]))))

  (it "makes no transition from stopped to playing if mouse click isn't over button"
    (let [input  (setup {:mouse {:clicked? true :x 0 :y 0}})
          result (update_ input)]
      (should= :stopped (:player result))
      (should= false (get-in result [:play-button :hovering?]))))

  (it "makes no transition if the mouse is merely hovering"
    (let [input  (setup {:mouse {:clicked? false :x 250 :y 550}})
          result (update_ input)]
      (should= :stopped (:player result))
      (should= true (get-in result [:play-button :hovering?]))))

  (it "makes no changes having already transitions to playing"
    (let [input   (setup {:mouse {:clicked? true :x 250 :y 550}})
          playing (update_ input)
          result  (update_ playing)]
      (should= :playing (:player result))
      (should= false (get-in result [:play-button :hovering?]))))
  )

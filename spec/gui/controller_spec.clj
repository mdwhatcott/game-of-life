(ns gui.controller-spec
  (:require
    [speclj.core :refer :all]
    [gui.bounds :as bounds]
    [gui.controller :refer :all]))

(defn pointing-at [x y]
  {:clicked? false :x x :y y})

(defn click-at [x y]
  {:clicked? true :x x :y y})

(def initial-mouse (pointing-at 0 0))

(def play-button-box (bounds/bounding-cube [2 2] 4))

(describe "Updating the Controller"
  (it "starts in the stopped state"
    (let [state (setup {:mouse initial-mouse} play-button-box)]
      (should= :stopped (:player state))
      (should= {:bounds    [[0 0] [4 4]]
                :hovering? false} (:play-button state))))

  (for [x (range 1 4) y (range 1 4)]
    (it (str "transitions from stopped to playing "
             "with a mouse click over the button "
             (format "(at [%d %d])" x y))
      (let [initial (setup {:mouse (click-at x y)} play-button-box)
            clicked (update_ initial)]
        (should= :playing (:player clicked)))))

  (it (str "makes no transition from stopped to playing "
           "if mouse click isn't over button")
    (let [input  (setup {:mouse (click-at 0 0)} play-button-box)
          result (update_ input)]
      (should= :stopped (:player result))
      (should= false (get-in result [:play-button :hovering?]))))

  (for [x (range 1 4) y (range 1 4)]
    (it (str "makes no transition if the mouse is "
             "merely hovering over the button "
             (format "(at [%d %d])" x y))
      (let [input  (setup {:mouse (pointing-at x y)} play-button-box)
            result (update_ input)]
        (should= :stopped (:player result))
        (should= true (get-in result [:play-button :hovering?])))))

  (it "makes no changes having already transitions to playing"
    (let [input   (setup {:mouse (click-at 2 2)} play-button-box)
          playing (update_ input)
          result  (update_ playing)]
      (should= :playing (:player result))
      (should= false (get-in result [:play-button :hovering?]))))

  (it "erases hovering marker when mouse moves away"
    (let [input           (setup {:mouse (pointing-at 2 2)} play-button-box)
          hovering        (update_ input)
          move-mouse-away (assoc hovering :mouse (pointing-at 42 42))
          result          (update_ move-mouse-away)]
      (should= false (get-in result [:play-button :hovering?]))))
  )

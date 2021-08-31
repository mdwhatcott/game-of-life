(ns gui.main-spec
  (:require
    [speclj.core :refer :all]
    [gui.main :refer :all]))

(describe "Game Setup"
  (it "initializes all the things"
    (let [initial (setup-root)]
      (should= {:mouse                {:ready-to-click? true
                                       :clicked?        false
                                       :x               nil
                                       :y               nil}
                :frame-count          0
                :frames-per-evolution 10
                :player               :stopped
                :play-button          {:bounds    [[0 500] [500 600]]
                                       :hovering? false}
                :grid                 {:bounds         [[0 0] [500 500]]
                                       :cell-row-count 50
                                       :live-cells     #{}}
                } initial))))

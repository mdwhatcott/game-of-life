(ns life.glider-spec
  (:require
    [speclj.core :refer :all]
    [life.rules :refer :all]))

(def glider-test
  ["glider gun"

   (str " *  "
        "  * "
        "*** "
        "    ")

   (str "    "
        "* * "
        " ** "
        " *  ")

   (str "    "
        "  * "
        "* * "
        " ** ")

   (str "    "
        " *  "
        "  **"
        " ** ")

   (str "    "
        "  * "
        "   *"
        " ***")])

(defn parse-grid [raw]
  (let [width (int (Math/sqrt (count raw)))]
    (set (for [n (range (count raw))
               :let [x (int (mod n width))
                     y (int (/ n width))]
               :when (= (nth raw n) \*)]
           [x y]))))

; TODO: activate
#_(describe "Grid Evolution"
  (for [[title & pattern] [glider-test]]
    (it (str "iterates to create: " title)
      (let [expected (map parse-grid pattern)
            input    (first expected)
            actual   (take (count pattern) (iterate evolve input))]
        (should= expected actual)))))

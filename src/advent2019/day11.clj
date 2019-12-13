(ns advent2019.day11
  (:require [advent2019.common :as common]
            [advent2019.intcode :as intcode]
            [clojure.string :as str]))

(defn run-robot [computer in]
  (loop [computer computer grid {} [x y] [0 0] direction "north" in in]
    (if (:halted? computer)
      grid
      (let [input-state (intcode/run-intcode (assoc computer :out []) in)
            output-state (intcode/run-intcode input-state in)
            next-comp-state (intcode/run-intcode output-state in)
            out             (:out next-comp-state)
            next-grid       (assoc grid [x y] (first out))
            next-direction  (case direction
                             "north" (if (= 0 (second out)) "west" "east")
                             "west" (if (= 0 (second out)) "south" "north")
                             "south" (if (= 0 (second out)) "east" "west")
                             "east" (if (= 0 (second out)) "north" "south"))
            next-coord      (case next-direction
                              "north" (vector x (dec y))
                              "west" (vector (dec x) y)
                              "south" (vector x (inc y))
                              "east" (vector (inc x) y))
            next-in         (if (contains? grid next-coord) (grid next-coord) 0)]
        (recur next-comp-state next-grid next-coord next-direction next-in)))))

(defn print-grid [grid]
  (let [[[from-x to-x] [from-y to-y]]
        (mapv (fn [f] (mapv #(apply % (map f (keys grid))) [min (comp inc max)])) [first second])]
    (->> (for [x (range from-x to-x)
               y (range from-y to-y)]
           (if (= 1 (grid [x y])) \# \.))
         (partition to-y)
         (apply mapv vector)
         (mapv #(apply str %)))))

(defn day11 [file]
  (let [computer (->> file
                      common/get-content
                      intcode/create-computer)]
    [(count      (run-robot computer 0))
     (print-grid (run-robot computer 1))]))

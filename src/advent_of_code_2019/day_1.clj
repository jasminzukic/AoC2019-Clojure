(ns advent-of-code-2019.day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn day-1 []
  (let [xs (->> "inputs/day-1-input.txt" io/resource slurp str/split-lines (map #(Integer/parseInt %)))
        fuel (fn [x] (- (quot x 3) 2))
        fuels (fn [x] (->> x fuel (iterate fuel) (take-while #(<= 0 %))))
        day1a (apply + (map fuel xs))
        day1b (apply + (mapcat fuels xs))]
    [day1a day1b]))

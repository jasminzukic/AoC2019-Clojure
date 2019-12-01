(ns advent2019.day1
  (:require [advent2019.common :as common]
            [clojure.string :as str]))

(defn day1 [file]
  (let [xs (map #(Integer/parseInt %) (common/get-lines file))
        fuel     #(- (quot % 3) 2)
        fuel-rec (fn [x] (->> x fuel (iterate fuel) (take-while #(<= 0 %))))
        day1a (apply + (map fuel xs))
        day1b (apply + (mapcat fuel-rec xs))]
    [day1a day1b]))

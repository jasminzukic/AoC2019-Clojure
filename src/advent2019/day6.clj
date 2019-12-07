(ns advent2019.day6
  (:require [advent2019.common :as common]
            [clojure.string :as str]
            [clojure.set :refer [union difference]]))

(defn day6a [tree node level]
  (let [children (tree node)]
    (if children
      (+ level (apply + (map #(day6a tree % (inc level)) children)))
      level)))

(defn chain-to-center [links node]
  (let [next (links node)]
    (if (= "COM" next)
      #{}
      (conj (chain-to-center links next) next))))

(defn day6b [links]
  (let [you   (chain-to-center links "YOU")
        santa (chain-to-center links "SAN")]
    (count (union (difference santa you) (difference you santa)))))

(defn day6 [file]
  (let [xs (map #(str/split % #"\)") (common/get-lines file))
        tree (reduce (fn [acc [f s]] (if (contains? acc f)
                                       (update acc f #(conj % s))
                                       (assoc acc f (vector s)))) {} xs)
        links (into {} (map (fn [[f s]] [s f]) xs))]
    (println (day6a tree "COM" 0))
    (println (day6b links))))

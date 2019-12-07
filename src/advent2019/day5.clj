(ns advent2019.day5
  (:require [advent2019.common :as common]
            [clojure.string :as str]))

(defn day5a [xs in]
  (loop [xs xs i 0]
    (let [[m a b s & _] (drop i xs)
          [op m1 m2]    [(mod m 100) (mod (quot m 100) 10) (quot m 1000)]
          sum-or-mul    (fn [op] (assoc xs s (op (if (= 1 m1) a (xs a)) (if (= 1 m2) b (xs b)))))
          jump-if       (fn [p] (if (p 0 (if (= 1 m1) a (xs a))) (if (= 1 m2) b (xs b)) (+ i 3)))
          compare-with  (fn [p] (assoc xs s (if (p (if (= 1 m1) a (xs a)) (if (= 1 m2) b (xs b))) 1 0)))]
      (case op
        99 (first xs)
        1 (recur (sum-or-mul   +) (+ i 4))
        2 (recur (sum-or-mul   *) (+ i 4))
        3 (recur (assoc xs a in ) (+ i 2))
        4 (let [out (xs a)] (println out) (recur xs (+ i 2)))
        5 (recur xs               (jump-if not=))
        6 (recur xs               (jump-if =))
        7 (recur (compare-with <) (+ i 4))
        8 (recur (compare-with =) (+ i 4))
        ))))


(defn day5 [file]
  (let [xs (mapv #(Long/parseLong %) (str/split (common/get-content file) #","))]
    (day5a xs 1)
    (day5a xs 5)))

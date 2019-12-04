(ns advent2019.day3
  (:require [advent2019.common :as common]
            [clojure.string :as str]))

(defn parse-line [line]
  (mapv #(vector (first %) (Long/parseLong (apply str (rest %)))) (str/split line #",")))

(defn fill-grid-with-wire [wire]
  (first (reduce
           (fn [[grid [x y]] [dir len]]
             (let [[wr new-curr] (case dir
                                   \R [(for [i (range (+ y 1)   (+ y len 1))] [x i]) [x (+ y len)]]
                                   \U [(for [i (range x (- x len) -1)] [i y]) [(- x len) y]]
                                   \L [(for [i (range y (- y len) -1)] [x i]) [x (- y len)]]
                                   \D [(for [i (range (+ x 1)   (+ x len 1))] [i y]) [(+ x len) y]])]
               [(concat grid wr) new-curr]))
           [[] [0 0]] wire)))

(defn manhattan [[^int x1 ^int y1] [^int x2  ^int y2]]
  (+ (Math/abs (- x2 x1)) (Math/abs (- y2 y1))))

(defn part1 [cross]
  (apply min (map #(manhattan [0 0] %) cross)))

(defn part2 [wire1 wire2 cross]
  (apply min (map (fn [x] (+ 2 (count (take-while #(not= x %) wire1))
                               (count (take-while #(not= x %) wire2)))) cross)))

(defn day3 [file]
  (let [xs (mapv parse-line (common/get-lines file))
        wire1 (fill-grid-with-wire (first xs))
        wire2 (fill-grid-with-wire (second xs))
        cross (clojure.set/intersection (into #{} wire1) (into #{} wire2))]
    [(part1 cross)
     (part2 wire1 wire2 cross)]))

#_(defn fill-grid-with-wire2 [wire]
  (loop [grid grid wire wire [x y] [0 0]]
    (if (empty? wire)
      grid
      (let [[dir len] (first wire)
            [wr new-curr] (case dir
                            \R [(for [i (range (+ y 1)   (+ y len 1))] [x i]) [x (+ y len)]]
                            \U [(for [i (range (- x len)  x         )] [i y]) [(- x len) y]]
                            \L [(for [i (range (- y len)  y         )] [x i]) [x (- y len)]]
                            \D [(for [i (range (+ x 1)   (+ x len 1))] [i y]) [(+ x len) y]])]
        (recur (concat grid wr) (rest wire) new-curr)))))

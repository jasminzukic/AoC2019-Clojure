(ns advent2019.day10
  (:require [advent2019.common :as common]
            [clojure.string :as str]))

(defn greatest-common-divisor [n m]
  (if (= 0 m) (Math/abs n) (recur m (rem n m))))

(defn get-line-coordinates [[x1 y1] [x2 y2]]
  (let [h (- x2 x1)
        w (- y2 y1)
        d (greatest-common-divisor h w)
        x-to (if (< x1 x2) (inc x2) (dec x2))
        y-to (if (< y1 y2) (inc y2) (dec y2))]
    (if (= 0 d)
      []
      (rest (mapv #(vector %1 %2)
                  (if (= x1 x2) (repeat x1) (range x1 x-to (quot h d)))
                  (if (= y1 y2) (repeat y1) (range y1 y-to (quot w d))))))))

(defn any-asteroids-between? [[c1 _] [c2 _] rst]
  (not= 1 (count (filter rst (get-line-coordinates c1 c2)))))

(defn count-visible-asteroids [a rst]
  (count (remove #(any-asteroids-between? a % (dissoc rst %)) rst)))

(defn day10a [asteroids]
  (->> (mapv #(vector % (count-visible-asteroids % (dissoc asteroids %))) asteroids)
       (reduce (fn [acc x] (if (< (second acc) (second x)) x acc)))))

;; (defn find-border-coordinates [h w [x y]]
;;   (let [north-right (map #(vector % 0) (range x w))
;;         north-left (map #(vector % 0) (range 0 x))
;;         east-border (map #(vector (dec w) %) (range 1 (dec h)))
;;         south-border (map #(vector % (dec h)) (range (dec w) 0 -1))
;;         west-border (map #(vector 0 %) (range (dec h) 0 -1))]
;;     (concat north-right east-border south-border west-border north-left)))

(defn create-sequence [xss]
  (loop [xss xss acc []]
    (if (empty? xss)
      acc
      (recur (remove empty? (mapv rest xss)) (concat acc (mapv first xss))))))

(defn leave-longest [xss]
  (loop [[xs & xss] xss s #{} yss [[]]]
    (if (empty? xss);;jer ima već jedan prazni na kraju
      yss
      (if (some s xs)
        (recur xss s yss)
        (recur xss (clojure.set/union s (into #{} xs)) (conj yss xs))))))

(defn calc-angle [source target]
  (- Math/PI (apply #(Math/atan2 %1 %2) ^:double (map (comp double -) target source)))
  )

(defn add-angle [center xss]
  (mapv #(vector % (calc-angle center (first %))) xss)
  )

(defn day10b [center asteroids]
  (let [lines (->> (mapv first asteroids)
                   (mapv #(filter asteroids (get-line-coordinates center %)))
                   (sort-by count)
                   reverse
                   leave-longest
                   rest
                   (add-angle center)
                   (sort-by second)
                   (map first)
                   create-sequence
                   )

        ;; sequence (create-sequence lines)
        ]
    (nth lines 199)
    ))

(defn day10 [file]
  (let [xs (common/get-lines file)
        height (count xs)
        width (count (first xs))
        is (for [i (range 0 width) j (range 0 height)] [i j])
        grid (zipmap is (apply concat (apply mapv vector xs)))
        asteroids (into {} (filter #(= \# (second %)) grid))
        part1 (day10a asteroids)
        center (first (first part1))
        ;; border (find-border-coordinates height width center)
        ]
    [ part1
     (day10b center asteroids)]
    ))

(ns advent2019.day12
  (:require [advent2019.common :as common]
            [clojure.string :as str]))

(defn calc-gravity [n m]
  (cond
    (< n m)  1
    (= n m)  0
    :else   -1))

(defn f [x xs v]
  (+ v (apply + (mapv #(calc-gravity x %) xs))))

(defn calc-velocities [xs vx]
  (mapv (fn [i] (f (nth xs i) (filter #(not= i (.indexOf xs %)) xs) (nth vx i))) (range 0 4)))

(defn step [planets]
  (let [velocities (mapv #(calc-velocities (%1 planets) (%2 planets)) [:xs :ys :zs] [:vx :vy :vz])
        positions  (mapv #(mapv + (%1 planets) %2) [:xs :ys :zs] velocities)]
    (->> (concat positions velocities)
         (zipmap [:xs :ys :zs :vx :vy :vz])
         (reduce (fn [acc [k v]] (assoc acc k v)) planets))))

(defn energy [vs]
  (let [planets (apply mapv vector (vals vs))
        energies (mapv (fn [p] (mapv #(Math/abs %) p)) planets)]
    (apply + (mapv #(* (apply + (take 3 %)) (apply + (drop 3 %))) energies))))

(defn day12a [vs]
  (loop [vs vs time 0]
    (if (= 1000 time)
      (energy vs)
        (recur (step vs) (inc time)))))

(defn gcd [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm [a b] (/ (* a b) (gcd a b)))

(defn lcmv [& v] (reduce lcm v))

(defn lowest-common-multiple [& args]
  (loop [step (apply max args) i step args args]
    (if (every? zero? (map #(rem i %) args))
      i
      (recur step (+ i step) args))))

(defn day12b [original-vs]
  (loop [vs original-vs time 0 cx nil cy nil cz nil]
    (if (every? some? (vector cx cy cz))
      ;; (if (some? cx)
      (lcmv cx cy cz)
      (let [ vs-          (step vs)
            [cx- cy- cz-] (mapv #(if (and (nil? %1)
                                          (= (%2 original-vs) (%2 vs-))
                                          (= (%3 original-vs) (%3 vs-))) (inc time) %1)
                                [cx cy cz] [:xs :ys :zs] [:vx :vy :vz])]
        (recur vs- (inc time) cx- cy- cz-)))))

(defn parse-number [s]
  (Long/parseLong (str/replace s #"[<a-z= >]" "")))

(defn create-planets [[xs ys zs]]
  {:xs xs
   :ys ys
   :zs zs
   :vx [0 0 0 0]
   :vy [0 0 0 0]
   :vz [0 0 0 0]})

(defn day12 [file]
  (let [planets (->> file
                     common/get-lines
                     (mapv #(->> (str/split % #",")
                                 (mapv parse-number)
                                 ))
                     (apply mapv vector)
                     create-planets)]
    [(day12a planets)
     (day12b planets)]))

(ns advent2019.day7
  (:require [advent2019.common :as common]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [permutations]]))

(defn parse-line [s]
  (mapv #(Long/parseLong %) (str/split s #",")))

(defn run-intcode [amps phase-setting part1?]
  (loop [amps amps phase 0 out 0]
    (let [[m a b s & _] (drop (:pc (amps phase)) (:intcode (amps phase)))
          [op m1 m2]    [(mod m 100) (mod (quot m 100) 10) (quot m 1000)]

          sum-or-mul    (fn [op] (assoc-in amps [phase :intcode s] (op (if (= 1 m1) a (get-in amps [phase :intcode a]))
                                                                       (if (= 1 m2) b (get-in amps [phase :intcode b])))))
          jump-if       (fn [p] (if (p 0 (if (= 1 m1) a (get-in amps [phase :intcode a])))
                                  (assoc-in amps [phase :pc] (if (= 1 m2) b (get-in amps [phase :intcode b])))
                                  (update-in amps [phase :pc] #(+ % 3))))
          compare-with  (fn [p] (assoc-in amps [phase :intcode s] (if (p (if (= 1 m1) a (get-in amps [phase :intcode a]))
                                                                         (if (= 1 m2) b (get-in amps [phase :intcode b])))
                                                                    1 0)))
          set-initialized (fn [amp] (assoc-in amp [phase :initialized?] true))
          update-pc     (fn [i amp] (update-in amp [phase :pc] #(+ % i)))]

      (case op
        99 out
        1 (recur (update-pc 4 (sum-or-mul   +)) phase out)
        2 (recur (update-pc 4 (sum-or-mul   *)) phase out)
        3 (recur (update-pc 2 (set-initialized (assoc-in amps [phase :intcode a] (if (get-in amps [phase :initialized?])
                                                                                   out
                                                                                   (phase-setting phase))))) phase out)
        4 (if (and part1? (= 4 phase))
            (get-in amps [phase :intcode a])
            (recur (update-pc 2 amps) (if (= 4 phase) 0 (inc phase)) (get-in amps [phase :intcode a])))
        5 (recur (jump-if not=) phase out)
        6 (recur (jump-if =) phase out)
        7 (recur (update-pc 4 (compare-with <)) phase out)
        8 (recur (update-pc 4 (compare-with =)) phase out)
        ))))


(defn amplifier [xs]
  {:intcode      xs
   :pc           0
   :initialized? false})

(defn run-amplifiers [xs phase-settings part1?]
  (->> (permutations phase-settings)
       (map #(run-intcode (zipmap (range 5) (repeat 5 (amplifier xs))) % part1?))
       (apply max)))

(defn day7a [xs]
  (run-amplifiers xs [0 1 2 3 4] true))

(defn day7b [xs]
  (run-amplifiers xs [5 6 7 8 9] false))

(defn day7 [file]
  (let [xs (parse-line (common/get-content file))]
    [(day7a xs)
     (day7b xs)]))

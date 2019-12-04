(ns advent2019.day4)

(def from 165432)
(def to 707912)

(defn digits [n] (rseq (mapv #(mod % 10) (take-while pos? (iterate #(quot % 10) n)))))

(defn valid? [xs p]
  (and (apply <= xs) (seq (filter #(p 2 %) (map count (vals (group-by identity xs)))))))

(defn day4 []
  (map #(reduce (fn [acc x] (+ acc (if (valid? (digits x) %) 1 0))) 0 (range from (+ 1 to))) [<= =]))

;; Alternative function representations

(defn digits->> [n]
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))
       rseq))

(defn valid?->> [xs p]
  (->> xs
       (group-by identity)
       vals
       (map count)
       (filter #(p 2 %))
       not-empty
       (and (apply <= xs))))

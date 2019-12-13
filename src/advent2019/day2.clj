(ns advent2019.day2
  (:require [advent2019.common :as common]
            [advent2019.intcode :as intcode]))

(defn day2a [computer a b]
  (let [c (assoc-in (assoc-in computer [:intcode 1] a) [:intcode 2] b)]
    (-> (intcode/run-intcode c nil)
        (get-in [:intcode 0]))))

(defn day2b [computer]
  (for [a (range 0 100) b (range 0 100) :when (= 19690720 (day2a computer a b))]
    (+ (* 100 a) b)))

(defn day2 [file]
  (let [computer (intcode/create-computer (common/get-content file))]
    [(day2a computer 12 2)
     (first (day2b computer))]))

;;Version before refactoring the working intcode computer

;; (:require [clojure.string :as str])

;; (defn day2a [xs]
;;   (loop [xs xs i 0]
;;     (let [[op a b s & _] (drop i xs)]
;;       (case op
;;         99 (first xs)
;;         1 (recur (assoc xs s (+ (xs a) (xs b))) (+ i 4))
;;         2 (recur (assoc xs s (* (xs a) (xs b))) (+ i 4))))))

;; (defn day2b [xs]
;;   (for [a (range 0 100) b (range 0 100) :when (= 19690720 (day2a (assoc (assoc xs 1 a) 2 b)))]
;;     (+ (* 100 a) b)))

;; (defn day2 [file]
;;   (let [xs (mapv #(Integer/parseInt %) (str/split (common/get-content file) #","))]
;;     [(day2a (assoc (assoc xs 1 12) 2 2))
;;      (first (day2b xs))]))

(ns advent2019.day9
  (:require [advent2019.common :as common]
            [advent2019.intcode :as intcode]))

(defn day9a [computer in]
  (first (:out (intcode/run-intcode (intcode/run-intcode computer in) nil))))

(defn day9 [file]
  (let [computer (intcode/create-computer (common/get-content file))]
    [(day9a computer 1)
     (day9a computer 2)]))


;;Version before refactoring the working intcode computer

;; (defn parse-line [s]
;;   (->> (str/split s #",")
;;        (mapv #(Long/parseLong %))
;;        (zipmap (range))))

;; (defn get-var [m mode a]
;;   (let [var (case mode
;;               0 (get-in m [:intcode a])
;;               1 a
;;               2 (get-in m [:intcode (+ a (:rel m))]))]
;;     (if (some? var) var 0)))

;; (defn update-pc [offset m]
;;   (update m :pc #(+ % offset)))

;; (defn update-rel [mode a m]
;;   (let [offset (get-var m mode a)]
;;     (->> (update m :rel #(+ % offset))
;;          (update-pc 2))))

;; (defn sum-or-mul [op mode1 mode2 mode3 a b c m]
;;   (let [x (get-var m mode1 a)
;;         y (get-var m mode2 b)
;;         z (if (= 2 mode3) (+ (:rel m) c) c)]
;;     (->> (op x y)
;;          (assoc-in m [:intcode z])
;;          (update-pc 4))))

;; (defn input [mode a in m]
;;   (let [x (if (= 2 mode) (+ (:rel m) a) a)]
;;     (->> (assoc-in m [:intcode x] in)
;;          (update-pc 2))))

;; (defn output [mode a m]
;;   (let [x (get-var m mode a)]
;;     (println x)
;;     (update-pc 2 m)))

;; (defn jump-if [p mode1 mode2 a b m]
;;   (let [x (get-var m mode1 a)
;;         y (get-var m mode2 b)]
;;     (if (p 0 x)
;;       (assoc m :pc y)
;;       (update m :pc #(+ % 3)))))

;; (defn compare-with [p mode1 mode2 mode3 a b c m]
;;   (let [x (get-var m mode1 a)
;;         y (get-var m mode2 b)
;;         z (if (= 2 mode3) (+ (:rel m) c) c)]
;;     (->> (if (p x y) 1 0)
;;          (assoc-in m [:intcode z])
;;          (update-pc 4))))

;; (defn get-operation [intcode pc]
;;   (mapv #(intcode (+ % pc)) (range 4)))

;; (defn run-intcode [intcode in]
;;   (loop [m intcode]
;;     (let [[modes a b c & _]      (get-operation (:intcode m) (:pc m))
;;           [op mode1 mode2 mode3] [(mod modes 100) (mod (quot modes 100) 10) (mod (quot modes 1000) 10) (quot modes 10000)]]
;;       (case (long op)
;;         99 (first (:intcode m))
;;         1 (recur (sum-or-mul + mode1 mode2 mode3 a b c m))
;;         2 (recur (sum-or-mul * mode1 mode2 mode3 a b c m))
;;         3 (recur (input mode1 a in m))
;;         4 (recur (output mode1 a m))
;;         5 (recur (jump-if not= mode1 mode2 a b m))
;;         6 (recur (jump-if = mode1 mode2 a b m))
;;         7 (recur (compare-with < mode1 mode2 mode3 a b c m))
;;         8 (recur (compare-with = mode1 mode2 mode3 a b c m))
;;         9 (recur (update-rel mode1 a m))))))

;; (defn create-computer [intcode]
;;   {:intcode intcode
;;    :pc 0
;;    :rel 0})

;; (defn day9 [file]
;;   (let [intcode (parse-line (common/get-content file))
;;         computer (create-computer intcode)]
;;     [(run-intcode computer 1)
;;      (run-intcode computer 2)]))

(ns advent2019.day8
  (:require [advent2019.common :as common]
            [clojure.string :as str]))

(def height 6)
(def width 25)

(defn parse-line [s]
  (->> s
       (mapv #(Long/parseLong (str %)))
       (partition (* height width))))

(defn day8a [xss]
  (let [l (first (sort-by #(get % 0) (map frequencies xss)))]
    (* (get l 1) (get l 2))))

(defn transpose [m]
  (apply mapv vector m))

(defn top-pixel [layers]
  (let [top (first (drop-while #(= 2 %) layers))]
    (if (= 1 top) \# \space)))

(defn day8b [xss]
  (mapv str/join (partition width (mapv top-pixel (transpose xss)))))

(defn day8 [file]
  (let [xss (parse-line (common/get-content file))]
    (println (day8a xss))
    (map println (day8b xss))))

;;code-golf version

#_(defn day8 [file]
  (let [xss (partition 150 (mapv #(Long/parseLong (str %)) (common/get-content file)))]
    [(apply * (map (fn [n] (get (first (sort-by #(get % 0) (map frequencies xss))) n)) [1 2]))
     (map str/join (partition 25 (map #(if (= 1 (first (drop-while (partial = 2) %))) \# \_) (apply map vector xss))))]))

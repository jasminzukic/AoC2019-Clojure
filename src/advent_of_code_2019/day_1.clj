(ns advent-of-code-2019.day-1
  (:require [advent-of-code-2019.common.core :refer [words]]
            [advent-of-code-2019.common.core :as common]
            [clojure.java.io :as io]))

(defn part-1 []
  (let [s (slurp (io/resource "inputs/day-1-input.txt"))]
    (first (common/lines s))))

(part-1)

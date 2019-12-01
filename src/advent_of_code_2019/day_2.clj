(ns advent-of-code-2019.day-2
  (:require [advent-of-code-2019.common.core :refer [words]]
            [advent-of-code-2019.common.core :as common]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn day-2 []
  (let [xs (->> "inputs/day-1-input.txt" io/resource slurp str/split-lines)
        day-2a ()
        day-2b ()]
    [day-2a day-2b]))

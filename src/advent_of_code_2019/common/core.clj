(ns advent-of-code-2019.common.core
  (:require [clojure.string :as str]))

(defn words
  "Breaks a string up into a list of words, which were delimited by white space."
  [s]
  (str/split s #"\s+"))

(defn unwords
  "Inverse operation to words. It joins words with separating spaces."
  [xs]
  (str/join " " xs))

(defn lines
  "Breaks a string up into a list of strings at newline characters.
  The resulting strings do not contain newlines."
  [s]
  (str/split-lines s))

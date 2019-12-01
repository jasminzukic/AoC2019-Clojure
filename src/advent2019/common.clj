(ns advent2019.common
  (:require [clojure.java.io :as io]
            [clojure.string  :as str]))

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

(defn get-lines
  "Reads a file and splits it's content into a list of strings."
  [file-name]
  (->> file-name
       (str "inputs/")
       io/resource
       slurp
       str/split-lines))

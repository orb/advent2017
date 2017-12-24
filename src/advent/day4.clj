(ns advent.day4
  (:require [clojure.string :as string]))


(defn parse-phrase [phrase]
  (string/split (string/trim phrase) #"\W"))

(defn valid-passphrase? [phrase]
  (loop [words (parse-phrase phrase)
         seen #{}]
    (if (seq words)
      (let [word (first words)]
        (if (seen word)
          false
          (recur (rest words)
                 (conj seen word))))
      true)))

(defn part1 [phrases]
  (count (filter valid-passphrase? phrases)))


(defn normalize [word]
  (apply str (sort word)))

(defn valid-stronger-passphrase? [phrase]
  (loop [words (parse-phrase phrase)
         seen #{}]
    (if (seq words)
      (let [word (normalize (first words))]
        (if (seen word)
          false
          (recur (rest words)
                 (conj seen word))))
      true)))

(defn part2 [phrases]
  (count (filter valid-stronger-passphrase? phrases)))

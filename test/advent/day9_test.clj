(ns advent.day9-test
  (:require [advent.day9 :refer :all]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))

(def my-input (slurp (io/resource "day9-input.txt")))

(deftest test-part1
  (are [expected pattern] (= expected (part1 pattern))
    1 "{}"
    6 "{{{}}}"
    5 "{{},{}}"
    16 "{{{},{},{{}}}}"
    1 "{<a>,<a>,<a>,<a>}"
    9 "{{<ab>},{<ab>},{<ab>},{<ab>}}"
    9 "{{<!!>},{<!!>},{<!!>},{<!!>}}"
    3 "{{<a!>},{<a!>},{<a!>},{<ab>}}"
    14204 my-input))

(deftest test-part2
  (are [expected pattern] (= expected (part2 pattern))
    0 "{}"
    17 "<random characters>"
    3 "<<<<>"
    2 "<{!>}>"
    0 "<!!>"
    0 "<!!!>>"
    10 "<{o\"i!a,<{i<a>"
   6622 my-input))




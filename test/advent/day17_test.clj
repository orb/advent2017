(ns advent.day17-test
  (:require [advent.day17 :refer :all]
            [clojure.test :refer :all]))

(def my-input 355)

(deftest test-part1
  (is (= 638 (part1 3)))
  (is (= 1912 (part1 my-input))))


(deftest test-part2
  (is (= 21066990 (part2 my-input))))

(ns advent.day15-test
  (:require [advent.day15 :refer :all]
            [clojure.test :refer :all]))

(def my-input [873 583])

(deftest test-part1 []
  (is (= 588 (part1 65 8921)))
  (is (= 631 (apply part1 my-input))))

(deftest test-part2 []
  (is (= 309 (part2 65 8921)))
  (is (= 279 (apply part2 my-input))))




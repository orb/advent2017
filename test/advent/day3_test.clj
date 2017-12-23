(ns advent.day3-test
  (:require [advent.day3 :refer :all]
            [clojure.test :refer :all]))

(def my-input 277678)

(deftest test-xy-distance
  (is (= 0 (xy-distance [0 0])))
  (is (= 1 (xy-distance [0 1])))
  (is (= 5 (xy-distance [-5 0])))
  (is (= 10 (xy-distance [5 -5])))
  (is (= 10 (xy-distance [5 -5]))))

(deftest test-part1
  (is (= 0 (part1 1)))
  (is (= 3 (part1 12)))
  (is (= 2 (part1 23)))
  (is (= 31 (part1 1024)))
  (is (= 475 (part1 my-input))))

(deftest test-part2
  (is (= 2 (part2 1)))
  (is (- 4 (part2 2)))
  (is (= 23 (part2 15)))
  (is (= 747 (part2 701)))
  (is (= 1 (part2 my-input))))

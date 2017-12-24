(ns advent.day6-test
  (:require  [advent.day6 :refer :all]
             [clojure.test :refer :all]))

(def my-input
  [0 5 10 0 11 14 13 4 11 8 8 7 1 4 12 11])

(deftest test-largest-bank
  (is (= 0 (largest-bank [10])))
  (is (= 2 (largest-bank [10 20 30])))
  (is (= 1 (largest-bank [10 20 20])))
  (is (= 0 (largest-bank [30 20 30])))
  (is (= 2 (largest-bank [0 2 7 0])))
  (is (= 1 (largest-bank [2 4 1 2]))))

(deftest test-reallocate
  (is (= [2 4 1 2] (reallocate [0 2 7 0])))
  (is (= [3 1 2 3] (reallocate [2 4 1 2])))
  (is (= [0 2 3 4] (reallocate [3 1 2 3])))
  (is (= [1 3 4 1] (reallocate [0 2 3 4])))
  (is (= [2 4 1 2] (reallocate [1 3 4 1]))))


(deftest test-part1
  (is (= 5 (part1 [0 2 7 0])))
  (is (= 7864 (part1 my-input))))

(deftest test-part2
  (is (= 4 (part2 [0 2 7 0])))
  (is (= 1695 (part2 my-input))))

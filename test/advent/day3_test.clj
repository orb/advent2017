(ns advent.day3-test
  (:require [advent.day3 :refer :all]
            [clojure.test :refer :all]))

(deftest test-board-size
  (is (= 1 (board-size 1)))
  (is (= 2 (board-size 2)))
  (is (= 2 (board-size 4)))
  (is (= 3 (board-size 5)))
  (is (= 3 (board-size 9)))
  (is (= 4 (board-size 10)))
  (is (= 4 (board-size 16)))
  (is (= 5 (board-size 17))))

(deftest test-part1
  (is (= 0 (part1 1)))
  (is (= 12 (part1 3)))
  (is (= 2 (part1 23)))
  (is (= 1024 (part1 31))))



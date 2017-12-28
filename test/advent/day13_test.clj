(ns advent.day13-test
  (:require [advent.day13 :refer :all]
            [clojure.test :refer :all]))

(def example-input
  {0 3
   1 2
   4 4
   6 4})

(def my-input
  {0 5
   1 2
   2 3
   4 4
   6 6
   8 4
   10 8
   12 6
   14 6
   16 14
   18 6
   20 8
   22 8
   24 10
   26 8
   28 8
   30 10
   32 8
   34 12
   36 9
   38 20
   40 12
   42 12
   44 12
   46 12
   48 12
   50 12
   52 12
   54 12
   56 14
   58 14
   60 14
   62 20
   64 14
   66 14
   70 14
   72 14
   74 14
   76 14
   78 14
   80 12
   90 30
   92 17
   94 18})

(deftest test-distance
  (is (= 2 (distance 2)))
  (is (= 4 (distance 3)))
  (is (= 6 (distance 4))))

(deftest test-part1
  (is (= 24 (part1 example-input)))
  (is (= 2604 (part1 my-input))))

(deftest test-part2
  (is (= 10 (part2 example-input)))
  (is (= 3941460 (part2 my-input))))

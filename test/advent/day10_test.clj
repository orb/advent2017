(ns advent.day10-test
  (:require [advent.day10 :refer :all]
            [clojure.test :refer :all]))

(def my-input [70 66 255 2 48 0 54 48 80 141 244 254 160 108 1 41])
(def my-input-str "70,66,255,2,48,0,54,48,80,141,244,254,160,108,1,41")

(deftest test-part1
  (is (= 12 (part1 5 [3 4 1 5])))
  (is (= 7888 (part1 256 my-input))))

(deftest test-part2
  (is (= "a2582a3a0e66e6e86e3812dcb672a272" (part2 "")))
  (is (= "33efeb34ea91902bb2f59c9920caa6cd" (part2 "AoC 2017")))
  (is (= "3efbe78a8d82f29979031a4aa0b16a9d" (part2 "1,2,3")))
  (is (= "decdf7d377879877173b7f2fb131cf1b" (part2 my-input-str))))

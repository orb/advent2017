(ns advent.day14-test
  (:require [advent.day14 :refer :all]
            [clojure.test :refer :all]))

(def example-input "flqrgnkx")
(def my-input "wenycdww")

;; I'm starting to get lazy about tests. Some of these problems haven't been friendly about test cases
;; but this one definitely could have had more

(deftest test-part1
  (is (= 8108 (part1 example-input)))
  (is (= 8226 (part1 my-input))))

(deftest test-part2
  (is (= 1242 (part2 example-input)))
  (is (= 1128 (part2 my-input))))

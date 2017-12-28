(ns advent.day11-test
  (:require [advent.day11 :refer :all]
            [clojure.test :refer :all]
            [clojure.java.io :as io]))


(def my-input (slurp (io/resource "day11-input.txt")))

(def origin [0 0 0])

(deftest test-coords
  (is (not= origin (move origin :n)))
  (is (not= origin (move origin :s)))
  (is (not= origin (move origin :ne)))
  (is (not= origin (move origin :nw)))
  (is (not= origin (move origin :sw)))
  (is (not= origin (move origin :se)))

  (is (= origin
         (reduce move origin [:n :s])))
  (is (= origin
         (reduce move origin [:s :n])))
  (is (= origin
         (reduce move origin [:nw :se])))
  (is (= origin
         (reduce move origin [:sw :ne])))
  (is (= (move origin :n)
         (reduce move origin [:nw :ne])))
  (is (= (move origin :s)
         (reduce move origin [:sw :se])))
  (is (= (move origin :nw)
         (reduce move origin [:s :ne :ne :nw :nw :sw]))))


(deftest test-part1
  (is (= 3 (part1 "ne,ne,ne")))
  (is (= 0 (part1 "ne,ne,sw,sw")))
  (is (= 2 (part1 "ne,ne,s,s")))
  (is (= 3 (part1 "se,sw,se,sw,sw")))
  (is (= 698 (part1 my-input))))

(deftest test-part2
  (is (= 1435 (part2 my-input))))

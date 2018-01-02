(ns advent.day16-test
  (:require [advent.day16 :refer :all]
            [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def my-input (string/trim (slurp (io/resource "day16-input.txt"))))

(deftest test-part1
  (is (= "paedcbfghijklmno" (part1 "s1,x3/4,pe/b")))
  (is (= "cknmidebghlajpfo" (part1 my-input))))


(deftest test-part2
  (is (= "cbolhmkgfpenidaj" (part2 my-input))))

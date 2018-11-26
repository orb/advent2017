(ns advent.day18-test
  (:require [advent.day18 :refer :all]
            [clojure.test :refer :all]
            [clojure.java.io :as io]
            [instaparse.core :as instaparse]))

(def sample-input
  "set a 1
add a 2
mul a a
mod a 5
snd a
set a 0
rcv a
jgz a -1
set a 1
jgz a -2")


(def sample-part2
  "snd 1
snd 2
snd p
rcv a
rcv b
rcv c
rcv d")

(def my-input (slurp (io/resource "day18-input.txt")))

(deftest test-parse
  (is (not (instaparse/failure? (load-sound sample-input))))
  (is (not (instaparse/failure? (load-sound my-input))))
  (is (not (instaparse/failure? (load-send sample-part2))))
  (is (not (instaparse/failure? (load-send my-input)))))

(deftest test-part1
  (is (= 4 (part1 sample-input)))
  (is (= 1187 (part1 my-input))))

(deftest test-part2
  (is (= 3 (part2 sample-part2)))
  (is (= 5969 (part2 my-input))))

(ns advent.day8-test
  (:require [advent.day8 :refer :all]
            [clojure.java.io :as io]
            [clojure.test :refer :all]
            [instaparse.core :as instaparse]))

(def my-input (slurp (io/resource "day8-input.txt")))

(defn parse-command [source]
  (instaparse/transform program-transformation
                        (program-parser source :start :command)))
(defn parse-condition [source]
  (instaparse/transform program-transformation
                        (program-parser source :start :condition)))

(deftest test-register-get-set
  (is (= 0 (register-get {} :a)))
  (is (= 5 (register-get (register-set {} :a 5) :a)))
  (is (= 0 (register-get (register-set {} :b 5) :a)))
  (is (= 10 (register-get (-> {}
                              (register-set :a 10)
                              (register-set :b 20))
                          :a)))
  (is (= 20 (register-get (-> {}
                              (register-set :a 10)
                              (register-set :b 20))
                          :b))))


(deftest test-command
  (is (= {:q 1}
         (-> {}
             (eval-command (parse-command "q inc 1")))))
  (is (= {:q 25}
         (-> {}
             (register-set :q 15)
             (eval-command (parse-command "q inc 10")))))
  (is (= {:q 5}
         (-> {}
             (register-set :q 15)
             (eval-command (parse-command "q inc -10")))))
  (is (= {:q 42}
         (-> {}
             (register-set :q 57)
             (eval-command (parse-command "q dec 15")))))
  (is (= {:q 100}
         (-> {}
             (register-set :q 57)
             (eval-command (parse-command "q dec -43")))))
  (is (= {:bits 8}
         (-> {}
             (eval-command (parse-command "bits inc 2"))
             (eval-command (parse-command "bits dec -2"))
             (eval-command (parse-command "bits inc 2"))
             (eval-command (parse-command "bits dec -2"))))))

(deftest test-conditional
  (is (= false (-> {}
                   (test-condition (parse-condition "q == 1")))))
  (is (= true (-> {}
                  (test-condition (parse-condition "q == 0")))))
  (is (= true (-> {:q 5}
                  (test-condition (parse-condition "q != 0")))))
  (is (= true (-> {:q 5}
                  (test-condition (parse-condition "q == 5")))))


  (is (= false (-> {:q 10}
                   (test-condition (parse-condition "q < 10")))))
  (is (= false (-> {:q 10}
                   (test-condition (parse-condition "q > 10")))))
  (is (= true (-> {:q 10}
                   (test-condition (parse-condition "q <= 10")))))
  (is (= true (-> {:q 10}
                   (test-condition (parse-condition "q >= 10")))))

  (is (= false (-> {:q 10}
                   (test-condition (parse-condition "q < 10")))))
  (is (= false (-> {:q 10}
                   (test-condition (parse-condition "q > 10")))))
  (is (= true (-> {:q 10}
                  (test-condition (parse-condition "q <= 10")))))
  (is (= true (-> {:q 10}
                  (test-condition (parse-condition "q >= 10"))))))



(def test-program-1
  "b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10")

(deftest test-program
  (is (= {:a 1 :c -10}
         (run-program (load-program test-program-1)))))

(deftest test-part1
  (is (= 1 (part1 test-program-1)))
  (is (= 7296 (part1 my-input))))

(deftest test-part1
  (is (= 10 (part2 test-program-1)))
  (is (= 8186 (part2 my-input))))

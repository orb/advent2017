(ns advent.day17
  (:require [clojure.string :as string]))


(defn format-state [state position]
  (string/join " "
               (for [i (range (count state))
                     :let [v (nth state i)]]
                 (if (= i position)
                   (format "(%s)" v)
                   (format "%s" v)))))

(defn insert-at-pos [vs position new-value]
  (vec (concat (subvec vs 0 (inc position))
               [new-value]
               (subvec vs (inc position)))))


(defn part1 [steps]
  (loop [state [0]
         position 0]
    (let [i (count state)]
      (if (= 2018 i)
        (nth state (mod (inc position) i))
        (let [new-position (mod (+ position steps) i)
              new-state (insert-at-pos state new-position i)]
          (recur new-state (inc new-position)))))))



(defn part2 [steps]
  (loop [slot-one -1
         i 1
         position 0]
    (if (= 50000001 i)
      slot-one
      (let [new-position (inc (mod (+ position steps) i))
            new-slot-one (if (= 1 new-position)
                           i
                           slot-one)]
        (recur new-slot-one
               (inc i)
               new-position)))))



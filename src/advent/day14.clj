(ns advent.day14
  (:require [advent.day10]))

(def knot advent.day10/part2)


(def hex-bitcount
  {\0 0
   \1 1
   \2 1
   \3 2
   \4 1
   \5 2
   \6 2
   \7 3
   \8 1
   \9 2
   \a 2
   \b 3
   \c 2
   \d 3
   \e 3
   \f 4})

(def hex-bits
  {\0 [0 0 0 0]
   \1 [0 0 0 1]
   \2 [0 0 1 0]
   \3 [0 0 1 1]
   \4 [0 1 0 0]
   \5 [0 1 0 1]
   \6 [0 1 1 0]
   \7 [0 1 1 1]
   \8 [1 0 0 0]
   \9 [1 0 0 1]
   \a [1 0 1 0]
   \b [1 0 1 1]
   \c [1 1 0 0]
   \d [1 1 0 1]
   \e [1 1 1 0]
   \f [1 1 1 1]})


(defn hash->bits [k]
  (map hex-bitcount (knot k)))

(defn part1 [word]
  (reduce +
          (for [row (range 128)]
            (let [k (format "%s-%d" word row)
                  counts (map hex-bitcount (knot (format "%s-%d" word row)))]
              (reduce + counts)))))

(defn ->bitmap [word]
  (into []
        (for [row (range 128)]
          (let [k (format "%s-%d" word row)
                bits (map hex-bits (knot (format "%s-%d" word row)))]
            (into [] (apply concat bits))))))


(defn find-bit [bitmap]
  (first
   (for [x (range 128)
         y (range 128)
         :when (= 1 (get-in bitmap [x y]))]
     [x y])))

(defn bit-on [bitmap [x y :as xy]]
  (and (<= 0 x 127)
       (<= 0 y 127)
       (= 1 (get-in bitmap xy))))

(defn clear-bit [bitmap xy]
  (assoc-in bitmap xy 0))

(defn neighbors [[x y]]
  [[(dec x) y]
   [(inc x) y]
   [x (dec y)]
   [x (inc y)]])

(defn remove-group [bitmap start-xy]
  (loop [bitmap bitmap
         to-visit #{start-xy}]
    (if (seq to-visit)
      (let [node (first to-visit)
            new-nodes (filter (partial bit-on bitmap)
                              (neighbors node))
            new-visit-list (reduce conj (disj to-visit node) new-nodes)]
        (recur (clear-bit bitmap node)
               new-visit-list))
      bitmap)))

(defn count-groups [bitmap]
  (loop [bitmap bitmap
         groups 0]
    (if-let [xy (find-bit bitmap)]
      (recur (remove-group bitmap xy)
             (inc groups))
      groups)))

(defn part2 [word]
  (let [bitmap (->bitmap word)]
    (count-groups bitmap)))

(defn show [bitmap]
  (let [n 8]
    (doseq [row (take n bitmap)]
      (println (apply str (take n row))))))

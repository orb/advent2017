(ns advent.day15)

;; (generator A uses 16807; generator B uses 48271),

;;           . That final remainder is the value it produces next.

(def GEN_MOD 2147483647)

(defn make-generator [seed]
  (partial iterate (fn [n] (mod (*' n seed) GEN_MOD))))

(def generator-a
  (make-generator 16807))

(def generator-b
  (make-generator 48271))

(defn ab-pairs [a-seed b-seed]
  (map vector
       (rest (generator-a a-seed))
       (rest (generator-b b-seed))))


(defn divisible-by [m]
  (fn [n] (zero? (mod n m))))

(defn ab-pairs-filtered [a-seed b-seed]
  (map vector
       (filter (divisible-by 4) (rest (generator-a a-seed)))
       (filter (divisible-by 8) (rest (generator-b b-seed)))))

(defn good-pair? [[a b]]
  (= (bit-and a 65535)
     (bit-and b 65535)))


(defn million [n]
  (* n 1000000))

(defn part1 [a-seed b-seed]
  (count (filter good-pair? (take (million 40) (ab-pairs a-seed b-seed)))))

(defn part2 [a-seed b-seed]
  (count (filter good-pair? (take (million 5) (ab-pairs-filtered a-seed b-seed)))))

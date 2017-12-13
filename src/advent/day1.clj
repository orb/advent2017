(ns advent.day1)

(defn char->digit [c]
  (- (int c) (int \0)))

(defn make-digits [numbers]
  (map char->digit numbers))

(defn make-pairs [items]
  (take (count items)
        (partition 2 1 (cycle items))))

(defn good-pair? [pair]
  (= (first pair)
     (second pair)))

(defn points-for-pair [pair]
  (if (good-pair? pair)
    (first pair)
    0))

(defn part1 [number-string]
  (->> number-string
       make-digits
       make-pairs
       (map points-for-pair)
       (apply +)))


(defn make-halfway-pairs [items]
  (let [num-items (count items)
        skip (/ num-items 2)]
    (map (juxt first last)
         (take num-items
               (partition (inc skip) 1 (cycle items))))))

(defn part2 [number-string]
  (->> number-string
       make-digits
       make-halfway-pairs
       (map points-for-pair)
       (apply +)))


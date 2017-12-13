(ns advent.day2)

(defn checksum [row]
  (let [hi (reduce max row)
        lo (reduce min row)]
    (- hi lo)))

(defn part1 [input]
  (reduce + (map checksum input)))



(defn all-pairs [items]
  (when (seq items)
    (let [[x & ys] items]
      (concat (for [y ys] [(min x y) (max x y)])
              (all-pairs ys)))))

(defn divides [pair]
  (let [[lo hi] pair]
    (if (= 0 (mod hi lo))
      (quot hi lo)
      0)))

(defn checksum2 [row]
  (first (filter pos? (map divides (all-pairs row)))))

(defn part2 [input]
  (reduce + (map checksum2 input)))



(ns advent.day12)

(defn traverse [process-map starting-node]
  (loop [visited #{} to-visit [starting-node]]
    (if (seq to-visit)
      (let [visit (first to-visit)]
        (recur (merge visited visit)
               (concat (rest to-visit)
                       (remove visited (get process-map visit)))))
      visited)))

(defn part1 [process-map]
  (count (traverse process-map 0)))

(defn part2 [process-map]
  (loop [groups 0 remaining-processes process-map]
    (if (seq remaining-processes)
      (let [starting-node (first (keys remaining-processes))
            reachable (traverse remaining-processes starting-node)]
        (recur (inc groups)
               (apply dissoc remaining-processes reachable)))
      groups)))

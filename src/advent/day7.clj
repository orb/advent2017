(ns advent.day7
  "http://adventofcode.com/2017/day/7"
  (:require [clojure.string :as string]))



(defn towers->map
  "take a tower input and return map of discs to parents"
  [towers]
  (reduce (fn [tower-map [name [weight] _ & parents]]
            (assoc tower-map name parents))
          {}
          towers))

(defn tower-weights
  "take a tower input and return map of discs to parents"
  [towers]
  (reduce (fn [tower-map [name [weight]]]
            (assoc tower-map name weight))
          {}
          towers))

(defn invert-map
  "take a map of k -> vs and return map of v->ks"
  [in-map]
  (letfn [(invert-kvs [acc [k vs]]
            (reduce (fn [acc v]
                      (update acc v conj k))
                    acc
                    vs))]
    (reduce invert-kvs {} in-map)))

(defn part1 [towers]
  (let [tower-map (towers->map towers)
        inverted (invert-map tower-map)]
    ;; when we invert the map, the top tower will not be present
    ;; we can conveniently use the inverted map as predecate to filter
    ;; out all but the top tower
    (first (remove inverted (keys tower-map)))))


(defn weight-of [node connections weights]
  (let [connected-nodes (connections node)
        child-weights (map #(weight-of % connections weights) connected-nodes)]
    (reduce + (weights node) child-weights)))


;; the solution here is a ugly and inefficient. I'd definitely like to revisit this
(defn part2 [towers]
  (let [connections (towers->map towers)
        node-weights (tower-weights towers)
        nodes (keys connections)
        ;; not efficient
        tree-weights (zipmap nodes
                             (map #(weight-of % connections node-weights) nodes))]

    (letfn [(balanced? [node]
              (let [child-weights (map tree-weights (connections node))]
                (or (empty? child-weights)
                    (apply = child-weights))))

            (children-balanced? [node]
              (every? balanced? (connections node)))

            (correct-child-weight [node]
              (let [children (connections node)
                    freqs (frequencies (map tree-weights (connections node)))
                    [wrong right] (sort-by freqs (keys freqs))
                    wrong-node (first (filter #(= wrong (tree-weights %)) children))]

                (+ (node-weights wrong-node)
                   (- right wrong))))]
      (first (for [node nodes
                   :when (and (not (balanced? node))
                              (children-balanced? node))]
               (correct-child-weight node))))))

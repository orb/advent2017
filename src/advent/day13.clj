(ns advent.day13)

(defn distance [depth]
  (+ depth depth -2))


(defn run
  "returns the caught locations as pairs of layer and depth"
  [input offset]
  (let [layers
        (sort (keys input))

        test-layer
        (fn [layer]
          (let [depth (input layer)
                distance (distance depth)]
            (if (zero? (mod (+ layer offset) distance))
              [depth layer])))]
    (remove nil? (map test-layer layers))))

(defn part1 [input]
  (->> (run input 0)
       (map (fn [[depth layer]] (* depth layer)))
       (reduce +)))


(defn caught? [input offset]
  (seq (run input offset)))


;; so slooooow
(defn part2 [input]
  (loop [offset 0]
    (if (caught? input offset)
      (recur (inc offset))
      offset)))

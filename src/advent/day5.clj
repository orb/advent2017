(ns advent.day5)

(defn run-instructions [instructions modifier]
  (loop [instructions instructions
         position 0
         steps 0]

    #_(println (apply str
                    (format "[%4s]" steps)
                    (for [i (range (count instructions))]
                      (if (= i position)
                        (format " >%- 4d" (instructions i))
                        (format "  %- 4d" (instructions i))))))

    (if (not (<= 0 position (dec (count instructions))))
      steps
      (let [jump-delta (nth instructions position)]
        (recur (update-in instructions [position] modifier)
               (+ position jump-delta)
               (inc steps))))))

(defn part1 [instructions]
  (run-instructions instructions inc))


(defn part2-modifier [n]
  (if (>= n 3)
    (dec n)
    (inc n)))

(defn part2 [instructions]
  (run-instructions instructions part2-modifier))


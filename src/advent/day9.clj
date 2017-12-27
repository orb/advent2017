(ns advent.day9)


(defn process [in-stream]
  (loop [input in-stream
         states []
         score 0
         garbage 0]
    (if (not (seq input))
      {:score score
       :garbage garbage}
      (let [c (first input)
            state (peek states)]
        (cond
          (= :negate state)
          (recur (rest input)
                 (pop states)
                 score
                 garbage)

          (= \! c)
          (recur (rest input)
                 (conj states :negate)
                 score
                 garbage)

          (= :garbage state)
          (if (= \> c)
            (recur (rest input)
                   (pop states)
                   score
                   garbage)
            (recur (rest input)
                   states
                   score
                   (inc garbage)))

          (= \< c)
          (recur (rest input)
                 (conj states :garbage)
                 score
                 garbage)

          (= \{ c)
          (recur (rest input)
                 (conj states :open)
                 score
                 garbage)

          (= \} c)
          (let [local-score (count states)]
            (recur (rest input)
                   (pop states)
                   (+ score (count states))
                   garbage))

          :else
          (recur (rest input)
                 states
                 score
                 garbage))))))

(defn part1 [input]
  (:score (process input)))


(defn part2 [input]
  (:garbage (process input)))

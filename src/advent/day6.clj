(ns advent.day6)

(defn largest-bank [banks]
  (loop [bank 0
         max-bank 0]
    (if (>= bank (count banks))
      max-bank
      (recur (inc bank)
             (if (> (banks bank)
                    (banks max-bank))
               bank
               max-bank)))))

(defn reallocate [banks]
  (let [largest (largest-bank banks)
        amount (banks largest)
        bank-order (->> (cycle (range (count banks))) ;; cycle through the banks
                        (drop (inc largest))          ;; starting at next bank
                        (take amount))                ;; and only for the specified ammount
        starting-banks (assoc banks largest 0)]
    (reduce (fn [bank-state next-bank]
              (update bank-state next-bank inc))
            starting-banks
            bank-order)))

(defn part1 [memory]
  (loop [memory memory
         step 0
         seen #{}]
    (if (seen memory)
      step
      (recur (reallocate memory)
             (inc step)
             (conj seen memory)))))

(defn part2 [memory]
  (loop [memory memory
         step 0
         seen {}]
    (if-let [seen-at-step (seen memory)]
      (- step seen-at-step)
      (recur (reallocate memory)
             (inc step)
             (assoc seen memory step)))))

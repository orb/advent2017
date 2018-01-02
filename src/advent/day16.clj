(ns advent.day16
  (:require [instaparse.core :as instaparse]))

(def instructions-grammar
  "
  instructions=instruction (<sep> instruction)*
  instruction = spin | exchange | pair
  spin = <'s'> number
  exchange = <'x'> number <'/'> number
  pair = <'p'> letter <'/'> letter
  letter = #'[a-zA-Z]'
  number = #'-?[0-9]+'
  sep = ','
  ")

(def instructions-parser
  (instaparse/parser instructions-grammar))

(defn parse-number [text]
  (Integer/parseInt text))

(defn load-instructions [input]
  (instaparse/transform {:number parse-number
                         ;;:spin (fn [n] {:spin n})
                         ;;:exchange (fn [n1 n2] {:exchange [n1 n2]})
                         ;;:pair (fn [l1 l2] {:pair [l1 l2]})
                         :letter first ;; create char
                         :instruction identity
                         :instructions vector}

                        (instructions-parser input)))

(defmulti eval-instruction
  (fn [state instruction]
    (first instruction)))

(defmethod eval-instruction :default [state instruction]
  (throw (RuntimeException. (str instruction))))
(defmethod eval-instruction :spin [state instruction]
  ;; makes X programs move from the end to the front, but maintain their order otherwise. (For example, s3 on abcde produces cdeab).
  (let [[_ n] instruction]
    (let [howmany (- (count state) n)]
      (vec (concat (drop howmany state)
                   (take howmany state))))))

(defmethod eval-instruction :exchange [state instruction]
  (let [[_ index1 index2] instruction]

    (-> state
        (assoc index1 (get state index2))
        (assoc index2 (get state index1)))))

(defmethod eval-instruction :pair [state instruction]
  (let [[_ instruction1 instruction2] instruction
        index1 (.indexOf state instruction1)
        index2 (.indexOf state instruction2)]
    (if (or (nil? index1) (nil? index2))
      (println "X" (apply str state) instruction1 instruction2))
    (eval-instruction state [:exchange index1 index2])))

(defn eval-instructions [state instructions]
  (reduce eval-instruction state instructions))

(defn char-range [from to]
  ;; inclusive
  (mapv char (range (int from) (inc (int to)))))

(defn initial-state []
  (char-range \a \p))

(defn run
  ([source]
   (run source (initial-state)))
  ([source state]
   (eval-instructions state
    (load-instructions source))))


(defn state->string [state]
  (apply str state))

(defn part1 [input]
  (state->string (run input (initial-state))))


;; I'm unhjappy with this as a general purpose solution. I assumed
;; either the input could be simplified or the input would have a
;; cycle because otherwise I'd be waiting a week for this to
;; run. Thankfully, the cycle on my input was really small and I
;; I don't have to wait...

(defn part2 [input]
  (let [instructions (load-instructions input)
        run-once #(eval-instructions % instructions)
        state (initial-state)
        target 1000000000]
    (loop [iteration 0
           state (initial-state)
           seen {}]
      (let [state-str (state->string state)]
        (if (= iteration target)
          state-str
          (if-let [last-seen (seen state-str)]
            (let [cycle-length (- iteration last-seen)
                  cycles-to-skip (quot (- target iteration) cycle-length)
                  skip-to (+ iteration (* cycle-length cycles-to-skip))]
              (recur skip-to
                     state
                     {})) ;; clear out the seen state here so
            (recur (inc iteration)
                   (eval-instructions state instructions)
                   (assoc seen state-str iteration))))))))

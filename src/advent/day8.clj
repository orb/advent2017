(ns advent.day8
  (:require [instaparse.core :as instaparse]))

(def program-grammar
  "
  program = instruction*
  instruction = command 'if' condition
  command = register op number
  condition = register comparison number
  register = #'[a-z]+'
  op = 'inc' | 'dec'
  comparison = '==' | '!=' | '<' | '>' | '<=' | '>='
  number=#'-?[0-9]+'")


(def operations
  {"inc" +
   "dec" -})

(def comparisons
  {"==" ==
   "!=" not=
   "<=" <=
   ">=" >=
   "<"  <
   ">"  >})

(def program-transformation
  {:program vector
   :instruction (fn [command _ condition]
                  [command condition])
   :condition (fn [register comparison number]
                [register comparison number])
   :command  (fn [register op number]
               [register op number])
   :number #(Integer/parseInt %)
   :register keyword
   :comparison comparisons
   :op operations})

(def program-parser
  (instaparse/parser program-grammar :auto-whitespace :standard))

(defn load-program [source]
  (instaparse/transform program-transformation
                        (program-parser source)))

(defn register-get [registers register]
  (or (registers register) 0))

(defn register-set [registers register value]
  (assoc registers register value))

(defn eval-command [registers command]
  (let [[register op number] command]
    (register-set registers register
                  (op (register-get registers register) number))))

(defn test-condition [registers condition]
  (let [[register comparison value] condition]
    (comparison (register-get registers register) value)))

(defn eval-instruction [registers instruction]
  (let [[command condition] instruction]
    (if (test-condition registers condition)
      (eval-command registers command)
      registers)))


(defn run-program
  ([program] (run-program {} program))
  ([registers program]
   (reduce eval-instruction registers program)))

(defn part1 [source]
  (let [registers (run-program (load-program source))]
    ;; unset registers are 0 values - what do we do if there are no positive values
    ;; but there are unset referenced registers?
    (reduce max (vals registers))))

(defn nilmax
  "the maximum of two values, allowing for nil meaning no value"
  ([]
   nil)
  ([x]
    x)
  ([x y]
   (cond
     (nil? x) y
     (nil? y) x
     :else (max x y))))

(defn part2 [source]
  (loop [instructions (load-program source)
         registers {}
         max-value nil]

    (if (seq instructions)
      (let [next-state (eval-instruction registers (first instructions))]
        (recur (rest instructions)
               next-state
               (nilmax max-value (reduce nilmax (vals next-state)))))
      max-value)))

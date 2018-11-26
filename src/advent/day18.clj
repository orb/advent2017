(ns advent.day18
  (:require [instaparse.core :as instaparse]))

;; This solution is really ugly. I bolted on the changes for part2 onto an already sub-par part1
;; implementation. It seems like there should be a way to implement part2 on top of part1 in a less
;; hacky way. I didn't make an attempt to clean it up.

;; ========== part 1
;; snd X plays a sound with a frequency equal to the value of X.
;; set X Y sets register X to the value of Y.
;; add X Y increases register X by the value of Y.
;; mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
;; mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y
;;     (that is, it sets X to the result of X modulo Y).
;; rcv X recovers the frequency of the last sound played, but only when the value of X is not zero.
;;     (If it is zero, the command does nothing.)
;; jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
;;     (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)

;; ========== part 2
;; snd X sends the value of X to the other program. These values wait in a queue until that program is
;;     ready to receive them. Each program has its own message queue, so a program can never receive a
;;     message it sent.
;; rcv X receives the next value and stores it in register X. If no values are in the queue, the program
;;     waits for a value to be sent to it. Programs do not continue to the next instruction until they
;;     have received a value. Values are received in the order they are sent.

(def sound-grammer
  "
  commands = command (<'\n'> command)*
  <command> = snd | set | add | mul | mod | rcv | jgz
  snd = <'snd'> arg
  set = <'set'> register arg
  add = <'add'> register arg
  mul = <'mul'> register arg
  mod = <'mod'> register arg
  rcv = <'rcv'> register
  jgz = <'jgz'> arg arg
  <arg> = register | value
  register = #'[a-z]'
  value  = number
  number = #'-?[0-9]+'
  sp = ' '
 ")

(def send-grammer
  "
  commands = command (<'\n'> command)*
  <command> = snd | set | add | mul | mod | receive | jgz
  snd = <'snd'> arg
  set = <'set'> register arg
  add = <'add'> register arg
  mul = <'mul'> register arg
  mod = <'mod'> register arg
  receive = <'rcv'> register
  jgz = <'jgz'> arg arg
  <arg> = register | value
  register = #'[a-z]'
  value  = number
  number = #'-?[0-9]+'
  sp = ' '
 ")

(def sound-parser (instaparse/parser sound-grammer :auto-whitespace :standard))
(def send-parser (instaparse/parser send-grammer :auto-whitespace :standard))


(defn load-sound [input]
  (instaparse/transform {:number #(Integer/parseInt %)
                         :register (fn [register-name]
                                     [:register register-name])
                         :value (fn [number]
                                  [:constant number])
                         :commands vector}

                        (sound-parser input)))

(defn load-send [input]
  (instaparse/transform {:number #(Integer/parseInt %)
                         :register (fn [register-name]
                                     [:register register-name])
                         :value (fn [number]
                                  [:constant number])
                         :commands vector}

                        (send-parser input)))


;; ----------------------------------------

(defn increment-pc [state]
  (update state :pc inc))

;; ----------------------------------------

(defmulti eval-arg
  (fn [[arg-type arg] state]
    arg-type))

(defmethod eval-arg :register [[_ register] state]
  (or (get-in state [:registers register])))

(defmethod eval-arg :constant [[_ number] state]
  number)


;; ----------------------------------------

(defmulti eval-instruction
  (fn [[cmd & _] state]
    cmd))


(defmethod eval-instruction :set [[_ [_ register-name] arg] state]
  (let [value (eval-arg arg state)]
    (-> state
        (assoc-in [:registers register-name] value)
        (increment-pc))))

(defmethod eval-instruction :add [[_ [_ register-name] arg] state]
  (let [value (eval-arg arg state)]
    (if (nil? value)
      (println "****" arg (:registers state)))
    (-> state
        (update-in [:registers register-name] (fnil + 0) value)
        (increment-pc))))

(defmethod eval-instruction :mul [[_ [_ register-name] arg] state]
  (let [value (eval-arg arg state)]
    (-> state
        (update-in [:registers register-name] (fnil * 0) value)
        (increment-pc))))

(defmethod eval-instruction :mod [[_ [_ register-name] arg] state]
  (let [value (eval-arg arg state)]
    (-> state
        (update-in [:registers register-name] (fnil mod 0) value)
        (increment-pc))))

(defmethod eval-instruction :jgz [[_ arg-test arg-jump] state]
  (let [test-value (eval-arg arg-test state)
        jump-value (eval-arg arg-jump state)]
    (if (pos? test-value)
      (-> state
          (update :pc + jump-value))
      (-> state
          (increment-pc)))))

(defmethod eval-instruction :snd [[_ arg] state]
  (let [value (eval-arg arg state)]
    (-> state
        (assoc :snd value)
        (update :sent (fnil inc 0))
        (increment-pc))))

(defmethod eval-instruction :rcv [[_ arg] state]
  (let [value (eval-arg arg state)]
    (if (zero? value)
      (-> state
          (increment-pc))
      (-> state
          (assoc :recover (:snd state))
          (assoc :done true)))))

(defmethod eval-instruction :receive [[_ [_ register-name]] state]
  (if-let [received (first (:queue state))]
    (-> state
        (dissoc :waiting)
        (assoc-in [:registers register-name] received)
        (update :queue subvec 1)
        (increment-pc))
    (-> state
        (assoc :waiting true))))

(defn next-instruction [state]
  (when (not (:done state))
    (nth (:instructions state) (:pc state) nil)))

(defn step [state]
  (if-let [instruction (next-instruction state)]
    (eval-instruction instruction state)
    (assoc state :done true)))


(defn blocked? [state]
  (and (:waiting state) (empty? (:queue state))))


;; ----------------------------------------

(defn make-program-part1 [sound-program]
  {:snd nil
   :pc 0
   :instructions sound-program
   :registers {}})

(defn part1 [input]
  (let [final-state
        (loop [state (make-program-part1 (load-sound input))]
          (if (:done state)
            state
            (recur (step state))))]
    (:recover final-state)))


(defn make-program-part2 [send-program program-id]
  {:queue []
   :sent 0
   :snd nil
   :pc 0
   :instructions send-program
   :registers {"p" program-id}})

(defn transfer-snd
  "take a sent value from the sender and add it to the receiver"
  [receiver sender]
  (if-let [sent-value (:snd sender)]
    (update receiver :queue conj sent-value)
    receiver))

(defn clear-snd [state]
  (dissoc state :snd))

(defn part2 [input]
  (let [instructions (load-send input)]
    (loop [count 0
           state0 (make-program-part2 instructions 0)
           state1 (make-program-part2 instructions 1)]
      (if (and (or (blocked? state0) (:done state0))
               (or (blocked? state1) (:done state1)))
        (:sent state1)
        (let [next0 (step state0)
              next1 (step state1)]
          (recur (inc count)
                 (clear-snd (transfer-snd next0 next1))
                 (clear-snd (transfer-snd next1 next0))))))))

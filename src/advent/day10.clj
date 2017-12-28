(ns advent.day10
  (:require [clojure.string :as string]))

(defn reverse-n [items n]
  (concat (reverse (take n items))
          (drop n items)))

(defn skip-n [items n]
  (let [n (mod n (count items))]
    (concat (drop n items)
            (take n items))))

(defn run [items lengths]
    (let [rotated
        (reduce (fn [items [nrev nskip]]
                  (-> items
                      (reverse-n nrev)
                      (skip-n (+ nrev nskip))))
                items
                (map vector
                     lengths
                     (range)))

        offset ;; calculate far we've rotated and apply the opposite rotation
        (reduce + (map + lengths (range)))

        final
        (skip-n rotated (- offset))]
    final))

(defn part1 [nitems lengths]
  (let [final (run (take nitems (range)) lengths)]
    (* (first final)
       (second final))))

(defn xor [row]
  (apply bit-xor row))

(defn ->hex [ascii]
  (format "%02x" ascii))

(defn part2 [text]
  (let [input (concat (map int text) [17 31 73 47 23])
        result (run (range 256)
                 (apply concat (repeat 64 input)))]
    (->> result
         (partition 16)
         (map xor)
         (map ->hex)
         (string/join))))

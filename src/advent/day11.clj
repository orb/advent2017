(ns advent.day11
  (:require [clojure.string :as string]))


(def directions
  {:n  [0 1 -1]
   :s  [0 -1 1]
   :nw [-1 1 0]
   :ne [1 0 -1]
   :sw [-1 0 1]
   :se [1 -1 0]})

(defn move [from direction]
  (mapv + from (directions direction)))

(defn moves
  ([steps]
   (moves [0 0 0] steps))
  ([from steps]
   (loop [position from
          max-distance 0
          steps steps]
     (if (not (seq steps))
       {:position position
        :distance (distance position)
        :max-distance max-distance}
       (let [new-position (move position (first steps))
             new-distance (distance new-position)]
         (recur new-position
                (max max-distance new-distance)
                (rest steps)))))))

(defn distance [[x y z]]
  (max (Math/abs x)
       (Math/abs y)
       (Math/abs z)))

(defn parse-moves [move-str]
  (->> (string/split move-str #",")
       (map string/trim)
       (map keyword)))

(defn part1 [move-str]
  (let [destination (moves (parse-moves move-str))]
    (:distance destination)))

(defn part2 [move-str]
  (let [destination (moves (parse-moves move-str))]
    (:max-distance destination)))

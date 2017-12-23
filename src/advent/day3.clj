(ns advent.day3)

(def offsets
  {:left  [-1 0]
   :right [1 0]
   :up    [0 1]
   :down  [0 -1]})

(def turn-left
  {:right :up
   :up :left
   :left :down
   :down :right})

(defn dup-each [items]
  (mapcat (juxt identity identity) items))

(defn xy-distance [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(defn move [[x y] facing]
  (let [[dx dy] (offsets facing)]
    [(+ x dx) (+ y dy)]))

(defn part1 [input]
  ;; we'll start facing down at location 1
  ;; we'll take the alloted number of steps in each direction and then turn left
  ;; until we are at the destination location, then we'll calculate our distance
  (loop [location   1
         xy         [0 0]
         facing     :down
         steps-left 0
         steps      (dup-each (iterate inc 1 ))]
    (if (= location input)
      (xy-distance xy)
      (if (= steps-left 0)
        ;; no steps in this direction, turn left
        (recur location
               xy
               (turn-left facing)
               (first steps)
               (rest steps))
        ;; take a step in the direction we are facing
        (recur (inc location)
               (move xy facing)
               facing
               (dec steps-left)
               steps)))))

(defn neighboring-xy
  "get all the neigbors for an xy"
  [[x y]]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= 0 dx dy)]
    [(+ x dx) (+ y dy)]))

(defn matrix-value-for-xy
  "sum the neihbors of an xy position, default to 1 for base case of no neighbors"
  [matrix xy]
  (let [vals (keep matrix (neighboring-xy xy))]
    (if (seq vals)
      (reduce + vals)
      1)))

(defn part2 [input]
  (loop [location   1
         xy         [0 0]
         facing     :down
         steps-left 0
         steps      (dup-each (iterate inc 1 ))
         matrix     {}]
    (if (= steps-left 0)
      ;; no steps in this direction, turn left
      (recur location
             xy
             (turn-left facing)
             (first steps)
             (rest steps)
             matrix)
      (let [location-value (matrix-value-for-xy matrix xy)]
        (println (format "%s @ %s :: %s" location xy location-value))
        (if (> location-value input)
          location-value
          ;; take a step in the direction we are facing
          (recur (inc location)
                 (move xy facing)
                 facing
                 (dec steps-left)
                 steps
                 (assoc matrix xy location-value)))))))

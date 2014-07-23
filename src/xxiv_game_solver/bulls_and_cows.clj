(ns xxiv-game-solver.bulls-and-cows
  "A bonus problem to solve for Clojure DC meetup.
  See http://en.wikipedia.org/wiki/Bulls_and_cows for a description of the game."
  (:require [clojure.set :as set]
            [clojure.math.combinatorics :as combo]))

(def possible-guesses
  (let [possible-nums (range 10)]
    (for [n1 possible-nums
          n2 possible-nums
          n3 possible-nums
          n4 possible-nums
          :when (and (not= n2 n1)
                     (not= n3 n2)
                     (not= n3 n1)
                     (not= n4 n3)
                     (not= n4 n2)
                     (not= n4 n1))]
      [n1 n2 n3 n4])))




(defn solve-bulls-and-cows
  "Implements playing the bulls and cows game. The guess-fn takes 4 numbers and returns a tuple
  of the number of bulls and cows. 'Bulls' are exact matches (position and number). 'Cows' and numbers
  that match position but not the correct index. It should return the matching numbers."
  [guess-fn]

  (->> possible-guesses
       (map (fn [nums]
              [(guess-fn nums) nums]))
       (filter (fn [[[bulls cows] nums]]
                 (= bulls 4)))
       (map second)
       first))

(comment
  (solve-bulls-and-cows (partial guess [1 2 3 4]))
  (test-solver [4 2 3 5])
  (test-solver [1 2 3 4])
  (test-solver [0 1 2 3])
)




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Code to test a bulls and cows solver

(defn- count-cows
  [secret-nums guess-nums]
  (- 4 (count (set/difference (set guess-nums) (set secret-nums)))))

(defn- count-bulls
  [secret-nums guess-nums]
  (count (filter (partial apply =)
                 (map #(vector %1 %2) secret-nums guess-nums))))

(defn guess
  "Checks which of the guess nums are bulls and cows. Returns a tuple of the number of bulls and cows."
  [secret-nums guess-nums]

  (when (not= (count (distinct secret-nums)) 4)
    (throw (Exception. "Secret nums must be a set of 4 distinct numbers")))
  (when (not= (count (distinct guess-nums)) 4)
    (throw (Exception. "A guess must be a set of 4 distinct numbers")))

  (let [num-bulls (count-bulls secret-nums guess-nums)]
    [num-bulls (- (count-cows secret-nums guess-nums) num-bulls)]))

(defn test-solver
  "Tests the bulls and cows solver using the given number. Returns true if it finishes."
  [secret-nums]
  (let [counter (atom 0)
        guess-fn (fn [nums]
                   (swap! counter inc)
                   (guess secret-nums nums))
        answer (solve-bulls-and-cows guess-fn)]
    (println "Number of guesses" @counter)
    (= answer secret-nums)))



(ns xxiv-game-solver.bulls-and-cows
  "A bonus problem to solve for Clojure DC meetup.
  See http://en.wikipedia.org/wiki/Bulls_and_cows for a description of the game."
  (:require [clojure.set :as set]
            [clojure.math.combinatorics :as combo]))


(defn same-result? [secret-nums [guess-nums bulls cows]]
  (and (= bulls (count-bulls secret-nums guess-nums))
       (= cows (count-cows secret-nums guess-nums))))

(defn could-be-solution? [results-so-far potential-solution]
  (every? #(same-result? potential-solution %) results-so-far))

(defn solve-bulls-and-cows
  "Implements playing the bulls and cows game. The guess-fn takes 4
  numbers and returns a tuple of the number of bulls and cows. 'Bulls'
  are exact matches (position and number). 'Cows' and numbers that
  match position but not the correct index. It should return the
  matching numbers."
  [guess-fn]
  ;; TODO implement this
  ;;
  ;; each time I go through I get # of bulls and cows.
  ;;
  ;; I can filter further solutions to those that have the same # of
  ;; bulls and cows.
  (loop [all-answers (combo/combinations (range 9) 4)
         results-so-far '()]
    (let [current-answer (first all-answers)
          [bulls cows] (guess-fn current-answer)
          results-after-this-guess (cons (list current-answer bulls cows))]
      (if (= bulls 4)
        current-answer
        (recur (filter (partial could-be-solution? results-so-far)
                       (rest all-answers))
               (cons results-after-this-guess [current-answer bulls cows]))))))



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
  "Checks which of the guess nums are bulls and cows. Returns a tuple
   of the number of bulls and cows."
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
  (let [guess-fn (partial guess secret-nums)
        answer (solve-bulls-and-cows guess-fn)]
    (= answer secret-nums)))



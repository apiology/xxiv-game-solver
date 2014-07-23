(ns xxiv-game-solver.core
  (:require [clojure.math.combinatorics :as combo]
            [clojure.walk :as w]))

(defn all-number-permutations [n1 n2 n3 n4]
  (combo/permutations [n1 n2 n3 n4]))

(def all-operator-options
  (combo/selections [+ - / *] 3))

(defn create-expression [[n1 n2 n3 n4] [o1 o2 o3]]
  (list o1 n1 (list o2 n2 (list o3 n3 n4))))

(defn possible-answers [n1 n2 n3 n4]
  (for [numbers (all-number-permutations n1 n2 n3 n4)
        operators all-operator-options]
    (create-expression numbers operators)))

(defn evals-to-24? [l]
  (try
    (= 24 (eval l))
    (catch java.lang.ArithmeticException e
      false)))

(defn solve-xxiv
  "Takes a list of 4 numbers. It should see if the 4 numbers can be
  combined mathematically using +, *, -, and / to somehow equal the
  sum 24. It should return a sequence of Clojure code combining n1,
  n2, n3, and n4 with the 4 operations that evaluate to 24."  
  [n1 n2 n3 n4]
  (filter evals-to-24? (possible-answers n1 n2 n3 n4)))

(ns xxiv-game-solver.core
  (:require [clojure.test.check.generators :as gen]
            [clojure.math.combinatorics :as combo]
            [clojure.walk :as w]))

(def nums
  (gen/choose 1 9))

(def num-lists
  (gen/vector nums 4))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; my implementation

(combo/permutations [7 4 3 1])

(def possible-operations
  [+ * / -]
  ;; easier printing for debugging
  ; [:+ :* :/ :-]
  )

(defn pair-combinations
  [as b]
  (mapcat (fn [a]
         (map (fn [op] (list op a b)) possible-operations))
       as))

(defn nums->operations
  [nums]
  (mapcat (fn [[a b c d]]
            (-> [a]
                (pair-combinations b)
                (pair-combinations c)
                (pair-combinations d)))
          (combo/permutations nums)))

(defn solve-xxiv
  "TODO"
  [nums]
  (filter (fn [op]
            (= 24 (eval op)))
          (nums->operations nums)))

(defn pretty-print-equation
  [op]
  (w/prewalk-replace {* :* + :+ - :- / :/} op))
(comment 
(map pretty-print-equation (solve-xxiv [1 3 1 4]))
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn evaluate-solver
  []
  (let [num-lists (gen/sample-seq 100)]
    ;; should iterate over each num list evaluating the results 
    ;; make sure it is 24
    ;; Count the number of requests that it can find 24
    
    ))
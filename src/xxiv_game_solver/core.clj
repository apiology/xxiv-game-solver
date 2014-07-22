(ns xxiv-game-solver.core
  (:require [clojure.math.combinatorics :as combo]
            [clojure.walk :as w]))

(comment 
  
  ;; an example of the 24 game
  (let [n1 1
        n2 2
        n3 3
        n4 4
        
        ;; ` is the syntax quote
        ;; ~ is unqote
        ;; For what those do see: http://yobriefca.se/blog/2014/05/19/the-weird-and-wonderful-characters-of-clojure/ 
        ;; "operation" will now contain a list containing some clojure functions and the the values 
        ;; in n1, n2, n3, and n4. This is a data structure that can be evaluated as clojure code.
        operation `(* (+ ~n1 ~n2 ~n3) ~n4)]
    
    ;; We can evaluate operation as clojure code.
    (eval operation))
  
  )


(defn solve-xxiv
  "Takes a list of 4 numbers. It should see if the 4 numbers can be combined mathematically using 
  +, *, -, and / to somehow equal the sum 24. It should return a sequence of Clojure code combining
  n1, n2, n3, and n4 with the 4 operations that evaluate to 24."
  [n1 n2 n3 n4]
  
  ;; TODO implement this.
  
  )


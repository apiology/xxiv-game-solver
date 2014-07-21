(ns xxiv-game-solver.core-test
  (:require [clojure.test :refer :all]
            [xxiv-game-solver.core :refer :all]
            [clojure.test.check.properties :refer [for-all]]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]))

(deftest solve-xxiv-test
  (testing "problems with no solution"
    (is (nil? (seq (solve-xxiv 1 1 1 1))) "1 1 1 1 has no solution and should return nil or an empty sequence")
    (is (nil? (seq (solve-xxiv 7 7 7 7))) "7 7 7 7 has no solution and should return nil or an empty sequence"))
  
  (testing "problems with solutions"
    (let [answers (solve-xxiv 1 2 3 4)]
      (is answers "1 2 3 4 should find an answer")
      (is (seq? answers) "The response should be a sequence of matches")
      (is (some? (seq answers)) "The response should contain some matches")
      (is (every? (comp (partial = 24) eval) answers)))))

(defspec solve-xxiv-spec 10
  (for-all [nums (gen/vector (gen/choose 1 9) 4)]
    (let [answers (apply solve-xxiv nums)]
      (every? (comp (partial = 24) eval) 
              ;; Limit the answers we evaluate so it will finish faster.
              (take 2 answers)))))


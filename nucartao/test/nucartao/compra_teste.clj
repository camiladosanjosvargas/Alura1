(ns nucartao.compra-teste
  (:require [clojure.test :refer :all]
            [nucartao.logica :refer :all]))

(deftest maior-ou-igual-a-zero?-teste
  (testing "Verdadeiro quando o valor é 0"
    (is (maior-ou-igual-a-zero? 0)))

  (testing "Verdadeiro quando o valor é 1"
    (is (maior-ou-igual-a-zero? 1)))

  (testing "Falso quando o velor é -1"
    (is (not (maior-ou-igual-a-zero? -1)))))
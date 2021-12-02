(ns nucartao.compra-teste
  (:require [clojure.test :refer :all]
            [nucartao.logica :refer :all]))

(deftest maior-ou-igual-a-zero?-retornar-true-quando-o-numero-eh-0
  (testing "Existe compra para o cartao indicado"
    (is (maior-ou-igual-a-zero? 0))))

(deftest maior-ou-igual-a-zero?-retornar-true-quando-o-numero-eh-1
  (testing "Existe compra para o cartao indicado"
    (is (maior-ou-igual-a-zero? 1))))

(deftest maior-ou-igual-a-zero?-retornar-false-quando-o-numero-eh-menor-do-que-zero
  (testing "Existe compra para o cartao indicado"
    (is (not (maior-ou-igual-a-zero? -1)))))

(deftest maior-ou-igual-a-zero?-retornar-true-quando-o-numero-eh-0
  (testing "Existe compra para o cartao indicado"
    (is (maior-ou-igual-a-zero? 0))))
(ns nucartao.compra-teste
  (:require [clojure.test :refer :all]
            [nucartao.logica :refer :all]))

(deftest maior-ou-igual-a-zero?-teste
  (testing "Verdadeiro quando o valor é 0"
    (is (maior-ou-igual-a-zero? 0)))

  (testing "Verdadeiro quando o valor é 1"
    (is (maior-ou-igual-a-zero? 1)))

  (testing "Falso quando o valor é -1"
    (is (not (maior-ou-igual-a-zero? -1)))))

(deftest detalhes-de-compras-teste
  (testing "Formato de retorno válido da funcao detalhes-de-compras"
    (is (= [{:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}]
           (detalhes-de-compras [{:id 1, :cartao 10, :detalhes {:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}}]))))

  (testing "Formato de retorno inválido da funcao detalhes-de-compras"
    (is (not (= [:detalhes {:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}]
                (detalhes-de-compras [{:id 1, :cartao 10, :detalhes {:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}}]))))))
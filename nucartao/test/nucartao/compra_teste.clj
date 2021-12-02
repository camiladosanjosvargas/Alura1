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
  (testing "Formato de retorno válido"
    (is (= [{:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"},
            {:data "2020 10 02", :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"},
            {:data "2020 10 02", :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}]
           (detalhes-de-compras [{:id 1, :cartao 10, :detalhes {:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                 {:id 2, :cartao 50, :detalhes {:data "2020 10 02", :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                 {:id 3, :cartao 10, :detalhes {:data "2020 10 02", :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}}]))))

  (testing "Formato de retorno inválido"
    (is (not (= [:detalhes {:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"},
                 :detalhes {:data "2020 10 02", :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"},
                 :detalhes {:data "2020 10 02", :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}]
                (detalhes-de-compras [{:id 1, :cartao 10, :detalhes {:data "2020 10 02", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                      {:id 2, :cartao 50, :detalhes {:data "2020 10 02", :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                      {:id 3, :cartao 10, :detalhes {:data "2020 10 02", :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}}]))))))
(ns nucartao.compra-teste
  (:require [clojure.test :refer :all]
            [nucartao.logica :refer :all]
            [nucartao.modelo :refer :all]
            [nucartao.util :as n.u]
            [java-time :as t])
  (:import (java.time LocalDate)))

(def data n.u/data)

(deftest maior-ou-igual-a-zero?-teste
  (testing "Verdadeiro quando o valor é 0"
    (is (maior-ou-igual-a-zero? 0)))

  (testing "Verdadeiro quando o valor é 1"
    (is (maior-ou-igual-a-zero? 1)))

  (testing "Falso quando o valor é -1"
    (is (not (maior-ou-igual-a-zero? -1)))))

(deftest detalhes-de-compras-teste
  (testing "Formato de retorno válido - teste da funcao que lista os detalhes de todos as compras"
    (is (= [{:data (data 2021 01 12), :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"},
            {:data (data 2021 02 22), :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"},
            {:data (data 2021 10 23), :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}]
           (detalhes-de-compras [{:id 1, :cartao 10, :detalhes {:data (data 2021 01 12), :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                 {:id 2, :cartao 50, :detalhes {:data (data 2021 02 22), :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                 {:id 3, :cartao 10, :detalhes {:data (data 2021 10 23), :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}}]))))

  (testing "Formato de retorno inválido - teste da funcao que lista os detalhes de todos as compras"
    (is (not (= [:detalhes {:data (data 2021 01 12), :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"},
                 :detalhes {:data (data 2021 02 22), :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"},
                 :detalhes {:data (data 2021 10 23), :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}]
                (detalhes-de-compras [{:id 1, :cartao 10, :detalhes {:data (data 2021 01 12), :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                      {:id 2, :cartao 50, :detalhes {:data (data 2021 02 22), :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"}},
                                      {:id 3, :cartao 10, :detalhes {:data (data 2021 10 23), :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}}]))))))

(deftest todas-as-compras-por-categoria-teste
  (testing "Formato de retorno válido - teste da funcao de todos gastos por categoria"
    (is (= [{"Educação" "R$ 90.00"} {"Saúde" "R$ 5.00"}]
           (todas-as-compras-por-categoria [{:valor 80, :estabelecimento "EscolaABC", :categoria "Educação"},
                                            {:valor 10, :estabelecimento "EscolaABC", :categoria "Educação"},
                                            {:valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}]))))

  (testing "Formato de retorno inválido - teste da funcao de todos gastos por categoria"
    (is (not (= [{"Educação" "R$ 190.00"} {"Saúde" "R$ 51.00"}]
                (todas-as-compras-por-categoria [{:valor 80, :estabelecimento "EscolaABC", :categoria "Educação"},
                                                 {:valor 10, :estabelecimento "EscolaABC", :categoria "Educação"},
                                                 {:valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}]))))))

(deftest nova-compra-detalhada-teste
  (testing "teste da funcao que adiciona nova compra"
    (is (= [{:id 1, :cartao 10, :detalhes {:data (data 2021 01 12), :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}},
            {:id 2, :cartao 50, :detalhes {:data (data 2021 02 22), :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"}},
            {:id 3, :cartao 10, :detalhes {:data (data 2021 10 23), :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}},
            {:id 4, :cartao 20, :detalhes {:data (data 2021 11 26), :valor 50, :estabelecimento "EscolaABC", :categoria "Educação"}},
            {:id 5, :cartao 20, :detalhes {:data (data 2021 07 17), :valor 50, :estabelecimento "EscolaABC", :categoria "Educação"}},
            {:id 6, :cartao 20, :detalhes {:data (data 2021 11 18), :valor 5, :estabelecimento "EscolaABC", :categoria "Educação"}},
            {:id 7, :cartao 30, :detalhes {:data (data 2021 10 30), :valor 30, :estabelecimento "SalaoABC", :categoria "Beleza"}},
            {:id 8, :cartao 30, :detalhes {:data (data 2021 11 11), :valor 40, :estabelecimento "EscolaABC", :categoria "Educação"}},
            {:id 9, :cartao 40, :detalhes {:data (data 2021 12 12) :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}},
            {:id 10, :cartao 10, :detalhes {:data (n.u/data), :valor 180, :estabelecimento "FarmaciaABC", :categoria "Saude"}}]
           (nova-compra-detalhada {:cartao 10, :detalhes {:valor 180, :estabelecimento "FarmaciaABC", :categoria "Saude"}})))))

(deftest detalhar-compras-do-cartao-teste
  (testing "Formato de retorno válido - teste da funcao que detalha todas as compras do cartao"
    (is (= {:cliente                       1,
            :quantidade-total-de-compras   2,
            :total-de-gastos               "R$ 85.00",
            :total-de-gastos-por-categoria [{"Educação" "R$ 80.00"} {"Saúde" "R$ 5.00"}],
            :compras-realizadas            [{:data            (data 2021 01 12),
                                             :valor           80,
                                             :estabelecimento "EscolaABC",
                                             :categoria       "Educação"}
                                            {:data            (data 2021 10 23),
                                             :valor           5,
                                             :estabelecimento "FarmaciaABC",
                                             :categoria       "Saúde"}]}
           (detalhar-compras-do-cartao 10))))

  (testing "Formato de retorno inválido - teste da funcao que detalha todas as compras do cartao"
    (is (not (= {:cliente                       1,
                 :quantidade-total-de-compras   2,
                 :total-de-gastos               "R$ 85.00",
                 :total-de-gastos-por-categoria [{"Educação" "R$ 80.00"} {"Saúde" "R$ 5.00"}]}
                (detalhar-compras-do-cartao 10)))))

  )




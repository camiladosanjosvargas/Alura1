(ns nucartao.db
  (:require [nucartao.util :as n.u]))

(def data n.u/data)

(defn clientes []
  [{:cliente 1, :nome "Maria", :cpf "11111111111", :Email "maria@gmail.com.br"},
   {:cliente 2, :nome "José", :cpf "22222222222", :Email "jose@gmail.com.br"},
   {:cliente 3, :nome "Paulo", :cpf "33333333333", :Email "paulo@gmail.com.br"},
   {:cliente 4, :nome "Carla", :cpf "44444444444", :Email "carla@gmail.com.br"}])

(defn cartoes []
  [{:cartao 10, :cliente 1 :numero "908976", :CVV 989, :validade "2023-12-04", :limite 100},
   {:cartao 50, :cliente 1 :numero "7646378", :CVV 546, :validade "2023-12-10", :limite 500},
   {:cartao 20, :cliente 2 :numero "4563575", :CVV 764, :validade "2025-12-14", :limite 200},
   {:cartao 30, :cliente 3 :numero "753567", :CVV 643, :validade "2026-12-12", :limite 300},
   {:cartao 40, :cliente 4 :numero "845354", :CVV 123, :validade "2024-12-20", :limite 400}])

(defn todas-as-compras []
  [{:id 1, :cartao 10, :detalhes {:data "2020-12-10", :valor 80, :estabelecimento "EscolaABC", :categoria "Educação"}},
   {:id 2, :cartao 50, :detalhes {:data "2021-05-11", :valor 10, :estabelecimento "EscolaABC", :categoria "Educação"}},
   {:id 3, :cartao 10, :detalhes {:data "2020-07-12", :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}},
   {:id 4, :cartao 20, :detalhes {:data "2020-09-20", :valor 50, :estabelecimento "EscolaABC", :categoria "Educação"}},
   {:id 5, :cartao 20, :detalhes {:data "2018-01-31", :valor 50, :estabelecimento "EscolaABC", :categoria "Educação"}},
   {:id 6, :cartao 20, :detalhes {:data "2019-06-05", :valor 5, :estabelecimento "EscolaABC", :categoria "Educação"}},
   {:id 7, :cartao 30, :detalhes {:data "2020-08-25", :valor 30, :estabelecimento "SalaoABC", :categoria "Beleza"}},
   {:id 8, :cartao 30, :detalhes {:data "2021-02-17", :valor 40, :estabelecimento "EscolaABC", :categoria "Educação"}},
   {:id 9, :cartao 40, :detalhes {:data "2017-10-25", :valor 5, :estabelecimento "FarmaciaABC", :categoria "Saúde"}}])


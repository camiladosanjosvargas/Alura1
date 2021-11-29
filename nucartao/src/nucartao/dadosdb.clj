(ns nucartao.dadosdb
  (:require [nucartao.util :as n.u]))

(def data n.u/data)

(defn clientes []
  [{:cliente 1, :nome "Maria", :cpf "11111111111", :Email "maria@gmail.com.br"},
   {:cliente 2, :nome "José", :cpf "22222222222", :Email "jose@gmail.com.br"},
   {:cliente 3, :nome "Paulo", :cpf "33333333333", :Email "paulo@gmail.com.br"},
   {:cliente 4, :nome "Carla", :cpf "44444444444", :Email "carla@gmail.com.br"}])

(defn cartoes []
  [{:cartao 10, :cliente 1 :numero "908976", :CVV 989, :validade (data 2021 01 10), :limite 100},
   {:cartao 50, :cliente 1 :numero "7646378", :CVV 546, :validade (data 2022 01 10), :limite 500},
   {:cartao 20, :cliente 2 :numero "4563575", :CVV 764, :validade (data 2021 01 10), :limite 200},
   {:cartao 30, :cliente 3 :numero "753567", :CVV 643, :validade (data 2021 01 10), :limite 300},
   {:cartao 40, :cliente 4 :numero "845354", :CVV 123, :validade (data 2021 01 10), :limite 400}])


(ns nucartao.comprasdb
  (:require [nucartao.util :as n.u]))

(def data n.u/data)

(def compra-1 {:id      1
               :cliente 1,
               :cartao  10,
               :itens   {:data            (data 2020 10 02),
                         :valor           80,
                         :estabelecimento "Escola",
                         :categoria       "Educação"}})

(def compra-2 {:id      2
               :cliente 1,
               :cartao  10,
               :itens   {:data            (data 2020 10 05),
                         :valor           10,
                         :estabelecimento "Escola",
                         :categoria       "Educação"}})

(def compra-3 {:id      3
               :cliente 1,
               :cartao  10,
               :itens   {:data            (data 2020 10 10),
                         :valor           5,
                         :estabelecimento "Farmacia",
                         :categoria       "Saúde"}})

(def compra-4 {:id      4
               :cliente 2,
               :cartao  20,
               :itens   {:data            (data 2020 11 02),
                         :valor           50,
                         :estabelecimento "Escola",
                         :categoria       "Educação"}})

(def compra-5 {:id      5
               :cliente 2,
               :cartao  20,
               :itens   {:data            (data 2020 11 30),
                         :valor           50,
                         :estabelecimento "Escola",
                         :categoria       "Educação"}})

(def compra-6 {:id      6
               :cliente 2,
               :cartao  20,
               :itens   {:data            (data 2020 12 02),
                         :valor           5,
                         :estabelecimento "Escola",
                         :categoria       "Educação"}})

(def compra-7 {:id      6
               :cliente 3,
               :cartao  30,
               :itens   {:data            (data 2020 12 02),
                         :valor           30,
                         :estabelecimento "Salao",
                         :categoria       "Beleza"}})

(def compra-8 {:id      7
               :cliente 3,
               :cartao  30,
               :itens   {:data            (data 2020 12 31),
                         :valor           40,
                         :estabelecimento "Escola",
                         :categoria       "Educação"}})

(def compra-9 {:id      8
               :cliente 4,
               :cartao  40,
               :itens   {:data            (data 2020 12 31),
                         :valor           5,
                         :estabelecimento "Farmacia",
                         :categoria       "Saúde"}})



(defn todas-as-compras []
  [compra-1, compra-2, compra-3, compra-4, compra-5, compra-6, compra-7, compra-8, compra-9])


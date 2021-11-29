(ns nucartao.comprasdb
  (:require [nucartao.util :as n.u]))

(def data n.u/data)

(defn todas-as-compras []
  [{:id 1 :cliente 1, :cartao 10, :detalhes {:data (data 2020 10 02), :valor 80, :estabelecimento "Escola", :categoria "Educação"}},
   {:id 2 :cliente 1, :cartao 50, :detalhes {:data (data 2020 10 05), :valor 10, :estabelecimento "Escola", :categoria "Educação"}},
   {:id 3 :cliente 1, :cartao 10, :detalhes {:data (data 2020 10 10), :valor 5, :estabelecimento "Farmacia", :categoria "Saúde"}},
   {:id 4 :cliente 2, :cartao 20, :detalhes {:data (data 2020 11 02), :valor 50, :estabelecimento "Escola", :categoria "Educação"}},
   {:id 5 :cliente 2, :cartao 20, :detalhes {:data (data 2020 11 30), :valor 50, :estabelecimento "Escola", :categoria "Educação"}},
   {:id 6 :cliente 2, :cartao 20, :detalhes {:data (data 2020 12 02), :valor 5, :estabelecimento "Escola", :categoria "Educação"}},
   {:id 7 :cliente 3, :cartao 30, :detalhes {:data (data 2020 12 02), :valor 30, :estabelecimento "Salao", :categoria "Beleza"}},
   {:id 8 :cliente 3, :cartao 30, :detalhes {:data (data 2020 12 31), :valor 40, :estabelecimento "Escola", :categoria "Educação"}}
   {:id 9 :cliente 4, :cartao 40, :detalhes {:data (data 2020 12 31), :valor 5, :estabelecimento "Farmacia", :categoria "Saúde"}}])

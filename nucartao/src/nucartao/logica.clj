(ns nucartao.logica
  (:use [clojure pprint])
  (:require [nucartao.util :as n.u]
            [nucartao.db :as n.db]
            [schema.core :as s]))

(def todas-as-compras n.db/todas-as-compras)
(def formata-com-duas-casas-decimais n.u/formata-com-duas-casas-decimais)
(def obtem-mes n.u/obtem-mes)
(def obtem-ano n.u/obtem-ano)
(def cartoes n.db/cartoes)
(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))
(defn maior-ou-igual-a-zero? [x] (>= x 0))
(def ValorFinanceiro (s/constrained s/Num maior-ou-igual-a-zero?))

(def Detalhes {:valor ValorFinanceiro, :estabelecimento s/Str, :categoria s/Str})
(def CompraDetalhada {:cartao PosInt :detalhes Detalhes})


(defn gera-id []
  (get (into [] (map inc (into [] (map :id (into [] (take-last 1 (todas-as-compras))))))) 0))

(defn adiciona-id-data
  [compra]
  (assoc-in (assoc compra :id (gera-id)) [:detalhes :data] (n.u/data)))

(s/defn detalhes-da-compra
  [valor :- ValorFinanceiro, estabelecimento :- s/Str, categoria :- s/Str]
  {:valor valor, :estabelecimento estabelecimento, :categoria categoria})

(s/defn nova-compra-detalhada
  [cartao :- PosInt, detalhes :- Detalhes]
  (conj (todas-as-compras) (adiciona-id-data {:cartao cartao, :detalhes detalhes})) )

;chamada para adicionar uma nova compra validando os parametros de entrada
(pprint (nova-compra-detalhada 10 (detalhes-da-compra 180 "FarmaciaABC" "Saude")))



(defn total-dos-gastos
  [elementos]
  (formata-com-duas-casas-decimais (reduce + (map :valor elementos))))

(defn todos-os-gastos
  [[chave valor]]
  {chave (total-dos-gastos valor)})


(defn todas-as-compras-por-categoria
  [elementos]
  (->> elementos
       (group-by :categoria)
       (map todos-os-gastos)))

(defn detalhes-de-compras
  [elementos]
  (map :detalhes elementos))




(defn cartao-do-cliente?
  [filtro]
  (fn [cartao] (= (get cartao :cartao 0) filtro)))

(defn obtem-cliente
  [cartao]
  (->> (cartoes)
       (filter (cartao-do-cliente? cartao))))

(defn localiza-cliente
  [cartao]
  (map :cliente (obtem-cliente cartao)))

(defn todas-as-compras-realizadas
  [cartao compras]
  (let [detalhes (detalhes-de-compras compras)]
    {:cliente                       (localiza-cliente cartao)
     :quantidade-total-de-compras   (count compras)
     :total-de-gastos               (total-dos-gastos detalhes)
     :total-de-gastos-por-categoria (todas-as-compras-por-categoria detalhes)
     :compras-realizadas            detalhes}))

(defn existe-compra?
  [cartao]
  (fn [compra] (= (get compra :cartao 0) cartao)))

(defn detalhar-compras-do-cartao
  "Detalhar as compras do cartão"
  [cartao]
  (->> (todas-as-compras)
       (filter (existe-compra? cartao))
       (todas-as-compras-realizadas cartao)))


(defn compra-estah-no-mes-ano-de-referencia?
  [mes ano]
  (fn [compra] (and (= (obtem-mes (get compra :data 0)) mes)
                    (= (obtem-ano (get compra :data 0)) ano))))

(defn todas-as-compras-no-mes-ano-de-referencia
  [mes ano compras]
  (->> compras
       (detalhes-de-compras)
       (filter (compra-estah-no-mes-ano-de-referencia? mes ano))))

(defn detalhar-faturas-por-mes
  [cartao mes ano compras]
  (let [compras-mes-ano-referencia (todas-as-compras-no-mes-ano-de-referencia mes ano compras)]
    {:cliente            (localiza-cliente cartao)
     :mes-de-referencia  mes
     :ano-de-referencia  ano
     :total-da-fatura    (total-dos-gastos compras-mes-ano-referencia)
     :compras-realizadas compras-mes-ano-referencia}))

(defn detalhar-fatura-do-cartao-por-mes-e-ano
  "Detalhar fatura do cartão no mês e ano de referência"
  [cartao mes ano]
  (->> (todas-as-compras)
       (filter (existe-compra? cartao))
       (detalhar-faturas-por-mes cartao mes ano)))

(defn compra-realizada-maior-ou-igual-ao-valor-especificado?
  [filtro]
  (fn [compra] (>= (get compra :valor 0) filtro)))

(defn detalhar-todas-as-compras-por-valor
  [filtro compras]
  (->> compras
       (detalhes-de-compras)
       (filter (compra-realizada-maior-ou-igual-ao-valor-especificado? filtro))))

(defn compra-realizada-no-estabelecimento-especificado?
  [filtro]
  (fn [compra] (= (get compra :estabelecimento 0) filtro)))

(defn detalhar-todas-as-compras-por-estabelecimento
  [filtro compras]
  (->> compras
       (detalhes-de-compras)
       (filter (compra-realizada-no-estabelecimento-especificado? filtro))))

(defn todas-compras-por-filtro
  [cartao filtro compras]
  {:cliente  (localiza-cliente cartao)
   :filtro   filtro
   :detalhar (if (string? filtro)
               (detalhar-todas-as-compras-por-estabelecimento filtro compras)
               (detalhar-todas-as-compras-por-valor filtro compras))})

(defn busca-compras-por-filtro
  [cartao filtro]
  (->> (todas-as-compras)
       (filter (existe-compra? cartao))
       (todas-compras-por-filtro cartao filtro)))

(def Cartao s/Num)
(def Filtro (s/if pos-int? s/Num s/Str))

(defn busca-de-compras-valor-ou-estabelecimento
  "Encontrar as compras realizadas por filtro de estabelecimento ou valor (maior ou igual)"
  [cartao filtro]
  (let [cartao-validado (s/validate Cartao cartao)
        filtro-validado (s/validate Filtro filtro)]
    (busca-compras-por-filtro cartao-validado filtro-validado)))





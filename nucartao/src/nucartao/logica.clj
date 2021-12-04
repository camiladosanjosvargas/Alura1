(ns nucartao.logica
  (:use [clojure pprint])
  (:require [nucartao.util :as n.u]
            [nucartao.db :as n.db]
            [nucartao.modelo :as n.md]
            [schema.core :as s]
            [java-time :as t]))

(def todas-as-compras n.db/todas-as-compras)
(def formata-com-duas-casas-decimais n.u/formata-com-duas-casas-decimais)
(def obtem-mes n.u/obtem-mes)
(def obtem-ano n.u/obtem-ano)
(def cartoes n.db/cartoes)
(s/set-fn-validation! true)

(defn- gera-id []
  (get (into [] (map inc (into [] (map :id (into [] (take-last 1 (todas-as-compras))))))) 0))

(defn- adiciona-id-data
  [compra]
  (assoc-in (assoc compra :id (gera-id)) [:detalhes :data] (t/format "yyyy-MM-dd" (n.u/data))))

(s/defn nova-compra-detalhada :- [n.md/CompraDetalhada]
  [compra :- n.md/CompraDetalhada]
  {
   :pre  [(contains? (into [] (map :cartao (todas-as-compras))) 8)]
   :post [(contains? (into [] (map :cartao %)) 9)]
   }
  (conj (todas-as-compras) (adiciona-id-data compra)))

;chamada para adicionar uma nova compra validando os parametros de entrada
(pprint (nova-compra-detalhada {:cartao 10, :detalhes {:valor 180, :estabelecimento "FarmaciaABC", :categoria "Saude"}}))

(defn- total-dos-gastos
  [elementos]
  (formata-com-duas-casas-decimais (reduce + (map :valor elementos))))

(s/defn todos-os-gastos :- n.md/TotalGastosPorCategoria
  [[chave valor]] :- n.md/mapaDesestruturado
  {chave (total-dos-gastos valor)})

(s/defn todas-as-compras-por-categoria :- [n.md/TotalGastosPorCategoria]
  [elementos :- [n.md/Detalhes]]
  (into [] (map todos-os-gastos (group-by :categoria elementos))))

(s/defn detalhes-de-compras :- [n.md/Detalhes]
  [elementos :- [n.md/CompraDetalhada]]
  (map :detalhes elementos))

(s/defn cartao-do-cliente?
  [filtro :- n.md/PosInt]
  (fn [cartao] (= (get cartao :cartao 0) filtro)))

(s/defn obtem-cliente
  [cartao :- n.md/PosInt]
  (filter (cartao-do-cliente? cartao) (cartoes)))

;TODO: é uma boa prática ficar transformando em vetor? Como validar o retorno quando é uma sequencia?
(s/defn localiza-cliente :- [n.md/PosInt]
  [cartao :- n.md/PosInt]
  (into [] (map :cliente (obtem-cliente cartao))))

(s/defn todas-as-compras-realizadas :- n.md/RelatorioDeCompra
  [cartao :- n.md/PosInt compras :- [n.md/CompraDetalhada]]
  (let [detalhes (detalhes-de-compras compras)]
    {:cliente                       (get (localiza-cliente cartao) 0)
     :quantidade-total-de-compras   (count compras)
     :total-de-gastos               (total-dos-gastos detalhes)
     :total-de-gastos-por-categoria (todas-as-compras-por-categoria detalhes)
     :compras-realizadas            (into [] detalhes)}))

(s/defn existe-compra?
  [cartao :- n.md/PosInt]
  (fn [compra] (= (get compra :cartao 0) cartao)))

(s/defn detalhar-compras-do-cartao :- n.md/RelatorioDeCompra
  "Detalhar as compras do cartão"
  [cartao :- n.md/PosInt]
  (->> (todas-as-compras)
       (filter (existe-compra? cartao))
       (todas-as-compras-realizadas cartao)))

(s/defn compra-estah-no-mes-ano-de-referencia?
  [mes :- n.md/PosInt ano :- n.md/PosInt]
  (fn [compra] (and (= (obtem-mes (get compra :data 0)) mes)
                    (= (obtem-ano (get compra :data 0)) ano))))

(s/defn todas-as-compras-no-mes-ano-de-referencia
  [mes :- n.md/PosInt ano :- n.md/PosInt compras :- [n.md/CompraDetalhada]]
  (->> compras
       (detalhes-de-compras)
       (filter (compra-estah-no-mes-ano-de-referencia? mes ano))))

(s/defn detalhar-faturas-por-mes
  [cartao :- n.md/PosInt mes :- n.md/PosInt ano :- n.md/PosInt compras :- [n.md/CompraDetalhada]]
  (let [compras-mes-ano-referencia (todas-as-compras-no-mes-ano-de-referencia mes ano compras)]
    {:cliente           (localiza-cliente cartao)
     :mes-de-referencia mes
     :ano-de-referencia ano
     :total-da-fatura    (total-dos-gastos compras-mes-ano-referencia)
     :compras-realizadas compras-mes-ano-referencia}))

(s/defn detalhar-fatura-do-cartao-por-mes-e-ano
  "Detalhar fatura do cartão no mês e ano de referência"
  [cartao :- n.md/PosInt mes :- n.md/PosInt ano :- n.md/PosInt]
  (->> (todas-as-compras)
       (filter (existe-compra? cartao))
       (detalhar-faturas-por-mes cartao mes ano)))

(s/defn compra-realizada-maior-ou-igual-ao-valor-especificado?
  [filtro :- n.md/Filtro condicao :- n.md/PossiveisCondicoes]
  (fn [compra] (condicao (get compra :valor 0) filtro)))

(s/defn detalhar-todas-as-compras-por-valor
  [filtro :- n.md/Filtro compras :- [n.md/CompraDetalhada]]
  (->> compras
       (detalhes-de-compras)
       (filter (compra-realizada-maior-ou-igual-ao-valor-especificado? filtro >=))))

(s/defn compra-realizada-no-estabelecimento-especificado?
  [filtro :- n.md/Filtro]
  (fn [compra] (= (get compra :estabelecimento 0) filtro)))

(s/defn detalhar-todas-as-compras-por-estabelecimento
  [filtro :- n.md/Filtro compras :- [n.md/CompraDetalhada]]
  (->> compras
       (detalhes-de-compras)
       (filter (compra-realizada-no-estabelecimento-especificado? filtro))))

(s/defn todas-compras-por-filtro
  [cartao :- n.md/PosInt filtro :- n.md/Filtro compras :- [n.md/CompraDetalhada]]
  {:cliente  (get (into [] (localiza-cliente cartao)) 0)
   :filtro   filtro
   :detalhar (into [] (if (string? filtro)
                        (detalhar-todas-as-compras-por-estabelecimento filtro compras)
                        (detalhar-todas-as-compras-por-valor filtro compras)))})

(s/defn busca-compras-por-filtro
  [cartao :- n.md/PosInt filtro :- n.md/Filtro]
  (todas-compras-por-filtro cartao filtro (filter (existe-compra? cartao) (todas-as-compras))))

;TODO: é possível tratar a mensagem de uma exception lançada pelo schema?
(s/defn busca-de-compras-valor-ou-estabelecimento :- n.md/BuscaPorFiltro
  "Encontrar as compras realizadas por filtro de estabelecimento ou valor (maior ou igual)"
  [cartao :- n.md/PosInt, filtro :- n.md/Filtro]
  (busca-compras-por-filtro cartao filtro))



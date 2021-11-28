(ns nucartao.logica
  (:require [nucartao.comprasdb :as n.cdb]
            [nucartao.util :as n.u]))

(def valor-com-duas-casas-decimais n.u/valor-com-duas-casas-decimais)
(def obtem-mes n.u/obtem-mes)

(defn total-dos-gastos
  [elementos]
  (valor-com-duas-casas-decimais (reduce + (map :valor elementos))))

(defn todos-os-gastos
  [[chave valor]]
  {chave (total-dos-gastos valor)})

(defn todas-as-compras-por-categoria
  [elementos]
  (->> elementos
       (group-by :categoria)
       (map todos-os-gastos)))

(defn todas-as-compras
  [elementos]
  (map :itens elementos))

(defn todas-as-compras-realizadas-por-cliente
  [cliente compras]
  {:cliente                       cliente
   :quantidade-total-de-compras   (count compras)
   :total-de-gastos               (total-dos-gastos (todas-as-compras compras))
   :total-de-gastos-por-categoria (todas-as-compras-por-categoria (todas-as-compras compras))
   :compras-realizadas            (todas-as-compras compras)})

(defn conta-cadastrada?
  [cliente cartao]
  (fn [compra] (and (= (get compra :cliente 0) cliente)
                    (= (get compra :cartao 0) cartao))))

(defn detalhar-compras-do-cliente-e-cartao
  "Detalhar as compras do cliente no cartão indicado"
  [cliente cartao]
  (->> (n.cdb/todas-as-compras)
       (filter (conta-cadastrada? cliente cartao))
       (todas-as-compras-realizadas-por-cliente cliente)))

(defn compra-estah-no-mes-de-referencia?
  [mes]
  (fn [compra] (= (obtem-mes (get compra :data 0)) mes)))

(defn todas-as-compras-no-mes-de-referencia
  [mes compras]
  (->> compras
       (todas-as-compras)
       (filter (compra-estah-no-mes-de-referencia? mes))))

(defn detalhar-faturas-por-mes
  [cliente mes compras]
  {:cliente            cliente
   :mes-de-referencia  mes
   :total-da-fatura    (total-dos-gastos (todas-as-compras-no-mes-de-referencia mes compras))
   :compras-realizadas (todas-as-compras-no-mes-de-referencia mes compras)})

(defn detalhar-fatura-do-cliente-e-cartao-por-mes
  "Detalhar fatura do cliente no cartão e mês de referência indicado"
  [cliente cartao mes]
  (->> (n.cdb/todas-as-compras)
       (filter (conta-cadastrada? cliente cartao))
       (detalhar-faturas-por-mes cliente mes)))

(defn compra-realizada-maior-do-que-valor-especificado?
  [filtro]
  (fn [compra] (>= (get compra :valor 0) filtro)))

(defn detalhar-todas-as-compras-por-valor
  [filtro compras]
  (->> compras
       (todas-as-compras)
       (filter (compra-realizada-maior-do-que-valor-especificado? filtro))))

(defn compra-realizada-no-estabelecimento-especificado?
  [filtro]
  (fn [compra] (= (get compra :estabelecimento 0) filtro)))

(defn detalhar-todas-as-compras-por-estabelecimento
  [filtro compras]
  (->> compras
       (todas-as-compras)
       (filter (compra-realizada-no-estabelecimento-especificado? filtro))))

(defn todas-compras-por-filtro
  [cliente filtro compras]
  {:cliente cliente
   :filtro filtro
   :detalhar (if (string? filtro)
               (detalhar-todas-as-compras-por-estabelecimento filtro compras)
               (detalhar-todas-as-compras-por-valor filtro compras))})

(defn busca-compras-por-filtro
  [cliente cartao filtro]
  (->> (n.cdb/todas-as-compras)
       (filter (conta-cadastrada? cliente cartao))
       (todas-compras-por-filtro cliente filtro)))

(defn busca-de-compras-valor-ou-estabelecimento
  "Encontrar as compras realizadas por filtro de estabelecimento ou valor (maior ou igual)"
  [cliente cartao filtro]
  (cond (or (string? filtro)
            (and (number? filtro)
                 (pos? filtro))) (busca-compras-por-filtro cliente cartao filtro)
        :else "Filtro de busca inválido"))


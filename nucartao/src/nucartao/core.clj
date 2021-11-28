(ns nucartao.core
  (:use [clojure pprint])
  (:require [nucartao.logica :as n.l]))

(println "Detalhar compras realizadas")
(pprint (n.l/detalhar-compras-do-cliente-e-cartao 1 10))

(println "\n\n\nDetalhar fatura do cartao")
(pprint (n.l/detalhar-fatura-do-cliente-e-cartao-por-mes 2 20 12))

(println "\n\n\nBuscar pot valor ou estabelecimento")
(println (n.l/busca-de-compras-valor-ou-estabelecimento 1 10 "Farmacia"))
(println (n.l/busca-de-compras-valor-ou-estabelecimento 1 10 80))
(println (n.l/busca-de-compras-valor-ou-estabelecimento 1 10 true))




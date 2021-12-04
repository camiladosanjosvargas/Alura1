(ns nucartao.core
  (:use [clojure pprint])
  (:require [nucartao.logica :as n.l]))

(println "\n\n\nDetalhar compras realizadas")
(pprint (n.l/detalhar-compras-do-cartao 10))

(println "\n\n\nDetalhar fatura do cartao")
(pprint (n.l/detalhar-fatura-do-cartao-por-mes-e-ano 20 01 2018))

(println "\n\n\nBusca por estabelecimento")
(pprint (n.l/busca-de-compras-valor-ou-estabelecimento 10 "FarmaciaABC"))

(println "\n\n\nBusca por valor")
(pprint (n.l/busca-de-compras-valor-ou-estabelecimento 10 80))

(println "\n\n\nBusca inválida")
(pprint (n.l/busca-de-compras-valor-ou-estabelecimento 10 -1))

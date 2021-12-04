(ns nucartao.modelo
  (:require [schema.core :as s]))

(def PosInt (s/pred pos-int? 'inteiro-positivo))

(defn maior-ou-igual-a-zero? [x] (>= x 0))
(def ValorFinanceiro (s/constrained s/Num maior-ou-igual-a-zero?))

(def ComprasRealizadas [{:data            s/Str
                         :valor           ValorFinanceiro,
                         :estabelecimento s/Str,
                         :categoria       s/Str}])

(def Detalhes {(s/optional-key :data) s/Str,
               :valor                 ValorFinanceiro,
               :estabelecimento       s/Str,
               :categoria             s/Str})

(def CompraDetalhada {(s/optional-key :id) PosInt,
                      :cartao              PosInt
                      :detalhes            Detalhes})

(def Filtro (s/if pos-int? s/Num s/Str))

(def ComprasRealizadas [{:data            s/Str
                         :valor           ValorFinanceiro,
                         :estabelecimento s/Str,
                         :categoria       s/Str}])

(def TotalGastosPorCategoria {s/Str s/Str})

(def RelatorioDeCompra {:cliente                       PosInt
                        :quantidade-total-de-compras   PosInt,
                        :total-de-gastos               s/Str,
                        :total-de-gastos-por-categoria [TotalGastosPorCategoria],
                        :compras-realizadas            ComprasRealizadas})

(def mapaDesestruturado [s/Str [Detalhes]])

(def PossiveisCondicoes (s/enum < > <= >=))

(def Bool (or true false))

(def Detalhar [{:data            s/Any,
                :valor           ValorFinanceiro,
                :estabelecimento s/Str,
                :categoria       s/Str}])

(def BuscaPorFiltro {:cliente  s/Num,
                     :filtro   Filtro,
                     :detalhar Detalhar})

(def Cartoes {:cartao   PosInt,
              :cliente  PosInt,
              :numero   PosInt,
              :CVV      PosInt,
              :validade s/Str,
              :limite   ValorFinanceiro})

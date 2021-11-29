(ns nucartao.util
  (:require [java-time :as t]))

(defn data
  [ano mes dia]
  (t/local-date ano mes dia))

(defn formata-data
  [data]
  (t/format data))

(defn obtem-mes
  [data]
  (t/as data :month-of-year))

(defn obtem-ano
  [data]
  (t/as data :year))

(defn formata-com-duas-casas-decimais
  [valor]
  (clojure.core/format "R$ %.2f" (float valor)))

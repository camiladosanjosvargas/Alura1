(ns nucartao.util
  (:require [java-time :as t]))

(defn data
  ([ano mes dia] (t/local-date ano mes dia))
  ([] (t/local-date)))

(defn obtem-mes [data-string] (t/as (t/local-date data-string) :month-of-year))

(defn obtem-ano [data-string] (t/as (t/local-date data-string) :year))

(defn formata-com-duas-casas-decimais [valor] (clojure.core/format "R$ %.2f" (float valor)))


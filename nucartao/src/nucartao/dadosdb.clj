(ns nucartao.dadosdb
  (:require [nucartao.util :as n.u]))

(def data n.u/data)

(def dados-cadastrais-cliente-1 {:cliente 1 {:nome  "Maria",
                                             :cpf   "2343545645",
                                             :Email "ex@gmail.com.br"},
                                          :cartao 10 {:numero   "908976",
                                                      :CVV      989,
                                                      :validade (data 2021 01 10),
                                                      :limite   100}})

(def dados-cadastrais-cliente-2 {:cliente 2 {:nome  "José",
                                             :cpf   "2343545645",
                                             :Email "ex@gmail.com.br"},
                                          :cartao 20 {:numero   "908976",
                                                      :CVV      989,
                                                      :validade (data 2021 01 10),
                                                      :limite   200}})

(def dados-cadastrais-cliente-3 {:cliente 3 {:nome  "Paulo",
                                             :cpf   "2343545645",
                                             :Email "ex@gmail.com.br"},
                                          :cartao 30 {:numero   "908976",
                                                      :CVV      989,
                                                      :validade (data 2021 01 10),
                                                      :limite   300}})

(def dados-cadastrais-cliente-4 {:cliente 4 {:nome  "Carla",
                                             :cpf   "2343545645",
                                             :Email "ex@gmail.com.br"},
                                          :cartao 40 {:numero   "908976",
                                                      :CVV      989,
                                                      :validade (data 2021 01 10),
                                                      :limite   400}})



(defn dados-cadastrais []
  [dados-cadastrais-cliente-1, dados-cadastrais-cliente-2, dados-cadastrais-cliente-3, dados-cadastrais-cliente-4])



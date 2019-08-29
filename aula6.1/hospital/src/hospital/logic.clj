(ns hospital.logic
  (:require [hospital.model :as h.model]
            [schema.core :as s]))

; Test Driven Development
; Test Driven Design

; existe um problema de condicional quando o departamento não existe
;(defn cabe-na-fila?
;  [hospital departamento]
;  (-> hospital
;      departamento
;      count
;      (< 5)))


; funciona para o caso de não ter o departamento
;(defn cabe-na-fila?
;  [hospital departamento]
;  (when-let [fila (get hospital departamento)]
;    (-> fila
;        count
;        (< 5))))

; também funciona mas usa o some->
; desvantagem/vantagem "menos explicito"
; desvantagem qq um que der nil, devolve nil
(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
          departamento
          count
          (< 5)))

;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)
;    (throw (ex-info "Não cabe ninguém neste departamento" {:paciente pessoa}))))


;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)
;    (throw (IllegalStateException. "Não cabe ninguém neste departamento." ))))


; nil???
;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)))

; exemplo para extrair com ex-data
;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)
;    (throw (ex-info "Não cabe ninguém neste departamento" {:paciente pessoa, :tipo :impossivel-colocar-pessoa-na-fila}))))


;(defn- tenta-colocar-na-fila
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)))
;
;(defn chega-em
;  [hospital departamento pessoa]
;  (if-let [novo-hospital (tenta-colocar-na-fila hospital departamento pessoa)]
;    { :hospital novo-hospital, :resultado :sucesso}
;    { :hospital hospital, :resultado :impossivel-colocar-pessoa-na-fila}))

; antes de fazer swap chega-em vai ter que tratar o resultado
; não dá pra fugir disso (preocupacoes), se o resultado é para ser usado com atomos ou similares
; e ao mesmo tempo tratar erros
;(defn chega-em!
;  [hospital departamento pessoa]
;  (chega-em hospital departamento pessoa))


; código de um curso anterior

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Não cabe ninguém neste departamento" {:paciente pessoa}))))

(s/defn atende :- h.model/Hospital
  [hospital :- h.model/Hospital, departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proxima :- h.model/PacienteID
  "Retorna o próximo paciente da fila"
  [hospital :- h.model/Hospital, departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(s/defn transfere :- h.model/Hospital
  "Transfere o próximo paciente da fila de para a fila para"
  [hospital :-  h.model/Hospital, de :- s/Keyword, para :- s/Keyword]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))


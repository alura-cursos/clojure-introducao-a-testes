(ns hospital.logic-test
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]))

(deftest cabe-na-fila?-test

  ; boundary tests
  ; exatamente na borda e one off. -1, +1. <=, >=, =.

  ; checklist na minha cabeca. poderia ser um checklist no papel

  ; borda do zero
  (testing "Que cabe numa fila vazia"
    (is (cabe-na-fila? {:espera []}, :espera)))

  ; borda do limite
  (testing "Que não cabe na fila quando a fila está cheia"

    ; é de simples leitura pois é sequencial
    ; mas a desvantagem é que podemos errar em fazer coisas sequenciais
    ;(is (not (cabe-na-fila? {:espera [1 2 3 4 5]}, :espera)))

    ; não precisa ser sequencial e no mundo real não é
    ; portanto faça testes que não são sequenciais!
    (is (not (cabe-na-fila? {:espera [1 5 37 54 21]}, :espera)))
    )

  ; one off da borda do limite pra cima
  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

  ; dentro das bordas
  (testing "Que cabe na fila quando tem gente mas não está cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]}, :espera))
    (is (cabe-na-fila? {:espera [1 2]}, :espera)))

  (testing "Que não cabe quando o departamento não existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))

(deftest chega-em-test

  (testing "aceita pessoas enquanto cabem pessoas na fila"

    ; implementação ruim pois testa que escrevemos o que escrevemos.
    ; isto é, testa que erramos o que erramos. e que acertamos o que acertamos.
    ;(is (= (update {:espera [1, 2]} :espera conj 5)
    ;       (chega-em {:espera [1, 2]}, :espera, 5)))

    (is (= {:espera [1, 2, 3, 4, 5]}
           (chega-em {:espera [1, 2, 3, 4]}, :espera, 5)))

    ; teste não sequencial
    (is (= {:espera [1, 2, 5]}
           (chega-em {:espera [1, 2]}, :espera, 5)))))

















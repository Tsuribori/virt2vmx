(ns virt2vmx.preprocess
  (:require [clojure.set :refer [difference union]]))

(defn preprocess-interfaces [s]
  "Add numbering starting from 0 to network interfaces"
  (loop [nodes s interface-num 0 output []]
    (if (empty? nodes)
      output
      (let [is-interface (= (:tag (first nodes)) (keyword "interface")) node (first nodes)]
        (recur
         (rest nodes)
         (if is-interface (inc interface-num) interface-num)
         (if is-interface
           (conj output (assoc-in node [:attrs :interface-num] interface-num))
           (conj output node)))))))

(def preprocess-func (comp
                      preprocess-interfaces))

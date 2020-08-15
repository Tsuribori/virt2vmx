(ns virt2vmx.objects.common)

(defn get-tag [coll tag]
  (first (filter #(= (:tag %) tag) coll)))

(defprotocol ToRepr
  (to-repr [this]))

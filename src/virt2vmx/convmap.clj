(ns virt2vmx.convmap
  (:require [virt2vmx.objects.disk :refer [get-disk]]))

(defn convert-tag [tag]
  (case tag
    :name
    (fn [_ val] (format "displayName = \"%s\"\n" (first val)))
    :memory
    (fn [_ val]
      (let [ival (Integer/parseInt (first val))]
        (format "memSize = \"%s\"\n"
                (let [megabytes (/ (* ival 1024) 1000000)]
                  (str (- megabytes (mod megabytes 4)))))))
    :vcpu
    (fn [_ val] (format "numvcpus = \"%s\"\n" (first val)))
    :disk
    (fn [attrs val] (get-disk attrs val))
    nil))

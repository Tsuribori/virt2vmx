(ns virt2vmx.objects.interface
  (:require [clojure.string :as s])
  (:require [virt2vmx.objects.common :as common :refer [get-tag]]))

(deftype Interface [type network-name unit]
  common/ToRepr
  (common/to-repr [this]
    (transduce
     (map #(str (format "ethernet%s." unit) %))
     str
     ["present = \"true\"\n"
      (format "connectionType = \"%s\"\n" type)
      (format "networkName = \"%s\"\n" network-name)])))


(defn get-interface-type [type]
  (case type
    "network" "nat"
    "bridged" "bridged"
    "nat"))

(defn get-network-name [val type]
  (get-in (get-tag val :source) [:attrs (keyword type)]))


(defn get-interface-unit [attrs]
  (get attrs :interface-num))

(defn get-interface [attrs val]
  (let [type (get attrs :type)]
    (common/to-repr (->Interface
                     (get-interface-type type)
                     (get-network-name val type)
                     (get-interface-unit attrs)))))

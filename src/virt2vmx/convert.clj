(ns virt2vmx.convert
  (:require [clojure.data.xml :refer [parse-str]])
  (:require [virt2vmx.preprocess :refer [preprocess-func]])
  (:require [virt2vmx.convmap :refer :all]))

(defprotocol Translation
  ^{:doc "Protocol for translating domain XML to VMX"}
  (translate [this]))

(deftype Tag [tag contents attrs]
  ^{:doc "Type for XML tags e.g. <foo buzz=1>2</foo>"}
  Translation
  (translate [this]
    (when-let [convert-func (convert-tag (.tag this))]
        (convert-func (.attrs this) (.contents this)))))

(defn translate-node [node]
  "Apply translation for a single XML tag and it's attributes"
  (translate (->Tag (get node :tag) (get node :content) (get node :attrs))))

(defn get-xml [text]
  (xml-seq (parse-str text)))

(defn loop-tree
  "Go through the XML tree seq recursively."
  ([node]
   (loop-tree node []))
  ([node output]
   (if (empty? node)
     output
     (recur (rest node) (conj output (translate-node (first node)))))))
 
(defn transform-to-vmx [contents]
  (str
   ".encoding = \"UTF-8\"\n"
   "config.version = \"8\"\n"
   "virtualHW.version = \"8\"\n"
   (apply str (loop-tree (preprocess-func (get-xml contents))))))

(defn convert [file output]
  (let [contents (slurp file)]
    (spit output (transform-to-vmx contents))))

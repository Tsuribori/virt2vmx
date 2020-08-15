(ns virt2vmx.core
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.string :as str])
  (:require [virt2vmx.convert :refer [convert]]))


(defn get-output [name]
  (str/replace name #".xml" ".vmx"))

(def cli-options
  [["-f" "--file NAME" "Qemu file to convert"]
   ["-o" "--output NAME" "Name of the output file"]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [options]} (parse-opts args cli-options)]
    (let [{:keys [file output]} options]
      (if (nil? output)
        (convert file (get-output file))
        (convert file output)))))

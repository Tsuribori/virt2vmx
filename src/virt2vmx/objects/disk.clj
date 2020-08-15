(ns virt2vmx.objects.disk
  (:require [clojure.java.shell :refer [sh]])
  (:require [clojure.string :as s])
  (:require [virt2vmx.objects.common :as common :refer [get-tag]]))

(defn convert-disk [source source-converted]
  (do
    (println "Converting disk... (this may take a while!)")
    (let [exit-code (:exit (sh "qemu-img" "convert" "-f" "qcow2" "-O" "vmdk" source source-converted))]
      (if (= exit-code 0)
        (println "Succesfully converted disk!")
        (throw (Exception. "Error while converting disk! Do you have permission to read it?"))))
    (shutdown-agents)))

(deftype Disk [bus unit type source source-converted target]
  common/ToRepr
  (common/to-repr [this]
    (do
      (when (= (.type this) "disk")
        (convert-disk (.source this) (.source-converted this)))
      (apply str
             (map
              #(when (not (nil? %)) (str (format "%s%s:0.%s" bus unit %)))
              ["present = \"true\"\n"
               (format "deviceType = \"%s\"\n" (.type this))
               (when-let [filename (.source-converted this)]
                 (format "filename: \"%s\"\n" filename))])))))

(defn get-disk-bus [val]
  (get-in (get-tag val :target) [:attrs :bus]))

(defn get-disk-unit [val]
  (get-in (get-tag val :address )[:attrs :unit]))

(defn get-disk-type [val]
  (case val
    "cdrom" "cdrom-image"
    "disk" "disk"))

(defn get-disk-source [val]
  (get-in (get-tag val :source) [:attrs :file]))

(defn get-disk-source-converted [source type]
  "No relative paths in VMX!"
  (last
   (s/split
    (if (= type "disk")
      (s/replace source #".qcow2" ".vmdk")
      source)
    #"/")))

;;(defn get-disk-target [val]
;;(get-in (get-tag val :target) [:attrs :dev])) 

(defn get-disk [attrs val]
  (let [source (get-disk-source val) type (get-disk-type (get attrs :device))]
    (common/to-repr (->Disk
              (get-disk-bus val)
              (get-disk-unit val)
              type
              source
              (get-disk-source-converted source type)
              (str "")))))

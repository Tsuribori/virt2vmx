(defproject virt2vmx "0.1.2-SNAPSHOT"
  :description "Convert libvirt domain XML to VMX format."
  :url "https://github.com/Tsuribori/virt2vmx/releases"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.cli "1.0.194"] 
                 [org.clojure/data.xml "0.0.8"]]
  :main ^:skip-aot virt2vmx.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})

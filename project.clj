(defproject clojure-trie-react-experiment "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.5.1"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.2.1"]]


  :plugins [[lein-ring "0.9.7"]]

  :ring {:init clojure-trie-react-experiment.core/init
         :handler clojure-trie-react-experiment.core/site})

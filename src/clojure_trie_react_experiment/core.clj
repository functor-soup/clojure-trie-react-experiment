(ns clojure-trie-react-experiment.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.util.response :as resp ])
  (:use ring.middleware.params
        ring.adapter.jetty
        [ring.middleware.json :only [wrap-json-response]]))

;; http://stackoverflow.com/questions/1452680/clojure-how-to-generate-a-trie

(def trie (atom {}))

(defn add-to-trie
  [trie word]
  (assoc-in trie word (merge (get-in trie word) {:val word :terminal true})))

(defn prefix-matches
  [trie prefix]
  (keep :val (tree-seq map? vals (get-in trie prefix))))

;; http://clojure-doc.org/articles/cookbooks/files_and_directories.html
(defn init
  "load words into trie ... job queue?"
  []
  (with-open [rdr (io/reader "/usr/share/dict/words")]
    (doall (map #(swap! trie add-to-trie %) (line-seq rdr)))))

(defn handler_
  [word]
  (resp/response
   (->> word
    (prefix-matches @trie)
    (take 10))))

(defroutes app
  (GET "/" []
    (resp/content-type (resp/resource-response "index.html" {:root "public"}) "text/html"))
  (GET "/alpha/:x" [x] (resp/content-type (handler_ x) "text/json"))
  (route/not-found "not found: monkeys will investiagte"))


(def site
  (-> app
      (wrap-defaults  site-defaults)
      (wrap-json-response)))

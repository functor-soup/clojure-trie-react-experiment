(ns clojure-trie-react-experiment.core
  (:require [clojure.java.io :as io])
  (:use ring.middleware.params
        ring.util.response
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


(defn handler
  [{params :uri}]
  (let [word (subs params 1)]
    (response  (->> word
                    (prefix-matches @trie)
                    (take 10)))))

(def app
    (wrap-json-response handler))
;;(run-jetty (wrap-json-response handler) {:port 9000})

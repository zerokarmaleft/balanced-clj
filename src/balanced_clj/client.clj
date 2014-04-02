(ns balanced-clj.client
  (:refer-clojure :exclude [get])
  (:require [clojure.string  :as str]
            [clj-http.client :as http]
            [cheshire.core   :as json]))

(def ^:dynamic username      "ak-test-2nOJTWkRcJWTIjGJAQ6DG2eGSgXEXk5lf")
(def ^:dynamic password      "")
(def ^:dynamic accept-header "application/vnd.api+json;revision=1.1")

(defn get
  [path]
  (let [url (str/join "/" path)]
    (-> (http/get url
                  {:basic-auth [username password]
                   :accept     accept-header})
        :body
        json/parse-string)))

(defn post
  [path & {:keys [form-params]}]
  (let [url (str/join "/" path)]
    (-> (http/post url
                   {:basic-auth  [username password]
                    :accept      accept-header
                    :form-params form-params})
        :body
        json/parse-string)))

(defn put
  [path & {:keys [form-params]}]
  (let [url (str/join "/" path)]
    (-> (http/put url
                  {:basic-auth  [username password]
                   :accept      accept-header
                   :form-params form-params})
        :body
        json/parse-string)))

(defn delete
  [path]
  (let [url (str/join "/" path)]
    (http/delete url
                 {:basic-auth [username password]
                  :accept     accept-header})))

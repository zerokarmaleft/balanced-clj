(ns balanced-clj.client
  (:require [clj-http.client :as http]
            [cheshire.core   :as json]))

(def ^:dynamic api-url        "https://api.balancedpayments.com")
(def ^:dynamic api-version    1)
(def ^:dynamic client-agent   "balanced-clj")
(def ^:dynamic client-version "0.0.1")

(def ^:dynamic username       "ak-test-2nOJTWkRcJWTIjGJAQ6DG2eGSgXEXk5lf")
(def ^:dynamic password       "")
(def ^:dynamic accept-header  "application/vnd.api+json;revision=1.1")

(defn get
  [url]
  (-> (http/get url
                {:basic-auth [username password]
                 :accept     accept-header})
      :body
      json/parse-string))

(defn post
  [url & {:keys [form-params]}]
  (-> (http/post url
                 {:basic-auth  [username password]
                  :accept      accept-header
                  :form-params form-params})
      :body
      json/parse-string))

(defn put
  [url & {:keys [form-params]}]
  (-> (http/put url
                {:basic-auth  [username password]
                 :accept      accept-header
                 :form-params form-params})
      :body
      json/parse-string))

(defn delete
  [url]
  (http/delete url
               {:basic-auth [username password]
                :accept     accept-header}))

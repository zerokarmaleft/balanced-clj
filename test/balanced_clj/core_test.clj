(ns balanced-clj.core-test
  (:require [clojure.test      :refer :all]
            [vcr-clj.clj-http  :refer [with-cassette]]
            [balanced-clj.core :refer :all]))

;; ===========================================================================
;; API Keys
;; ===========================================================================
(deftest test-api-keys
  (with-cassette :create-api-key do
    (let [api-key (-> (create-api-key)
                      (get "api_keys")
                      first)]
      (is (not-empty api-key))
      (is (= #{"links" "created_at" "secret" "href" "meta" "id"}
             (set (keys api-key))))
      (is (.startsWith (api-key "secret") "ak-test-"))
      (is (.startsWith (api-key "href")   "/api_keys")))))

(deftest test-fetch-api-key
  (with-cassette :fetch-api-key do
    (let [api-key (-> (fetch-api-key "AK4b6Elv2iAqJRpu9lD7H53a")
                      (get "api_keys")
                      first)]
      (is (not-empty api-key))
      (is (= #{"href" "created_at" "meta" "id" "links"}
             (set (keys api-key)))))))

(deftest test-list-api-keys
  (with-cassette :list-api-keys do
    (let [api-keys (list-api-keys)]
      (is (not-empty api-keys))
      (is (= #{"api_keys" "links" "meta"}
             (set (keys api-keys)))))))

(deftest test-delete-api-key
  (with-cassette :delete-api-key do
    (let [resp (delete-api-key "AK4b6Elv2iAqJRpu9lD7H53a")]
      (is (not-empty resp))
      (is (= 204 (:status resp))))))

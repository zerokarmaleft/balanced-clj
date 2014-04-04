(ns balanced-clj.core-test
  (:require [clojure.test      :refer :all]
            [vcr-clj.clj-http  :refer [with-cassette]]
            [balanced-clj.core :refer :all]))

;; ===========================================================================
;; API Keys
;; ===========================================================================
(deftest test-api-keys
  (with-cassette :create-api-key do
    (let [[api-key _] (:api_keys (create-api-key))]
      (is (not-empty api-key))
      (is (= #{:links :created_at :secret :href :meta :id}
             (set (keys api-key))))
      (is (.startsWith (:secret api-key) "ak-test-"))
      (is (.startsWith (:href api-key)   "/api_keys")))))

(deftest test-fetch-api-key
  (with-cassette :fetch-api-key do
    (let [[new-api-key _] (:api_keys (create-api-key))
          [api-key _]     (:api_keys (fetch-api-key (:id new-api-key)))]
      (is (not-empty api-key))
      (is (= #{:href :created_at :meta :id :links}
             (set (keys api-key))))
      (is (= (dissoc new-api-key :secret) api-key)))))

(deftest test-list-api-keys
  (with-cassette :list-api-keys do
    (let [api-keys (list-api-keys)]
      (is (not-empty api-keys))
      (is (= #{:api_keys :links :meta}
             (set (keys api-keys))))
      (is (= 3 (count api-keys))))))

(deftest test-delete-api-key
  (with-cassette :delete-api-key do
    (let [[api-key _] (:api_keys (create-api-key))
          resp        (delete-api-key (:id api-key))]
      (is (not-empty resp))
      (is (= 204 (:status resp))))))

;; ===========================================================================
;; Customers
;; ===========================================================================
(deftest test-create-customer
  (with-cassette :create-customer do
    (let [[customer _] (:customers (create-customer))]
      (is (not-empty customer))
      (is (= #{:id :name :ssn_last4 :dob_year :dob_month
               :email :phone :address
               :business_name :ein :merchant_status
               :meta :href :links
               :created_at :updated_at}
             (set (keys customer)))))))

(deftest test-fetch-customer
  (with-cassette :fetch-customer do
    (let [[new-customer _] (:customers (create-customer))
          [customer _]     (:customers (fetch-customer (:id new-customer)))]
      (is (not-empty customer))
      (is (= #{:id :name :ssn_last4 :dob_year :dob_month
               :email :phone :address
               :business_name :ein :merchant_status
               :meta :href :links
               :created_at :updated_at}
             (set (keys customer))))
      (is (= (:id new-customer) (:id customer))))))

(deftest test-list-customers
  (with-cassette :list-customers do
    (let [customers (list-customers)]
      (is (not-empty customers))
      (is (= #{:customers :meta :links}
             (set (keys customers))))
      (is (= 6 (count (:customers customers)))))))

(deftest test-update-customer
  (with-cassette :update-customer do
    (let [[customer _]       (:customers (create-customer))
          [updated-customer] (:customers (update-customer (:id customer)
                                                          {:address
                                                           {:city  "Tulsa"
                                                            :state "OK"}}))]
      (is (= (:id customer) (:id updated-customer)))
      (is (= "Tulsa" (get-in updated-customer [:address :city])))
      (is (= "OK"    (get-in updated-customer [:address :state]))))))

(deftest test-delete-customer
  (with-cassette :delete-customer do
    (let [[customer _] (:customers (create-customer))
          resp         (delete-customer (:id customer))]
      (is (not-empty resp))
      (is (= 204 (:status resp))))))

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

;; ===========================================================================
;; Customers
;; ===========================================================================
(deftest test-create-customer
  (with-cassette :create-customer do
    (let [customers    (create-customer {})
          customer (first (customers "customers"))]
      (is (not-empty customers))
      (is (= #{"customers" "links"}
             (set (keys customers))))
      (is (not-empty customer))
      (is (= #{"id" "name" "ssn_last4" "dob_year" "dob_month"
               "email" "phone" "address"
               "business_name" "ein" "merchant_status"
               "meta" "href" "links"
               "created_at" "updated_at"}
             (set (keys customer)))))))

(deftest test-fetch-customer
  (with-cassette :fetch-customer do
    (let [id        "CU4D1bVOYwIoZiyYRIRNp82c"
          customers (fetch-customer id)
          customer  (first (customers "customers"))]
      (is (not-empty customers))
      (is (= #{"customers" "links"}
             (set (keys customers))))
      (is (not-empty customer))
      (is (= #{"id" "name" "ssn_last4" "dob_year" "dob_month"
               "email" "phone" "address"
               "business_name" "ein" "merchant_status"
               "meta" "href" "links"
               "created_at" "updated_at"}
             (set (keys customer))))
      (is (= id (customer "id"))))))

(deftest test-list-customers
  (with-cassette :list-customers do
    (let [customers (list-customers)]
      (is (not-empty customers))
      (is (= #{"customers" "meta" "links"}
             (set (keys customers))))
      (is (= 6 (count (customers "customers")))))))

(deftest test-update-customer
  (with-cassette :update-customer do
    (let [id        "CU4D1bVOYwIoZiyYRIRNp82c"
          customers (update-customer id {"address" {"city"  "Tulsa"
                                                    "state" "OK"}})
          customer  (first (customers "customers"))]
      (is (not-empty customers))
      (is (= #{"customers" "links"}
             (set (keys customers))))
      (is (not-empty customer))
      (is (= #{"id" "name" "ssn_last4" "dob_year" "dob_month"
               "email" "phone" "address"
               "business_name" "ein" "merchant_status"
               "meta" "href" "links"
               "created_at" "updated_at"}
             (set (keys customer))))
      (is (= id (customer "id")))
      (is (= "Tulsa" (get-in customer ["address" "city"])))
      (is (= "OK"    (get-in customer ["address" "state"]))))))

(deftest test-delete-customer
  (with-cassette :delete-customer do
    (let [id   "CU4D1bVOYwIoZiyYRIRNp82c"
          resp (delete-customer id)]
      (is (not-empty resp))
      (is (= 204 (:status resp))))))

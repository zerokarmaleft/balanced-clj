(ns balanced-clj.core
  (:require [balanced-clj.client :as balanced]
            [clojure.string      :as str]))

(def ^:dynamic *api-url* "https://api.balancedpayments.com")

;; ===========================================================================
;; API Keys
;; ===========================================================================
(defn create-api-key
  []
  (let [url (str/join "/" [*api-url* "api_keys" ])]
    (balanced/post url)))

(defn fetch-api-key
  [api-key-id]
  (let [url (str/join "/" [*api-url* "api_keys" api-key-id])]
    (balanced/get url)))

(defn list-api-keys
  []
  (let [url (str/join "/" [*api-url* "api_keys"])]
    (balanced/get url)))

(defn delete-api-key
  [api-key-id]
  (let [url (str/join "/" [*api-url* "api_keys" api-key-id])]
    (balanced/delete url)))

;; ===========================================================================
;; Customers
;; ===========================================================================
(defn create-customer
  [customer]
  (let [url (str/join "/" [*api-url* "customers"])]
    (balanced/post url :form-params customer)))

(defn fetch-customer
  [customer-id]
  (let [url (str/join "/" [*api-url* "customers" customer-id])]
    (balanced/get url)))

(defn list-customers
  []
  (let [url (str/join "/" [*api-url* "customers"])]
    (balanced/get url)))

(defn update-customer
  [customer-id customer]
  (let [url (str/join "/" [*api-url* "customers" customer-id])]
    (balanced/put url :form-params customer)))

(defn delete-customer
  [customer-id]
  (let [url (str/join "/" [*api-url* "customers" customer-id])]
    (balanced/delete url)))

;; ===========================================================================
;; Orders
;; ===========================================================================
(defn create-order
  [customer-id & {:keys [delivery-address description meta]
                  :as   order}]
  (let [url (str/join "/" [*api-url* "customers" customer-id "orders"])]
    (balanced/post url :form-params order)))

(defn fetch-order
  [order-id]
  (let [url (str/join "/" [*api-url* "orders" order-id])]
    (balanced/get url)))

(defn list-orders
  []
  (let [url (str/join "/" [*api-url* "orders"])]
    (balanced/get url)))

(defn update-order
  [order-id & {:keys [description meta]
               :as   order}]
  (let [url (str/join "/" [*api-url* "orders" order-id])]
    (balanced/put url :form-params order)))

;; ===========================================================================
;; Bank Accounts
;; ===========================================================================
(defn fetch-bank-account
  [bank-account-id]
  (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id])]
    (balanced/get url)))

(defn list-bank-accounts
  []
  (let [url (str/join "/" [*api-url* "bank_accounts"])]
    (balanced/get url)))

(defn update-bank-account
  [bank-account-id bank-account]
  (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id])]
    (balanced/put url :form-params bank-account)))

(defn delete-bank-account
  [bank-account-id]
  (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id])]
    (balanced/delete url)))

(defn debit-bank-account
  [bank-account-id & {:keys [amount appears-on-statement-as description meta order source]
                      :as   debit}]
  (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id "debits"])]
    (balanced/post url :form-params debit)))

(defn credit-bank-account
  [bank-account-id
   & {:keys [amount appears-on-statement-as destination order]
      :as   credit}]
  (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id "credits"])]
    (balanced/put url :form-params credit)))

(defn associate-bank-account
  ""
  [bank-account-id bank-account]
  (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id])]
    (balanced/put url :form-params bank-account)))

;; ===========================================================================
;; Bank Account Verifications
;; ===========================================================================
(defn create-bank-account-verification
  [bank-account-id]
  (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id "verifications"])]
    (balanced/post url)))

(defn fetch-bank-acccount-verification
  [verification-id]
  (let [url (str/join "/" [*api-url* "verifications" verification-id])]
    (balanced/get url)))

(defn confirm-bank-account-verification
  [verification-id & {:keys [amount-1 amount-2]
                      :as   amounts}]
  (let [url (str/join "/" [*api-url* "verifications" verification-id])]
    (balanced/put url :form-params amounts)))

;; ===========================================================================
;; Cards
;; ===========================================================================
(defn fetch-card
  [card-id]
  (let [url (str/join "/" [*api-url* "cards" card-id])]
    (balanced/get url)))

(defn list-cards
  []
  (let [url (str/join "/" (*api-url* "cards"))]
    (balanced/get url)))

(defn update-card
  [card-id & card]
  (let [url (str/join "/" [*api-url* "cards" card-id])]
    (balanced/put url :form-params card)))

(defn delete-card
  [card-id]
  (let [url (str/join "/" [*api-url* "cards" card-id])]
    (balanced/delete url)))

(defn debit-card
  [card-id & {:keys [amount appears-on-statement-as description meta order source]
              :as   debit}]
  (let [url (str/join "/" [*api-url* "cards" card-id "debits"])]
    (balanced/post url :form-params debit)))

(defn associate-card
  [card-id card]
  (let [url (str/join "/" [*api-url* "cards" card-id])]
    (balanced/put url :form-params card)))

;; ===========================================================================
;; Card Holds
;; ===========================================================================
(defn create-card-hold
  [card-id & {:keys [amount description]
              :as   hold}]
  (let [url (str/join "/" [*api-url* "cards" card-id "card_holds"])]
    (balanced/post url :form-params hold)))

(defn fetch-card-hold
  [card-hold-id]
  (let [url (str/join "/" [*api-url* "card_holds" card-hold-id])]
    (balanced/get url)))

(defn list-card-holds
  []
  (let [url (str/join "/" [*api-url* "card_hods"])]
    (balanced/get url)))

(defn update-card-hold
  [card-hold-id & {:keys [description meta]
                   :as   card-hold-attrs}]
  (let [url (str/join "/" [*api-url* "card_holds" card-hold-id])]
    (balanced/put url card-hold-attrs)))

(defn capture-card-hold
  [card-hold-id
   & {:keys [amount appears-on-statement-as description meta order source]
      :as   debit}]
  (let [url (str/join "/" [*api-url* "card_holds" card-hold-id "debits"])]
    (balanced/post url :form-params debit)))

(defn void-card-hold
  [card-hold-id & {:keys [description meta]
                   :as   card-hold-attrs}]
  (let [url (str/join "/" [*api-url* "card_holds" card-hold-id])]
    (balanced/put url :form-params (assoc card-hold-attrs "is_void" "true"))))

;; ===========================================================================
;; Debits
;; ===========================================================================
(defn fetch-debit
  [debit-id]
  (let [url (str/join "/" [*api-url* "debits" debit-id])]
    (balanced/get url)))

(defn list-debits
  []
  (let [url (str/join "/" [*api-url* "debits"])]
    (balanced/get url)))

(defn update-debit
  [debit-id & {:keys [description meta]
               :as   debit-attrs}]
  (let [url (str/join "/" [*api-url* "debits" debit-id])]
    (balanced/put url :form-params debit-attrs)))

;; ===========================================================================
;; Credits
;; ===========================================================================
(defn fetch-credit
  [credit-id]
  (let [url (str/join "/" [*api-url* "credits" credit-id])]
    (balanced/get url)))

(defn list-credits
  ([]
    (let [url (str/join "/" [*api-url* "credits"])]
      (balanced/get url)))
  ([bank-account-id]
    (let [url (str/join "/" [*api-url* "bank_accounts" bank-account-id "credits"])]
      (balanced/get url))))

(defn update-credit
  [credit-id & {:keys [description meta]
                :as   credit-attrs}]
  (let [url (str/join "/" [*api-url* "credits" credit-id])]
    (balanced/put url :form-params credit-attrs)))

;; ===========================================================================
;; Refunds (for Debits)
;; ===========================================================================
(defn create-refund
  [debit-id & {:keys [amount description meta]
               :as   refund}]
  (let [url (str/join "/" [*api-url* "debits" debit-id "refunds"])]
    (balanced/post url :form-params refund)))

(defn fetch-refund
  [refund-id]
  (let [url (str/join "/" [*api-url* "refunds" refund-id])]
    (balanced/get url)))

(defn list-refunds
  []
  (let [url (str/join "/" [*api-url* "refunds"])]
    (balanced/get url)))

(defn update-refund
  [refund-id & {:keys [description meta]
                :as   refund-attrs}]
  (let [url (str/join "/" [*api-url* "refunds" refund-id])]
    (balanced/put url :form-params refund-attrs)))

;; ===========================================================================
;; Reversals (for Credits)
;; ===========================================================================
(defn create-reversal
  [credit-id & {:keys [amount description meta]
                :as   reversal}]
  (let [url (str/join "/" [*api-url* "credits" credit-id "reversals"])]
    (balanced/post url :form-params reversal)))

(defn fetch-reversal
  [reversal-id]
  (let [url (str/join "/" [*api-url* "reversals" reversal-id])]
    (balanced/get url)))

(defn list-reversals
  []
  (let [url (str/join "/" [*api-url* "reversals"])]
    (balanced/get url)))

(defn update-reversal
  [reversal-id & {:keys [description]
                  :as   reversal-attrs}]
  (let [url (str/join "/" [*api-url* "reversals" reversal-id])]
    (balanced/put url :form-params reversal-attrs)))

;; ===========================================================================
;; Events
;; ===========================================================================
(defn fetch-event
  [event-id]
  (let [url (str/join "/" [*api-url* "events" event-id])]
    (balanced/get url)))

(defn list-events
  []
  (let [url (str/join "/" [*api-url* "events"])]
    (balanced/get url)))

;; ===========================================================================
;; Callbacks (for Events)
;; ===========================================================================
(defn create-callback
  [& {:keys [url method]
      :as   callback}]
  (let [url (str/join "/" [*api-url* "callbacks"])]
    (balanced/post url :form-params callback)))

(defn fetch-callback
  [callback-id]
  (let [url (str/join "/" [*api-url* "callbacks" callback-id])]
    (balanced/get url)))

(defn list-callbacks
  []
  (let [url (str/join "/" [*api-url* "callbacks"])]
    (balanced/get url)))

(defn delete-callback
  [callback-id]
  (let [url (str/join "/" [*api-url* "callbacks" callback-id])]
    (balanced/delete url)))

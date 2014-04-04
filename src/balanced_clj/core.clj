(ns balanced-clj.core
  (:require [balanced-clj.client :as balanced]))

(def ^:dynamic *api-url* "https://api.balancedpayments.com")

;; ===========================================================================
;; API Keys
;; ===========================================================================
(defn create-api-key
  []
  (balanced/post [*api-url* "api_keys"]))

(defn fetch-api-key
  [api-key-id]
  (balanced/get [*api-url* "api_keys" api-key-id]))

(defn list-api-keys
  []
  (balanced/get [*api-url* "api_keys"]))

(defn delete-api-key
  [api-key-id]
  (balanced/delete [*api-url* "api_keys" api-key-id]))

;; ===========================================================================
;; Customers
;; ===========================================================================
(defn create-customer
  ([]
     (create-customer {}))
  ([customer]
     (balanced/post [*api-url* "customers"]
                    :form-params customer)))

(defn fetch-customer
  [customer-id]
  (balanced/get [*api-url* "customers" customer-id]))

(defn list-customers
  []
  (balanced/get [*api-url* "customers"]))

(defn update-customer
  ([customer-id]
     (update-customer customer-id {}))
  ([customer-id customer]
     (balanced/put [*api-url* "customers" customer-id]
                   :form-params customer)))

(defn delete-customer
  [customer-id]
  (balanced/delete [*api-url* "customers" customer-id]))

;; ===========================================================================
;; Orders
;; ===========================================================================
(defn create-order
  ([customer-id]
     (create-order customer-id {}))
  ([customer-id order]
     (balanced/post [*api-url* "customers" customer-id "orders"]
                    :form-params order)))

(defn fetch-order
  [order-id]
  (balanced/get [*api-url* "orders" order-id]))

(defn list-orders
  []
  (balanced/get [*api-url* "orders"]))

(defn update-order
  ([order-id]
     (update-order order-id {}))
  ([order-id order]
     (balanced/put [*api-url* "orders" order-id]
                   :form-params order)))

;; ===========================================================================
;; Bank Accounts
;; ===========================================================================
(defn fetch-bank-account
  [bank-account-id]
  (balanced/get [*api-url* "bank_accounts" bank-account-id]))

(defn list-bank-accounts
  []
  (balanced/get [*api-url* "bank_accounts"]))

(defn update-bank-account
  [bank-account-id bank-account]
  (balanced/put [*api-url* "bank_accounts" bank-account-id]
                :form-params bank-account))

(defn delete-bank-account
  [bank-account-id]
  (balanced/delete [*api-url* "bank_accounts" bank-account-id]))

(defn debit-bank-account
  [bank-account-id
   & {:keys [amount appears-on-statement-as description meta order source]
      :as   debit}]
  (balanced/post [*api-url* "bank_accounts" bank-account-id "debits"]
                 :form-params debit))

(defn credit-bank-account
  [bank-account-id
   & {:keys [amount appears-on-statement-as destination order]
      :as   credit}]
  (balanced/put [*api-url* "bank_accounts" bank-account-id "credits"]
                :form-params credit))

(defn associate-bank-account
  [bank-account-id bank-account]
  (balanced/put [*api-url* "bank_accounts" bank-account-id]
                :form-params bank-account))

;; ===========================================================================
;; Bank Account Verifications
;; ===========================================================================
(defn create-bank-account-verification
  [bank-account-id]
  (balanced/post [*api-url* "bank_accounts" bank-account-id "verifications"]))

(defn fetch-bank-acccount-verification
  [verification-id]
  (balanced/get [*api-url* "verifications" verification-id]))

(defn confirm-bank-account-verification
  [verification-id & {:keys [amount-1 amount-2]
                      :as   amounts}]
  (balanced/put [*api-url* "verifications" verification-id]
                :form-params amounts))

;; ===========================================================================
;; Cards
;; ===========================================================================
(defn fetch-card
  [card-id]
  (balanced/get [*api-url* "cards" card-id]))

(defn list-cards
  []
  (balanced/get [*api-url* "cards"]))

(defn update-card
  [card-id & card]
  (balanced/put [*api-url* "cards" card-id]
                :form-params card))

(defn delete-card
  [card-id]
  (balanced/delete [*api-url* "cards" card-id]))

(defn debit-card
  [card-id
   & {:keys [amount appears-on-statement-as description meta order source]
      :as   debit}]
  (balanced/post [*api-url* "cards" card-id "debits"]
                 :form-params debit))

(defn associate-card
  [card-id card]
  (balanced/put [*api-url* "cards" card-id]
                :form-params card))

;; ===========================================================================
;; Card Holds
;; ===========================================================================
(defn create-card-hold
  [card-id & {:keys [amount description]
              :as   hold}]
  (balanced/post [*api-url* "cards" card-id "card_holds"]
                 :form-params hold))

(defn fetch-card-hold
  [card-hold-id]
  (balanced/get [*api-url* "card_holds" card-hold-id]))

(defn list-card-holds
  []
  (balanced/get [*api-url* "card_hods"]))

(defn update-card-hold
  [card-hold-id & {:keys [description meta]
                   :as   card-hold-attrs}]
  (balanced/put [*api-url* "card_holds" card-hold-id]
                :form-params card-hold-attrs))

(defn capture-card-hold
  [card-hold-id
   & {:keys [amount appears-on-statement-as description meta order source]
      :as   debit}]
  (balanced/post [*api-url* "card_holds" card-hold-id "debits"]
                 :form-params debit))

(defn void-card-hold
  [card-hold-id & {:keys [description meta]
                   :as   card-hold-attrs}]
  (balanced/put [*api-url* "card_holds" card-hold-id]
                :form-params (assoc card-hold-attrs "is_void" "true")))

;; ===========================================================================
;; Debits
;; ===========================================================================
(defn fetch-debit
  [debit-id]
  (balanced/get [*api-url* "debits" debit-id]))

(defn list-debits
  []
  (balanced/get [*api-url* "debits"]))

(defn update-debit
  [debit-id & {:keys [description meta]
               :as   debit-attrs}]
  (balanced/put [*api-url* "debits" debit-id]
                :form-params debit-attrs))

;; ===========================================================================
;; Credits
;; ===========================================================================
(defn fetch-credit
  [credit-id]
  (balanced/get [*api-url* "credits" credit-id]))

(defn list-credits
  ([]
     (balanced/get [*api-url* "credits"]))
  ([bank-account-id]
     (balanced/get [*api-url* "bank_accounts" bank-account-id "credits"])))

(defn update-credit
  [credit-id & {:keys [description meta]
                :as   credit-attrs}]
  (balanced/put [*api-url* "credits" credit-id]
                :form-params credit-attrs))

;; ===========================================================================
;; Refunds (for Debits)
;; ===========================================================================
(defn create-refund
  [debit-id & {:keys [amount description meta]
               :as   refund}]
  (balanced/post [*api-url* "debits" debit-id "refunds"]
                 :form-params refund))

(defn fetch-refund
  [refund-id]
  (balanced/get [*api-url* "refunds" refund-id]))

(defn list-refunds
  []
  (balanced/get [*api-url* "refunds"]))

(defn update-refund
  [refund-id & {:keys [description meta]
                :as   refund-attrs}]
  (balanced/put [*api-url* "refunds" refund-id]
                :form-params refund-attrs))

;; ===========================================================================
;; Reversals (for Credits)
;; ===========================================================================
(defn create-reversal
  [credit-id & {:keys [amount description meta]
                :as   reversal}]
  (balanced/post [*api-url* "credits" credit-id "reversals"]
                 :form-params reversal))

(defn fetch-reversal
  [reversal-id]
  (balanced/get [*api-url* "reversals" reversal-id]))

(defn list-reversals
  []
  (balanced/get [*api-url* "reversals"]))

(defn update-reversal
  [reversal-id & {:keys [description]
                  :as   reversal-attrs}]
  (balanced/put [*api-url* "reversals" reversal-id]
                :form-params reversal-attrs))

;; ===========================================================================
;; Events
;; ===========================================================================
(defn fetch-event
  [event-id]
  (balanced/get [*api-url* "events" event-id]))

(defn list-events
  []
  (balanced/get [*api-url* "events"]))

;; ===========================================================================
;; Callbacks (for Events)
;; ===========================================================================
(defn create-callback
  [& {:keys [url method]
      :as   callback}]
  (balanced/post [*api-url* "callbacks"]
                 :form-params callback))

(defn fetch-callback
  [callback-id]
  (balanced/get [*api-url* "callbacks" callback-id]))

(defn list-callbacks
  []
  (balanced/get [*api-url* "callbacks"]))

(defn delete-callback
  [callback-id]
  (balanced/delete [*api-url* "callbacks" callback-id]))

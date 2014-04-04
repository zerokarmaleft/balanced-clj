(defproject balanced-clj "0.1.0-SNAPSHOT"
  :description "Clojure client API library for Balanced Payments"
  
  :url         "https://github.com/zerokarmaleft/balanced-clj"
  
  :license     {:name "MIT License"
                :url  "https://github.com/zerokarmaleft/balanced-clj/LICENSE"}

  :dependencies [[org.clojure/clojure     "1.6.0"]
                 [clj-http                "0.7.9"]
                 [cheshire                "5.2.0"]

                 [com.gfredericks/vcr-clj "0.3.3"]
                 [org.clojure/test.check  "0.5.7"]])

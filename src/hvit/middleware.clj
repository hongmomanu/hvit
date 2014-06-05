(ns hvit.middleware
  (:require [taoensso.timbre :as timbre]
            [selmer.parser :as parser]
            [noir.response :as resp]
            [clj-http.client :as client]
            [hvitmiddleware.core :as hvitmd]
            [environ.core :refer [env]]))

(defn log-request [handler]
  (if (env :dev)
    (fn [req]
      (timbre/debug req)
      (handler req))
    handler))

(defn session-filter [handler]
  (hvitmd/session-filter handler "http://localhost:3000")

  )

(defn template-error-page [handler]
  (if (env :dev)
    (fn [request]
      (try
        (handler request)
        (catch clojure.lang.ExceptionInfo ex
          (let [{:keys [type error-template] :as data} (ex-data ex)]
            (if (= :selmer-validation-error type)
              {:status 500
               :body (parser/render error-template data)}
              (throw ex))))))
    handler))

(ns hvit.controller.sqltest
  (:use compojure.core)

  (:require [hvit.models.db :as db]
            [noir.response :as resp]
            )
  )




(defn sqltest []
          ;(resp/json (db/postgres-test))
          (resp/json (db/sqlserver-test))
  )

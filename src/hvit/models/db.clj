(ns hvit.models.db
  (:use korma.core
        [korma.db :only [defdb with-db]])
  (:require [hvit.models.schema :as schema]))

(defdb db schema/db-spec)
(defdb sqlitedb schema/db-spec-sqlite)

(defdb postgresdb schema/db-postgres)

(defdb sqlserverdb schema/db-sqlserver)

(defentity users
  (database db)
  )

(defn create-user [user]
  (insert users
          (values user)))

(defn update-user [id first-name last-name email]
  (update users
  (set-fields {:first_name first-name
               :last_name last-name
               :email email})
  (where {:id id})))

(defn get-user [id]
  (first (select users
                 (where {:id id})
                 (limit 1))))

(defn h2db-test[]

  (with-db db
    (exec-raw ["SELECT 1 WHERE 1 = ? " [1]] :results))

  )

(defn sqlite-test []
  (with-db sqlitedb
    (exec-raw ["SELECT \"sqlite\" WHERE 1 = ? " [1]] :results)
    )

  )

(defn postgres-test[]

  (with-db postgresdb
    (exec-raw ["SELECT 1 WHERE 1 = ? " [1]] :results))
  )
(defn sqlserver-test[]

  (with-db sqlserverdb
    (exec-raw ["SELECT 2 WHERE 1 = ? " [1]] :results))
  )

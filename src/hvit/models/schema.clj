(ns hvit.models.schema
  (:require [clojure.java.jdbc :as sql]
            [noir.io :as io]))

(def db-store "site.db")
(def db-store-sqlite "sqlite.db3")

(def datapath (str (System/getProperty "user.dir") "/"))
;;h2嵌入式数据库
(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname (str datapath db-store)
              :user "sa"
              :password ""
              :make-pool? true
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})
;;sqlite 嵌入式数据库
(def db-spec-sqlite {:classname "org.sqlite.JDBC"
                     :subprotocol "sqlite"
                     :subname (str datapath db-store-sqlite)
                     })

;;oracle 连接
(def db-oracle  {:classname "oracle.jdbc.OracleDriver"
                 :subprotocol "oracle"
                 :subname "thin:@10.33.253.32:1521:orcl"
                 :user "sms"
                 :password "sms"
                 :naming {:keys clojure.string/lower-case :fields clojure.string/upper-case}})
;;mysql 连接
(def db-mysql {:subprotocol "mysql"
               ;;:subname "//127.0.0.1:9306?characterEncoding=utf8&maxAllowedPacket=512000"
               :subname "//10.33.5.103:3306/jopens"
               :user "root"
               :password "rootme"
               })
;;sqlserver 连接
(def db-sqlserver {:classname "net.sourceforge.jtds.jdbc.Driver"
                   :subprotocol "jtds:sqlserver"
                   :subname "//192.168.2.141:11630/haiyusql;instance=SQLEXPRESS;user=sa;password=hvit123!"
                   })

;;postgres 连接
(def db-postgres  {:classname "org.postgresql.Driver" ; must be in classpath
                   :subprotocol "postgresql"
                   :subname "//localhost:5432/postgres"
                   ; Any additional keys are passed to the driver
                   ; as driver-specific properties.
                   :user "postgres"
                   :password "shayu626"})




(defn initialized?
  "checks to see if the database schema is present"
  []
  (.exists (new java.io.File (str datapath db-store ".h2.db"))))

(defn create-users-table
  []
  (sql/db-do-commands
    db-spec
    (sql/create-table-ddl
      :users
      [:id "varchar(20) PRIMARY KEY"]
      [:first_name "varchar(30)"]
      [:last_name "varchar(30)"]
      [:email "varchar(30)"]
      [:admin :boolean]
      [:last_login :time]
      [:is_active :boolean]
      [:pass "varchar(100)"])))

(defn create-tables
  "creates the database tables used by the application"
  []
  (create-users-table))

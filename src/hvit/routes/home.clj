(ns hvit.routes.home
  (:use compojure.core)
  (:require [hvit.views.layout :as layout]
            [hvit.util :as util]
            [noir.response :as resp]
            [hvitmiddleware.core :as hvitmd]
            ))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/exceltest" [] (resp/json(hvitmd/make-excel "/home/jack/testexcel.xls" "[{\"name\":\"日誌內容\",\"value\":\"logcontent\",\"columns\":[],\"col\":[0,0],\"row\":[1,2]},{\"name\":\"序号\",\"value\":\"userid\",\"columns\":[],\"col\":[1,1],\"row\":[1,2]},{\"name\":\"操作用戶\",\"value\":\"username\",\"columns\":[],\"col\":[2,2],\"row\":[1,2]},{\"name\":\"操作時間\",\"value\":\"times\",\"columns\":[{\"name\":\"操作用戶\",\"value\":\"username\",\"columns\":[],\"col\":[3,3],\"row\":[2,2]},{\"name\":\"操作用戶\",\"value\":\"username\",\"columns\":[],\"col\":[4,4],\"row\":[2,2]}],\"col\":[3,4],\"row\":[1,1]}]" "[]" "{\"userid\":30}" "test" 2 5 true "http://112.124.50.195:8000/hvitpublic/auth/getlogs" "{\"rowsname\":\"rows\",\"totalname\":\"total\",\"limit\":\"10\",\"start\":\"0\",\"edtime\":\"\",\"bgtime\":\"\",\"keyword\":\"\"}" "rows" "[{\"name\":\"填表人:\",\"value\":\"\",\"row\":[1,2],\"col\":[1,2]},{\"name\":\"時間:\",\"value\":\"2015-02-01\",\"row\":[1,2],\"col\":[3,4]}]")))
  (GET "/about" [] (about-page)))

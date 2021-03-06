(ns castra-notify-chat.handler
  (:require
    [compojure.core :as c]
    [compojure.route :as route]
    [ring.middleware.defaults :as d]
    [ring.util.response :as response]
    [castra.middleware :as castra]))

(c/defroutes app-routes
  (c/GET "/" req (response/content-type
                   (response/resource-response "index.html")
                   "text/html"))
  (route/resources "/" {:root ""}))

(def app
(-> app-routes
    (castra/wrap-castra
      'castra-notify-chat.chat-api
      'castra-notify-chat.user-api
      'notify.notification-api)
    (castra/wrap-castra-session "a 16-byte secret")
    (d/wrap-defaults d/api-defaults)))

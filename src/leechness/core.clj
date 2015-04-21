(ns leechness.core
  (:require [net.cgrand.enlive-html :as el]
            [clj-http.client :as client]
            [clj-http.cookies :as monster]))

(defn get-url [url cookies]
  (client/get url {:cookie-store cookies}))

(defn post-url [url params cookies]
  (client/post url {:form-params params :cookie-store cookies}))

(defn select-item [attr value html]
  (:attrs (first (el/select
                  (el/html-snippet html)
                  [(el/attr= attr value)]))))

(defn ness [id password]
  (let [jar (monster/cookie-store)
        ness "https://ness.ncl.ac.uk"
        ncl-gateway "https://gateway.ncl.ac.uk/idp/Authn/UserPassword"]
    (get-url ness jar)
    (post-url ncl-gateway {:j_username id :j_password password :_eventId "submit" :submit "LOGIN"} jar)
    (let [body (:body (get-url ness jar))]
      (when-let [session-key (:value (select-item :name "SAMLResponse" body))]
        (let [redirect (:action (select-item :method "post" body))
              validation (post-url redirect {:SAMLResponse session-key} jar)
              response (get-url ness jar)]
          {:response response :cookies (monster/get-cookies jar)})))))

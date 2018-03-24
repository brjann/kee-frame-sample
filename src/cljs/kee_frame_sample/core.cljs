(ns kee-frame-sample.core
  (:require [cljsjs.material-ui]
            [day8.re-frame.http-fx]
            [re-frame.core :refer [subscribe]]
            [kee-frame.core :as k]
            [kee-frame-sample.controller.league]
            [kee-frame-sample.controller.team]
            [kee-frame-sample.controller.leagues]
            [kee-frame-sample.controller.live]
            [kee-frame-sample.controller.common]
            [kee-frame-sample.subscriptions]
            [kee-frame-sample.layout :as layout]
            [cljs.spec.alpha :as s]
            [kee-frame-sample.view.live :as live]
            [kee-frame-sample.view.team :as team]
            [kee-frame-sample.view.league :as league]))

(enable-console-print!)

(goog-define debug false)

(defn dispatch-main []
  [k/switch-route :handler
   :league [league/league-dispatch]
   :team [team/team]
   :live [live/live]
   nil [:div "Loading..."]])

(def routes ["" {"/"                       :live
                 ["/league/" :id "/" :tab] :league
                 ["/team/" :href]          :team}])

(def initial-db {:drawer-open?  false
                 :leagues       nil
                 :fixtures      nil
                 :table         nil
                 :live-matches  nil
                 :ongoing-only? false})

(s/def ::league (s/keys :req-un [::caption ::id]))
(s/def ::leagues (s/nilable (s/coll-of ::league)))
(s/def ::db-spec (s/keys :req-un [::drawer-open? ::leagues ::fixtures ::table ::live-matches ::ongoing-only?]))

(k/start! {:debug?         debug
           :routes         routes
           :initial-db     initial-db
           :root-component [layout/main-panel [dispatch-main]]
           :app-db-spec    ::db-spec})
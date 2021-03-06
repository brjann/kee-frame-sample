(ns kee-frame-sample.controller.common
  (:require [kee-frame.core :refer [reg-controller reg-event-fx reg-event-db]]))

(reg-event-db :toggle-drawer
              (fn [db [flag]]
                (assoc db :drawer-open? flag)))

(reg-controller :hide-drawer-on-navigate
                ;; Will make the controller restart on every route change
                {:params identity
                 :start  [:toggle-drawer false]})
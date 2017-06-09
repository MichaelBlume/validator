(ns validator.core
  (:require [validator.validate :refer [validate-edn]]
            [clojure.string :refer [trim]]
            [reagent.core :as reagent :refer [atom]]))

;;;

(defn atom-input [value]
  [:textarea {:type "text"
              :value @value
              :on-change #(reset! value (-> % .-target .-value))}])

(defn get-validate-result [s]
  (try
    (let [result (with-out-str (validate-edn s))]
      (if (seq (trim result))
        result
        "Looks like some valid EDN"))
    (catch js/Error e
      (.-message e))))

(defn app []
  (let [val (reagent/atom "foo")]
    (fn []
      [:div
       [atom-input val]   
       [:p (get-validate-result @val)]])))

(reagent/render-component 
  [app]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

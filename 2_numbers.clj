;; 2. Numbers
;; Checking Whether a String Is a Valid Number

(import '(java.text NumberFormat ParseException)
        '(java.util Locale))

(def locale Locale/US)

(defn nb [s]
  (let [nf (NumberFormat/getInstance locale)]
    (.parse nf s)))

; user=> (nb "100")
; 100
; user=> (nb "not a number")
; java.text.ParseException: Unparseable number: "not a number"

; (def s1 "100")
; (def s1 "not a number")
(try
  (Integer/parseInt s1)
  (catch NumberFormatException _ex
    (println (str s1 " is not an integer"))))

; (def s2 3.14)
; (def s2 "foo")
(try
  (Float/parseFloat s2)
  (catch NumberFormatException _ex
    (println (str s2 " is not a float"))))

(defn isNumeric [s]
  (let [nf (NumberFormat/getInstance locale)]
    (try
      (do
        (.parse nf s)
        true)
      (catch ParseException _ex false))))

;; Comparing Floating-Point Numbers
;;----------------------------------------------------------------------------------
; (equal NUM1 NUM2 ACCURACY) returns true if NUM1 and NUM2 are
; equal to ACCURACY number of decimal places
(defn equal [num1 num2 accuracy]
  (let [bigNum (fn [num]
                 (.setScale
                  (BigDecimal. num)
                  accuracy
                  BigDecimal/ROUND_DOWN))]
    (= (.compareTo
        (bigNum num1)
        (bigNum num2)) 0)))
;;----------------------------------------------------------------------------------
; with a scaling factor
(def wage (BigDecimal. "5.36"))
(def hours (BigDecimal. "40"))
(def week (.multiply wage hours))

(println (str "One week's wage is: $" week))
; One week's wage is: $214.40
;;----------------------------------------------------------------------------------

;; Rounding Floating-Point Numbers
;;----------------------------------------------------------------------------------
; (def unrounded ...)
; (def scale ...)
; (def roundingMode ...)
(def rounded (.setScale unrounded scale roundingMode)
;;----------------------------------------------------------------------------------
(def a (BigDecimal. "0.255"))
(def b (.setScale a 2 BigDecimal/ROUND_HALF_UP))
(println (str "Unrounded: " a))
(println (str "Rounded: " b))
;=> Unrounded: 0.255
;=> Rounded: 0.26
;;----------------------------------------------------------------------------------
; caution, Math.rint() rounds to the nearest integer!
(def a [3.3 3.5 3.7 -3.3])
(println "number\tint\tfloor\ceil")
(map (fn [x] (println (str (Math/rint x) "\t" (Math/floor x) "\t" (Math/ceil x)))) a)
;;  3.0     3.0     4.0
;;  4.0     3.0     4.0
;;  4.0     3.0     4.0
;; -3.0    -4.0    -3.0

;; Converting Between Binary and Decimal
;;----------------------------------------------------------------------------------
(def i (Integer/parseInt s 2))
;;----------------------------------------------------------------------------------
(def s (Integer/toString i 2))
;;----------------------------------------------------------------------------------
(def i (Integer/parseInt "0110110" 2)) ; i = 54
;;----------------------------------------------------------------------------------
(def s (Integer/toString 54 2)) ; s = 110110
;;----------------------------------------------------------------------------------

;; Operating on a Series of Integers
;;----------------------------------------------------------------------------------
(map (fun [i]
          ; i is set to every integer from X to Y inclusive
          )
     (range x (+ y 1)))

(map (fun [i]
          ; i is set to every integer from X to Y, stepsize = 7
          )
     (range x (+ y 1) 7))
;;----------------------------------------------------------------------------------
(apply println (cons "Infancy is:" (range 0 3)))
(apply println (cons "Toddling is:" (range 3 5)))
(apply println (cons "Childhood is:" (range 5 13)))
;; Infancy is: 0 1 2
;; Toddling is: 3 4
;; Childhood is: 5 6 7 8 9 10 11 12
;;----------------------------------------------------------------------------------

;; Working with Roman Numerals
;;----------------------------------------------------------------------------------
;; no roman module available
;;----------------------------------------------------------------------------------

;; Generating Random Numbers
;;----------------------------------------------------------------------------------
(def random (Random. ))
(def i (+ (.nextInt random (- y (+ x 1))) x))
;;----------------------------------------------------------------------------------
(def i (+ (.nextInt random 51) 25))
(println i)
;;----------------------------------------------------------------------------------

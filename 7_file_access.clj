;; FILE ACCESS
;; @@PLEAC@@_7.0 Introduction
;; -----------------------------
(ns pleac-section-7.0
  (:require [clojure.java.io :as io])
  (:import [java.io BufferedReader FileReader]))

;; The perl version contains an or die "Couldn't open filename: $!"
;; after the file open, but this isn't quite as necessary
;; in languages with exceptions, such as Clojure.
(defn print-blue-lines-in-file [filename]
  (with-open [rdr (io/reader filename)]
    (doseq [line (line-seq rdr)]
      (if (.contains line "blue")
        (println line)))))

;; => (print-blue-lines-in-file "/usr/local/widgets/data")
;; blue
;; blue's
;; bluebell
;; ...
;; -----------------------------
;; The Perl code shows how to use * to cast the STDIN file handle
;; to a scalar variable.  This is unnecessary in Clojure, the
;; stdin reader object is already bound to the name *in*, which
;; can be manipulated like any other symbol.
(def stdin-var *in*)
(mysub stdin-var logfile)
;; -----------------------------

;; Here's another way to do the blue line iterator.
(defn print-blue-lines-in-file [filename]
  (with-open [rdr (io/BufferedReader. (io/FileReader. filename))]
    (doseq [line (line-seq rdr)]
      (when (re-find #"blue" line)
        (printf "%s\n" line)))))

;; I'm checking on the Clojure group, but I don't know of a way to
;; specify the output strem of a print function other than temporarily
;; binding *out* to the desired output stream.  It would be
;; straightforward to implement such a function in Clojure that does
;; this.
(defn print-digital-lines-from-stdin []
  (doseq [line (line-seq (io/reader *in*))]      ; reads from *in*
    (when-not (re-find #"\d" line)
      (binding [*out* *err*]
        (println "No digit found.")))            ; writes to *err*
    (printf "Read: %s\n" line)))                 ; writes to *out*
;; -----------------------------
;; The Perl code shows how to assign a file handle to LOGFILE...
;; Use (io/writer "/tmp/log" :append true) if you wish to append to
;; existing file contents, like Perl's >>.  The example below is for
;; overwriting any existing file contents, like Perl's >.
(def log-file-handle (io/writer "/tmp/log"))
;; -----------------------------
(.close log-file-handle)
;; -----------------------------
;; ... but because unlike Perl, flow-control in Clojure can be
;; interupted with conditions and exceptions, it's a good idea to
;; use the with-open macro to ensure the file handle is closed.
(with-open [log-file-handle (io/writer "/tmp/log")]
  ;; do stuff with log-file-handle.  Log-file-handle will be closed when
  ;; evaluation of this expression terminates.
  )
;; -----------------------------

;; The with-out-str macro redirects everything written to *out*
;; into a string.
;; mbac: rewrite as a macro so calling is less awkward.
(defn append-stdout-to-file [f filename]
  (with-open [fh (io/writer filename :append true)]
    (.write fh (with-out-str (f)))))
;; => (append-stdout-to-file (fn [] (printf "foo bar baz\n")) "/tmp/log.txt")

;; Here is another way
(binding [*out* logfile]      ; switch to logfile for output inside body
  (printf "Countdown initiated...\n"))
(printf "You have 30 seconds to reach minimum safety distance.\n")
;; -----------------------------

;; @@PLEAC@@_7.1 Opening a File
;; -----------------------------
(ns pleac-section-7.1
  (:require [clojure.java.io :as io])
  (:import [java.io BufferedReader FileReader]))

;; open PATH for reading
(def source (io/reader path))

;; open PATH for writing
(def sink (io/writer path))

;; ----------------------------

;; The Perl code makes POSIX open for read and open for write calls
;; which aren't directly accessible from Clojure.
;; mbac: maybe find a UNIX/POSIX module?

;; -----------------------------

;; The Perl code shows how to open files through an object oriented
;; interface to contrast the file handle interface.  Clojure already uses
;; objects.

;; mbac: maybe we can show interesting file openers from contrib instead?

;; -----------------------------
;; sysopen(FILEHANDLE, $name, $flags)         or die "Can't open $name : $!";
;; sysopen(FILEHANDLE, $name, $flags, $perms) or die "Can't open $name : $!";
;; #-----------------------------
;; open(FH, "< $path")                                 or die $!;
;; sysopen(FH, $path, O_RDONLY)                        or die $!;
;; #-----------------------------
;; open(FH, "> $path")                                 or die $!;
;; sysopen(FH, $path, O_WRONLY|O_TRUNC|O_CREAT)        or die $!;
;; sysopen(FH, $path, O_WRONLY|O_TRUNC|O_CREAT, 0600)  or die $!;
;; #-----------------------------
;; sysopen(FH, $path, O_WRONLY|O_EXCL|O_CREAT)         or die $!;
;; sysopen(FH, $path, O_WRONLY|O_EXCL|O_CREAT, 0600)   or die $!;
;; #-----------------------------
;; open(FH, ">> $path")                                or die $!;
;; sysopen(FH, $path, O_WRONLY|O_APPEND|O_CREAT)       or die $!;
;; sysopen(FH, $path, O_WRONLY|O_APPEND|O_CREAT, 0600) or die $!;
;; #-----------------------------
;; sysopen(FH, $path, O_WRONLY|O_APPEND)               or die $!;
;; #-----------------------------
;; open(FH, "+< $path")                                or die $!;
;; sysopen(FH, $path, O_RDWR)                          or die $!;
;; #-----------------------------
;; sysopen(FH, $path, O_RDWR|O_CREAT)                  or die $!;
;; sysopen(FH, $path, O_RDWR|O_CREAT, 0600)            or die $!;
;; #-----------------------------
;; sysopen(FH, $path, O_RDWR|O_EXCL|O_CREAT)           or die $!;
;; sysopen(FH, $path, O_RDWR|O_EXCL|O_CREAT, 0600)     or die $!;
;; #-----------------------------
@@INCOMPLETE@@

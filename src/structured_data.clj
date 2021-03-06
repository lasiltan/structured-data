(ns structured-data)

(defn do-a-thing [x]
  (let [xx (+ x x)]
    (Math/pow xx xx)))

(defn spiff [v]
  (let [first (get v 0)
        third (get v 2)]
    (+ first third)))

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (let [[a b c] v]
    (+ a c)))

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [[p1 p2]]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (- x2 x1)))

(defn height [[p1 p2]]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (- y2 y1)))

(defn square? [[p1 p2]]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (= (- x2 x1) (- y2 y1))))

(defn area [[p1 p2]]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (* (- x2 x1) (- y2 y1))))

(defn contains-point? [[p1 p2] point]
  (let [[x1 y1] p1
        [x2 y2] p2
        [x y] point]
    (and (<= x1 x x2) (<= y1 y y2))))

(defn contains-rectangle? [outer [p1 p2]]
  (and (contains-point? outer p1) (contains-point? outer p2)))

(defn title-length [book]
  (count (:title book)))

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (> (author-count book) 1))

(defn add-author [book new-author]
  (let [authors (:authors book)]
    (assoc book :authors (conj authors new-author))))

(defn alive? [author]
  (not (contains? author :death-year)))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  ;(map second collection))
  (let [sec (fn [x] (get x 1))]
    (map sec collection)))

(defn titles [books]
  (map :title books))

(defn monotonic? [a-seq]
  (or (apply <= a-seq) (apply >= a-seq)))

(defn stars [n]
  (apply str (repeat n \*)))

(defn toggle [a-set elem]
  (cond
   (contains? a-set elem) (disj a-set elem)
   :else (conj a-set elem)))

(defn contains-duplicates? [a-seq]
  (let [count-orig (count a-seq)
       count-unique (count (set a-seq))]
    (not (= count-orig count-unique))))

(defn old-book->new-book [book]
  (assoc book :authors (set (:authors book))))

(defn has-author? [book author]
  (contains? (:authors book) author))

(defn authors [books]
  (apply clojure.set/union (map :authors books)))

(defn all-author-names [books]
  (set (map :name (authors books))))

(defn author->string [author]
  (let [name-string (:name author)
        birth-year (:birth-year author)
        death-year (:death-year author)
        year-string (cond
                       (contains? author :death-year) (str " " \( birth-year " "  \- " " death-year \))
                       (contains? author :birth-year) (str " " \( birth-year " " \-  " " \))
                       :else "")]
    (str name-string year-string)))

(defn authors->string [authors]
  (apply str (interpose ", " (map author->string authors))))

(defn book->string [book]
  (let [authors-string (authors->string (:authors book))
        title-string (:title book)]
    (apply str (interpose ", written by " [title-string authors-string]))))

(defn books->string [books]
  (let [book-count (count books)
        book-count-string (cond
                             (= 0 book-count) "No books."
                             (= 1 book-count) "1 book. "
                             :else (str book-count " books. "))
        books-string (apply str (interpose ". " (map book->string books)))
        trailing-dot-string (if (> book-count 0) "." "")]
    (str book-count-string books-string trailing-dot-string)))

(defn books-by-author [author books]
  (filter (fn [x] (has-author? x author)) books))

(defn author-by-name [name authors]
  (let [matching (filter (fn [author] (= (:name author) name)) authors)
        matches? (> (count matching) 0)]
    (if matches? (first matching) nil)))

(defn living-authors [authors]
  (filter alive? authors))

(defn has-a-living-author? [book]
  (not (empty? (living-authors (:authors book)))))

(defn books-by-living-authors [books]
  (filter has-a-living-author? books))

; %________%

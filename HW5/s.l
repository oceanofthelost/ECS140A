;

;Part 1: The Double Functions
(defun double-recursive (L)
  (cond ((not (listp L)) nil)
	((null L) nil)
	((cons (car L) (cons (car L) (double-recursive (cdr L)))))))

(defun double-iterative (y)
  (cond ((not (listp y)) nil)
	(t (do ((cpy  y (cdr cpy))
		(a nil (append a (list (car cpy) (car cpy)))))
	       ((null cpy) a)))))

(defun double-mapcar (y)
  (cond ((not (listp y)) nil)
	(t (apply 'append (mapcar (lambda (x) (list x x))  y)))))

;Part 2: The Recursive Double Function
(defun rdouble (y)
  (cond ((not (listp y)) nil)
	((null y) nil)
	((listp (car y)) (cons (rdouble (car y))     (rdouble (cdr y))))
	(t               (cons (car y) (cons (car y) (rdouble (cdr y)))))))

;Part 3: The my-mapcar Function
(defun my-mapcar (fun l1 l2)
  (cond ((or (null l1) (null l2)) nil)
	(t (cons (funcall fun (car l1) (car l2)) 
		 (my-mapcar fun (cdr l1) (cdr l2))))))

;Part 4: The Rewrite Function
(defun rewrite (x)
  (cond ((not (listp x)) x)
	((null x) nil)
	((eq 'if (car x))
	 (cond ((null (cdddr x)) (list 'cond (list (rewrite(cadr x)) (rewrite(caddr x)))))
	       (t (cons 'cond (list (list (rewrite(cadr x)) (rewrite(caddr x))) 
				    (list 't (rewrite(cadddr x))))))))
	((listp (car x))  (cons (rewrite(car x)) (rewrite(cdr x))))
	(t                (cons (car x) (rewrite(cdr x))))))

;Part 5: The Check Function
(defun check (x)
  (list (equal (eval x) (eval (rewrite x))) 
	(eval x) 
	(eval (rewrite x))))

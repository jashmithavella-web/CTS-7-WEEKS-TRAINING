-- Exercise 1: Control Structures (SQL)
-- Topic: IF/ELSE, CASE, WHILE-style loop (via WHILE), basic stored procedure usage
-- 
-- Works best on MySQL 8+ (because it supports stored procedures with WHILE + IF).
-- If you use another DB, tell me which one and I’ll tailor it.

-- 1) Setup demo tables
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
  customer_id INT PRIMARY KEY AUTO_INCREMENT,
  customer_name VARCHAR(100) NOT NULL,
  country VARCHAR(50) NOT NULL
);

CREATE TABLE payments (
  payment_id INT PRIMARY KEY AUTO_INCREMENT,
  customer_id INT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  payment_date DATE NOT NULL,
  CONSTRAINT fk_payments_customer
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- 2) Insert sample data
INSERT INTO customers(customer_name, country) VALUES
('Asha', 'India'),
('Rahul', 'India'),
('Mia', 'USA'),
('Chen', 'China');

INSERT INTO payments(customer_id, amount, payment_status, payment_date) VALUES
(1, 50.00,  'PENDING', '2026-06-01'),
(1, 150.00, 'SUCCESS', '2026-06-10'),
(2, 80.00,  'FAILED',  '2026-06-11'),
(3, 220.00, 'SUCCESS', '2026-06-12'),
(4, 30.00,  'PENDING', '2026-06-13');

-- 3) CASE expression demo (no stored procedure needed)
--    Categorize each payment amount.

SELECT
  p.payment_id,
  c.customer_name,
  p.amount,
  CASE
    WHEN p.amount >= 200 THEN 'HIGH'
    WHEN p.amount >= 100 THEN 'MEDIUM'
    ELSE 'LOW'
  END AS amount_category,
  p.payment_status
FROM payments p
JOIN customers c ON c.customer_id = p.customer_id
ORDER BY p.payment_id;

-- 4) IF/ELSE demo using a simple stored procedure
--    The procedure classifies a single payment by updating its status.
--
-- Logic:
--   IF amount >= 200 THEN status = 'SUCCESS'
--   ELSE IF amount >= 100 THEN status = 'REVIEW'
--   ELSE status = 'PENDING'

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_update_payment_status $$
CREATE PROCEDURE sp_update_payment_status(IN p_payment_id INT)
BEGIN
  DECLARE v_amount DECIMAL(10,2);

  -- Get amount for the payment
  SELECT amount INTO v_amount
  FROM payments
  WHERE payment_id = p_payment_id;

  -- Use IF/ELSEIF/ELSE
  IF v_amount >= 200 THEN
    UPDATE payments
    SET payment_status = 'SUCCESS'
    WHERE payment_id = p_payment_id;

  ELSEIF v_amount >= 100 THEN
    UPDATE payments
    SET payment_status = 'REVIEW'
    WHERE payment_id = p_payment_id;

  ELSE
    UPDATE payments
    SET payment_status = 'PENDING'
    WHERE payment_id = p_payment_id;
  END IF;
END $$

DELIMITER ;

-- Test the stored procedure
SELECT * FROM payments ORDER BY payment_id;

CALL sp_update_payment_status(1);
CALL sp_update_payment_status(3);
CALL sp_update_payment_status(4);

SELECT * FROM payments ORDER BY payment_id;

-- 5) WHILE loop demo inside a stored procedure
--    Goal: Iterate over all payments and
--          count how many payments are LOW/MEDIUM/HIGH
--    (loop over payment IDs).

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_count_amount_buckets $$
CREATE PROCEDURE sp_count_amount_buckets()
BEGIN
  DECLARE v_done INT DEFAULT 0;
  DECLARE v_payment_id INT;
  DECLARE v_amount DECIMAL(10,2);

  DECLARE v_low INT DEFAULT 0;
  DECLARE v_medium INT DEFAULT 0;
  DECLARE v_high INT DEFAULT 0;

  -- Cursor to fetch payment rows one by one
  DECLARE cur CURSOR FOR
    SELECT payment_id, amount
    FROM payments
    ORDER BY payment_id;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

  OPEN cur;

  read_loop: LOOP
    FETCH cur INTO v_payment_id, v_amount;

    IF v_done = 1 THEN
      LEAVE read_loop;
    END IF;

    -- Bucketize using IF (control structure)
    IF v_amount >= 200 THEN
      SET v_high = v_high + 1;
    ELSEIF v_amount >= 100 THEN
      SET v_medium = v_medium + 1;
    ELSE
      SET v_low = v_low + 1;
    END IF;

  END LOOP;

  CLOSE cur;

  -- Return result
  SELECT
    v_low AS low_count,
    v_medium AS medium_count,
    v_high AS high_count;
END $$

DELIMITER ;

-- Run the loop-based procedure
CALL sp_count_amount_buckets();

-- 6) CASE demo inside an aggregate query
--    Count payments by category for each customer.

SELECT
  c.customer_name,
  COUNT(*) AS total_payments,
  SUM(CASE WHEN p.amount >= 200 THEN 1 ELSE 0 END) AS high_amounts,
  SUM(CASE WHEN p.amount >= 100 AND p.amount < 200 THEN 1 ELSE 0 END) AS medium_amounts,
  SUM(CASE WHEN p.amount < 100 THEN 1 ELSE 0 END) AS low_amounts
FROM payments p
JOIN customers c ON c.customer_id = p.customer_id
GROUP BY c.customer_name
ORDER BY total_payments DESC;


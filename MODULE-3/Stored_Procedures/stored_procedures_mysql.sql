-- Exercise 3: Stored Procedures (MySQL 8+)
-- Topic: Stored procedures with IF/ELSE and WHILE loops + test calls

-- 1) Setup demo tables
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
  customer_id INT AUTO_INCREMENT,
  customer_name VARCHAR(100) NOT NULL,
  country VARCHAR(50) NOT NULL,
  PRIMARY KEY (customer_id)
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
(1,  50.00,  'PENDING', '2026-06-01'),
(1, 150.00,  'PENDING', '2026-06-10'),
(2,  80.00,  'FAILED',  '2026-06-11'),
(3, 220.00,  'SUCCESS', '2026-06-12'),
(4,  30.00,  'PENDING', '2026-06-13');

-- 3) Stored Procedure 1: Update a single payment status using IF/ELSE
--    Logic:
--      amount >= 200 -> SUCCESS
--      amount >= 100 -> REVIEW
--      else           -> PENDING

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_update_payment_status $$
CREATE PROCEDURE sp_update_payment_status(IN p_payment_id INT)
BEGIN
  DECLARE v_amount DECIMAL(10,2);

  -- Get amount for the payment
  SELECT amount INTO v_amount
  FROM payments
  WHERE payment_id = p_payment_id;

  -- Update payment_status based on IF/ELSE
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

-- Test sp_update_payment_status
SELECT payment_id, amount, payment_status FROM payments ORDER BY payment_id;
CALL sp_update_payment_status(1);
CALL sp_update_payment_status(3);
CALL sp_update_payment_status(4);
SELECT payment_id, amount, payment_status FROM payments ORDER BY payment_id;

-- 4) Stored Procedure 2: Loop through payments and return bucket counts
--    LOW: amount < 100
--    MEDIUM: 100 <= amount < 200
--    HIGH: amount >= 200

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

  -- Cursor to fetch rows one by one
  DECLARE cur CURSOR FOR
    SELECT payment_id, amount
    FROM payments
    ORDER BY payment_id;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

  OPEN cur;

  read_loop: WHILE v_done = 0 DO
    FETCH cur INTO v_payment_id, v_amount;

    IF v_done = 1 THEN
      LEAVE read_loop;
    END IF;

    -- Bucketize using IF/ELSE
    IF v_amount >= 200 THEN
      SET v_high = v_high + 1;
    ELSEIF v_amount >= 100 THEN
      SET v_medium = v_medium + 1;
    ELSE
      SET v_low = v_low + 1;
    END IF;
  END WHILE;

  CLOSE cur;

  -- Return result
  SELECT v_low AS low_count,
         v_medium AS medium_count,
         v_high AS high_count;
END $$
DELIMITER ;

-- Test sp_count_amount_buckets
CALL sp_count_amount_buckets();

-- 5) Extra: View results in a single query (CASE expression)
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


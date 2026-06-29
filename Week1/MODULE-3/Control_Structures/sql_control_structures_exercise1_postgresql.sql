-- Exercise 1: Control Structures (SQL) - PostgreSQL version
-- Topic: CASE, IF/ELSE, loop/iteration using PL/pgSQL (WHILE) inside DO blocks
--
-- PostgreSQL supports stored procedures/functions using PL/pgSQL.

-- 1) Setup demo tables
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
  customer_id SERIAL PRIMARY KEY,
  customer_name VARCHAR(100) NOT NULL,
  country VARCHAR(50) NOT NULL
);

CREATE TABLE payments (
  payment_id SERIAL PRIMARY KEY,
  customer_id INT NOT NULL REFERENCES customers(customer_id),
  amount NUMERIC(10,2) NOT NULL,
  payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  payment_date DATE NOT NULL
);

-- 2) Insert sample data
INSERT INTO customers(customer_name, country) VALUES
('Asha', 'India'),
('Rahul', 'India'),
('Mia', 'USA'),
('Chen', 'China');

INSERT INTO payments(customer_id, amount, payment_status, payment_date) VALUES
(1,  50.00, 'PENDING', '2026-06-01'),
(1, 150.00, 'SUCCESS', '2026-06-10'),
(2,  80.00, 'FAILED',  '2026-06-11'),
(3, 220.00, 'SUCCESS', '2026-06-12'),
(4,  30.00, 'PENDING', '2026-06-13');

-- 3) CASE expression demo
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

-- 4) IF/ELSE demo using a PostgreSQL function
--    Classify a single payment by updating its status.

CREATE OR REPLACE FUNCTION sp_update_payment_status(p_payment_id INT)
RETURNS VOID
LANGUAGE plpgsql
AS $$
DECLARE
  v_amount NUMERIC(10,2);
BEGIN
  SELECT amount INTO v_amount
  FROM payments
  WHERE payment_id = p_payment_id;

  IF v_amount >= 200 THEN
    UPDATE payments SET payment_status = 'SUCCESS' WHERE payment_id = p_payment_id;
  ELSIF v_amount >= 100 THEN
    UPDATE payments SET payment_status = 'REVIEW' WHERE payment_id = p_payment_id;
  ELSE
    UPDATE payments SET payment_status = 'PENDING' WHERE payment_id = p_payment_id;
  END IF;
END;
$$;

-- Test function
SELECT * FROM payments ORDER BY payment_id;
SELECT sp_update_payment_status(1);
SELECT sp_update_payment_status(3);
SELECT sp_update_payment_status(4);
SELECT * FROM payments ORDER BY payment_id;

-- 5) WHILE loop demo
--    Count LOW/MEDIUM/HIGH payments using a WHILE loop.

CREATE OR REPLACE FUNCTION sp_count_amount_buckets()
RETURNS TABLE(low_count INT, medium_count INT, high_count INT)
LANGUAGE plpgsql
AS $$
DECLARE
  v_low INT := 0;
  v_medium INT := 0;
  v_high INT := 0;

  v_ids INT[];
  v_idx INT := 1;
  v_amount NUMERIC(10,2);
BEGIN
  SELECT ARRAY_AGG(payment_id ORDER BY payment_id) INTO v_ids
  FROM payments;

  WHILE v_idx <= COALESCE(array_length(v_ids,1),0) LOOP
    SELECT amount INTO v_amount
    FROM payments
    WHERE payment_id = v_ids[v_idx];

    IF v_amount >= 200 THEN
      v_high := v_high + 1;
    ELSIF v_amount >= 100 THEN
      v_medium := v_medium + 1;
    ELSE
      v_low := v_low + 1;
    END IF;

    v_idx := v_idx + 1;
  END LOOP;

  low_count := v_low;
  medium_count := v_medium;
  high_count := v_high;
  RETURN NEXT;
END;
$$;

SELECT * FROM sp_count_amount_buckets();

-- 6) CASE inside aggregate query
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


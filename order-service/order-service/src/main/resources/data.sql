-- ORDERS TABLE

INSERT INTO orders (id, order_status, price) VALUES
(1, 'PENDING', 159998.0),
(2, 'SHIPPED', 74999.0),
(3, 'DELIVERED', 29999.0),
(4, 'CANCELLED', 42999.0),
(5, 'PENDING', 19998.0),
(6, 'DELIVERED', 114999.0),
(7, 'SHIPPED', 65999.0),
(8, 'PENDING', 17999.0),
(9, 'DELIVERED', 8999.0),
(10, 'SHIPPED', 5499.0);


-- ORDER ITEMS TABLE (owning side)

INSERT INTO order_item (product_id, quantity, order_id) VALUES
-- Order 1
(1, 2, 1),
(7, 1, 1),

-- Order 2
(2, 1, 2),

-- Order 3
(11, 1, 3),

-- Order 4
(16, 1, 4),

-- Order 5
(13, 2, 5),
(18, 1, 5),

-- Order 6
(7, 1, 6),

-- Order 7
(15, 1, 7),

-- Order 8
(17, 1, 8),

-- Order 9
(18, 1, 9),

-- Order 10
(20, 1, 10);
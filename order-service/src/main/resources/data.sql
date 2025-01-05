INSERT INTO orders(total_price, order_status) VALUES
(100.50, 'PENDING'),
(130.50, 'PENDING'),
(100.50, 'PENDING'),
(170.50, 'CANCELLED'),
(180.50, 'CANCELLED'),
(110.50, 'CANCELLED'),
(130.50, 'CONFIRMED'),
(130.50, 'CONFIRMED'),
(180.50, 'CONFIRMED');

INSERT INTO order_item(order_id, product_id, quantity) VALUES
(1,101,2),
(1,102,2),
(2,103,2),
(2,104,2),
(3,105,2),
(3,106,2),
(4,107,2),
(4,108,2);




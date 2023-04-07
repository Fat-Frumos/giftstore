CREATE TABLE orders
(
    order_id   SERIAL PRIMARY KEY,
    order_date TIMESTAMP,
    user_id    INTEGER REFERENCES users (user_id),
    amount     DECIMAL(10, 2)
);

CREATE DATABASE shopping_cart_localization;

\c shopping_cart_localization;

CREATE TABLE cart_records (
    id SERIAL PRIMARY KEY,
    total_items INT NOT NULL,
    total_cost DOUBLE PRECISION NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart_items (
    id SERIAL PRIMARY KEY,
    cart_record_id INT REFERENCES cart_records(id) ON DELETE CASCADE,
    item_number INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity INT NOT NULL,
    subtotal DOUBLE PRECISION NOT NULL
);

CREATE TABLE localization_strings (
    id SERIAL PRIMARY KEY,
    key VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL
);

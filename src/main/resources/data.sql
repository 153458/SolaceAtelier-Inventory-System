-- This runs automatically on startup to populate the warehouse
INSERT INTO product (name, brand, price, stock, created_at) VALUES 
('Heavyweight Hoodie', 'Solace', 85.00, 50, CURRENT_TIMESTAMP),
('Cargo Pants', 'Solace', 110.00, 30, CURRENT_TIMESTAMP),
('Logo Tee', 'Solace', 40.00, 100, CURRENT_TIMESTAMP),
('Denim Jacket', 'Levi', 150.00, 20, CURRENT_TIMESTAMP);
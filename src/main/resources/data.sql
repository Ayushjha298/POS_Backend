---- Insert users
--INSERT INTO users (id, name, email, password, role) VALUES
--(1, 'Admin User', 'admin@example.com', 'admin123', 'ADMIN'),
--(2, 'John Doe', 'john@example.com', 'password', 'CUSTOMER'),
--(3, 'Jane Smith', 'jane@example.com', 'password', 'CUSTOMER');
--
---- Insert stores
--INSERT INTO stores (id, store_name, location, contact_number) VALUES
--(1, 'Downtown Pizza', 'Downtown City', '9876543210'),
--(2, 'Uptown Pizza', 'Uptown Area', '9123456780');
--
---- Insert food items
--INSERT INTO food_items (id, name, description, price, store_id) VALUES
--(1, 'Margherita Pizza', 'Classic cheese and tomato pizza', 299.00, 1),
--(2, 'Pepperoni Pizza', 'Spicy pepperoni with cheese', 399.00, 1),
--(3, 'Veggie Supreme', 'Loaded with veggies', 349.00, 2),
--(4, 'BBQ Chicken Pizza', 'Smoky BBQ chicken and cheese', 449.00, 2);
--
---- Insert orders
--INSERT INTO orders (id, order_date, status, user_id) VALUES
--(1, NOW(), 'PLACED', 2),
--(2, NOW(), 'DELIVERED', 3);
--
---- Insert order items
--INSERT INTO order_items (id, quantity, total_price, order_id, food_item_id) VALUES
--(1, 2, 598.00, 1, 1),
--(2, 1, 399.00, 1, 2),
--(3, 1, 349.00, 2, 3);
-- 🧹 Clean tables before inserting (for development only)
TRUNCATE TABLE order_items CASCADE;
TRUNCATE TABLE orders CASCADE;
TRUNCATE TABLE food_items CASCADE;
TRUNCATE TABLE stores CASCADE;
TRUNCATE TABLE users CASCADE;

-- 👤 USERS (Admin + Customers)
INSERT INTO users (id, name, email, password, role)
VALUES
(1, 'Ayush', 'admin@gmail.com', '$2a$10$R4fRkK0QzZbYxM5ZPqFKe.y0GzXuzUIEpP8T1ifh/hJ8qVfLGp8gK', 'ROLE_ADMIN'),  -- password: admin123 (BCrypt)
(2, 'John Doe', 'john@gmail.com', '$2a$10$R4fRkK0QzZbYxM5ZPqFKe.y0GzXuzUIEpP8T1ifh/hJ8qVfLGp8gK', 'ROLE_USER'),
(3, 'Jane Smith', 'jane@gmail.com', '$2a$10$R4fRkK0QzZbYxM5ZPqFKe.y0GzXuzUIEpP8T1ifh/hJ8qVfLGp8gK', 'ROLE_USER');

-- 🏪 STORES
INSERT INTO stores (id, store_name, location, contact_number)
VALUES
(1, 'Domino''s', 'Delhi', '9999999999'),
(2, 'Pizza Hut', 'Mumbai', '8888888888'),
(3, 'La Pinoz Pizza', 'Bangalore', '7777777777');

-- 🍕 FOOD ITEMS
INSERT INTO food_items (id, name, description, price, store_id)
VALUES
(1, 'Cheese Burst Pizza', 'Loaded with cheese and crust', 499.99, 1),
(2, 'Veggie Paradise', 'Topped with onion, capsicum, tomato', 399.99, 1),
(3, 'Chicken Supreme', 'Delicious chicken and cheese', 599.99, 2),
(4, 'Margherita', 'Classic cheese and tomato pizza', 299.99, 3);

-- 📦 ORDERS
INSERT INTO orders (id, order_date, status, user_id)
VALUES
(1, NOW(), 'PLACED', 2),
(2, NOW(), 'DELIVERED', 3);

-- 🍽️ ORDER ITEMS
INSERT INTO order_items (id, quantity, total_price, order_id, food_item_id)
VALUES
(1, 2, 999.98, 1, 1),
(2, 1, 399.99, 1, 2),
(3, 3, 1799.97, 2, 3);

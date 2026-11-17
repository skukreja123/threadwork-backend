-- Threadwork: queries for your data. Run in psql:  psql threadwork -f queries.sql
-- base must be one of: tee | polo | drop | hoodie | wtop   (it picks the 3D shape)
-- section must be: Men | Women      price is in Rs.      color is a hex colour

-- 1) The 5 products are created automatically on first start (migration V2__products.sql).
--    To add another one:
-- INSERT INTO products (name, section, base, price, color, sleeves, active)
--   VALUES ('Sweatshirt','Men','hoodie',2800,'#222222',false,true);

-- 2) CHECK WHAT YOU HAVE
SELECT id, name, section, base, price, color, sleeves, active FROM products ORDER BY created_at;

-- The parts below are examples. Remove the leading -- from the one you need.

-- 3) CHANGE A PRICE
-- UPDATE products SET price = 1400 WHERE name = 'T-shirt';

-- 4) HIDE / SHOW A PRODUCT IN THE SHOP
-- UPDATE products SET active = false WHERE name = 'Hoodie';
-- UPDATE products SET active = true  WHERE name = 'Hoodie';

-- 5) DELETE A PRODUCT (old orders keep their data)
-- DELETE FROM products WHERE name = 'Hoodie';

-- 6) MAKE A USER AN ADMIN (they must sign up first)
-- UPDATE users SET role = 'admin' WHERE email = 'you@email.com';

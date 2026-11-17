-- Your real catalogue: 4 men's pieces and 1 women's top. Runs once, and only if the table is empty.
-- Edit prices and colours here before the first start, or change them later in the admin portal.
INSERT INTO products (name, section, base, price, color, sleeves, active)
SELECT * FROM (VALUES
  ('T-shirt',           'Men',   'tee',    1200, '#1b1b1f', true,  true),
  ('Polo shirt',        'Men',   'polo',   1800, '#2e7d5b', true,  true),
  ('Drop shoulder tee', 'Men',   'drop',   1600, '#c0392b', true,  true),
  ('Hoodie',            'Men',   'hoodie', 3200, '#2b59ff', false, true),
  ('Women’s top',       'Women', 'wtop',   1500, '#d96aa7', false, true)
) AS v(name, section, base, price, color, sleeves, active)
WHERE NOT EXISTS (SELECT 1 FROM products);

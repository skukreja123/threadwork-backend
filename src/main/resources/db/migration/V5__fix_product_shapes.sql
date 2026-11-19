-- Products saved with the default shape (tee) whose name shows they are another piece.
UPDATE products SET base = 'hoodie', sleeves = false WHERE base = 'tee' AND name ~* '(hood|sweat)';
UPDATE products SET base = 'polo' WHERE base = 'tee' AND name ~* 'polo';
UPDATE products SET base = 'drop' WHERE base = 'tee' AND name ~* 'drop';
UPDATE products SET base = 'wtop', sleeves = false WHERE base = 'tee' AND section = 'Women' AND name ~* '(top|crop)';

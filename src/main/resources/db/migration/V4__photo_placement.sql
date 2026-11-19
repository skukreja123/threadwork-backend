ALTER TABLE order_designs ADD COLUMN photo_x real, ADD COLUMN photo_y real;   -- placement on the real product photo, in % of the photo
ALTER TABLE orders ADD COLUMN product_image_url text;                         -- photo of the product at order time

CREATE TABLE users (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name text NOT NULL, email text NOT NULL UNIQUE, password_hash text NOT NULL,
  role text NOT NULL DEFAULT 'customer' CHECK (role IN ('customer','staff','admin')),
  plan text NOT NULL DEFAULT 'free', ai_used int NOT NULL DEFAULT 0,
  created_at timestamptz NOT NULL DEFAULT now());
CREATE TABLE products (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name text NOT NULL, section text NOT NULL CHECK (section IN ('Men','Women')),
  base text NOT NULL CHECK (base IN ('tee','polo','drop','hoodie','wtop')),
  price int NOT NULL CHECK (price >= 0), color text NOT NULL DEFAULT '#1b1b1f',
  sleeves boolean NOT NULL DEFAULT true, active boolean NOT NULL DEFAULT true,
  created_at timestamptz NOT NULL DEFAULT now());
CREATE TABLE orders (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(), code text NOT NULL UNIQUE,
  user_id uuid REFERENCES users(id) ON DELETE SET NULL,
  product_id uuid REFERENCES products(id) ON DELETE SET NULL,
  product_name text NOT NULL, product_base text NOT NULL,      -- snapshot, so old orders survive product edits
  color text NOT NULL, sleeve text NOT NULL, size text NOT NULL,
  qty int NOT NULL CHECK (qty > 0), total int NOT NULL,
  status text NOT NULL DEFAULT 'new' CHECK (status IN ('new','in_production','shipped','delivered','cancelled')),
  customer_name text NOT NULL, customer_phone text NOT NULL, customer_address text NOT NULL, customer_note text,
  created_at timestamptz NOT NULL DEFAULT now());
CREATE TABLE order_designs (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  order_id uuid NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  side text NOT NULL CHECK (side IN ('front','back')), zone text NOT NULL,
  method text NOT NULL CHECK (method IN ('print','embroidery')),
  x real NOT NULL, y real NOT NULL, size real NOT NULL,
  image_url text NOT NULL, preview_url text);
CREATE TABLE messages (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name text NOT NULL, email text NOT NULL, body text NOT NULL, created_at timestamptz NOT NULL DEFAULT now());
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_created ON orders(created_at DESC);
CREATE INDEX idx_designs_order ON order_designs(order_id);

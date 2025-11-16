CREATE TABLE orders (
  id uuid PRIMARY KEY,
  tenant_id varchar NOT NULL,
  customer_id varchar,
  status varchar,
  total_amount numeric(12,2),
  currency varchar(3),
  items jsonb,
  idempotency_key varchar(255),
  created_at timestamptz DEFAULT now(),
  updated_at timestamptz DEFAULT now()
);
CREATE INDEX idx_orders_tenant ON orders (tenant_id);
CREATE UNIQUE INDEX ux_orders_idempotency_tenant ON orders (tenant_id, idempotency_key) WHERE idempotency_key IS NOT NULL;

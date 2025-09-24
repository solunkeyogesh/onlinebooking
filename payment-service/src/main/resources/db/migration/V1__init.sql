CREATE TABLE IF NOT EXISTS payment_txn (
  payment_id   VARCHAR(64) PRIMARY KEY,
  order_id     VARCHAR(64) NOT NULL,
  status       VARCHAR(32) NOT NULL,
  amount       DECIMAL(19,2) NOT NULL,
  currency     VARCHAR(8) NOT NULL,
  provider     VARCHAR(16) NOT NULL,
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_payment_order_id ON payment_txn(order_id);

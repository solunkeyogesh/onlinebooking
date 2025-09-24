CREATE TABLE IF NOT EXISTS sku (
  sku_code     VARCHAR(64) PRIMARY KEY,
  name         VARCHAR(255),
  on_hand      DECIMAL(19,2) NOT NULL DEFAULT 0,
  reserved     DECIMAL(19,2) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS stock_ledger (
  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
  sku_code     VARCHAR(64) NOT NULL,
  delta        DECIMAL(19,2) NOT NULL,
  order_id     VARCHAR(64),
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_ledger_sku (sku_code)
);

CREATE TABLE IF NOT EXISTS reservation (
  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id     VARCHAR(64) NOT NULL,
  sku_code     VARCHAR(64) NOT NULL,
  quantity     DECIMAL(19,2) NOT NULL,
  status       VARCHAR(32) NOT NULL,
  created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_res_order (order_id),
  INDEX idx_res_sku (sku_code)
);

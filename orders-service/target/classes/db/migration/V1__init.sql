CREATE TABLE IF NOT EXISTS order_hdr (
  order_id VARCHAR(64) PRIMARY KEY,
  status VARCHAR(32) NOT NULL,
  total_amount DECIMAL(19,2) NOT NULL,
  total_currency VARCHAR(8) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS order_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id VARCHAR(64) NOT NULL,
  sku_code VARCHAR(64) NOT NULL,
  name VARCHAR(255),
  quantity INT NOT NULL,
  unit_amount DECIMAL(19,2) NOT NULL,
  unit_currency VARCHAR(8) NOT NULL,
  CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES order_hdr(order_id)
);

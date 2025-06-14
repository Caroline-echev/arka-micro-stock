CREATE TABLE tb_inventory (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    country_id BIGINT NOT NULL,
    stock_actual INT NOT NULL,
    stock_minimo INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT unique_product_country UNIQUE(product_id, country_id)
);

CREATE TABLE tb_inventory_supplier (
    id BIGSERIAL PRIMARY KEY,
    inventory_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    delivery_time INT NOT NULL,
    FOREIGN KEY (inventory_id) REFERENCES tb_inventory(id),
    FOREIGN KEY (supplier_id) REFERENCES tb_suppliers(id)
);

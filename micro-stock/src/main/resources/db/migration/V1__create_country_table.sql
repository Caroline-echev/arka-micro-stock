CREATE TABLE tb_country (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    logistics_supervisor_id BIGINT,
    status VARCHAR(50)
);

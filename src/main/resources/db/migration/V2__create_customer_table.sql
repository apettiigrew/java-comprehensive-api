CREATE TABLE customers (
    uuid CHAR(36) NOT NULL primary key,
    username VARCHAR(50) not null,
    password VARCHAR(255) not null,
    enabled boolean not null,
    role VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Automatically set creation timestamp
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Automatically update timestamp
    deleted_at DATETIME NULL            -- Timestamp for soft deletion, nullable
);

CREATE INDEX idx_customers_deleted_at on customers(uuid, deleted_at)
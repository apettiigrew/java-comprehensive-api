CREATE TABLE users (
    uuid CHAR(36) NOT NULL PRIMARY KEY, -- UUID stored as a CHAR(36) for readability
    first_name VARCHAR(255) NOT NULL,   -- First name, required
    last_name VARCHAR(255) NOT NULL,    -- Last name, required
    birth_date DATE NOT NULL,           -- Birthdate, required
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Automatically set creation timestamp
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Automatically update timestamp
    deleted_at DATETIME NULL            -- Timestamp for soft deletion, nullable
);

CREATE INDEX idx_users_deleted_at on users(uuid, deleted_at)
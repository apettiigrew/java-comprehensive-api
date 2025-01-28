CREATE TABLE users (
    uuid CHAR(36) NOT NULL,
    username VARCHAR(50) not null primary key,
    password VARCHAR(255) not null,
    enabled boolean not null,
    role VARCHAR(50) NOT NULL,   -- First name, required
    first_name VARCHAR(255) NOT NULL,   -- First name, required
    last_name VARCHAR(255) NOT NULL,    -- Last name, required
    birth_date DATE NOT NULL,           -- Birthdate, required
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Automatically set creation timestamp
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Automatically update timestamp
    deleted_at DATETIME NULL            -- Timestamp for soft deletion, nullable
);

--create table authorities (
--	username VARCHAR(50) not null,
--	authority VARCHAR(50) not null,
--	constraint fk_authorities_users foreign key(username) references users(username)
--);
create unique index ix_auth_username on authorities (username,authority);
--CREATE INDEX idx_users_deleted_at on users(username, deleted_at)
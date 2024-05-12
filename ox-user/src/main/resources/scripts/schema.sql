ALTER TABLE IF EXISTS account
DROP CONSTRAINT IF EXISTS FK_account_users;

DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(255) NOT NULL CHECK (user_role IN ('ADMIN', 'USER')),
    bag_id uuid
);

CREATE TABLE account
(
    id BIGSERIAL PRIMARY KEY,
    balance NUMERIC(10, 2),
    user_id BIGINT REFERENCES users(id)
);

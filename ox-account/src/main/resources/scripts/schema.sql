DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    balance  DECIMAL(10, 2),
    username VARCHAR(255)
);

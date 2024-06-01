-- Schema for tests
DROP INDEX IF EXISTS idx_user_role_mapping;

DROP TABLE IF EXISTS user_entity;
DROP TABLE IF EXISTS keycloak_role;
DROP TABLE IF EXISTS user_role_mapping;
DROP TABLE IF EXISTS credential;

CREATE TABLE user_entity
(
    id                VARCHAR(36) PRIMARY KEY,
    email             VARCHAR(255) NOT NULL UNIQUE,
    email_constraint  VARCHAR(255) NOT NULL,
    email_verified    BOOLEAN,
    enabled           BOOLEAN,
    realm_id          VARCHAR(255) NOT NULL,
    username          VARCHAR(255) NOT NULL UNIQUE,
    created_timestamp BIGINT
);

CREATE TABLE keycloak_role
(
    id       VARCHAR(36) PRIMARY KEY,
    name     VARCHAR(255) NOT NULL UNIQUE,
    realm_id VARCHAR(255) NOT NULL
);

CREATE TABLE user_role_mapping
(
    role_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(36)  NOT NULL
        CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l
            REFERENCES user_entity,
    CONSTRAINT constraint_c
        PRIMARY KEY (role_id, user_id)
);

CREATE INDEX idx_user_role_mapping
    ON user_role_mapping (user_id);

ALTER TABLE user_role_mapping
    DROP CONSTRAINT IF EXISTS fk_c4fqv34p1mbylloxang7b1q3l;

CREATE TABLE credential
(
    id              VARCHAR(36) NOT NULL PRIMARY KEY,
    salt            BYTEA,
    type            VARCHAR(255),
    user_id         VARCHAR(36)
        CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0
            REFERENCES user_entity,
    created_date    BIGINT,
    user_label      VARCHAR(255),
    secret_data     TEXT,
    credential_data TEXT,
    priority        INTEGER
);

ALTER TABLE credential
    DROP CONSTRAINT IF EXISTS fk_pfyr0glasqyl0dei3kl69r6v0;

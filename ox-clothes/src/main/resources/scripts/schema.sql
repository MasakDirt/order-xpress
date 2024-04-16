ALTER TABLE socks_colors
    DROP CONSTRAINT FK_SOCKS_COLORS_SOCKS_ID;
ALTER TABLE outerwear_sizes
    DROP CONSTRAINT FK_OUTERWEAR_SIZES_OUTERWEAR_ID;
ALTER TABLE outerwear_colors
    DROP CONSTRAINT FK_OUTERWEAR_COLORS_OUTERWEAR_ID;

DROP TABLE IF EXISTS socks;
DROP TABLE IF EXISTS socks_colors;
DROP TABLE IF EXISTS outerwear;
DROP TABLE IF EXISTS outerwear_sizes;
DROP TABLE IF EXISTS outerwear_colors;

CREATE TABLE socks
(
    id               BIGINT AUTO_INCREMENT,
    product_name     VARCHAR(255)   NOT NULL UNIQUE,
    price            DECIMAL(19, 2) NOT NULL,
    sex              VARCHAR(255)   NOT NULL,
    description      VARCHAR(255)   NOT NULL,
    available_amount INT            NOT NULL,
    size             VARCHAR(255)   NOT NULL,
    PRIMARY KEY (id),
    CHECK (sex IN ('MALE', 'FEMALE', 'UNISEX')),
    CHECK (size IN ('XS', 'S', 'M', 'L', 'XL', 'XXL', 'ONE_SIZE'))
);

CREATE TABLE socks_colors
(
    socks_id BIGINT       NOT NULL,
    color    VARCHAR(255) NOT NULL,
    PRIMARY KEY (socks_id, color),
    CONSTRAINT FK_SOCKS_COLORS_SOCKS_ID FOREIGN KEY (socks_id) REFERENCES socks (id)
);


CREATE TABLE outerwear
(
    id               BIGINT AUTO_INCREMENT,
    product_name     VARCHAR(255)   NOT NULL UNIQUE,
    price            DECIMAL(19, 2) NOT NULL,
    sex              VARCHAR(255)   NOT NULL,
    description      VARCHAR(255)   NOT NULL,
    available_amount INT            NOT NULL,
    type             VARCHAR(255)   NOT NULL,
    PRIMARY KEY (id),
    CHECK (sex IN ('MALE', 'FEMALE', 'UNISEX')),
    CHECK (type IN ('T_SHIRT', 'PANTS', 'JACKET'))
);

CREATE TABLE outerwear_sizes
(
    outerwear_id BIGINT       NOT NULL,
    size         VARCHAR(255) NOT NULL,
    PRIMARY KEY (outerwear_id, size),
    CHECK (size IN ('XS', 'S', 'M', 'L', 'XL', 'XXL', 'ONE_SIZE')),
    CONSTRAINT FK_OUTERWEAR_SIZES_OUTERWEAR_ID FOREIGN KEY (outerwear_id) REFERENCES outerwear (id)
);

CREATE TABLE outerwear_colors
(
    outerwear_id BIGINT       NOT NULL,
    color        VARCHAR(255) NOT NULL,
    PRIMARY KEY (outerwear_id, color),
    CONSTRAINT FK_OUTERWEAR_COLORS_OUTERWEAR_ID FOREIGN KEY (outerwear_id) REFERENCES outerwear (id)
);
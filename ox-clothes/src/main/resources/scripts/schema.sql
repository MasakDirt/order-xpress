DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS clothes_sizes;
DROP TABLE IF EXISTS clothes_colors;


CREATE TABLE clothes
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
    CHECK (type IN ('T_SHIRT', 'PANTS', 'JACKET', 'SOCKS'))
);

CREATE TABLE clothes_sizes
(
    clothes_id BIGINT       NOT NULL,
    sizes      VARCHAR(255) NOT NULL,
    PRIMARY KEY (clothes_id, sizes),
    CHECK (sizes IN ('XS', 'S', 'M', 'L', 'XL', 'XXL', 'ONE_SIZE')),
    CONSTRAINT FK_CLOTHES_SIZES_CLOTHES_ID FOREIGN KEY (clothes_id) REFERENCES clothes (id)
);

CREATE TABLE clothes_colors
(
    clothes_id BIGINT       NOT NULL,
    colors     VARCHAR(255) NOT NULL,
    PRIMARY KEY (clothes_id, colors),
    CONSTRAINT FK_CLOTHES_COLORS_CLOTHES_ID FOREIGN KEY (clothes_id) REFERENCES clothes (id)
);

ALTER TABLE clothes_sizes
    DROP CONSTRAINT FK_CLOTHES_SIZES_CLOTHES_ID;
ALTER TABLE clothes_colors
    DROP CONSTRAINT FK_CLOTHES_COLORS_CLOTHES_ID;
INSERT INTO clothes (product_name, price, sex, description, available_amount, type)
VALUES ('Sock1', 1.50, 'MALE', 'Men\'s socks', 97, 'SOCKS'),
       ('Sock2', 2.00, 'MALE', 'Men\'s socks', 15, 'SOCKS'),
       ('Sock3', 4.99, 'MALE', 'Men\'s socks', 200, 'SOCKS'),
       ('Sock4', 6.99, 'MALE', 'Men\'s socks', 250, 'SOCKS'),
       ('Sock5', 9.00, 'MALE', 'Men\'s socks', 54, 'SOCKS'),
       ('Sock6', 2.45, 'MALE', 'Men\'s socks', 30, 'SOCKS'),
       ('Sock7', 4.12, 'MALE', 'Men\'s socks', 400, 'SOCKS'),
       ('Sock8', 1.56, 'FEMALE', 'Women\'s socks', 3, 'SOCKS'),
       ('Sock9', 2.89, 'FEMALE', 'Women\'s socks', 56, 'SOCKS'),
       ('Sock10', 4.05, 'FEMALE', 'Women\'s socks', 200, 'SOCKS'),
       ('Sock11', 6.00, 'FEMALE', 'Women\'s socks', 20, 'SOCKS'),
       ('Sock12', 8.23, 'FEMALE', 'Women\'s socks', 300, 'SOCKS'),
       ('Sock13', 2.66, 'FEMALE', 'Women\'s socks', 98, 'SOCKS'),
       ('Sock14', 9.78, 'FEMALE', 'Women\'s socks', 40, 'SOCKS'),
       ('Sock15', 1.99, 'UNISEX', 'Unisex socks', 3, 'SOCKS'),
       ('Sock16', 2.99, 'UNISEX', 'Unisex socks', 150, 'SOCKS'),
       ('Sock17', 4.99, 'UNISEX', 'Unisex socks', 20, 'SOCKS'),
       ('Sock18', 0.99, 'UNISEX', 'Unisex socks', 22, 'SOCKS'),
       ('Sock19', 1.99, 'UNISEX', 'Unisex socks', 30, 'SOCKS'),
       ('Sock20', 22.99, 'UNISEX', 'Unisex socks', 37, 'SOCKS'),
       ('Sock21', 2.34, 'UNISEX', 'Unisex socks', 40, 'SOCKS');

INSERT INTO clothes (product_name, price, sex, description, available_amount, type)
VALUES ('Male T-Shirt', 24.99, 'MALE', 'Men\'s T-shirt', 100, 'T_SHIRT'),
       ('Male Pants', 34.23, 'MALE', 'Men\'s Pants', 12, 'PANTS'),
       ('Male Jacket', 21.24, 'MALE', 'Men\'s Jacket', 87, 'JACKET'),
       ('Male T-Shirt With Smile', 21.34, 'MALE', 'Men\'s Smile T-shirt', 32, 'T_SHIRT'),
       ('Male Pants Fit', 16.67, 'MALE', 'Men\'s Fit Pants', 69, 'PANTS'),
       ('Male Jacket Heated', 75.89, 'MALE', 'Men\'s Heated Jacket', 35, 'JACKET'),
       ('Female T-Shirt', 24.99, 'FEMALE', 'Women\'s T-shirt', 100, 'T_SHIRT'),
       ('Female Pants', 39.99, 'FEMALE', 'Women\'s Pants', 55, 'PANTS'),
       ('Female Jacket', 5.59, 'FEMALE', 'Women\'s Jacket', 2, 'JACKET'),
       ('Female T-Shirt Slim', 2.78, 'FEMALE', 'Women\'s Slim T-shirt', 78, 'T_SHIRT'),
       ('Female Pants Long', 23.45, 'FEMALE', 'Women\'s Long Pants', 23, 'PANTS'),
       ('Female Jacket Heated', 9.66, 'FEMALE', 'Women\'s Heated Jacket', 90, 'JACKET'),
       ('Unisex T-Shirt', 24.99, 'UNISEX', 'Unisex T-shirt', 4546, 'T_SHIRT'),
       ('Unisex Pants', 7.99, 'UNISEX', 'Unisex Pants', 65, 'PANTS'),
       ('Unisex Jacket', 21.99, 'UNISEX', 'Unisex Jacket', 2, 'JACKET'),
       ('Unisex T-Shirt Shorten', 9.9, 'UNISEX', 'Unisex shorten T-shirt', 23, 'T_SHIRT'),
       ('Unisex Pants Drawing', 34.2, 'UNISEX', 'Unisex drawing Pants', 96, 'PANTS'),
       ('Unisex Jacket Solid', 7.83, 'UNISEX', 'Unisex solid Jacket', 37, 'JACKET');


INSERT INTO clothes_colors (clothes_id, colors)
SELECT 1, color
FROM (SELECT 'Red' AS color
      UNION ALL
      SELECT 'Blue'
      UNION ALL
      SELECT 'Green'
      UNION ALL
      SELECT 'Yellow') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 2, color
FROM (SELECT 'Black' AS color
      UNION ALL
      SELECT 'Gray'
      UNION ALL
      SELECT 'Pink') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 3, color
FROM (SELECT 'Orange' AS color
      UNION ALL
      SELECT 'Beige') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 4, color
FROM (SELECT 'Navy' AS color
      UNION ALL
      SELECT 'Turquoise'
      UNION ALL
      SELECT 'Maroon'
      UNION ALL
      SELECT 'Olive') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 5, color
FROM (SELECT 'Cyan' AS color
      UNION ALL
      SELECT 'Peach') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 6, color
FROM (SELECT 'Burgundy' AS color) AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 7, color
FROM (SELECT 'Sky Blue' AS color
      UNION ALL
      SELECT 'Charcoal'
      UNION ALL
      SELECT 'Lime Green'
      UNION ALL
      SELECT 'Indigo') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 8, color
FROM (SELECT 'Cream' AS color
      UNION ALL
      SELECT 'Salmon'
      UNION ALL
      SELECT 'Violet') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 9, color
FROM (SELECT 'Khaki' AS color
      UNION ALL
      SELECT 'Ruby') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 10, color
FROM (SELECT 'Aqua' AS color
      UNION ALL
      SELECT 'Pearl'
      UNION ALL
      SELECT 'Sienna'
      UNION ALL
      SELECT 'Amber') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 11, color
FROM (SELECT 'Silver' AS color
      UNION ALL
      SELECT 'Crimson'
      UNION ALL
      SELECT 'Denim') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 12, color
FROM (SELECT 'Plum' AS color
      UNION ALL
      SELECT 'Periwinkle'
      UNION ALL
      SELECT 'Fuchsia'
      UNION ALL
      SELECT 'Bisque') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 13, color
FROM (SELECT 'Copper' AS color
      UNION ALL
      SELECT 'Azure'
      UNION ALL
      SELECT 'Gold'
      UNION ALL
      SELECT 'Ivory') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 14, color
FROM (SELECT 'Cerulean' AS color
      UNION ALL
      SELECT 'Slate Gray'
      UNION ALL
      SELECT 'Ochre'
      UNION ALL
      SELECT 'Scarlet') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 15, color
FROM (SELECT 'Steel Blue' AS color
      UNION ALL
      SELECT 'Chartreuse'
      UNION ALL
      SELECT 'Rose') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 16, color
FROM (SELECT 'Cobalt' AS color) AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 17, color
FROM (SELECT 'Celadon' AS color) AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 18, color
FROM (SELECT 'Electric Blue' AS color
      UNION ALL
      SELECT 'Emerald') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 19, color
FROM (SELECT 'Mint Cream' AS color
      UNION ALL
      SELECT 'Ruby Red'
      UNION ALL
      SELECT 'Pumpkin') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 20, color
FROM (SELECT 'Honeydew' AS color
      UNION ALL
      SELECT 'Salmon Pink'
      UNION ALL
      SELECT 'Lavender Blue'
      UNION ALL
      SELECT 'Forest Green') AS colors;

INSERT INTO clothes_colors (clothes_id, colors)
SELECT 21, color
FROM (SELECT 'Medium Purple' AS color
      UNION ALL
      SELECT 'Powder Blue'
      UNION ALL
      SELECT 'Goldenrod') AS colors;


INSERT INTO clothes_colors (clothes_id, colors)
VALUES (22, 'Black'),
       (22, 'Blue'),

       (23, 'Gray'),
       (23, 'Brown'),
       (23, 'Beige'),

       (24, 'Navy'),
       (24, 'Green'),
       (24, 'Red'),
       (24, 'Indigo'),

       (25, 'Maroon'),

       (26, 'Green'),
       (26, 'Magenta'),
       (26, 'Purple'),

       (27, 'Teal'),
       (27, 'Navy'),

       (28, 'Pink'),
       (28, 'Yellow'),
       (28, 'Purple'),

       (29, 'Orange'),
       (29, 'Cyan'),
       (29, 'Magenta'),

       (30, 'Maroon'),
       (30, 'Teal'),

       (31, 'Turquoise'),
       (31, 'Olive'),

       (32, 'Green'),
       (32, 'Coral'),
       (32, 'Bronze'),

       (33, 'Green'),
       (33, 'Violet'),

       (34, 'Silver'),
       (34, 'Gold'),
       (34, 'Bronze'),

       (35, 'Indigo'),
       (35, 'Violet'),
       (35, 'Turquoise'),

       (36, 'Peach'),

       (37, 'Violet'),
       (37, 'Magenta'),
       (37, 'Orange'),

       (38, 'Green'),
       (38, 'Navy'),
       (38, 'Coral'),
       (38, 'Teal'),

       (39, 'Silver'),
       (39, 'Pink');

INSERT INTO clothes_sizes (clothes_id, sizes)
VALUES (1, 'M'),
       (1, 'L'),
       (1, 'XL'),

       (2, 'S'),
       (2, 'L'),

       (3, 'M'),
       (3, 'L'),
       (3, 'XL'),

       (4, 'S'),
       (4, 'XXL'),
       (4, 'XL'),

       (5, 'XS'),
       (5, 'S'),

       (6, 'ONE_SIZE'),

       (7, 'M'),
       (7, 'L'),

       (8, 'XS'),
       (8, 'S'),
       (8, 'M'),
       (8, 'XXL'),

       (9, 'ONE_SIZE'),

       (10, 'S'),
       (10, 'L'),
       (10, 'XXL'),

       (11, 'XL'),
       (11, 'S'),

       (12, 'ONE_SIZE'),

       (13, 'XS'),
       (13, 'S'),
       (13, 'M'),
       (13, 'L'),

       (14, 'XL'),
       (14, 'XXL'),

       (15, 'M'),
       (15, 'L'),

       (16, 'S'),
       (16, 'M'),
       (16, 'L'),

       (17, 'M'),
       (17, 'S'),
       (17, 'XS'),
       (17, 'XL'),

       (18, 'XS'),

       (19, 'M'),
       (19, 'L'),
       (19, 'XL'),

       (20, 'S'),
       (20, 'L'),

       (21, 'M'),
       (21, 'L'),
       (21, 'XL'),

       (22, 'S'),
       (22, 'XXL'),
       (22, 'XL'),

       (23, 'XS'),
       (23, 'S'),

       (24, 'ONE_SIZE'),

       (25, 'M'),
       (25, 'L'),

       (26, 'XS'),
       (26, 'S'),
       (26, 'M'),
       (26, 'XXL'),

       (27, 'ONE_SIZE'),

       (28, 'S'),
       (28, 'L'),
       (28, 'XXL'),

       (29, 'XL'),
       (29, 'S'),

       (30, 'ONE_SIZE'),

       (31, 'XS'),
       (31, 'S'),
       (31, 'M'),
       (31, 'L'),

       (32, 'XL'),
       (32, 'XXL'),

       (33, 'M'),
       (33, 'L'),

       (34, 'S'),
       (34, 'M'),
       (34, 'L'),

       (35, 'M'),
       (35, 'S'),
       (35, 'XS'),
       (35, 'XL'),

       (36, 'XS'),

       (37, 'M'),
       (37, 'L'),

       (38, 'S'),
       (38, 'M'),
       (38, 'L'),

       (39, 'M'),
       (39, 'S'),
       (39, 'XS'),
       (39, 'XL');

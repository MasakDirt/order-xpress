
INSERT INTO socks_colors (socks_id, colors)
SELECT 1, color
FROM (SELECT 'Red' AS color
      UNION ALL
      SELECT 'Blue'
      UNION ALL
      SELECT 'Green'
      UNION ALL
      SELECT 'Yellow') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 2, color
FROM (SELECT 'Black' AS color
      UNION ALL
      SELECT 'Gray'
      UNION ALL
      SELECT 'Pink') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 3, color
FROM (SELECT 'Orange' AS color
      UNION ALL
      SELECT 'Beige') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 4, color
FROM (SELECT 'Navy' AS color
      UNION ALL
      SELECT 'Turquoise'
      UNION ALL
      SELECT 'Maroon'
      UNION ALL
      SELECT 'Olive') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 5, color
FROM (SELECT 'Cyan' AS color
      UNION ALL
      SELECT 'Peach') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 6, color
FROM (SELECT 'Burgundy' AS color) AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 7, color
FROM (SELECT 'Sky Blue' AS color
      UNION ALL
      SELECT 'Charcoal'
      UNION ALL
      SELECT 'Lime Green'
      UNION ALL
      SELECT 'Indigo') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 8, color
FROM (SELECT 'Cream' AS color
      UNION ALL
      SELECT 'Salmon'
      UNION ALL
      SELECT 'Violet') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 9, color
FROM (SELECT 'Khaki' AS color
      UNION ALL
      SELECT 'Ruby') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 10, color
FROM (SELECT 'Aqua' AS color
      UNION ALL
      SELECT 'Pearl'
      UNION ALL
      SELECT 'Sienna'
      UNION ALL
      SELECT 'Amber') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 11, color
FROM (SELECT 'Silver' AS color
      UNION ALL
      SELECT 'Crimson'
      UNION ALL
      SELECT 'Denim') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 12, color
FROM (SELECT 'Plum' AS color
      UNION ALL
      SELECT 'Periwinkle'
      UNION ALL
      SELECT 'Fuchsia'
      UNION ALL
      SELECT 'Bisque') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 13, color
FROM (SELECT 'Copper' AS color
      UNION ALL
      SELECT 'Azure'
      UNION ALL
      SELECT 'Gold'
      UNION ALL
      SELECT 'Ivory') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 14, color
FROM (SELECT 'Cerulean' AS color
      UNION ALL
      SELECT 'Slate Gray'
      UNION ALL
      SELECT 'Ochre'
      UNION ALL
      SELECT 'Scarlet') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 15, color
FROM (SELECT 'Steel Blue' AS color
      UNION ALL
      SELECT 'Chartreuse'
      UNION ALL
      SELECT 'Rose') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 16, color
FROM (SELECT 'Cobalt' AS color)AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 17, color
FROM (SELECT 'Celadon' AS color) AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 18, color
FROM (SELECT 'Electric Blue' AS color
      UNION ALL
      SELECT 'Emerald') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 19, color
FROM (SELECT 'Mint Cream' AS color
      UNION ALL
      SELECT 'Ruby Red'
      UNION ALL
      SELECT 'Pumpkin') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 20, color
FROM (SELECT 'Honeydew' AS color
      UNION ALL
      SELECT 'Salmon Pink'
      UNION ALL
      SELECT 'Lavender Blue'
      UNION ALL
      SELECT 'Forest Green') AS colors;

INSERT INTO socks_colors (socks_id, colors)
SELECT 21, color
FROM (SELECT 'Medium Purple' AS color
      UNION ALL
      SELECT 'Powder Blue'
      UNION ALL
      SELECT 'Goldenrod') AS colors;


DROP TABLE IF EXISTS artists;

CREATE TABLE artists
(
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL UNIQUE
);


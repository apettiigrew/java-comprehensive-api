CREATE TABLE authorities (
  id INT NOT NULL AUTO_INCREMENT,
  user_uuid CHAR(36) NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  KEY user_uuid (user_uuid),
  CONSTRAINT authorities_ibfk_1 FOREIGN KEY (user_uuid) REFERENCES users (uuid)
);
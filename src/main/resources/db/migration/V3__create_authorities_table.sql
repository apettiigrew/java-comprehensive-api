CREATE TABLE authorities (
  id INT NOT NULL AUTO_INCREMENT,
  customer_uuid CHAR(36) NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  KEY customer_uuid (customer_uuid),
  CONSTRAINT authorities_ibfk_1 FOREIGN KEY (customer_uuid) REFERENCES customers (uuid)
);
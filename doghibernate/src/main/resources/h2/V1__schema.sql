-- DDL creation for DOG table
CREATE TABLE IF NOT EXISTS DOG (
  ID UUID PRIMARY KEY,
  NAME varchar2(100) NOT NULL,
  BIRTH_DATE DATE,
  HEIGHT INT NOT NULL,
  WEIGHT INT NOT NULL,
  HOUSE_NAME varchar2(100)
);


CREATE TABLE IF NOT EXISTS HOUSE (
  ID UUID PRIMARY KEY,
  NAME varchar2(100) NOT NULL,
);


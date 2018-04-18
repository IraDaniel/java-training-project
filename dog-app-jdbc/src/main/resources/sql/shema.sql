-- DDL creation for DOG table
CREATE TABLE IF NOT EXISTS DOG (
  ID UUID PRIMARY KEY,
  NAME varchar2(100) NOT NULL,
  BIRTH_DATE DATE,
  HEIGHT INT NOT NULL,
  WEIGHT INT NOT NULL,
);

insert into dog(id, name, birth_date, height, weight) values (random_uuid(), 'Dog1', '2017-03-11', 3, 3);
insert into dog(id, name, birth_date, height, weight) values (random_uuid(), 'Dog2', '2016-03-11', 4, 5);


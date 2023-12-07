DROP TABLE IF EXISTS examinations CASCADE;
CREATE TABLE examinations(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    type SMALLINT NOT NULL,
    short_description VARCHAR NOT NULL,
    long_description VARCHAR,
    cost NUMERIC(10,2) NOT NULL ,
    created_on TIMESTAMP,
    updated_on TIMESTAMP
);

DROP SEQUENCE IF EXISTS examinations_seq;
CREATE SEQUENCE examinations_seq START WITH 500 INCREMENT BY 1 NO MAXVALUE;
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    pesel VARCHAR(11) NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    role SMALLINT
);

DROP TABLE IF EXISTS diagnostic_results CASCADE;
CREATE TABLE diagnostic_results(
    id SERIAL PRIMARY KEY,
    type SMALLINT,
    registration TIMESTAMP,
    results_pdf BYTEA NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

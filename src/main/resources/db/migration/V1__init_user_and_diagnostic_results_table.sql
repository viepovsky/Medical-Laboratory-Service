drop table if exists users;
create table users(
    id SERIAL primary key,
    login varchar(30) not null,
    pesel varchar(30) not null,
    password varchar(255) not null,
    email varchar(100) not null,
    name varchar(30) not null,
    last_name varchar(30) not null,
    phone_number varchar(30),
    role smallint,
    created timestamp
);

drop table if exists diagnostic_results;
create table diagnostic_results(
    id SERIAL primary key,
    type smallint,
    registration timestamp,
    results_pdf oid,
    user_id bigint
);

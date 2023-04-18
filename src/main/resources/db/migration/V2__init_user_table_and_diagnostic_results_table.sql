drop table if exists users CASCADE;
create table users(
    id SERIAL primary key,
    login varchar not null,
    pesel varchar not null,
    password varchar not null,
    email varchar not null,
    name varchar not null,
    last_name varchar not null,
    phone_number varchar,
    role smallint,
    created timestamp
);

drop table if exists diagnostic_results CASCADE;
create table diagnostic_results(
    id SERIAL primary key,
    type smallint,
    registration timestamp,
    results_pdf oid,
    user_id bigint,
    foreign key (id) references users (id)
);

drop table if exists examinations CASCADE;
create table examinations(
    id SERIAL primary key,
    name varchar not null ,
    type smallint not null ,
    short_description varchar not null ,
    long_description varchar,
    cost numeric not null,
    created_on timestamp,
    updated_on timestamp
);

drop sequence if exists examinations_seq;
create sequence examinations_seq start with 500 increment by 1 no maxvalue;
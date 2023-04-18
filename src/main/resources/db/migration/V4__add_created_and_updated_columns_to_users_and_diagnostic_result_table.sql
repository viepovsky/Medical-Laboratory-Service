alter table users add column created_on timestamp null;
alter table users add column updated_on timestamp null;

alter table diagnostic_results add column created_on timestamp;
alter table diagnostic_results add column updated_on timestamp;

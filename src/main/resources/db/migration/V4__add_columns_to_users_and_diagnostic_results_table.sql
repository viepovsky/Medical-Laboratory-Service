ALTER TABLE users ADD COLUMN created_on TIMESTAMP NULL;
ALTER TABLE users ADD COLUMN updated_on TIMESTAMP NULL;

ALTER TABLE diagnostic_results ADD COLUMN created_on TIMESTAMP;
ALTER TABLE diagnostic_results ADD COLUMN updated_on TIMESTAMP;

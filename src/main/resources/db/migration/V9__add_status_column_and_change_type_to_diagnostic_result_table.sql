ALTER TABLE diagnostic_results ADD COLUMN status varchar null;
ALTER TABLE diagnostic_results ALTER COLUMN type SET DATA TYPE varchar;


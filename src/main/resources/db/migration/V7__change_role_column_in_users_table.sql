ALTER TABLE users ALTER COLUMN role SET DATA TYPE varchar(255) USING CASE role WHEN 0 THEN 'USER' ELSE 'ADMIN' END;

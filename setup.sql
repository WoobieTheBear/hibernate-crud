DROP DATABASE IF EXISTS tutorial_db;
CREATE DATABASE tutorial_db;
\connect tutorial_db;

DROP ROLE IF EXISTS tutorial_user;
-- [INFO]: this user and password is referenced in ./crud/src/main/resources/application.properties
CREATE ROLE tutorial_user WITH PASSWORD 'th3-P455word+f0r-Connection';
ALTER ROLE tutorial_user WITH LOGIN;
GRANT CREATE, DROP ON DATABASE tutorial_db TO tutorial_user;

--
-- Setup tables for app `crud`
--

DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR (63) NOT NULL,
  last_name VARCHAR (63) NOT NULL,
  email VARCHAR (255) UNIQUE NOT NULL,
  company VARCHAR (255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  last_login TIMESTAMP
);

-- Grant necessary permissions for JDBC to access table
GRANT INSERT, UPDATE, SELECT, DELETE, TRUNCATE ON person TO tutorial_user;

-- Configure the primary key sequence for person_id_seq
GRANT USAGE, SELECT ON SEQUENCE person_id_seq TO tutorial_user;

-- Following line does not work now anymore cause JDBC dictates the start
ALTER SEQUENCE IF EXISTS person_id_seq RESTART WITH 10000;

-- Database: runkeeper

-- DROP DATABASE model;

CREATE DATABASE runkeeper
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE runkeeper
    IS 'Database for the runkeeper and nevex application ';

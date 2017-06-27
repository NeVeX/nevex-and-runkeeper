-- Table: nevex.runkeeper_users

-- DROP TABLE nevex.runkeeper_users;

CREATE TABLE nevex.runkeeper_users
(
    id integer NOT NULL,
    user_id integer NOT NULL,
    created_date timestamp with time zone NOT NULL,
    name character varying NOT NULL,
    location character varying,
    gender character varying,
    birthday date,
    sign_up_date date NOT NULL,
    athlete_type character varying,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE nevex.runkeeper_users
    OWNER to postgres;
COMMENT ON TABLE nevex.runkeeper_users
    IS 'The users we will use in the service';

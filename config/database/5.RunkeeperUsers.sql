-- Table: nevex.runkeeper_users

-- DROP TABLE nevex.runkeeper_users;

CREATE TABLE nevex.runkeeper_users
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    created_date timestamp with time zone NOT NULL,
    updated_date timestamp with time zone,
    name text NOT NULL,
    location text,
    gender text,
    birthday date,
    athlete_type text,
    is_active boolean NOT NULL,
    is_friend_request_sent boolean NOT NULL,
    is_friend boolean NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE nevex.runkeeper_users
    OWNER to postgres;
COMMENT ON TABLE nevex.runkeeper_users
    IS 'The users we will use in the service';

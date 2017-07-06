-- Table: nevex.oauth_users

-- DROP TABLE nevex.oauth_users;

CREATE TABLE nevex.oauth_users
(
    id serial NOT NULL,
    user_id integer,
    code text NOT NULL,
    created_date timestamp with time zone NOT NULL,
    updated_date timestamp with time zone,
    access_token text,
    is_active boolean NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE nevex.oauth_users
    OWNER to postgres;
COMMENT ON TABLE nevex.oauth_users
    IS 'The information about each user with oauth';

-- Table: nevex.comments_to_users

-- DROP TABLE nevex.comments_to_users;

CREATE TABLE nevex.comments_to_users
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    created_date timestamp with time zone NOT NULL,
    comment character varying NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE nevex.comments_to_users
    OWNER to postgres;
COMMENT ON TABLE nevex.comments_to_users
    IS 'The record of what comments were used for users';

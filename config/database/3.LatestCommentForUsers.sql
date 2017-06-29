-- Table: nevex.latest_comment_for_users

-- DROP TABLE nevex.latest_comment_for_users;

CREATE TABLE nevex.latest_comment_for_users
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    last_comment_added_date timestamp with time zone NOT NULL,
    last_fitness_id bigint NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE nevex.latest_comment_for_users
    OWNER to postgres;
COMMENT ON TABLE nevex.latest_comment_for_users
    IS 'Tracks the latest comment left for users';

-- Table: nevex.comment_jobs

-- DROP TABLE nevex.comment_jobs;

CREATE TABLE nevex.comment_jobs
(
    id serial NOT NULL,
    date_ran timestamp with time zone NOT NULL,
    time_taken_ms integer NOT NULL,
    active_users integer NOT NULL,
    comments_added integer NOT NULL,
    comments_failed integer NOT NULL,
    comments_ignored integer NOT NULL,
    comment_used character varying NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE nevex.comment_jobs
    OWNER to postgres;
COMMENT ON TABLE nevex.comment_jobs
    IS 'Record of all the comment jobs that ran';

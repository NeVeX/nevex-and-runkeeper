-- Table: nevex."CommentJobs"

-- DROP TABLE nevex."CommentJobs";

CREATE TABLE nevex."CommentJobs"
(
    "Id" integer NOT NULL DEFAULT nextval('nevex."CommentJobs_Id_seq"'::regclass),
    "DateRan" time with time zone NOT NULL,
    "TimeTakenMs" integer NOT NULL,
    "ActiveUsers" integer NOT NULL,
    "CommentsAdded" integer NOT NULL,
    "CommentsFailed" integer NOT NULL,
    "CommentsIgnored" integer NOT NULL,
    "CommentUsed" character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "CommentJobs_pkey" PRIMARY KEY ("Id")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE nevex."CommentJobs"
    OWNER to postgres;
COMMENT ON TABLE nevex."CommentJobs"
    IS 'Record of all the comment jobs that ran';

-- Table: nevex."LatestCommentForUsers"

-- DROP TABLE nevex."LatestCommentForUsers";

CREATE TABLE nevex."LatestCommentForUsers"
(
    "Id" integer NOT NULL DEFAULT nextval('nevex."LatestCommentForUsers_Id_seq"'::regclass),
    "UserId" integer NOT NULL,
    "LastCommentAdded" time with time zone NOT NULL,
    "LastFitnessId" bigint NOT NULL,
    CONSTRAINT "LatestCommentForUsers_pkey" PRIMARY KEY ("UserId")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE nevex."LatestCommentForUsers"
    OWNER to postgres;
COMMENT ON TABLE nevex."LatestCommentForUsers"
    IS 'Tracks the latest comment left for users';

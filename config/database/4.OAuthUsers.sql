-- Table: nevex."OAuthUsers"

-- DROP TABLE nevex."OAuthUsers";

CREATE TABLE nevex."OAuthUsers"
(
    "Id" integer NOT NULL DEFAULT nextval('nevex."OAuthUsers_Id_seq"'::regclass),
    "UserId" integer,
    "Code" character varying COLLATE pg_catalog."default" NOT NULL,
    "CreatedDate" time with time zone NOT NULL,
    "UpdatedDate" time with time zone,
    "RedirectUrl" character varying COLLATE pg_catalog."default",
    "State" character varying COLLATE pg_catalog."default",
    "ClientId" character varying COLLATE pg_catalog."default",
    "AccessToken" character varying COLLATE pg_catalog."default",
    "IsFriendRequestSent" boolean NOT NULL,
    "IsFriend" boolean NOT NULL,
    "IsActive" boolean NOT NULL,
    CONSTRAINT "OAuthUsers_pkey" PRIMARY KEY ("Id")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE nevex."OAuthUsers"
    OWNER to postgres;
COMMENT ON TABLE nevex."OAuthUsers"
    IS 'The information about each user with oauth';

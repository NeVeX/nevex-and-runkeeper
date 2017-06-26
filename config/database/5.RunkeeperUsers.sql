-- Table: nevex."RunkeeperUsers"

-- DROP TABLE nevex."RunkeeperUsers";

CREATE TABLE nevex."RunkeeperUsers"
(
    "Id" integer NOT NULL DEFAULT nextval('nevex."RunkeeperUsers_Id_seq"'::regclass),
    "UserId" integer NOT NULL,
    "Name" character varying COLLATE pg_catalog."default" NOT NULL,
    "Location" character varying COLLATE pg_catalog."default",
    "Gender" character varying COLLATE pg_catalog."default",
    "Birthday" date,
    "SignUpDate" date NOT NULL,
    "AthleteType" character varying COLLATE pg_catalog."default",
    CONSTRAINT "RunkeeperUsers_pkey" PRIMARY KEY ("Id")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE nevex."RunkeeperUsers"
    OWNER to postgres;
COMMENT ON TABLE nevex."RunkeeperUsers"
    IS 'The users we will use in the service';

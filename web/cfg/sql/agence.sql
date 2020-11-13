CREATE TABLE public.agence
(
    id character varying(40) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    host character varying(17) DEFAULT '127.0.0.1',
    port int not null default 5432,
	database character varying(32) DEFAULT 'postgres' not null,
	username character varying(32) DEFAULT 'honyi' not null,
	password character varying(32) DEFAULT 'honyi123' not null,
    status int not null default 1,
    CONSTRAINT agence_pkey PRIMARY KEY (id)
)
;
CREATE TABLE public.window
(
	id character varying(40) COLLATE pg_catalog."default" NOT NULL,
    win_id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    win_number smallint,
    name character varying(255) COLLATE pg_catalog."default",
    status smallint NOT NULL,
    win_group_id character varying(32) COLLATE pg_catalog."default",
    default_user character varying(32) COLLATE pg_catalog."default",
    branch_id character varying(32) COLLATE pg_catalog."default",
	id_db character varying(40) not null references agence(id) ON DELETE CASCADE,
    CONSTRAINT window_pkey PRIMARY KEY (id)
);
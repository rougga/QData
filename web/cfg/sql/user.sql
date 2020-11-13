CREATE TABLE public.user
(
	id character varying(40) COLLATE pg_catalog."default" NOT NULL,
    user_id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    account character varying(50) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default",
    nike_name character varying(100) COLLATE pg_catalog."default",
    limit_time timestamp(6) without time zone,
    access_time timestamp(6) without time zone,
    status numeric(1,0) DEFAULT 1,
    dept_id character varying(32) COLLATE pg_catalog."default",
    usertype numeric(3,0),
    avatar character varying(300) COLLATE pg_catalog."default",
    work_num character varying(100) COLLATE pg_catalog."default",
    work character varying(100) COLLATE pg_catalog."default",
	id_db character varying(40) not null references agence(id) ON DELETE CASCADE,
	CONSTRAINT user_pkey PRIMARY KEY (id)
);
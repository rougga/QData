
CREATE TABLE public.t_user
(
    id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    account character varying(50) COLLATE pg_catalog."default" NOT NULL,
    passwd character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default",
    nike_name character varying(100) COLLATE pg_catalog."default",
    limit_time timestamp(6) without time zone,
    access_time timestamp(6) without time zone,
    status numeric(1,0) DEFAULT 1,
    gender character varying(1) COLLATE pg_catalog."default",
    birthday timestamp(6) without time zone,
    mobile character varying(30) COLLATE pg_catalog."default",
    tel character varying(50) COLLATE pg_catalog."default",
    email character varying(50) COLLATE pg_catalog."default",
    addresss character varying(300) COLLATE pg_catalog."default",
    dept_id character varying(32) COLLATE pg_catalog."default",
    usertype numeric(3,0),
    add_user character varying(32) COLLATE pg_catalog."default",
    add_time timestamp(6) without time zone,
    mod_user character varying(32) COLLATE pg_catalog."default",
    mod_time timestamp(6) without time zone,
    salt character varying(500) COLLATE pg_catalog."default",
    ip character varying(100) COLLATE pg_catalog."default",
    avatar character varying(300) COLLATE pg_catalog."default",
    work_num character varying(100) COLLATE pg_catalog."default",
    work character varying(100) COLLATE pg_catalog."default",
    star smallint,
db_id  character varying(40) not null references agence(id) ON DELETE CASCADE
);
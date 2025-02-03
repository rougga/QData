CREATE TABLE public.t_login_log
(
    id character varying(32) COLLATE pg_catalog."default",
    login_time timestamp(6) without time zone,
    login_type character varying(50) COLLATE pg_catalog."default",
    user_id character varying(32) COLLATE pg_catalog."default",
    account character varying(100) COLLATE pg_catalog."default",
    login_source character varying(1000) COLLATE pg_catalog."default",
    logs character varying(4000) COLLATE pg_catalog."default",
    login_ip character varying(100) COLLATE pg_catalog."default",
    session_id character varying(4000) COLLATE pg_catalog."default",
    successed numeric(1,0),
    db_id  character varying(40) not null references agence(id) ON DELETE CASCADE
);
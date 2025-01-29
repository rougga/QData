
CREATE TABLE public.t_window
(
    id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    win_number smallint,
    name character varying(255) COLLATE pg_catalog."default",
    com_name character varying(30) COLLATE pg_catalog."default",
    screen_address character varying(30) COLLATE pg_catalog."default",
    protocol character varying(32) COLLATE pg_catalog."default",
    enable_eval numeric(1,0),
    standard character varying(10) COLLATE pg_catalog."default",
    status smallint NOT NULL,
    mod_user character varying(20) COLLATE pg_catalog."default",
    mod_time timestamp(6) without time zone,
    screen_type character varying(10) COLLATE pg_catalog."default" NOT NULL,
    win_group_id character varying(32) COLLATE pg_catalog."default",
    ip_address character varying(32) COLLATE pg_catalog."default",
    eval_address character varying(30) COLLATE pg_catalog."default",
    default_user character varying(32) COLLATE pg_catalog."default",
    ctrl_server character varying(32) COLLATE pg_catalog."default",
    beeper_addr character varying(32) COLLATE pg_catalog."default",
    sound_server character varying(32) COLLATE pg_catalog."default",
    branch_id character varying(32) COLLATE pg_catalog."default",
db_id  character varying(40) not null references agence(id) ON DELETE CASCADE
);
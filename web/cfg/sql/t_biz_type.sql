
CREATE TABLE public.t_biz_type
(
    id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    biz_prefix character varying(32) COLLATE pg_catalog."default",
    card_enable smallint,
    status smallint DEFAULT 1,
    mod_user character varying(20) COLLATE pg_catalog."default",
    mod_time timestamp(6) without time zone DEFAULT now(),
    auto_trans character varying(32) COLLATE pg_catalog."default",
    comments character varying(100) COLLATE pg_catalog."default",
    start_num character varying(10) COLLATE pg_catalog."default",
    sort smallint DEFAULT 0,
    call_delay smallint DEFAULT 0,
    info character varying(300) COLLATE pg_catalog."default",
    need_book smallint DEFAULT 0,
    biz_class_id character varying(32) COLLATE pg_catalog."default",
    deal_time_warning integer,
    branch_id character varying(32) COLLATE pg_catalog."default" DEFAULT 1,
    limit_by_ampm smallint DEFAULT 0,
    hidden smallint DEFAULT 0,
    biz_code character varying(100) COLLATE pg_catalog."default",
    external_code character varying(100) COLLATE pg_catalog."default",
    extend1 character varying(100) COLLATE pg_catalog."default",
    extend2 character varying(100) COLLATE pg_catalog."default",
    extend3 character varying(100) COLLATE pg_catalog."default",
    extend4 character varying(100) COLLATE pg_catalog."default",
    extend5 character varying(100) COLLATE pg_catalog."default",
    extend6 character varying(100) COLLATE pg_catalog."default",
    extend7 character varying(100) COLLATE pg_catalog."default",
    db_id  character varying(40) not null references agence(id) ON DELETE CASCADE
);
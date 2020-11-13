create table service (
	id character varying(40) COLLATE pg_catalog."default" NOT NULL,
    biz_type_id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    biz_prefix character varying(32) COLLATE pg_catalog."default",
    status smallint DEFAULT 1,
    start_num character varying(10) COLLATE pg_catalog."default",
    sort smallint DEFAULT 0,
    call_delay smallint DEFAULT 0,
    biz_class_id character varying(32) COLLATE pg_catalog."default",
    deal_time_warning integer,
    hidden smallint DEFAULT 0,
	id_db  character varying(40) not null references agence(id) ON DELETE CASCADE,
    CONSTRAINT service_pkey PRIMARY KEY (id)

);
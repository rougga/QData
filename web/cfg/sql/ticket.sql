CREATE TABLE public.ticket
(
    id character varying(40) COLLATE pg_catalog."default" NOT NULL,
	ticket_id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    biz_type_id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    evaluation_id character varying(32) COLLATE pg_catalog."default",
    ticket_num character varying(10) COLLATE pg_catalog."default",
    ticket_type character varying(32) COLLATE pg_catalog."default" NOT NULL,
    status smallint NOT NULL,
    deal_win character varying(32) COLLATE pg_catalog."default",
    transfer_win character varying(32) COLLATE pg_catalog."default",
    deal_user character varying(20) COLLATE pg_catalog."default",
    id_card_info_id character varying(32) COLLATE pg_catalog."default",
    ticket_time timestamp(6) without time zone,
    finish_time timestamp(6) without time zone,
    call_time timestamp(6) without time zone,
    start_time timestamp(6) without time zone,
    call_type character varying(255) COLLATE pg_catalog."default" DEFAULT 1,
    branch_id character varying(32) COLLATE pg_catalog."default" DEFAULT 1,
    id_card_name character varying(100) COLLATE pg_catalog."default",
	id_db character varying(40) not null references agence(id) ON DELETE CASCADE,
    CONSTRAINT t_ticket_pkey PRIMARY KEY (id)
);
CREATE TABLE public.t_window_status
(
    window_id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    user_id character varying(20) COLLATE pg_catalog."default",
    status numeric(1,0),
    access_time timestamp(6) without time zone,
    pause_count numeric(5,0),
    final_opernate_time timestamp(6) without time zone,
    ip character varying(32) COLLATE pg_catalog."default",
    win_puase_time timestamp(6) without time zone,
    batch_deal_status numeric(1,0),
    pause_time numeric(5,0),
    current_ticket character varying(32) COLLATE pg_catalog."default",
db_id  character varying(40) not null references agence(id) ON DELETE CASCADE
);
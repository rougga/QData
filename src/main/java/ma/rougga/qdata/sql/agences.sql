CREATE TABLE public.rougga_agences
(
    id character varying(40) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    host character varying(17) DEFAULT '127.0.0.1',
    port int not null default 8888,
    lastupdated_at timestamp(6) without time zone default NOW(),
    status int not null default 1,
    CONSTRAINT rougga_agences_pkey PRIMARY KEY (id)
)
;
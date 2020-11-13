create table lastupdate (
	id_db  character varying(40) not null references agence(id) ON DELETE CASCADE,
    last_update timestamp(6) without time zone
);
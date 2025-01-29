create table cible(
	biz_type_id character varying(32) not null,
	db_id character varying(40) not null references agence(id) ON DELETE CASCADE,
	cibleA NUMERIC DEFAULT 0,
	cibleT NUMERIC DEFAULT 0,
	dCible NUMERIC DEFAULT 0
);
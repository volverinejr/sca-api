create table sistema (
	id bigint generated by default as identity,
	
	nome varchar(100) not null,
	time_id bigint,
	ativo boolean not null,
	
	created_by varchar(255),
	created_date timestamp not null,
	last_modified_by varchar(255),
	last_modified_date timestamp,
	
	primary key (id)
)
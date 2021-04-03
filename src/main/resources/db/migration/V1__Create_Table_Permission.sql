CREATE TABLE IF NOT EXISTS permission (
	id bigint not null,
	description varchar(255),
	
	created_by varchar(255),
	created_date timestamp not null,
	last_modified_by varchar(255),
	last_modified_date timestamp,
	
	primary key (id)
)

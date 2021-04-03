create table user_time (
	id bigint generated by default as identity,

	user_id bigint,
	time_id bigint,
	
	created_by varchar(255),
	created_date timestamp not null,

	primary key (id)
)
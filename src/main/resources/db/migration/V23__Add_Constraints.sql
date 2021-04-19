alter table solicitacao_movimentacao 
	add constraint FKseghc6xtowxl1lnp3qmgsb96q foreign key (solicitacao_id) references solicitacao;


alter table sprint 
	add constraint FK113v6xi0wxjp2d13ko63y23xi foreign key (time_id) references time;


alter table sistema 
	add constraint FKd99em0lhijuixe6vdt26hr3tr foreign key (time_id) references time;


alter table users 
	add constraint FKq2jofli6q6um1l7al1xdveitk foreign key (cliente_id) references cliente;
	

alter table users 
	add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);


alter table users 
	add constraint UK_k8d0f2n7n88w1a16yhua64onx unique (user_name);


alter table user_permission 
	add constraint FKbklmo9kchans5u3e4va0ouo1s foreign key (permission_id) references permission;


alter table user_permission 
	add constraint FKn8ba4v3gvw1d82t3hofelr82t foreign key (user_id) references users;
	
	
alter table cliente_sistema 
	add constraint UKs11ai5h6aynely9tucayjnc20 unique (cliente_id, sistema_id);

alter table cliente_sistema 
	add constraint FKpvdiebiol2pbevkxmb781lguu foreign key (cliente_id) references cliente;

alter table cliente_sistema 
	add constraint FK7ls8b1m94poecdddmxxoqiue6 foreign key (sistema_id) references sistema;
	



alter table solicitacao_fase 
	add constraint FKo5rd0vxlfkb6o44os6mtqm49a foreign key (fase_id) references fase;


alter table solicitacao_fase 
	add constraint FKd0mv4obad0mqt2engur1ccaoo foreign key (solicitacao_id) references solicitacao;






alter table sprint_solicitacao 
	add constraint UKjf99xdicfux5upi2pqbdq20pj unique (sprint_id, solicitacao_id);

alter table sprint_solicitacao 
	add constraint FKgbfnv2cv9vk9blpw4n6q7ifel foreign key (solicitacao_id) references solicitacao;

alter table sprint_solicitacao add constraint FKrp80jtdhh4aj35j4o2nsf4d8p foreign key (sprint_id) references sprint;






alter table user_cliente_sistema 
	add constraint UKdihjpo9gc9jyv8ssfruxms04k unique (user_id, cliente_sistema_id);

alter table user_cliente_sistema 
	add constraint FKegtf88lrx30fr3ifkbnla50d7 foreign key (cliente_sistema_id) references cliente_sistema;


alter table user_cliente_sistema 
	add constraint FK1yilikpm00bxjgl3fd8nnf1f9 foreign key (user_id) references users;





alter table user_time 
	add constraint UK2gditko8r69wb4u105ftj2wft unique (user_id, time_id);

alter table user_time 
	add constraint FKbawngufb6iabtb1i0gmsidni0 foreign key (time_id) references time;

alter table user_time 
	add constraint FKc3gknqam4rmdrvgyijs4wallb foreign key (user_id) references users;







alter table solicitacao 
	add constraint FKltuy9jbajlptwo49p321vkgp6 foreign key (cliente_id) references cliente;


alter table solicitacao 
	add constraint FKf2njxdvy38afyi8axyq0472ih foreign key (sistema_id) references sistema;


alter table solicitacao 
	add constraint FKgksebnae7xms5e7g6m1prdogt foreign key (user_id) references users;


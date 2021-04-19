alter table solicitacao_fase 
   add constraint FK2hi52ulxulit9gfpla6a1rpms 
   foreign key (responsavel_id) 
   references users;
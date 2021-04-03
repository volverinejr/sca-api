INSERT INTO fase (id, nome, pedir_aceite_do_usuario, created_by, created_date) 
VALUES (1, 'Análise', false, 'Automático', NOW() );

﻿INSERT INTO fase (id, nome, pedir_aceite_do_usuario, created_by, created_date) 
VALUES (2, 'Implementação e Teste', false, 'Automático', NOW() );

﻿INSERT INTO fase (id, nome, pedir_aceite_do_usuario, created_by, created_date) 
VALUES (3, 'Deploy em Homologação', false, 'Automático', NOW() );

﻿INSERT INTO fase (id, nome, pedir_aceite_do_usuario, created_by, created_date) 
VALUES (4, 'Homologação', true, 'Automático', NOW() );

INSERT INTO fase (id, nome, pedir_aceite_do_usuario, created_by, created_date) 
VALUES (5, 'Deploy em Produção', false, 'Automático', NOW() );

INSERT INTO fase (id, nome, pedir_aceite_do_usuario, created_by, created_date) 
VALUES (6, 'Finalizado', true, 'Automático', NOW() );


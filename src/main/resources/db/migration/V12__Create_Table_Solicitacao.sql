CREATE TABLE IF NOT EXISTS `solicitacao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `descricao` varchar(2000) NOT NULL,
  `prioridade` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `sistema_id` bigint DEFAULT NULL,
  `status_atual` varchar(50) DEFAULT NULL,
  
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  KEY `FKltuy9jbajlptwo49p321vkgp6` (`cliente_id`),
  KEY `FKf2njxdvy38afyi8axyq0472ih` (`sistema_id`),
  KEY `FKgksebnae7xms5e7g6m1prdogt` (`user_id`),
  CONSTRAINT `FKf2njxdvy38afyi8axyq0472ih` FOREIGN KEY (`sistema_id`) REFERENCES `sistema` (`id`),
  CONSTRAINT `FKgksebnae7xms5e7g6m1prdogt` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKltuy9jbajlptwo49p321vkgp6` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)

) ENGINE=InnoDB;
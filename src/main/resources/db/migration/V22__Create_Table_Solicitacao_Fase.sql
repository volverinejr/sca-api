CREATE TABLE IF NOT EXISTS `solicitacao_fase` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `solicitacao_id` bigint DEFAULT NULL,
  `fase_id` bigint DEFAULT NULL,
  `observacao` varchar(2000) DEFAULT NULL,
  `finalizada` bit(1) DEFAULT NULL,

  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2f4od45j3n7rm98me7mclw4y4` (`solicitacao_id`,`fase_id`),
  KEY `FKo5rd0vxlfkb6o44os6mtqm49a` (`fase_id`),
  CONSTRAINT `FKd0mv4obad0mqt2engur1ccaoo` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`),
  CONSTRAINT `FKo5rd0vxlfkb6o44os6mtqm49a` FOREIGN KEY (`fase_id`) REFERENCES `fase` (`id`)
) ENGINE=InnoDB;
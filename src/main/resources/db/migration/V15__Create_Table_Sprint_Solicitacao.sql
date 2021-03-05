CREATE TABLE IF NOT EXISTS `sprint_solicitacao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `sprint_id` bigint DEFAULT NULL,
  `solicitacao_id` bigint DEFAULT NULL,
  `finalizada` bit(1) NOT NULL,

  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjf99xdicfux5upi2pqbdq20pj` (`sprint_id`,`solicitacao_id`),
  KEY `FKgbfnv2cv9vk9blpw4n6q7ifel` (`solicitacao_id`),
  CONSTRAINT `FKgbfnv2cv9vk9blpw4n6q7ifel` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`),
  CONSTRAINT `FKrp80jtdhh4aj35j4o2nsf4d8p` FOREIGN KEY (`sprint_id`) REFERENCES `sprint` (`id`)

) ENGINE=InnoDB;
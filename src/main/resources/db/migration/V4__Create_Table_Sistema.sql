CREATE TABLE IF NOT EXISTS `sistema` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `nome` varchar(100) NOT NULL,
  `time_id` bigint DEFAULT NULL,
  `ativo` bit(1) NOT NULL,
  
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `nome` (`nome`),
  KEY `FKd99em0lhijuixe6vdt26hr3tr` (`time_id`),
  CONSTRAINT `FKd99em0lhijuixe6vdt26hr3tr` FOREIGN KEY (`time_id`) REFERENCES `time` (`id`)
) ENGINE=InnoDB;

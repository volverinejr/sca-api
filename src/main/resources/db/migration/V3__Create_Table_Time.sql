CREATE TABLE IF NOT EXISTS `time` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `ativo` bit(1) NOT NULL,

  `created_by` varchar(255) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  KEY `nome` (`nome`)  
) ENGINE=InnoDB;

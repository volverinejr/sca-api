CREATE TABLE IF NOT EXISTS `fase` (
  `id` bigint NOT NULL AUTO_INCREMENT,

  `nome` varchar(100) NOT NULL,
  `pedir_aceite_do_usuario` bit(1) DEFAULT NULL,
  
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `solicitacao_movimentacao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `solicitacao_id` bigint DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `observacao` varchar(2000) DEFAULT NULL,

  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  
  PRIMARY KEY (`id`),
  KEY `FKseghc6xtowxl1lnp3qmgsb96q` (`solicitacao_id`),
  CONSTRAINT `FKseghc6xtowxl1lnp3qmgsb96q` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`)

) ENGINE=InnoDB;
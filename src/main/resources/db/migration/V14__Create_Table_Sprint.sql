CREATE TABLE IF NOT EXISTS `sprint` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `data_inicio` date NOT NULL,
  `data_fim` date NOT NULL,
  `time_id` bigint DEFAULT NULL,
  `data_encaminhamento_ao_time` datetime(6) DEFAULT NULL,

  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  KEY `FK113v6xi0wxjp2d13ko63y23xi` (`time_id`),
  CONSTRAINT `FK113v6xi0wxjp2d13ko63y23xi` FOREIGN KEY (`time_id`) REFERENCES `time` (`id`)
) ENGINE=InnoDB;
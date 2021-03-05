CREATE TABLE IF NOT EXISTS `cliente_sistema` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint DEFAULT NULL,
  `sistema_id` bigint DEFAULT NULL,
  
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKs11ai5h6aynely9tucayjnc20` (`cliente_id`,`sistema_id`),
  KEY `FKpvdiebiol2pbevkxmb781lguu` (`cliente_id`),
  KEY `FK7ls8b1m94poecdddmxxoqiue6` (`sistema_id`),
  CONSTRAINT `FK7ls8b1m94poecdddmxxoqiue6` FOREIGN KEY (`sistema_id`) REFERENCES `sistema` (`id`),
  CONSTRAINT `FKpvdiebiol2pbevkxmb781lguu` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `user_cliente_sistema` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `cliente_sistema_id` bigint DEFAULT NULL,

  `created_by` varchar(255) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdihjpo9gc9jyv8ssfruxms04k` (`user_id`,`cliente_sistema_id`),
  KEY `FKegtf88lrx30fr3ifkbnla50d7` (`cliente_sistema_id`),
  CONSTRAINT `FK1yilikpm00bxjgl3fd8nnf1f9` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKegtf88lrx30fr3ifkbnla50d7` FOREIGN KEY (`cliente_sistema_id`) REFERENCES `cliente_sistema` (`id`)
) ENGINE=InnoDB;
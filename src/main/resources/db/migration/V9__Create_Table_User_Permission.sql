CREATE TABLE IF NOT EXISTS `user_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `user_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  
  PRIMARY KEY (`id`),
  KEY `fk_userpermission_user` (`user_id`),
  KEY `fk_userpermission_permission` (`permission_id`),
  CONSTRAINT `fk_userpermission_permission` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `fk_userpermission_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB;
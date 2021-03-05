CREATE TABLE IF NOT EXISTS `user_time` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  
  `user_id` bigint DEFAULT NULL,
  `time_id` bigint DEFAULT NULL,

  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2gditko8r69wb4u105ftj2wft` (`user_id`,`time_id`),
  KEY `FKbawngufb6iabtb1i0gmsidni0` (`time_id`),
  CONSTRAINT `FKbawngufb6iabtb1i0gmsidni0` FOREIGN KEY (`time_id`) REFERENCES `time` (`id`),
  CONSTRAINT `FKc3gknqam4rmdrvgyijs4wallb` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)

) ENGINE=InnoDB;
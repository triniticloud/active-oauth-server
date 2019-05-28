CREATE TABLE if NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE if NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE if NOT EXISTS `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
);

CREATE TABLE if NOT EXISTS `oauth_client_details` (
  `client_id` VARCHAR(256) PRIMARY KEY,
  `resource_ids` VARCHAR(256),
  `client_secret` VARCHAR(256),
  `scope` VARCHAR(256),
  `authorized_grant_types` VARCHAR(256),
  `web_server_redirect_uri` VARCHAR(2560),
  `authorities` VARCHAR(256),
  `access_token_validity` INTEGER,
  `refresh_token_validity` INTEGER,
  `additional_information` VARCHAR(4096),
  `autoapprove` VARCHAR(256)
);

CREATE TABLE if NOT EXISTS `oauth_client_token` (
  `token_id` VARCHAR(256),
  `token` LONG VARBINARY,
  `authentication_id` VARCHAR(256) PRIMARY KEY,
  `user_name` VARCHAR(256),
  `client_id` VARCHAR(256)
);

CREATE TABLE if NOT EXISTS `oauth_access_token` (
  `token_id` VARCHAR(256),
  `token` LONG VARBINARY,
  `authentication_id` VARCHAR(256) PRIMARY KEY,
  `user_name` VARCHAR(256),
  `client_id` VARCHAR(256),
  `authentication` LONG VARBINARY,
  `refresh_token` VARCHAR(256)
);

CREATE TABLE if NOT EXISTS `oauth_refresh_token` (
  `token_id` VARCHAR(256),
  `token` LONG VARBINARY,
  `authentication` LONG VARBINARY
);

CREATE TABLE if NOT EXISTS `oauth_code` (
  `code` VARCHAR(256),
  `authentication` LONG VARBINARY
);

CREATE TABLE if NOT EXISTS `oauth_approvals` (
	`user_id` VARCHAR(256),
	`client_id` VARCHAR(256),
	`scope` VARCHAR(256),
	`last_modified_at` TIMESTAMP,
	`status` VARCHAR(10),
	`expires_at` DATETIME
);

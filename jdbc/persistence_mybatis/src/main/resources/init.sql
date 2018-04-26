drop table  if exists users;

CREATE TABLE `users` (
	`id` INT(11) AUTO_INCREMENT NOT NULL,
	`user_name` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`password` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

INSERT INTO USERS (user_name, password) VALUES ("张三", "zs");
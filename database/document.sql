CREATE  TABLE `document-db`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(20) NULL ,
  `email` VARCHAR(50) NULL ,
  `password` VARCHAR(120) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) );

INSERT INTO `document-db`.`users` (`username`, `email`, `password`) VALUES ('admin', 'admin@gmail.com', 'password');
INSERT INTO `document-db`.`users` (`username`, `email`, `password`) VALUES ('kieuanh', 'kanh@gmail.com', 'kieuanh');

delete from user_roles where user_id=13;
CREATE  TABLE `document-db`.`roles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(20) NULL ,
  PRIMARY KEY (`id`) );

INSERT INTO `document-db`.`roles` (`name`) VALUES ('ROLE_USER');
INSERT INTO `document-db`.`roles` (`name`) VALUES ('ROLE_ADMIN');

CREATE  TABLE `document-db`.`user_roles` (
  `user_id` BIGINT(20) NOT NULL ,
  `role_id` INT(11) NOT NULL ,
  /* PRIMARY KEY (`user_id`, `role_id`) */
  	FOREIGN KEY(user_id) REFERENCES users(id),
  	FOREIGN KEY(role_id) REFERENCES roles(id)
);

INSERT INTO `document-db`.`user_roles` (`user_id`,`role_id`) VALUES ('1','2');
INSERT INTO `document-db`.`user_roles` (`user_id`,`role_id`) VALUES ('2','1');

CREATE  TABLE `document-db`.`documents` (
  `id` BIGINT(20) auto_increment PRIMARY KEY NOT NULL ,
  `type` ENUM('COM-CA-TPL','COM-CAR-TPL')  ,
  `title` VARCHAR(500)  ,
  `user_id` BIGINT(20) ,
  `updated` DATETIME ,
  `status` ENUM('NEW','APPROVED') ,
  `doc_data` TEXT ,
  	FOREIGN KEY(user_id) REFERENCES users(id)
);


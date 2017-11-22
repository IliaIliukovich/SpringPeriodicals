-- -----------------------------------------------------
-- Table `periodicals`.`user`
-- -----------------------------------------------------
CREATE TABLE `user` (
  `id_user`  INT         NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `role`     VARCHAR(45) NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`id_user`)
);

-- -----------------------------------------------------
-- Table `periodicals`.`journal`
-- -----------------------------------------------------
CREATE TABLE `journal` (
  `id_journal` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NULL,
  `price` DECIMAL(9,2) NOT NULL,
  PRIMARY KEY (`id_journal`)
);

-- -----------------------------------------------------
-- Table `periodicals`.`choice`
-- -----------------------------------------------------
CREATE TABLE `choice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_journal` INT NOT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `periodicals`.`subscription`
-- -----------------------------------------------------
CREATE TABLE `subscription` (
  `id`         INT NOT NULL AUTO_INCREMENT,
  `id_user`    INT NOT NULL,
  `id_journal` INT NOT NULL,
  PRIMARY KEY (`id`)
);
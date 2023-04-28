-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema periodicals
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `periodicals` ;
CREATE SCHEMA IF NOT EXISTS `periodicals` DEFAULT CHARACTER SET utf8 ;
USE `periodicals` ;

-- -----------------------------------------------------
-- Table `periodicals`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals`.`periodicalsuser` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `role` VARCHAR(45) NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`id_user`),
  UNIQUE INDEX `Login_UNIQUE` (`username` ASC))
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `periodicals`.`journal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals`.`journal` (
  `id_journal` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NULL,
  `price` DECIMAL(9,2) NOT NULL,
  PRIMARY KEY (`id_journal`))
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `periodicals`.`choice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals`.`choice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_journal` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_choice_id_user_idx` (`id_user` ASC),
  INDEX `fk_choice_id_journal_idx` (`id_journal` ASC),
  CONSTRAINT `fk_choice_id_user`
  FOREIGN KEY (`id_user`)
  REFERENCES `periodicals`.`periodicalsuser` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_choice_id_journal`
  FOREIGN KEY (`id_journal`)
  REFERENCES `periodicals`.`journal` (`id_journal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `periodicals`.`subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals`.`subscription` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_journal` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_subscription_id_user_idx` (`id_user` ASC),
  INDEX `fk_subscription_id_journal_idx` (`id_journal` ASC),
  CONSTRAINT `fk_subscription_id_user`
  FOREIGN KEY (`id_user`)
  REFERENCES `periodicals`.`periodicalsuser` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subscription_id_journal`
  FOREIGN KEY (`id_journal`)
  REFERENCES `periodicals`.`journal` (`id_journal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
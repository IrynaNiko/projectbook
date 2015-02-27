SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `PB` DEFAULT CHARACTER SET latin1 ;
USE `PB`;


-- -----------------------------------------------------
-- Table `PB`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`role` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`priority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`priority` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`status` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`files`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`files` (
  `id` BIGINT(20) UNSIGNED AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`company_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`company_info` (
  `id` BIGINT(20) UNSIGNED AUTO_INCREMENT,
  `name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`user` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,  
  `role_id` BIGINT(20) UNSIGNED NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `surname` VARCHAR(60) NULL DEFAULT NULL,
  `position` VARCHAR(100) NOT NULL,
  `birth_date` DATE NULL DEFAULT NULL,
  `phone_no` VARCHAR(30) NULL DEFAULT NULL,
  `address` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC),
  INDEX `FK_user_role_id` (`role_id` ASC),
  CONSTRAINT `FK_user_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `PB`.`role` (`id`)
	ON DELETE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`announcement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`announcement` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED,
  `date_creation` DATETIME NOT NULL,
  `name` VARCHAR(70) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `file_id` BIGINT(20) UNSIGNED DEFAULT NULL,
  INDEX `FK_announcement_user_id` (`user_id` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `FK_announcement_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `PB`.`user` (`id`)
	ON DELETE SET NULL,
  CONSTRAINT `FK_files_file_id`
    FOREIGN KEY (`file_id`)
    REFERENCES `PB`.`files` (`id`)
	ON DELETE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`company_files`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`company_files` (
	`section_id` BIGINT(20) UNSIGNED,
	`file_id` BIGINT(20) UNSIGNED,
  INDEX `FK_company_files_file_id` (`file_id` ASC),
  INDEX `FK_company_files_section_id` (`section_id` ASC),
  CONSTRAINT `FK_company_files_file_id`
    FOREIGN KEY (`file_id`)
    REFERENCES `PB`.`files` (`id`)
	ON DELETE SET NULL,
  CONSTRAINT `FK_company_files_section_id`
    FOREIGN KEY (`section_id`)
    REFERENCES `PB`.`company_info` (`id`)
	ON DELETE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`project` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(75) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `date_creation` DATETIME NOT NULL,
  `date_started` DATETIME NOT NULL,
  `date_deadline` DATETIME NOT NULL,
  `date_finished` DATETIME NULL DEFAULT NULL,
  `manager_id` BIGINT(20) UNSIGNED,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC),
  INDEX `FK_project_manager_id` (`manager_id` ASC),
  CONSTRAINT `FK_project_manager_id`
    FOREIGN KEY (`manager_id`)
    REFERENCES `PB`.`user` (`id`)
	ON DELETE SET NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`project_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`project_members` (
  `user_id` BIGINT(20) UNSIGNED,
  `project_id` BIGINT(20) UNSIGNED,
  `project_manager` TINYINT(1) NOT NULL,
  INDEX `FK_project_members_user_id` (`user_id` ASC),
  INDEX `FK_project_members_project_id` (`project_id` ASC),
  CONSTRAINT `FK_project_members_project_id`
    FOREIGN KEY (`project_id`)
    REFERENCES `PB`.`project` (`id`)
	ON DELETE SET NULL,
  CONSTRAINT `FK_project_members_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `PB`.`user` (`id`)
	ON DELETE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`task` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `date_creation` DATETIME NOT NULL,
  `date_started` DATETIME NOT NULL,
  `date_deadline` DATETIME NOT NULL,
  `date_finished` DATETIME NULL DEFAULT NULL,
  `status_id` BIGINT(20) UNSIGNED NOT NULL,
  `priority_id` BIGINT(20) UNSIGNED NOT NULL,
  `manager_id` BIGINT(20) UNSIGNED,
  `executive_id` BIGINT(20) UNSIGNED,
  `project_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC),
  INDEX `FK_task_status_id` (`status_id` ASC),
  INDEX `FK_task_priority_id` (`priority_id` ASC),
  INDEX `FK_task_user_manager_id` (`manager_id` ASC),
  INDEX `FK_task_user_executive_id` (`executive_id` ASC),
  INDEX `FK_task_project_id` (`project_id` ASC),
  CONSTRAINT `FK_task_priority_id`
    FOREIGN KEY (`priority_id`)
    REFERENCES `PB`.`priority` (`id`)
	ON DELETE NO ACTION,
  CONSTRAINT `FK_task_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `PB`.`status` (`id`)
	ON DELETE NO ACTION,
  CONSTRAINT `FK_task_project_id`
    FOREIGN KEY (`project_id`)
    REFERENCES `PB`.`project` (`id`)
	ON DELETE CASCADE,  
  CONSTRAINT `FK_task_user_executive_id`
    FOREIGN KEY (`executive_id`)
    REFERENCES `PB`.`user` (`id`)
	ON DELETE SET NULL,
  CONSTRAINT `FK_task_user_manager_id`
    FOREIGN KEY (`manager_id`)
    REFERENCES `PB`.`user` (`id`)
	ON DELETE SET NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `PB`.`scheduler`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PB`.`scheduler` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `PB`.`user` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

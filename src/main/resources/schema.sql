SET MODE MySQL;
SET IGNORECASE=TRUE;

--DROP SCHEMA medicaldb;
--CREATE SCHEMA medicaldb;
--use medicaldb;


-- -----------------------------------------------------
-- Table `categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `categories` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `diagnosis` will need to set category_id to not null after category logic has been added in
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `diagnoses` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `category_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `diagnosis_information`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `diagnosis_information` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `diagnosis_id` INT UNSIGNED NOT NULL,
  `field` VARCHAR(100) NOT NULL,
  `value` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `notifications` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `content` VARCHAR(100) NULL,
    `creation_date` DATETIME NULL,
    `diagnosis_id` INT NOT NULL,
    `is_read` BOOLEAN NULL,
    `is_done` BOOLEAN NULL,
    PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- ---------------
-- -----------------------------------------------------
-- Table `cases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cases` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `demographics` VARCHAR(100) NOT NULL,
  `ward` VARCHAR(100) NULL,
  `presenting_complaint` VARCHAR(100) NULL,
  `presenting_complaint_history` VARCHAR(100) NULL,
  `medical_history` VARCHAR(100) NULL,
  `drug_history` VARCHAR(100) NULL,
  `allergies` VARCHAR(100) NULL,
  `family_history` VARCHAR(100) NULL,
  `social_history` VARCHAR(100) NULL,
  `notes` VARCHAR(300) NULL,
  `date_created` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `cases_diagnoses_link` to link many-to-many relationship
-- between `cases` and `diagnoses` table
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cases_diagnoses_link` (
--  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `diagnosis_id` INT NOT NULL,
  `case_id` INT NOT NULL,
  PRIMARY KEY (`diagnosis_id`, `case_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table  `Users`
-- -----------------------------------------------------
-- CREATE TABLE if not exists `user`
-- (
--     `id`       int(11)      NOT NULL AUTO_INCREMENT,
--     `username` VARCHAR(45)  NOT NULL,
--     `password` VARCHAR(100) NOT NULL,
--     `user_type`     int(11)      NULL,
--     PRIMARY KEY (`id`)
-- )
--     ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table  `role`
-- -----------------------------------------------------
CREATE TABLE if not exists `role`
(
    `id`     int(11)     NOT NULL AUTO_INCREMENT,
--     `userid` int(11)     NOT NULL,
    `role`   varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;
CREATE TABLE if not exists `user`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45)  NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `ward_id` INT NULL
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `user_roles`
(
    `user_id` INT NOT NULL,
    `roles_id` INT NOT NULL,
    PRIMARY KEY(`user_id`)
)

ENGINE = InnoDB;
-- CREATE TABLE if not exists `role`
-- (
--     `id`     int(11)     NOT NULL AUTO_INCREMENT,
--     `userid` int(11)     NOT NULL,
--     `role`   varchar(45) NOT NULL,
--     PRIMARY KEY (`id`)
-- )
--     ENGINE = InnoDB;

-- CREATE TABLE if not exists `auth_user`
-- (
--     `auth_user_id` INT(11) NOT NULL AUTO_INCREMENT,
--     `first_name` VARCHAR(255) NOT NULL,
--     `last_name` VARCHAR(255) NOT NULL,
--     `email` VARCHAR(255) NOT NULL,
--     `password` VARCHAR(100) NOT NULL,
--     `status` VARCHAR(255) NOT NULL,
--     PRIMARY KEY(`auth_user_id`)
-- )
--     ENGINE = InnoDB;
--
-- CREATE TABLE if not exists `auth_user_role`
-- (
--     `auth_user_id` INT(11) NOT NULL,
--     `auth_role_id` INT(11) NOT NULL,
--     PRIMARY KEY(`auth_user_id`, `auth_role_id`),
--     KEY FK_user_role(auth_role_id),
--     CONSTRAINT FK_auth_user FOREIGN KEY (auth_user_id) REFERENCES auth_user(auth_user_id),
--     CONSTRAINT FK_auth_user_role FOREIGN KEY (auth_role_id) REFERENCES auth_role(auth_role_id)
--
-- )
--     ENGINE = InnoDB;

-- CREATE TABLE if not exists `auth_role`
-- (
--     `auth_role_id` INT(11) NOT NULL,
--     `role_name` VARCHAR(255) NOT NULL,
--     `role_desc` VARCHAR(255) NOT NULL,
--     PRIMARY KEY(`auth_role_id`)
-- )
--     ENGINE = InnoDB;

-- CREATE TABLE if not exists `auth_role` (
--   `auth_role_id` INT(11) NOT NULL AUTO_INCREMENT,
--   `role_name` VARCHAR(255) DEFAULT NULL,
--   `role_desc` VARCHAR (255) DEFAULT NULL,
--   PRIMARY KEY (`auth_role_id`)
-- ) ENGINE = InnoDB;
--
-- CREATE TABLE if not exists auth_user (
--   `auth_user_id`   INT(11) NOT NULL AUTO_INCREMENT,
--   `first_name` VARCHAR (255) NOT NULL,
--   `last_name` VARCHAR (255) NOT NULL,
--   `email` VARCHAR (255) NOT NULL,
--   `password` VARCHAR (255) NOT NULL,
--   `status` VARCHAR (255),
--   PRIMARY KEY (`auth_user_id`)
-- )ENGINE = InnoDB;
--
-- CREATE TABLE if not exists auth_user_role (
--   `auth_user_id` INT(11) NOT NULL,
--   `auth_role_id` INT(11) NOT NULL,
--   PRIMARY KEY (`auth_user_id`,`auth_role_id`),
--   KEY FK_user_role (`auth_role_id`),
--   CONSTRAINT FK_auth_user FOREIGN KEY (`auth_user_id`) REFERENCES auth_user (`auth_user_id`),
--   CONSTRAINT FK_auth_user_role FOREIGN KEY (`auth_role_id`) REFERENCES auth_role (`auth_role_id`)
-- ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table ward that will link to cases
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ward` (
 `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
 `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;
-- ----------------------------------------------------
-- Table `ward_cases_link` to link many-to-many relationship
-- between `cases` and `ward` table
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ward_cases_link` (
--  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ward_id` INT NOT NULL,
  `case_id` INT NOT NULL,
  PRIMARY KEY (`ward_id`, `case_id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `freehandnotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `freehand_notes` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `diagnosis_id` INT UNSIGNED NOT NULL,
  `field` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


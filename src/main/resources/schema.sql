SET MODE MySQL;
SET IGNORECASE=TRUE;

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
CREATE TABLE IF NOT EXISTS `diagnosis_info` (
  `diagnosis_id` INT UNSIGNED NOT NULL,
  `key` VARCHAR(100) NOT NULL,
  `value` VARCHAR(100) NOT NULL)
ENGINE = InnoDB;
-- ---------------
-- -----------------------------------------------------
-- Table `cases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cases` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `demographics` VARCHAR(100) NOT NULL,
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
-- CREATE TABLE if not exists `role`
-- (
--     `id`     int(11)     NOT NULL AUTO_INCREMENT,
--     `userid` int(11)     NOT NULL,
--     `role`   varchar(45) NOT NULL,
--     PRIMARY KEY (`id`)
-- )
--     ENGINE = InnoDB;
CREATE TABLE if not exists `user`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45)  NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

CREATE TABLE if not exists `role`
(
    `id`     int(11)     NOT NULL AUTO_INCREMENT,
    `userid` int(11)     NOT NULL,
    `role`   varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

CREATE TABLE if not exists `auth_user`
(
    `auth_user_id` (11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`auth_user_id`)
)
    ENGINE = InnoDB;
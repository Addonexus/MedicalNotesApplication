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
-- Table `cases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cases` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `demographics` VARCHAR(100) NOT NULL,
  `notes` VARCHAR(100) NULL,
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
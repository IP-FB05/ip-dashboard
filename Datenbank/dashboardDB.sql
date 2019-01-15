CREATE DATABASE `dashboardDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

DROP TABLE IF EXISTS `dashboardDB`.`category`;
CREATE TABLE IF NOT EXISTS `dashboardDB`.`category` (
  `categoryID` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`categoryID`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = latin1

DROP TABLE IF EXISTS `dashboardDB`.`documents`;
CREATE TABLE IF NOT EXISTS `dashboardDB`.`documents` (
  `documentID` INT(11) NOT NULL AUTO_INCREMENT,
  `categoryID` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `lastChanged` DATE NOT NULL,
  `link` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`documentID`),
  INDEX `kategorieID_idx` (`categoryID` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = latin1

DROP TABLE IF EXISTS `dashboardDB`.`processes`;
CREATE TABLE IF NOT EXISTS `dashboardDB`.`processes` (
  `processID` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(5000) NOT NULL,
  `pic` VARCHAR(300) NOT NULL,
  `subs` INT(11) NULL DEFAULT NULL,
  `varFile` VARCHAR(200) NULL DEFAULT NULL,
  `bpmn` VARCHAR(200) NOT NULL,
  `added` DATE NULL DEFAULT NULL,
  `category` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`processID`),
  INDEX `kategorie_idx` (`category` ASC) VISIBLE,
  CONSTRAINT `kategorie`
    FOREIGN KEY (`category`)
    REFERENCES `dashboardDB`.`category` (`categoryID`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci

DROP TABLE IF EXISTS `dashboardDB`.`subs`;
CREATE TABLE `subs` (
  `subID` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`subID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `dashboardDB`.`process_has_subs`;
CREATE TABLE IF NOT EXISTS `dashboardDB`.`process_has_subs` (
  `processes_processID` INT(11) NOT NULL,
  `subs_subID` INT(11) NOT NULL,
  PRIMARY KEY (`processes_processID`, `subs_subID`),
  INDEX `fk_prozesse_has_subs_subs1_idx` (`subs_subID` ASC) VISIBLE,
  INDEX `fk_prozesse_has_subs_prozesse1_idx` (`processes_processID` ASC) VISIBLE,
  CONSTRAINT `fk_prozesse_has_subs_prozesse1`
    FOREIGN KEY (`processes_processID`)
    REFERENCES `dashboardDB`.`processes` (`processID`),
  CONSTRAINT `fk_prozesse_has_subs_subs1`
    FOREIGN KEY (`subs_subID`)
    REFERENCES `dashboardDB`.`subs` (`subID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci

DROP TABLE IF EXISTS `dashboardDB`.`systems`;
CREATE TABLE IF NOT EXISTS `dashboardDB`.`systems` (
  `systemID` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(5000) NULL DEFAULT 'Keine Beschreibung vorhanden',
  `link` VARCHAR(100) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`systemID`))
ENGINE = InnoDB
AUTO_INCREMENT = 120
DEFAULT CHARACTER SET = latin1
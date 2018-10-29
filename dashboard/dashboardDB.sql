CREATE TABLE IF NOT EXISTS `dashboard`.`dokumente` (
  `dokumentID` INT NOT NULL,
  `kategorieID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `lastChanged` DATE NOT NULL,
  `link` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`dokumentID`),
  INDEX `kategorieID_idx` (`kategorieID` ASC) VISIBLE,
  CONSTRAINT `kategorieID`
    FOREIGN KEY (`kategorieID`)
    REFERENCES `dashboard`.`kategorie` (`kategorieID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `dashboard`.`kategorie` (
  `kategorieID` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`kategorieID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `dashboard`.`systeme` (
  `systemID` INT NOT NULL,
  `beschreibung` VARCHAR(5000) NULL DEFAULT 'Keine Beschreibung vorhanden',
  `link` VARCHAR(100) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`systemID`))
ENGINE = InnoDB
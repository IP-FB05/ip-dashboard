CREATE DATABASE `dashboardDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

DROP TABLE IF EXISTS `kategorie`;
CREATE TABLE `kategorie` (
  `kategorieID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`kategorieID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `dokumente`;
CREATE TABLE `dokumente` (
  `dokumentID` int(11) NOT NULL AUTO_INCREMENT,
  `kategorieID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `lastChanged` date NOT NULL,
  `link` varchar(500) NOT NULL,
  PRIMARY KEY (`dokumentID`),
  KEY `kategorieID_idx` (`kategorieID`),
  CONSTRAINT `kategorieID` FOREIGN KEY (`kategorieID`) REFERENCES `kategorie` (`kategorieid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `prozesse`;
CREATE TABLE `prozesse` (
  `prozessID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `beschreibung` varchar(5000) NOT NULL,
  `bild` longblob NOT NULL,
  `subs` int(11) DEFAULT NULL,
  `var` varchar(200) DEFAULT NULL,
  `bpmn` varchar(200) NOT NULL,
  PRIMARY KEY (`prozessID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `subs`;
CREATE TABLE `subs` (
  `subID` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`subID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `prozesse_has_subs`;
CREATE TABLE `prozesse_has_subs` (
  `prozesse_prozessID` int(11) NOT NULL,
  `subs_subID` int(11) NOT NULL,
  PRIMARY KEY (`prozesse_prozessID`,`subs_subID`),
  KEY `fk_prozesse_has_subs_subs1_idx` (`subs_subID`),
  KEY `fk_prozesse_has_subs_prozesse1_idx` (`prozesse_prozessID`),
  CONSTRAINT `fk_prozesse_has_subs_prozesse1` FOREIGN KEY (`prozesse_prozessID`) REFERENCES `prozesse` (`prozessid`),
  CONSTRAINT `fk_prozesse_has_subs_subs1` FOREIGN KEY (`subs_subID`) REFERENCES `subs` (`subid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `systeme`;
CREATE TABLE `systeme` (
  `systemID` int(11) NOT NULL AUTO_INCREMENT,
  `beschreibung` varchar(5000) DEFAULT 'Keine Beschreibung vorhanden',
  `link` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`systemID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

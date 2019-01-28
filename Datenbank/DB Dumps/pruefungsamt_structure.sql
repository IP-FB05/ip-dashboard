-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com    Database: pruefungsamt
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `abschlussarbeit`
--

DROP TABLE IF EXISTS `abschlussarbeit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abschlussarbeit` (
  `idabschlussarbeit` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `verlaengert` datetime DEFAULT NULL,
  PRIMARY KEY (`idabschlussarbeit`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `module` (
  `modulnr` int(11) NOT NULL,
  `modultext` varchar(100) DEFAULT NULL,
  `beschreibung` text,
  `leistungspunkte` int(11) DEFAULT NULL,
  `fachsemester` int(11) DEFAULT NULL,
  PRIMARY KEY (`modulnr`),
  UNIQUE KEY `pruefungstext_UNIQUE` (`modultext`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_student`
--

DROP TABLE IF EXISTS `module_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `module_student` (
  `modul` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  PRIMARY KEY (`modul`,`student`),
  KEY `student_idx` (`student`),
  CONSTRAINT `modul` FOREIGN KEY (`modul`) REFERENCES `module` (`modulnr`),
  CONSTRAINT `student` FOREIGN KEY (`student`) REFERENCES `student` (`matrikelnr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_studiengang`
--

DROP TABLE IF EXISTS `module_studiengang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `module_studiengang` (
  `modul` int(11) NOT NULL,
  `studiengang` int(11) NOT NULL,
  `semester` int(11) DEFAULT NULL,
  PRIMARY KEY (`modul`,`studiengang`),
  KEY `fk_pruefung_studiengang_1_idx` (`studiengang`),
  CONSTRAINT `fk_module_studiengang_1` FOREIGN KEY (`modul`) REFERENCES `module` (`modulnr`),
  CONSTRAINT `fk_module_studiengang_2` FOREIGN KEY (`studiengang`) REFERENCES `studiengang` (`idstudiengang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `praktikum`
--

DROP TABLE IF EXISTS `praktikum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `praktikum` (
  `idpraktikum` int(11) NOT NULL,
  `modul` int(11) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `plaetze` int(11) DEFAULT NULL,
  PRIMARY KEY (`idpraktikum`),
  KEY `modul_idx` (`modul`),
  CONSTRAINT `prak_modul` FOREIGN KEY (`modul`) REFERENCES `module` (`modulnr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `praktikum_student`
--

DROP TABLE IF EXISTS `praktikum_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `praktikum_student` (
  `praktikum` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`praktikum`,`student`),
  KEY `prak_stud_student_idx` (`student`),
  CONSTRAINT `prak_stud_praktikum` FOREIGN KEY (`praktikum`) REFERENCES `praktikum` (`idpraktikum`),
  CONSTRAINT `prak_stud_student` FOREIGN KEY (`student`) REFERENCES `student` (`matrikelnr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pruefung_student`
--

DROP TABLE IF EXISTS `pruefung_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pruefung_student` (
  `student` int(11) NOT NULL,
  `pruefung` int(11) NOT NULL,
  `note` float DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`student`,`pruefung`),
  KEY `fk_pruefung_student_2_idx` (`pruefung`),
  CONSTRAINT `fk_pruefung_student_1` FOREIGN KEY (`student`) REFERENCES `student` (`matrikelnr`),
  CONSTRAINT `fk_pruefung_student_2` FOREIGN KEY (`pruefung`) REFERENCES `pruefungen` (`pruefungsnr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pruefungen`
--

DROP TABLE IF EXISTS `pruefungen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pruefungen` (
  `pruefungsnr` int(11) NOT NULL AUTO_INCREMENT,
  `modulnr` int(11) DEFAULT NULL,
  `pruefungszeitpunkt` datetime DEFAULT CURRENT_TIMESTAMP,
  `pruefer` varchar(45) DEFAULT NULL,
  `abschlussarbeitnr` int(11) DEFAULT NULL,
  PRIMARY KEY (`pruefungsnr`),
  KEY `pruef_modul_idx` (`modulnr`),
  KEY `pruef_abschlussarbeit_idx` (`abschlussarbeitnr`),
  CONSTRAINT `pruef_abschlussarbeit` FOREIGN KEY (`abschlussarbeitnr`) REFERENCES `abschlussarbeit` (`idabschlussarbeit`),
  CONSTRAINT `pruef_modul` FOREIGN KEY (`modulnr`) REFERENCES `module` (`modulnr`)
) ENGINE=InnoDB AUTO_INCREMENT=558148 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `matrikelnr` int(11) NOT NULL,
  `vorname` varchar(45) DEFAULT NULL,
  `nachname` varchar(45) DEFAULT NULL,
  `kuerzel` varchar(7) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `studiengang` int(11) DEFAULT NULL,
  PRIMARY KEY (`matrikelnr`),
  KEY `fk_student_1_idx` (`studiengang`),
  CONSTRAINT `fk_student_1` FOREIGN KEY (`studiengang`) REFERENCES `studiengang` (`idstudiengang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `studiengang`
--

DROP TABLE IF EXISTS `studiengang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `studiengang` (
  `idstudiengang` int(11) NOT NULL,
  `bezeichnung` varchar(45) DEFAULT NULL,
  `semesteranz` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idstudiengang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-28 15:42:26

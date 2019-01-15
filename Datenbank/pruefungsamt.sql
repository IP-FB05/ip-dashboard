CREATE DATABASE  IF NOT EXISTS `pruefungsamt` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `pruefungsamt`;
-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: pruefungsamt
-- ------------------------------------------------------
-- Server version	5.7.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `modulnr` int(11) NOT NULL,
  `modultext` varchar(100) DEFAULT NULL,
  `beschreibung` text,
  `leistungspunkte` int(11) DEFAULT NULL,
  PRIMARY KEY (`modulnr`),
  UNIQUE KEY `pruefungstext_UNIQUE` (`modultext`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_studiengang`
--

DROP TABLE IF EXISTS `module_studiengang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_studiengang` (
  `modul` int(11) NOT NULL,
  `studiengang` int(11) NOT NULL,
  `semester` int(11) DEFAULT NULL,
  PRIMARY KEY (`modul`,`studiengang`),
  KEY `fk_pruefung_studiengang_1_idx` (`studiengang`),
  CONSTRAINT `fk_module_studiengang_1` FOREIGN KEY (`modul`) REFERENCES `module` (`modulnr`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_module_studiengang_2` FOREIGN KEY (`studiengang`) REFERENCES `studiengang` (`idstudiengang`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_studiengang`
--

LOCK TABLES `module_studiengang` WRITE;
/*!40000 ALTER TABLE `module_studiengang` DISABLE KEYS */;
/*!40000 ALTER TABLE `module_studiengang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pruefung_student`
--

DROP TABLE IF EXISTS `pruefung_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pruefung_student` (
  `student` int(11) NOT NULL,
  `pruefung` int(11) NOT NULL,
  `note` float DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`student`,`pruefung`),
  KEY `fk_pruefung_student_2_idx` (`pruefung`),
  CONSTRAINT `fk_pruefung_student_1` FOREIGN KEY (`student`) REFERENCES `student` (`matrikelnr`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pruefung_student_2` FOREIGN KEY (`pruefung`) REFERENCES `pruefungen` (`pruefungsnr`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pruefung_student`
--

LOCK TABLES `pruefung_student` WRITE;
/*!40000 ALTER TABLE `pruefung_student` DISABLE KEYS */;
/*!40000 ALTER TABLE `pruefung_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pruefungen`
--

DROP TABLE IF EXISTS `pruefungen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pruefungen` (
  `pruefungsnr` int(11) NOT NULL,
  `modulnr` varchar(45) DEFAULT NULL,
  `pruefungszeitpunkt` datetime DEFAULT NULL,
  PRIMARY KEY (`pruefungsnr`),
  KEY `fk_pruefungen_1_idx` (`modulnr`),
  CONSTRAINT `fk_pruefungen_1` FOREIGN KEY (`modulnr`) REFERENCES `module` (`modultext`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pruefungen`
--

LOCK TABLES `pruefungen` WRITE;
/*!40000 ALTER TABLE `pruefungen` DISABLE KEYS */;
/*!40000 ALTER TABLE `pruefungen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `matrikelnr` int(11) NOT NULL,
  `vorname` varchar(45) DEFAULT NULL,
  `nachname` varchar(45) DEFAULT NULL,
  `kuerzel` varchar(7) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `studiengang` int(11) DEFAULT NULL,
  PRIMARY KEY (`matrikelnr`),
  KEY `fk_student_1_idx` (`studiengang`),
  CONSTRAINT `fk_student_1` FOREIGN KEY (`studiengang`) REFERENCES `studiengang` (`idstudiengang`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studiengang`
--

DROP TABLE IF EXISTS `studiengang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studiengang` (
  `idstudiengang` int(11) NOT NULL,
  `bezeichnung` varchar(45) DEFAULT NULL,
  `semesteranz` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idstudiengang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studiengang`
--

LOCK TABLES `studiengang` WRITE;
/*!40000 ALTER TABLE `studiengang` DISABLE KEYS */;
/*!40000 ALTER TABLE `studiengang` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-22 14:09:31

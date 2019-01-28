-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com    Database: dashboardDB
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
-- Table structure for table `allowed_groups`
--

DROP TABLE IF EXISTS `allowed_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `allowed_groups` (
  `processes_processID` int(11) NOT NULL,
  `usergroups_usergroup_id` int(11) NOT NULL,
  PRIMARY KEY (`processes_processID`,`usergroups_usergroup_id`),
  KEY `fk_processes_has_usergroups_usergroups1_idx` (`usergroups_usergroup_id`),
  KEY `fk_processes_has_usergroups_processes1_idx` (`processes_processID`),
  CONSTRAINT `fk_processes_has_usergroups_processes1` FOREIGN KEY (`processes_processID`) REFERENCES `processes` (`processid`),
  CONSTRAINT `fk_processes_has_usergroups_usergroups1` FOREIGN KEY (`usergroups_usergroup_id`) REFERENCES `usergroups` (`usergroup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allowed_groups`
--

LOCK TABLES `allowed_groups` WRITE;
/*!40000 ALTER TABLE `allowed_groups` DISABLE KEYS */;
INSERT INTO `allowed_groups` VALUES (1211,1),(1213,1),(1217,1),(1219,1),(1220,1),(1221,1),(1222,1),(1224,1),(1211,2),(1213,2),(1217,2),(1218,2),(1219,2),(1220,2),(1221,2),(1222,2),(1224,2),(1221,3),(1224,3),(1221,4),(1224,4),(1211,5),(1213,5),(1217,5),(1218,5),(1219,5),(1220,5),(1221,5),(1222,5),(1224,5),(1211,6),(1221,6),(1224,6);
/*!40000 ALTER TABLE `allowed_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `categoryID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Informatik'),(2,'MCD'),(3,'Elektrotechnik'),(4,'Termine und Fristen'),(14,'AngularTEst222');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `documents` (
  `documentID` int(11) NOT NULL AUTO_INCREMENT,
  `categoryID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `lastChanged` date NOT NULL,
  `link` varchar(500) NOT NULL,
  `categoriename` varchar(255) DEFAULT NULL,
  `last_changed` date DEFAULT NULL,
  PRIMARY KEY (`documentID`),
  KEY `kategorieID_idx` (`categoryID`),
  CONSTRAINT `category` FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (48,2,'Prüfungsordnung','2018-05-11','https://www.fh-aachen.de/downloads/fh-mitteilungen/pruefungsordnungen/elektrotechnik-und-informationstechnik/bachelor/mcd-abws1516/?no_cache=1&download=2018_112_PO_B_MCD_StBabWS1819_korr.pdf&did=4581',NULL,NULL),(49,3,'Prüfungsordnung','2018-01-27','https://www.fh-aachen.de/downloads/fh-mitteilungen/pruefungsordnungen/elektrotechnik-und-informationstechnik/bachelor/elektrotechnik-abws1516/?no_cache=1&download=2018_167_PO_B_ETechnik_lf_StBabWS1819.pdf&did=4569',NULL,NULL),(50,1,'Prüfungsordnung','2018-07-22','https://www.fh-aachen.de/downloads/fh-mitteilungen/pruefungsordnungen/elektrotechnik-und-informationstechnik/bachelor/informatik-abws1516/?no_cache=1&download=2018_111_PO_B_Informatik_lf_StBabWS1819_k.pdf&did=4575',NULL,NULL),(51,4,'Prüfungen | Januar/Februar 2019','2018-11-21','https://www.fh-aachen.de/fileadmin/fb/fb05_elektrotechnik/Rechenzentrale/Termine_und_Fristen/2019_Klausurplan_JanFeb_v6.pdf',NULL,NULL),(52,4,'Prüfungen | März 2019','2018-11-21','https://www.fh-aachen.de/fileadmin/fb/fb05_elektrotechnik/Rechenzentrale/Termine_und_Fristen/2019_Klausurplan_M%C3%A4rz_v4.pdf',NULL,NULL),(141,3,'Klausurphase3','2019-01-25','http://ip-dash.ddnss.ch:9090/files/(prof)2019_Klausurplan_JanFeb_v6 (1) - Kopie - Kopie - Kopie.pdf',NULL,NULL),(142,2,'Klausurphase 4','2019-01-25','http://ip-dash.ddnss.ch:9090/files/(PA)2019_Klausurplan_JanFeb_v6 (1) - Kopie - Kopie.pdf',NULL,NULL),(154,3,'Anambabam','2019-01-28','http://localhost:9090/files/Report6ee47370-e563-4405-9993-37f41e015f47.pdf',NULL,NULL),(155,1,'ab','2019-01-28','http://localhost:9090/files/Organisation_Infos.pdf',NULL,NULL);
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (15),(15),(15);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notification` (
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES ('aa1234s'),('bb1234m');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_instance`
--

DROP TABLE IF EXISTS `process_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `process_instance` (
  `instanceID` int(11) NOT NULL AUTO_INCREMENT,
  `camunda_instanceID` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`instanceID`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_instance`
--

LOCK TABLES `process_instance` WRITE;
/*!40000 ALTER TABLE `process_instance` DISABLE KEYS */;
INSERT INTO `process_instance` VALUES (42,'b379b2df-21c2-11e9-a13c-b4b686e9bbd5'),(43,'91fd3301-21c3-11e9-a13c-b4b686e9bbd5'),(44,'9d65bb64-21c3-11e9-a13c-b4b686e9bbd5'),(45,'aa917797-21c3-11e9-a13c-b4b686e9bbd5'),(46,'acb2aa0a-21c3-11e9-a13c-b4b686e9bbd5'),(47,'237901a4-21c4-11e9-a13c-b4b686e9bbd5'),(48,'016968c7-21c5-11e9-a13c-b4b686e9bbd5'),(49,'2b63deb1-21c5-11e9-a13c-b4b686e9bbd5'),(50,'5318f885-21c5-11e9-a13c-b4b686e9bbd5'),(51,'08c46312-21c6-11e9-a13c-b4b686e9bbd5'),(52,'4ead1dd5-21c6-11e9-a13c-b4b686e9bbd5'),(53,'53e309c7-21c6-11e9-aaee-d0509993700d'),(54,'6488f90f-21c6-11e9-a13c-b4b686e9bbd5'),(55,'8c2951dc-21c6-11e9-aaee-d0509993700d'),(56,'b22dd503-21c6-11e9-aaee-d0509993700d'),(57,'4510e976-21c9-11e9-a13c-b4b686e9bbd5'),(58,'d43c7448-21cb-11e9-a13c-b4b686e9bbd5'),(59,'f3d809bb-21cb-11e9-a13c-b4b686e9bbd5'),(60,'1638d8ce-21cc-11e9-a13c-b4b686e9bbd5'),(61,'3bf5a5db-223b-11e9-a13c-b4b686e9bbd5'),(62,'791e1775-223f-11e9-a13c-b4b686e9bbd5'),(63,'ab649361-223f-11e9-a13c-b4b686e9bbd5'),(64,'c6b5d514-223f-11e9-a13c-b4b686e9bbd5'),(65,'dd29dba8-2240-11e9-a13c-b4b686e9bbd5'),(66,'97d1f664-2248-11e9-a13c-b4b686e9bbd5'),(67,'4042fe11-2249-11e9-a13c-b4b686e9bbd5'),(68,'c41ddc3b-2250-11e9-a13c-b4b686e9bbd5'),(69,'c012547e-2252-11e9-a13c-b4b686e9bbd5'),(70,'ddcb4a56-2256-11e9-a13c-b4b686e9bbd5'),(71,'2f4040fe-2257-11e9-a13c-b4b686e9bbd5'),(72,'89c2b9e6-2257-11e9-a13c-b4b686e9bbd5'),(73,'e9755d68-2258-11e9-a13c-b4b686e9bbd5'),(74,'03557101-225b-11e9-a13c-b4b686e9bbd5'),(75,'b5375a7a-225c-11e9-a13c-b4b686e9bbd5'),(76,'ef057b12-225c-11e9-a13c-b4b686e9bbd5'),(77,'52332ffa-225d-11e9-a13c-b4b686e9bbd5'),(78,'e0f81466-2260-11e9-a13c-b4b686e9bbd5'),(79,'813b7f3e-2262-11e9-a13c-b4b686e9bbd5'),(80,'fd88d6b2-2267-11e9-a13c-b4b686e9bbd5'),(81,'a54e8408-2268-11e9-a13c-b4b686e9bbd5'),(82,'69050d9e-2269-11e9-a13c-b4b686e9bbd5'),(83,'9b8dc2e9-2269-11e9-a13c-b4b686e9bbd5'),(84,'05033031-226f-11e9-a13c-b4b686e9bbd5'),(85,'e489a169-226f-11e9-a13c-b4b686e9bbd5'),(86,'cb3008b5-22f7-11e9-a13c-b4b686e9bbd5'),(87,'bd8ebccc-22f9-11e9-a13c-b4b686e9bbd5'),(88,'4d67940b-22fb-11e9-a13c-b4b686e9bbd5'),(89,'cf0c3f39-22fc-11e9-a13c-b4b686e9bbd5'),(90,'99c530ce-2300-11e9-a13c-b4b686e9bbd5'),(91,'c00ced7a-2302-11e9-a13c-b4b686e9bbd5'),(92,'8cc1603f-2306-11e9-a13c-b4b686e9bbd5');
/*!40000 ALTER TABLE `process_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_instance_has_subs`
--

DROP TABLE IF EXISTS `process_instance_has_subs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `process_instance_has_subs` (
  `process_instance_instanceID` int(11) NOT NULL,
  `subs_subID` int(11) NOT NULL,
  PRIMARY KEY (`process_instance_instanceID`,`subs_subID`),
  KEY `fk_process_instance_has_subs_subs1_idx` (`subs_subID`),
  KEY `fk_process_instance_has_subs_process_instance1_idx` (`process_instance_instanceID`),
  CONSTRAINT `fk_process_instance_has_subs_process_instance1` FOREIGN KEY (`process_instance_instanceID`) REFERENCES `process_instance` (`instanceid`),
  CONSTRAINT `fk_process_instance_has_subs_subs1` FOREIGN KEY (`subs_subID`) REFERENCES `subs` (`subID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_instance_has_subs`
--

LOCK TABLES `process_instance_has_subs` WRITE;
/*!40000 ALTER TABLE `process_instance_has_subs` DISABLE KEYS */;
INSERT INTO `process_instance_has_subs` VALUES (56,13);
/*!40000 ALTER TABLE `process_instance_has_subs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processes`
--

DROP TABLE IF EXISTS `processes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `processes` (
  `processID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` mediumtext NOT NULL,
  `verbal` longtext NOT NULL,
  `bpmn` varchar(200) NOT NULL,
  `added` date DEFAULT NULL,
  `camunda_processID` varchar(150) DEFAULT 'None',
  `allowed_usergroups` varchar(100) DEFAULT 'None',
  `creator` varchar(100) DEFAULT NULL,
  `published` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`processID`)
) ENGINE=InnoDB AUTO_INCREMENT=1225 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processes`
--

LOCK TABLES `processes` WRITE;
/*!40000 ALTER TABLE `processes` DISABLE KEYS */;
INSERT INTO `processes` VALUES (1209,'Masterarbeit','BeschreibungOhneLeerzeichen','VerbaleBeschreibungOhneLeerzeichen','http://ip-dash.ddnss.ch:9090/files/Masterarbeit.bpmn','2019-01-26','masterarbeit:1:733dc6d4-21bb-11e9-a13c-b4b686e9bbd5','admin,professor,pruefungsamt,student,','demo',1),(1211,'Bachelorarbeit','BBachelorarbeitBeschreibung','BachelorarbeitVerbal','http://ip-dash.ddnss.ch:9090/files/Bachelorarbeit.bpmn','2019-01-27','bachelorarbeit:1:b5936b61-21c2-11e9-a13c-b4b686e9bbd5','admin,professor,pruefungsamt,student,','demo',1),(1213,'Praxissemester','praxissemesterKurzbeschreibung','Praxissemester','http://ip-dash.ddnss.ch:9090/files/Praxissemester.bpmn','2019-01-27','praxissemester:1:c9368cc6-223f-11e9-a13c-b4b686e9bbd5','admin,professor,student,','cc1234s',1),(1217,'aSF','ASF','af','http://ip-dash.ddnss.ch:9090/files/Praxissemester.bpmn','2019-01-27','null','student,admin,professor,','demo',1),(1218,'Modulabmeldung','modulabmeldung','modulabmeldung','http://ip-dash.ddnss.ch:9090/files/Modulabmeldung.bpmn','2019-01-27','modulabmeldung:1:aced595f-2268-11e9-a13c-b4b686e9bbd5','admin,student,','demo',1),(1219,'asf','ag','adgf','http://ip-dash.ddnss.ch:9090/files/Praxissemester.bpmn','2019-01-27','null','student,admin,professor,','demo',1),(1220,'TestNeu','abc','def','http://ip-dash.ddnss.ch:9090/files/Praxissemester.bpmn','2019-01-27','null','student,admin,professor,','demo',1),(1221,'Kapazitaetsplanung','wmv','wmv','http://ip-dash.ddnss.ch:9090/files/Kapazitaetsplanung.bpmn','2019-01-27','null','admin,gast,mitarbeiter,professor,pruefungsamt,student','demo',1),(1222,'Praxissemester','kurz','lang','http://ip-dash.ddnss.ch:9090/files/Praxissemester.bpmn','2019-01-27','Process_0ag6eaj:1:affd78f3-226b-11e9-a13c-b4b686e9bbd5','student,admin,professor,','demo',1),(1224,'EvaluationLV','eva','eva','http://ip-dash.ddnss.ch:9090/files/null.bpmn','2019-01-28','null','admin,gast,mitarbeiter,professor,pruefungsamt,student','cc1234s',1);
/*!40000 ALTER TABLE `processes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processes_has_process_instance`
--

DROP TABLE IF EXISTS `processes_has_process_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `processes_has_process_instance` (
  `processes_processID` int(11) NOT NULL,
  `process_instance_instanceID` int(11) NOT NULL,
  PRIMARY KEY (`processes_processID`,`process_instance_instanceID`),
  KEY `fk_processes_has_process_instance_process_instance1_idx` (`process_instance_instanceID`),
  KEY `fk_processes_has_process_instance_processes1_idx` (`processes_processID`),
  CONSTRAINT `fk_processes_has_process_instance_process_instance1` FOREIGN KEY (`process_instance_instanceID`) REFERENCES `process_instance` (`instanceid`),
  CONSTRAINT `fk_processes_has_process_instance_processes1` FOREIGN KEY (`processes_processID`) REFERENCES `processes` (`processID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processes_has_process_instance`
--

LOCK TABLES `processes_has_process_instance` WRITE;
/*!40000 ALTER TABLE `processes_has_process_instance` DISABLE KEYS */;
INSERT INTO `processes_has_process_instance` VALUES (1211,43),(1211,44),(1211,45),(1211,47),(1209,50),(1211,57),(1211,58),(1209,78),(1209,79),(1218,82),(1218,83),(1211,84),(1211,85),(1209,86),(1209,88),(1211,91);
/*!40000 ALTER TABLE `processes_has_process_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processes_has_subs`
--

DROP TABLE IF EXISTS `processes_has_subs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `processes_has_subs` (
  `processes_processID` int(11) NOT NULL,
  `subs_subID` int(11) NOT NULL,
  PRIMARY KEY (`processes_processID`,`subs_subID`),
  KEY `fk_processes_has_subs_subs1_idx` (`subs_subID`),
  KEY `fk_processes_has_subs_processes1_idx` (`processes_processID`),
  CONSTRAINT `fk_processes_has_subs_processes1` FOREIGN KEY (`processes_processID`) REFERENCES `processes` (`processID`),
  CONSTRAINT `fk_processes_has_subs_subs1` FOREIGN KEY (`subs_subID`) REFERENCES `subs` (`subID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processes_has_subs`
--

LOCK TABLES `processes_has_subs` WRITE;
/*!40000 ALTER TABLE `processes_has_subs` DISABLE KEYS */;
INSERT INTO `processes_has_subs` VALUES (1211,12),(1209,14);
/*!40000 ALTER TABLE `processes_has_subs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nb4h0p6txrmfc0xbrd1kglp9t` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subs`
--

DROP TABLE IF EXISTS `subs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subs` (
  `subID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`subID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subs`
--

LOCK TABLES `subs` WRITE;
/*!40000 ALTER TABLE `subs` DISABLE KEYS */;
INSERT INTO `subs` VALUES (14,'aa1234s'),(13,'cc1234s'),(12,'demo');
/*!40000 ALTER TABLE `subs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systems`
--

DROP TABLE IF EXISTS `systems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `systems` (
  `systemID` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(5000) DEFAULT 'Keine Beschreibung vorhanden',
  `link` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`systemID`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systems`
--

LOCK TABLES `systems` WRITE;
/*!40000 ALTER TABLE `systems` DISABLE KEYS */;
INSERT INTO `systems` VALUES (4,'Der webbasierte Studienplaner CAMPUS-Office ist Teil des integrierten CAMPUS-Informationssystems der FH Aachen und erlaubt den Studierenden direkten Zugriff auf ihre persönlichen Vorlesungs- und Veranstaltungsdaten. Damit steht Ihnen ein Werkzeug zur Verfügung, das eine optimierte Planung und Verwaltung Ihres Studiums ermöglicht.','https://www.campusoffice.fh-aachen.de','CampusOffice'),(142,'Ilias eLearning','https://www.ili.fh-aachen.de/goto_elearning_root_1.html','Ilias'),(144,'Ilias 3','https://www.ili.fh-aachen.de/goto_elearning_root_1.html','Ilias 3'),(145,'Ilias 4','https://www.ili.fh-aachen.de/goto_elearning_root_1.html','Ilias 4');
/*!40000 ALTER TABLE `systems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroups`
--

DROP TABLE IF EXISTS `usergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usergroups` (
  `usergroup_id` int(11) NOT NULL AUTO_INCREMENT,
  `usergroup_name` varchar(45) NOT NULL,
  PRIMARY KEY (`usergroup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroups`
--

LOCK TABLES `usergroups` WRITE;
/*!40000 ALTER TABLE `usergroups` DISABLE KEYS */;
INSERT INTO `usergroups` VALUES (1,'professor'),(2,'student'),(3,'gast'),(4,'mitarbeiter'),(5,'admin'),(6,'pruefungsamt');
/*!40000 ALTER TABLE `usergroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-28 15:40:57

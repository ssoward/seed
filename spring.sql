-- MySQL dump 10.13  Distrib 5.6.16, for osx10.9 (x86_64)
--
-- Host: localhost    Database: spring
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorities` (
  `username` char(50) NOT NULL,
  `authority` char(50) NOT NULL,
  KEY `fk_authorities_users` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('user@me.com','ROLE_ADMIN'),('normal@me.com','ROLE_AUTH'),('test@me.com','ROLE_AUTH'),('youthemandme@gmail.com','ROLE_ADMIN');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `awards`
--

DROP TABLE IF EXISTS `awards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `awards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `createdDt` datetime DEFAULT NULL,
  `createdBy` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `cost` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=119 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `awards`
--

LOCK TABLES `awards` WRITE;
/*!40000 ALTER TABLE `awards` DISABLE KEYS */;
INSERT INTO `awards` VALUES (117,'IMG_5125.JPG','2014-04-18 16:43:43','user@me.com','scottTest','1'),(118,'IMG_5145.JPG','2014-04-18 17:00:13','user@me.com','kidsUpsidedown','99');
/*!40000 ALTER TABLE `awards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compliments`
--

DROP TABLE IF EXISTS `compliments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compliments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `createdBy` varchar(40) DEFAULT NULL,
  `createdDt` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compliments`
--

LOCK TABLES `compliments` WRITE;
/*!40000 ALTER TABLE `compliments` DISABLE KEYS */;
INSERT INTO `compliments` VALUES (36,'Evidentiary Family Restoration - Getting a youth home and keeping it that way!','user@me.com','2014-03-05'),(35,'The Force for Familes - Getting it done when the family needed it the most!','user@me.com','2014-03-05'),(37,'Super Hero - Great support when needed it the most!','user@me.com','2014-03-05'),(38,'Flying High - Clinical skill beyond compare!','user@me.com','2014-03-05'),(39,'Kite Flying - Going above and beyond!','user@me.com','2014-03-05'),(40,'Great support when needed it the most!','user@me.com','2014-03-05'),(41,'Putting Families first!','user@me.com','2014-03-05'),(42,'Super Hero Clinical Skills','user@me.com','2014-03-05'),(43,'Hello world','user@me.com','2014-03-05');
/*!40000 ALTER TABLE `compliments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `give`
--

DROP TABLE IF EXISTS `give`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `give` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(100) NOT NULL,
  `receivedDt` datetime NOT NULL,
  `givenDt` datetime DEFAULT NULL,
  `receivedBy` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `giveType` varchar(100) DEFAULT NULL,
  `spentDt` datetime DEFAULT NULL,
  `praise` int(11) DEFAULT NULL,
  `award` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=174 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `give`
--

LOCK TABLES `give` WRITE;
/*!40000 ALTER TABLE `give` DISABLE KEYS */;
INSERT INTO `give` VALUES (159,'user@me.com','2014-03-10 23:49:16','2014-03-10 23:49:20','MONTHLY','EXPIRED','MONTHLY_USED',NULL,63,NULL),(160,'user@me.com','2014-03-10 23:49:16','2014-03-10 23:49:24','MONTHLY','EXPIRED','MONTHLY_USED',NULL,64,NULL),(161,'user@me.com','2014-03-10 23:49:16','2014-03-10 23:49:28','MONTHLY','EXPIRED','MONTHLY_USED',NULL,65,NULL),(162,'user@me.com','2014-03-10 23:49:28',NULL,'PARTICIPATION','EXPIRED','PARTICIPATION',NULL,66,NULL),(163,'test@me.com','2014-03-10 23:49:55','2014-03-10 23:50:01','MONTHLY','GIVEN_SPENT','MONTHLY_USED','2014-04-18 23:41:22',67,NULL),(164,'test@me.com','2014-03-10 23:49:55','2014-03-10 23:50:08','MONTHLY','GIVEN_SPENT','MONTHLY_USED','2014-04-18 23:42:21',68,NULL),(165,'test@me.com','2014-03-10 23:49:55','2014-03-10 23:50:14','MONTHLY','GIVEN','MONTHLY_USED',NULL,69,NULL),(166,'test@me.com','2014-03-10 23:50:14',NULL,'PARTICIPATION','GIVEN','PARTICIPATION',NULL,70,NULL),(167,'user@me.com','2014-04-03 20:13:36','2014-04-18 22:39:17','MONTHLY','TOBE_GIVEN','MONTHLY_USED','2014-04-18 23:10:30',80,NULL),(168,'user@me.com','2014-04-03 20:13:36','2014-04-18 22:50:16','MONTHLY','TOBE_GIVEN','MONTHLY_USED',NULL,81,NULL),(169,'user@me.com','2014-04-03 20:13:36','2014-04-18 22:25:46','MONTHLY','TOBE_GIVEN','MONTHLY_USED',NULL,73,NULL),(172,'user@me.com','2014-04-18 22:04:23','2014-04-18 22:26:28','ADMIN','TOBE_GIVEN','ADMIN',NULL,75,NULL),(173,'user@me.com','2014-04-18 22:25:46',NULL,'PARTICIPATION','TOBE_GIVEN','PARTICIPATION',NULL,74,NULL);
/*!40000 ALTER TABLE `give` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `praise`
--

DROP TABLE IF EXISTS `praise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `praise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `praiser` varchar(45) NOT NULL,
  `praisee` varchar(45) NOT NULL,
  `comment` varchar(200) DEFAULT NULL,
  `praise` varchar(200) NOT NULL,
  `praise_dt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `praise`
--

LOCK TABLES `praise` WRITE;
/*!40000 ALTER TABLE `praise` DISABLE KEYS */;
INSERT INTO `praise` VALUES (63,'user@me.com','test@me.com','dsafafd','35','2014-03-10 23:49:20'),(64,'user@me.com','youthemandme@gmail.com','asdasdf','37','2014-03-10 23:49:24'),(65,'user@me.com','youthemandme@gmail.com','asdasd','37','2014-03-10 23:49:28'),(66,'PARTICIPATION','user@me.com',NULL,'PARTICIPATION','2014-03-10 23:49:28'),(67,'test@me.com','user@me.com','asd','38','2014-03-10 23:50:01'),(68,'test@me.com','user@me.com','asdasd fasd','38','2014-03-10 23:50:08'),(69,'test@me.com','youthemandme@gmail.com','asdasdf','39','2014-03-10 23:50:14'),(70,'PARTICIPATION','test@me.com',NULL,'PARTICIPATION','2014-03-10 23:50:14'),(71,'user@me.com','youthemandme@gmail.com','you rock dudette!','39','2014-04-18 22:23:28'),(72,'user@me.com','test@me.com','dfasdfasdf adsf','40','2014-04-18 22:25:03'),(73,'user@me.com','youthemandme@gmail.com','asd','38','2014-04-18 22:25:46'),(74,'PARTICIPATION','user@me.com',NULL,'PARTICIPATION','2014-04-18 22:25:46'),(75,'user@me.com','youthemandme@gmail.com','asd','38','2014-04-18 22:26:28'),(76,'user@me.com','youthemandme@gmail.com','asd','43','2014-04-18 22:27:46'),(77,'user@me.com','youthemandme@gmail.com','asdasd','43','2014-04-18 22:28:14'),(78,'user@me.com','youthemandme@gmail.com','dfadfa','40','2014-04-18 22:30:05'),(79,'user@me.com','youthemandme@gmail.com','asd','38','2014-04-18 22:32:02'),(80,'user@me.com','youthemandme@gmail.com','asdasdf asdf asdasdf a','38','2014-04-18 22:39:17'),(81,'user@me.com','youthemandme@gmail.com','dsffsefsd','38','2014-04-18 22:50:16');
/*!40000 ALTER TABLE `praise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` char(50) NOT NULL,
  `password` char(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `first_name` char(50) NOT NULL,
  `last_name` char(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('user@me.com','scott',1,'scott louis','soward'),('youthemandme@gmail.com','password',1,'mike','soward'),('test@me.com','scott',1,'test','last');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-18 23:44:13

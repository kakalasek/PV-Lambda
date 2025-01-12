/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.10-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: garden
-- ------------------------------------------------------
-- Server version	10.11.10-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `garden`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `garden` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;

USE `garden`;

--
-- Table structure for table `Flowerbed`
--

DROP TABLE IF EXISTS `Flowerbed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Flowerbed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `size` decimal(10,2) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Flowerbed`
--

LOCK TABLES `Flowerbed` WRITE;
/*!40000 ALTER TABLE `Flowerbed` DISABLE KEYS */;
INSERT INTO `Flowerbed` VALUES
(1,1.20,1),
(2,5.12,2),
(3,6.30,3),
(4,3.13,4),
(5,2.69,5);
/*!40000 ALTER TABLE `Flowerbed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Packaging`
--

DROP TABLE IF EXISTS `Packaging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Packaging` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expiration_date` date DEFAULT NULL,
  `number_of_seeds` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Packaging`
--

LOCK TABLES `Packaging` WRITE;
/*!40000 ALTER TABLE `Packaging` DISABLE KEYS */;
INSERT INTO `Packaging` VALUES
(1,'2026-06-24',15),
(2,'2028-10-30',40),
(3,'2025-08-10',10);
/*!40000 ALTER TABLE `Packaging` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Plant`
--

DROP TABLE IF EXISTS `Plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Plant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `life_length` enum('Annual','Perennial','Biennial') DEFAULT NULL,
  `growing_time` int(11) DEFAULT NULL,
  `spacing` int(11) DEFAULT NULL,
  `planting_depth` int(11) DEFAULT NULL,
  `planting_time` int(11) DEFAULT NULL,
  `pre_growing` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plant`
--

LOCK TABLES `Plant` WRITE;
/*!40000 ALTER TABLE `Plant` DISABLE KEYS */;
INSERT INTO `Plant` VALUES
(1,'bazalka prava','Annual',42,30,1,3,0),
(2,'mrkev darina','Biennial',170,8,3,3,0),
(3,'hrach ambrosia','Annual',80,20,4,4,0);
/*!40000 ALTER TABLE `Plant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `Planted_plants`
--

DROP TABLE IF EXISTS `Planted_plants`;
/*!50001 DROP VIEW IF EXISTS `Planted_plants`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `Planted_plants` AS SELECT
 1 AS `Planting_id`,
  1 AS `Plant_Name`,
  1 AS `Life_Length`,
  1 AS `Time_To_Grow`,
  1 AS `Spacing`,
  1 AS `Depth_Of_Planting`,
  1 AS `Month_To_Plant`,
  1 AS `Pre_Growing`,
  1 AS `Date_Of_Planting`,
  1 AS `Date_Of_Disposal`,
  1 AS `Number_Of_Seeds`,
  1 AS `Flowerbed_Number`,
  1 AS `Flowerbed_Size` */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `Planting`
--

DROP TABLE IF EXISTS `Planting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Planting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_from` date NOT NULL,
  `date_to` date DEFAULT NULL,
  `number_of_seeds` int(11) NOT NULL,
  `plant_id` int(11) NOT NULL,
  `flowerbed_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Planting_Plant` (`plant_id`),
  KEY `FK_Planting_Flowerbed` (`flowerbed_id`),
  CONSTRAINT `FK_Planting_Flowerbed` FOREIGN KEY (`flowerbed_id`) REFERENCES `Flowerbed` (`id`),
  CONSTRAINT `FK_Planting_Plant` FOREIGN KEY (`plant_id`) REFERENCES `Plant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Planting`
--

LOCK TABLES `Planting` WRITE;
/*!40000 ALTER TABLE `Planting` DISABLE KEYS */;
INSERT INTO `Planting` VALUES
(1,'2023-03-15','2023-09-12',2,2,1),
(2,'2024-03-16','2023-09-12',4,2,3),
(3,'2024-04-20',NULL,20,3,2);
/*!40000 ALTER TABLE `Planting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Storage`
--

DROP TABLE IF EXISTS `Storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plant_id` int(11) NOT NULL,
  `packaging_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `packaging_id` (`packaging_id`),
  KEY `FK_Storage_Plant` (`plant_id`),
  CONSTRAINT `FK_Storage_Packaging` FOREIGN KEY (`packaging_id`) REFERENCES `Packaging` (`id`),
  CONSTRAINT `FK_Storage_Plant` FOREIGN KEY (`plant_id`) REFERENCES `Plant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Storage`
--

LOCK TABLES `Storage` WRITE;
/*!40000 ALTER TABLE `Storage` DISABLE KEYS */;
INSERT INTO `Storage` VALUES
(1,1,1),
(2,1,2),
(3,2,3);
/*!40000 ALTER TABLE `Storage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `Stored_plants`
--

DROP TABLE IF EXISTS `Stored_plants`;
/*!50001 DROP VIEW IF EXISTS `Stored_plants`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `Stored_plants` AS SELECT
 1 AS `Stored_Plant_id`,
  1 AS `Plant_Name`,
  1 AS `Life_Length`,
  1 AS `Time_To_Grow`,
  1 AS `Spacing`,
  1 AS `Depth_Of_Planting`,
  1 AS `Month_To_Plant`,
  1 AS `Pre_Growing`,
  1 AS `Expiration_Date`,
  1 AS `Number_Of_Seeds_In_Package` */;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'garden'
--
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
/*!50003 DROP PROCEDURE IF EXISTS `plant_seeds` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
DELIMITER ;;
CREATE DEFINER=`gardenAdmin`@`localhost` PROCEDURE `plant_seeds`(IN var_id INT, IN var_number_of_seeds INT)
BEGIN

		IF var_number_of_seeds <= 0 THEN
			DELETE FROM Storage
			WHERE packaging_id = var_id;
			DELETE FROM Packaging
			WHERE id = var_id;
		ELSE
			UPDATE Packaging SET number_of_seeds = var_number_of_seeds WHERE id = var_id;
		END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remove_seeds` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
DELIMITER ;;
CREATE DEFINER=`gardenAdmin`@`localhost` PROCEDURE `remove_seeds`(IN var_id INT)
BEGIN
	
	DELETE FROM Storage WHERE id = var_id;
	DELETE FROM Packaging WHERE id = (SELECT packaging_id FROM Storage WHERE id = var_id);
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Current Database: `garden`
--

USE `garden`;

--
-- Final view structure for view `Planted_plants`
--

/*!50001 DROP VIEW IF EXISTS `Planted_plants`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`gardenAdmin`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `Planted_plants` AS select `Planting`.`id` AS `Planting_id`,`Plant`.`name` AS `Plant_Name`,`Plant`.`life_length` AS `Life_Length`,`Plant`.`growing_time` AS `Time_To_Grow`,`Plant`.`spacing` AS `Spacing`,`Plant`.`planting_depth` AS `Depth_Of_Planting`,`Plant`.`planting_time` AS `Month_To_Plant`,`Plant`.`pre_growing` AS `Pre_Growing`,`Planting`.`date_from` AS `Date_Of_Planting`,`Planting`.`date_to` AS `Date_Of_Disposal`,`Planting`.`number_of_seeds` AS `Number_Of_Seeds`,`Flowerbed`.`number` AS `Flowerbed_Number`,`Flowerbed`.`size` AS `Flowerbed_Size` from ((`Planting` join `Plant` on(`Planting`.`plant_id` = `Plant`.`id`)) join `Flowerbed` on(`Planting`.`flowerbed_id` = `Flowerbed`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `Stored_plants`
--

/*!50001 DROP VIEW IF EXISTS `Stored_plants`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`gardenAdmin`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `Stored_plants` AS select `Storage`.`id` AS `Stored_Plant_id`,`Plant`.`name` AS `Plant_Name`,`Plant`.`life_length` AS `Life_Length`,`Plant`.`growing_time` AS `Time_To_Grow`,`Plant`.`spacing` AS `Spacing`,`Plant`.`planting_depth` AS `Depth_Of_Planting`,`Plant`.`planting_time` AS `Month_To_Plant`,`Plant`.`pre_growing` AS `Pre_Growing`,`Packaging`.`expiration_date` AS `Expiration_Date`,`Packaging`.`number_of_seeds` AS `Number_Of_Seeds_In_Package` from ((`Storage` join `Plant` on(`Storage`.`plant_id` = `Plant`.`id`)) join `Packaging` on(`Storage`.`packaging_id` = `Packaging`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-12 23:46:11

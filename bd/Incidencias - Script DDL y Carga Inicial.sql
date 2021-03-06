-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 10.0.1.63    Database: incidencias
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

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
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuracion` (
  `EMPRESA_CONSEJERIA_NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL DEFAULT '100',
  `EMPRESA_CONSEJERIA_TELEFONO` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `EMPRESA_CONSEJERIA_EMAIL` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `IES_NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `IES_CIF` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `IES_CODIGO_CENTRO` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `IES_PERSONA_CONTACTO_NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `IES_PERSONA_CONTACTO_APELLIDO1` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `IES_PERSONA_CONTACTO_APELLIDO2` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `IES_EMAIL` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `ESTADO_INICIAL_INCIDENCIA` int(11) NOT NULL,
  `ESTADO_FINAL_INCIDENCIA` int(11) NOT NULL,
  `LDAP_URL` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `LDAP_DOMINIO` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `LDAP_DN` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `LDAP_ATRIBUTO_CUENTA` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `LDAP_ATRIBUTO_NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `LDAP_ATRIBUTO_APELLIDO` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `LDAP_ATRIBUTO_DEPARTAMENTO` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `LDAP_ATRIBUTO_PERFIL` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  KEY `IX_Relationship13` (`ESTADO_INICIAL_INCIDENCIA`),
  KEY `IX_Relationship14` (`ESTADO_FINAL_INCIDENCIA`),
  CONSTRAINT `Relationship13` FOREIGN KEY (`ESTADO_INICIAL_INCIDENCIA`) REFERENCES `estado` (`ESTADO_ID`),
  CONSTRAINT `Relationship14` FOREIGN KEY (`ESTADO_FINAL_INCIDENCIA`) REFERENCES `estado` (`ESTADO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
INSERT INTO `configuracion` VALUES ('Inforteckkk','942676765','asistencia@infortec.es','IES Miguel Herrer Pereda','Q123456789A','X232323','Pepe','Ruiz','Lara','incidencias.iesmhp@gmail.com',1,4,'ldap://10.0.1.48','iesmhp.local','dc=iesmhp,dc=local','name','givenName','sn','department','title');
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dependencia`
--

DROP TABLE IF EXISTS `dependencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependencia` (
  `DEPENDENCIA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODIGO` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`DEPENDENCIA_ID`),
  UNIQUE KEY `CODIGO` (`CODIGO`),
  UNIQUE KEY `NOMBRE` (`NOMBRE`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependencia`
--

LOCK TABLES `dependencia` WRITE;
/*!40000 ALTER TABLE `dependencia` DISABLE KEYS */;
INSERT INTO `dependencia` VALUES (1,'1','(P1) Carro'),(2,'2','(P1)AMPA'),(3,'3','(P1)Aula ADF '),(4,'4','(P1)Aula Apoyo Orientación'),(5,'5','(P1)Aula Audición y Lenguaje'),(6,'6','(P1)Aula Automatismos'),(7,'7','(P1)Aula Carrocería'),(8,'8','(P1)Aula CGA1 (Mecanografía)'),(9,'9','(P1)Aula CGA2 ( Aula Empresa)'),(10,'10','(P1)Aula Distancia EMV'),(11,'11','(P1)Aula EMV1'),(12,'12','(P1)Aula EMV2'),(13,'13','(P1)Aula Metrología y Ensayos'),(14,'14','(P1)Aula Plástica 1'),(15,'15','(P1)Aula Plástica 2'),(16,'16','(P1)Aula Radio'),(17,'17','(P1)Aula20'),(18,'18','(P1)Aula21'),(19,'19','(P1)Aula22'),(20,'20','(P1)Aula23'),(21,'21','(P1)Aula24'),(22,'22','(P1)Aula25'),(23,'23','(P1)Conserjes  Planta 1'),(24,'24','(P1)Dep Orientación'),(25,'25','(P1)Dep. Administración y Finanzas'),(26,'26','(P1)Dep. FOL'),(27,'27','(P1)Dep. Humanidades'),(28,'28','(P1)Dep. Informática'),(29,'29','(P1)Dep. Mecanizado'),(30,'30','(P1)Dep. Plástica'),(31,'31','(P1)Dep.EMV'),(32,'32','(P1)IF01'),(33,'33','(P1)IF02'),(34,'34','(P1)IF03'),(35,'35','(P1)IF04'),(36,'36','(P1)Sala Visitas'),(37,'37','(P1)Salón de Actos'),(38,'38','(P2) Carro'),(39,'39','(P2)Almacén TIC'),(40,'40','(P2)Aula 30 (Lab Idiomas)'),(41,'41','(P2)Aula 31'),(42,'42','(P2)Aula 32'),(43,'43','(P2)Aula 33'),(44,'44','(P2)Aula 34'),(45,'45','(P2)Aula 35'),(46,'46','(P2)Aula 36'),(47,'47','(P2)Aula 37'),(48,'48','(P2)Aula 38'),(49,'49','(P2)Aula EOC1'),(50,'50','(P2)Aula EOC2-'),(51,'51','(P2)Aula Musica1'),(52,'52','(P2)Aula Musica2'),(53,'53','(P2)Conserjes Planta2 TIC'),(54,'54','(P2)Dep. Economía y Latín'),(55,'55','(P2)Dep. EOC'),(56,'56','(P2)Dep. Idiomas'),(57,'57','(P2)Dep. Matemáticas'),(58,'58','(P2)Dep. Música'),(59,'59','(P2)Dep. Tecnología'),(60,'60','(P2)Informática Bachiderato- Distancia Informática'),(61,'61','(P2)Lab. Tecnología'),(62,'62','(PB) Carro'),(63,'63','(PB)Aula Mecanizado CNC'),(64,'64','(PB)Aula1'),(65,'65','(PB)Aula10'),(66,'66','(PB)Aula12'),(67,'67','(PB)Aula13'),(68,'68','(PB)Aula14'),(69,'69','(PB)Aula15'),(70,'70','(PB)Aula18 Mecanizado'),(71,'71','(PB)Aula2'),(72,'72','(PB)Aula3'),(73,'73','(PB)Aula4'),(74,'74','(PB)Aula5'),(75,'75','(PB)Aula6 Convivencia'),(76,'76','(PB)Aula7'),(77,'77','(PB)Aula8'),(78,'78','(PB)Aula9 Informática'),(79,'79','(PB)Biblioteca'),(80,'80','(PB)Biblioteca PC alumnos'),(81,'81','(PB)Conserjes Planta Baja'),(82,'82','(PB)Dep. Educación Física'),(83,'83','(PB)Dep. Física, Química, Biología y Geología'),(84,'84','(PB)Dep. Lengua Castellana'),(85,'85','(PB)Dep. Mecanizado Oficina'),(86,'86','(PB)Director'),(87,'87','(PB)Fotocopiadora'),(88,'88','(PB)Informática ESO'),(89,'89','(PB)Informática General o Polivalente'),(90,'90','(PB)Jefatura de Estudios'),(91,'91','(PB)Lab. Biología y Geología'),(92,'92','(PB)Lab. Física y Química'),(93,'93','(PB)Sala Profesores'),(94,'94','(PB)Sala Reuniones Jefatura'),(95,'95','(PB)Secretaria'),(96,'96','(PB)Secretario y Administrador'),(97,'97','(PB)Taller Carrocería'),(98,'98','(PB)Taller Colometria'),(99,'99','(PB)Taller EMV'),(100,'100','(PB)Taller Mecanizado'),(101,'101','(PB)Tecnología ESO');
/*!40000 ALTER TABLE `dependencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipo` (
  `EQUIPO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NUMERO_ETIQUETA_CONSEJERIA` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `TIPO_EQUIPO_ID` int(11) NOT NULL,
  PRIMARY KEY (`EQUIPO_ID`),
  UNIQUE KEY `NUMERO_ETIQUETA_CONSEJERIA_UNIQUE` (`NUMERO_ETIQUETA_CONSEJERIA`),
  KEY `IX_Relationship5` (`TIPO_EQUIPO_ID`),
  CONSTRAINT `Relationship5` FOREIGN KEY (`TIPO_EQUIPO_ID`) REFERENCES `tipo_equipo` (`TIPO_EQUIPO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1461 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES (1,'2067',7),(10,'10000',8),(28,'20497',7),(43,'48121',7),(65,'51985',7),(83,'59494',4),(84,'63877',7),(85,'64475',16),(86,'68116',7),(87,'68124',7),(88,'68125',7),(89,'68126',7),(92,'68131',7),(93,'68132',7),(94,'68133',7),(95,'68134',7),(96,'68139',7),(97,'68140',7),(98,'71079',5),(99,'71093',7),(100,'020407',7),(101,'020398',7),(102,'055063',7),(103,'055065',7),(104,'055068',7),(105,'055069',7),(106,'020390',7),(107,'020396',7),(108,'020399',7),(109,'055061',7),(110,'055070',7),(111,'20390',7),(112,'020392',7),(113,'58106',7),(114,'20469',7),(115,'020503',7),(116,'027293',7),(117,'39831',7),(118,'39823',7),(119,'008381',5),(120,'008478',7),(121,'008367',7),(122,'008469',7),(123,'058111',17),(124,'008468',7),(125,'20482',7),(126,'008477',7),(127,'008371',7),(128,'057244',7),(129,'008440',5),(130,'20471',7),(131,'058108',7),(132,'008476',7),(133,'008380',7),(134,'008433',7),(135,'8376',7),(136,'8375',7),(137,'039839',7),(138,'020480',5),(139,'008467',7),(140,'8467',7),(141,'008485',7),(142,'20467',7),(143,'20421',7),(144,'008416',7),(145,'027247',7),(146,'27251',7),(147,'027251',7),(148,'027301',7),(149,'027350',7),(150,'026689',5),(151,'31009',9),(152,'070752',9),(153,'27350',7),(154,'027490',7),(155,'027250',7),(156,'27259',7),(157,'029634',7),(158,'063878',7),(159,'029658',4),(160,'31003',7),(161,'008370',7),(162,'8370',7),(163,'27268',7),(164,'045996',8),(165,'020483',7),(166,'024068',7),(167,'029646',7),(168,'029648',7),(169,'60064',7),(170,'029616',9),(171,'029617',9),(172,'020509',7),(173,'02406',7),(174,'010572',7),(175,'20388',7),(176,'020405',7),(177,'020406',7),(178,'024059',7),(179,'024060',7),(180,'029610',7),(181,'50170',7),(182,'063877',7),(183,'024058',5),(184,'020387',5),(185,'029613',5),(186,'50161',7),(187,'50162',7),(188,'50163',7),(189,'50164',7),(190,'50166',7),(191,'50167',7),(192,'50175',7),(193,'024053',7),(194,'028920',7),(195,'7664',7),(196,'020380',7),(197,'020388',7),(198,'020401',7),(199,'020495',7),(200,'024055',7),(201,'029612',7),(202,'050148',7),(203,'050161',7),(204,'050164',7),(205,'050172',7),(206,'050174',7),(207,'050151',5),(208,'028924',7),(209,'20075',7),(210,'55496',8),(211,'020505',7),(212,'055499',8),(213,'029666',4),(214,'0055496',8),(215,'039829',7),(216,'029642',4),(217,'008444',3),(218,'35775',7),(219,'039836',7),(220,'029656',4),(221,'064473',7),(222,'07789',5),(223,'029663',7),(224,'020511',7),(225,'35778',7),(226,'029641',7),(227,'008410',7),(228,'55461',7),(229,'008423',4),(230,'5885',14),(231,'55473',7),(232,'55475',7),(233,'050184',7),(234,'050204',7),(235,'050188',7),(236,'050202',7),(237,'045109',7),(238,'45115',7),(239,'071058',7),(240,'029601',4),(241,'045099',7),(242,'045115',7),(243,'45127',7),(244,'071022',7),(245,'2250',7),(246,'39821',7),(247,'39839',7),(248,'039847',7),(249,'039848',7),(250,'63878',7),(251,'029619',4),(252,'7734',15),(253,'054194',7),(254,'54200',7),(255,'054200',7),(256,'54203',7),(257,'054208',7),(258,'054218',7),(259,'020548',4),(260,'054192',7),(261,'054206',7),(262,'45099',7),(263,'51009',7),(264,'051018',7),(265,'051221',7),(266,'020418',7),(267,'20419',7),(268,'20420',7),(269,'20426',7),(270,'020428',7),(271,'20430',7),(272,'020430',7),(273,'20432',7),(274,'20438',7),(275,'20440',7),(276,'20442',7),(277,'20444',7),(278,'20446',7),(279,'20458',7),(280,'020476',7),(281,'059497',7),(282,'63872',7),(283,'63874',7),(284,'029653',7),(285,'29653',7),(286,'035907',7),(287,'035909',7),(288,'035911',7),(289,'008382',7),(290,'20491',7),(291,'20422',7),(292,'20481',7),(293,'50101',7),(294,'051035',7),(295,'20112',7),(296,'20466',7),(297,'20468',7),(298,'20476',7),(299,'7193',9),(300,'51023',7),(301,'51017',7),(302,'059495',4),(303,'035773',7),(304,'020501',4),(305,'007702',7),(306,'008374',7),(307,'7804',7),(308,'34684',7),(309,'63863',14),(310,'55494',9),(311,'59489',9),(314,'50168',7),(315,'50169',7),(316,'50171',7),(317,'50172',7),(318,'50173',7),(319,'50174',7),(321,'50179',7),(322,'50180',7),(324,'50184',7),(325,'50186',7),(327,'50190',7),(328,'50192',7),(329,'50194',7),(331,'50198',7),(332,'50200',7),(333,'50202',7),(335,'50206',7),(339,'51013',7),(340,'51014',7),(341,'51015',7),(342,'51016',7),(343,'51018',7),(345,'51020',7),(346,'51021',7),(347,'51022',7),(348,'51024',7),(349,'54192',7),(351,'54198',7),(352,'54202',7),(353,'54204',7),(354,'54206',7),(355,'54208',7),(356,'54210',7),(357,'54212',7),(358,'54214',7),(360,'54218',7),(363,'55060',7),(364,'55061',7),(365,'55062',7),(366,'55063',7),(367,'55064',7),(368,'55065',7),(369,'55066',7),(370,'55067',7),(371,'55068',7),(372,'55069',7),(374,'55471',7),(375,'55472',7),(377,'55476',7),(378,'55477',7),(379,'55478',7),(380,'55479',7),(381,'55480',7),(382,'55481',7),(383,'55482',7),(384,'55483',7),(385,'55484',7),(386,'55485',7),(388,'55487',7),(389,'55488',7),(390,'55501',7),(391,'55502',7),(392,'55503',7),(393,'57240',7),(394,'57243',7),(395,'57244',7),(396,'57245',7),(397,'58849',7),(398,'58851',7),(399,'58852',7),(400,'58853',7),(401,'58854',7),(402,'58855',7),(403,'59497',7),(404,'59499',7),(405,'63876',7),(406,'64113',7),(407,'64116',7),(408,'64117',7),(409,'64473',7),(410,'64474',7),(411,'55523',1),(412,'59496',1),(413,'55047',1),(414,'55048',1),(415,'57227',1),(416,'57228',1),(417,'57229',1),(418,'57230',1),(419,'57231',1),(420,'57232',1),(421,'57233',1),(422,'57234',1),(423,'57235',1),(424,'57236',1),(425,'57237',1),(426,'57238',1),(427,'57239',1),(428,'7111',3),(429,'7114',3),(430,'7149',3),(431,'7228',3),(432,'7619',3),(433,'7654',3),(434,'7741',3),(435,'7770',3),(436,'7795',3),(437,'8395',3),(438,'8444',3),(439,'8484',3),(440,'8525',3),(441,'8599',3),(442,'29630',3),(443,'30989',3),(444,'36433',3),(445,'63862',1),(446,'64124',1),(447,'64125',1),(448,'64126',1),(449,'7629',15),(450,'29498',15),(451,'29578',4),(452,'29601',4),(453,'29628',4),(454,'29651',4),(455,'29658',4),(456,'29660',4),(457,'29666',4),(458,'29670',4),(459,'29671',4),(460,'36425',4),(461,'36434',4),(462,'45959',4),(463,'45968',4),(464,'45978',4),(465,'59485',4),(466,'59486',4),(467,'59487',4),(468,'59488',4),(469,'59495',4),(470,'7108',4),(471,'7121',4),(472,'7133',4),(473,'7687',4),(474,'7690',4),(475,'7729',4),(476,'7730',4),(477,'8384',4),(478,'20415',4),(479,'20448',4),(480,'20451',4),(481,'20485',4),(482,'20501',4),(483,'20548',4),(484,'29619',4),(485,'29620',4),(486,'45956',4),(487,'45964',4),(488,'45977',4),(489,'45985',4),(490,'45992',4),(491,'7689',4),(492,'29589',4),(493,'29642',4),(494,'29665',4),(495,'7122',4),(496,'7129',4),(497,'7130',4),(498,'7147',4),(499,'7190',4),(500,'7191',4),(501,'7229',4),(502,'7259',4),(503,'7306',4),(504,'7310',4),(505,'7601',4),(506,'7604',4),(507,'7616',4),(508,'7630',4),(509,'7636',4),(510,'7639',4),(511,'7650',4),(512,'7653',4),(513,'7686',4),(514,'7727',4),(515,'7744',4),(516,'7796',4),(517,'7798',4),(518,'8394',4),(519,'8423',4),(520,'8446',4),(521,'8483',4),(522,'8526',4),(523,'8594',4),(524,'8598',4),(525,'8601',4),(526,'8603',4),(527,'20487',4),(528,'20490',4),(529,'20493',4),(530,'60494',5),(531,'7608',5),(532,'7634',5),(533,'7674',5),(534,'7675',5),(535,'7678',5),(536,'7682',5),(537,'7683',5),(538,'7684',5),(539,'7685',5),(540,'29604',5),(541,'29605',5),(542,'7123',5),(543,'7151',5),(544,'7614',5),(545,'7640',5),(546,'7737',5),(547,'7738',5),(548,'7773',5),(549,'7792',5),(550,'7797',5),(551,'8499',5),(552,'8600',5),(553,'7109',5),(554,'7120',5),(555,'7127',5),(556,'7136',5),(557,'7173',5),(558,'7174',5),(559,'7175',5),(560,'7176',5),(561,'7177',5),(562,'7179',5),(563,'7180',5),(564,'7181',5),(565,'7182',5),(566,'7183',5),(567,'7184',5),(568,'7185',5),(569,'7186',5),(570,'7187',5),(571,'7188',5),(572,'7189',5),(573,'7243',5),(574,'7245',5),(575,'7246',5),(576,'7247',5),(577,'7249',5),(578,'7250',5),(579,'7251',5),(580,'7252',5),(581,'7253',5),(582,'7254',5),(583,'7255',5),(584,'7257',5),(585,'7258',5),(586,'7602',5),(587,'7605',5),(588,'7615',5),(589,'7620',5),(590,'7627',5),(591,'7637',5),(592,'7651',5),(593,'7655',5),(594,'7715',5),(595,'7717',5),(596,'7720',5),(597,'7725',5),(598,'7743',5),(599,'7765',5),(600,'8285',5),(601,'8286',5),(602,'8287',5),(603,'8288',5),(604,'8289',5),(605,'8290',5),(606,'8292',5),(607,'8293',5),(608,'8297',5),(609,'8366',5),(610,'8393',5),(611,'8427',5),(612,'8428',5),(613,'8429',5),(614,'8430',5),(615,'8431',5),(616,'8432',5),(617,'8433',5),(618,'8434',5),(619,'8435',5),(620,'8437',5),(621,'8439',5),(622,'8440',5),(623,'8442',5),(624,'8448',5),(625,'8449',5),(626,'8450',5),(627,'8451',5),(628,'8453',5),(629,'8454',5),(630,'8455',5),(631,'8456',5),(632,'8457',5),(633,'8458',5),(634,'8459',5),(635,'8460',5),(636,'8461',5),(637,'8462',5),(638,'8463',5),(639,'8464',5),(640,'8486',5),(641,'8493',5),(642,'30986',5),(643,'30987',5),(644,'20068',5),(645,'20070',5),(646,'20072',5),(647,'20074',5),(648,'20076',5),(649,'20078',5),(650,'20080',5),(651,'20082',5),(652,'20084',5),(653,'20086',5),(654,'20088',5),(655,'20110',5),(656,'20379',5),(657,'20381',5),(658,'20383',5),(659,'20385',5),(661,'20389',5),(662,'20391',5),(663,'20393',5),(664,'20395',5),(665,'20397',5),(666,'20400',5),(667,'20402',5),(668,'20404',5),(669,'20406',5),(670,'20408',5),(671,'20410',5),(673,'20414',5),(674,'20417',5),(675,'20423',5),(676,'20425',5),(677,'20427',5),(678,'20429',5),(679,'20431',5),(680,'20433',5),(681,'20435',5),(682,'20437',5),(683,'20439',5),(684,'20441',5),(685,'20443',5),(686,'20445',5),(687,'20447',5),(688,'20450',5),(689,'20453',5),(691,'20457',5),(692,'20459',5),(693,'20461',5),(694,'20463',5),(695,'20465',5),(696,'20473',5),(697,'20475',5),(698,'20477',5),(699,'20478',5),(700,'20480',5),(701,'20484',5),(702,'20489',5),(703,'20492',5),(704,'20496',5),(705,'20498',5),(706,'20499',5),(707,'20500',5),(708,'20504',5),(709,'20506',5),(710,'20508',5),(711,'20512',5),(712,'24052',5),(713,'24054',5),(714,'24056',5),(715,'24058',5),(716,'24060',5),(717,'24062',5),(718,'24063',5),(719,'24065',5),(720,'24067',5),(721,'24070',5),(722,'24071',5),(723,'24073',5),(724,'24075',5),(725,'24077',5),(726,'26662',5),(727,'26669',5),(728,'26674',5),(729,'26680',5),(730,'26681',5),(731,'26682',5),(732,'26683',5),(733,'26687',5),(734,'26688',5),(735,'26689',5),(736,'26690',5),(737,'26691',5),(738,'28563',5),(739,'28915',5),(740,'28917',5),(741,'28919',5),(742,'28921',5),(743,'28923',5),(744,'29584',5),(745,'29587',5),(746,'29603',5),(747,'29613',5),(748,'29614',5),(749,'29615',5),(750,'29621',5),(751,'29622',5),(752,'29627',5),(753,'29629',5),(754,'29631',5),(755,'29632',5),(756,'29633',5),(757,'29637',5),(758,'29640',5),(759,'29645',5),(760,'29647',5),(761,'29650',5),(762,'29654',5),(763,'29657',5),(764,'29662',5),(765,'29664',5),(766,'29669',5),(767,'29675',5),(768,'29680',5),(769,'29685',5),(770,'29686',5),(771,'31337',5),(772,'34606',5),(773,'34607',5),(774,'34608',5),(775,'34609',5),(776,'34611',5),(777,'34623',5),(778,'35767',5),(779,'35768',5),(780,'35769',5),(781,'35770',5),(782,'35771',5),(783,'35772',5),(784,'45989',5),(785,'45991',5),(786,'55049',5),(787,'55050',5),(788,'55051',5),(789,'55052',5),(790,'55053',5),(791,'55054',5),(792,'55055',5),(793,'55056',5),(794,'55057',5),(795,'55058',5),(796,'55059',5),(797,'57242',5),(798,'57246',5),(799,'57247',5),(800,'57248',5),(801,'29667',5),(802,'29672',5),(803,'29673',5),(804,'29674',5),(805,'29679',5),(806,'30984',5),(807,'31004',5),(808,'35887',5),(809,'35888',5),(810,'35889',5),(811,'35890',5),(812,'35891',5),(813,'35892',5),(814,'35893',5),(815,'35894',5),(816,'35895',5),(817,'35896',5),(818,'35897',5),(819,'35898',5),(820,'35899',5),(821,'35900',5),(822,'35901',5),(823,'35902',5),(824,'39820',5),(825,'39822',5),(826,'39824',5),(827,'39826',5),(828,'39828',5),(829,'39830',5),(830,'39832',5),(831,'39834',5),(832,'39837',5),(833,'39838',5),(835,'39842',5),(837,'39846',5),(838,'39849',5),(839,'39850',5),(840,'45098',5),(841,'45100',5),(842,'45103',5),(843,'45104',5),(844,'45106',5),(845,'45108',5),(846,'45110',5),(847,'45112',5),(848,'45114',5),(849,'45116',5),(850,'45118',5),(851,'45120',5),(852,'45122',5),(853,'45124',5),(854,'45126',5),(855,'45128',5),(856,'45965',5),(857,'45966',5),(858,'45971',5),(859,'45972',5),(860,'45981',5),(861,'45983',5),(862,'45984',5),(863,'45993',5),(864,'45997',5),(865,'45998',5),(866,'45999',5),(867,'46000',5),(868,'46001',5),(869,'46002',5),(870,'50144',5),(871,'50145',5),(872,'50146',5),(873,'50147',5),(874,'50148',5),(875,'50149',5),(876,'50150',5),(877,'50151',5),(878,'50152',5),(879,'50153',5),(880,'50154',5),(881,'50155',5),(882,'50156',5),(883,'50157',5),(884,'50158',5),(885,'50159',5),(886,'50176',5),(887,'50178',5),(888,'50181',5),(889,'50183',5),(890,'50185',5),(891,'50187',5),(892,'50189',5),(893,'50191',5),(894,'50193',5),(895,'50195',5),(896,'50197',5),(897,'50199',5),(898,'50201',5),(899,'50203',5),(901,'50207',5),(902,'51025',5),(903,'51026',5),(904,'51027',5),(905,'51028',5),(906,'51029',5),(907,'51030',5),(908,'51031',5),(909,'51032',5),(910,'51033',5),(911,'51034',5),(912,'51035',5),(913,'51036',5),(914,'51037',5),(915,'51038',5),(916,'51039',5),(917,'51040',5),(918,'54193',5),(919,'54195',5),(920,'54197',5),(921,'54199',5),(922,'54201',5),(923,'54205',5),(924,'54207',5),(925,'54209',5),(926,'54211',5),(927,'54213',5),(928,'54215',5),(930,'54219',5),(931,'54221',5),(933,'55453',5),(934,'55454',5),(935,'55455',5),(936,'55456',5),(937,'55457',5),(938,'55458',5),(939,'55459',5),(940,'55460',5),(941,'55462',5),(942,'55463',5),(943,'55464',5),(944,'55465',5),(945,'55466',5),(946,'55467',5),(947,'55468',5),(948,'55469',5),(949,'55470',5),(950,'57241',5),(951,'58115',5),(952,'58842',5),(953,'58843',5),(954,'58844',5),(955,'58845',5),(956,'58846',5),(957,'58847',5),(958,'58848',5),(959,'59498',5),(960,'59500',5),(961,'7105',7),(962,'7107',7),(963,'7110',7),(964,'7119',7),(965,'7124',7),(966,'7128',7),(967,'7132',7),(968,'7134',7),(969,'7150',7),(970,'7153',7),(971,'7154',7),(972,'7155',7),(973,'7157',7),(974,'7158',7),(975,'7159',7),(976,'7160',7),(977,'7161',7),(978,'7162',7),(979,'7163',7),(980,'7164',7),(981,'7165',7),(982,'7166',7),(983,'7167',7),(984,'7168',7),(985,'7169',7),(986,'7194',7),(987,'7195',7),(988,'7196',7),(989,'7197',7),(990,'7198',7),(991,'7199',7),(992,'7200',7),(993,'7201',7),(994,'7202',7),(995,'7203',7),(996,'7204',7),(997,'7205',7),(998,'7206',7),(999,'7207',7),(1000,'7209',7),(1001,'7309',7),(1002,'7603',7),(1003,'7606',7),(1004,'7607',7),(1005,'7613',7),(1006,'7617',7),(1007,'7621',7),(1008,'7625',7),(1009,'7628',7),(1010,'7632',7),(1011,'7638',7),(1012,'7641',7),(1013,'7652',7),(1014,'7661',7),(1015,'7665',7),(1016,'7666',7),(1017,'7668',7),(1018,'7669',7),(1019,'7671',7),(1020,'7672',7),(1021,'7694',7),(1022,'7695',7),(1023,'7698',7),(1024,'7699',7),(1025,'7700',7),(1026,'7702',7),(1027,'7706',7),(1028,'7740',7),(1029,'7742',7),(1030,'7759',7),(1031,'7760',7),(1032,'7789',7),(1034,'8368',7),(1035,'8369',7),(1037,'8372',7),(1038,'8373',7),(1039,'8374',7),(1040,'8377',7),(1041,'8378',7),(1042,'8379',7),(1044,'8381',7),(1045,'8382',7),(1046,'8390',7),(1047,'8392',7),(1048,'8405',7),(1049,'8406',7),(1050,'8407',7),(1051,'8408',7),(1052,'8409',7),(1053,'8410',7),(1054,'8411',7),(1055,'8412',7),(1056,'8413',7),(1057,'8414',7),(1058,'8415',7),(1059,'8416',7),(1060,'8417',7),(1061,'8418',7),(1062,'8419',7),(1063,'8420',7),(1064,'8421',7),(1065,'8441',7),(1066,'8445',7),(1068,'8466',7),(1071,'8470',7),(1072,'8471',7),(1073,'8472',7),(1074,'8473',7),(1075,'8474',7),(1076,'8475',7),(1077,'8476',7),(1078,'8477',7),(1079,'8478',7),(1080,'8479',7),(1081,'8485',7),(1082,'8521',7),(1083,'8602',7),(1084,'8605',7),(1086,'20069',7),(1087,'20071',7),(1088,'20073',7),(1090,'20079',7),(1091,'20081',7),(1092,'20083',7),(1093,'20085',7),(1094,'20087',7),(1095,'20109',7),(1096,'20380',7),(1097,'20382',7),(1098,'20384',7),(1099,'20386',7),(1100,'20392',7),(1101,'20394',7),(1103,'20398',7),(1104,'20399',7),(1105,'20401',7),(1106,'20403',7),(1107,'20405',7),(1108,'20407',7),(1110,'20411',7),(1111,'20413',7),(1112,'20416',7),(1118,'20449',7),(1119,'20452',7),(1120,'20454',7),(1121,'20456',7),(1122,'20460',7),(1123,'20462',7),(1124,'20464',7),(1125,'20470',7),(1127,'20474',7),(1128,'20479',7),(1129,'20483',7),(1130,'20486',7),(1132,'20494',7),(1134,'20502',7),(1135,'20503',7),(1137,'20509',7),(1138,'20511',7),(1139,'24051',7),(1140,'24053',7),(1142,'24057',7),(1143,'24059',7),(1144,'24061',7),(1145,'24064',7),(1146,'24066',7),(1147,'24068',7),(1148,'24069',7),(1149,'24072',7),(1150,'24074',7),(1151,'24076',7),(1152,'24078',7),(1153,'27247',7),(1154,'27250',7),(1155,'27293',7),(1156,'27301',7),(1157,'27356',7),(1158,'27357',7),(1159,'27359',7),(1160,'27485',7),(1161,'27490',7),(1162,'28562',7),(1163,'28916',7),(1164,'28918',7),(1165,'28920',7),(1166,'28922',7),(1167,'28924',7),(1168,'29582',7),(1169,'29606',7),(1170,'29607',7),(1171,'29610',7),(1172,'29611',7),(1173,'29612',7),(1174,'29626',7),(1175,'29634',7),(1176,'29641',7),(1177,'29646',7),(1178,'29648',7),(1179,'29649',7),(1180,'29656',7),(1181,'29659',7),(1182,'29661',7),(1183,'29663',7),(1184,'29676',7),(1185,'29678',7),(1186,'30980',7),(1187,'30982',7),(1188,'30983',7),(1189,'31338',7),(1190,'34693',7),(1191,'34694',7),(1192,'34892',7),(1193,'34895',7),(1194,'34905',7),(1195,'34906',7),(1196,'35773',7),(1197,'35774',7),(1198,'35776',7),(1199,'35777',7),(1200,'35903',7),(1201,'35904',7),(1202,'35905',7),(1203,'35906',7),(1204,'35907',7),(1205,'35908',7),(1206,'35909',7),(1207,'35910',7),(1208,'35911',7),(1212,'35915',7),(1213,'35916',7),(1215,'35918',7),(1216,'36427',7),(1217,'39825',7),(1220,'39833',7),(1221,'39835',7),(1222,'39836',7),(1224,'39843',7),(1225,'39845',7),(1226,'39847',7),(1228,'39851',7),(1229,'45097',7),(1230,'45101',7),(1231,'45102',7),(1232,'45105',7),(1233,'45107',7),(1234,'45109',7),(1235,'45111',7),(1236,'45113',7),(1237,'45117',7),(1238,'45119',7),(1239,'45121',7),(1240,'45123',7),(1241,'45125',7),(1242,'45129',7),(1243,'45962',7),(1244,'45969',7),(1245,'45973',7),(1246,'45976',7),(1247,'45980',7),(1248,'45986',7),(1249,'46100',7),(1250,'46101',7),(1251,'63871',5),(1252,'63873',5),(1253,'63875',5),(1254,'64112',5),(1255,'64114',5),(1256,'64115',5),(1257,'64479',5),(1258,'64480',5),(1259,'64481',5),(1260,'55452',1),(1262,'8608',8),(1263,'23876',8),(1264,'23877',8),(1265,'23878',8),(1266,'23879',8),(1267,'23880',8),(1268,'23881',8),(1269,'29593',8),(1270,'29596',8),(1271,'29597',8),(1272,'29639',8),(1273,'29644',8),(1274,'30981',8),(1275,'30998',8),(1276,'31002',8),(1277,'31007',8),(1278,'31010',8),(1279,'35191',8),(1280,'35201',8),(1281,'35209',8),(1282,'35241',8),(1284,'35261',8),(1285,'35262',8),(1286,'36431',8),(1287,'39250',8),(1289,'45970',8),(1290,'45996',8),(1291,'55489',8),(1292,'55490',8),(1293,'55491',8),(1294,'55492',8),(1295,'55493',8),(1296,'55495',8),(1297,'55499',8),(1298,'55500',8),(1299,'64476',8),(1300,'64477',8),(1301,'64478',8),(1302,'7735',9),(1303,'8422',9),(1304,'8518',9),(1305,'20510',9),(1306,'29579',9),(1307,'29581',9),(1308,'29592',9),(1309,'29595',9),(1310,'29598',9),(1311,'29602',9),(1312,'29608',9),(1313,'29616',9),(1314,'29617',9),(1315,'29635',9),(1316,'29638',9),(1317,'29643',9),(1318,'29655',9),(1319,'29668',9),(1320,'29677',9),(1321,'29682',9),(1322,'29683',9),(1323,'29684',9),(1324,'30999',9),(1325,'31001',9),(1326,'31005',9),(1327,'31006',9),(1328,'33318',9),(1329,'34273',9),(1330,'34274',9),(1331,'34281',9),(1332,'34284',9),(1333,'34289',9),(1334,'34302',9),(1335,'34315',9),(1336,'36423',9),(1337,'36426',9),(1338,'36429',9),(1339,'36432',9),(1340,'37320',9),(1341,'37321',9),(1342,'37322',9),(1343,'45957',9),(1344,'45963',9),(1345,'45995',9),(1346,'64122',9),(1347,'45987',10),(1348,'45988',10),(1349,'55504',10),(1350,'55505',10),(1351,'55506',10),(1352,'55507',10),(1353,'55508',10),(1354,'55509',10),(1355,'55510',10),(1356,'55511',10),(1357,'55512',10),(1358,'55513',10),(1359,'55514',10),(1360,'55515',10),(1361,'55516',10),(1362,'55517',10),(1363,'55518',10),(1364,'55519',10),(1365,'55520',10),(1366,'55521',10),(1367,'55522',10),(1368,'29583',12),(1369,'29623',12),(1370,'29624',12),(1371,'31000',12),(1372,'31008',12),(1373,'36422',12),(1374,'36424',12),(1375,'45975',12),(1376,'63861',12),(1377,'63865',12),(1378,'63859',13),(1379,'63860',13),(1380,'63866',14),(1381,'63867',14),(1382,'63868',14),(1383,'63869',14),(1384,'7115',15),(1385,'7139',15),(1386,'7210',15),(1387,'7242',15),(1388,'7307',15),(1389,'7609',15),(1390,'7657',15),(1391,'7691',15),(1392,'7692',15),(1393,'7771',15),(1394,'7808',15),(1395,'8385',15),(1396,'8425',15),(1397,'8426',15),(1398,'8593',15),(1399,'20547',15),(1400,'29580',15),(1401,'29585',15),(1402,'29586',15),(1403,'29600',15),(1404,'29609',15),(1405,'29618',15),(1406,'29625',15),(1407,'29681',15),(1408,'30988',15),(1409,'30991',15),(1410,'30992',15),(1411,'30993',15),(1412,'30994',15),(1413,'30995',15),(1414,'30996',15),(1415,'36421',15),(1416,'36430',15),(1417,'45958',15),(1418,'45960',15),(1419,'45961',15),(1420,'45967',15),(1421,'45979',15),(1422,'45982',15),(1423,'45990',15),(1424,'45994',15),(1425,'54279',15),(1426,'57226',15),(1427,'59501',15),(1428,'59502',15),(1429,'59503',15),(1430,'63870',15),(1431,'64123',15),(1432,'55451',17),(1433,'58094',17),(1434,'58095',17),(1436,'58097',17),(1437,'58098',17),(1438,'58099',17),(1439,'58100',17),(1440,'58101',17),(1441,'58102',17),(1442,'58103',17),(1443,'58104',17),(1444,'58105',17),(1445,'58107',17),(1446,'58108',17),(1447,'58109',17),(1448,'58110',17),(1449,'58111',17),(1450,'58112',17),(1451,'58113',17),(1453,'58841',17),(1454,'59483',17),(1455,'59484',17),(1456,'59490',17),(1457,'59491',17),(1458,'59492',17),(1459,'59493',17),(1460,'59504',17);
/*!40000 ALTER TABLE `equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado` (
  `ESTADO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODIGO` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`ESTADO_ID`),
  UNIQUE KEY `CODIGO` (`CODIGO`),
  UNIQUE KEY `NOMBRE` (`NOMBRE`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Iniciado','Iniciado'),(2,'INFORTEC','INFORTEC'),(3,'Pendiente','Pendiente'),(4,'Hecho','Hecho'),(5,'SinSol','Sin Solución');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial`
--

DROP TABLE IF EXISTS `historial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historial` (
  `HISTORIAL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA` datetime NOT NULL,
  `INCIDENCIA_ID` int(11) NOT NULL,
  `ESTADO_ID` int(11) NOT NULL,
  PRIMARY KEY (`HISTORIAL_ID`),
  KEY `IX_Relationship9` (`INCIDENCIA_ID`),
  KEY `IX_Relationship10` (`ESTADO_ID`),
  CONSTRAINT `Relationship10` FOREIGN KEY (`ESTADO_ID`) REFERENCES `estado` (`ESTADO_ID`),
  CONSTRAINT `Relationship9` FOREIGN KEY (`INCIDENCIA_ID`) REFERENCES `incidencia` (`INCIDENCIA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial`
--

LOCK TABLES `historial` WRITE;
/*!40000 ALTER TABLE `historial` DISABLE KEYS */;
/*!40000 ALTER TABLE `historial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidencia`
--

DROP TABLE IF EXISTS `incidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incidencia` (
  `INCIDENCIA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `POSICION_EQUIPO_DEPENDENCIA` varchar(500) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DESCRIPCION` varchar(500) COLLATE utf8_spanish2_ci NOT NULL,
  `COMENTARIO_ADMINISTRADOR` varchar(500) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `FECHA_REGISTRO` datetime NOT NULL,
  `FECHA_CIERRE` datetime DEFAULT NULL,
  `FECHA_ESTADO_ACTUAL` datetime NOT NULL,
  `USUARIO_ID` int(11) NOT NULL,
  `EQUIPO_ID` int(11) NOT NULL,
  `DEPENDENCIA_ID` int(11) NOT NULL,
  `ESTADO_ID` int(11) NOT NULL,
  PRIMARY KEY (`INCIDENCIA_ID`),
  KEY `IX_Relationship3` (`USUARIO_ID`),
  KEY `IX_Relationship7` (`EQUIPO_ID`),
  KEY `IX_Relationship12` (`DEPENDENCIA_ID`),
  KEY `IX_Relationship15` (`ESTADO_ID`),
  CONSTRAINT `Relationship12` FOREIGN KEY (`DEPENDENCIA_ID`) REFERENCES `dependencia` (`DEPENDENCIA_ID`),
  CONSTRAINT `Relationship15` FOREIGN KEY (`ESTADO_ID`) REFERENCES `estado` (`ESTADO_ID`),
  CONSTRAINT `Relationship3` FOREIGN KEY (`USUARIO_ID`) REFERENCES `usuario` (`USUARIO_ID`),
  CONSTRAINT `Relationship7` FOREIGN KEY (`EQUIPO_ID`) REFERENCES `equipo` (`EQUIPO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencia`
--

LOCK TABLES `incidencia` WRITE;
/*!40000 ALTER TABLE `incidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidencia` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`incidencias`@`%`*/ /*!50003 TRIGGER GENERAR_FECHA_CIERRE
  BEFORE
  UPDATE
  ON incidencia
  FOR EACH ROW
  BEGIN
	DECLARE X INT;
    SELECT max(estado_final_incidencia) INTO X FROM configuracion;
    
    IF NEW.estado_id = X THEN
        SET NEW.fecha_cierre = NOW();
    END IF;    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `GENERAR_HISTORIAL`
  AFTER
  UPDATE
  ON incidencia
  FOR EACH ROW
  BEGIN
    if OLD.estado_id != NEW.estado_id THEN
        insert into HISTORIAL(fecha,incidencia_id,estado_id)

            values(OLD.fecha_estado_actual,OLD.incidencia_id,OLD.estado_id);

    END IF;    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tipo_equipo`
--

DROP TABLE IF EXISTS `tipo_equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_equipo` (
  `TIPO_EQUIPO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODIGO` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`TIPO_EQUIPO_ID`),
  UNIQUE KEY `CODIGO` (`CODIGO`),
  UNIQUE KEY `NOMBRE` (`NOMBRE`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_equipo`
--

LOCK TABLES `tipo_equipo` WRITE;
/*!40000 ALTER TABLE `tipo_equipo` DISABLE KEYS */;
INSERT INTO `tipo_equipo` VALUES (1,'Otros','Otros'),(2,'CPD','CPD'),(3,'Escaner','Escaner'),(4,'Impresora','Impresora'),(5,'Monitor','Monitor'),(6,'PantallaD.','Pantalla Digital'),(7,'PC o Torre','PC o Torre'),(8,'Portatil','Portatil'),(9,'Proyector','Proyector'),(10,'P. Acceso','Punto de acceso'),(11,'Raton','Raton'),(12,'Router','Router'),(13,'SAI','SAI'),(14,'Servidor','Servidor'),(15,'Switch','Switch'),(16,'Teclado','Teclado'),(17,'V.Splitter','Video Splitter');
/*!40000 ALTER TABLE `tipo_equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `USUARIO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUENTA` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `NOMBRE` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `APELLIDO` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DEPARTAMENTO` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  PRIMARY KEY (`USUARIO_ID`),
  UNIQUE KEY `CUENTA` (`CUENTA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'incidencias'
--

--
-- Dumping routines for database 'incidencias'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-02 19:09:51

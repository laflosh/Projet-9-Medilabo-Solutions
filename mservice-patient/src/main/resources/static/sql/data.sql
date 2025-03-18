--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES 
(1,'TestNone','Test','1966-12-31','F','1 Brookside St','100-222-3333'),
(2,'TestBorderline','Test','1945-06-24','M','2 High St','200-333-4444'),
(3,'TestInDanger','Test','2004-06-18','M','3 Club Road ','300-444-5555'),
(4,'TestEarlyOnset','Test','2002-06-28','F','4 Valler Dr','400-555-6666');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;
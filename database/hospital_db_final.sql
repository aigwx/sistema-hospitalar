-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: hospital_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agendamento`
--

DROP TABLE IF EXISTS `agendamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agendamento` (
  `id_agendamento` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `id_profissional` int NOT NULL,
  `data_hora` datetime NOT NULL,
  `status` varchar(30) NOT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_agendamento`),
  KEY `idx_agendamento_data` (`data_hora`),
  KEY `idx_agendamento_profissional_data` (`id_profissional`,`data_hora`),
  KEY `idx_agendamento_paciente_data` (`id_paciente`,`data_hora`),
  CONSTRAINT `agendamento_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`),
  CONSTRAINT `agendamento_ibfk_2` FOREIGN KEY (`id_profissional`) REFERENCES `profissional` (`id_profissional`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendamento`
--

LOCK TABLES `agendamento` WRITE;
/*!40000 ALTER TABLE `agendamento` DISABLE KEYS */;
INSERT INTO `agendamento` VALUES (1,1,1,'2026-11-10 14:00:00','Confirmado','Consulta de teste'),(3,1,1,'2026-11-12 14:00:00','Confirmado','Consulta de acompanhamento confirmada'),(4,1,1,'2026-11-16 10:00:00','Confirmado','Consulta para teste de auditoria');
/*!40000 ALTER TABLE `agendamento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_agendamento_insert` AFTER INSERT ON `agendamento` FOR EACH ROW begin 
insert into historico_agendamento (
id_agendamento,
id_usuario,
acao,
descricao
)
values ( new.id_agendamento,
@id_usuario_logado, 
'insert', 
'Novo agendamento cadastrado'
);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_agendamento_update` AFTER UPDATE ON `agendamento` FOR EACH ROW begin
insert into historico_agendamento (
id_agendamento,
id_usuario,
acao,
descricao
)
values (new.id_agendamento,
@id_usuario_logado,
'update',
concat( 'Status anterior: ', OLD.status, ' | Novo status: ', NEW.status
)
);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `atendimento`
--

DROP TABLE IF EXISTS `atendimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `atendimento` (
  `id_atendimento` int NOT NULL AUTO_INCREMENT,
  `id_agendamento` int NOT NULL,
  `data_hora` datetime NOT NULL,
  `diagnostico` text,
  `observacoes` text,
  PRIMARY KEY (`id_atendimento`),
  UNIQUE KEY `id_agendamento` (`id_agendamento`),
  CONSTRAINT `atendimento_ibfk_1` FOREIGN KEY (`id_agendamento`) REFERENCES `agendamento` (`id_agendamento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atendimento`
--

LOCK TABLES `atendimento` WRITE;
/*!40000 ALTER TABLE `atendimento` DISABLE KEYS */;
INSERT INTO `atendimento` VALUES (1,1,'2026-11-05 09:12:11','Diagnóstico atualizado para teste','Atendimento realizado para validar o sistema'),(3,4,'2026-11-16 10:30:00','Paciente apresenta melhora no quadro clínico','Atendimento atualizado para teste de auditoria');
/*!40000 ALTER TABLE `atendimento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_atendimento_insert` AFTER INSERT ON `atendimento` FOR EACH ROW BEGIN
    INSERT INTO historico_atendimento (
        id_atendimento,
        id_usuario,
        acao,
        descricao
    )
    VALUES (
        NEW.id_atendimento,
        @id_usuario_logado,
        'INSERT',
        'Novo atendimento cadastrado'
    );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_atendimento_update` AFTER UPDATE ON `atendimento` FOR EACH ROW BEGIN
    INSERT INTO historico_atendimento (
        id_atendimento,
        id_usuario,
        acao,
        descricao
    )
    VALUES (
        NEW.id_atendimento,
        @id_usuario_logado,
        'UPDATE',
        CONCAT(
            'Diagnóstico anterior: ', COALESCE(OLD.diagnostico, 'Não informado'),
            ' | Novo diagnóstico: ', COALESCE(NEW.diagnostico, 'Não informado'),
            ' | Observações anteriores: ', COALESCE(OLD.observacoes, 'Não informado'),
            ' | Novas observações: ', COALESCE(NEW.observacoes, 'Não informado')
        )
    );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `enfermeiro`
--

DROP TABLE IF EXISTS `enfermeiro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enfermeiro` (
  `id_profissional` int NOT NULL,
  `coren` varchar(20) NOT NULL,
  PRIMARY KEY (`id_profissional`),
  UNIQUE KEY `coren` (`coren`),
  CONSTRAINT `enfermeiro_ibfk_1` FOREIGN KEY (`id_profissional`) REFERENCES `profissional` (`id_profissional`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enfermeiro`
--

LOCK TABLES `enfermeiro` WRITE;
/*!40000 ALTER TABLE `enfermeiro` DISABLE KEYS */;
/*!40000 ALTER TABLE `enfermeiro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidade`
--

DROP TABLE IF EXISTS `especialidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidade` (
  `id_especialidade` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_especialidade`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidade`
--

LOCK TABLES `especialidade` WRITE;
/*!40000 ALTER TABLE `especialidade` DISABLE KEYS */;
INSERT INTO `especialidade` VALUES (2,'Cardiologia','Especialidade medica dedicada ao diagnostico e tratamento de doencas do coracao'),(3,'Oftalmologia','Teste de funcionalidades no front end');
/*!40000 ALTER TABLE `especialidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exame`
--

DROP TABLE IF EXISTS `exame`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exame` (
  `id_exame` int NOT NULL AUTO_INCREMENT,
  `id_atendimento` int NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `data_solicitacao` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_resultado` datetime DEFAULT NULL,
  `resultado` text,
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`id_exame`),
  KEY `id_atendimento` (`id_atendimento`),
  CONSTRAINT `exame_ibfk_1` FOREIGN KEY (`id_atendimento`) REFERENCES `atendimento` (`id_atendimento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exame`
--

LOCK TABLES `exame` WRITE;
/*!40000 ALTER TABLE `exame` DISABLE KEYS */;
/*!40000 ALTER TABLE `exame` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupos_usuarios`
--

DROP TABLE IF EXISTS `grupos_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupos_usuarios` (
  `id_grupo` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_grupo`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos_usuarios`
--

LOCK TABLES `grupos_usuarios` WRITE;
/*!40000 ALTER TABLE `grupos_usuarios` DISABLE KEYS */;
INSERT INTO `grupos_usuarios` VALUES (2,'Administrador','Grupo responsável pelo gerenciamento geral do sistema hospitalar');
/*!40000 ALTER TABLE `grupos_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_agendamento`
--

DROP TABLE IF EXISTS `historico_agendamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historico_agendamento` (
  `id_historico` int NOT NULL AUTO_INCREMENT,
  `id_agendamento` int NOT NULL,
  `id_usuario` int DEFAULT NULL,
  `acao` varchar(50) NOT NULL,
  `data_hora` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `descricao` text,
  PRIMARY KEY (`id_historico`),
  KEY `id_agendamento` (`id_agendamento`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `historico_agendamento_ibfk_1` FOREIGN KEY (`id_agendamento`) REFERENCES `agendamento` (`id_agendamento`),
  CONSTRAINT `historico_agendamento_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_agendamento`
--

LOCK TABLES `historico_agendamento` WRITE;
/*!40000 ALTER TABLE `historico_agendamento` DISABLE KEYS */;
INSERT INTO `historico_agendamento` VALUES (1,1,NULL,'insert','2026-11-05 09:00:38','Novo agendamento cadastrado'),(2,1,1,'update','2026-11-05 09:06:28','Status anterior: Agendado | Novo status: Confirmado'),(3,3,NULL,'insert','2026-11-11 11:45:41','Novo agendamento cadastrado'),(4,3,NULL,'update','2026-11-11 11:46:55','Status anterior: Agendado | Novo status: Confirmado'),(5,4,NULL,'insert','2026-11-14 11:07:17','Novo agendamento cadastrado'),(6,4,4,'update','2026-11-14 23:11:45','Status anterior: Confirmado | Novo status: Confirmado');
/*!40000 ALTER TABLE `historico_agendamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_atendimento`
--

DROP TABLE IF EXISTS `historico_atendimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historico_atendimento` (
  `id_historico` int NOT NULL AUTO_INCREMENT,
  `id_atendimento` int NOT NULL,
  `id_usuario` int DEFAULT NULL,
  `acao` varchar(50) NOT NULL,
  `data_hora` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `descricao` text,
  PRIMARY KEY (`id_historico`),
  KEY `id_atendimento` (`id_atendimento`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `historico_atendimento_ibfk_1` FOREIGN KEY (`id_atendimento`) REFERENCES `atendimento` (`id_atendimento`),
  CONSTRAINT `historico_atendimento_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_atendimento`
--

LOCK TABLES `historico_atendimento` WRITE;
/*!40000 ALTER TABLE `historico_atendimento` DISABLE KEYS */;
INSERT INTO `historico_atendimento` VALUES (1,1,1,'INSERT','2026-11-05 09:12:11','Novo atendimento cadastrado'),(2,1,1,'UPDATE','2026-11-05 09:22:00','Diagnóstico anterior: Diagnóstico de teste | Novo diagnóstico: Diagnóstico atualizado para teste | Observações anteriores: Atendimento realizado para validar o sistema | Novas observações: Atendimento realizado para validar o sistema'),(3,3,NULL,'INSERT','2026-11-14 11:16:55','Novo atendimento cadastrado'),(4,3,NULL,'UPDATE','2026-11-14 11:18:49','Diagnóstico anterior: Paciente apresenta quadro estável | Novo diagnóstico: Paciente apresenta melhora no quadro clínico | Observações anteriores: Atendimento realizado para teste da API | Novas observações: Atendimento atualizado durante teste da API'),(5,3,4,'UPDATE','2026-11-14 23:18:13','Diagnóstico anterior: Paciente apresenta melhora no quadro clínico | Novo diagnóstico: Paciente apresenta melhora no quadro clínico | Observações anteriores: Atendimento atualizado durante teste da API | Novas observações: Atendimento atualizado para teste de auditoria');
/*!40000 ALTER TABLE `historico_atendimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_prescricao`
--

DROP TABLE IF EXISTS `item_prescricao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_prescricao` (
  `id_prescricao` int NOT NULL,
  `numero_item` int NOT NULL,
  `id_medicamento` int NOT NULL,
  `dosagem` varchar(50) NOT NULL,
  `frequencia` varchar(50) NOT NULL,
  `duracao` varchar(50) NOT NULL,
  PRIMARY KEY (`id_prescricao`,`numero_item`),
  KEY `id_medicamento` (`id_medicamento`),
  CONSTRAINT `item_prescricao_ibfk_1` FOREIGN KEY (`id_prescricao`) REFERENCES `prescricao` (`id_prescricao`),
  CONSTRAINT `item_prescricao_ibfk_2` FOREIGN KEY (`id_medicamento`) REFERENCES `medicamento` (`id_medicamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_prescricao`
--

LOCK TABLES `item_prescricao` WRITE;
/*!40000 ALTER TABLE `item_prescricao` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_prescricao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicamento`
--

DROP TABLE IF EXISTS `medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicamento` (
  `id_medicamento` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `principio_ativo` varchar(150) NOT NULL,
  `fabricante` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_medicamento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicamento`
--

LOCK TABLES `medicamento` WRITE;
/*!40000 ALTER TABLE `medicamento` DISABLE KEYS */;
INSERT INTO `medicamento` VALUES (2,'Dipirona 500mg','Dipirona monoidratada','Medley');
/*!40000 ALTER TABLE `medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `id_profissional` int NOT NULL,
  `crm` varchar(20) NOT NULL,
  PRIMARY KEY (`id_profissional`),
  UNIQUE KEY `crm` (`crm`),
  CONSTRAINT `medico_ibfk_1` FOREIGN KEY (`id_profissional`) REFERENCES `profissional` (`id_profissional`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` VALUES (1,'CRM-98765');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente` (
  `id_paciente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cpf` char(11) NOT NULL,
  `data_nascimento` date NOT NULL,
  `logradouro` varchar(150) DEFAULT NULL,
  `numero` varchar(10) DEFAULT NULL,
  `cidade` varchar(100) DEFAULT NULL,
  `numero_carteirinha` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_paciente`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `numero_carteirinha` (`numero_carteirinha`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES (1,'Paciente Atualizado','00000000001','1995-05-20','Avenida Central','200','Brasilia','TESTE-API-001'),(5,'Paciente Teste','98765432100','1995-05-20','Rua de Teste','100','Brasilia','TESTE-001'),(6,'Maria Elisa Alves Reina Alves','03683504102','1952-12-12','sei lá','7000','São Paulo','015');
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescricao`
--

DROP TABLE IF EXISTS `prescricao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescricao` (
  `id_prescricao` int NOT NULL AUTO_INCREMENT,
  `id_atendimento` int NOT NULL,
  `data_prescricao` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `observacoes` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id_prescricao`),
  KEY `id_atendimento` (`id_atendimento`),
  CONSTRAINT `prescricao_ibfk_1` FOREIGN KEY (`id_atendimento`) REFERENCES `atendimento` (`id_atendimento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescricao`
--

LOCK TABLES `prescricao` WRITE;
/*!40000 ALTER TABLE `prescricao` DISABLE KEYS */;
INSERT INTO `prescricao` VALUES (2,3,'2026-11-14 12:50:53','Prescrição para teste dos itens');
/*!40000 ALTER TABLE `prescricao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profissional`
--

DROP TABLE IF EXISTS `profissional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profissional` (
  `id_profissional` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cpf` char(11) NOT NULL,
  `data_admissao` date NOT NULL,
  PRIMARY KEY (`id_profissional`),
  UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profissional`
--

LOCK TABLES `profissional` WRITE;
/*!40000 ALTER TABLE `profissional` DISABLE KEYS */;
INSERT INTO `profissional` VALUES (1,'Profissional teste','00000000002','2026-01-01');
/*!40000 ALTER TABLE `profissional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profissional_especialidade`
--

DROP TABLE IF EXISTS `profissional_especialidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profissional_especialidade` (
  `id_profissional` int NOT NULL,
  `id_especialidade` int NOT NULL,
  PRIMARY KEY (`id_profissional`,`id_especialidade`),
  KEY `id_especialidade` (`id_especialidade`),
  CONSTRAINT `profissional_especialidade_ibfk_1` FOREIGN KEY (`id_profissional`) REFERENCES `medico` (`id_profissional`),
  CONSTRAINT `profissional_especialidade_ibfk_2` FOREIGN KEY (`id_especialidade`) REFERENCES `especialidade` (`id_especialidade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profissional_especialidade`
--

LOCK TABLES `profissional_especialidade` WRITE;
/*!40000 ALTER TABLE `profissional_especialidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `profissional_especialidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prontuario`
--

DROP TABLE IF EXISTS `prontuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prontuario` (
  `id_prontuario` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `data_criacao` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `observacoes_gerais` text,
  `codigo_prontuario` varchar(40) NOT NULL,
  PRIMARY KEY (`id_prontuario`),
  UNIQUE KEY `id_paciente` (`id_paciente`),
  UNIQUE KEY `codigo_prontuario` (`codigo_prontuario`),
  CONSTRAINT `prontuario_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prontuario`
--

LOCK TABLES `prontuario` WRITE;
/*!40000 ALTER TABLE `prontuario` DISABLE KEYS */;
INSERT INTO `prontuario` VALUES (1,1,'2026-11-14 11:33:27','Paciente em acompanhamento médico. Histórico inicial registrado para teste do sistema.','PRT-CF249F6FE10611F1B63102004C4F4F50'),(2,5,'2026-11-14 11:44:45','Observações atualizadas durante o teste do prontuário.','PRT-CF254E8EE10611F1B63102004C4F4F50');
/*!40000 ALTER TABLE `prontuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_prontuario_gerar_codigo` BEFORE INSERT ON `prontuario` FOR EACH ROW BEGIN
    IF NEW.codigo_prontuario IS NULL
       OR NEW.codigo_prontuario = '' THEN

        SET NEW.codigo_prontuario = fn_gerar_codigo_prontuario();

    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `telefone`
--

DROP TABLE IF EXISTS `telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telefone` (
  `id_telefone` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `telefone` varchar(20) NOT NULL,
  PRIMARY KEY (`id_telefone`),
  KEY `id_paciente` (`id_paciente`),
  CONSTRAINT `telefone_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefone`
--

LOCK TABLES `telefone` WRITE;
/*!40000 ALTER TABLE `telefone` DISABLE KEYS */;
INSERT INTO `telefone` VALUES (1,5,'(61) 99999-1234');
/*!40000 ALTER TABLE `telefone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_grupo`
--

DROP TABLE IF EXISTS `usuario_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_grupo` (
  `id_usuario` int NOT NULL,
  `id_grupo` int NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_grupo`),
  KEY `id_grupo` (`id_grupo`),
  CONSTRAINT `usuario_grupo_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `usuario_grupo_ibfk_2` FOREIGN KEY (`id_grupo`) REFERENCES `grupos_usuarios` (`id_grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_grupo`
--

LOCK TABLES `usuario_grupo` WRITE;
/*!40000 ALTER TABLE `usuario_grupo` DISABLE KEYS */;
INSERT INTO `usuario_grupo` VALUES (1,2),(4,2);
/*!40000 ALTER TABLE `usuario_grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `senha_hash` varchar(255) NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Administrador Principal','admin@hospital.com','$2a$10$mM7S8x3U7wBWINOzgzSEkuHn4Kv7V8Pb.O6bCx9TRcoC4dH8nUnG6',0),(4,'Usuario de Teste','teste@hospital.com','$2a$10$EKj8pHQIfYflsB8ZbTJKG.Psi6Ll1TX.LUJQnisXrJUdIxPE6MUZm',1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_agendamentos`
--

DROP TABLE IF EXISTS `vw_agendamentos`;
/*!50001 DROP VIEW IF EXISTS `vw_agendamentos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_agendamentos` AS SELECT 
 1 AS `id_agendamento`,
 1 AS `paciente`,
 1 AS `profissional`,
 1 AS `data_hora`,
 1 AS `status`,
 1 AS `motivo`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_atendimentos`
--

DROP TABLE IF EXISTS `vw_atendimentos`;
/*!50001 DROP VIEW IF EXISTS `vw_atendimentos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_atendimentos` AS SELECT 
 1 AS `id_atendimento`,
 1 AS `paciente`,
 1 AS `profissional`,
 1 AS `data_agendamento`,
 1 AS `data_atendimento`,
 1 AS `diagnostico`,
 1 AS `observacoes`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'hospital_db'
--

--
-- Dumping routines for database 'hospital_db'
--
/*!50003 DROP FUNCTION IF EXISTS `fn_gerar_codigo_prontuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_gerar_codigo_prontuario`() RETURNS varchar(40) CHARSET utf8mb4
    NO SQL
BEGIN
    RETURN CONCAT(
        'PRT-',
        UPPER(REPLACE(UUID(), '-', ''))
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fn_total_agendamentos_paciente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_total_agendamentos_paciente`(
    p_id_paciente INT
) RETURNS int
    READS SQL DATA
BEGIN
    DECLARE total INT;

    SELECT COUNT(*)
    INTO total
    FROM agendamento
    WHERE id_paciente = p_id_paciente;

    RETURN total;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_agendamentos_por_paciente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_agendamentos_por_paciente`(
    IN p_id_paciente INT
)
BEGIN
    SELECT
        a.id_agendamento,
        p.nome AS paciente,
        pr.nome AS profissional,
        a.data_hora,
        a.status,
        a.motivo
    FROM agendamento a
    JOIN paciente p
        ON a.id_paciente = p.id_paciente
    JOIN profissional pr
        ON a.id_profissional = pr.id_profissional
    WHERE a.id_paciente = p_id_paciente
    ORDER BY a.data_hora;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_atendimentos_por_paciente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_atendimentos_por_paciente`(
    IN p_id_paciente INT
)
BEGIN
    SELECT
        at.id_atendimento,
        p.nome AS paciente,
        pr.nome AS profissional,
        at.data_hora AS data_atendimento,
        at.diagnostico,
        at.observacoes
    FROM atendimento at
    JOIN agendamento ag
        ON at.id_agendamento = ag.id_agendamento
    JOIN paciente p
        ON ag.id_paciente = p.id_paciente
    JOIN profissional pr
        ON ag.id_profissional = pr.id_profissional
    WHERE p.id_paciente = p_id_paciente
    ORDER BY at.data_hora DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `vw_agendamentos`
--

/*!50001 DROP VIEW IF EXISTS `vw_agendamentos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_agendamentos` AS select `a`.`id_agendamento` AS `id_agendamento`,`p`.`nome` AS `paciente`,`pr`.`nome` AS `profissional`,`a`.`data_hora` AS `data_hora`,`a`.`status` AS `status`,`a`.`motivo` AS `motivo` from ((`agendamento` `a` join `paciente` `p` on((`a`.`id_paciente` = `p`.`id_paciente`))) join `profissional` `pr` on((`a`.`id_profissional` = `pr`.`id_profissional`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_atendimentos`
--

/*!50001 DROP VIEW IF EXISTS `vw_atendimentos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_atendimentos` AS select `at`.`id_atendimento` AS `id_atendimento`,`p`.`nome` AS `paciente`,`pr`.`nome` AS `profissional`,`ag`.`data_hora` AS `data_agendamento`,`at`.`data_hora` AS `data_atendimento`,`at`.`diagnostico` AS `diagnostico`,`at`.`observacoes` AS `observacoes` from (((`atendimento` `at` join `agendamento` `ag` on((`at`.`id_agendamento` = `ag`.`id_agendamento`))) join `paciente` `p` on((`ag`.`id_paciente` = `p`.`id_paciente`))) join `profissional` `pr` on((`ag`.`id_profissional` = `pr`.`id_profissional`))) */;
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

-- Dump completed on 2026-11-15 13:36:26

-- Host: localhost    Database: hospital_db
-- ------------------------------------------------------
-- Server version	8.0.46

-- SISTEMA HOSPITALAR 
-- MySQL 8.0 | Estrutura exportada da base hospital_db em 15/11/2026
-- Contem: 19 tabelas, 2 views, 5 triggers, 2 functions, 2 procedures,
-- indices, chaves estrangeiras e somente contas ficticias para demonstracao.
-- ATENCAO: o script recria as tabelas (DROP TABLE IF EXISTS) e apaga dados
-- existentes no banco selecionado. NAO o execute sobre sua base em uso.
-- Execute como usuario com permissoes de CREATE, DROP, TRIGGER e ROUTINE.
-- Para instalar usuarios do MySQL, execute depois permissoes_mysql.sql como admin.
-- As credenciais das contas de demonstracao sao apenas para ambiente local.

CREATE DATABASE IF NOT EXISTS `hospital_db`
  CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `hospital_db`;

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50003 TRIGGER `trg_agendamento_insert` AFTER INSERT ON `agendamento` FOR EACH ROW begin 
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
/*!50003 CREATE*/ /*!50003 TRIGGER `trg_agendamento_update` AFTER UPDATE ON `agendamento` FOR EACH ROW begin
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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50003 TRIGGER `trg_atendimento_insert` AFTER INSERT ON `atendimento` FOR EACH ROW BEGIN
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
/*!50003 CREATE*/ /*!50003 TRIGGER `trg_atendimento_update` AFTER UPDATE ON `atendimento` FOR EACH ROW BEGIN
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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50003 TRIGGER `trg_prontuario_gerar_codigo` BEFORE INSERT ON `prontuario` FOR EACH ROW BEGIN
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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Dados desta tabela omitidos; somente contas demonstrativas aparecem ao final.

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
CREATE FUNCTION `fn_gerar_codigo_prontuario`() RETURNS varchar(40) CHARSET utf8mb4
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
CREATE FUNCTION `fn_total_agendamentos_paciente`(
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
CREATE PROCEDURE `sp_agendamentos_por_paciente`(
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
CREATE PROCEDURE `sp_atendimentos_por_paciente`(
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
/*!50013 SQL SECURITY DEFINER */
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
/*!50013 SQL SECURITY DEFINER */
/*!50001 VIEW `vw_atendimentos` AS select `at`.`id_atendimento` AS `id_atendimento`,`p`.`nome` AS `paciente`,`pr`.`nome` AS `profissional`,`ag`.`data_hora` AS `data_agendamento`,`at`.`data_hora` AS `data_atendimento`,`at`.`diagnostico` AS `diagnostico`,`at`.`observacoes` AS `observacoes` from (((`atendimento` `at` join `agendamento` `ag` on((`at`.`id_agendamento` = `ag`.`id_agendamento`))) join `paciente` `p` on((`ag`.`id_paciente` = `p`.`id_paciente`))) join `profissional` `pr` on((`ag`.`id_profissional` = `pr`.`id_profissional`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

-- ================================================================
-- CONTAS DEMONSTRATIVAS (dados ficticios)
-- Necessarias para validar os quatro perfis apos uma restauracao limpa.
-- As senhas estao armazenadas como BCrypt (nao em texto puro).
-- ALTERE as senhas se publicar ou implantar fora de ambiente academico.
-- ================================================================
INSERT INTO `grupos_usuarios` (`id_grupo`,`nome`,`descricao`) VALUES
(2,'Administrador','Grupo responsavel pelo gerenciamento geral do sistema hospitalar'),
(3,'Medico','Profissional responsavel por atendimentos, prontuarios, exames e prescricoes'),
(4,'Enfermeiro','Profissional responsavel pelo acompanhamento e assistencia aos pacientes'),
(5,'Recepcao','Responsavel pelo cadastro de pacientes e agendamento de consultas');

INSERT INTO `usuarios` (`id_usuario`,`nome`,`email`,`senha_hash`,`ativo`) VALUES
(4,'Usuario de Teste','teste@hospital.com','$2a$10$EKj8pHQIfYflsB8ZbTJKG.Psi6Ll1TX.LUJQnisXrJUdIxPE6MUZm',1),
(5,'Medico de Teste','medico@hospital.com','$2a$10$nPsnNdtuIGz/tBFFBLuycuknIzOwkVhdz0nHhvzqU2NciNeifXHc.',1),
(6,'Enfermeiro de Teste','enfermeiro@hospital.com','$2a$10$WbbzLWiskgzFZKCbQqgbFeQdbYttoqCXgmgHZ0v4WH8e2izin/nZa',1),
(7,'Recepcionista de Teste','recepcao@hospital.com','$2a$10$WnA1rcWQyD8FB4bm5PpUqORfJKJKYG1hlaRa7XlEqkwZ9xwNMeZyq',1);

INSERT INTO `usuario_grupo` (`id_usuario`,`id_grupo`) VALUES
(4,2),(5,3),(6,4),(7,5);

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-15 22:49:32

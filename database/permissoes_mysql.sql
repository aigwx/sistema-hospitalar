-- Execute ESTE arquivo separadamente como administrador do MySQL.
-- Substitua os exemplos de senha por senhas novas e fortes, em ambiente local.
-- CREATE USER IF NOT EXISTS nao altera a senha se o usuario ja existir.
-- O dump principal nao precisa ser executado com hospital_app.

CREATE USER IF NOT EXISTS 'hospital_app'@'localhost'
  IDENTIFIED BY 'TROQUE_ESTA_SENHA_APP_ANTES_DE_EXECUTAR';
GRANT SELECT, INSERT, UPDATE, DELETE ON `hospital_db`.*
  TO 'hospital_app'@'localhost';

CREATE USER IF NOT EXISTS 'hospital_consulta'@'localhost'
  IDENTIFIED BY 'TROQUE_ESTA_SENHA_CONSULTA_ANTES_DE_EXECUTAR';
GRANT SELECT ON `hospital_db`.*
  TO 'hospital_consulta'@'localhost';

-- Verificacao opcional:
SHOW GRANTS FOR 'hospital_app'@'localhost';
SHOW GRANTS FOR 'hospital_consulta'@'localhost';

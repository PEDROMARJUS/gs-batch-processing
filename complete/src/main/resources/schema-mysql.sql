-- Base de Datos contenedora de las tablas
use pru_db;

-- Se crea la tabla para almacenar los datos
CREATE TABLE IF NOT EXISTS pruuser  (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
 , nombre VARCHAR(20)
 , apellido1 VARCHAR(20)
 , apellido2 VARCHAR(20)
);

DELETE FROM pruuser;

ALTER TABLE pruuser AUTO_INCREMENT = 1

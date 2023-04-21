
CREATE TABLE nuevosEmpleados (
  CodigoEmpleado number(6) NOT NULL,
  Nombre varchar2(50) NOT NULL,
  Apellido1 varchar2(50) NOT NULL,
  Apellido2 varchar2(50) DEFAULT NULL,
  Extension varchar2(10) NOT NULL,
  Email varchar2(100) NOT NULL,
  CodigoOficina varchar2(10) NOT NULL,
  CodigoJefe number(6) DEFAULT NULL,
  Puesto varchar2(50) DEFAULT NULL,
  PRIMARY KEY (CodigoEmpleado) 
);
/
INSERT INTO nuevosEmpleados VALUES (41,'Alicia','Gomez','Perez','35897','alix@jardineria.es','TAL-ES',777,'Representante Compras'); //error jefe
INSERT INTO nuevosEmpleados VALUES (42,'Maria','Suarez','Martinez','20899','msuarez@jardineria.es','aaaaa',11,'Jardinera'); //error ofi

INSERT INTO nuevosEmpleados VALUES (43,'Juana','Suarez','Bellido','20899','jbll@jardineria.es','BCN-ES',11,'Jardinera'); //OK, nuevo
INSERT INTO nuevosEmpleados VALUES (44,'Jymii','Bell','Grau','3321','jimiii@gardening.com','LON-UK',3,'Director Oficina'); //OK, nuevo

INSERT INTO nuevosEmpleados VALUES (45,'Lupe','Garcia','Bru','20899','lupe@jardineria.es','aaaaa',7777,'Jardinera'); //error ofi y jefe

INSERT INTO nuevosEmpleados VALUES (46,'Merche','Ramos','Gil','7565','mercheee@gardening.com','BOS-USA',20,'Representante Ventas'); //OK, nuevo

INSERT INTO nuevosEmpleados VALUES (30,'Julian','Bellinelli','','3211','jbellinelli@gardening.com','TAL-ES',2,'Subdirector Ventas'); // Actualiza oficina, puesto y jefe

INSERT INTO nuevosEmpleados VALUES (27,'Larry','Westfalls','','3322','lwestfalls@gardening.com','LON-UK',6666,'Representante Ventas'); //error al actualizar dir, no existe
INSERT INTO nuevosEmpleados VALUES (28,'John','Walton','','3322','jwalton@gardening.com','LLLLL',26,'Representante Ventas'); //error al actualiza ofi no existe

INSERT INTO nuevosEmpleados VALUES (20,'Hilary','Washington','','7565','hilarynue@gardening.com','BOS-USA',3,'Director general'); // Actualiza correo y puesto


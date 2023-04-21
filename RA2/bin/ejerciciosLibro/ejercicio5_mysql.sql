-- --------------------------------------------------------------------------
-- Tablas MYSQL:
-- --------------------------------------------------------------------------
-- CREAR BD Y USUARIO CON NOMBRE unidad2
-- --------------------------------------------------------------------------
-- ------------------
-- Tabla CLIENTES:
-- ------------------
CREATE TABLE CLIENTES (
  ID         TINYINT unsigned NOT NULL PRIMARY KEY,
  NOMBRE     varchar(50) NOT NULL,
  DIRECCION  varchar(50) DEFAULT NULL,
  POBLACION  varchar(50) DEFAULT NULL,  
  TELEF      varchar(20) DEFAULT NULL,
  NIF        varchar(10) DEFAULT NULL
) ENGINE=InnoDB;

INSERT INTO CLIENTES  VALUES
(1, 'MARIA SERRANO', 'C/Las Flores 23', 'Guadalajara', '949876655','34343434L'),
(2, 'PEDRO BRAVO', 'C/Galiano 6', 'Guadalajara', '949256376', '2256880E'),
(3, 'MANUEL SERRA', 'Av Atance 24', 'Guadalajara','949800090','1234567E'),
(4, 'ALICIA PÉREZ', 'C/La Azucena 123', 'Talavera','925678090', '56564564J');

-- Tabla PRODUCTOS:
CREATE TABLE PRODUCTOS (
  ID     MEDIUMINT unsigned NOT NULL PRIMARY KEY,
  DESCRIPCION     varchar(50) NOT NULL,
  STOCKACTUAL MEDIUMINT unsigned default 0,
  STOCKMINIMO MEDIUMINT unsigned default 0,
  PVP  DECIMAL(8,2) default 0  
) ENGINE=InnoDB;

INSERT INTO PRODUCTOS  VALUES
(4,'Diccionario Maria Moliner 2 tomos', 55,5, 43.00),
(5,'Impresora HP Deskjet F370', 10, 1, 30.65),
(6,'Pen Drive 8 Gigas', 52, 5, 7.00),
(7,'Ratón óptico inalámbrico Logitecht',14, 2, 15.00),
(8,'El señor de los anillos, 3 DVDs',8 ,2, 25.00);

-- ------------------
-- Tabla VENTAS:
-- ------------------
CREATE TABLE VENTAS (
  IDVENTA SMALLINT(5) UNSIGNED NOT NULL PRIMARY KEY,
  FECHAVENTA DATE NOT NULL,
  IDCLIENTE  TINYINT UNSIGNED NOT NULL,
  IDPRODUCTO MEDIUMINT unsigned NOT NULL,
  CANTIDAD TINYINT(3) UNSIGNED NOT NULL,       
  CONSTRAINT VENTAS_PROD FOREIGN KEY (IDPRODUCTO) REFERENCES PRODUCTOS (ID),
  CONSTRAINT VENTAS_CLI  FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTES (ID)
) ENGINE=INNODB;

INSERT INTO VENTAS VALUES
(1, '2012-07-16', 1, 4, 3),
(2, '2012-07-17', 4, 5, 2),
(3, '2012-07-19', 2, 5, 1),
(4, '2012-08-20', 1, 6, 5),
(5, '2012-08-22', 3, 4, 1);
SET SESSION FOREIGN_KEY_CHECKS=0;

/* CREAR BD: bdarticulosclien USUARIO Y NOMBRE IGUAL*/

drop table IF EXISTS ventas, clientes , articulos ;

CREATE TABLE Clientes
(
	CODCLI VARCHAR(4) NOT NULL,
	NOMBRE VARCHAR(30),
	POBLACION VARCHAR(30),
	PRIMARY KEY (CODCLI)
);

CREATE TABLE ARTICULOS
(
	CODART VARCHAR(4) NOT NULL,
	DENOMINACION VARCHAR(30),
	PVP FLOAT,
	STOCK INT,
	PRIMARY KEY (CODART)
);

create  TABLE VENTAS (
  NUMVENTA VARCHAR(4) NOT NULL,
  CODCLI VARCHAR(4), 
  CODART VARCHAR(4),
  FECHA DATE,
  UNIDADES INT,
  PRIMARY KEY (NUMVENTA) 
  ) ;
  
  ALTER TABLE VENTAS
	ADD FOREIGN KEY (CODCLI) REFERENCES Clientes (CODCLI)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
  ALTER TABLE VENTAS
	 ADD FOREIGN KEY (CODART) REFERENCES ARTICULOS (CODART)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

/* INSERTAR CLIENTES */
INSERT INTO CLIENTES VALUES ('CL1', 'Producciones límite','Talavera');
INSERT INTO CLIENTES VALUES ('CL2', 'La alberca S.A.','Talavera');
INSERT INTO CLIENTES VALUES ('CL3', 'Juán García','Madrid');
INSERT INTO CLIENTES VALUES ('CL4', 'Piscinas Moreno S.L.','Madrid');


/* INSERTAR ARTCULOS */
INSERT INTO articulos VALUES ('AR1', 'Cloro multiacción 4K',15, 10);
INSERT INTO articulos VALUES ('AR2', 'Lona Piscina 5x8',450, 3);
INSERT INTO articulos VALUES ('AR3', 'Limpiafondos manual',35, 5);
INSERT INTO articulos VALUES ('AR4', 'Elevador PH 6K',17, 10);
INSERT INTO articulos VALUES ('AR5', 'Cloro Polvo Choque 6K',25, 15);


/* INSERTAR VENTAS 
NUMVENTA  , CODCLI  , CODART , FECHA ,  UNIDADES,  */
INSERT INTO ventas values ('VE1','CL1','AR1', DATE_ADD(curdate(),interval -12 day),2);
INSERT INTO ventas values ('VE2','CL1','AR1', DATE_ADD(curdate(),interval -11 day),1);
INSERT INTO ventas values ('VE3','CL1','AR2', DATE_ADD(curdate(),interval -5 day),1);

INSERT INTO ventas values ('VE4','CL2','AR1', DATE_ADD(curdate(),interval -15 day),2);
INSERT INTO ventas values ('VE5','CL2','AR3', DATE_ADD(curdate(),interval -15 day),2);
INSERT INTO ventas values ('VE6','CL2','AR5', DATE_ADD(curdate(),interval -10 day),3);
INSERT INTO ventas values ('VE7','CL2','AR4', DATE_ADD(curdate(),interval -3 day),1);
 
 INSERT INTO ventas values ('VE8','CL3','AR2', DATE_ADD(curdate(),interval -15 day),1);
INSERT INTO ventas values ('VE9','CL3','AR3', DATE_ADD(curdate(),interval -15 day),2);
INSERT INTO ventas values ('VE10','CL3','AR5', DATE_ADD(curdate(),interval -10 day),1);
INSERT INTO ventas values ('VE11','CL3','AR1', DATE_ADD(curdate(),interval -3 day),3);
 
 INSERT INTO ventas values ('VE12','CL4','AR4', DATE_ADD(curdate(),interval -5 day),2);
INSERT INTO ventas values ('VE13','CL4','AR2', DATE_ADD(curdate(),interval -3 day),1);
 
 







SET SESSION FOREIGN_KEY_CHECKS=0;

/* crear BD de nombre empresas, usuario:empresas  y clave: empresas  */

/* Drop Tables */

DROP TABLE IF EXISTS departamentos;
DROP TABLE IF EXISTS empleados;

DROP TABLE IF EXISTS empresas;
DROP TABLE IF EXISTS oficios;


/* Create Tables */

CREATE TABLE departamentos
(
	coddepart int NOT NULL,
	nombre varchar(25) NOT NULL,
	direccion varchar(35),
	localidad varchar(35),
	codjefedepartamento int,
	codempre int NOT NULL,
	PRIMARY KEY (coddepart)
);


CREATE TABLE empleados
(
	codemple int NOT NULL,
	nombre varchar(35) NOT NULL,
	direccion varchar(35),
	poblacion varchar(25),
	fechaalta date,
	codencargado int ,
	coddepart int NOT NULL,
	codoficio int NOT NULL,
	PRIMARY KEY (codemple)
);




CREATE TABLE empresas
(
	codempre int NOT NULL,
	nombre varchar(35) NOT NULL,
	direccion varchar(60) NOT NULL,
	tlf varchar(15),
	presupuesto float,
	codsector int NOT NULL,
	sede int NOT NULL,
	PRIMARY KEY (codempre)
);


CREATE TABLE oficios
(
	codoficio int NOT NULL,
	nombre varchar(35)NOT NULL,
	salariomes float,
	preciotrienio float,
	PRIMARY KEY (codoficio)
);



/* Insertamos */

/* 	codoficio int NOT NULL, 	nombre varchar(30)NOT NULL,	salariomes float,	preciotrienio float,*/

insert into oficios values (1, 'TÉCNICO ADMINISTRATIVO', 2000, 50);
insert into oficios values (2, 'ABOGADO', 2700, 100);
insert into oficios values (3, 'ECONOMISTA', 2700, 100);
insert into oficios values (4, 'INFORMÁTICO', 2900, 100);
insert into oficios values (5, 'TÉCNICO INFORMÁTICO', 2300, 50);

insert into oficios values (11, 'TÉCNICO GANADERO', 2000, 50);
insert into oficios values (12, 'PEÓN GANADERO', 1500, 50);
insert into oficios values (13, 'INGENIERO AGRÍCOLA ', 2500, 60);

insert into oficios values (21, 'TÉCNICO INDUSTRIAL', 2000, 50);
insert into oficios values (22, 'TÉCNICO ENERGÍAS', 2100, 50);
insert into oficios values (23, 'INGENIERO INDUSTRIAL ', 2600, 60);

insert into oficios values (31, 'INGENIERO TELECO ', 2600, 60);
insert into oficios values (32, 'COMERCIAL ', 2500, 80);
insert into oficios values (33, 'LIMPIADOR', 1500, 70);


/* 	codempre int NOT NULL,	nombre varchar(30) NOT NULL,	direccion varchar(30) NOT NULL,
	tlf varchar(15),	presupuesto float,	codsector int NOT NULL,	sede int NOT NULL,
	PRIMARY KEY (codempre) */

insert into empresas values (1,'Comercios Madrid','Polígono 724. 28211. Madrid','910078909', 150000, 32, 724); /*sede espa*/
insert into empresas values (2,'GANADERÍAS S.A.','Finca Las Moreras S/N. Talavera. 45600','925889090', 120000, 10,724); /*sede espa*/

insert into empresas values (3,'Comunicaciones TALA S.A.','C/Francisco Aguirre. Talavera. 45600','925808990', 125000, 31,372); /*sede irlanda */

insert into empresas values (4,'Construcciones Indrustiales S.L.', 'Nave 112. Polígono LAS ROSETAS. 28001','916808990', 225000, 20,784); /*sede emiratos*/
insert into empresas values (5,'Indrustias renovables. S.L.', 'Polígono MIRALRIO 1. 19001','949808990', 245000, 21,32); /*sede argentina*/

insert into empresas values (10,'Toldos Sin Sol', 'Poligono 7. Urbanizacion Rosas. 45600','925889900', 185000, 20,784); /*sede emiratos*/
insert into empresas values (12,'Los Pochoclos ', 'Polígono El pibe 17. 19006','949213456', 155000, 21,32); /*sede argentina*/



/* departamentos
 *	coddepart int NOT NULL,	nombre varchar(20) NOT NULL,	direccion varchar(30),
	localidad varchar(30),	codjefedepartamento int,	codempre int NOT NULL, */

/* empresa 1 */
insert into departamentos values(11, 'GESTIÓN', 'C/Mayor 17', 'Madrid', 101, 1);
insert into departamentos values(12, 'ALMACÉN', 'Polígono Torrehierro. 10', 'Talavera de la Reina', 102, 1);


/* empresa 2 */
insert into departamentos values(21, 'GESTIÓN', 'C/Mayor 17', 'Talavera de la Reina', 201, 2);


/* empresa 3 */
insert into departamentos values(31, 'GESTIÓN', 'C/Arboleda 10', 'Madrid', 301, 3);
insert into departamentos values(32, 'DISTRIBUCIÓN', 'Polígono Alfares 22', 'Talavera de la Reina', 321, 3);



/* empresa 4 */
insert into departamentos values(41, 'GESTIÓN', 'Edificio 4 Torres 2', 'Abu DAbi', 401, 4);
insert into departamentos values(42, 'MARKETING', 'C/ Jovellanos 2 .28001', 'Madrid', 411, 4);


/* Trabajadores codemple int NOT NULL,	nombre varchar(30) NOT NULL,
	direccion varchar(30),	poblacion varchar(20),	fechaalta date,
	codencargado int NOT NULL,	coddepart int NOT NULL, 	codoficio int NOT NULL, 
 */

/* departamento 11 y 12 */
insert into empleados values(101, 'Pedro Martínez', 'C/Los Sauces 5', 'Madrid', '2000/01/01',      null, 11 , 13 );
insert into empleados values(107, 'Juana Salazar', 'C/Los Catedritos 3', 'Madrid', '2005/01/12',    101, 11 , 1 );
   
insert into empleados values(102, 'Luisa Ros', 'C/Barricas 15', 'Talavera de la Reina','2001/11/02',         101, 12,  13  );
insert into empleados values(103, 'Juan Sales', 'C/Los Sauces 5', 'Madrid', '2005/11/12',                    102, 12 , 11 );
insert into empleados values(104, 'Antonio Rios', 'C/Los Alfareros 34', 'Talavera de la Reina','2006/01/01', 102, 12 ,11 );
insert into empleados values(105, 'Alberto Martín', 'C/Los Abetros 35', 'Oropesa', '2008/01/01',             102, 12 ,12  );
insert into empleados values(106, 'María Suarez', 'C/Los Molinos 10', 'Oropesa', '2010/01/06',               102, 12 ,12 );


/* departamento 21 */
insert into empleados values(201, 'J Manuel Salas', 'C/Lapiceros 22', 'Madrid',  '2003/01/02',             null, 21 , 3 );
insert into empleados values(202, 'Mariano Embid', 'C/Saucedilla 45', 'Talavera de la Reina','2004/11/11', 201, 21,  23  );
insert into empleados values(203, 'Casimiro Regatas', 'C/Los Lucillos 33', 'Oropesa',   '2004/01/01',     201, 21 , 1 );
insert into empleados values(204, 'Alicia Martín', 'C/Las Minas 65', 'Talavera de la Reina','2006/10/10', 201, 21 ,5 );
insert into empleados values(205, 'Juana Orea', 'C/Las Minas 67', 'Talavera de la Reina',  '2007/11/10',   201, 21 ,5 );


/* departamento 31 y 32 */  

insert into empleados values(301, 'J Antonio Rodriguez', 'Avda Mangantes 33', 'Madrid',  '2005/12/02',              null, 31 , 3 );
insert into empleados values(302, 'Marcelino Pan y Vino', 'C/Los Serranos 23','Talavera de la Reina','2005/12/22', 301, 31,  4  );
insert into empleados values(303, 'Juan Maria Cordel', 'C/Uclés 4', 'Oropesa',  '2006/08/02',             301, 31 , 1 );

insert into empleados values(321, 'Amalia García', 'C/Puerto Rico 1', 'Madrid',  '2006/09/02',              301, 32 , 31 );
insert into empleados values(322, 'Antonia Gómez', 'C/La Perla 3', 'Talavera de la Reina', '2007/11/12',    321, 32,  22  );
insert into empleados values(323, 'Ainoa Yangh', 'C/Las Rosas 4', 'Oropesa',   '2007/12/02',                321, 32 , 22 );
insert into empleados values(324, 'Alberlo Melgares', 'C/Cavernas 55', 'Talavera de la Reina','2009/03/02', 321, 32 ,21 );
insert into empleados values(325, 'Jorge López', 'Finca Carnaval', 'Talavera de la Reina',  '2010/04/02',   321, 32 ,21 );


/* departamento 41 y 42 */

insert into empleados values(401, 'Juna Pedro Regaad ', 'Hotel Luz 7- Apt 20', 'Abu DAbi',  '2008/01/02',  null, 41 , 3 );
insert into empleados values(402, 'Hassan Asaad', 'Hotel Luz 7- Apt 21','Abu DAbi','2008/09/02', 401, 41,  2  );

insert into empleados values(411, 'Zaira Amahs', 'C/Los Goya 53', 'Madrid',   '2008/11/02',                  401, 42 , 4 );
insert into empleados values(412, 'Sonia Álvarez', 'C/Romerías 333', 'Talavera de la Reina', '2008/11/11',    411, 42,  5  );
insert into empleados values(413, 'Pedro Ramirez', 'Avda FCO AGG 467', 'Madrid', '2009/01/02',                  411, 42 , 5 );
insert into empleados values(414, 'Silvia Garcés', 'C/Las Inviernas 4', 'Madrid','2009/01/02',           411, 42 ,32 );
insert into empleados values(415, 'Antón Pérez', 'Hotel Mariluz - apt 5', 'Madrid', '2009/10/05',        411, 42 ,32 );
insert into empleados values(416, 'Fernando Sagunto', 'Avad Pedro Pérez 4', 'Madrid',  '2010/01/02',     411, 42 ,1 );
insert into empleados values(417, 'Mohamed Brain', 'C/ Los informáticos 5', 'Toledo',   '2010/06/02',    411, 42 ,1 );




/* Create Foreign Keys */

ALTER TABLE departamentos
	ADD FOREIGN KEY (codjefedepartamento)
	REFERENCES empleados (codemple);

ALTER TABLE departamentos
	ADD FOREIGN KEY (codempre)
	REFERENCES empresas (codempre);
	

ALTER TABLE empleados
	ADD FOREIGN KEY (codencargado)
	REFERENCES empleados (codemple);

ALTER TABLE empleados
	ADD FOREIGN KEY (codoficio)
	REFERENCES oficios (codoficio);

ALTER TABLE empleados
	ADD FOREIGN KEY (coddepart)
	REFERENCES departamentos (coddepart);
	

	


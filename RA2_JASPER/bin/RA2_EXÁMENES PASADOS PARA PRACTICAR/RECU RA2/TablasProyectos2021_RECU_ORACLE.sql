
/* BORRAR TABLAS */
DROP TABLE  colaboradores cascade constraints;
DROP TABLE empresas cascade constraints;
DROP TABLE proyectos cascade constraints;
DROP TABLE empleados cascade constraints;
DROP TABLE  departamentos cascade constraints;

/* Cambiamos formato fecha para los inser */

ALTER session SET NLS_DATE_FORMAT='YYYY-MM-DD';

/* Create Tables */
CREATE TABLE departamentos
(
	coddepartamento number(10,0) NOT NULL,
	nombre varchar2(30),
	direccion varchar2(40),
	tlf varchar2(10),
	coddirector number(10,0),
	PRIMARY KEY (coddepartamento)
);

INSERT INTO departamentos VALUES (1,'Dep Electronica. Univ Madrid','Avda Universidad 4. Madrid', '91342233',1);
INSERT INTO departamentos VALUES (2,'Dep Telemática SS', 'Avda Las Lomas 52. Talavera','925009988', 30);
INSERT INTO departamentos VALUES (3,'Matemáticas Telemáticas', 'Avda Las fábricas 6. Toledo', '95220099',50);
INSERT INTO departamentos VALUES (4,'Marketing y Presentación', 'Polígono Miraalcampo 33. Madrid','91880099',60);
INSERT INTO departamentos VALUES (5,'Materiales y combustibles', 'C/Los Pinares 200. Toledo', '925330330',70);



CREATE TABLE empleados
(
	codempleado number(10,0) NOT NULL,
	nombre varchar2(30),
	direccion varchar2(40),
	tlf varchar2(10),
	fechaalta date,
	codjefe number(10,0),
	coddepartamento number(10,0),
	PRIMARY KEY (codempleado)
);

/*DEP 1 */
INSERT INTO empleados VALUES (1,'ALICIA GARCÍA RAMOS' ,'C/Los Sauces 3. Talavera. ESP', '925666777','2018-01-20', null, 1 );
INSERT INTO empleados VALUES (2,'RAQUEL GARRIDO SANZ' , 'C/Ronda 9. Madrid. ESP', '910066777', '2018-01-23',1,1 );
INSERT INTO empleados VALUES (3,'CESSARE LANFALONI' , 'C/Via Platta. Peruggia. ITA', '925666777', '2018-01-24',1,1 );	
INSERT INTO empleados VALUES (4,'MATHEO CORLEONE' , 'C/Dolce Vita. Nápoles. ITA', '875666022',  '2018-02-15',2,1);

/*DEP 2 */
INSERT INTO empleados VALUES (30,'WILIAN STEWART' , 'Street Penny Lane 12. UK', '556677232', '2018-02-15',null,2);
INSERT INTO empleados VALUES (6,'DAVID HUTTON' , 'Street Football 2. Londres. UK', '234009911', '2018-02-25',30,2 );
INSERT INTO empleados VALUES (7,'ALEJANDRO MARTOS' , 'C/La Dolorosa 34. Madrid. ESP', '91222333',  '2018-04-15',30,2);

/*DEP 3 */
INSERT INTO empleados VALUES (50,'PEDRO SULIVAN' , 'C/Bardales 34. Talavera. ESP', '925444000', '2018-04-15',null, 3 );
INSERT INTO empleados VALUES (9,'MARIA TENAILLE' , 'C/Frco Aguirre 30. Talavera. ESP', '925888777', '2018-04-25',50,3 );
INSERT INTO empleados VALUES (10,'JUANDE RAMOS' , 'C/Los Dolomitas 23. Madrid. ESP', '91000999',  '2018-04-15',9,3);
INSERT INTO empleados VALUES (11,'MARÍA JIMÉNEZ SULIVAN' , 'C/Buero Vallejo 43. Guadalajara. ESP', '925444000', '2018-01-15',9,3 );

/*DEP 4 */
INSERT INTO empleados VALUES (60,'ANTONIO CAMACHO' , 'C/Emerson Fitipaldi 6. Talavera. ESP', '925888777', '2018-02-25',null,4 );
/*DEP 5 */
INSERT INTO empleados VALUES (70,'MARÍA GUERRERO' , 'C/Las Galeras 32. Madrid. ESP', '910088771', '2018-04-25', null,5);
	
	

CREATE TABLE empresas
(
	codempresa number(10,0) NOT NULL,
	nombre varchar2(45),
	tlf varchar2(10),
	direccion varchar2(40),
	pais varchar2(30),
	nombrerepresentante varchar2(30),
	PRIMARY KEY (codempresa)
);


INSERT INTO empresas VALUES (1,'Financiera de Electricidad', '91888999','Avda Madrid 4. Madrid','España', 'Juan Gómez');
INSERT INTO empresas VALUES (2,'Financiera de IFC', '92588999','Avda Logroño 22. Talavera','España', 'Pedro Alborada');
INSERT INTO empresas VALUES (3,'Banco emprendedores', '949220099','Avda Bardales 4. Paris','Francia','Philipp Rennes');
INSERT INTO empresas VALUES (4,'Centros Comerciales 5G', '911880088','Polígono Alcalá 1. Madrid','España','Arsenio García');
INSERT INTO empresas VALUES (5,'Gasolineras Limpias', '914443330','C/La mina 223. Bruselas','Bélgica', 'Joan Rouge');
	
	
CREATE TABLE proyectos
(
	codproyecto number(10,0) NOT NULL,
	nombre varchar2(45) UNIQUE,
	fechainicio date,
	fechafin date,
	presupuesto float,
	aportaciones float,
	coddepartamento number(10,0) NOT NULL,
	patrocinador number(10,0) NOT NULL,
	PRIMARY KEY (codproyecto)
);

/* Insert Proyectos */
/* dep 1 */
insert into proyectos values (1,'Big data para Redes Sociales','2018-01-10', '2020-03-10',150000.0, 0, 1, 1 );
insert into proyectos values (2,'Apps para la programación algorítmica', '2018-02-05', '2021-05-10',180000.0, 0, 1, 1 );
insert into proyectos values (3,'Herramientas de Ciberseguridad y auditoría', '2018-03-01', '2021-09-01',190000.0, 0, 1, 2 );
insert into proyectos values (4,'Sistemas de Control de acceso a instituciones', '2018-04-01', '2020-12-10',160000.0, 0, 1, 2 );

/* dep 2 */
insert into proyectos values (5,'Big data Comercialización','2018-01-15', '2020-01-10',150000.0, 0, 2, 2 );
insert into proyectos values (6,'Aplicaciones en la red', '2018-02-16', '2021-05-15',170000.0, 0, 2, 3 );
insert into proyectos values (7,'Herramientas de matemática', '2018-03-01', '2021-12-01',130000.0, 0, 2, 3 );


/* dep 3 */
insert into proyectos values (8,'Control cuántico', '2018-04-15', '2022-12-15',160000.0, 0, 3, 3);
insert into proyectos values (9,'Big data cuántica','2018-05-15', '2022-03-15',150000.0, 0, 3, 4 );

/* dep 4 */
insert into proyectos values (10,'Cuantica y Algorítmica', '2018-02-05', '2019-05-10',180000.0, 0, 4, 4 );
insert into proyectos values (11,'Appss Cibersataques', '2018-05-01', '2020-03-01',150000.0, 0, 4, 4 );
insert into proyectos values (12,'Caritos cuánticos', '2019-04-01', '2022-12-10',190000.0, 0, 4, 5 );

/* dep 5 */
insert into proyectos values (13,'Sistemas energéticos', '2018-04-01', '2020-12-10',160000.0, 0, 5, 5 );


CREATE TABLE colaboradores
(
	codempresa number(10,0) NOT NULL,
	codproyecto number(10,0) NOT NULL,
	aportacion float,
	fechacolaboracion date,
	PRIMARY KEY (codempresa, codproyecto)
);


/* Empresa 1 colabora con proyectos 3 , 4 y 5 */
insert into colaboradores values (1,3, 10000, '2018-03-15');
insert into colaboradores values (1,4, 15000, '2018-04-15');
insert into colaboradores values (1,5, 16000, '2018-05-15');

/* Empresa 2 colabora con proyectos 2, 6, 7, 8, 10 */
insert into colaboradores values (2,2, 5000, '2018-05-10');
insert into colaboradores values (2,6, 7000, '2018-07-10');
insert into colaboradores values (2,7, 10000, '2018-08-10');
insert into colaboradores values (2,8, 12000, '2018-10-10');
insert into colaboradores values (2,11, 5000, '2018-10-10');


/* Empresa 3 colabora con proyectos 1, 2, 9, 10, 11, 12 */
insert into colaboradores values (3,1, 10000, '2018-04-15');
insert into colaboradores values (3,2, 15000, '2018-05-15');
insert into colaboradores values (3,9, 3000, '2018-12-10');
insert into colaboradores values (3,10, 5000, '2018-12-10');
insert into colaboradores values (3,11, 6000, '2018-12-10');


/* Empresa 4 colabora con proyectos  2, 3, 10, 11, 12, 13*/
insert into colaboradores values (4,2, 8000, '2018-06-15');
insert into colaboradores values (4,3, 7000, '2018-07-15');
insert into colaboradores values (4,10, 15000, '2018-09-15');
insert into colaboradores values (4,11, 8000, '2018-10-10');
insert into colaboradores values (4,12, 5000, '2019-12-10');
insert into colaboradores values (4,13, 6000, '2018-09-10');


/* Empresa 5 colabora con proyectos   12*/
insert into colaboradores values (5,12, 16000, '2019-10-10');


/* Tablas ajenas */
/* Create Foreign Keys */

ALTER TABLE empleados
	ADD CONSTRAINT empleados_ibfk_1 FOREIGN KEY (coddepartamento)
	REFERENCES departamentos (coddepartamento)
;

ALTER TABLE proyectos
	ADD CONSTRAINT proyectosfkdepart FOREIGN KEY (coddepartamento)
	REFERENCES departamentos (coddepartamento)
;

ALTER TABLE departamentos
	ADD CONSTRAINT departamentos_ibfk_1 FOREIGN KEY (coddirector)
	REFERENCES empleados (codempleado)
;

ALTER TABLE empleados
	ADD CONSTRAINT empleados_ibfk_2 FOREIGN KEY (codjefe)
	REFERENCES empleados (codempleado)
;

ALTER TABLE colaboradores
	ADD CONSTRAINT colaboradores_ibfk_1 FOREIGN KEY (codempresa)
	REFERENCES empresas (codempresa)
;

ALTER TABLE proyectos
	ADD CONSTRAINT proyectos_ibfk_2 FOREIGN KEY (patrocinador)
	REFERENCES empresas (codempresa)
;

ALTER TABLE colaboradores
	ADD CONSTRAINT colaboradores_ibfk_2 FOREIGN KEY (codproyecto)
	REFERENCES proyectos (codproyecto)
;

ALTER session SET NLS_DATE_FORMAT='DD-MM-YYYY';





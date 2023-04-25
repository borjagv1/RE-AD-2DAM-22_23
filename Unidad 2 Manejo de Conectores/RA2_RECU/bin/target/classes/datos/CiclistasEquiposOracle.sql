-- CREAR USUARIO CICLISTAS Y CLAVE CICLISTAS
-- TABLAS ORACLE

DROP TABLE EQUIPOSSINCICLISTAS CASCADE CONSTRAINTS;
DROP table lleva CASCADE CONSTRAINTS;
DROP table camisetas CASCADE CONSTRAINTS;
DROP table  tramospuertos CASCADE CONSTRAINTS;
drop table etapas CASCADE CONSTRAINTS;
drop table  ciclistas CASCADE CONSTRAINTS;
drop table  equipos CASCADE CONSTRAINTS;


--
-- TABLA equipos 
-- 
CREATE TABLE equipos (
 codigoequipo  NUMBER NOT NULL PRIMARY KEY,
 nombreequipo  VARCHAR2(35) NOT NULL, 
 director      VARCHAR2(40) NOT NULL,
 pais      VARCHAR2(3) NOT NULL 
) ;


INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('AG2R Citroën Team', 'FRA',11, 'Kaleb Godon');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Astana Qazaqstan Team' ,'KAZ',9, 'Storm Pechell');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Bahrain Victorious' ,'BHR',15, 'Manny Penson');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Bora – Hansgrohe' ,'GER',8, 'Ailey Oddboy');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Cofidis' ,'FRA',4, 'Gui Kubiczek');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('EF Education – Easypost' ,'USA',3, 'Denny Teligin');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Groupama - FDJ' ,'FRA',16, 'Natalya Dougary');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Ineos Grenadiers' ,'GBR',2, 'Deane Semorad');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Jumbo-Visma' ,'NED',90, 'Caroljean Dumbleton');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Lotto Soudal' ,'BEL',6, 'Micky Dobrowski');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Movistar Team' ,'ESP',1, 'Artur McGrorty');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Quick-Step Alpha Vinyl Team' ,'BEL',12, 'Stacey Frotton');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Team BikeExchange - Jayco' ,'AUS',14, 'Conrad Kidds');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Team DSM' ,'NED',7, 'Eugine Vamplus');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('Trek – Segafredo' ,'USA',18, 'Shaughn Quarton');
INSERT INTO equipos (nombreequipo, pais, codigoequipo, director )
     VALUES ('UAE Team Emirates' ,'UAE',33, 'Shaine Titterton'); 
-- ---------------------------------------
--  ciclistas
-- --------------------------------
CREATE TABLE ciclistas (
	codigociclista INT NOT NULL PRIMARY KEY, 
	nombreciclista VARCHAR2(50) NOT NULL, 
	fechanacimiento date NOT NULL, 
	peso DECIMAL(5,2) NOT NULL, 
	codigoequipo INT not null, 
	jefeEquipo INT,
	CONSTRAINT  fK1 FOREIGN KEY (jefeEquipo) REFERENCES ciclistas(codigociclista),
	CONSTRAINT  fk2 FOREIGN KEY(codigoequipo) REFERENCES equipos(codigoequipo)
) ;


-- equipo 90 Jumbo-Visma
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(1,'PRIMOŽ ROGLIČ',TO_DATE('1998-01-02','YYYY-MM-DD') ,60.6 , 90, 1);

INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(2,'EDOARDO AFFINI',TO_DATE('2000-11-02','YYYY-MM-DD') ,59.5 , 90, 1);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(3,'ROHAN DENNIS',TO_DATE('2000-09-22','YYYY-MM-DD') ,70.6 , 90, 1);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(4,'ROBERT GESINK',TO_DATE('1992-06-11','YYYY-MM-DD') ,60.2 , 90, 1);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(5,'CHRIS HARPER',TO_DATE('2001-01-29','YYYY-MM-DD') ,65.6 , 90, 1);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(6,'SEPP KUSS',TO_DATE('1998-07-14','YYYY-MM-DD') ,67.3 , 90, 1);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(7,'SAM OOMEN',TO_DATE('1997-12-12','YYYY-MM-DD') ,69.6 , 90, 1);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(8,'MIKE TEUNISSEN',TO_DATE('1996-09-22','YYYY-MM-DD') ,710.4 , 90, 1);

-- equipo 11 AG2R Citroën Team
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(11,'BEN OCONNOR',TO_DATE('1998-01-02','YYYY-MM-DD') ,69.6 , 11, 11);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(12,'CLÉMENT CHAMPOUSSIN',TO_DATE('1998-01-02','YYYY-MM-DD') ,67.8 , 11, 11);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(13,'JAAKKO HÄNNINEN',TO_DATE('1999-01-02','YYYY-MM-DD') ,70.6 , 11, 11);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(14,'BOB JUNGELS',TO_DATE('1999-06-06' ,'YYYY-MM-DD'),72.6 , 11, 11);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(15,'NANS PETERS',TO_DATE('2000-01-02' ,'YYYY-MM-DD') ,76.6 , 11, 11);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(16,'NICOLAS PRODHOMME',TO_DATE('1998-01-02','YYYY-MM-DD') ,65.6 , 11, 11);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(17,'ANTOINE RAUGEL',TO_DATE('1998-12-12','YYYY-MM-DD') ,67.6 , 11, 11);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(18,'ANDREA VENDRAME',TO_DATE('1997-09-22','YYYY-MM-DD') ,60.6 , 11, 11);

-- equipo 9 Astana Qazaqstan Team
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(21,'MIGUEL ANGEL LOPEZ',TO_DATE('1996-07-09','YYYY-MM-DD') ,67.4 , 9, 21);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(22,'SAMUELE BATTISTELLA',TO_DATE('1999-07-06','YYYY-MM-DD') ,65.2 , 9, 21);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(23,'DAVID DE LA CRUZ',TO_DATE('1997-01-02','YYYY-MM-DD') ,68.6 , 9, 21);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(24,'YEVGENIY FEDOROV',TO_DATE('2000-08-22','YYYY-MM-DD'),71.6 , 9, 21);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(25,'ALEXEY LUTSENKO',TO_DATE('2001-05-14','YYYY-MM-DD') ,72.5 , 9, 21);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(26,'VINCENZO NIBALI',TO_DATE('1999-06-15','YYYY-MM-DD') ,69.2 , 9, 21);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(27,'VADIM PRONSKIY',TO_DATE('1997-01-30','YYYY-MM-DD') ,68.6 , 9, 21);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(28,'HAROLD ALFONSO TEJADA',TO_DATE('1999-01-02','YYYY-MM-DD') ,73.4 , 9, 21);

-- equipo 15 BAHRAIN VICTORIOUS
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(35,'LUIS LEON SANCHEZ',TO_DATE('1997-01-30','YYYY-MM-DD') ,67.6 , 15, 35);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(31,'MIKEL LANDA',TO_DATE('1997-01-30','YYYY-MM-DD') ,64.6 , 15, 35);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(32,'SANTIAGO BUITRAGO SANCHEZ',TO_DATE('1997-01-30','YYYY-MM-DD') ,68.6 , 15, 35);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(33,'GINO MÄDER',TO_DATE('1997-01-30','YYYY-MM-DD'),78.6 , 15, 35);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(34,'WOUTER POELS',TO_DATE('1997-01-30' ,'YYYY-MM-DD'),66.6 , 15, 35);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(36,'JASHA SÜTTERLIN',TO_DATE('1997-01-30','YYYY-MM-DD') ,65.4 , 15, 35);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(37,'FRED WRIGHT',TO_DATE('1997-01-30' ,'YYYY-MM-DD'),67.6 , 15, 35);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(38,'EDOARDO ZAMBANINI',TO_DATE('1997-01-30','YYYY-MM-DD') ,62.6 , 15, 35);

-- equipo 8  BORA - HANSGROHE
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(48,'DANNY VAN POPPEL',TO_DATE('1999-11-20','YYYY-MM-DD') ,62.6 , 8, 48);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(41,'SAM BENNETT',TO_DATE('2000-02-13','YYYY-MM-DD') ,72.6 , 8, 48);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(42,'MATTEO FABBRO',TO_DATE('1998-04-25' ,'YYYY-MM-DD'),62.4 , 8, 48);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(43,'SERGIO ANDRES HIGUITA',TO_DATE('1999-05-30','YYYY-MM-DD' ),64.3 , 8, 48);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(44,'JAI HINDLEY',TO_DATE('1999-12-12','YYYY-MM-DD') ,65.6 , 8, 48);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(45,'WILCO KELDERMAN',TO_DATE('2001-01-30' ,'YYYY-MM-DD'),66.6 , 8, 48);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(46,'JONAS KOCH',TO_DATE('2002-04-30','YYYY-MM-DD') ,68.6 , 8, 48);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(47,'RYAN MULLEN',TO_DATE('2004-09-27','YYYY-MM-DD') ,72.6 , 8, 48);

-- equipo 4 cofidis
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(54,'THOMAS CHAMPION',TO_DATE('1997-01-30' ,'YYYY-MM-DD'),65.6 , 4, 54);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(51,'JESUS HERRADA',TO_DATE('1998-01-30','YYYY-MM-DD') ,68.4 , 4, 54);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(52,'BRYAN COQUARD',TO_DATE('1996-01-30','YYYY-MM-DD') ,69.4 , 4, 54);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(53,'DAVIDE CIMOLAI',TO_DATE('1999-01-30','YYYY-MM-DD') ,62.6 , 4, 54);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(55,'RUBEN FERNANDEZ',TO_DATE('1997-01-30' ,'YYYY-MM-DD'),64.2 , 4, 54);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(56,'JOSE HERRADA',TO_DATE('2000-01-30','YYYY-MM-DD') ,65.6 , 4, 54);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(57,'RÉMY ROCHAS',TO_DATE('2000-02-12','YYYY-MM-DD') ,75.6 , 4, 54);
INSERT INTO ciclistas( codigociclista , nombreciclista, fechanacimiento, peso, codigoequipo, jefeEquipo) VALUES
(58,'DAVIDE VILLELLA',TO_DATE('1999-05-07','YYYY-MM-DD') ,72.6 , 4, 54);
-- ---------------------------------
-- ETAPAS
-- ---------------------------------
CREATE TABLE etapas (
 codigoetapa  INT NOT NULL PRIMARY KEY,
 tipoetapa    VARCHAR2(35) NOT NULL , 
 fechasalida  VARCHAR2(45) NOT NULL, 
 pobsalida    VARCHAR2(65) NOT NULL , 
 pobllegada    VARCHAR2(65) NOT NULL , 
 km  decimal(6,2) NOT NULL,
 ciclistaganador INT NOT NULL ,
 CONSTRAINT fk3 FOREIGN KEY  (ciclistaganador) REFERENCES ciclistas(codigociclista) 
) ;

-- --
INSERT INTO etapas VALUES
(1,	'Contrarreloj por equipos', 'Viernes, 19 de agosto de 2022', 'Utrecht', 'Utrecht', 23.3, 1 );
INSERT INTO etapas VALUES
(2, 'Llana', 'Sábado, 20 de agosto de 2022','Hertogenbosch','Utrecht',175.1, 14);

INSERT INTO etapas VALUES	
(3, 'Llana' ,'Domingo, 21 de agosto de 2022','Breda', 'Breda', 	193.5, 58);
INSERT INTO etapas VALUES
(4, 'Media Montaña', 'Martes, 23 de agosto de 2022','Vitoria-Gasteiz','Laguardia',	152.5, 1 );
INSERT INTO etapas VALUES
(5, 'Media Montaña','Miércoles, 24 de agosto de 2022','Irun','Bilbao',187.2, 43);

INSERT INTO etapas VALUES
(6,'Montaña','Jueves, 25 de agosto de 2022','Bilbao','Ascensión al Pico Jano. San Miguel de Aguayo',181.2, 15);
-- -----
INSERT INTO etapas VALUES
(7, 'Media Montaña','Viernes, 26 de agosto de 2022','Camargo','Cistierna',190, 5);

INSERT INTO etapas VALUES
(8, 'Montaña','Sábado, 27 de agosto de 2022','La Pola Llaviana/Pola de Laviana','Colláu Fancuaya. Yernes y Tameza',	153.4 , 55);

INSERT INTO etapas VALUES
(9, 'Montaña', 'Domingo, 28 de agosto de 2022','Villaviciosa','Les Praeres. Nava',	171.4 ,43);

INSERT INTO etapas VALUES 
(10, 'Contrarreloj individual'	,'Martes, 30 de agosto de 2022','Elche','Alicante',	30.9 ,55);

INSERT INTO etapas VALUES
(11, 'Llana',	'Miércoles, 31 de agosto de 2022',	'ElPozo Alimentación', 'Cabo de Gata',191.2, 53);
--
INSERT INTO etapas VALUES
(12, 'Llana. Final en alto'	,'Jueves, 1 de septiembre de 2022',	'Salobreña','Peñas Blancas. Estepona',	192.7, 34);

INSERT INTO etapas VALUES
(13, 'Llana','Viernes, 2 de septiembre de 2022'	,'Ronda' , 'Montilla', 168.4, 1);	

--
INSERT INTO etapas VALUES
(14,'Montaña',	'Sábado, 3 de septiembre de 2022'	,'Montoro' , 'Sierra de La Pandera',160.3 , 45);
INSERT INTO etapas VALUES
(15,'Montaña'	,'Domingo, 4 de septiembre de 2022'	,'Martos' , 'Sierra Nevada. Alto Hoya de la Mora. Monachil',	153, 32);

INSERT INTO etapas VALUES
(16, 'Llana',	'Martes, 6 de septiembre de 2022'	,'Sanlúcar de Barrameda' , 'Tomares',	189.4 , 42);
--
INSERT INTO etapas VALUES
(17, 'Llana. Final en alto',	'Miércoles, 7 de septiembre de 2022',	'Aracena', 'Monasterio de Tentudía',162.3 , 1);

INSERT INTO etapas VALUES
(18,'Montaña',	'Jueves, 8 de septiembre de 2022'	,'Trujillo' , 'Alto de Piornal',	192 , 35);
INSERT INTO etapas VALUES
(19, 'Media Montaña',	'Viernes, 9 de septiembre de 2022','Talavera de la Reina' , 'Talavera de la Reina',	138.3 , 45);

--
INSERT INTO etapas VALUES
(20, 'Montaña', 'Sábado, 10 de septiembre de 2022',	'Moralzarzal', 'Puerto de Navacerrada',	181, 45);

INSERT INTO etapas VALUES
(21, 'Llana','Domingo, 11 de septiembre de 2022','Las Rozas','Madrid. Paisaje de la Luz',96.7, 18);

-- -------------------------------
-- tramospuertos
--
CREATE TABLE tramospuertos (
 codigotramo  INT NOT NULL PRIMARY KEY,
 nombretramo  VARCHAR2(50) NOT NULL, 
 km  decimal(6,2) NOT NULL,
 categoria INT NOT NULL,
 pendiente VARCHAR2(50) NOT NULL, 
 numetapa  INT NOT NULL, 
 ciclistaganador INT NOT NULL,  
 CONSTRAINT fk4 FOREIGN KEY (ciclistaganador) REFERENCES ciclistas(codigociclista),
 CONSTRAINT fk5 FOREIGN KEY (numetapa) REFERENCES etapas(codigoetapa ) 
) ;

-- Etapa 2
-- Puertos Categorizados: 103 km: Alto de Amerongse (Cat 4) (2,2 km al 2,3%)
INSERT INTO tramospuertos VALUES
(21, 'Alto de Amerongse', 103, 4, '2,2 km al 2,3%',	2, 32);

-- Etapa 3
-- Puertos Categorizados: 59,6 km: Rijzendeweg (Cat 4) (0,3 km al 3,3%)
INSERT INTO tramospuertos VALUES
(31, 'Rijzendeweg', 59.6, 4, '0,3 km al 3,3%',	3, 16);

-- Etapa 4:
-- Puertos Categorizados:
-- 95,6 km: Puerto de Opakua (Cat 2) (5,3 km al 6,8%)
-- 21,8 km: Puerto de Herrera (Cat 3) (6,8 km al 5%) 
INSERT INTO tramospuertos VALUES
(41, 'Puerto de Opakua', 5.3, 2, '5,3 km al 6,8%',	4, 25);
INSERT INTO tramospuertos VALUES
(42, 'Puerto de Herrera', 6.8, 3, '6,8 km al 5%',	4, 34);

-- Etapa 5:
-- Puertos Categorizados
-- 96,9 km: Puerto de Gontzagarinaga (Cat 3) (4,3 km al 5,1%)
-- 89 km: Balcón de Bizkaia (Cat 3) (4,2 km al 5,6%)
-- 70,4 km: Alto de Morga (Cat 3) (8,1 km al 3,6%)
-- 47,8 km: Alto del Vivero (Cat 2) (4,6 km al 7,9%)
-- 19 km: Alto del Vivero (Cat 2) (4,6 km al 7,9%) 
INSERT INTO tramospuertos VALUES
(51, 'Puerto de Gontzagarinaga', 96.9, 3, '4,3 km al 5,1%',	5, 21);
INSERT INTO tramospuertos VALUES
(52, 'Balcón de Bizkaia', 89, 3, '4,2 km al 5,6%',5, 32);
INSERT INTO tramospuertos VALUES
(53, 'Alto de Morga', 70.4, 3, '8,1 km al 3,6%',5, 21);
INSERT INTO tramospuertos VALUES
(54, 'Alto del Vivero', 47.8, 2, '4,6 km al 7,9%',	5, 25);
INSERT INTO tramospuertos VALUES
(55, 'Alto del Vivero', 19, 2, '4,6 km al 7,9%',5, 34);


-- Etapa 6:
-- Puertos Categorizados
-- 112,2 km: Puerto de Alisas (Cat 2) (8,1 km al 6,4%)
-- 42,2 km: Collada de Brenes (Cat 1) (6,3 km al 8,6%) 
-- 12,6 km: Ascensión al Pico Jano (Cat 1) (12,4 km al 6,6%)
INSERT INTO tramospuertos VALUES
(61, 'Puerto de Alisas', 112.2, 2, '8,1 km al 6,4%',	6, 22);
INSERT INTO tramospuertos VALUES
(62, 'Collada de Brenes', 42.2, 1, '6,3 km al 8,6%',	6, 8);
INSERT INTO tramospuertos VALUES
(63, 'Ascensión al Pico Jano', 12.6, 1, '12,4 km al 6,6%',	6, 8);

-- Etapa 7:
-- Puertos Categorizados: 86,9 km: Puerto de San Glorio (Cat 1) (19,5 km al 5,8%)
INSERT INTO tramospuertos VALUES
(71, 'Puerto de San Glorio', 86.9, 1, '19,5 km al 5,8%',7, 25);

-- Etapa 8:
-- 150 km: Alto de La Colladona (Cat 2) (7 km al 6,3%)
-- 111,4 km: Alto de La Mozqueta (Cat 2) (7,2 km al 6%)
-- 92,6 km: Alto de Santo Emiliano (Cat 3) (5,8 km al 5,3%)
-- 60,6 km: Puerto de Tenebreo (Cat 3) (5,9 km al 5,4%) 
-- 39,6 km: Perlavia (Cat 3) (3,8 km al 7,9%)
-- 10,2 km: Colláu Fancuaya (Cat 1) (10,2 km al 7,8%)
INSERT INTO tramospuertos VALUES
(81, 'Alto de La Colladona', 150, 2, '7 km al 6,3%',8, 36);
INSERT INTO tramospuertos VALUES
(82, 'Alto de La Mozqueta', 11.4, 2, '7,2 km al 6%',8, 26);
INSERT INTO tramospuertos VALUES
(83, 'Alto de Santo Emiliano', 92.6, 3, '5,8 km al 5,3%',8, 4);
INSERT INTO tramospuertos VALUES
(84, 'Puerto de Tenebreo', 60.6, 3, '5,9 km al 5,4%',8, 37);
INSERT INTO tramospuertos VALUES
(85, 'Perlavia', 39.6, 3, '3,8 km al 7,9%',8, 4);
INSERT INTO tramospuertos VALUES
(86, 'Colláu Fancuaya', 10.2, 1, '10,2 km al 7,8%',8, 7);

-- Etapa 9:
-- 123,4 km: Alto del Torno (Cat 2) (8,3 km al 5,7%)
-- 87,4 km: Mirador del Fito (Cat 1) (10 km al 5,5%)
-- 60,4 km: Alto de la Llama (Cat 3) (6,7 km al 5,2%)
-- 32,1 km: La Campa (Cat 3) (9 km al 4,1%) 
-- 3,8 km: Les Praeres (Cat 1) (3,8 km al 12,9%)
INSERT INTO tramospuertos VALUES
(91, 'Alto del Torno', 123.4, 2, '8,3 km al 5,7%',9, 32);
INSERT INTO tramospuertos VALUES
(92, 'Mirador del Fito', 87.4, 1, '10 km al 5,5%',9, 6);
INSERT INTO tramospuertos VALUES
(93, 'Alto de la Llama', 60.4, 3, '6,7 km al 5,2%',9, 17);
INSERT INTO tramospuertos VALUES
(94, 'La Campa', 32.1, 3, '9 km al 4,1%',9, 32);
INSERT INTO tramospuertos VALUES
(95, 'Les Praeres', 3.8, 1, '3,8 km al 12,9%',9, 24);


-- Etapa 12:
-- 19 km: Peñas Blancas (Cat 1) (18,9 km al 6,5%)
INSERT INTO tramospuertos VALUES
(121, 'Peñas Blancas', 19, 1, '18,9 km al 6,5%',12, 43);


-- Etapa 14:
-- 63 km: Puerto de Siete Pilillas (Cat 3) (9,8 km al 3,5%)
-- 22,5 km: Puerto de Los Villares (Cat 2) (10 km al 5,5%)  
-- 8,4 km: Sierra de la Pandera (Cat 1) (8,6 km al 7,5%)
INSERT INTO tramospuertos VALUES
(141, 'Puerto de Siete Pilillas', 63, 3, '9,8 km al 3,5%',14, 7);
INSERT INTO tramospuertos VALUES
(142, 'Puerto de Los Villares', 22.5, 2, '10 km al 5,5%',14, 17);
INSERT INTO tramospuertos VALUES
(143, 'Sierra de la Pandera', 8.4, 1, '8,6 km al 7,5%',14, 8);


-- Etapa 15:
-- 122,3 km: Puerto del Castillo (Cat 3) (5,9 km al 5%)
-- 48,3 km: Alto del Purche (Cat 1) (9 km al 7,5%)  
-- 19,4 km: Sierra Nevada (Cat ESP) (19,4 km al 7,9%)
INSERT INTO tramospuertos VALUES
(151, 'Puerto del Castillo', 122.3, 3, '5,9 km al 5%',15, 43);
INSERT INTO tramospuertos VALUES
(152, 'Alto del Purche', 48.3, 1 , '9 km al 7,5%',15, 7);
INSERT INTO tramospuertos VALUES
(153, 'Sierra Nevada', 19.4, 10, '19,4 km al 7,9%',15, 8);

-- Etapa 17:
-- 9,4 km: Monasterio de Tentudía (Cat 2) (9,4 km al 5,2%)
INSERT INTO tramospuertos VALUES
(171, 'Monasterio de Tentudía', 9.4, 2, '9,4 km al 5,2%',17, 25);


-- Etapa 18:
-- 86 km: Alto de la Desperá (Cat 2) (10,3 km al 10,3%)
-- 54,3 km: Alto de Piornal (Cat 1) (13,6 km al 5%)  
-- 13,3 km: Alto de Piornal (Cat 1) (13,3 km al 5,6%)
INSERT INTO tramospuertos VALUES
(181, 'Alto de la Desperá', 86, 2, '10,3 km al 10,3%',18, 24);
INSERT INTO tramospuertos VALUES
(182, 'Alto de Piornal', 54.3, 1, '13,6 km al 5%',18, 35);
INSERT INTO tramospuertos VALUES
(183, 'Alto de Piornal', 13.3, 1, '13,3 km al 5,6%',18, 5);

-- Etapa 19:
-- 117,3 km: Puerto del Piélago (Cat 2) (8,9 km al 6%)
-- 51,3 km: Puerto del Piélago (Cat 2) (8,9 km al 6%)
INSERT INTO tramospuertos VALUES
(191, 'Puerto del Piélago', 117.3, 2, '8,9 km al 6%',19, 24);
INSERT INTO tramospuertos VALUES
(192, 'Puerto del Piélago', 51.3, 2, '8,9 km al 6%',19, 35);

-- Etapa 20:
-- 157,3 km: Puerto de Navacerrada (Cat 1) (10 km al 6,8%)
-- 98 km: Puerto de Navafría (Cat 2) (10 km al 5,5%)
-- 61,7 km: Puerto de Canencia (Cat 2) (7,3 km al 4,9%)
-- 46,7 km: Puerto de La Morcuera (Cat 1) (9,2 km al 6,8%)  
-- 17 km: Puerto de Cotos (Cat 1) (10,5 km al 5,6%)

INSERT INTO tramospuertos VALUES
(201, 'Puerto de Navacerrada', 157.3, 1, '10 km al 6,8%',20, 24);
INSERT INTO tramospuertos VALUES
(202, 'Puerto de Navafría', 98, 2, '10 km al 5,5%',20, 14);
INSERT INTO tramospuertos VALUES
(203, 'Puerto de Canencia', 61.7, 2, '7,3 km al 4,9%',20, 34);
INSERT INTO tramospuertos VALUES
(204, 'Puerto de La Morcuera', 46.7, 1, '9,2 km al 6,8%',20, 24);
INSERT INTO tramospuertos VALUES
(205, 'Puerto de Cotos', 17, 1, '10,5 km al 5,6%',20, 14);

-- ---------------------------------
-- CAMISETAS( codigocamiseta , tipo, color, importepremio)
--
CREATE TABLE camisetas (
 codigocamiseta  INT NOT NULL PRIMARY KEY,
 tipo            VARCHAR2(54) NOT NULL, 
 color           VARCHAR2(10) NOT NULL,
 importepremio      decimal not NULL 
) ;

insert into camisetas VALUES
(1,'Maillot de lider de la General', 'Rojo', 5000 );
insert into camisetas VALUES
(2,'Maillot de líder de la Clasificación por puntos', 'Verde', 3500 );
insert into camisetas VALUES
(3,'Maillot del mejor Joven', 'Blanco', 2500 );
insert into camisetas VALUES
(4,'Maillot del Rey de la Montaña', 'Lunares', 4000 );

-- 
-- lleva( numetapa, codigocamiseta,codigociclista )
--
CREATE TABLE lleva (
 numetapa        INT NOT NULL,
 codigocamiseta  INT NOT NULL ,
 codigociclista  INT NOT NULL,   
 CONSTRAINT fk55 FOREIGN KEY (codigociclista) REFERENCES ciclistas(codigociclista),
 CONSTRAINT fk6 FOREIGN KEY (numetapa) REFERENCES etapas(codigoetapa ),
 CONSTRAINT fk7 FOREIGN KEY (codigocamiseta) REFERENCES camisetas(codigocamiseta ),
 CONSTRAINT fk8 PRIMARY key (numetapa,  codigocamiseta)
) ;

-- ETAPAS 1 A 21 - CAMISETAS 1 A 4  CICLISTAS , camiseta 3 mas joven 25 5 2 3
-- CICLISTAS 1 A 8 , 11 A 18 , 21 A 28, 31 A 38, 41 A 48, 51 A 58
-- etapa 1

insert into lleva VALUES (1, 1, 8);
insert into lleva VALUES  (1, 2, 18);
insert into lleva VALUES  (1, 3, 5);
insert into lleva VALUES  (1, 4, 51);

-- etapa 2
insert into lleva VALUES (2, 1, 8);
insert into lleva VALUES (2, 2, 51);
insert into lleva VALUES  (2, 3, 25);
insert into lleva VALUES (2, 4, 35);

-- etapa 3
insert into lleva VALUES (3, 1, 8);
insert into lleva VALUES (3, 2, 2);
insert into lleva VALUES (3, 3, 15);
insert into lleva VALUES (3, 4, 35);

-- etapa 4
insert into lleva VALUES (4, 1, 8);
insert into lleva VALUES (4, 2, 15);
insert into lleva VALUES (4, 3, 5);
insert into lleva VALUES (4, 4, 35);

-- etapa 5
insert into lleva VALUES (5, 1, 43);
insert into lleva VALUES (5, 2, 8);
insert into lleva VALUES (5, 3, 25);
insert into lleva VALUES (5, 4, 35);

-- etapa 6
insert into lleva VALUES (6, 1, 16);
insert into lleva VALUES (6, 2, 15);
insert into lleva VALUES (6, 3, 5);
insert into lleva VALUES (6, 4, 35);

-- etapa 7
insert into lleva VALUES (7, 1, 18);
insert into lleva VALUES (7, 2, 15);
insert into lleva VALUES (7, 3, 5);
insert into lleva VALUES (7, 4, 35);

-- etapa 8
insert into lleva VALUES (8, 1, 32);
insert into lleva VALUES (8, 2, 16);
insert into lleva VALUES (8, 3, 5);
insert into lleva VALUES (8, 4, 48);

-- etapa 9
insert into lleva VALUES (9, 1, 25);
insert into lleva VALUES (9, 2, 16);
insert into lleva VALUES (9, 3, 5);
insert into lleva VALUES (9, 4, 32);

-- etapa 10
insert into lleva VALUES (10, 1, 48);
insert into lleva VALUES (10, 2, 16);
insert into lleva VALUES (10, 3, 32);
insert into lleva VALUES (10, 4, 5);

-- etapa 11
insert into lleva VALUES (11, 1, 51);
insert into lleva VALUES (11, 2, 5);
insert into lleva VALUES (11, 3, 16);
insert into lleva VALUES  (11, 4, 32);

-- etapa 12
insert into lleva VALUES (12, 1, 43);
insert into lleva VALUES  (12, 2, 16);
insert into lleva VALUES  (12, 3, 32);
insert into lleva VALUES  (12, 4, 48);

-- etapa 13
insert into lleva VALUES (13, 1, 48);
insert into lleva VALUES  (13, 2, 16);
insert into lleva VALUES  (13, 3, 2);
insert into lleva VALUES  (13, 4, 32);

-- etapa 14
insert into lleva VALUES (14, 1, 48);
insert into lleva VALUES  (14, 2, 16);
insert into lleva VALUES  (14, 3, 2);
insert into lleva VALUES  (14, 4, 32);

-- etapa 15
insert into lleva VALUES (15, 1, 48);
insert into lleva VALUES  (15, 2, 2);
insert into lleva VALUES  (15, 3, 16);
insert into lleva VALUES  (15, 4, 32);


-- etapa 16
insert into lleva VALUES (16, 1, 48);
insert into lleva VALUES  (16, 2, 32);
insert into lleva VALUES  (16, 3, 2);
insert into lleva VALUES (16, 4, 16);

-- etapa 17
insert into lleva VALUES (17, 1, 48);
insert into lleva VALUES  (17, 2, 16);
insert into lleva VALUES  (17, 3, 2);
insert into lleva VALUES  (17, 4, 32);

-- etapa 18
insert into lleva VALUES (18, 1, 48);
insert into lleva VALUES  (18, 2, 16);
insert into lleva VALUES  (18, 3, 2);
insert into lleva VALUES  (18, 4, 32);

-- etapa 19
insert into lleva VALUES (19, 1, 48);
insert into lleva VALUES  (19, 2, 16);
insert into lleva VALUES  (19, 3, 2);
insert into lleva VALUES  (19, 4, 32);

-- etapa 20
insert into lleva VALUES (20, 1, 4);
insert into lleva VALUES  (20, 2, 32);
insert into lleva VALUES  (20, 3, 16);
insert into lleva VALUES  (20, 4, 2);

-- etapa 21
insert into lleva VALUES (21, 1, 48);
insert into lleva VALUES  (21, 2, 16);
insert into lleva VALUES  (21, 3, 4);
insert into lleva VALUES  (21, 4, 32);


COMMIT;
-- ------------------------------------------------------
-- Datos insertados al azar, cualquier parecido con la 
-- realidad es pura coincidencia
-- ------------------------------------------------------

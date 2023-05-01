-- crear bd ra3hib, usuario y clave ra3hib
-- base de datos MySQL ra3hib, usuario y clave con el mismo nombre. 

SET SESSION FOREIGN_KEY_CHECKS=0;

DROP TABLE if exists  repventas CASCADE ;
DROP TABLE if exists  oficinas CASCADE;
DROP TABLE if exists  regiones  CASCADE;
DROP TABLE IF EXISTS  productos;
DROP TABLE IF EXISTS  ventas;

-- ***************TABLA regiones ********************** 

create table regiones (
cod_region  int not null primary key,  
nombre_re   varchar(25)
) engine=innodb;

INSERT INTO regiones VALUES (1, 'ANDALUCIA');
INSERT INTO regiones VALUES (2, 'LEVANTE');
INSERT INTO regiones VALUES (3, 'CATALUÑA');
INSERT INTO regiones VALUES (4, 'CENTRO');
INSERT INTO regiones VALUES (5, 'EXTREMADURA');
INSERT INTO regiones VALUES (6, 'NORTE');


-- ***************TABLA oficinas *********************** */

create table oficinas (
oficina int not null primary key,   
ciudad  varchar(15) not null,  
cod_region  int not null , 
director  int,        
total_ventas  float not null
)  engine=innodb;


INSERT INTO oficinas VALUES (11, 'Alicante',2,106,4900);
INSERT INTO oficinas VALUES (12, 'Barcelona',3,	104,	4900);
INSERT INTO oficinas VALUES (13,'Valencia',2, 	105, 2303);
INSERT INTO oficinas VALUES (21,'Cádiz',1,108,	4988);
INSERT INTO oficinas VALUES (22,'Sevilla',1,108, 1780);
INSERT INTO oficinas VALUES (23,'Huelva',1,NULL, 1000);
INSERT INTO oficinas VALUES (30,'Guadalajara',4,NULL, 1000);


-- ***************TABLA repventas *********************** */
create table repventas (
 numero_rep int not null primary key,   
 nombre   varchar(15) not null, 
 edad     int,           
 oficina_rep int ,   
 director int,      
 num_ventas int,     
 imp_ventas float not null
) engine=innodb;


INSERT INTO repventas VALUES (110,'Antonio Casado',41,11,101,2,707);
INSERT INTO repventas VALUES (106,'José Sánchez',52,11,NULL,15,1612);
INSERT INTO repventas VALUES (109,'Maria Gutierrez',31,11,106,2,1999);
INSERT INTO repventas VALUES (103,'Pablo Cruz',29,12,104,5,1286);
INSERT INTO repventas VALUES (101,'David Ruiz',45,12,104,2,2001);
INSERT INTO repventas VALUES (104,'Juan Agudo',33,12,106,12,2200);
INSERT INTO repventas VALUES (105,'Pedro Adams',37,13,104,23,2054);
INSERT INTO repventas VALUES (108,'Carlos Figo',62,21,106,3,2100);
INSERT INTO repventas VALUES (102,'Sara Agudo',48,21,108,22,2789);
INSERT INTO repventas VALUES (107,'Juani Annea',49,22,108,4,1090);

INSERT INTO repventas VALUES (200,'Anonimo',NULL,NULL,NULL,7,1590);
INSERT INTO repventas VALUES (201,'Anonima',NULL,NULL,NULL,3,3500);

COMMIT; 

-- *********************** CREACIÓN DE CLAVES AJENAS */
ALTER TABLE repventas
  ADD CONSTRAINT FKrpvoficinas FOREIGN KEY (oficina_rep) REFERENCES oficinas (oficina)   ON DELETE CASCADE ;
  
ALTER TABLE repventas 
    ADD CONSTRAINT directorrepventas FOREIGN KEY  ( director  )  REFERENCES repventas ( numero_rep ) ;

ALTER TABLE oficinas 
    ADD CONSTRAINT directoroficinas FOREIGN KEY ( director ) REFERENCES repventas ( numero_rep ) ;

ALTER TABLE oficinas 
    ADD CONSTRAINT oficinasregiones FOREIGN KEY ( cod_region  ) REFERENCES regiones ( cod_region  ) 
    ON DELETE CASCADE ;


-- -Tabla PRODUCTOS:
create table productos (
  id mediumint unsigned not null primary key,
  descripcion varchar(50) not null,
  stockactual mediumint unsigned default 0,
  stockminimo mediumint unsigned default 0,
  pvp  decimal(8,2) default 0  
) engine=innodb;


INSERT INTO productos  VALUES
(1,'Diccionario Maria Moliner 2 tomos', 55,5, 43.00),
(2,'Impresora HP Deskjet F370', 10, 1, 30.65),
(3,'Pen Drive 8 Gigas', 52, 5, 7.00),
(4,'Ratón óptico inalámbrico Logitecht',14, 2, 15.00),
(5,'El señor de los anillos, 3 DVDs',8 ,2, 25.00);

-- ------------------
-- Tabla VENTAS:
-- ------------------
create table ventas (
  idventa smallint(5) unsigned not null auto_increment primary key,
  fechaventa date not null,
  numero_rep int  not null,    
  idproducto mediumint unsigned not null,
  cantidad tinyint(3) unsigned not null,       
  constraint ventas_prod foreign key (idproducto) references productos (id),
  constraint ventas_rep  foreign key (numero_rep) references repventas (numero_rep)
) engine=innodb;


INSERT INTO ventas VALUES
(1, '2012-07-16', 101, 3, 3),
(2, '2012-07-17', 104, 2, 2),
(3, '2012-07-19', 102, 2, 1),
(4, '2012-08-20', 101, 1, 5),
(6, '2012-08-22', 103, 1, 1),
(7, '2012-09-11', 102, 2, 1),
(8, '2012-09-16', 101, 5, 5),
(9, '2012-09-22', 103, 2, 1);







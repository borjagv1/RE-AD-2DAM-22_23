
drop table reservas cascade constraints;
drop table clientes cascade constraints;
drop table habitaciones cascade constraints;
drop table Tiposhabitaciones cascade constraints;


create  TABLE clientes (
  CodigoCliente int NOT NULL,
  NombreCliente varchar2(50) NOT NULL,
  Apellido varchar2(30) DEFAULT NULL,
  Telefono varchar2(15),
  direccion varchar2(40),
  Ciudad varchar2(30) NOT NULL,
  Region varchar2(30) DEFAULT NULL,
  Pais varchar2(30)  NOT NULL,
  CodigoPostal varchar2(10) ,
  LimiteCredito float ,
  PRIMARY KEY (CodigoCliente)
  ) ;
/

INSERT INTO clientes VALUES (1,'Daniel G','GoldFish','5556901745','False Street 52 2 A','San Francisco',NULL,'USA','24006',3000);
INSERT INTO clientes VALUES (3,'Anne','Wright','5557410345','Wall-e Avenue','Miami','Miami','USA','24010',6000);

INSERT INTO clientes VALUES (2,'Oscar','Mayorga','925667788','Principal 1','Talavera','Castilla la Mancha','ESPAÑA','45600',5000);
INSERT INTO clientes VALUES (4,'Enzo','Santos Mayorga','75202345','Las lanchas 10','Madrid','Madrid','ESPAÑA','28600',4000);
INSERT INTO clientes VALUES (5,'Raquel','García Gant','66654433','Cornella 22','Barcelona','Cataluña','ESPAÑA','08680',4000);

INSERT INTO clientes VALUES (6,'Mary','Futvher','4475202345','Street 1 - A22','Londres',null,'INGLATERRA','828600',4600);
INSERT INTO clientes VALUES (7,'Paolo','Picolino','36455345','Atrio 1 - A22','Peruggia',null,'ITALIA','987700',2600);

INSERT INTO clientes VALUES (8,'Pedro','Rius','2333440','Avenida flores 1','Madrid','Madrid','ESPAÑA','28009',2300);

CREATE TABLE Tiposhabitaciones (
  Tipo varchar2(30) NOT NULL,
  DescripcionTexto varchar2(50),
  Precio float not null,
  PRIMARY KEY (Tipo)
);
/
INSERT INTO Tiposhabitaciones VALUES ('Doble1','Dos camas individuales 90',50);
INSERT INTO Tiposhabitaciones VALUES ('Doble2','Dos camas individuales 105',60);
INSERT INTO Tiposhabitaciones VALUES ('Twin','Dos camas unidas 200',60);
INSERT INTO Tiposhabitaciones VALUES ('Individual1','Una cama 105',30);
INSERT INTO Tiposhabitaciones VALUES ('Individual2','Una cama 105 mas office',60);
INSERT INTO Tiposhabitaciones VALUES ('SuiteA','Suite de 100 metros',160);
INSERT INTO Tiposhabitaciones VALUES ('SuiteB','Suite de 130 metros',180);


create TABLE habitaciones (
  NumHabitacion varchar2(4) NOT NULL, 
  NombreHabitacion varchar2(50) DEFAULT ' ',
  Tipo varchar2(30) ,
  PRIMARY KEY (NumHabitacion),
  CONSTRAINT habtipoFK FOREIGN KEY (Tipo) REFERENCES Tiposhabitaciones (Tipo)
  ) ;
/

INSERT INTO habitaciones VALUES ('0101','La rosa','Doble1');
INSERT INTO habitaciones VALUES ('0102','Candelaria','Twin');
INSERT INTO habitaciones VALUES ('0103','La verde','Individual1');
INSERT INTO habitaciones VALUES ('0104','Calera','Individual2');

INSERT INTO habitaciones VALUES ('0201','La rosa','Doble2');
INSERT INTO habitaciones VALUES ('0202','Candelaria','Twin');
INSERT INTO habitaciones VALUES ('0203','La verde','Individual1');
INSERT INTO habitaciones VALUES ('0204','Calera','Individual1');

INSERT INTO habitaciones VALUES ('0301','MiraGredos','SuiteA');
INSERT INTO habitaciones VALUES ('0302','La Sierra','SuiteB');
INSERT INTO habitaciones VALUES ('0303','Azul','Individual2');



create TABLE reservas (
  codreserva int not null,
  NumHabitacion varchar2(4) NOT NULL, 
  CodigoCliente int NOT NULL,
  Fechaentrada date not null,
  Fechasalida date not null,
  PRIMARY KEY (codreserva),
  CONSTRAINT resclientesFK FOREIGN KEY (CodigoCliente) REFERENCES clientes (CodigoCliente),
  CONSTRAINT reshabitacionesFK FOREIGN KEY (NumHabitacion) REFERENCES habitaciones (NumHabitacion)
 ) ;
/

/* 1 y 2 usa, 3 4 5 españa, 6 Inglaterra 7  Italia) -- 1 T 200, abril 150  */

insert into reservas values (1,'0101', 1,  sysdate-200, sysdate -198 );
insert into reservas values (3,'0103', 1,  sysdate-50, sysdate -47 );

insert into reservas values (2,'0102', 2, sysdate-150, sysdate -146  );
insert into reservas values (4,'0202', 2,  sysdate-30, sysdate -26  );
insert into reservas values (5,'0302', 2, sysdate-10, sysdate -4  );


insert into reservas values (6,'0201', 3, sysdate-240, sysdate-237);
insert into reservas values (7,'0202', 3,  sysdate-140, sysdate-137);
insert into reservas values (8,'0201', 3,  sysdate-40, sysdate-37);
insert into reservas values (12,'0301', 3,  sysdate-15, sysdate -13 );

insert into reservas values (9,'0202', 4,  sysdate-20, sysdate-15 );
insert into reservas values (10,'0301', 4,  sysdate, sysdate + 2 );

insert into reservas values (11,'0303', 5,  sysdate, sysdate + 3 );

insert into reservas values (13,'0202', 6,  sysdate-220, sysdate -217);
insert into reservas values (14,'0302', 6,  sysdate-160, sysdate -156 );
insert into reservas values (15,'0302', 6,  sysdate, sysdate + 4 );

insert into reservas values (16,'0302', 7,  sysdate-233, sysdate -230 );
insert into reservas values (17,'0302', 7,  sysdate-203, sysdate -200 );
insert into reservas values (18,'0201', 7,  sysdate, sysdate + 4 );


commit;







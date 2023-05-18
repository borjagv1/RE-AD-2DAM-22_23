--------------------------------------------------------
-- Archivo creado  - lunes-junio-13-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table TALUMNOS
--------------------------------------------------------

drop table TNOTAS cascade constraints;
/
drop table talumnos cascade constraints;
/
drop table tasignaturas cascade constraints;
/
drop table tcursos cascade constraints;

/
  CREATE TABLE "TALUMNOS" 
   (	"DNI" VARCHAR2(10 BYTE), 
	"NOMBRE" VARCHAR2(50 BYTE), 
	"DIRECCION" VARCHAR2(50 BYTE), 
	"POBLACION" VARCHAR2(50 BYTE), 
	"CODPOSTAL" NUMBER(5,0), 
	"PROVINCIA" VARCHAR2(40 BYTE), 
	"TELEFONO1" VARCHAR2(15 BYTE), 
	"TELEFONO2" VARCHAR2(15 BYTE), 
	"FECHA_NAC" DATE, 
	"ID_CURSO" NUMBER(4,0)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
--------------------------------------------------------
--  DDL for Table TASIGNATURAS
--------------------------------------------------------

  CREATE TABLE "TASIGNATURAS" 
   (	"COD_ASIG" NUMBER(4,0), 
	"NOMBRE" VARCHAR2(80 BYTE), 
	"TIPO" CHAR(1 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
--------------------------------------------------------
--  DDL for Table TCURSOS
--------------------------------------------------------

  CREATE TABLE "TCURSOS" 
   (	"ID_CURSO" NUMBER(4,0), 
	"DESCRIPCION" VARCHAR2(60 BYTE), 
	"NIVEL" VARCHAR2(30 BYTE), 
	"TURNO" CHAR(1 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
--------------------------------------------------------
--  DDL for Table TNOTAS
--------------------------------------------------------

  CREATE TABLE "TNOTAS" 
   (	"DNI" VARCHAR2(10 BYTE), 
	"COD_ASIG" NUMBER(4,0), 
	"NOTA1EV" NUMBER(4,2), 
	"NOTA2EV" NUMBER(4,2), 
	"NOTA3EV" NUMBER(4,2), 
	"NOTAFJUN" NUMBER(4,2), 
	"NOTASEPT" NUMBER(4,2)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
REM INSERTING into TALUMNOS
SET DEFINE OFF;
REM INSERTING into TASIGNATURAS
SET DEFINE OFF;
REM INSERTING into TCURSOS
SET DEFINE OFF;
REM INSERTING into TNOTAS
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index PKALUM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PKALUM" ON "TALUMNOS" ("DNI") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
--------------------------------------------------------
--  DDL for Index PKASIG
--------------------------------------------------------

  CREATE UNIQUE INDEX "PKASIG" ON "TASIGNATURAS" ("COD_ASIG") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
--------------------------------------------------------
--  DDL for Index PKCURSOS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PKCURSOS" ON "TCURSOS" ("ID_CURSO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
--------------------------------------------------------
--  DDL for Index PKNOT
--------------------------------------------------------

  CREATE UNIQUE INDEX "PKNOT" ON "TNOTAS" ("DNI", "COD_ASIG") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM" ;
/
--------------------------------------------------------
--  Constraints for Table TALUMNOS
--------------------------------------------------------

  ALTER TABLE "TALUMNOS" ADD CONSTRAINT "PKALUM" PRIMARY KEY ("DNI")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table TASIGNATURAS
--------------------------------------------------------

  ALTER TABLE "TASIGNATURAS" ADD CONSTRAINT "PKASIG" PRIMARY KEY ("COD_ASIG")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table TCURSOS
--------------------------------------------------------

  ALTER TABLE "TCURSOS" ADD CONSTRAINT "PKCURSOS" PRIMARY KEY ("ID_CURSO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
/
--------------------------------------------------------
--  Constraints for Table TNOTAS
--------------------------------------------------------

  ALTER TABLE "TNOTAS" ADD CONSTRAINT "PKNOT" PRIMARY KEY ("DNI", "COD_ASIG")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table TALUMNOS
--------------------------------------------------------

  ALTER TABLE "TALUMNOS" ADD CONSTRAINT "FKALUM" FOREIGN KEY ("ID_CURSO")
	  REFERENCES "TCURSOS" ("ID_CURSO") ENABLE;
/
--------------------------------------------------------
--  Ref Constraints for Table TNOTAS
--------------------------------------------------------

  ALTER TABLE "TNOTAS" ADD CONSTRAINT "FKNOT1" FOREIGN KEY ("COD_ASIG")
	  REFERENCES "TASIGNATURAS" ("COD_ASIG") ENABLE;
 
  ALTER TABLE "TNOTAS" ADD CONSTRAINT "FKNOT2" FOREIGN KEY ("DNI")
	  REFERENCES "TALUMNOS" ("DNI") ENABLE;
/


-- INSERCION DE DATOS


insert into tasignaturas values (1, 'Asignatura 1', 'A');
insert into tasignaturas values (2, 'Asignatura 2', 'A');
insert into tasignaturas values (3, 'Asignatura 3', 'B');
insert into tasignaturas values (4, 'Asignatura 4', 'B');
insert into tasignaturas values (5, 'Asignatura 5', 'A');

insert into tcursos values (1, 'Curso 1', 'BACH',   'V');
insert into tcursos values (2, 'Curso 2', 'FP1',   'D');
insert into tcursos values (3, 'Curso 3', 'FP2',   'V');
insert into tcursos values (4, 'Curso 4', 'GM',   'T');
insert into tcursos values (5, 'Curso 5', 'FP2',   'D');
insert into tcursos values (6, 'Curso 6', 'GS',   'V');

--Alumnos curso 1
insert into talumnos values ('111A', 'Alumno 1', 'C/dire 1', 'Madrid', 28009, 'MADRID','1239', '9122233', 
   sysdate-5000, 1);
insert into talumnos values ('222A', 'Alumno 2', 'C/dire 2', 'Toledo', 45001, 'TOLEDO','7781239', '9252233', 
   sysdate-5010, 1);
insert into talumnos values ('333A', 'Alumno 3', 'C/dire 3', 'Talavera', 45600, 'TOLEDO','9251239', '7122233', 
   sysdate-5200, 1);
   

--Alumnos curso 2
insert into talumnos values ('444B', 'Alumno 4', 'C/dire 4', 'Alcalá', 28802, 'MADRID','9101239', '9122233', 
   sysdate-5100, 2);
insert into talumnos values ('552B', 'Alumno 5', 'C/dire 5', 'Oropesa', 45500, 'TOLEDO','6451239', '9250913', 
   sysdate-5120, 2);
insert into talumnos values ('666C', 'Alumno 6', 'C/dire 6', 'Talavera', 45600, 'TOLEDO','925009988', '6387122', 
   sysdate-5210, 2);
   

---NOTAS alumno1
insert into tnotas values('111A', 1, 4,5,6,7,null );
insert into tnotas values('111A', 2, 6,3,3,4, 6 );
insert into tnotas values('111A', 3, 8,6,3,4, 7);


---NOTAS alumno2
insert into tnotas values('222A', 1, 5,6,3,4,5 );
insert into tnotas values('222A', 2, 6,3,3,5, null );
insert into tnotas values('222A', 3, 4,6,7,5, null);


---NOTAS alumno3
insert into tnotas values('333A', 1, 7,6,6,7,null );
insert into tnotas values('333A', 2, 6,8,9,9, null );
insert into tnotas values('333A', 3, 4,2,4,4, 6);


---NOTAS alumno4
insert into tnotas values('444B', 3, 7,6,6,7,null );
insert into tnotas values('444B', 4, 6,8,9,9, null );
insert into tnotas values('444B', 5, 4,2,4,4, 6);


---NOTAS alumno5
insert into tnotas values('552B', 3, 5,4,4,4,7 );
insert into tnotas values('552B', 4, 6,3,4,5, null );
insert into tnotas values('552B', 5, 6,7,4,6, null);

---NOTAS alumno6
insert into tnotas values('666C', 3, 9,7,10,9,null );
insert into tnotas values('666C', 4, 6,7,4,4, 8 );

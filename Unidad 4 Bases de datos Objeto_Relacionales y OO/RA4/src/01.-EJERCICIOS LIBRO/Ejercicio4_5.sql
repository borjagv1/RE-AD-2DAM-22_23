--oracle
CREATE TABLE departamentos (
 dept_no  number(2) NOT NULL PRIMARY KEY,
 dnombre  VARCHAR2(15),
 loc      VARCHAR2(15)
);

-- ---------------------------------------------------------------------------
-- INSERCIÓN DE FILAS EN DEPARTAMENTOS

INSERT INTO departamentos VALUES (10,'CONTABILIDAD','SEVILLA');
INSERT INTO departamentos VALUES (20,'INVESTIGACIÓN','MADRID');
INSERT INTO departamentos VALUES (30,'VENTAS','BARCELONA');
INSERT INTO departamentos VALUES (40,'PRODUCCIÓN','BILBAO');
COMMIT;
-- ---------------------------------------------------------------------------
-- EMPLEADOS oracle
-- ---------------------------------------------------------------------------
CREATE TABLE empleados (
 emp_no    number(4)  NOT NULL PRIMARY KEY,
 apellido  VARCHAR2(10),
 oficio    VARCHAR2(10),
 dir       number(4),
 fecha_alt DATE      ,
 salario   number(6,2),
 comision  number(6,2),
 dept_no   number(2),
 CONSTRAINT FK_DEP FOREIGN KEY (dept_no ) REFERENCES departamentos(dept_no)

);

ALTER SESSION SET NLS_DATE_FORMAT='DD/MM/YYYY';
 
INSERT INTO empleados VALUES (7369,'SANCHEZ','EMPLEADO',7902,'17/12/1990',
                        1040,NULL,20);
INSERT INTO empleados VALUES (7499,'ARROYO','VENDEDOR',7698,'20/02/1990',
                        1500,390,30);
INSERT INTO empleados VALUES (7521,'SALA','VENDEDOR',7698,'22/02/1991',
                        1625,650,30);
INSERT INTO empleados VALUES (7566,'JIMENEZ','DIRECTOR',7839,'02/04/1991',
                        2900,NULL,20);
INSERT INTO empleados VALUES (7654,'MARTIN','VENDEDOR',7698,'29/09/1991',
                        1600,1020,30);
INSERT INTO empleados VALUES (7698,'NEGRO','DIRECTOR',7839,'01/05/1991',
                        3005,NULL,30);
INSERT INTO empleados VALUES (7782,'CEREZO','DIRECTOR',7839,'09/06/1991',
                        2885,NULL,10);
INSERT INTO empleados VALUES (7788,'GIL','ANALISTA',7566,'09/11/1991',
                        3000,NULL,20);
INSERT INTO empleados VALUES (7839,'REY','PRESIDENTE',NULL,'17/11/1991',
                        4100,NULL,10);
INSERT INTO empleados VALUES (7844,'TOVAR','VENDEDOR',7698,'08/09/1991',
                        1350,0,30);
INSERT INTO empleados VALUES (7876,'ALONSO','EMPLEADO',7788,'23/09/1991',
                        1430,NULL,20);
INSERT INTO empleados VALUES (7900,'JIMENO','EMPLEADO',7698,'03/12/1991',
                        1335,NULL,30);
INSERT INTO empleados VALUES (7902,'FERNANDEZ','ANALISTA',7566,'03/12/1991',
                        3000,NULL,20);
INSERT INTO empleados VALUES (7934,'MUÑOZ','EMPLEADO',7782,'23/01/1992',
                        1690,NULL,10);
COMMIT;

create or replace NONEDITIONABLE TYPE PERSONA AS
    OBJECT (
        CODIGO NUMBER,
        NOMBRE VARCHAR2(20),
        DIREC DIRECCION,
        FECHA DATE,
        MAP MEMBER FUNCTION POR_CODIGO RETURN NUMBER
    );
create or replace NONEDITIONABLE TYPE DIRECCION AS OBJECT
(
  CALLE  VARCHAR2(25),
  CIUDAD VARCHAR2(20),
  CODIGO_POST NUMBER(5),
  MEMBER PROCEDURE SET_CALLE(C VARCHAR2),
  MEMBER FUNCTION GET_CALLE RETURN VARCHAR2  
);
-- Crear un VARRAY DE 5 ELEMENTOS DE TIPO PERSONA
create or replace NONEDITIONABLE TYPE V_PERSONA AS VARRAY(5) OF PERSONA;
-- Crear después la tabla Grupos con dos columnas: nombre varchar2(15) y la segunda de tipo V_PERSONA
create table grupos (nombre varchar2(15), personas V_PERSONA);
-- crear bloque pl sql para insertar en la tabla grupos los datos de los departamentos y empleados
-- NOMBRE DE GRUPO ES EL NOMBRE DEL DEPARTAMENTO
-- NOMBRE DE PERSONA ES EL APELLIDO DEL EMPLEADO
-- CODIGO ES LA COLUMNA EMP_NO
-- CALLE ES LA LOCALIDAD DEL DEPARTAMENTO
-- CADA FILA DE LA TABLA GRUPOS REPRESENTA UN DEPARTAMENTO CON HASTA 5 EMPLEADOS

-- Solución con una única sentencia SQL (no funciona muy bien):
INSERT INTO grupos (nombre, personas)
SELECT d.dnombre, V_PERSONA(
    PERSONA(e.emp_no, e.apellido, DIRECCION(d.loc, d.loc, 0), sysdate),
    PERSONA(e.emp_no, e.apellido, DIRECCION(d.loc, d.loc, 0), sysdate),
    PERSONA(e.emp_no, e.apellido, DIRECCION(d.loc, d.loc, 0), sysdate),
    PERSONA(e.emp_no, e.apellido, DIRECCION(d.loc, d.loc, 0), sysdate),
    PERSONA(e.emp_no, e.apellido, DIRECCION(d.loc, d.loc, 0), sysdate)
) 
FROM (SELECT DISTINCT dept_no FROM empleados) depts
JOIN departamentos d ON depts.dept_no = d.dept_no
JOIN (SELECT DISTINCT dept_no, emp_no, apellido
    FROM empleados) e ON depts.dept_no = e.dept_no;

-- Solución con un bloque PL/SQL (funciona mejor):

DECLARE
  v_grupo VARCHAR2(15);
  v_nombre VARCHAR2(10);
  v_codigo NUMBER(4);
  v_calle VARCHAR2(25);
  v_personas V_PERSONA := V_PERSONA();
  v_count NUMBER(4);
BEGIN
  FOR i IN (SELECT d.dnombre grupo, e.apellido nombre, e.emp_no codigo, d.loc calle
            FROM empleados e JOIN departamentos d ON e.dept_no = d.dept_no)
  LOOP
      IF v_personas.COUNT = 5 THEN
        -- Si ya se alcanzó el límite de 5 elementos, insertar el grupo completo en la tabla GRUPOS
        INSERT INTO grupos (nombre, personas) VALUES (i.grupo, v_personas);
        v_personas := V_PERSONA(); -- Reiniciar el VARRAY para el próximo grupo
      END IF;

      v_personas.EXTEND;
      v_personas(v_personas.LAST) := PERSONA(i.codigo, i.nombre, DIRECCION(i.calle, i.calle, 0), SYSDATE);
      v_grupo := i.grupo;

  END LOOP;

  -- Insertar cualquier grupo incompleto restante en la tabla GRUPOS
  IF v_personas.COUNT > 0 THEN
    INSERT INTO grupos (nombre, personas) VALUES (v_grupo, v_personas);
  END IF;

  COMMIT;

END;
/









    
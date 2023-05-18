--estos ejemplos están en el libro y es hasta la página 211. 
--Aquí cambia lo tipos T_ALUMNO Y PERSONA QUE TENÍAMOS CREADOS, BORRAMOS LOS ANTERIORES Y VOLVEMOS A AÑADIR ESTOS.

CREATE OR REPLACE TYPE PERSONA AS OBJECT
(
  CODIGO NUMBER,
  NOMBRE VARCHAR2(35),
  DIREC DIRECCION,
  FECHA_NAC DATE,
  MAP MEMBER FUNCTION POR_CODIGO RETURN NUMBER
);
/
CREATE OR REPLACE TYPE BODY PERSONA AS
  MAP MEMBER FUNCTION POR_CODIGO RETURN NUMBER IS
  BEGIN
    RETURN CODIGO;
  END;
END;
/
CREATE OR REPLACE TYPE t_alumno AS OBJECT ( /* TODO enter attribute and method declarations here */
    al    persona,
    nota1 NUMBER,
    nota2 NUMBER,
    nota3 NUMBER,
    MEMBER FUNCTION notamedia RETURN NUMBER,
    MEMBER FUNCTION notamaxima RETURN NUMBER
);
/

CREATE OR REPLACE TYPE BODY t_alumno AS
    MEMBER FUNCTION notamedia RETURN NUMBER IS
        media NUMBER;
    BEGIN
        media := ( nota1 + nota2 + nota3 ) / 3;
        RETURN media;
    END;

    MEMBER FUNCTION notamaxima RETURN NUMBER IS
        maxima NUMBER;
    BEGIN
        maxima := greatest(nota1, nota2, nota3);
        RETURN maxima;
    END;

END;
/
-------------comparacion de objetos
DECLARE
  P1 PERSONA := PERSONA(NULL, NULL, NULL, NULL);
  P2 PERSONA := PERSONA(NULL, NULL, NULL, NULL);
BEGIN
  P1.CODIGO := 3;
  P1.NOMBRE := 'JUAN';
  P1.DIREC := DIRECCION('CALLE 1', 'COLONIA 1', '12345');
  P1.FECHA := SYSDATE;
  P2.CODIGO := 1;
  P2.NOMBRE := 'PEDRO';
  P2.DIREC := DIRECCION('CALLE 2', 'COLONIA 2', '54321');
  P2.FECHA := SYSDATE;
  IF P1.POR_CODIGO = P2.POR_CODIGO THEN
    DBMS_OUTPUT.PUT_LINE('SON IGUALES');
  ELSE
    DBMS_OUTPUT.PUT_LINE('SON DIFERENTES');
  END IF;
END;
/
---------------------TABLAS DE OBJETOS -- 
CREATE TABLE ALUMNOS OF PERSONA (
  CODIGO PRIMARY KEY
);
/
--INSERCION DE DATOS
INSERT INTO ALUMNOS VALUES( 
  1, 'Juan Perez ', 
  DIRECCION ('C/Los manantiales 5', 'GUADALAJARA', 19005),
  '18/12/1991'
);

INSERT INTO ALUMNOS (CODIGO, NOMBRE, DIREC, FECHA_NAC) VALUES ( 
  2, 'Julia Bre�a',   
  DIRECCION ('C/Los espartales 25', 'GUADALAJARA', 19004),
  '18/12/1987'
);

--El siguiente bloque PL/SQL inserta una fila en la tabla ALUMNOS:
DECLARE
  DIR DIRECCION := DIRECCION('C/Sevilla 20', 'GUADALAJARA', 19004);
  PER PERSONA := PERSONA(5, 'MANUEL',DIR, '20/10/1987');
BEGIN  
  INSERT INTO ALUMNOS VALUES(PER); --insertar 
  COMMIT; 
END;
/
-------------------

DECLARE
  CURSOR C1 IS SELECT * FROM ALUMNOS;
BEGIN
  FOR I IN C1 LOOP
    DBMS_OUTPUT.PUT_LINE(I.NOMBRE ||
      ' - Calle: '|| I.DIREC.CALLE);
  END LOOP;
END;
/

--mostrar todos los DATAS
DECLARE
    CURSOR C1 IS
        SELECT
            *
        FROM
            ALUMNOS;
BEGIN
    FOR I IN C1 LOOP
        DBMS_OUTPUT.PUT_LINE(I.CODIGO
            || ' '
            || I.NOMBRE
            || ' '
            || I.DIREC.CALLE
            || ' '
            || I.DIREC.CIUDAD
            || ' '
            || I.DIREC.CODIGO_POST
            || ' '
            || I.FECHA);
    END LOOP;
END;
/
--MODIFICAR LA DIRECCION COMPLETA
DECLARE
   D DIRECCION := DIRECCION 
       ('C/Galiano 5','Guadalajara',19004);
BEGIN
    UPDATE ALUMNOS 
       SET direc = D WHERE NOMBRE ='Juan Perez'; 
  COMMIT;
END;
/
--SELECT usando metodo get CALLE
  NOMBRE,
  A.DIREC.GET_CALLE()
FROM
  ALUMNOS A;



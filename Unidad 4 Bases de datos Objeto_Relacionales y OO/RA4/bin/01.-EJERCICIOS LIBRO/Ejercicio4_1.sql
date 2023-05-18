CREATE OR REPLACE TYPE DIRECCION AS OBJECT
(
  CALLE  VARCHAR2(25),
  CIUDAD VARCHAR2(20),
  CODIGO_POST NUMBER(5)
);
/
-- 
-- 
CREATE OR REPLACE TYPE PERSONA AS OBJECT
(
  CODIGO NUMBER,
  NOMBRE VARCHAR2(35),
  DIREC  DIRECCION,
  FECHA_NAC DATE
);
/

CREATE OR REPLACE TYPE t_alumno AS OBJECT ( /* TODO enter attribute and method declarations here */
    al PERSONA,
    notA1 NUMBER,
    nota2 NUMBER,
    nota3 NUMBER
);
/

DECLARE
    dir direccion := direccion(NULL, NULL, NULL);
    per persona := persona(NULL, NULL, NULL, NULL);
    p   t_alumno := t_alumno(NULL, NULL, NULL, NULL); --NO OBLIGATIORIO PORQUE LUEGO SE INICIA

BEGIN
    p := t_alumno(persona(2, 'ana', direccion('dddd', 'ciudad', 88), sysdate), 8, 9, 10);

    dir.calle := 'La Mina, 3';
    dir.ciudad := 'Guadalajara';
    dir.codigo_post := 19001; 
  --
    per.codigo := 1;
    per.nombre := 'JUAN';
    per.direc := dir;
    per.fecha_nac := '10/11/1988';
  
  --DATOS DE T_ALUMNO

    p.al := per;
    p.nota1 := 7;
    p.nota2 := 5;
    p.nota3 := 8;
    dbms_output.put_line('NOMBRE: '
                         || p.al.nombre
                         || ' * CALLE:'
                         || p.al.direc.calle);

    dbms_output.put_line(' * NOTAS:'
                         || p.nota1
                         || ' * NOTAS:'
                         || p.nota2
                         || ' * NOTAS:'
                         || p.nota3);

END;
/
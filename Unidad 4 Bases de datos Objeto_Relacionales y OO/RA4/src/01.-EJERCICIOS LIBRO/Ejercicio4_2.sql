--ejercicio 4.2

CREATE OR REPLACE TYPE persona AS OBJECT (
    codigo    NUMBER,
    nombre    VARCHAR2(35),
    direc     direccion,
    fecha_nac DATE
);
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

DECLARE
    p t_alumno := t_alumno(NULL, NULL, NULL, NULL); --NO OBLIGATIORIO PORQUE LUEGO SE INICIA
BEGIN
    p := t_alumno(persona(2, 'ana', direccion('dddd', 'ciudad', 88), sysdate), 8, 9, 10);
    -- AQUI AÑADIR LOS METODOS Y LSISTO
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

    dbms_output.put_line(p.notamaxima || ' * ' || p.notamedia);
END;
/
--otra manera que yo entiendo mejor.
DECLARE
    p  t_alumno := t_alumno(NULL, NULL, NULL, NULL); --NO OBLIGATIORIO PORQUE LUEGO SE INICIA
    p2 t_alumno := t_alumno(NULL, NULL, NULL, NULL);
    dir direccion := direccion('dddd', 'ciudad', 88);
    pers persona := persona(2, 'ana', dir, sysdate);
BEGIN
    p := t_alumno(persona(2, 'ana', direccion('dddd', 'ciudad', 88), sysdate), 8, 9, 10);

    p2.al := pers;

    p2.nota1 := 8;
    p2.nota2 := 9;
    p2.nota3 := 10;
    -- AQUI AÑADIR LOS METODOS Y LSISTO
    dbms_output.put_line('NOMBRE: '
                         || p2.al.nombre
                         || ' * CALLE:'
                         || p2.al.direc.calle);

    dbms_output.put_line(' * NOTAS:'
                         || p2.nota1
                         || ' * NOTAS:'
                         || p2.nota2
                         || ' * NOTAS:'
                         || p2.nota3);

    dbms_output.put_line(p2.notamaxima
                         || ' * '
                         || p2.notamedia);
END;
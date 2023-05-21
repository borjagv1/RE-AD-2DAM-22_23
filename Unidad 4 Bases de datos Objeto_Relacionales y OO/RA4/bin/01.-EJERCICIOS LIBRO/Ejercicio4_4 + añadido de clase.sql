CREATE OR REPLACE NONEDITIONABLE FUNCTION buscar_telefono (
    n VARCHAR2
) RETURN telefono AS
    tel telefono := telefono();
BEGIN
    BEGIN
        SELECT
            telef
        INTO tel
        FROM
            agenda
        WHERE
            nombre = n;

    EXCEPTION
        WHEN no_data_found THEN
            tel := null;
    END;
-- COMPROBAR SI EL NOMBRE EXISTE
    IF ( tel IS NULL ) THEN
        dbms_output.put_line('EL NOMBRE NO EXISTE EN LA AGENDA');
-- COMPROBAR QUE EL TELEFONO NO ES NULO
    ELSE
        IF ( tel.count = 0 ) THEN
            dbms_output.put_line('EL TELEFONO ES NULO');
        ELSE


-- recorrer los telefonos del nombre dado y mostrar POR PANTALLA DBMS
            dbms_output.put_line('NOMBRE: ' || n);
            FOR i IN 1..tel.count LOOP -- PARA EL 1º SOLO PONER 1..1
                IF ( tel(i) IS NULL ) THEN
                    dbms_output.put_line('EL TELEFONO ES NULO');
                ELSE
                    dbms_output.put_line('TELEFONO: ' || tel(i));
                END IF;
            END LOOP;

        END IF;
    END IF;

    RETURN tel;
END buscar_telefono;
/
-- usar la funcion
DECLARE
    tel telefono;
BEGIN
    tel := buscar_telefono('JUAN');
END;
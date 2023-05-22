-- Ejercicio 4_7
-- crear un procedimiento que reciba un un ID y un objeto DIRECCION, debe insertar en la tabla EJEMPLO_TABLA_ANIDADA direcciones
-- para el ID recibido, si el ID no existe debe lanzar una excepción
-- si la tabla anidada es null, hacer update. Si no es null, hacer insert
-- SI LA DIRECCION YA EXISTE, SE VISUALIZA QUE NO SE PUEDE INSERTAR
create or replace NONEDITIONABLE PROCEDURE insertar_direccion (ident  NUMBER, dir DIRECCION) IS
    --v_direcciones TABLA_ANIDADA;
    v_countDIR       NUMBER;
    v_countID     NUMBER;
BEGIN
  -- Comprobar si el ID existe
    SELECT COUNT(*) INTO v_countID FROM ejemplo_tabla_anidada WHERE id = ident;
    IF v_countID = 0 THEN
        dbms_output.put_line('El ID no existe');
        RETURN;
    END IF;
    SELECT count(calle) into v_countdir from the (select direc from ejemplo_tabla_anidada where id = ident) where calle is not null;

    -- SI V_COUNTDIR ES 0, HACER UPDATE
    IF v_countDIR = 0 THEN
       
       Update EJEMPLO_TABLA_ANIDADA set direc = TABLA_ANIDADA (dir) Where ID = ident ;

    ELSE 
    -- Si la tabla anidada no es null, insertar la nueva dirección con INSERT
        SELECT COUNT(*) INTO v_countDIR from the (select direc from ejemplo_tabla_anidada where id = ident) WHERE
        calle = dir.calle AND ciudad = dir.ciudad AND codigo_post = dir.codigo_post;

        IF v_countDIR > 0 THEN
            dbms_output.put_line('La dirección ya existe');
        ELSE
            INSERT INTO TABLE (SELECT DIREC FROM EJEMPLO_TABLA_ANIDADA WHERE ID = ident) VALUES (dir);

        END IF;
    END IF;
END;


-- PROBAR EL PROCEDIMIENTO
declare
  v_direccion direccion;
begin
  v_direccion := direccion('Calle 1', 'Ciudad 1', 11111);
  insertar_direccion(1, v_direccion);
  v_direccion := direccion('Calle 2', 'Ciudad 2', 22222);
  insertar_direccion(1, v_direccion);
  v_direccion := direccion('Calle 3', 'Ciudad 3', 33333);
  insertar_direccion(1, v_direccion);
  v_direccion := direccion('Calle 4', 'Ciudad 4', 44444);
  insertar_direccion(1, v_direccion);
  v_direccion := direccion('Calle 5', 'Ciudad 5', 55555);
  insertar_direccion(1, v_direccion);
  v_direccion := direccion('Calle 6', 'Ciudad 6', 66666);
  insertar_direccion(1, v_direccion);
end;
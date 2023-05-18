BUSCAR_TELEFONO CREATE
OR REPLACE FUNCTION funcion_actividad_4_4 (nombreagenda IN VARCHAR2) RETURN VARCHAR2 AS CURSOR C1 is
SELECT
    *
FROM
    AGENDA
WHERE
    NOMBRE = nombreagenda;

tel VARCHAR2(80);

sw NUMBER := 0;

BEGIN FOR i IN c1 LOOP sw := 1;

-- CONTROLAR SI EL NOMBRE EXISTE
IF (i.telef IS NULL) THEN tel := 'COLUMNA TELEF ES NULA';

ELSE IF (i.telef.count > 0) THEN tel := i.telef(1);

ELSE tel := 'NO TIENE TELEFONOS';

END IF;

END IF;

END LOOP;

IF tel IS NULL THEN tel := 'EL PRIMER TELEFONO ES NULO';

END IF;

IF sw != 1 THEN tel := 'EL NOMBRE NO EXISTE EN LA AGENDA';

END IF;

TEL := nombreagenda || ' * ' || TEL;

RETURN tel;

END funcion_actividad_4_4;

--
--
-- AÑADIDO DE CLASE:
-- FUNCIÓN QUE RECIBA UN NOMBRE Y DEVUELVA LA COLUMNA DE TELÉFONOS. 
-- REALIZAR UN BLOQUE PL/SQL QUE HAGA USO DE LA FUNCIÓN. SI EL NOMBRE NO EXISTE DEBE DEVOLVER NULL.
CREATE
OR REPLACE FUNCTION function2_actividad_4_ejemploclase (n IN VARCHAR2) RETURN telefono AS CURSOR c1 IS
SELECT
    *
FROM
    agenda
WHERE
    nombre = n;

telefon telefono := NULL;

BEGIN FOR i IN c1 LOOP telefon := i.telef;

END LOOP;

RETURN telefon;

END function2_actividad_4_ejemploclase;
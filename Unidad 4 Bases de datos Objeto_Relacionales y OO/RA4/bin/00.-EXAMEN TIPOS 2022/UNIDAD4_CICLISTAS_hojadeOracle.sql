-- Defino el tipo TIPO_EQUIPO
CREATE OR REPLACE TYPE TIPO_EQUIPO AS OBJECT (
    codigoequipo NUMBER,
    nombreequipo VARCHAR2(50)
);
/
-- Creo la tabla de equipos del tipo TIPO_EQUIPO
CREATE TABLE TEMP_EQUIPOS OF TIPO_EQUIPO(codigoequipo PRIMARY KEY);
/
-- Inserto los datos de la tabla EQUIPOS en la tabla TEMP_EQUIPOS
INSERT INTO TEMP_EQUIPOS (codigoequipo, nombreequipo)
SELECT codigoequipo, nombreequipo FROM EQUIPOS;
/
--de tipo tabla anidada TA_TRAMOS_GANADOS. Se almacenará la información de los tramos de montaña ganados por los ciclistas: codigotramo, nombretramo, km, categoria, pendiente. Los tipos de datos para estos campos coincidirán con los tipos del modelo relacional. Tendrás que crear el tipo TA_TRAMOS_GANADOS y todo lo necesario para poder almacenar esa información.
CREATE OR REPLACE TYPE TIPO_TRAMOS_GANADOS AS OBJECT (
    codigotramo NUMBER,
    nombretramo VARCHAR2(50),
    km NUMBER,
    categoria NUMBER,
    pendiente VARCHAR2(50)
);
/
-- Creo la tabla anidada de tramos ganados del tipo TIPO_TRAMOS_GANADOS
CREATE OR REPLACE TYPE TA_TRAMOS_GANADOS AS TABLE OF TIPO_TRAMOS_GANADOS;
/
--de tipo VARRAY_LLEVA. Se almacenará el número de veces que el ciclista ha llevado camiseta de colores. Se trata de un varray de 4 elementos donde cada elemento almacene el color de la camiseta, el número de veces que el ciclista la ha llevado y el importe de premio de la camiseta, el índice del array coincidirá con el código de la camiseta (valores de 1 a 4). Tendrás que crear los tipos necesarios. Tendrás que crear el tipo VARRAY_LLEVA y todo lo necesario para almacenar la información solicitada.
CREATE OR REPLACE TYPE TIPO_LLEVA AS OBJECT (
    codigocamiseta NUMBER,
    color VARCHAR2(50),
    numveces NUMBER,
    importepremio NUMBER
);
/
-- Creo el tipo VARRAY_LLEVA
CREATE OR REPLACE TYPE VARRAY_LLEVA AS VARRAY(4) OF TIPO_LLEVA;
/
create or replace NONEDITIONABLE TYPE TIPO_CICLISTA AS OBJECT (
    codigociclista NUMBER,
    nombreciclista VARCHAR2(50),
    fechanacimiento DATE,
    equipo REF TIPO_EQUIPO,
    tramosganados TA_TRAMOS_GANADOS,
    tlleva VARRAY_LLEVA,
    --Definir un método de nombre NUMERO_TRAMOS que devuelva el número de tramos ganados por el ciclista.
    MEMBER FUNCTION NUMERO_TRAMOS RETURN NUMBER,
    --Definir un método de nombre NUMERO_CATEGORIAS que reciba una categoría y devuelva el número de tramos de montaña ganados en esa categoría.
    MEMBER FUNCTION NUMERO_CATEGORIAS (CATEGORIA NUMBER) RETURN NUMBER,
    --Definir un método de nombre NUMERO_VECES que reciba un color de camiseta y devuelva el número de veces que el ciclista ha llevado ese color de camiseta.
    MEMBER FUNCTION NUMERO_VECES (COLOR VARCHAR2) RETURN NUMBER,
    --Definir un método de nombre IMPORTE_TOTAL que devuelva el importe total del premio que le corresponde al ciclista por las veces que ha llevado camiseta (número de veces * importe de premio).
    MEMBER FUNCTION IMPORTE_TOTAL RETURN NUMBER
);
/
-- Creo la tabla de ciclistas del tipo TIPO_CICLISTA
CREATE TABLE TABLA_CICLISTAS OF TIPO_CICLISTA(codigociclista PRIMARY KEY) NESTED TABLE tramosganados STORE AS TABLA_TRAMOSGANADOS_ANIDADA;
/
-- llenar TABLA_CICLISTAS con los datos de la tabla CICLISTAS
-- codigociclista INT,
-- nombreciclista VARCHAR2(50),
-- fechanacimiento DATE,
-- equipo REF TIPO_EQUIPO,
-- tramosganados TA_TRAMOS_GANADOS,
-- tlleva VARRAY_LLEVA

INSERT INTO TABLA_CICLISTAS (codigociclista, nombreciclista, fechanacimiento, equipo, tramosganados, tlleva)
SELECT
  c.codigociclista,
  c.nombreciclista,
  c.fechanacimiento,
  (SELECT REF(e) FROM temp_equipos e WHERE e.codigoequipo = c.codigoequipo),
 CAST(
    MULTISET(
      SELECT TIPO_TRAMOS_GANADOS(tp.codigotramo, tp.nombretramo, tp.km, tp.categoria, tp.pendiente)
      FROM TRAMOSPUERTOS tp
      WHERE tp.ciclistaganador = c.codigociclista
    ) AS TA_TRAMOS_GANADOS
  ),
    VARRAY_LLEVA(
        TIPO_LLEVA(1, 
            (SELECT color FROM CAMISETAS WHERE codigocamiseta = 1),
            (SELECT COUNT(*) FROM LLEVA l WHERE l.codigociclista = c.codigociclista AND l.codigocamiseta = 1),
            (SELECT importepremio FROM CAMISETAS WHERE codigocamiseta = 1)
        ),
        TIPO_LLEVA(2, 
            (SELECT color FROM CAMISETAS WHERE codigocamiseta = 2),
            (SELECT COUNT(*) FROM LLEVA l WHERE l.codigociclista = c.codigociclista AND l.codigocamiseta = 2),
            (SELECT importepremio FROM CAMISETAS WHERE codigocamiseta = 2)
        ),
        TIPO_LLEVA(3, 
            (SELECT color FROM CAMISETAS WHERE codigocamiseta = 3),
            (SELECT COUNT(*) FROM LLEVA l WHERE l.codigociclista = c.codigociclista AND l.codigocamiseta = 3),
            (SELECT importepremio FROM CAMISETAS WHERE codigocamiseta = 3)
        ),
        TIPO_LLEVA(4, 
            (SELECT color FROM CAMISETAS WHERE codigocamiseta = 4),
            (SELECT COUNT(*) FROM LLEVA l WHERE l.codigociclista = c.codigociclista AND l.codigocamiseta = 4),
            (SELECT importepremio FROM CAMISETAS WHERE codigocamiseta = 4)
        )
    )
FROM
    CICLISTAS c;
/
-- crear body de TIPO_CICLISTA
create or replace NONEDITIONABLE TYPE BODY TIPO_CICLISTA AS
    -- Definir un método de nombre NUMERO_TRAMOS que devuelva el número de tramos ganados por el ciclista.
    MEMBER FUNCTION NUMERO_TRAMOS RETURN NUMBER IS
        v_num_tramos NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_num_tramos FROM TABLE(tramosganados);
        RETURN v_num_tramos;
    END;
    -- Definir un método de nombre NUMERO_CATEGORIAS que reciba una categoría y devuelva el número de tramos de montaña ganados en esa categoría
    MEMBER FUNCTION NUMERO_CATEGORIAS (CATEGORIA NUMBER) RETURN NUMBER IS
        v_num_categorias NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_num_categorias FROM TABLE(tramosganados) WHERE categoria = CATEGORIA;
        RETURN v_num_categorias;
    END;

    -- Definir un método de nombre NUMERO_VECES que reciba un color de camiseta y devuelva el número de veces que el ciclista ha llevado ese color de camiseta.
    MEMBER FUNCTION NUMERO_VECES (COLOR VARCHAR2) RETURN NUMBER IS
    v_num_veces NUMBER;
    BEGIN
        FOR i IN 1..tlleva.COUNT LOOP
            IF tlleva(i).color = COLOR THEN
                v_num_veces := tlleva(i).numveces;
                EXIT;
            END IF;
        END LOOP;  
    RETURN v_num_veces;
    END;

    -- Definir un método de nombre IMPORTE_TOTAL que devuelva el importe total del premio que le corresponde al ciclista por las veces que ha llevado camiseta (número de veces * importe de premio).
    MEMBER FUNCTION IMPORTE_TOTAL RETURN NUMBER IS
        v_importe_total NUMBER;
    BEGIN
        SELECT SUM(numveces * importepremio) INTO v_importe_total FROM TABLE(tlleva);
        RETURN v_importe_total;
    END;
END;
/
-- Probamos los métodos
SELECT
    CICLISTAS.codigociclista,
    CICLISTAS.nombreciclista,
    CICLISTAS.numero_tramos() AS tramosganados,
    CICLISTAS.numero_categorias(1) AS CATEGORIASGANADAS,
    CICLISTAS.NUMERO_VECES('Rojo')AS VECESLLEVADA,
    CICLISTAS.IMPORTE_TOTAL() AS IMPORTETOTAL
FROM
    tabla_ciclistas ciclistas;
/
-- CONSULTAS
SELECT
    ciclistas.codigociclista,
    ciclistas.nombreciclista,
    ciclistas.numero_tramos()         AS tramosganados,
    ciclistas.numero_categorias(1)    AS categoriasganadas,
    ciclistas.numero_veces('Rojo')    AS vecesllevada,
    ciclistas.numero_veces('Verde')   AS vecesllevada,
    ciclistas.numero_veces('Lunares') AS vecesllevada,
    ciclistas.numero_veces('Blanco')  AS vecesllevada,
    ciclistas.importe_total()         AS importetotal
FROM
    tabla_ciclistas         ciclistas,
    etapas                  e,
    TABLE ( tramosganados ) tg
WHERE
-- condicion de tramos ganados de montaña
        ciclistas.numero_tramos() > 0
    AND tg.categoria = e.codigoetapa
    AND e.tipoetapa = 'Llana'
GROUP BY
    ciclistas.codigociclista,
    ciclistas.nombreciclista,
    ciclistas.numero_tramos(),
    ciclistas.numero_categorias(1),
    ciclistas.numero_veces('Rojo'),
    ciclistas.numero_veces('Verde'),
    ciclistas.numero_veces('Lunares'),
    ciclistas.numero_veces('Blanco'),
    ciclistas.importe_total();
    --
/
SELECT
    ciclistas.codigociclista,
    ciclistas.nombreciclista,
    ciclistas.numero_categorias(1) AS categoriasganadas,
    tg.nombretramo,
    tg.pendiente
FROM
    etapas                  e,
    tabla_ciclistas         ciclistas,
    TABLE ( tramosganados ) tg
WHERE
    ciclistas.numero_categorias(1) = (
        SELECT
            MAX(ciclistas.numero_categorias(1))
        FROM
            tabla_ciclistas ciclistas
    )
    AND tg.categoria = 1 AND e.tipoetapa = 'Llana'
GROUP BY
    ciclistas.codigociclista,
    ciclistas.nombreciclista,
    ciclistas.numero_categorias(1),
    tg.nombretramo,
    tg.pendiente;
    
--
/
SELECT
  E.codigoequipo,
  E.nombreequipo,
  TO_CHAR(SUM(CICLISTAS.IMPORTE_TOTAL()), 'FM999G999D99', 'NLS_NUMERIC_CHARACTERS = '',.''') AS importe_total
FROM
  TEMP_EQUIPOS E
  JOIN TABLA_CICLISTAS CICLISTAS ON E.codigoequipo = ciclistas.equipo.codigoequipo
GROUP BY
  E.codigoequipo,
  E.nombreequipo
ORDER BY
  E.codigoequipo;


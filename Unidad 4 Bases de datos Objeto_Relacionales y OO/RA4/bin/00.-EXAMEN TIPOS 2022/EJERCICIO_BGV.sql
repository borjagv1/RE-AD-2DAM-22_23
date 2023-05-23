-- • EQUIPOS( codigoequipo , nombreequipo, director, pais): contiene los datos de los distintos equipos: nombre de equipo (PK), el nombre, el país y el nombre de su director.
-- • CICLISTAS(codigociclista, nombreciclista, fechanacimiento, peso, Codigoequipo, jefeEquipo): contiene los datos de los ciclistas que componen los distintos equipos. Los datos son: codigociclista (PK), nombre del ciclista, fecha de nacimiento, peso, código de equipo al que pertenece (FK) y jefe del equipo que es otro ciclista de su equipo (FK) y es el mismo para todo el equipo.
-- • ETAPAS(codigoetapa, tipoetapa, fechasalida , pobsalida, pobllegada, km, ciclistaganador): contiene los datos de las etapas que componen la vuelta ciclista: código de etapa( nº de 1 a 21) que es la PK, tipo de etapa (llana o montaña), nombre de la población de donde sale la etapa (pobsalida), nombre de la población donde está la meta de la etapa (pobllegada), kilómetros que tiene la etapa (km), y código del ciclista ganador de la etapa (FK).
-- • CAMISETAS( codigocamiseta , tipo, color, importepremio): contiene los datos de los premios que se otorgan mediante las distintas camisetas: código de la camiseta (PK), tipo de camiseta (líder de la General, líder de la Clasificación por puntos, mejor Joven, Rey de la Montaña) , color (Rojo, Verde, Blanco y Lunares) y el importe del premio que correspondería al ciclista que lleve esa camiseta en la vuelta.
-- • TRAMOSPUERTOS(codigotramo, nombretramo,km, categoria, pendiente, numetapa, codigociclistaganador ): contiene los datos de los tramos de montaña que visita la vuelta ciclista: código del tramo (PK), nombre del tramo, km en el que se encuentra el puerto, categoría (1, 2, 3, 4 y 10 para categoría especial), la pendiente media del tramo, el número de la etapa donde se sube el tramo (FK) y el código del ciclista que ha ganado el tramo al pasar en primera posición (FK).
-- • LLEVA(numetapa, codigocamiseta, codigociclista): contiene la información sobre qué ciclistas (codigociclista) han llevado cada camiseta (codigocamiseta) en cada una de las etapas (numetapa). La PK es numetapa + codigocamiseta. Las otras columnas son FK.

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
-- codigo, nombre y fecha
  c.codigociclista,
  c.nombreciclista,
  c.fechanacimiento,
-- equipo REF TIPO_EQUIPO
  (SELECT REF(e) FROM temp_equipos e WHERE e.codigoequipo = c.codigoequipo),
-- tramosganados TA_TRAMOS_GANADOS TABLA ANIDADA
 CAST(
    MULTISET(
      SELECT TIPO_TRAMOS_GANADOS(tp.codigotramo, tp.nombretramo, tp.km, tp.categoria, tp.pendiente)
      FROM TRAMOSPUERTOS tp
      WHERE tp.ciclistaganador = c.codigociclista
    ) AS TA_TRAMOS_GANADOS
  ),
-- tlleva VARRAY_LLEVA
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
-- CONSULTAS:
-- 1. Realiza una consulta que muestre el código de ciclista, nombre, nº de veces que ha llevado la camiseta de color Rojo, nº de veces que ha llevado la camiseta de color Verde, nº de veces que ha llevado la camiseta de color Lunares, nº de veces que ha llevado la camiseta de color Blanco, nº de tramos de montaña ganados y el importe total del premio que le corresponde. Los ciclistas que no han ganado tramos no se mostrarán. (Una fila por ciclista).
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
-- condicion de tramos ganados de montaña no sale por que no coge los caracteres de la ñ la BBDD pero pongo llana y funciona
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
--2. Realiza una consulta que muestre el código de ciclista, nombre de ciclista, número de veces que ha ganado tramos de montaña de Categoría 1, nombre del tramo de montaña y pendiente de los tramos de montaña de Categoría 1 para el ciclista que más tramos de montaña ha ganado. (Se mostrarán filas con el mismo código, nombre de ciclista y número de veces).
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

-- 3. Realiza una consulta que muestre por cada equipo el código, el nombre del equipo y el importe de premio total que le ha correspondido (una fila por equipo), dando formato al importe total con el punto de los miles y la coma de los decimales, ordenado por código de equipo.
SELECT
  E.codigoequipo,
  E.nombreequipo,
  -- los numeros raros son para dar formato (12.500,84) pero vamos que da igual
  TO_CHAR(SUM(CICLISTAS.IMPORTE_TOTAL()), 'FM999G999D99', 'NLS_NUMERIC_CHARACTERS = '',.''') AS importe_total
FROM
  TEMP_EQUIPOS E
  JOIN TABLA_CICLISTAS CICLISTAS ON E.codigoequipo = ciclistas.equipo.codigoequipo
GROUP BY
  E.codigoequipo,
  E.nombreequipo
ORDER BY
  E.codigoequipo;








-- RECU RA4 BORJA GUERRA VALENCIA
-- APARTADO 1: creo los tipos
CREATE OR REPLACE TYPE tipo_alergenos AS OBJECT (
  idproducto NUMBER(24,0),
  name VARCHAR2(100 CHAR)
);
/
CREATE OR REPLACE TYPE tipo_alergenos_varray AS VARRAY(10) OF tipo_alergenos;
/
CREATE OR REPLACE TYPE tipo_categoria AS OBJECT (
  id NUMBER(24,0),
  name VARCHAR2(100 CHAR)
);
/
CREATE TABLE temp_categoria OF tipo_categoria (id PRIMARY KEY);
/
-- creo esta tabla para la columna que hace ref a tipo_categorias 
INSERT INTO temp_categoria SELECT id, name FROM categories;
/

CREATE OR REPLACE TYPE tipo_producto AS OBJECT (
  id NUMBER(24,0),
  name VARCHAR2(100 CHAR),
  price FLOAT,
  alergenos tipo_alergenos_varray,
  categoria_id REF tipo_categoria
);
/
-- APARTADO 2: CREO LA TABLA E INSERTO DATOS 
CREATE TABLE productos_tipo OF tipo_producto (id PRIMARY KEY);
/

-- ESTA CREACIÓN NO ME SIRVE HAGO PL/SQL
-- INSERTAR EN TABLA PRODUCTOS_TIPO LOS ALERGENOS DE CADA PRODUCTO
--INSERT INTO productos_tipo SELECT
--  p.id,
--  p.name,
--  p.price,
--  CAST(
--    MULTISET(
--      SELECT TIPO_ALERGENOS(ap.allergen_id, a.name)
--      FROM allergens_products ap, allergens a
--      WHERE ap.product_id = p.id AND ap.allergen_id = a.id
--    ) AS tipo_alergenos_varray
--  ),
--  (SELECT REF(c) FROM temp_categoria c WHERE c.id = p.category_id)
--FROM products p;
-----------------------------------
-- BLOQUE PL/SQL
-- HAGO CONSULTAS ANIDADAS PARA IR LLENANDO DENTRO DEL MISMO BLOQUE CADA COLUMNA, ASÍ INCLUYO LOS VARRAYS
BEGIN
  FOR rec IN (
    SELECT
      p.id,
      p.name,
      p.price,
      CAST(     -- (visto en ejercicio CICLISTAS DE ESTE AÑO) convierto los objetos recogidos en el listado set al tipo que me interesa que es el varray de tipo alergenos creado mas arriba
        MULTISET( -- meto los objetos de la consulta en un listado como si fuera en java un SET. (visto en ejercicio CICLISTAS DE ESTE AÑO)
          SELECT TIPO_ALERGENOS(ap.allergen_id, a.name)
          FROM allergens_products ap, allergens a
          WHERE ap.product_id = p.id AND ap.allergen_id = a.id order by ap.allergen_id
        ) AS tipo_alergenos_varray
      ) AS tipo_alergenos,
      (SELECT REF(c) FROM temp_categoria c WHERE c.id = p.category_id) AS categoria_ref
    FROM products p order by p.id desc
  ) LOOP
    INSERT INTO productos_tipo (id, name, price, alergenos, categoria_id)
    VALUES (rec.id, rec.name, rec.price, rec.tipo_alergenos, rec.categoria_ref);
  END LOOP; 
  COMMIT;
END;
/
--
--APARTADO 3: PROCEDIMIENTO PARA VER LOS DATOS  DEL PRODUCTO DE LA TABLA QUE HE CREADO PRODUCTOS_TIPO
CREATE OR REPLACE PROCEDURE verDatos (idproducto IN NUMBER) AS
  v_producto_id NUMBER;
  V_producto_name VARCHAR2(100);
  v_categoria_id NUMBER;
  v_categoria_name VARCHAR2(100);
  v_alergenos tipo_alergenos_varray;
BEGIN
  SELECT id, name INTO v_producto_id, v_producto_name FROM productos_tipo p WHERE p.id = idproducto;
  SELECT DEREF(p.categoria_id).id, DEREF(p.categoria_id).name into v_categoria_id, v_categoria_name FROM productos_tipo p WHERE p.id = idproducto;
  SELECT alergenos INTO v_alergenos FROM productos_tipo p WHERE p.id = idproducto;
  DBMS_OUTPUT.PUT_LINE('=============================================================================');
  DBMS_OUTPUT.PUT_LINE('ID: ' || v_producto_id || ', NOMBRE: ' || v_producto_name);  
  
  DBMS_OUTPUT.PUT_LINE('CATEGORIA: ' || v_categoria_id || ', ' || v_categoria_name);
  
  DBMS_OUTPUT.PUT_LINE('NUMERO DE ALÉRGENOS: ' || v_alergenos.count);
  DBMS_OUTPUT.PUT_LINE('--------------------------------------');
  FOR i IN 1..v_alergenos.COUNT LOOP
    DBMS_OUTPUT.PUT_LINE('  ' || v_alergenos(i).idproducto || ' * ' || v_alergenos(i).name);
  END LOOP;  
END;
/
-- y la prueba del procedimiento:
BEGIN
 VERDATOS(3);
 VERDATOS(5);
 verdatos(36);
 END;
/
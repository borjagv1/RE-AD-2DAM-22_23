
CREATE TABLE CLIENTES (
  IDCLIENTE NUMBER ,
  NOMBRE     VARCHAR2(50),
  DIRECCION  VARCHAR2(50),
  POBLACION  VARCHAR2(50),
  CODPOSTAL  NUMBER(5),
  PROVINCIA  VARCHAR2(40),
  NIF        VARCHAR2(9) UNIQUE,
  TELEFONO1  VARCHAR2(15),
  TELEFONO2  VARCHAR2(15),
  TELEFONO3  VARCHAR2(15),
  constraint PK_CLI PRIMARY KEY(IDCLIENTE)
);
CREATE TABLE PRODUCTOS (
  IDPRODUCTO  NUMBER ,
  DESCRIPCION varchar2(80),
  PVP   NUMBER,
  STOCKACTUAL NUMBER,
  constraint PK_PRO PRIMARY KEY(IDPRODUCTO)
  );	
  
  CREATE TABLE VENTAS(
  IDVENTA     NUMBER,
  IDCLIENTE   NUMBER NOT NULL,
  FECHAVENTA  DATE  ,
  constraint PK_VEN PRIMARY KEY(IDVENTA),
  CONSTRAINT FK_VEN FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTES
);

CREATE TABLE LINEASVENTAS (  
  IDVENTA      NUMBER,
  NUMEROLINEA  NUMBER,
  IDPRODUCTO   NUMBER,
  CANTIDAD     NUMBER, 
  CONSTRAINT FK_LV1 FOREIGN KEY (IDVENTA) REFERENCES VENTAS (IDVENTA),
  CONSTRAINT FK_LV2 FOREIGN KEY (IDPRODUCTO) REFERENCES PRODUCTOS(IDPRODUCTO),  
  constraint PK_LV PRIMARY KEY (IDVENTA,NUMEROLINEA)
);
---------------------------------
CREATE TYPE TIP_TELEFONOS AS VARRAY(3) 
    OF VARCHAR2(15);
/
CREATE TYPE TIP_DIRECCION AS OBJECT(
  CALLE      VARCHAR2(50),
  POBLACION  VARCHAR2(50),
  CODPOSTAL  NUMBER(5),
  PROVINCIA  VARCHAR2(40)
);
/
CREATE TYPE TIP_CLIENTE AS OBJECT(
  IDCLIENTE  NUMBER,
  NOMBRE     VARCHAR2(50),
  DIREC      TIP_DIRECCION,
  NIF        VARCHAR2(9),
  TELEF      TIP_TELEFONOS
);
/	
CREATE TYPE TIP_PRODUCTO AS OBJECT (
  IDPRODUCTO   NUMBER,
  DESCRIPCION  VARCHAR2(80),
  PVP          NUMBER,
  STOCKACTUAL  NUMBER
);
/
CREATE TYPE  TIP_LINEAVENTA AS OBJECT (
  NUMEROLINEA  NUMBER,
  IDPRODUCTO   REF TIP_PRODUCTO,
  CANTIDAD     NUMBER
);
/
SHOW ERRORS;
--VARRAY DE 20 ELEMENTOS

CREATE TYPE TIP_LINEAS_VENTA AS VARRAY(20) OF TIP_LINEAVENTA;
/



--tipo venta
CREATE or replace TYPE  TIP_VENTA AS OBJECT (
  IDVENTA     NUMBER,   
  IDCLIENTE   REF TIP_CLIENTE,
  FECHAVENTA  DATE,   
  LINEAS      TIP_LINEAS_VENTA,--VARRAY
  MEMBER FUNCTION TOTAL_VENTA RETURN NUMBER
);
/
CREATE OR REPLACE TYPE BODY TIP_VENTA AS
  MEMBER FUNCTION TOTAL_VENTA RETURN NUMBER IS
   TOTAL NUMBER:=0;
   LINEA   TIP_LINEAVENTA;
   PRODUCT TIP_PRODUCTO;
  BEGIN
    FOR I IN 1..LINEAS.COUNT LOOP
      LINEA:=LINEAS(I);
      SELECT DEREF(LINEA.IDPRODUCTO) INTO PRODUCT  FROM DUAL;
      --PRODUCT:=DEREF(LINEA.IDPRODUCTO); (error si se ejecuta asi)
      TOTAL:=TOTAL + LINEA.CANTIDAD * PRODUCT.PVP;
    END LOOP;
    RETURN TOTAL;
  END;
END;
/
SHOW ERRORS;

--CREAMOS TABLAS
CREATE TABLE TABLA_CLIENTES OF TIP_CLIENTE (
  IDCLIENTE PRIMARY KEY,
  NIF UNIQUE
);
/
CREATE TABLE TABLA_PRODUCTOS OF TIP_PRODUCTO (
  IDPRODUCTO PRIMARY KEY
);
/
CREATE TABLE TABLA_VENTAS OF TIP_VENTA (
  IDVENTA PRIMARY KEY
);
/

DELETE TABLA_VENTAS;
DELETE TABLA_PRODUCTOS;
DELETE TABLA_CLIENTES;
COMMIT;

--insercion de datos
INSERT INTO TABLA_CLIENTES VALUES 
(1, 'Luis Gracia',
 TIP_DIRECCION ('C/Las Flores 23', 'Guadalajara', '19003', 'Guadalajara'), 
 '34343434L', 
 TIP_TELEFONOS( '949876655', '949876655')
);

INSERT INTO TABLA_CLIENTES VALUES 
(2, 'Ana Serrano',
 TIP_DIRECCION ('C/Galiana 6', 'Guadalajara', 
         '19004', 'Guadalajara'), 
  '76767667F', 
  TIP_TELEFONOS('94980009')
);

INSERT INTO TABLA_PRODUCTOS VALUES (1, 'CAJA DE CRISTAL DE MURANO', 100, 5);
INSERT INTO TABLA_PRODUCTOS VALUES (2, 'BICICLETA CITY', 120, 15);
INSERT INTO TABLA_PRODUCTOS VALUES (3, '100 LÁPICES DE COLORES', 20, 5);
INSERT INTO TABLA_PRODUCTOS VALUES (4, 'OPERACIONES CON BD', 25, 5);
INSERT INTO TABLA_PRODUCTOS VALUES (5, 'APLICACIONES WEB', 25.50, 10);

COMMIT;
--inserta una venta con id 1 para el cliente 1


INSERT INTO TABLA_VENTAS 
SELECT 1, REF(C), SYSDATE, TIP_LINEAS_VENTA()
FROM TABLA_CLIENTES C WHERE C.IDCLIENTE=1;

--Inserto en TABLA_VENTAS la venta con IDVENTA 2 
--para el IDCLIENTE 1:
INSERT INTO TABLA_VENTAS 
SELECT 2, REF(C), SYSDATE, TIP_LINEAS_VENTA()
FROM TABLA_CLIENTES C WHERE C.IDCLIENTE=1;

--INSERTO LINEAS - para EL IDVENTA 1
DECLARE
  Miarray tip_lineas_venta := tip_lineas_venta();  
  PRO1 ref tip_producto;
  Pro2 ref tip_producto;
  L2 Tip_Lineaventa;
  L1 tip_lineaventa;
Begin
  Select Ref(P) Into Pro1 From Tabla_Productos P Where P.Idproducto=1;
  Select Ref(P) Into Pro2 From Tabla_Productos P Where P.Idproducto=2;
  L1 := Tip_Lineaventa (1, Pro1, 1); --linea 1
  L2 := tip_lineaventa (2, Pro2, 2); --linea2
  Miarray.Extend;
  Miarray(Miarray.Count) := L1;
  Miarray.Extend;
  Miarray(Miarray.Count) := L2;
  UPDATE tabla_ventas SET lineas = miarray WHERE idventa =1;
End;
/


--INSERTO LINEAS - para EL IDVENTA 2

--Insertamos en TABLA_VENTAS tres líneas de venta para el IDVENTA  2 para los --productos 1 (la CANTIDAD es 2), 4 (la CANTIDAD es 1) y 5 (la CANTIDAD es 4)




--
SELECT * FROM TABLA_VENTAS;
--
--CONSULTAS
SELECT  IDVENTA, DEREF(IDCLIENTE).NOMBRE NOMBRE,
DEREF(IDCLIENTE).IDCLIENTE IDCLIENTE, T.TOTAL_VENTA() TOTAL
FROM TABLA_VENTAS T;


--
CREATE OR REPLACE
PROCEDURE VER_VENTA( ID NUMBER) AS
  IMPORTE NUMBER;
  TOTAL_V NUMBER;
  CLI TIP_CLIENTE := TIP_CLIENTE(NULL, NULL, NULL, NULL, NULL);
  Fec DATE;
  PROD tip_producto;
  Lin Tip_Lineaventa;
  Miarray tip_lineas_venta := tip_lineas_venta();
BEGIN
  --obtener datos de la venta
  Select Deref(Idcliente),Fechaventa, V.Total_Venta(), V.Lineas
  INTO CLI, FEC, TOTAL_V, Miarray
  From Tabla_Ventas V  Where Idventa = Id;
  
  DBMS_OUTPUT.PUT_LINE('NÚMERO DE VENTA: ' || ID || ' * Fecha de venta: ' || FEC);
  DBMS_OUTPUT.PUT_LINE('CLIENTE: ' || CLI.NOMBRE);
  DBMS_OUTPUT.PUT_LINE('DIRECCION: '|| CLI.DIREC.CALLE);
  Dbms_Output.Put_Line('============================================');
  
  --Recorrer array de lineas de venta
  FOR I IN 1..Miarray.Count
  LOOP
    Lin :=Miarray(I);
    SELECT deref(lin.idproducto) INTO prod FROM dual;
    Importe:= Lin.Cantidad * Prod.Pvp;
    Dbms_Output.Put_Line(Lin.Numerolinea|| '*' || Prod.Descripcion || '*' || PROD.PVP || '*' || LIN.CANTIDAD || '*' ||IMPORTE);
  End Loop;
  
  DBMS_OUTPUT.PUT_LINE('Total Venta: ' || TOTAL_V);
Exception

WHEN NO_DATA_FOUND THEN
  DBMS_OUTPUT.PUT_LINE('NO EXISTE EL IDVENTA: ' ||ID);
END VER_VENTA;
/

BEGIN
  --VER_VENTA(20);
  VER_VENTA(2);  
 -- VER_VENTA(1);
  
END;
/

-------------------FIN EJEMPLO-----------------------------
==========================================================================
ACTIVIDAD 4.9
Crea una función almacenada que reciba un identificador de venta y retorne el total de la venta. Comprobar si la venta existe, si no existe devolver -1. Realiza un bloque PL/SQL anónimo que haga uso de la función.
Realiza un procedimiento almacenado que reciba un identificador de producto y muestre: el identificador del producto, la descripción y la suma de las unidades vendidas. Si el producto no se encuentra debe mostrar un mensaje indicándolo.
==========================================================================




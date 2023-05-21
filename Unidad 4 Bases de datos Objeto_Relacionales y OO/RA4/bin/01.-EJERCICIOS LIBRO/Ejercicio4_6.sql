select ciudad, COUNT(*) numerodirecciones from table(
select direc from ejemplo_tabla_anidada where id = 1)
group by ciudad
/
select ciudad, COUNT(*) CIUDAD_MAS_DIRECCIONES FROM TABLE(
select direc from ejemplo_tabla_anidada where id = 1)
GROUP BY CIUDAD
ORDER BY 1 DESC
FETCH FIRST 2 ROW ONLY
/
SELECT CIUDAD, COUNT(*) AS NUMERO_DIRECCIONES
FROM TABLE((SELECT DIREC FROM EJEMPLO_TABLA_ANIDADA WHERE ID = 1))
GROUP BY CIUDAD
HAVING COUNT(*) = (SELECT MAX(COUNT(*))
                   FROM TABLE((SELECT DIREC FROM EJEMPLO_TABLA_ANIDADA WHERE ID = 1))
                   GROUP BY CIUDAD)
/
DECLARE
  CURSOR C1 IS SELECT ID,APELLIDOS,D.* FROM ejemplo_tabla_anidada,TABLE(DIREC) D ORDER BY 1;
  APE VARCHAR2(35):= ' ';
BEGIN
  FOR I IN C1 LOOP
        IF APE<> I.APELLIDOS THEN
             DBMS_OUTPUT.PUT_LINE(I.APELLIDOS);
             APE:=I.APELLIDOS;
             END IF;
         DBMS_OUTPUT.PUT_LINE(I.CALLE);
  END LOOP;
END;
/
                   
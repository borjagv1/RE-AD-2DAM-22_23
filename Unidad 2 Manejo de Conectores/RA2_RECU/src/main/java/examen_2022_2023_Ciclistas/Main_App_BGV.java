package examen_2022_2023_Ciclistas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Main_App_BGV {
    static Connection conexion = null;
    static PreparedStatement sentencia = null;
    static ResultSet rs, rs2, rs3 = null;
    static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    static String usuario = "CICLISTAS";
    static String password = "CICLISTAS";
    /*
     * • EQUIPOS( codigoequipo , nombreequipo, director, pais): contiene los datos
     * de los distintos equipos: nombre de equipo (PK), el nombre, el país y el
     * nombre de su director.
     * • CICLISTAS(codigociclista, nombreciclista, fechanacimiento, peso,
     * Codigoequipo, jefeEquipo): contiene los datos de los ciclistas que componen
     * los distintos equipos. Los datos son: codigociclista (PK), nombre del
     * ciclista, fecha de nacimiento, peso, código de equipo al que pertenece (FK) y
     * jefe del equipo que es otro ciclista de su equipo (FK) y es el mismo para
     * todo el equipo.
     * • ETAPAS(codigoetapa, tipoetapa, fechasalida , pobsalida, pobllegada, km,
     * ciclistaganador): contiene los datos de las etapas que componen la vuelta
     * ciclista: código de etapa( nº de 1 a 21) que es la PK, tipo de etapa (llana o
     * montaña), nombre de la población de donde sale la etapa (pobsalida), nombre
     * de la población donde está la meta de la etapa (pobllegada), kilómetros que
     * tiene la etapa (km), y código del ciclista ganador de la etapa (FK).
     * • TRAMOSPUERTOS(codigotramo, nombretramo,km, categoria, pendiente, numetapa,
     * codigociclistaganador ): contiene los datos de los tramos de montaña que
     * visita la vuelta ciclista: código del tramo (PK), nombre del tramo, km en el
     * que se encuentra el puerto, categoría (1, 2, 3, 4 y 10 para categoría
     * especial), la pendiente media del tramo, el número de la etapa donde se sube
     * el tramo (FK) y el código del ciclista que ha ganado el tramo al pasar en
     * primera posición (FK).
     * • CAMISETAS( codigocamiseta , tipo, color, importepremio): contiene los datos
     * de los premios que se otorgan mediante las distintas camisetas: código de la
     * camiseta (PK), tipo de camiseta (líder de la General, líder de la
     * Clasificación por puntos, mejor Joven, Rey de la Montaña) , color (Rojo,
     * Verde, Blanco y Lunares) y el importe del premio que correspondería al
     * ciclista que lleve esa camiseta en la vuelta.
     * • LLEVA(numetapa, codigocamiseta, codigociclista): contiene la información
     * sobre qué ciclistas (codigociclista) han llevado cada camiseta
     * (codigocamiseta) en cada una de las etapas (numetapa). La PK es numetapa +
     * codigocamiseta. Las otras columnas son FK.
     */

    public static void main(String[] args) {
        // Cargar el driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // conectamos con la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);

            // Ejercicio1(1);
            // Ejercicio2(1);
            // Ejercicio2(99);
            // Ejercicio2(90);
            // Ejercicio2(8);
            Ejercicio3();

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // por si no controlamos cualquier excepción aquí se coge todo
            e.printStackTrace();
        } finally {
            // Cerrar conexión y recursos
            try {
                if (rs != null)
                    rs.close();
                // System.out.println("Primer resultSet cerrado?: " + rs.isClosed());
                if (rs2 != null)
                    rs2.close();
                // System.out.println("Segundo resultSet cerrado?: " + rs2.isClosed());
                if (sentencia != null)
                    sentencia.close();
                //System.out.println("Sentencia cerrada?: " + sentencia.isClosed());
                if (conexion != null)
                    conexion.close();
                System.out.println("Conexion cerrada?: " + conexion.isClosed());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }// fin main

    private static void Ejercicio3() {
        // crear columnas en tabla ciclistas:
        // añada las siguientes columnas de tipo number(3) que no pueden tener valores nulos, en la tabla CICLISTAS:
        //✓ Etapasganadas: para que contenga el número de etapas que ha ganado.
        //✓ Etapascamiseta: para que contenga el número de etapas que ha llevado camiseta.
        //✓ Tramosganados: para que contenga el número de tramos que ha ganado.
        try{
        String sql = "ALTER TABLE CICLISTAS ADD (Etapasganadas NUMBER(3) DEFAULT 0 NOT NULL, Etapascamiseta NUMBER(3) DEFAULT 0 NOT NULL, Tramosganados NUMBER(3) DEFAULT 0 NOT NULL)";
 
        //Creamos la sentencia
        sentencia = conexion.prepareStatement(sql);
        //Ejecutamos la sentencia
        sentencia.executeUpdate();

        // Creo nueva consulta para actualizar las columnas
        // Actualizo columna etapasganadas
     sql = "\r\n"
                + "UPDATE ciclistas c\r\n"
                + "SET\r\n"
                + "    etapasganadas = (\r\n"
                + "        SELECT\r\n"
                + "            coalesce(COUNT(*), 0)\r\n"
                + "        FROM\r\n"
                + "            etapas e\r\n"
                + "        WHERE\r\n"
                + "            c.codigociclista = e.ciclistaganador\r\n"
                + "    )\r\n"
                + "WHERE\r\n"
                + "    EXISTS (\r\n"
                + "        SELECT\r\n"
                + "            1\r\n"
                + "        FROM\r\n"
                + "            etapas e\r\n"
                + "        WHERE\r\n"
                + "            e.ciclistaganador = c.codigociclista\r\n"
             + "    )";
     //Creamos la sentencia
     sentencia = conexion.prepareStatement(sql);
        //Ejecutamos la sentencia
        sentencia.executeUpdate();


        } catch (SQLException e) {
            // Si las columnas ya existen, se mostrará un mensaje de error
            if (e.getErrorCode() == 1430) {
                System.out.println("Las columnas ya existen en la tabla CICLISTAS.");
            } else {
                e.printStackTrace();
            }
        }



    }

    private static void Ejercicio2(int cod_equipo) {
        try {
            creaYllamaFunción1(cod_equipo);
            creaYllamaFunción2(cod_equipo);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // por si no controlamos cualquier excepción aquí se coge todo
            e.printStackTrace();
        } finally {
            // Cerrar conexión y recursos
            try {
                if (rs != null)
                    rs.close();
                // System.out.println("Primer resultSet cerrado?: " + rs.isClosed());
                if (rs2 != null)
                    rs2.close();
                // System.out.println("Segundo resultSet cerrado?: " + rs2.isClosed());
                if (sentencia != null)
                    sentencia.close();
                //System.out.println("Sentencia cerrada?: " + sentencia.isClosed());
                // if (conexion != null)
                // conexion.close();
                // System.out.println("Conexion cerrada?: " + conexion.isClosed());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void creaYllamaFunción2(int cod_equipo) throws SQLException {
        System.out.println("CICLISTAS QUE HAN LLEVADO LA CAMISETA");
        System.out.println("====================================");
        // Creamos otra función en oracle recibe el codigoEquipo para mostrar los
        // ciclistas que han llevado la camiseta
        String sql2 = "create or replace NONEDITIONABLE FUNCTION FUN2_BORJAGUERRAVALENCIA (\r\n"
                + "    cod_equipo IN NUMBER\r\n"
                + ") RETURN CLOB AS\r\n"
                + "    resultado CLOB;\r\n"
                + "BEGIN\r\n"
                + "    resultado := '';\r\n"
                + "    FOR c IN (\r\n"
                + "        SELECT\r\n"
                + "            ciclistas.nombreciclista,\r\n"
                + "            camisetas.tipo,\r\n"
                + "            camisetas.color\r\n"
                + "        FROM\r\n"
                + "            ciclistas,\r\n"
                + "            camisetas,\r\n"
                + "            lleva\r\n"
                + "        WHERE\r\n"
                + "                lleva.codigociclista = ciclistas.codigociclista\r\n"
                + "            AND ciclistas.codigoequipo = cod_equipo\r\n"
                + "            AND lleva.codigocamiseta = camisetas.codigocamiseta\r\n"
                + "            GROUP BY ciclistas.nombreciclista, camisetas.tipo, camisetas.color\r\n"
                + "    ) LOOP\r\n"
                + "        resultado := resultado\r\n"
                + "                     || c.nombreciclista\r\n"
                + "                     || ' - '\r\n"
                + "                     || c.tipo\r\n"
                + "                     || ' '\r\n"
                + "                     || c.color\r\n"
                + "                     || chr(13)\r\n"
                + "                     || chr(10);\r\n"
                + "    END LOOP;\r\n"
                + "\r\n"
                + "    IF resultado IS NULL THEN\r\n"
                + "        resultado := 'Ningún ciclista ha llevado camiseta';\r\n"
                + "    END IF;\r\n"
                + "    RETURN resultado;\r\n"
                + "END;";
        // CREAMOS SENTENCIA PARA crear la función

        sentencia = conexion.prepareStatement(sql2);
        // Ejecutamos la sentencia
        sentencia.executeUpdate();
        // CONSTRUIMOS ORDEN DE LLAMADA A LA FUNCIÓN CON CALLABLE STATEMENT
        sql2 = "{? = call FUN2_BORJAGUERRAVALENCIA(?)}";
        java.sql.CallableStatement llamada2 = conexion.prepareCall(sql2);

        // Registramos el tipo de la salida de la función
        llamada2.registerOutParameter(1, Types.CLOB);

        // Le pasamos el valor de cod_equipo a la sentencia
        llamada2.setInt(2, cod_equipo);

        // Ejecutamos la consulta
        llamada2.execute();
        // Mostramos los datos
        String cadena2 = llamada2.getString(1);
        System.out.println(cadena2);
    }

    private static void creaYllamaFunción1(int cod_equipo) throws SQLException {
        String sql = "create or replace NONEDITIONABLE FUNCTION FUN1_BORJAGUERRAVALENCIA(cod_equipo IN NUMBER) RETURN VARCHAR2 AS\r\n"
                + "   nombre_equipo VARCHAR2(40);\r\n"
                + "BEGIN\r\n"
                + "   SELECT distinct equipos.nombreequipo INTO nombre_equipo FROM equipos WHERE codigoequipo = cod_equipo;\r\n"
                + "   RETURN 'Equipo: ' || cod_equipo || ', nombre: ' || nombre_equipo;\r\n"
                + "EXCEPTION\r\n"
                + "   WHEN NO_DATA_FOUND THEN\r\n"
                + "      RETURN 'El equipo: ' || cod_equipo || ' NO EXISTE';\r\n"
                + "END;";
        // CREAMOS SENTENCIA PARA crear la función

        sentencia = conexion.prepareStatement(sql);
        // Ejecutamos la sentencia
        sentencia.executeUpdate();
        // CONSTRUIMOS ORDEN DE LLAMADA A LA FUNCIÓN CON CALLABLE STATEMENT
        sql = "{? = call FUN1_BORJAGUERRAVALENCIA(?)}";
        java.sql.CallableStatement llamada = conexion.prepareCall(sql);

        // Registramos el tipo de la salida de la función
        llamada.registerOutParameter(1, Types.VARCHAR);

        // Le pasamos el valor de cod_equipo a la sentencia
        llamada.setInt(2, cod_equipo);

        // Ejecutamos la consulta
        llamada.execute();
        // Mostramos los datos
        String cadena = llamada.getString(1);
        System.out.println(cadena);
    }

    private static void Ejercicio1(int cod_equipo) throws SQLException {
        // Creo una consulta para saber si el código equipo dado, pertenece a algún
        // ciclista
        String sql = "SELECT codigoequipo FROM equipos WHERE codigoequipo = ?";
        // Creamos la sentencia
        sentencia = conexion.prepareStatement(sql);
        // Le pasamos el valor de cod_equipo a la sentencia
        sentencia.setInt(1, cod_equipo);
        // Ejecutamos la consulta
        rs = sentencia.executeQuery();
        // Si el rs no tiene datos, es que no existe el equipo
        if (rs.next()) {
            // Creamos la consulta para saber codigoequipo, nombreequipo,
            // ciclistas.jefeequipo, pais where codigoequipo = cod_equipo
            sql = "SELECT equipos.codigoequipo, nombreequipo, pais FROM equipos, ciclistas WHERE equipos.codigoequipo = ? group by equipos.codigoequipo, equipos.nombreequipo, equipos.pais";
            // Creamos la sentencia
            sentencia = conexion.prepareStatement(sql);

            // Le pasamos el valor de cod_equipo a la sentencia
            sentencia.setInt(1, cod_equipo);

            // Ejecutamos la consulta
            rs = sentencia.executeQuery();

            // Recorremos el rs para obtener los datos
            while (rs.next()) {
                System.out
                        .println("COD-EQUIPO: " + rs.getInt("codigoequipo") + " NOMBRE: "
                                + rs.getString("nombreequipo")
                                + "\nPAIS: " + rs.getString("pais")
                                + ", Jefe de Equipo: " + dameNombreCiclista(cod_equipo));
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                // Ahora consultamos los ciclistas que han ganado alguna etapa de este equipo
                // Etapa, Tipo de etapa, Ciclista (cod/ nombre/ edad), KM, Salida y llegada
                String sql2 = "SELECT etapas.codigoetapa, etapas.tipoetapa, ciclistas.codigociclista, ciclistas.nombreciclista, ciclistas.fechanacimiento, etapas.km, etapas.pobsalida, etapas.pobllegada FROM etapas, ciclistas WHERE etapas.ciclistaganador = ciclistas.codigociclista AND ciclistas.codigoequipo = ?";
                sentencia = conexion.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                sentencia.setInt(1, cod_equipo);
                rs2 = sentencia.executeQuery();
                // recorremos el rs2 para obtener los datos formateados de la consulta
                System.out.println("LISTA DE CICLISTAS QUE HAN GANADO ALGUNA ETAPA:");
                if (rs2.next()) {
                    System.out.printf("%-5s %-21s %-33s %-5s %-30s %n", "Etapa", "Tipo de etapa",
                            "Ciclista (cod/ nombre/ edad)",
                            "KM", "Salida y llegada");
                    System.out
                            .println(
                                    "====================================================================================================");
                    rs2.beforeFirst();
                    while (rs2.next()) {
                        // Obtener la fecha de la base de datos como un objeto Date
                        Date fecha = rs2.getDate("fechanacimiento");
                        // Crear un objeto SimpleDateFormat con el patrón deseado
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                        // Formatear la fecha como una cadena usando el método format
                        String fechaFormateada = sdf.format(fecha);
                        // calcular la edad con la fechaFormateada usando la fecha de hoy
                        int edad = LocalDate.now().getYear() - Integer.parseInt(fechaFormateada);

                        System.out.printf("%-5d %-21s (%-2d %-25s %2d) %-5d %-30s %n", rs2.getInt("codigoetapa"),
                                rs2.getString("tipoetapa"), rs2.getInt("codigociclista"),
                                rs2.getString("nombreciclista"),
                                edad, rs2.getInt("km"),
                                rs2.getString("pobsalida") + " / " + rs2.getString("pobllegada"));
                    }
                } else {
                    System.out.println("No hay ciclistas que hayan ganado alguna etapa");
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                // Ahora consultamos los tramos de montaña que han ganado los ciclistas de este
                // equipo.
                // Etapa / Tipo de etapa Ciclista (cod/ nombre) Tramo / Nombre CATEGORIA

                String sql3 = "SELECT etapas.codigoetapa, etapas.tipoetapa, ciclistas.codigociclista, ciclistas.nombreciclista, tramospuertos.codigotramo, tramospuertos.nombretramo , tramospuertos.categoria FROM etapas, ciclistas, tramospuertos WHERE etapas.ciclistaganador = ciclistas.codigociclista AND ciclistas.codigoequipo = ? AND tramospuertos.numetapa = etapas.codigoetapa";
                sentencia = conexion.prepareStatement(sql3, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                sentencia.setInt(1, cod_equipo);
                rs3 = sentencia.executeQuery();
                // recorremos el rs3 para obtener los datos formateados de la consulta
                // LISTA DE CICLISTAS QUE HAN GANADO TRAMOS DE MONTAÑA
                // Etapa / Tipo de etapa Ciclista (cod/ nombre) Tramo / Nombre CATEGORIA
                // ============================= =============================
                // ============================== =========
                if (rs3.next()) {
                    System.out.println("LISTA DE CICLISTAS QUE HAN GANADO TRAMOS DE MONTAÑA:");
                    System.out.printf("%-5s %-21s %-32s %-30s %-5s %n", "Etapa", "Tipo de etapa",
                            "Ciclista (cod/ nombre)", "Tramo / Nombre", "CATEGORIA");
                    System.out.println(
                            "====================================================================================================");
                    rs3.beforeFirst();
                    while (rs3.next()) {
                        System.out.printf("%-5s %-21s (%2d %-27s) %-5s / %-30s %-5s %n",
                                rs3.getInt("codigoetapa"), rs3.getString("tipoetapa"),
                                rs3.getInt("codigociclista"), rs3.getString("nombreciclista"),
                                rs3.getInt("codigotramo"), rs3.getString("nombretramo"), rs3.getInt("categoria"));
                    }
                } else {
                    System.out.println("No hay ciclistas que haya ganado tramos de montaña");
                    System.out.println(
                            "--------------------------------------------------------------------------------------------------");
                }
            }
        } else {
            System.out.println("No existe el equipo con el código " + cod_equipo);
        }
    }

    private static String dameNombreCiclista(int cod_equipo) {
        String nombreCiclista = "*No existe*";
        // Buscamos el nombre del ciclista jefe de equipo entre los ciclistas del equipo
        String sql = "SELECT nombreciclista FROM ciclistas WHERE codigoequipo = ? AND jefeequipo = codigociclista";
        // creamos la sentencia:
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, cod_equipo);
            rs = sentencia.executeQuery();
            // recorremos el rs para obtener los datos formateados de la consulta
            while (rs.next()) {
                nombreCiclista = rs.getString("nombreciclista");
            }
            return nombreCiclista;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreCiclista;
    }

}

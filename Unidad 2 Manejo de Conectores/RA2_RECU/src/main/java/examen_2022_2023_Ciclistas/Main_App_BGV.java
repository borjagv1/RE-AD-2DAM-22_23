package examen_2022_2023_Ciclistas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
     */

    public static void main(String[] args) {
        // Cargar el driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // conectamos con la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);

            Ejercicio1(15);

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
                //System.out.println("Primer resultSet cerrado?: " + rs.isClosed());
                if (rs2 != null)
                    rs2.close();
                //System.out.println("Segundo resultSet cerrado?: " + rs2.isClosed());
                if (sentencia != null)
                    sentencia.close();
                System.out.println("Sentencia cerrada?: " + sentencia.isClosed());
                if (conexion != null)
                    conexion.close();
                System.out.println("Conexion cerrada?: " + conexion.isClosed());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

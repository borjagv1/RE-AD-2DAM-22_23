import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main2_GuerraValenciaBorja {
    static Connection conexion = null;
    static PreparedStatement sentencia = null;
    static ResultSet rs, rs2, rs3, rs4, rs5 = null;
    static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    static String usuario = "CAFETERIA";
    static String password = "CAFETERIA";

    public static void main(String[] args) {
        // Cargar el driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            conexion = DriverManager.getConnection(url, usuario, password);

            CrearFuncion();// crear procedimientos y funciones
            ejercicio2(22);
            ejercicio2(1);
            ejercicio2(2);
            ejercicio2(3);

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // por si no controlamos cualquier excepción aquí se coge todo
            e.printStackTrace();
        } finally {
            // Cerrar conexión y recursos
            System.out.println("");
            try {
                if (rs != null)
                    rs.close();
                // System.out.println("\nPrimer resultSet cerrado?: " + rs.isClosed());
                if (rs2 != null)
                    rs2.close();
                // System.out.println("Segundo resultSet cerrado?: " + rs2.isClosed());
                if (rs != null)
                    rs3.close();
                // System.out.println("Tercer resultSet cerrado?: " + rs2.isClosed());
                if (rs != null)
                    rs4.close();
                // System.out.println("Cuarto resultSet cerrado?: " + rs2.isClosed());
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
    }// main

    private static void CrearFuncion() throws SQLException {
        // CREAMOS SENTENCIA SQL PARA CREAR LA FUNCIÓN:
        String sql = "CREATE OR REPLACE FUNCTION FUN1_BORJA (IDALERGENO IN NUMBER) RETURN VARCHAR2 AS ALERGENO VARCHAR2(40); BEGIN  SELECT a.name into alergeno FROM ALLERGENS A WHERE A.ID = IDALERGENO; RETURN 'ALERGENO: ' || IDALERGENO || ', ' || alergeno; EXCEPTION WHEN NO_DATA_FOUND THEN RETURN 'NO EXISTE' || ' EL ALERGENO CON ID: ' || IDALERGENO; END FUN1_BORJA;";

        // Creamos la sentencia
        sentencia = conexion.prepareStatement(sql);
        // ejecutamos la sentencia
        try {
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // --------------------------

        // CREAMOS LA SIGUIENTE FUNCION PARA SACAR LOS PLATOS QUE CONTIENEN EL ALÉRGENO
        // id
        sql = "CREATE OR REPLACE FUNCTION FUN2_BORJA (\r\n"
                + "    idalergeno IN NUMBER\r\n"
                + ") RETURN CLOB AS\r\n"
                + "    resultado CLOB;\r\n"
                + "    num_registros NUMBER;\r\n"
                + "BEGIN\r\n"
                + "SELECT COUNT(*) INTO num_registros FROM allergens_products ap WHERE ap.allergen_id = idalergeno; IF num_registros = 0 THEN  resultado := ' '; ELSE"
                + "    resultado := 'PLATOS: ' || CHR(10);\r\n"
                + "    FOR r IN (\r\n"
                + "        SELECT\r\n"
                + "            p.id,\r\n"
                + "            p.name\r\n"
                + "        FROM\r\n"
                + "            allergens          a,\r\n"
                + "            products           p,\r\n"
                + "            allergens_products ap\r\n"
                + "        WHERE\r\n"
                + "                a.id = idalergeno\r\n"
                + "            AND ap.allergen_id = idalergeno\r\n"
                + "            AND ap.product_id = p.id\r\n"
                + "    ) LOOP\r\n"
                + "        resultado := resultado\r\n"
                + "                  || 'Plato: '\r\n"
                + "                  || r.id\r\n"
                + "                  || ': '\r\n"
                + "                  || r.name\r\n"
                + "                  || chr(10);\r\n"
                + "    END LOOP;\r\n"
                + "\r\n"
                + " END IF;"
                + "    RETURN resultado;\r\n"
                + "END FUN2_BORJA;";
        // Creamos la sentencia
        sentencia = conexion.prepareStatement(sql);
        // ejecutamos la sentencia
        try {
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // --------------------------
        // Creamos la tercera función para mostrar menús que contienen el alérgeno id
        sql = "CREATE OR REPLACE NONEDITIONABLE FUNCTION fun3_borja (\r\n"
                + "    idalergeno IN NUMBER\r\n"
                + ") RETURN CLOB AS\r\n"
                + "    resultado CLOB;\r\n"
                + "    num_registros NUMBER;\r\n"
                + "BEGIN\r\n"
                + "SELECT COUNT(*) INTO num_registros FROM allergens_products ap WHERE ap.allergen_id = idalergeno; IF num_registros = 0 THEN  resultado := CHR(10); ELSE"
                + "    resultado := 'MENÚS: ' || CHR(10);\r\n"
                + "    FOR r IN (\r\n"
                + "        SELECT DISTINCT\r\n"
                + "            dm.menu_id,\r\n"
                + "            m.name AS menu_name,\r\n"
                + "            d.id   AS dish_id,\r\n"
                + "            d.name AS dish_name\r\n"
                + "        FROM\r\n"
                + "                 allergens a\r\n"
                + "            JOIN allergens_products ap ON a.id = ap.allergen_id\r\n"
                + "            JOIN products           p ON ap.product_id = p.id\r\n"
                + "            JOIN dishes_menus       dm ON p.id = dm.dish_id\r\n"
                + "            JOIN products           d ON dm.dish_id = d.id\r\n"
                + "            JOIN (\r\n"
                + "                SELECT DISTINCT\r\n"
                + "                    id,\r\n"
                + "                    name\r\n"
                + "                FROM\r\n"
                + "                    products\r\n"
                + "                WHERE\r\n"
                + "                    type = 'menu'\r\n"
                + "            )                  m ON dm.menu_id = m.id\r\n"
                + "        WHERE\r\n"
                + "            a.id = idalergeno\r\n"
                + "        ORDER BY\r\n"
                + "            dm.menu_id\r\n"
                + "    ) LOOP\r\n"
                + "\r\n"
                + "        resultado := resultado\r\n"
                + "                     || 'Menu: '\r\n"
                + "                     || r.menu_id\r\n"
                + "                     || ': '\r\n"
                + "                     || r.menu_name\r\n"
                + "                     || ' (Plato: '\r\n"
                + "                     || r.dish_id\r\n"
                + "                     || ': '\r\n"
                + "                     || r.dish_name\r\n"
                + "                     || ')'\r\n"
                + "                     || chr(10);\r\n"
                + "\r\n"
                + "    END LOOP;\r\n"
                + "\r\n"
                + "END IF;"
                + "    RETURN resultado;\r\n"
                + "END fun3_borja;";
        // Creamos la sentencia
        sentencia = conexion.prepareStatement(sql);
        // ejecutamos la sentencia
        try {
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void ejercicio2(int id) throws SQLException {
        // A DESARROLLAR POR EL ALUMNO
        // Hacemos uso de la función que acabo de crear con callableStatement
        String sql2 = "{? = call FUN1_BORJA(?)}";
        java.sql.CallableStatement llamada = conexion.prepareCall(sql2);
        // Doy valor al parámetro IN de entrada y al valor que devuelve el return
        llamada.setInt(2, id);

        // describo el tipo que devuelve el return de la función
        llamada.registerOutParameter(1, java.sql.Types.VARCHAR);

        // ejecuto la función
        llamada.execute();
        // recojo el valor del return de la función
        String resultado = llamada.getString(1);
        System.out.println(resultado);

        // Hacemos uso de la función que acabo de crear con callableStatement
        String sql3 = "{? = call FUN2_BORJA(?)}";
        java.sql.CallableStatement llamada2 = conexion.prepareCall(sql3);
        // Doy valor al parámetro IN de entrada y al valor que devuelve el return
        llamada2.setInt(2, id);
        // describo el tipo que devuelve el return de la función
        llamada2.registerOutParameter(1, java.sql.Types.CLOB);
        // ejecuto la función
        llamada2.execute();
        // recojo el valor del return de la función
        String resultado2 = llamada2.getString(1);
        System.out.println(resultado2);

        // Hacemos uso de la función que acabo de crear con callableStatement
        String sql4 = "{? = call FUN3_BORJA(?)}";
        java.sql.CallableStatement llamada3 = conexion.prepareCall(sql4);
        // Doy valor al parámetro IN de entrada y al valor que devuelve el return
        llamada3.setInt(2, id);
        // describo el tipo que devuelve el return de la función
        llamada3.registerOutParameter(1, java.sql.Types.CLOB);
        // ejecuto la función
        llamada3.execute();
        // recojo el valor del return de la función
        String resultado3 = llamada3.getString(1);
        System.out.println(resultado3);

    }
}// class

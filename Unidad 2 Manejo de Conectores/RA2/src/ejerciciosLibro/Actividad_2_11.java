package ejerciciosLibro;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * Actividad_2_11
 */
public class Actividad_2_11 {

    private static final String JDBC_MYSQL_LOCALHOST_EJEMPLO = "jdbc:mysql://localhost/ejemplo";
    private static String USER = "ejemplo";
    private static String PASSWORD = "ejemplo";

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet rs;
    private static ResultSet rs2;
    private static final int dept_no = 10;

    public static void main(String[] args) {
        int cont = 0;

        // establecer conexion con mysql
        try {
            // cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(JDBC_MYSQL_LOCALHOST_EJEMPLO, USER, PASSWORD);

            // Preparamos la consulta con prepared statement
            // se pide un select para visualizar APELLIDO, SALARIO Y OFICIO DE LOS EMPLEADOS
            // DE UN DEPARTAMENTO DETERMINADO
            String sql = "SELECT apellido, salario, oficio FROM empleados WHERE dept_no = ?";
            sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, dept_no);

            rs = sentencia.executeQuery();
            // recorremos el rs para obtener los datos
            System.out.println("DATOS DEL DEPARTAMENTO " + dept_no);
            while (rs.next()) {
                System.out.println("APELLIDO: " + rs.getString("apellido") + "\tSALARIO: " + rs.getInt("salario")
                        + "\tOFICIO: " + rs.getString("oficio"));
                cont++;

            }
            // Obtener por cada empleado leído del departamento, el salario medio y el
            // numero de empleados del departamento:
            // Preparamos la consulta con prepared statement
            sql = "SELECT AVG(salario) AS salario_medio, COUNT(*) AS num_empleados FROM empleados WHERE dept_no = ?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, dept_no);
            rs2 = sentencia.executeQuery();
            // recorremos el rs para obtener los datos
            while (rs2.next()) {
                //dar formato al salario medio
                DecimalFormat df = new DecimalFormat("##,##0.00");
                String salarioMedio = df.format(rs2.getFloat("salario_medio"));
                System.out.println("\nSALARIO MEDIO: " + salarioMedio + "\tNUMERO EMPLEADOS: "
                        + rs2.getInt("num_empleados") + cont);
            }
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // Catch-all para cualquier otra excepción no manejada anteriormente
            // Aquí podrías agregar algún mensaje de registro o notificación del error
            e.printStackTrace();
        } finally {
            // Cerrar conexión y recursos
            try {
                if (rs != null)
                    rs.close();
                if (rs2 != null)
                    rs2.close();
                if (sentencia != null)
                    sentencia.close();
                if (conexion != null)
                    conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
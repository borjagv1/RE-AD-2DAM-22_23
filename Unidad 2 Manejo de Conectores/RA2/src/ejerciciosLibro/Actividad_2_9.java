package ejerciciosLibro;
/*
 probar una consulta con dos tablas y visualizar el nombre de la tabla a la que pertenece la columna getTableName(i); HECHO como modificación del ejemplo resusetmetadata
 */

import java.sql.*;

public class Actividad_2_9 {

	public static void main(String[] args) {
		try {

			// conexion a ORACLE
//			
//			  Class.forName ("oracle.jdbc.driver.OracleDriver"); Connection
//			  conexion = DriverManager.getConnection
//			  ("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO", "EJEMPLO");

			Class.forName("com.mysql.jdbc.Driver"); // Cargar el driver
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT e.APELLIDO, e.SALARIO, d.dnombre "
					+ "FROM EMPLEADOS e, DEPARTAMENTOS d " + "WHERE d.dept_no = e.dept_no ORDER BY 1");

			ResultSetMetaData rsmd = rs.getMetaData();

			int nColumnas = rsmd.getColumnCount();
			String nula;
			System.out.printf("Número de columnas recuperadas: %d%n", nColumnas);
			for (int i = 1; i <= nColumnas; i++) {
				System.out.printf("Columna %d: %n ", i);
				System.out.printf("  Nombre: %s %n   Tipo: %s  Tabla: %s %n ", rsmd.getColumnName(i),
						rsmd.getColumnTypeName(i), rsmd.getTableName(i));
				if (rsmd.isNullable(i) == 0)
					nula = "NO";
				else
					nula = "SI";

				System.out.printf("  Puede ser nula?: %s %n ", nula);
				System.out.printf("  Máximo ancho de la columna: %d %n", rsmd.getColumnDisplaySize(i));
			} // for

			sentencia.close();
			rs.close();
			conexion.close();

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main
}

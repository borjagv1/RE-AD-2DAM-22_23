import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainHSQLDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Cargar el driver
			Class.forName("org.hsqldb.jdbcDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection(
					"jdbc:hsqldb:file:C:/Users/borja/Bases de Datos AD/HSQLDB/ejemplo/ejemplo");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT * FROM departamentos";
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila. Los nº son cada columna - numero - nombre - ciudad
			// Se hace un bucle mientras haya registros y se van visualizando
			while (resul.next()) {
				System.out.printf("%d, %s, %s %n", resul.getInt(1), resul.getString(2), resul.getString(3));
			}
			//Lo mismo pero en vez de con nºs con los nombres de las columnas:
			
			System.out.println("");
			resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila. Los nº son cada columna - numero - nombre - ciudad
			// Se hace un bucle mientras haya registros y se van visualizando
			while (resul.next()) {
				System.out.printf("%d, %s, %s %n", resul.getInt("dept_no"), resul.getString("dnombre"), resul.getString("loc"));
			}


			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexi�n

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// fin de main
}// fin de la clase

import java.sql.*;

public class Main3 {
	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String cad = "DIRECTOR";
			
			String sql = "SELECT apellido FROM empleados WHERE oficio = '" + cad + "'";
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila. Los nº son cada columna - numero - nombre - ciudad
			// Se hace un bucle mientras haya registros y se van visualizando
			while (resul.next()) {
				System.out.printf("%s %n", resul.getString(1));
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

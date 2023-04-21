import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearTabla_ControlExc {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			String sql = "CREATE TABLE TABLA1 (NOM VARCHAR(20), ED INT)";
			System.out.println(sql);
			Statement sentencia = conexion.createStatement();
			try { //SQLSyntaxErrorException

				int filas = sentencia.executeUpdate(sql.toString());
				System.out.printf("Resultado  de la ejecución: %d %n", filas);
				
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("Cod error : %s %n", e.getErrorCode());
				//System.err.println(e.getMessage());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexi�n

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // Cargar el driver

	}// fin de main
}// fin de la clase
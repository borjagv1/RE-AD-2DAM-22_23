package recursosRA2.ejemplosJDBC;

import java.sql.*;

public class EjemploDatabaseMetadata {
	public static void main(String[] args) {
		try {
			// Cargar el driver MYSQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");
//
//			// Cargar el driver
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//
//			// Establecemos la conexion con la BD MYSQL
//			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO",
//					"EJEMPLO");

			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos
			// objeto DatabaseMetaData
			ResultSet resul = null;

			String nombre = dbmd.getDatabaseProductName();
			String driver = dbmd.getDriverName();
			String url = dbmd.getURL();
			String usuario = dbmd.getUserName();

			System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:");
			System.out.println("===================================");
			System.out.printf("Nombre : %s %n", nombre);
			System.out.printf("Driver : %s %n", driver);
			System.out.printf("URL    : %s %n", url);
			System.out.printf("Usuario: %s %n", usuario);

			// Obtener informaci�n de las tablas y vistas que hay
			// resul = dbmd.getTables(null, "EJEMPLO", null, null); // ORACLE
			resul = dbmd.getTables("ejemplo", null, "de%", null); // mysql //Lo de de% es para que muestre lo que
																	// empieza por 'de', podemos modificar esto poniendo
																	// ejemplo en otro lugar y ver lo que hace. En el 2º
																	// nos muestra la bbddd y sus tablas

//			// Para ver todas las tablas y vistas a las que accede el esquema-usuario actual
//			String[] tipos = { "TABLE", "VIEW" };
//			resul = dbmd.getTables(null, null, null, tipos);

			while (resul.next()) {
				String catalogo = resul.getString(1);// columna 1
				String esquema = resul.getString(2); // columna 2
				String tabla = resul.getString(3); // columna 3
				String tipo = resul.getString(4); // columna 4
				System.out.printf("%s - Catalogo: %s, Esquema: %s, Nombre: %s %n", tipo, catalogo, esquema, tabla);
			}
			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main
}// fin de la clase

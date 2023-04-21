package recursosRA2.ejemplosJDBC;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemplogetColumns {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO",
					"EJEMPLO");

//			Class.forName("com.mysql.jdbc.Driver"); //Cargar el driver
//			//Establecemos la conexion con la BD
//	        Connection conexion = DriverManager.getConnection  
//	            ("jdbc:mysql://localhost/ejemplo","ejemplo", "ejemplo");   
//	  
			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos
			// objeto DatabaseMetaData
			ResultSet resul = null;

			System.out.println("COLUMNAS TABLA DEPARTAMENTOS:");
			System.out.println("===================================");
			ResultSet columnas = null;
			// BBDD, USUARIO, TABALA A CONSULTAR COLUMNAS, FILTRO (COLUMNAS QUE EMPIECEN POR
			// D)
			// columnas = dbmd.getColumns(null, "ejemplo", "departamentos", null); //mysql

			// columnas = dbmd.getColumns("ejemplo", "ejemplo", "departamentos", "d%");
			// //Mysql

			columnas = dbmd.getColumns("EJEMPLO", "EJEMPLO", "DEPARTAMENTOS", null); // ORACLE

			while (columnas.next()) {
				String nombCol = columnas.getString("COLUMN_NAME"); // getString(4)
				String tipoCol = columnas.getString("TYPE_NAME"); // getString(6)
				String tamCol = columnas.getString("COLUMN_SIZE"); // getString(7)
				String nula = columnas.getString("IS_NULLABLE"); // getString(18)

				System.out.printf("Columna: %s, Tipo: %s, Tamaño: %s, ¿Puede ser Nula:? %s %n", nombCol, tipoCol,
						tamCol, nula);
			}

			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main

}

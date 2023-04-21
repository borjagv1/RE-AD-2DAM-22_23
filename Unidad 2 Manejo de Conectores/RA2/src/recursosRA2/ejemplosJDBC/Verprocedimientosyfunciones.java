package recursosRA2.ejemplosJDBC;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Verprocedimientosyfunciones {

	public static void main(String[] args) {
		try {
			
			  // conexion a ORACLE
			  
			  Class.forName ("oracle.jdbc.driver.OracleDriver"); 
			  Connection conexion =
			  DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:XE",
			  "EJEMPLO", "EJEMPLO");
			 
// 			MYSQL
//			Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver
//			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");
//
		DatabaseMetaData dbmd = conexion.getMetaData();// Creamos objeto
//															// DatabaseMetaData

			ResultSet proc = dbmd.getProcedures(null, "EJEMPLO", null); //oracle
			//proc = dbmd.getProcedures("ejemplo", "ejemplo", null); //oracle
			while (proc.next()) {
				String proc_name = proc.getString("PROCEDURE_NAME");
				String proc_type = proc.getString("PROCEDURE_TYPE");
				System.out.printf("Nombre Procedimiento: %s - Tipo: %s %n", proc_name, proc_type);
			}

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main

}

package recursosRA2.ejemplosJDBC;

import java.sql.*;

public class ProcSubida {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Cargar el driver
			Connection conexion = DriverManager.getConnection(
					"jdbc:mysql://localhost/ejemplo", "ejemplo",
					    "ejemplo");

			 //conexion a ORACLE		  
		     /*Class.forName ("oracle.jdbc.driver.OracleDriver");	
			  Connection conexion = DriverManager.getConnection  
	          ("jdbc:oracle:thin:@localhost:1521:XE", "ejemplo", "ejemplo");  
            */
			  			
			// recuperar parametros de main
			String dep = "10";   //"10"; // departamento
			String subida = "1000";//"1000"; // subida
			
			// construir orden DE LLAMADA
			String sql = "{ call subida_sal (?, ?) } ";

			// Preparamos la llamada
			CallableStatement llamada = conexion.prepareCall(sql);
			// Damos valor a los argumentos
			llamada.setInt(1, Integer.parseInt(dep)); // primer argumento-dep
			llamada.setFloat(2, Float.parseFloat(subida)); // segundo arg
															
			llamada.executeUpdate(); // ejecutar el procedimiento
			System.out.println("Subida realizada....");
			llamada.close();
			conexion.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// fin de main
}// fin de la clase

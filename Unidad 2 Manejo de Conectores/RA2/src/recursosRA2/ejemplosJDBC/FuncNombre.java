package recursosRA2.ejemplosJDBC;

import java.sql.*;
public class FuncNombre {
	   public static void main(String[] args) {
	     try {
	       Class.forName("oracle.jdbc.driver.OracleDriver");
	       Connection conexion = DriverManager.getConnection
	        ("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO", "EJEMPLO");

	       //recuperar parametro de main
		 String dep = "10"; //departamento

	       //construir orden de llamada
	       String sql = "{ ? = call nombre_dep (?, ?) } "; // ORACLE

	       //Preparar la llamada
	       CallableStatement llamada = conexion.prepareCall(sql);

	       //registrar par�metro de resultado
	       llamada.registerOutParameter(1, Types.VARCHAR);//valor devuelto

	       llamada.setInt(2,Integer.parseInt(dep));      //param de entrada

	       //registrar par�metro de salida
	       llamada.registerOutParameter(3, Types.VARCHAR);//par�metro OUT

	       llamada.executeUpdate(); // ejecutar el procedimiento
	       System.out.printf("Nombre Dep: %s, Localidad: %s %n",
	                        llamada.getString(1), llamada.getString(3));
	       llamada.close();
	       conexion.close();
	     } 
	     catch (ClassNotFoundException cn) { cn.printStackTrace(); }
	     catch (SQLException e)            { e.printStackTrace(); }
	   }// fin de main
	}// fin de la clase



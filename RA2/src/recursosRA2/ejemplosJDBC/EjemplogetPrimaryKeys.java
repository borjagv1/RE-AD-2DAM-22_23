package recursosRA2.ejemplosJDBC;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemplogetPrimaryKeys {

	public static void main(String[] args) {
		 try
		  {
			Class.forName("com.mysql.cj.jdbc.Driver"); //Cargar el driver
			//Establecemos la conexion con la BD
	        Connection conexion = DriverManager.getConnection  
	            ("jdbc:mysql://localhost/ejemplo","ejemplo", "ejemplo");   
	        
	        
	        
	        //Oracle 
		    /*Class.forName ("oracle.jdbc.driver.OracleDriver");	
		    Connection conexion = DriverManager.getConnection  
		          ("jdbc:oracle:thin:@localhost:1521:XE", "ejemplo", "ejemplo");
		    */
		    
	  
			DatabaseMetaData dbmd = conexion.getMetaData();//Creamos 
	                               //objeto DatabaseMetaData
	  		@SuppressWarnings("unused")
			ResultSet resul = null;
			 
	  		System.out.println("CLAVE PRIMARIA TABLA DEPARTAMENTOS:");
	  		System.out.println("===================================");
	  		ResultSet pk = dbmd.getPrimaryKeys("ejemplo", "ejemplo", "departamentos");
	  		String pkDep="", separador="";
	  		while (pk.next()) {
	  			   pkDep = pkDep + separador + 
	  		               pk.getString("COLUMN_NAME");//getString(4)
	  			   separador="+";
	  		 }
	  		System.out.println("Clave Primaria: " + pkDep);
	  		
	  		System.out.println("CLAVE PRIMARIA TABLA EMPLEADOS:");
	  		System.out.println("===================================");
	  		pk = dbmd.getPrimaryKeys("ejemplo", "ejemplo", "empleados");
	  		pkDep="";
	  				separador="";
	  		while (pk.next()) {
	  			   pkDep = pkDep + separador + 
	  		               pk.getString("COLUMN_NAME");//getString(4)
	  			   separador="+";
	  		 }
	  		System.out.println("Clave Primaria: " + pkDep);



				
	  		 conexion.close(); //Cerrar conexion   		  	   
		  } 
	   catch (ClassNotFoundException cn) {cn.printStackTrace();} 
				   catch (SQLException e) {e.printStackTrace();}		
		}// fin de main

}

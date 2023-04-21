package recursosRA2.ejemplosJDBC;



import java.sql.*;

public class FuncNombreMysql {

	public static void main(String[] args) {
		try {

			Class.forName("com.mysql.jdbc.Driver"); 
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			// recuperar parametro de main
			String dep = "11";// args[0]; //departamento

			// MYSQL
			// CREATE FUNCTION nombre_dep(d int) RETURNS VARCHAR(15)
			// CREATE PROCEDURE datos_dep(d int, OUT nom VARCHAR(15),
			// OUT locali VARCHAR(15))

			String sql = "{ ? = call nombre_dep (?) } "; // MYSQL

			// Preparamos la llamada
			CallableStatement llamada = conexion.prepareCall(sql);

			llamada.registerOutParameter(1, Types.VARCHAR); // valor devuelto
			llamada.setInt(2, Integer.parseInt(dep)); // param de entrada

			llamada.executeUpdate(); // ejecutar el procedimiento
			System.out.println("Nombre Dep: " + llamada.getString(1));
			llamada.close();
			conexion.close();

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main
}// fin de la clase
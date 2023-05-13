package ejemplos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class ListadoAlumnos2OrderbyCodigo {

	public static void main(String[] args) {
		try {
			// Oracle
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD4", "UNIDAD4");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "select * from alumnos2 a order by a.pers.codigo";
			ResultSet resul = sentencia.executeQuery(sql);
			
			//java.sql.Struct
			while (resul.next()) {
				System.out.println("Entra");
				oracle.jdbc.OracleStruct objeto = (oracle.jdbc.OracleStruct) resul.getObject(1);
				int eva1 = resul.getInt(2);
				int eva2 = resul.getInt(3);
				int eva3 = resul.getInt(4);
				
				Object[] atribpersona = objeto.getAttributes();
				BigDecimal codigo = (BigDecimal) atribpersona[0];
				String nombre = (String) atribpersona[1];
				
				oracle.jdbc.OracleStruct objeto2 = (oracle.jdbc.OracleStruct) atribpersona[2];
				
				Object[] atribdirec = objeto2.getAttributes();
				String calle = (String) atribdirec[0];
				String ciudad = (String) atribdirec[1];
				java.math.BigDecimal codigo_post = (java.math.BigDecimal) atribdirec[2];
				Date fecha = (Date) atribpersona[3];
				

				System.out.printf("Codigo:%s, Nombre:%s Fecha_nac: %s %n \t Calle: %s, Ciudad: %s, CP: %d, Eva1: %s, Eva2: %s, Eva3: %s %n", codigo,
						nombre, fecha, calle, ciudad, codigo_post.intValue(),eva1,eva2,eva3);
				
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

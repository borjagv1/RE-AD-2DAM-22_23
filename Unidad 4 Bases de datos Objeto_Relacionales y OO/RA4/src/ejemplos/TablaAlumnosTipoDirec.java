package ejemplos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TablaAlumnosTipoDirec {
	public static void main(String[] args) {
		try {
			// Oracle
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD4", "UNIDAD4");
			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "select * from ALUMNOS";
			ResultSet resul = sentencia.executeQuery(sql);

			// java.sql.Struct
			while (resul.next()) {
				int CODIGO = resul.getInt(1);
				String NOMBRE = resul.getString(2);
				java.sql.Date FECHA_NAC = resul.getDate(4);
				// Obtengo el objeto DIRECCION
				oracle.jdbc.OracleStruct objeto = (oracle.jdbc.OracleStruct) resul.getObject(3);
				// java.sql.Struct objeto = (java.sql.Struct) resul.getObject(3);

				// Saco sus atributos CALLE, CIUDAD, CODIGO_POST
				Object[] atributos = objeto.getAttributes();
				String calle = (String) atributos[0];
				String ciudad = (String) atributos[1];
				java.math.BigDecimal codigo_post = (java.math.BigDecimal) atributos[2];
				System.out.printf("Codigo:%d, Nombre:%s Fecha_nac: %s %n \t Calle: %s, Ciudad: %s, CP: %d %n", CODIGO,
						NOMBRE, FECHA_NAC, calle, ciudad, codigo_post.intValue());
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main
}// fin de la clase

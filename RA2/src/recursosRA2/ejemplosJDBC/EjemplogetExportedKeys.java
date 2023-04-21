package recursosRA2.ejemplosJDBC;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemplogetExportedKeys {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver
			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			// Oracle
			/*
			 * Class.forName ("oracle.jdbc.driver.OracleDriver"); Connection
			 * conexion = DriverManager.getConnection
			 * ("jdbc:oracle:thin:@localhost:1521:XE", "ejemplo", "ejemplo");
			 */

			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos
			// objeto DatabaseMetaData
			ResultSet resul = null;

			System.out.println("CLAVES ajenas que referencian a DEPARTAMENTOS:");
			System.out.println("==============================================");

			ResultSet fk = dbmd.getExportedKeys(null, "EJEMPLO", "DEPARTAMENTOS");

			while (fk.next()) {
				String fk_name = fk.getString("FKCOLUMN_NAME");
				String pk_name = fk.getString("PKCOLUMN_NAME");
				String pk_tablename = fk.getString("PKTABLE_NAME");
				String fk_tablename = fk.getString("FKTABLE_NAME");
				//System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
				System.out.printf("\tTabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
			}
			
			System.out.println("\nCLAVES ajenas que referencian a EMPLEADOS:");
			System.out.println("==============================================");

			fk = dbmd.getExportedKeys(null, "EJEMPLO", "EMPLEADOS");

			while (fk.next()) {
				String fk_name = fk.getString("FKCOLUMN_NAME");
				String pk_name = fk.getString("PKCOLUMN_NAME");
				String pk_tablename = fk.getString("PKTABLE_NAME");
				String fk_tablename = fk.getString("FKTABLE_NAME");
				//System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
				System.out.printf("\tTabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
			}
			System.out.println("\n - getImportedKeys() -");
			System.out.println("\nCLAVES ajenas que existen en DEPARTAMENTOS (no hay):");
			System.out.println("==============================================");

			ResultSet ik = dbmd.getImportedKeys("ejemplo", "ejemplo", "DEPARTAMENTOS");

			while (ik.next()) {
				String fk_name = ik.getString("FKCOLUMN_NAME");
				String pk_name = ik.getString("PKCOLUMN_NAME");
				String pk_tablename = ik.getString("PKTABLE_NAME");
				String fk_tablename = ik.getString("FKTABLE_NAME");
				System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
				System.out.printf("\tTabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
			}
			
			System.out.println("\nCLAVES ajenas que existen en EMPLEADOS:");
			System.out.println("==============================================");

			ik = dbmd.getImportedKeys("ejemplo", "ejemplo", "EMPLEADOS");

			while (ik.next()) {
				String fk_name = ik.getString("FKCOLUMN_NAME");
				String pk_name = ik.getString("PKCOLUMN_NAME");
				String pk_tablename = ik.getString("PKTABLE_NAME");
				String fk_tablename = ik.getString("FKTABLE_NAME");
				System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
				System.out.printf("\tTabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
			}

			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main

}

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class MainJimenezMartinezDavid {
	static Connection conexion;
	public static void main(String[] args) {
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/empresas", "empresas", "empresas");
	
			ejercicio1(1);
			
			ejercicio2(1);
			ejercicio2(1000);
			ejercicio2(4);
			
			eliminaColumna();
			ejercicio3(11, "GESTIÓN", "C/Mayor 17", "Madrid", 101, 1);
			ejercicio3(111, "GESTIÓN", "C/Mayor 17", "Madrid", 10, 123);
			ejercicio3(11, "GESTIÓN", "C/Mayor 17", "Madrid", 10, 123);
			ejercicio3(112, "GESTIÓN", "C/Mayor 17", "Madrid", 101, 1); 
			ejercicio3(113, "GESTIÓN", "C/Mayor 17", "Madrid", 101, 2);
						
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("HA OCURRIDO UNA EXCEPCIÓN:");
			System.out.println("Mensaje:    " + e.getMessage());
			System.out.println("SQL estado: " + e.getSQLState());
			System.out.println("Cód error:  " + e.getErrorCode());
		}
		

	}
	private static void ejercicio3(int cdep, String ndep, String dirdep, String locdep, int jefdep, int empdep) throws SQLException {
		// comprobar que coddepar no existe
		
		boolean errores = false;
		
		String sql;
		creaColumna();
		
		sql = "SELECT CODDEPART FROM DEPARTAMENTOS WHERE CODDEPART = ?";
		PreparedStatement sentenciaprep = conexion.prepareStatement(sql);
		sentenciaprep.setInt(1, cdep);
		ResultSet rs = sentenciaprep.executeQuery();
		if(rs.next()) {
			System.out.println("EL DEPARTAMENTO "+cdep+" ya existe.");
			errores = true;
		}
		
		sql = "SELECT CODEMPLE FROM EMPLEADOS WHERE CODEMPLE = ?";
		sentenciaprep = conexion.prepareStatement(sql);
		sentenciaprep.setInt(1, jefdep);
		rs = sentenciaprep.executeQuery();
		if(!rs.next()) {
			System.out.println("EL JEFE DE DEPARTAMENTO "+jefdep+" NO existe.");
			errores = true;
		}
		
		sql = "SELECT CODEMPRE FROM EMPRESAS WHERE CODEMPRE = ?";
		sentenciaprep = conexion.prepareStatement(sql);
		sentenciaprep.setInt(1, empdep);
		rs = sentenciaprep.executeQuery();
		if(!rs.next()) {
			System.out.println("EL CODIGO DE EMPRESA "+empdep+", NO existe.");
			errores = true;
		}
		
		if (errores) {
			System.out.println("HAY ERRORES, NO SE INSERTARÁ EL REGISTRO\n");
		}else {
			sql= "UPDATE EMPRESAS SET CONTADOR = ((SELECT CONTADOR FROM EMPRESAS WHERE CODEMPRE = ?) + 1) WHERE CODEMPRE = ?";
			sentenciaprep = conexion.prepareStatement(sql);
			sentenciaprep.setInt(1, empdep);
			sentenciaprep.setInt(2, empdep);
			sentenciaprep.executeUpdate();
			System.out.println("REGISTRO INSERTADO...\n");
			System.out.println("SE HA SUMADO 1 A LA EMPRESA: "+empdep+"\n");
			sql= "INSERT INTO DEPARTAMENTOS VALUES(?,?,?,?,?,?)";
			sentenciaprep = conexion.prepareStatement(sql);
			sentenciaprep.setInt(1, cdep);
			sentenciaprep.setInt(2, empdep);
			sentenciaprep.setString(3, dirdep);
			sentenciaprep.setString(4, locdep);
			sentenciaprep.setInt(5, jefdep);
			sentenciaprep.setInt(6, empdep);
			sentenciaprep.executeUpdate();
		}
		
		
		
		//MENSAJES DE ERROR
		
		
	}
	private static void eliminaColumna() throws SQLException {
		String sql = "ALTER TABLE EMPRESAS DROP COLUMN IF EXISTS CONTADOR";
		Statement sentencia = conexion.createStatement();
		sentencia.execute(sql);
		
	}
	private static void creaColumna() throws SQLException {

		String sql = "ALTER TABLE EMPRESAS ADD COLUMN IF NOT EXISTS CONTADOR INT(11) DEFAULT 0";
		Statement sentencia = conexion.createStatement();
		sentencia.execute(sql);
	}
	private static void ejercicio2(int codempre) throws SQLException {
		String sql;
		creacionFuncionesEj2();
		
		sql = "{ ? = call FUN1_DAVIDJIMENEZ (?) } ";
		CallableStatement llamada1 = conexion.prepareCall(sql);
		llamada1.registerOutParameter(1, Types.INTEGER); 
		llamada1.setInt(2, codempre); 
		llamada1.executeUpdate();
		
		
		sql = "{ ? = call FUN2_DAVIDJIMENEZ (?) } ";
		CallableStatement llamada2 = conexion.prepareCall(sql);
		llamada2.registerOutParameter(1, Types.INTEGER); 
		llamada2.setInt(2, codempre);
		llamada2.executeUpdate();
		
		sql = "SELECT CODEMPRE, NOMBRE FROM EMPRESAS WHERE CODEMPRE = ?";
		PreparedStatement sentenciaprep = conexion.prepareStatement(sql);
		sentenciaprep.setInt(1, codempre);
		ResultSet rs = sentenciaprep.executeQuery();
		if(rs.next()) {
			System.out.printf("Código empresa: %d, Nombre: %s %n"
					+ "\tNúmero de departamentos: %d %n"
					+ "\tNúmero de empleados: %d %n %n", rs.getInt(1),rs.getString(2), 
					llamada1.getInt(1),
					llamada2.getInt(1));
		}else {
			System.out.println("La empresa "+codempre+", no existe. \n");
		}
		
		
		
	}
	private static void creacionFuncionesEj2() throws SQLException {
		String sql = "CREATE OR REPLACE FUNCTION FUN1_DAVIDJIMENEZ(codemp INT) RETURNS INT\r\n"
				+ "BEGIN\r\n"
				+ "    DECLARE ndepar INT; \r\n"
				+ "    SELECT count(d.CODDEPART) INTO ndepar\r\n"
				+ "    FROM DEPARTAMENTOS d \r\n"
				+ "    WHERE d.CODEMPRE = codemp\r\n"
				+ "    GROUP BY d.CODEMPRE;\r\n"
				+ "    RETURN ndepar;\r\n"
				+ "END;";
		Statement sentencia = conexion.createStatement();
		sentencia.execute(sql);
		sql = "CREATE OR REPLACE FUNCTION FUN2_DAVIDJIMENEZ(codemp INT) RETURNS INT\r\n"
				+ "BEGIN\r\n"
				+ "    DECLARE nemple INT;\r\n"
				+ "    SELECT COUNT(ed.CODEMPLE) INTO nemple\r\n"
				+ "    FROM empleados ed JOIN departamentos d ON ed.coddepart = d.coddepart\r\n"
				+ "    WHERE d.codempre = codemp\r\n"
				+ "    GROUP BY d.CODEMPRE;\r\n"
				+ "    RETURN nemple;\r\n"
				+ "END;";
		sentencia = conexion.createStatement();
		sentencia.execute(sql);
	}
	private static void ejercicio1(int codemp) throws SQLException {
		String sql = "SELECT er.CODEMPRE, er.NOMBRE, er.DIRECCION, COUNT(d.CODDEPART)\r\n"
				+ "FROM EMPRESAS er JOIN DEPARTAMENTOS d ON er.CODEMPRE = d.CODEMPRE\r\n"
				+ "WHERE er.CODEMPRE = ? "
				+ "GROUP BY er.CODEMPRE";
		PreparedStatement sentenciaprep = conexion.prepareStatement(sql);
		sentenciaprep.setInt(1, codemp);
		ResultSet rs1 = sentenciaprep.executeQuery();
		
		if(rs1.next()) {
			System.out.printf("COD-EMPRESA: %-3d NOMBRE: %-20s\r\n"
					+ "DIRECCIÓN: %-33s Número de departamentos: %-3d %n",
					rs1.getInt(1),rs1.getString(2),rs1.getString(3),rs1.getInt(4));
			System.out.println("-----------------------------------------------------------------------------------");
			sql = "SELECT d.CODDEPART, d.NOMBRE, d.LOCALIDAD, "
					+ "(SELECT ed.NOMBRE FROM EMPLEADOS ed WHERE d.CODJEFEDEPARTAMENTO = ed.CODEMPLE),"
					+ "(SELECT COUNT(ed.NOMBRE )FROM EMPLEADOS ed WHERE d.CODDEPART = ed.CODDEPART)\r\n"
					+ "FROM DEPARTAMENTOS d\r\n"
					+ "WHERE d.CODEMPRE = ? ";
			sentenciaprep = conexion.prepareStatement(sql);
			sentenciaprep.setInt(1, codemp);
			ResultSet rs2 = sentenciaprep.executeQuery();
			while(rs2.next()) {
				System.out.printf("COD-DEPARTAMENTO: %-3d NOMBRE: %-10s LOCALIDAD: %-10s %n",
						rs2.getInt(1),rs2.getString(2),rs2.getString(3));
				System.out.println("-----------------------------------------------------------------------------------");
				System.out.printf("  %-6s %-20s %-23s %-16s %n", "COD-EMP", "NOMBRE", "OFICIO", "NOMBRE ENCARGADO");
				System.out.println("  ------- -------------------- ----------------------- ----------------");
				sql = "SELECT ed.CODEMPLE, ed.NOMBRE, \r\n"
						+ "(SELECT o.NOMBRE FROM OFICIOS o WHERE ed.CODOFICIO = o.CODOFICIO), \r\n"
						+ "(SELECT ed2.NOMBRE FROM EMPLEADOS ed2 WHERE ed.CODENCARGADO = ed2.CODEMPLE)\r\n"
						+ "FROM EMPLEADOS ed\r\n"
						+ "WHERE ed.CODDEPART = ? ";
				sentenciaprep = conexion.prepareStatement(sql);
				sentenciaprep.setInt(1, rs2.getInt(1));
				ResultSet rs3 = sentenciaprep.executeQuery();
				while (rs3.next()) {
					String nencg ;
					if (rs3.getString(4) == null) {
						nencg="***";
					}else {
						nencg = rs3.getString(4);
					}
						
					System.out.printf("   %6s %-20s %-23s %-16s %n", rs3.getInt(1), rs3.getString(2), rs3.getString(3),nencg);
					
				}//FIN BUCLE EMPLE
				System.out.println("\n  Número de empleados del departamento: "+rs2.getInt(5));
				System.out.println("Nombre del jefe del departamento: "+rs2.getString(4));
			}//FIN BUCLE DEPART
			
		}//FIN BUCLE EMPRESA
		else {
			System.out.println("El codigo de empresa dado no existe");
		}
	}

}

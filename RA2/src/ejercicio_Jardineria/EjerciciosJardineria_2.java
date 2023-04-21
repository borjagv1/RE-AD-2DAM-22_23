package ejercicio_Jardineria;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Types;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EjerciciosJardineria_2 {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// int ccli = 19;

//		ejercicio1("1Marcos1", "1Magaña1", "1Perez", "3897", "marcos@jardineria.es", "TAL-ES", 2, "Director General");
//		ejercicio2(3);
//		ejercicio3(); //falta el delete pero no lo hago para no tener que estar volviendo a meter los datos en la tabla,
//						 //la sentencia delete se hace igual que una select pero cambiando un poco la sintaxis
//		ejercicio4();
//		ejercicio5();
//		ejercicio6();
		ejercicio7();
//		ejercicio8();

	}

	private static void ejercicio8() { //EJECUTAR SCRIPT "NUEVOSEMPLEADOS.sql" CON PROCESSBUILDER COMO EN LA ACTIVAD_5 DEL LIBRO 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexion;
		
		//lanzarScriptTabla(); Corregido M.JESUS.
		
		try {
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA", "JARDINERIA");

			conexion.setAutoCommit(false);
			// CODEMPLE, JEFE Y OFICINA
			String sql = "SELECT CODIGOEMPLEADO, CODIGOJEFE, CODIGOOFICINA, EXTENSION, EMAIL, PUESTO, "
					+ "NOMBRE, APELLIDO1, APELLIDO2 FROM NUEVOSEMPLEADOS";
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery(sql);
			while (rs.next()) {

				StringBuilder sb = new StringBuilder();

				sql = "SELECT CODIGOEMPLEADO, CODIGOJEFE, CODIGOOFICINA, EXTENSION, EMAIL, PUESTO, \r\n"
						+ "NOMBRE, APELLIDO1, APELLIDO2 FROM EMPLEADOS WHERE CODIGOEMPLEADO = " + rs.getInt(1);
				sentencia = conexion.createStatement();
				ResultSet resul = sentencia.executeQuery(sql);
				boolean b1 = false;
				// hacer select en empleados para comprobar si existen
				if (resul.next()) {
					System.out.println("Existe empleado");// AQUI TOCA HACER UN UPDATE Y SI NO ES UN INSERT
					b1 = true;
				}
				boolean b2 = true;
				sql = "SELECT CODIGOEMPLEADO FROM EMPLEADOS WHERE CODIGOEMPLEADO = " + rs.getInt(2);
				sentencia = conexion.createStatement();
				resul = sentencia.executeQuery(sql);
				// hacer select en empleados para comprobar si existen

				if (!resul.next()) {
					System.out.println("No se inserta ni actualiza");
					b2 = false;
				}
				boolean b3 = true;
				sql = "SELECT * FROM OFICINAS WHERE CODIGOOFICINA = " + rs.getString(3);
				sentencia = conexion.createStatement();
				resul = sentencia.executeQuery(sql);
				// hacer select en empleados para comprobar si existen
				if (!resul.next()) {
					System.out.println("No se inserta ni actualiza");
					b3 = false;
				}
				//////////////////////////////////////////

				sql = "SELECT CODIGOEMPLEADO FROM EMPLEADOS WHERE CODIGOEMPLEADO = " + rs.getInt(2);
				sentencia = conexion.createStatement();
				ResultSet rs2 = sentencia.executeQuery(sql);
				// hacer select en empleados para comprobar si existen
				if (!rs2.next()) {
					System.out.println("No se inserta ni actualiza");
				}

				sql = "SELECT * FROM OFICINAS WHERE CODIGOOFICINA = " + rs.getString(3);
				sentencia = conexion.createStatement();
				rs2 = sentencia.executeQuery(sql);
				// hacer select en empleados para comprobar si existen
				if (!rs2.next()) {
					System.out.println("No se inserta ni actualiza");
				}

				if (b2 == true && b3 == true) {
					if (b1 == true) {
						sql = "INSERT INTO EMPLEADOS (CODIGOEMPLEADO, CODIGOJEFE, CODIGOOFICINA, EXTENSION, EMAIL, PUESTO, "
								+ "NOMBRE, APELLIDO1, APELLIDO2 FROM NUEVOSEMPLEADOS) VALUES(?,?,?,?,?,?,?,?,?)";
						PreparedStatement sentencia2 = conexion.prepareStatement(sql);
						sentencia2.setInt(1, rs.getInt(1));
						sentencia2.setString(2, rs.getString(2));
						sentencia2.setInt(3, rs.getInt(3));
						sentencia2.setString(4, rs.getString(4));
						sentencia2.setString(5, rs.getString(5));
						sentencia2.setString(6, rs.getString(6));
						sentencia2.setString(7, rs.getString(7));
						sentencia2.setString(8, rs.getString(8));
						sentencia2.setString(9, rs.getString(9));
						sentencia2.executeUpdate();

					} else {
						sql = "UPDATE EMPLEADOS SET (CODIGOEMPLEADO, NOMBRE, APELLIDO1, APELLIDO2, "
								+ "EXTENSION, EMAIL, CODIGOOFICINA, CODIGOJEFE, PUESTO) = (SELECT * "
								+ "FROM NUEVOSEMPLEADOS WHERE CODIGOEMPLEADO = ?) ";
						PreparedStatement sentencia2 = conexion.prepareStatement(sql);
						sentencia2.setInt(1, rs.getInt(1));
						sentencia2.executeUpdate();
					}
				}
			}

			conexion.setAutoCommit(true);
			conexion.close();
		} catch (SQLException e) {

			// System.out.println("LA COLUMNA YA EXISTE");
			e.printStackTrace();
		}
	}

	private static void ejercicio7() { //CORREGIDO M.J EXACTAMENTE ASÍ.
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexion;

		Statement sentencia;
		try {
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA", "JARDINERIA");
			String sql = "SELECT CODIGOCLIENTE FROM CLIENTES";
			sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			while (resul.next()) {
				ejercicio2(resul.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void ejercicio6() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO", "EJEMPLO");

			String sql = "CREATE OR REPLACE\r\n" + "FUNCTION veroficina( cod VARCHAR2, ciudad OUT VARCHAR2 ,\r\n"
					+ "pais OUT VARCHAR2, region OUT VARCHAR2 , direcc OUT VARCHAR2)\r\n" + "RETURN NUMBER\r\n"
					+ "AS\r\n" + "cuenta NUMBER:=-1;\r\n" + "BEGIN\r\n"
					+ "SELECT COUNT(*) INTO cuenta FROM empleados WHERE codigooficina = cod;\r\n"
					+ "IF (cuenta=0) THEN\r\n" + "ciudad :='NO EXISTE OFICINA';\r\n" + "pais :='NO EXISTE OFICINA';\r\n"
					+ "ELSE\r\n" + "SELECT ciudad, pais, region, lineadireccion1\r\n"
					+ "INTO ciudad, pais, region, direcc\r\n" + "FROM oficinas WHERE codigooficina = cod;\r\n"
					+ "END IF;\r\n" + "RETURN CUENTA;\r\n" + "END;";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sql);
			sql = "SELECT CODIGOOFICINA FROM OFICINAS";
			sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			System.out.println(
					"COD OFICINA CIUDAD           PAIS              REGION            DIRECCION1       NUM EMPLES");
			System.out.println(
					"----------- ---------------- ----------------- ----------------- ---------------- ----------");
			while (resul.next()) {
				sql = "{? = call veroficina(?,?,?,?,?)}";
				// sentencia = conexion.createStatement();
				CallableStatement llamada = conexion.prepareCall(sql);

				llamada.registerOutParameter(1, Types.VARCHAR);
				llamada.setString(2, resul.getString(1));
				llamada.registerOutParameter(3, Types.VARCHAR);
				llamada.registerOutParameter(4, Types.VARCHAR);
				llamada.registerOutParameter(5, Types.VARCHAR);
				llamada.registerOutParameter(6, Types.VARCHAR);

				llamada.executeUpdate(); // ejecutar el procedimiento
				System.out.printf("%-10s %-15s %-10s %-20s %-30s %-10s %n", resul.getString(1), llamada.getString(3),
						llamada.getString(4), llamada.getString(5), llamada.getString(6), llamada.getString(1));

			}
			resul.close();
		} catch (SQLException e) {

			// System.out.println("LA COLUMNA YA EXISTE");
			e.printStackTrace();
		}

	}

	private static void ejercicio5() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO", "EJEMPLO");
			try {
				String sql = "ALTER TABLE PRODUCTOS DROP COLUMN STOCKACTUALIZADO";
				Statement sentencia = conexion.createStatement();
				sentencia.execute(sql);
			} catch (SQLSyntaxErrorException e) {
				System.out.println("La columna no existe");
			}

			String sql = "ALTER TABLE PRODUCTOS ADD STOCKACTUALIZADO NUMBER";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sql);

			sql = "UPDATE PRODUCTOS p SET STOCKACTUALIZADO=((p.cantidadenstock - (\r\n"
					+ "    SELECT nvl(SUM(dp.cantidad),0)\r\n" + "    FROM DETALLEPEDIDOS dp\r\n"
					+ "    WHERE p.codigoproducto=dp.codigoproducto)))";
			// System.out.println(sql);
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql);
			sql = "SELECT p.codigoproducto, p.stockactualizado, p.cantidadenstock"
					+ " FROM PRODUCTOS p WHERE p.stockactualizado < 5";
			sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			System.out.println("COD-PROD    STOCK ACTUALIZADO  CANTIDADENSTOCK");
			System.out.println("========    =================  ===============");
			while (resul.next()) {

				System.out.printf("%-15s %-20d %-10d %n", resul.getString(1), resul.getInt(2), resul.getInt(3));

			}
			// System.out.println("Filas afectadas: "+filas);
		} catch (SQLException e) {

			// System.out.println("LA COLUMNA YA EXISTE");
			e.printStackTrace();
		}

	}

	private static void ejercicio4() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO", "EJEMPLO");
			try {
				String sql = "ALTER TABLE EMPLEADOS DROP COLUMN NUMCLIENTES";
				Statement sentencia = conexion.createStatement();
				sentencia.execute(sql);
			} catch (SQLSyntaxErrorException e) {
				System.out.println("La columna no existe");
			}

			String sql = "ALTER TABLE EMPLEADOS ADD NUMCLIENTES NUMBER";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sql);
			sql = "UPDATE EMPLEADOS e SET NUMCLIENTES=(\r\n" + "SELECT COUNT(codigoempleadorepventas)\r\n"
					+ "FROM CLIENTES c\r\n" + "WHERE c.codigoempleadorepventas = e.codigoempleado)";
			sentencia = conexion.createStatement();
			int filas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filas);
		} catch (SQLException e) {

			// System.out.println("LA COLUMNA YA EXISTE");
			e.printStackTrace();
		}

	}

	private static void ejercicio3() { //Este ejercicio corregido por M-Jesús está OK
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO",
					"EJEMPLO");
			try {
				String sql = "DROP TABLE CLIENTESSINPEDIDO cascade constraints";
				Statement sentencia = conexion.createStatement();
				sentencia.execute(sql);
			} catch (SQLSyntaxErrorException e) {
				System.out.println("No existe la tabla");
			}

			String sql = "CREATE TABLE CLIENTESSINPEDIDO(\r\n" 
					+ "  CodigoCliente number(6) NOT NULL,\r\n"
					+ "  NombreCliente varchar2(50) NOT NULL,\r\n" 
					+ "  NombreContacto varchar2(30) DEFAULT NULL,\r\n"
					+ "  ApellidoContacto varchar2(30) DEFAULT NULL,\r\n" 
					+ "  Telefono varchar2(15) NOT NULL,\r\n"
					+ "  Fax varchar2(15) NOT NULL,\r\n" 
					+ "  LineaDireccion1 varchar2(50) NOT NULL,\r\n"
					+ "  LineaDireccion2 varchar2(50) DEFAULT NULL,\r\n" 
					+ "  Ciudad varchar2(50) NOT NULL,\r\n"
					+ "  Region varchar2(50) DEFAULT NULL,\r\n" 
					+ "  Pais varchar2(50) DEFAULT NULL,\r\n"
					+ "  CodigoPostal varchar2(10) DEFAULT NULL,\r\n"
					+ "  CodigoEmpleadoRepVentas number(6) DEFAULT NULL,\r\n"
					+ "  LimiteCredito number(15,2) DEFAULT NULL,\r\n" 
					+ "  PRIMARY KEY (CodigoCliente),\r\n"
					+ "  CONSTRAINT Clientessinpedidos_EmpleadosFK FOREIGN KEY (CodigoEmpleadoRepVentas) "
					+ "REFERENCES Empleados (CodigoEmpleado)\r\n" + ")";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sql);

			insertarEnCLIENTESSINPEDIDO();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void insertarEnCLIENTESSINPEDIDO() { //Corregido M.J
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO",
					"EJEMPLO");
//
//			String sql = "INSERT INTO CLIENTESSINPEDIDO" // se podría hacer con un NOT IN
//					+ " SELECT * FROM CLIENTES c WHERE (SELECT COUNT(*) FROM PEDIDOS p WHERE p.CODIGOCLIENTE = c.CODIGOCLIENTE)=0"; 
//			
			//corregido M.JESUS
			String sql = "INSERT INTO CLIENTESSINPEDIDO SELECT * FROM  CLIENTES WHERE CODIGOCLIENTE NOT IN (SELECT DISTINCT CODIGOCLIENTE FROM PEDIDOS ) ORDER BY 1)";																											// hacer
																																	
			Statement sentencia = conexion.createStatement();
			int filas = sentencia.executeUpdate(sql);
//			sql = "DELETE FROM CLIENTES c WHERE (SELECT COUNT(*) FROM PEDIDOS p WHERE p.CODIGOCLIENTE = c.CODIGOCLIENTE)=0"; // se // podría hacer con un NOT IN
			
			//corregido M.JESUS
			sql = "Delete Clientes where CODIGOCLIENTE IN \r\n"
					+ "+(Select CODIGOCLIENTE From CLIENTESSINPEDIDO)";		
			
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filas);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void ejercicio2(int ccli) {
		try {

			DameCodCli(ccli);
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");

			String sql = "SELECT distinct ped.CODIGOPEDIDO, ped.FECHAPEDIDO, ped.ESTADO " + "FROM PEDIDOS ped \r\n"
					+ "WHERE ped.CODIGOCLIENTE=" + ccli;
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			/////////////////////
			String europeanDatePattern = "dd/MM/yyyy";
			DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);

			////////////////////////
			while (resul.next()) {
				System.out.printf("COD-PEDIDO: %-7d FECHA PEDIDO: %-11s ESTADO DEL PEDIDO: %-11s %n", resul.getInt(1),
						resul.getDate(2), resul.getString(3));

				sql = "SELECT DISTINCT dp.NUMEROLINEA, dp.CODIGOPRODUCTO, (SELECT NOMBRE FROM PRODUCTOS WHERE dp.codigoproducto = productos.codigoproducto),\r\n"
						+ "dp.CANTIDAD, dp.PRECIOUNIDAD, (dp.CANTIDAD * dp.PRECIOUNIDAD)\r\n"
						+ "FROM PEDIDOS ped LEFT JOIN DETALLEPEDIDOS dp ON ped.codigopedido = dp.codigopedido\r\n"
						+ "WHERE ped.CODIGOCLIENTE=" + ccli + " AND ped.CODIGOPEDIDO=" + resul.getInt(1)
						+ "ORDER BY dp.NUMEROLINEA";
				sentencia = conexion.createStatement();
				ResultSet rs = sentencia.executeQuery(sql);

				if (rs.next()) {
					if (rs.getInt(1) == 0) {
						System.out.println("\t\tNO TIENE PRODUCTOS ESTE PEDIDO...");
					} else {
						rs = sentencia.executeQuery(sql);
						System.out.printf("\t%-9s %-15s %-40s %-8s %-11s %-10s %n", "NUM-LINEA", "COD-PROD",
								"NOMBRE PRODUCTO", "CANTIDAD", "PREC-UNID", "IMPORTE");
						System.out.printf("\t%-9s %-15s %-40s %-8s %-11s %-10s %n", "---------", "---------------",
								"----------------------------------------", "--------", "-----------", "----------");
						while (rs.next()) {

							System.out.printf("\t%-9d %-15s %-40s %-8d %-11.2f %-8.2f %n", rs.getInt(1),
									rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getFloat(6));
						}
						System.out.printf("\t%-9s %-15s %-40s %-8s %-11s %-10s %n", "---------", "---------------",
								"----------------------------------------", "--------", "-----------", "----------");
						sql = "SELECT SUM(dp.CANTIDAD), SUM(dp.PRECIOUNIDAD), SUM(dp.CANTIDAD * dp.PRECIOUNIDAD)\r\n"
								+ "FROM PEDIDOS ped LEFT JOIN DETALLEPEDIDOS dp ON ped.codigopedido = dp.codigopedido\r\n"
								+ "WHERE ped.CODIGOCLIENTE=" + ccli + " AND ped.CODIGOPEDIDO=" + resul.getInt(1);
						// System.out.println(sql);
						sentencia = conexion.createStatement();
						rs = sentencia.executeQuery(sql);
						while (rs.next()) {
							System.out.printf("\t%-18s %50d       %-11.2f %-8.2f %n%n", "TOTALES POR PEDIDO",
									rs.getInt(1), rs.getFloat(2), rs.getFloat(3));
						}

					}
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int lecturaEntero(String mensaje, int min, int max) {
		int nu = 0;
		boolean bien = false;
		do {
			try {
				System.out.print(mensaje);
				nu = sc.nextInt();
				sc.nextLine();
				bien = true;
			} catch (InputMismatchException ex) {
				System.out.println("\tNO ES UN VALOR ENTERO VÁLIDO");
				bien = false;
				sc.nextLine();
			}
			if (nu < min || nu > max) {
				System.out.println("\t\tRANGO INCORRECTO");
			}
		} while (nu < min || bien == false || nu > max);

		return nu;
	}

	private static void DameCodCli(int ccli) throws SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA", "JARDINERIA");

		String sql = "SELECT NOMBRECLIENTE, LINEADIRECCION1 FROM CLIENTES WHERE CODIGOCLIENTE = " + ccli;
		Statement sentencia = conexion.createStatement();
		ResultSet resul = sentencia.executeQuery(sql);
		String nombre;
		String direccion;
		if (resul.next()) {
			nombre = resul.getString(1);
			direccion = resul.getString(2);
			sql = "SELECT COUNT(CODIGOCLIENTE) FROM PEDIDOS WHERE CODIGOCLIENTE = " + ccli;
			sentencia = conexion.createStatement();
			resul = sentencia.executeQuery(sql);

			while (resul.next()) {
				if (resul.getInt(1) == 0) {
					System.out.println("EL CLIENTE " + ccli + " NO TIENE PEDIDOS...");
				} else {
					System.out.printf(
							"COD-CLIENTE: %-15d NOMBRE: %-14s %n" + "DIRECCIÓN1: %-15s Número de pedidos: %-7d %n",
							ccli, nombre, direccion, resul.getInt(1));
					System.out.println("------------------------------------------------"
							+ "------------------------------------------------------");
				}

			}
		}

	}

	private static void ejercicio1(String nombre, String ape1, String ape2, String extension, String email,
			String CodigoOficina, int CodigoJefe, String Puesto) {

		// construir orden INSERT
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conexion;
		try {
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO", "EJEMPLO");
			String sql = "INSERT INTO empleados VALUES "

					+ "( ?, ?, ? , ?, ?, ?, ? , ?, ?)";
			// El código de empleado será una unidad más,
			// al código de empleado máximo almacenado en la tabla.
			int codEmpleado = DameCodEmpleado();
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, codEmpleado);
			sentencia.setString(2, nombre);
			sentencia.setString(3, ape1);
			sentencia.setString(4, ape2);
			sentencia.setString(5, extension);
			sentencia.setString(6, email);
			sentencia.setString(7, CodigoOficina);
			sentencia.setInt(8, CodigoJefe);
			sentencia.setString(9, Puesto);

			// Al insertar comprobar que exista el código de oficina y el código de jefe.

			// Visualizar todos los mensajes de error que ocurran, e indicar si el registro

			// se ha insertado o no.

			try {
				sentencia.executeUpdate();
				System.out.println("Registro INSERTADO....");
			} catch (SQLException e) {

				if (e.getErrorCode() == 2291) { // error en fk

					if (e.getMessage().contains("EMPLEADOS_OFICINASFK")) {
						System.out.println("ERROR. EL codigo de oficina no existe");
					} else {
						System.out.println("ERROR. EL codigo de Jefe (cod empleado) no existe");
					}

				} else { // OTROS ERRORES
					System.out.println("HA OCURRIDO UNA EXCEPCIÓN:");
					System.out.println("Mensaje:    " + e.getMessage());
					System.out.println("SQL estado: " + e.getSQLState());
					System.out.println("Cód error:  " + e.getErrorCode());
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}// ejercicio1

	private static int DameCodEmpleado() throws SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO", "EJEMPLO");

		String sql = "SELECT MAX(CodigoEmpleado) + 1 FROM EMPLEADOS";
		Statement sentencia = conexion.createStatement();
		ResultSet resul = sentencia.executeQuery(sql);
		int n = 0;

		if (resul.next()) {
			n = resul.getInt(1);
		}

		return n;

	}
}
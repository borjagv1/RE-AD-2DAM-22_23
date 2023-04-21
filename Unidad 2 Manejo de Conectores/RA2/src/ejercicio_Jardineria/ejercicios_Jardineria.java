package ejercicio_Jardineria;
//FALTA EJERCICIOS A PARTIR DEL 4

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;

public class ejercicios_Jardineria {

	static Connection conexion;
@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			System.out.println("ORACLE");
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Establecemos la conexion con la BD MYSQL
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA", "JARDINERIA");

//			ejercicio1();

//			int codigoCliente = 14;
//			ejercicio2(codigoCliente);

//			ejercicio3();

//			ejercicio4();

			// ejercicio puesto en clase 9
			/*
			 * 9. REALIZA UNA FUNCIÓN ALMACENADA QUE RECIBA UN CODIGO DE PEDIDO Y DEVUELVA
			 * EN UNA CADENA TODAS LAS LINEAS DE ESE PEDIDO, La información por cada línea
			 * es:
			 * 
			 * Num-linea, cod prod, nombre de producto, cantidad y pvp PARA SEPARAR LINEAS
			 * USAR CHR(18) Y CHR (13)
			 * 
			 * select d.numerolinea, d.codigoproducto, p.nombre, d.cantidad,
			 * (d.preciounidad*d.cantidad) AS PVP from detallepedidos d, productos p where
			 * d.codigoproducto = p.codigoproducto ORDER BY 3;
			 */
			String sql = "create or replace NONEDITIONABLE FUNCTION verpedidos (\r\n"
					+ "    cp IN NUMBER\r\n"
					+ ") RETURN VARCHAR2 AS\r\n"
					+ "\r\n"
					+ "    CURSOR c1 IS\r\n"
					+ "    SELECT\r\n"
					+ "        d.numerolinea,\r\n"
					+ "        d.codigoproducto,\r\n"
					+ "        p.nombre,\r\n"
					+ "        d.cantidad,\r\n"
					+ "        ( d.preciounidad * d.cantidad ) AS pvp\r\n"
					+ "    FROM\r\n"
					+ "        detallepedidos d,\r\n"
					+ "        productos      p\r\n"
					+ "    WHERE\r\n"
					+ "            d.codigoproducto = p.codigoproducto\r\n"
					+ "        AND d.codigopedido = cp\r\n"
					+ "    ORDER BY\r\n"
					+ "        1;\r\n"
					+ "\r\n"
					+ "    cad VARCHAR2(1000) := '';\r\n"
					+ "    sw  number := 0;\r\n"
					+ "BEGIN\r\n"
					+ "    FOR i IN c1 LOOP\r\n"
					+ "        sw := 1;\r\n"
					+ "        cad :=  cad\r\n"
					+ "               || i.numerolinea\r\n"
					+ "               || ', '\r\n"
					+ "               || i.codigoproducto\r\n"
					+ "               || ', '\r\n"
					+ "               || i.nombre\r\n"
					+ "               || ', '\r\n"
					+ "               || i.cantidad\r\n"
					+ "               || ', '\r\n"
					+ "               || i.pvp\r\n"
					+ "               || chr(13);\r\n"
					+ "\r\n"
					+ "    END LOOP;\r\n"
					+ "\r\n"
					+ "    IF ( sw = 0 ) THEN\r\n"
					+ "        cad := 'NO EXISTE O NO TIENE LINEA EL PEDIDO ' || cp;\r\n"
					+ "    END IF;\r\n"
					+ "    RETURN cad;\r\n"
					+ "END;";
			
			try {
				Statement sentencia = conexion.createStatement();
				int filas = 0;

				filas = sentencia.executeUpdate(sql);
				System.out.println("Filas afectadas: " + filas);

				sentencia.close(); // Cerrar Statement
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("C�d error : %s %n", e.getErrorCode());
			}
			String sql2 = "{? = call VERPEDIDOS(?) }"; // ORACLE
			
			//PREPARAMOS LA LLAMADA
			CallableStatement llamada = conexion.prepareCall(sql2);
			llamada.registerOutParameter(1, Types.VARCHAR);
			
			int codigopedido = 1;
			llamada.setInt(2, codigopedido); //param de entrada.
			llamada.executeUpdate(); //EJecutar proc
			
			System.out.println("Codigo pedido " + codigopedido);
			System.out.println(llamada.getString(1));

			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

	}// MAIN

	// MÉTODOS EJERCICIO 4

	private static void ejercicio4() {
		creaColumnaNumEmpleados();

		String sql = "select CODIGOEMPLEADO from empleados order by 1";
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);

			while (resul.next()) {
				int codemple = resul.getInt(1);
				sql = "UPDATE EMPLEADOS E SET (E.NUMCLIENTES) = (SELECT COUNT(C.CODIGOEMPLEADOREPVENTAS) \r\n"
						+ "FROM CLIENTES C, EMPLEADOS E\r\n" + "WHERE C.CODIGOEMPLEADOREPVENTAS = " + codemple + " AND"
						+ " e.codigoempleado = " + codemple + " ) " + "WHERE CODIGOEMPLEADO = " + codemple;
				Statement sentencia2 = conexion.createStatement();

				System.out.println(sql);

				int filas = sentencia2.executeUpdate(sql);
				// sentencia2.close();
			} // while 1

			sentencia.close(); // Cerrar Statement
			resul.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
		//////////////////////////////////////////////////////////////////////////////////////
	}

	private static void creaColumnaNumEmpleados() {
		// ALTER TABLE EMPLEADOS ADD (NUMCLIENTES NUMBER);

		String sql = "ALTER TABLE EMPLEADOS DROP COLUMN NUMCLIENTES";

		try {
			Statement sentencia = conexion.createStatement();

			int filas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filas);
			sentencia.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		} // TRYCATCH
			/////////////////////////////
		sql = "ALTER TABLE EMPLEADOS ADD (NUMCLIENTES NUMBER)";

		try {
			Statement sentencia = conexion.createStatement();

			int filas = sentencia.executeUpdate(sql);
			System.out.println("Filas afectadas: " + filas);
			sentencia.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		} // TRYCATCH
	}

	// MÉTODOS EJERCICIO 3

	private static void ejercicio3() {
		////////////////////////////////////////////////////////////////////////////////
		/*
		 * 3. En la tabla CLIENTES hay clientes que no tienen pedidos. Se desea hacer un
		 * método para eliminar los clientes que no han realizado pedidos. Los clientes
		 * borrados se deben de añadir a una tabla que el método tiene que crear. Esta
		 * tabla se llamará CLIENTESSINPEDIDO, tendrá la misma estructura que la tabla
		 * CLIENTES. El método a crear deberá crear la tabla CLIENTESSINPEDIDO, añadir a
		 * esa tabla los clientes sin pedidos, y eliminar de la tabla CLIENTES esos
		 * mismos clientes. El método se podrá ejecutar tantas veces como se desea. Hay
		 * que controlar todos los errores posibles.
		 * 
		 * 
		 * select codigocliente, nombrecliente from clientes where codigocliente NOT IN
		 * (SELECT DISTINCT CODIGOCLIENTE FROM PEDIDOS ) GROUP BY codigocliente,
		 * nombrecliente;
		 */
		crearTablaClientesSinPedido();

		String sql2 = "INSERT INTO ClientesSinPedido select * from clientes \r\n"
				+ "where codigocliente NOT IN (SELECT DISTINCT CODIGOCLIENTE FROM PEDIDOS) ORDER BY 1";

		try {
			Statement sentencia2 = conexion.createStatement();

			int filas = sentencia2.executeUpdate(sql2);
			System.out.println("Filas afectadas: " + filas);
			sentencia2.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		} // TRYCATCH

		/////////////////////////////////////////////////////////////////////////////
		sql2 = "DELETE FROM CLIENTES WHERE CODIGOCLIENTE IN (SELECT CODIGOCLIENTE FROM CLIENTESSINPEDIDO)";

		try {
			Statement sentencia = conexion.createStatement();

			int filas = sentencia.executeUpdate(sql2);
			System.out.println("Filas afectadas: " + filas);
			sentencia.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		} // TRYCATCH

		////////////////////////////////////////////////////////////////////////////
	}

	private static void crearTablaClientesSinPedido() {
		String sql2 = "drop table JARDINERIA.CLIENTESSINPEDIDO cascade constraints";

		try {
			Statement sentencia2 = conexion.createStatement();

			int filas = sentencia2.executeUpdate(sql2);
			System.out.println("Filas afectadas: " + filas);
			sentencia2.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		} // TRYCATCH

		String sql = "CREATE TABLE ClientesSinPedido (\r\n" + "  CodigoCliente number(6) NOT NULL,\r\n"
				+ "  NombreCliente varchar2(50) NOT NULL,\r\n" + "  NombreContacto varchar2(30) DEFAULT NULL,\r\n"
				+ "  ApellidoContacto varchar2(30) DEFAULT NULL,\r\n" + "  Telefono varchar2(15) NOT NULL,\r\n"
				+ "  Fax varchar2(15) NOT NULL,\r\n" + "  LineaDireccion1 varchar2(50) NOT NULL,\r\n"
				+ "  LineaDireccion2 varchar2(50) DEFAULT NULL,\r\n" + "  Ciudad varchar2(50) NOT NULL,\r\n"
				+ "  Region varchar2(50) DEFAULT NULL,\r\n" + "  Pais varchar2(50) DEFAULT NULL,\r\n"
				+ "  CodigoPostal varchar2(10) DEFAULT NULL,\r\n"
				+ "  CodigoEmpleadoRepVentas number(6) DEFAULT NULL,\r\n"
				+ "  LimiteCredito number(15,2) DEFAULT NULL,\r\n" + "  PRIMARY KEY (CodigoCliente),\r\n"
				+ "  CONSTRAINT ClientesSinPedidos_EmpleadosFK FOREIGN KEY (CodigoEmpleadoRepVentas) REFERENCES Empleados (CodigoEmpleado)\r\n"
				+ ") ";
		Statement sentencia;
		try {
			sentencia = conexion.createStatement();

			sentencia.execute(sql);
			sentencia.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		} // TRYCATCH
	}// Clientes sin pedido

	// MÉTODOS EJERCICIO 2

	/*
	 * 2. Realiza un método java que reciba un código de cliente, y visualice los
	 * pedidos de ese cliente, con los totales por cada pedido, y el pedido con
	 * total importe máximo, y el producto más comprado. Visualizar cada pedido
	 * ordenado por número de línea. Visualizar si el cliente no tiene pedidos o si
	 * el cliente no existe en la BD. La salida a visualizar es la siguiente:
	 */

	private static void ejercicio2(int codigoCliente) {
		try {
			System.out.println("ORACLE");
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");
			// VARIABLES

			boolean b1 = existeCliente(codigoCliente);

			// STATEMENT Y RESULTSET
			if (b1) {
				int numeroPedidos = dameNumeroPedidos(codigoCliente);
				String sql = "SELECT CODIGOCLIENTE, NOMBRECLIENTE, LINEADIRECCION1 FROM CLIENTES WHERE CODIGOCLIENTE = ?";
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, codigoCliente);

				try {
					ResultSet resul = sentencia.executeQuery();
					int cantidadtot = 0, codigopedido = 0, cantidadmax = 0;
					String fechapedido = "", codigoproducto = "", nombreproducto = "";
					float preciounidadTot = 0, importetotal = 0, importemax = 0;

					while (resul.next()) {
						System.out.printf(
								"CODIGO CLIENTE: %-15d NOMBRECLIENTE: %-14s%n"
										+ "DIRECCION1: %-19s NUMEROPEDIDOS: %-7d%n",
								resul.getInt(1), resul.getString(2), resul.getString(3), numeroPedidos);
						System.out.println(
								"--------------------------------------------------------------------------------------------------------------------");
						// linea de pedidos
						sql = "SELECT CODIGOPEDIDO, FECHAPEDIDO, ESTADO FROM PEDIDOS WHERE CODIGOCLIENTE = ?";
						PreparedStatement sentencia2 = conexion.prepareStatement(sql);
						sentencia2.setInt(1, codigoCliente);

						ResultSet resul2 = sentencia2.executeQuery();
						while (resul2.next()) {
							//////////// FORMATO DE FECHA QUE RECOGEMOS DEL RESUL//////////////////
							SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
							String fecha = formato.format(resul2.getDate(2));
							//////////// FORMATO DE FECHA QUE RECOGEMOS DEL RESUL//////////////////
							System.out.printf("   CODIGO PEDIDO: %d\t FECHA PEDIDO: %s\t ESTADO DEL PEDIDO: %s%n",
									resul2.getInt(1), fecha, resul2.getString(3));

							// linea de datos de los pedidos

							// NUM-LINEA COD-PROD NOMBRE PRODUCTO CANTIDAD PREC-UNID IMPORTE
							sql = "SELECT D.NUMEROLINEA, D.CODIGOPRODUCTO, P.NOMBRE, D.CANTIDAD, D.PRECIOUNIDAD "
									+ "FROM DETALLEPEDIDOS D, PRODUCTOS P "
									+ "WHERE D.CODIGOPEDIDO = ? AND D.CODIGOPRODUCTO = P.CODIGOPRODUCTO "
									+ "ORDER BY D.NUMEROLINEA";
							PreparedStatement sentencia3 = conexion.prepareStatement(sql);
							sentencia3.setInt(1, resul2.getInt(1));

							ResultSet resul3 = sentencia3.executeQuery();
							System.out.println(
									"\tNUM-LINEA COD-PROD NOMBRE PRODUCTO                           CANTIDAD PREC-UNID   IMPORTE");
							System.out.println(
									"\t--------- -------- ----------------------------------------- -------- ----------- ----------");

							while (resul3.next()) {
								// NUM-LINEA COD-PROD NOMBRE PRODUCTO CANTIDAD PREC-UNID IMPORTE
								float importe = (resul3.getInt(4) * resul3.getInt(5));

								System.out.printf("\t%9d %8s %41s %8d %11.2f %10.2f%n", resul3.getInt(1),
										resul3.getString(2), resul3.getString(3), resul3.getInt(4), resul3.getFloat(5),
										importe);

								cantidadtot = resul3.getInt(4) + cantidadtot;
								preciounidadTot = resul3.getFloat(5) + preciounidadTot;
								importetotal = importe + importetotal;

								if (importetotal > importemax) {
									importemax = importetotal;
									codigopedido = resul2.getInt(1);
									fechapedido = fecha;
								}
								if (resul3.getInt(4) > cantidadmax) {
									cantidadmax = resul3.getInt(4);
									codigoproducto = resul3.getString(2);
									nombreproducto = resul3.getString(3);
								}
							} // while 3

							System.out.println(
									"\t--------- -------- ----------------------------------------- -------- ----------- ----------");
							System.out.printf("\tTOTALES POR PEDIDO: %49d %11.2f %10.2f\n\n", cantidadtot,
									preciounidadTot, importetotal);

						} // while 2

						// TOTALES Y MÁXIMOS
						System.out.println(
								"--------------------------------------------------------------------------------------------------------------------");
						System.out.println("COD de PEDIDO y FECHA PEDIDO CON TOTAL IMPORTE MÁXIMO: " + codigopedido
								+ ", " + fechapedido);
						System.out
								.println("COD PRODUCTO y NOMBRE PERODUCTO, del producto más comprado(Cantidad Máxima): "
										+ codigoproducto + ", " + nombreproducto);

					} // while 1

				} catch (SQLException e) {
					// e.printStackTrace();
					System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
					System.out.printf("Mensaje   : %s %n", e.getMessage());
					System.out.printf("SQL estado: %s %n", e.getSQLState());
					System.out.printf("Cód error : %s %n", e.getErrorCode());
				}

				sentencia.close(); // Cerrar Statement
			} // FIN IF
			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH
	}

	private static boolean existeCliente(int codigoCliente) {
		boolean existe = false;

		try {

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");
			// Preparamos la consulta
			String sql = "SELECT CODIGOCLIENTE FROM CLIENTES WHERE CODIGOCLIENTE = ?"; // '" + codigooficina + "'"
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, codigoCliente);

			try {
				ResultSet resul = sentencia.executeQuery();

				// Recorremos el resultado para visualizar cada fila
				// Se hace un bucle mientras haya registros y se van visualizando
				if (resul.next()) {
					existe = true;
				} else {
					System.out.println("\tNO EXISTE EL CLIENTE: " + codigoCliente);
					existe = false;
				}
				resul.close(); // Cerrar ResultSet
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("Cód error : %s %n", e.getErrorCode());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

		return existe;

	}// EXISTE cod CLiente

	private static int dameNumeroPedidos(int codigoCliente) {
		int numeroPedidos = 0;
		try {
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");
			// Preparamos la consulta
			String sql = "SELECT COUNT(CODIGOPEDIDO) FROM PEDIDOS WHERE CODIGOCLIENTE = ?"; // '" + codigooficina + "'"
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoCliente);

			try {
				ResultSet resul = sentencia.executeQuery();
				while (resul.next()) {
					numeroPedidos = resul.getInt(1);
				}
				if (numeroPedidos == 0) {
					System.out.println("EL CLIENTE NO TIENE PEDIDOS");
				}
				resul.close(); // Cerrar ResultSet
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("Cód error : %s %n", e.getErrorCode());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

		return numeroPedidos;

	}

	// MÉTODOS EJERCICIO 1

	/*
	 * 1. Realiza un método java que reciba los datos de un empleado y los inserte
	 * en la tabla EMPLEADOS. Los datos a recibir son el nombre, apellido1,
	 * apellido2, extensión, email, codigooficina, codigojefe, y puesto. El código
	 * de empleado será una unidad más, al código de empleado máximo almacenado en
	 * la tabla. Al insertar comprobar que exista el código de oficina y el código
	 * de jefe. Visualizar todos los mensajes de error que ocurran, e indicar si el
	 * registro se ha insertado o no.
	 */
	private static void ejercicio1() {
		try {

			System.out.println("ORACLE");

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");
			// VARIABLES
			String codigooficina = "PAR-FR";
			int codigojefe = 14;

			boolean b1 = existeCodigoOficina(codigooficina);
			boolean b2 = existeCodigoJefe(codigojefe);
			int codempleado = dameEmpleado();

			if (b1 && b2) { // true
				// STATEMENT Y RESULTSET
				String sql = "INSERT INTO EMPLEADOS VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				sentencia.setInt(1, codempleado);
				sentencia.setString(2, "BORJA");
				sentencia.setString(3, "GUERRA");
				sentencia.setString(4, "VALENCIA");
				sentencia.setString(5, "1234");
				sentencia.setString(6, "a_a@aaaa.com");
				sentencia.setString(7, codigooficina);
				sentencia.setInt(8, codigojefe);
				sentencia.setString(9, "ANALISTA DE DATOS");

				try {
					int filas = sentencia.executeUpdate();
					System.out.println("Filas afectadas: " + filas);

				} catch (SQLException e) {
					// e.printStackTrace();
					System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
					System.out.printf("Mensaje   : %s %n", e.getMessage());
					System.out.printf("SQL estado: %s %n", e.getSQLState());
					System.out.printf("Cód error : %s %n", e.getErrorCode());
				}

				sentencia.close(); // Cerrar Statement
			} // FIN IF

			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH
	}// EJERCICIO1

	private static int dameEmpleado() {
		int codemple = 0;
		try {

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");
			// Preparamos la consulta
			String sql = "SELECT MAX(CODIGOEMPLEADO) FROM EMPLEADOS"; // '" + codigooficina + "'"
			Statement sentencia = conexion.createStatement();

			try {
				ResultSet resul = sentencia.executeQuery(sql);
				while (resul.next()) {
					codemple = resul.getInt(1);
				}
				codemple = codemple + 1;
				resul.close(); // Cerrar ResultSet
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("Cód error : %s %n", e.getErrorCode());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

		return codemple;

	}

	private static boolean existeCodigoJefe(int codigojefe) {
		boolean existe = false;

		try {

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");
			// Preparamos la consulta
			String sql = "SELECT CODIGOEMPLEADO FROM EMPLEADOS WHERE CODIGOEMPLEADO = ?"; // '" + codigooficina + "'"
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, codigojefe);

			try {
				ResultSet resul = sentencia.executeQuery();

				// Recorremos el resultado para visualizar cada fila
				// Se hace un bucle mientras haya registros y se van visualizando
				if (resul.next()) {
					existe = true;
				} else {
					System.out.println("\tNO EXISTE EL CODJEFE EN LOS EMPLEADOS: " + codigojefe);
					existe = false;
				}
				resul.close(); // Cerrar ResultSet
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("Cód error : %s %n", e.getErrorCode());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

		return existe;

	}// EXISTE cod jefe

	private static boolean existeCodigoOficina(String codigooficina) {
		boolean existe = false;

		try {

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "JARDINERIA",
					"JARDINERIA");
			// Preparamos la consulta
			String sql = "SELECT CODIGOOFICINA FROM OFICINAS WHERE CODIGOOFICINA = ?"; // '" + codigooficina + "'"
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setString(1, codigooficina);

			try {
				ResultSet resul = sentencia.executeQuery();

				// Recorremos el resultado para visualizar cada fila
				// Se hace un bucle mientras haya registros y se van visualizando
				if (resul.next()) {
					existe = true;
				} else {
					System.out.println("\tNO EXISTE EL COD_OFICINA: " + codigooficina);
					existe = false;
				}
				resul.close(); // Cerrar ResultSet
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("Cód error : %s %n", e.getErrorCode());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

		return existe;
	}// EXISTECOD_OFICINA

}// CLASS

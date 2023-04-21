package ejerciciosLibro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Activadad_6 {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("INSERCIÓN MYSQL: ");

		inserciónMySQL();

		System.out.println("INSERCIÓN ORACLE: ");

		insercionOracle();

	}// MAIN

	private static void insercionOracle() {
		try {

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD2",
					"UNIDAD2");

			// conexion.setAutoCommit(false);

			int idventa, idcliente, idproducto, cantidad;

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // dd/MM/yyyy //yyyy/MM/dd
			LocalDate f = LocalDate.now();
			String fecha = f.format(formato);

			System.out.println("LECTURA DE DATOS: ");

			idventa = leerEntero("Introduce idventa: ", 0, 9999);

			while (idventa != 0) {

				idcliente = leerEntero("Introduce idcliente: ", 1, 9999);
				idproducto = leerEntero("Introduce idproducto: ", 1, 9999);
				cantidad = leerEntero("Introduce cantidad: ", 1, 9999);

				if (existeidVentaOracle(idventa) == false && existeIdClienteOracle(idcliente) == true
						&& existeIdProductoOracle(idproducto) == true) {
					Locale.setDefault(Locale.UK); // Evita este error.
					// Column count doesnt't match value count at row 1
					String sql = "INSERT INTO ventas VALUES(?, ?, ?, ?, ?)";
					PreparedStatement sentencia = conexion.prepareStatement(sql);
					sentencia.setInt(1, idventa);
					sentencia.setString(2, fecha);
					sentencia.setInt(3, idcliente);
					sentencia.setInt(4, idproducto);
					sentencia.setInt(5, cantidad);

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
				} else {
					System.out.println("\tNO SE INSERTA");
				} // FIN IF

				idventa = leerEntero("Introduce idventa: ", 0, 9999);

			} // FIN WHILE

			// conexion.commit();
			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static boolean existeIdProductoOracle(int idproducto) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD2",
					"UNIDAD2");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT id FROM productos WHERE id = " + idproducto;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out.println("\tNO EXISTE EL ID PRODUCTO: " + idproducto);
				existe = false;
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static boolean existeIdClienteOracle(int idcliente) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD2",
					"UNIDAD2");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT id FROM clientes WHERE id = " + idcliente;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out.println("\tNO EXISTE EL ID CLIENTE: " + idcliente);
				existe = false;
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static boolean existeidVentaOracle(int idventa) {
		// en la tabla empleados
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD2",
					"UNIDAD2");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT idventa FROM ventas WHERE idventa = " + idventa;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				System.out.println("\tYA EXISTE EL IDVENTA: " + idventa);
				existe = true;
			} else {
				existe = false;
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static void inserciónMySQL() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");// Cargar el driver
			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			// conexion.setAutoCommit(false);

			int idventa, idcliente, idproducto, cantidad;

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // dd/MM/yyyy
			LocalDate f = LocalDate.now();
			String fecha = f.format(formato);

			System.out.println("LECTURA DE DATOS: ");

			idventa = leerEntero("Introduce idventa: ", 0, 9999);

			while (idventa != 0) {

				idcliente = leerEntero("Introduce idcliente: ", 1, 9999);
				idproducto = leerEntero("Introduce idproducto: ", 1, 9999);
				cantidad = leerEntero("Introduce cantidad: ", 1, 9999);

				if (existeidVenta(idventa) == false && existeIdCliente(idcliente) == true
						&& existeIdProducto(idproducto) == true) {
					Locale.setDefault(Locale.UK); // Evita este error.
					// Column count doesnt't match value count at row 1
					String sql = "INSERT INTO ventas VALUES(?, ?, ?, ?, ?)";
					PreparedStatement sentencia = conexion.prepareStatement(sql);
					sentencia.setInt(1, idventa);
					sentencia.setString(2, fecha);
					sentencia.setInt(3, idcliente);
					sentencia.setInt(4, idproducto);
					sentencia.setInt(5, cantidad);

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
				} else {
					System.out.println("\tNO SE INSERTA");
				} // FIN IF

				idventa = leerEntero("Introduce idventa: ", 0, 9999);

			} // FIN WHILE

			// conexion.commit();
			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static boolean existeIdProducto(int idproducto) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT id FROM productos WHERE id = " + idproducto;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out.println("\tNO EXISTE EL ID PRODUCTO: " + idproducto);
				existe = false;
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static boolean existeIdCliente(int idcliente) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT id FROM clientes WHERE id = " + idcliente;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out.println("\tNO EXISTE EL ID CLIENTE: " + idcliente);
				existe = false;
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static boolean existeidVenta(int idventa) {
		// en la tabla empleados
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT idventa FROM ventas WHERE idventa = " + idventa;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				System.out.println("\tYA EXISTE EL IDVENTA: " + idventa);
				existe = true;
			} else {
				existe = false;
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static int leerEntero(String mensaje, int min, int max) {
		boolean salir = false;
		int numero = 0;

		do {
			try {
				System.out.print(mensaje);
				numero = sc.nextInt();
				sc.nextLine();
				while (numero < min || numero > max) {
					System.out.print("\tSuperado límite (> " + min + " y < " + max + ")");
					System.out.print("\n\tOtra vez: ");

					numero = sc.nextInt();
					sc.nextLine();
					System.out.println();
				}
				salir = true;

			} catch (InputMismatchException exc) {
				sc.nextLine();
				System.out.print("\n\tIncorrecto, escríbelo de nuevo: ");
			}
		} while (!salir);

		return numero;
	}// LecturaEntero

}// FIN ACTIVIDAD 6

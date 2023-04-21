package ejercicios_Baloncesto_Ventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/*
 * FALTA ESTA PARTE DE VENTAS DEL PDF BALONCESTO_VENTAS
 */
public class Insertar_TablaVentas {
	static Connection conexión;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexión = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			int lineasVenta, cantidad = 0, stockactual = 0, stockminimo;
			String idCliente, idventa, idProducto = "";
			
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // dd/MM/yyyy //yyyy/MM/dd
			LocalDate f = LocalDate.now();
			String fecha = f.format(formato);

			System.out.println("LECTURA DE DATOS");

			idventa = leerCadena("Introduce idventa: ");

			// COMPROBACIONES
			while (!idventa.equals("0")) {
				idCliente = leerCadena("Introduce idCliente: ");
				// idproducto = leerEntero("Introduce idproducto", 0, 9999);
				if (existeIdVenta(idventa) == false && existeIdCliente(idCliente) == true) {
					Locale.setDefault(Locale.UK); // Evita este error. Column count doesn't match value count at row 1
					lineasVenta = leerEntero("Introduce Lineas Venta: ", 0, 9999);
					if (lineasVenta < 1 && lineasVenta > 5) {
						System.out.println("NÚMERO DE LÍNEAS INCORRECTO (valor de 1 a 5)...");
						System.out.println("FIN PROCESO...");
					} else {
						// HASTA AQUÍ OK, INSERTO VENTA

						insertarVenta(idCliente, idventa, fecha);

						// BUCLE FOR PARA INSERTAR CADA LINEA DE VENTA HASTA EL Nº DE LINEAS DADO
						for (int i = 1; i <= lineasVenta; i++) {
							System.out.println("Linea: " + i);

							boolean datoCorrecto = false; // Para salir de los bucles
							boolean dato2Correcto = false;
							while (!datoCorrecto || !dato2Correcto) {

								idProducto = leerCadena("Introduce idProducto: ");
								while (!datoCorrecto) {
									if (existeIdProducto(idProducto)) {

										datoCorrecto = true;
									} else {
										System.out.println("Introduce de nuevo: ");
										idProducto = leerCadena("Introduce idProducto: ");

									}
								}

								// SI EXISTE EL PRODUCTO AHORA HACEMOS EL CONTROL DE STOCK Y CANTIDAD:
								cantidad = leerEntero("Introduce cantidad: ", 0, 9999);
								stockactual = dameStockActual(idProducto);
								stockminimo = dameStockMínimo(idProducto);
								System.out.println(
										"CANTIDAD TOTAL: " + stockactual + " - " + cantidad + " = " + (stockactual
												- cantidad));
								System.out.println("STOCK MÍNIMO: " + stockminimo);
								if ((stockactual - cantidad) < stockminimo) {
									System.out.println("NO HAY STOCK PARA EL PRODUCTO " + idProducto);
									System.out.println(
											"INTRODUCE LOS DATOS DE NUEVO DE LA LINEA: " + i + "\nLINEA: " + i);

								} else {
									dato2Correcto = true;
									System.out.println("INSERTO TLINEA " + i);

									// Actualizamos stock de producto según insertamos una linea de venta:

									insertarTLineasVentas(cantidad, stockactual, idventa, idProducto, i);

								}

							}

						}

					}
				} else {
					System.out.println("\tFIN DE INSERCIÓN, REPETIMOS: ");
				} // FIN IF
				
				idventa = leerCadena("Introduce idventa: ");

			} // while
			conexión.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		} // try catch
	}

	private static void insertarTLineasVentas(int cantidad, int stockactual, String idventa, String idProducto, int i)
			throws SQLException {
		String sql;
		PreparedStatement sentencia;
		sql = "INSERT INTO tlineasventas VALUES(?, ?, ?, ?)";
		sentencia = conexión.prepareStatement(sql);
		sentencia.setString(1, idventa);
		sentencia.setInt(2, i);
		sentencia.setString(3, idProducto);
		sentencia.setInt(4, cantidad);

		try {
			sentencia.executeUpdate();

			// Actualizamos stock de producto según insertamos una linea de venta:

			actualizaStock(cantidad, stockactual, idProducto);
			System.out.println("LINEA DE VENTA INSERTADA: " + i);
			System.out.println("Producto: " + idProducto + " Actualizado...");
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
	}

	private static void insertarVenta(String idCliente, String idventa, String fecha) throws SQLException {
		String sql = "INSERT INTO tventas VALUES(?, ?, ?)";
		PreparedStatement sentencia = conexión.prepareStatement(sql);
		sentencia.setString(1, idventa);
		sentencia.setString(2, idCliente);
		sentencia.setString(3, fecha);

		try {
			sentencia.executeUpdate();
			// actualizaStock(cantidad, stockactual, idProducto);
			//System.out.println("Filas afectadas: " + filas);
			// System.out.println("Producto: " + idProducto + " Actualizado...");
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
	}

	private static void actualizaStock(int cantidad, int stockactual, String idProducto) {
		int cantidadTotal = stockactual - cantidad;
		String sql = "UPDATE tproductos SET stockactual = ? where idproducto = ?";
		try {
			PreparedStatement sentencia = conexión.prepareStatement(sql);
			sentencia.setInt(1, cantidadTotal);

			sentencia.setString(2, idProducto);

			int filas = sentencia.executeUpdate();

			System.out.println("Filas afectadas: " + filas);
			System.out.println("STOCK DEL PRODUCTO: " + idProducto + " Actualizado...");
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
	}

	private static int dameStockMínimo(String idProducto) {
		String sql = "SELECT stockminimo FROM tproductos Where idProducto = ?";
		int cantidad = 0;
		try {
			PreparedStatement sentencia = conexión.prepareStatement(sql);
			sentencia.setString(1, idProducto);
			ResultSet resul = sentencia.executeQuery();
			if (resul.next()) {
				cantidad = resul.getInt(1);
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
		return cantidad;
	}

	private static int dameStockActual(String idProducto) {
		String sql = "SELECT stockactual FROM tproductos Where idProducto = ?";
		int cantidad = 0;
		try {
			PreparedStatement sentencia = conexión.prepareStatement(sql);
			sentencia.setString(1, idProducto);
			ResultSet resul = sentencia.executeQuery();
			if (resul.next()) {
				cantidad = resul.getInt(1);
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
		return cantidad;
	}

	private static String leerCadena(String mensaje) {
		String cadena;
		do {
			System.out.print(mensaje);
			cadena = sc.nextLine();

		} while (cadena.length() == 0 || cadena.length() > 10);
		return cadena;
	}// LeerCadena

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
				System.out.print("\n\tIncorrecto, escribe de nuevo: ");
			}
		} while (!salir);

		return numero;
	}// LecturaEntero

	private static boolean existeIdProducto(String idProducto) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexión con la BD
			Connection conexión = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			// Preparamos la consulta

			String sql = "SELECT idproducto FROM tproductos WHERE idproducto = ?";
			PreparedStatement sentencia = conexión.prepareStatement(sql);
			sentencia.setString(1, idProducto);
			ResultSet resul = sentencia.executeQuery();

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out.println("\tNO EXISTE EL ID PRODUCTO: " + idProducto);
				existe = false;
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexión.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static boolean existeIdCliente(String idcliente) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexión con la BD
			Connection conexión = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			String sql = "SELECT idcliente FROM tclientes WHERE idcliente = ?";
			PreparedStatement sentencia = conexión.prepareStatement(sql);

			// Establece el parámetro en la consulta
			sentencia.setString(1, idcliente);

			ResultSet resul = sentencia.executeQuery();

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
			// conexión.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	private static boolean existeIdVenta(String idventa) {
		// en la tabla empleados
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexión con la BD
			Connection conexión = DriverManager.getConnection("jdbc:mysql://localhost/unidad2", "unidad2", "unidad2");

			// Preparamos la consulta

			String sql = "SELECT idventa FROM tventas WHERE idventa = ?";
			PreparedStatement sentencia = conexión.prepareStatement(sql);

			// Establece el parámetro en la consulta
			sentencia.setString(1, idventa);

			ResultSet resul = sentencia.executeQuery();

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
			conexión.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

}

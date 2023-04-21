package ejerciciosLibro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Actividad_7 {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {

			System.out.println("ORACLE");

			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD2",
					"UNIDAD2");

			System.out.println("LECTURA DE DATOS: ");

			int idcliente = leerEntero("Introduce idcliente: ", 0, 9999);
			while (idcliente != 0) {
				if (existeIdClienteOracle(idcliente) == true) {

					String sql = "SELECT nombre FROM clientes WHERE id = ?";
					PreparedStatement sentencia = conexion.prepareStatement(sql);
					sentencia.setInt(1, idcliente);

					try {
						ResultSet resul = sentencia.executeQuery();

						// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
						// numero - nombre - ciudad
						// Se hace un bucle mientras haya registros y se van visualizando
						while (resul.next()) {
							System.out.printf("\nVentas del cliente: %s %n", resul.getString(1));

							String sql2 = "SELECT V.IDVENTA, V.FECHAVENTA, P.DESCRIPCION, V.CANTIDAD, P.PVP "
									+ "FROM VENTAS V, PRODUCTOS P " + "WHERE V.IDCLIENTE = ? AND v.idproducto = p.id";
							PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
							sentencia2.setInt(1, idcliente);

							ResultSet resul2 = sentencia2.executeQuery();

							// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
							// numero - nombre - ciudad
							// Se hace un bucle mientras haya registros y se van visualizando
							float importetotal = 0;
							int cont = 0;
							while (resul2.next()) {
								float importe = (resul2.getInt(4) * resul2.getFloat(5));

								System.out.printf(
										"Venta: ID VENTA: %d Fecha Venta: %s %n\t " + "Producto: %s%n\t "
												+ "Cantidad: %d PVP: %.2f %n\t " + "Importe: %.2f%n",
										resul2.getInt(1), resul2.getString(2), resul2.getString(3), resul2.getInt(4),
										resul2.getFloat(5), importe);
								importetotal = importetotal + importe;
								cont++;
							}
							System.out.println("Número total de ventas: " + cont);
							System.out.println("ImporteTotal de ventas: " + importetotal);

						}
					} catch (SQLException e) {
						// e.printStackTrace();
						System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
						System.out.printf("Mensaje   : %s %n", e.getMessage());
						System.out.printf("SQL estado: %s %n", e.getSQLState());
						System.out.printf("Cód error : %s %n", e.getErrorCode());
					}

					sentencia.close(); // Cerrar Statement

				} // FIN IF
				idcliente = leerEntero("\rIntroduce idCliente: ", 0, 9999);

			} // FIN WHILE

			// conexion.commit();
			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// main

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

	private static boolean existeIdClienteOracle(int idcliente) {
		boolean existe = false;
		// en la tabla VENTAS
		try {
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "UNIDAD2",
					"UNIDAD2");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT IDCLIENTE FROM VENTAS WHERE IDCLIENTE = " + idcliente;
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
}

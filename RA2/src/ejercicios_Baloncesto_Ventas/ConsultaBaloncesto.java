package ejercicios_Baloncesto_Ventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ConsultaBaloncesto {

	static Connection conexion;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/baloncesto", "baloncesto", "baloncesto");

			listado1();

			listado2();

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

	}	// MAIN


	private static void listado2() {
		String nombreEquipo = "";
		int cont = 0;
		System.out.println("----------------------------------------------------------------------------------");

		System.out.println("LECTURA DE DATOS: ");
		nombreEquipo = leerCadena("Introduce nombre de equipo: ");
		System.out
		.println("==================================================================================");


		while (!nombreEquipo.equals("*")) {

			if (existeEquipo(nombreEquipo)) {
				Locale.setDefault(Locale.UK); // Evita este error.
				
//					  select j.codigo, j.Nombre, round(avg(est.Puntos_por_partido), 2) from
//					  jugadores j, equipos e, estadisticas EST where j.Nombre_equipo = '76ers' and
//					  j.codigo = est.jugador group by j.codigo;
				 
				String sql = "select e.nombre, j.codigo, j.Nombre, round(avg(est.Puntos_por_partido), 2) from\r\n"
						+ "	jugadores j, equipos e, estadisticas EST where j.Nombre_equipo = ? and\r\n"
						+ "	j.codigo = est.jugador group by j.codigo;";
				try {
					PreparedStatement sentencia = conexion.prepareStatement(sql);
					sentencia.setString(1, nombreEquipo);

					ResultSet resul = sentencia.executeQuery();
					cont = 1;
					String nombre = damequipo(nombreEquipo);
					System.out.println("Equipo: " + nombre);
					while (resul.next()) {
						System.out.printf("%d, %10s\t :\t%.2f%n", resul.getInt(2), resul.getString(3), resul.getFloat(4));
						
						
						cont++;
					}
					System.out
					.println("==================================================================================");

					 System.out.println("Num JUGADORES: " + cont);
					 sentencia.close(); // Cerrar Statement
						System.out
						.println("----------------------------------------------------------------------------------");

				} catch (SQLException e) {
					// e.printStackTrace();
					System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
					System.out.printf("Mensaje   : %s %n", e.getMessage());
					System.out.printf("SQL estado: %s %n", e.getSQLState());
					System.out.printf("Cód error : %s %n", e.getErrorCode());
				} // try catch
			} // FIN IF

			nombreEquipo = leerCadena("Introduce nombre de equipo: ");
			System.out
			.println("==================================================================================");


		} // FIN WHILE

		System.out.println("\nFIN LISTADO 2....");
		System.out
		.println("----------------------------------------------------------------------------------");
	}

	private static String damequipo(String nombreEquipo) {
		String nombre = "";
		String sql = "select e.nombre from\r\n"
				+ "	equipos e where e.nombre = ?;";
		
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setString(1, nombreEquipo);

			ResultSet resul = sentencia.executeQuery();

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				nombre = resul.getString(1);

			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
	
		return nombre;
	}

	private static boolean existeEquipo(String nombreEquipo) {
		boolean existe = false;

		// Preparamos la consulta
		String sql = "SELECT nombre FROM equipos WHERE nombre = ?";

		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setString(1, nombreEquipo);

			ResultSet resul = sentencia.executeQuery();

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out
						.println("==================================================================================");

				System.out.println("\tEl equipo no existe");
				System.out
						.println("----------------------------------------------------------------------------------");

				existe = false;
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}

		return existe;

	}


	@SuppressWarnings("resource")
	private static void listado1() {
		int numeroJugador, cont = 0;

		System.out.println("----------------------------------------------------------------------------------");

		System.out.println("LECTURA DE DATOS: ");
		numeroJugador = leerEntero("Introduce Nº de Jugador: ", 0, 9999);

		while (numeroJugador != 0) {

			if (existeNumJug(numeroJugador)) {
				Locale.setDefault(Locale.UK); // Evita este error.
				// SELECT J.codigo, J.Nombre, J.Nombre_equipo, E.temporada,
				// E.Puntos_por_partido, E.Asistencias_por_partido, E.Tapones_por_partido,
				// E.Rebotes_por_partido FROM JUGADORES J, ESTADISTICAS E WHERE J.codigo = 227
				// AND E.jugador = 227;
				String sql = "SELECT codigo, Nombre, Nombre_equipo " + "FROM JUGADORES " + "WHERE codigo = ?;";
				try {
					PreparedStatement sentencia = conexion.prepareStatement(sql);
					sentencia.setInt(1, numeroJugador);

					ResultSet resul = sentencia.executeQuery();

					while (resul.next()) {
						cont = 0;
						System.out.printf("DATOS DEL JUGADOR: %d%nNombre: %s%nEquipo: %s%n", resul.getInt(1),
								resul.getString(2), resul.getString(3));

						sql = "SELECT E.temporada,\r\n"
								+ " E.Puntos_por_partido, E.Asistencias_por_partido, E.Tapones_por_partido,\r\n"
								+ "E.Rebotes_por_partido FROM ESTADISTICAS E WHERE E.jugador = ?;";

						sentencia = conexion.prepareStatement(sql);
						sentencia.setInt(1, numeroJugador);

						resul = sentencia.executeQuery();

						System.out.println("Temporada  Ptos    Asis  Tap   Reb");
						System.out.println("===================================");
						cont = 0;
						while (resul.next()) {

							System.out.printf("%s %10.2f %6.2f %5.2f %5.2f%n", resul.getString(1), resul.getFloat(2),
									resul.getFloat(3), resul.getFloat(4), resul.getFloat(5));

							cont++;
						}

					} // while 1

					System.out.println("Num registros: " + cont);
					sentencia.close(); // Cerrar Statement

				} catch (SQLException e) {
					// e.printStackTrace();
					System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
					System.out.printf("Mensaje   : %s %n", e.getMessage());
					System.out.printf("SQL estado: %s %n", e.getSQLState());
					System.out.printf("Cód error : %s %n", e.getErrorCode());
				} // try catch
			} // FIN IF

			numeroJugador = leerEntero("Introduce Nº de Jugador: ", 0, 9999);

		} // FIN WHILE

		System.out.println("\nFIN LISTADO 1....");
		System.out
		.println("----------------------------------------------------------------------------------");

	}

	private static boolean existeNumJug(int numeroJugador) {
		boolean existe = false;

		// Preparamos la consulta
		String sql = "SELECT CODIGO FROM JUGADORES WHERE CODIGO = ?";

		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, numeroJugador);

			ResultSet resul = sentencia.executeQuery();

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out
						.println("==================================================================================");

				System.out.println("\tEl jugador no existe");
				System.out
						.println("----------------------------------------------------------------------------------");

				existe = false;
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}

		return existe;

	}

	// ==========================LEERENTERO_LIMITES_Y_REPETIR_SI_FALLA========================
	private static int leerEntero(String mensaje, int min, int max) {
		boolean salir = false;
		int numero = 0;

		do {
			try {
				System.out.print(mensaje);
				numero = sc.nextInt();
				sc.nextLine();
				while (numero < min || numero > max) {
					System.out.print("\tSuperado l�mite (> " + min + " y < " + max + ")");
					System.out.print("\n\tOtra vez: ");

					numero = sc.nextInt();
					sc.nextLine();
					System.out.println();
				}
				salir = true;

			} catch (InputMismatchException exc) {
				sc.nextLine();
				System.out.print("\n\tIncorrecto, escr�belo de nuevo: ");
			}
		} while (!salir);

		return numero;
	}// LecturaEntero

	// ===========================LEERCADENA===================================================
	private static String leerCadena(String mensaje) {
		String cadena;
		do {
			System.out.println(mensaje);
			cadena = sc.nextLine();

		} while (cadena.length() == 0 || cadena.length() > 10);
		return cadena;
	}// LeerCadena

}// CLASE

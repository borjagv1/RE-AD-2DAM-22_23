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

/*
 * EJERCICIO 2.10 MODIFICADO POR ELLA Ejercicio que manda, no es el 2.10 es uno
 * que modifica ella.
 * 
 * El importante es la mezcla entre el 2.10 y lo que añade ella: Leer por
 * teclado los datos a insertar en un proceso repetitivo hasta que el número de
 * empleado sea 0, cuando sea 0 no se pedirán más datos por teclado.
 * 
 * SE LEERA POR TECLADO: EMP NO; APELLIDO, OFICIO, DIR, SALARIO, COMISION,
 * DEPT_NO ANTES DE INSERTAR SE DEBEN REALIZAR LAS SIGUIENTES COMPROBACIONES:
 * RESTRICCIONES DE LOS CAMPOS DE ENTRADA: 
 * - DEPT_NO 1 Y 99 
 * - EMP_NO Y DIR 1 Y 9999 
 * - COMISION 0 Y 999 
 * - SALARIO 1 Y 9999 
 * - LONGITUD DE APELLIDO Y OFICIO > 0 (10 CARACTERES)
 * 
 * … Lo demás igual que el ejercicio 2.10 * Fecha de alta la fecha actual.
 */
public class Actividad_2_10 {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// Cargar el driver
			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			//conexion.setAutoCommit(false);
			
			
			int emp_no, dir, dept_no;
			String apellido = null, oficio = null;
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // dd/MM/yyyy
			LocalDate f = LocalDate.now();
			String fecha = f.format(formato);
			float salario, comision;

			System.out.println("LECTURA DE DATOS: ");
			emp_no = leerEntero("Introduce emp_no: ", 0, 9999);

			while (emp_no != 0) {

				apellido = leerCadena("Introduce apellido: ");
				oficio = leerCadena("Introduce oficio: ");
				dir = leerEntero("Introduce dir: ", 1, 9999);
				salario = LeerFloat("introduce salario: ", 1, 9999);
				comision = LeerFloat("introduce comision: ", 0, 999);
				dept_no = leerEntero("Introduce dept_no: ", 1, 9999);

				if (existeEmp_no(emp_no) == false && existeDir(dir) == true && existeDept_no(dept_no) == true) {
					Locale.setDefault(Locale.UK); // Evita este error.
					//Column count doesnt't match value count at row 1
					String sql = "INSERT INTO empleados VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
					
					PreparedStatement sentencia = conexion.prepareStatement(sql);
					sentencia.setInt(1, emp_no);
					sentencia.setString(2, apellido);
					sentencia.setString(3, oficio);
					sentencia.setInt(4, dir);
					sentencia.setString(5, fecha);
					sentencia.setFloat(6, salario);
					sentencia.setFloat(7, comision);
					sentencia.setInt(8, dept_no);

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

				emp_no = leerEntero("Introduce emp_no: ", 0, 9999);

			} // FIN WHILE
			
			//conexion.commit();
			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// Fin Main

	private static boolean existeDept_no(int dept_no) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT dept_no FROM departamentos WHERE dept_no = " + dept_no;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out.println("\tNO EXISTE EL DEPT_NO: " + dept_no);
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

	private static boolean existeDir(int dir) {
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT dir FROM empleados WHERE dir = " + dir;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = true;
			} else {
				System.out.println("\tNO EXISTE EL DIR: " + dir);
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

	private static boolean existeEmp_no(int emp_no) {
		// en la tabla empleados
		boolean existe = false;
		// en la tabla departamentos
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT emp_no FROM empleados WHERE emp_no = " + emp_no;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				System.out.println("\tYA EXISTE EL EMP_NO: " + emp_no);
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

	private static float LeerFloat(String mensaje, int desde, int hasta) {
		float numero = 0;
		do {
			System.out.print(mensaje);
			// Introducir decimales con .
			try {
				numero = sc.nextFloat();
				sc.nextLine();
			} catch (NumberFormatException exc) {
				numero = 0;
				sc.nextLine();
			} catch (InputMismatchException exc) {
				numero = 0;
				sc.nextLine();
			}
		} while (numero < desde || numero > hasta);
		return numero;
	}// LeerDouble

	private static String leerCadena(String mensaje) {
		String cadena;
		do {
			System.out.print(mensaje);
			cadena = sc.nextLine();

		} while (cadena.length() == 0 || cadena.length() > 10);
		return cadena;
	}// LeerCadena

}

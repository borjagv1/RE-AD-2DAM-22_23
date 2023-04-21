package ejerciciosLibro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * Manda ejercicio 2.6 de la pag 93: cambia donde pone 10 por 
 * “leído por teclado, proceso repetitivo hasta departamento = 0” Visualizar datos dept.
 * 
 */
public class Actividad_2_6 {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		parte1();
		parte2();

	}// fin de main

	private static void parte1() {
		int dep;
		String nombredep, local;
		try {
			// Cargar el driver
			Class.forName("org.sqlite.JDBC");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/borja/Bases de Datos AD/SQLITE/ejemplo2223.db");

			System.out.println("introduce dep: ");
			dep = sc.nextInt();

			while (dep != 0) {
				nombredep = nombreDepart(dep);
				local = nombreLoc(dep);

				if (nombredep.length() == 0) {
					System.out.println("\tNO EXISTE DEP");
				} else {
					System.out.println("DEPARTAMENTO: " + nombredep + ", LOCALIDAD: " + local);
					// Preparamos la consulta
					Statement sentencia = conexion.createStatement();
					String sql = "SELECT APELLIDO, OFICIO, SALARIO FROM empleados WHERE dept_no = " + dep;
					ResultSet resul = sentencia.executeQuery(sql);

					// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
					// numero - nombre - ciudad
					// Se hace un bucle mientras haya registros y se van visualizando

					System.out.println("DATOS DE EMPLEADOS: ");
					while (resul.next()) {
						System.out.printf("\t%s, %s, %.2f %n", resul.getString(1), resul.getString(2),
								resul.getFloat(3));
					}
					// PARTE 2
					Statement sentencia2 = conexion.createStatement();
					String sql2 = "SELECT * FROM departamentos WHERE dept_no = " + dep;
					ResultSet resul2 = sentencia2.executeQuery(sql2);

					// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
					// numero - nombre - ciudad
					// Se hace un bucle mientras haya registros y se van visualizando

					System.out.println("\nDATOS DE DEPARTAMENTO: " + dep);
					while (resul2.next()) {
						System.out.printf("\t%d, %s, %s %n", resul2.getInt(1), resul2.getString(2),
								resul2.getString(3));
					}

					//

					resul.close(); // Cerrar ResultSet
					sentencia.close(); // Cerrar Statement
				} // FIN IF
				System.out.println("introduce dep: ");
				dep = sc.nextInt();
			} // FIN WHILE
			System.out.println("FIN LISTADO");

			conexion.close(); // Cerrar conexi�n
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void parte2() {
		try {
			// Cargar el driver
			Class.forName("org.sqlite.JDBC");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/borja/Bases de Datos AD/SQLITE/ejemplo2223.db");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT e.apellido, max(e.SALARIO), d.dnombre FROM empleados e, departamentos d WHERE e.dept_no = d.dept_no";
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
			// numero - nombre - ciudad
			// Se hace un bucle mientras haya registros y se van visualizando

			System.out.println("\nDATO DE EMPLEADO CON MÁX SALARIO Y SU DEPARTAMENTO: ");
			while (resul.next()) {
				System.out.printf("\t%s, %.2f, %s %n", resul.getString(1), resul.getFloat(2), resul.getString(3));
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement

			System.out.println("FIN LISTADO");

			conexion.close(); // Cerrar conexi�n
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String nombreDepart(int dep) {

		String nombre = "";
		try {
			// Cargar el driver
			Class.forName("org.sqlite.JDBC");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/borja/Bases de Datos AD/SQLITE/ejemplo2223.db");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT dnombre FROM departamentos WHERE dept_no = " + dep;
			ResultSet resul = sentencia.executeQuery(sql);

			while (resul.next()) {
				nombre = resul.getString(1);
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement

			conexion.close(); // Cerrar conexi�n
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
	}

	private static String nombreLoc(int dep) {
		String nombre = null;
		try {
			// Cargar el driver
			Class.forName("org.sqlite.JDBC");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/borja/Bases de Datos AD/SQLITE/ejemplo2223.db");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT loc FROM departamentos WHERE dept_no = " + dep;
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
			// numero - nombre - ciudad
			// Se hace un bucle mientras haya registros y se van visualizando

			while (resul.next()) {
				nombre = resul.getString(1);
			}

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
	}

}// fin de la clase

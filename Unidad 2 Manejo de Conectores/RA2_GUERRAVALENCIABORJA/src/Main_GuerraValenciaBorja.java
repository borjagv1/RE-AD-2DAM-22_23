import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main_GuerraValenciaBorja {
	static Connection conexion;

	public static void main(String[] args) {
		try {

			System.out.println("ORACLE");
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Establecemos la conexion con la BD MYSQL
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "CICLISTAS", "CICLISTAS");
			//////////////////////////////////////////////////////////////////////////////////
			ejercicio1(15);
			ejercicio1(1);
			ejercicio1(215);

			ejercicio2(1);
			ejercicio2(99);
			ejercicio2(90);
			ejercicio2(8);

			ejercicio3();
			//////////////////////////////////////////////////////////////////////////////////
			System.out.println("\nFIN EJERCICIO");

			conexion.close(); // Cerrar conexión

			System.exit(0);

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} // FIN PRIMER TRY-CATCH

	}// MAIN

	private static void ejercicio1(int codigoequipo) {
		System.out.println("ejercicio 1:");

		BigInteger codequipo = BigInteger.valueOf(codigoequipo);
		try {
			// Ejecuta la consulta para obtener el equipo con el código especificado
			PreparedStatement sentencia = conexion.prepareStatement("SELECT * FROM equipos WHERE codigoequipo = ?");
			sentencia.setInt(1, codigoequipo);
			ResultSet resul = sentencia.executeQuery();

			if (resul.next()) {
				System.out.println("El equipo " + resul.getString("NOMBREEQUIPO") + " existe\n");

				// Preparamos la consulta para obtener el jefe de equipo
				String jefe = dameJefeEquipo(codigoequipo);
				// Preparamos la consulta para obtener el jefe de equipo

				System.out.println("Cod Equipo: " + resul.getInt(1) + " Nombre: " + resul.getString(2) + "\nPAIS: "
						+ resul.getString(4) + " Jefe de equipo: " + jefe);
				System.out.println(
						"-------------------------------------------------------------------------------------");

				// Ejecuta la consulta para obtener la lista de ciclistas que han ganado alguna
				// etapa
				sentencia = conexion.prepareStatement(
						"SELECT e.codigoetapa, e.tipoetapa, c.codigociclista, c.nombreciclista, c.fechanacimiento, e.km, e.pobsalida, e.pobllegada FROM etapas e INNER JOIN ciclistas c ON e.ciclistaganador = c.codigociclista INNER JOIN equipos q ON c.codigoequipo = q.codigoequipo WHERE q.codigoequipo = ? ORDER BY 1");
				sentencia.setInt(1, codigoequipo);
				resul = sentencia.executeQuery();

				if (!resul.next()) {
					System.out.println("NO EXISTEN CICLISTAS QUE HAYAN GANADO ALGUNA ETAPA EN ESTE EQUIPO");
				} else {
					System.out.println("LISTA DE CICLISTAS QUE HAN GANADO ALGUNA ETAPA:");
					System.out.println(
							"Etapa / Tipo de etapa         Ciclista (cod/ nombre/ edad)       KM  Salida y llegada");
					System.out.println(
							"======================================================================================");

					do { // PONEMOS EL WHILE AL FINAL POR ESO DO-WHILE, YA QUE YA HICIMOS resul.next() al
							// comprobar si existe listado en el if

						int code = resul.getInt("CODIGOETAPA");
						String tipoe = resul.getString("TIPOETAPA");
						int codc = resul.getInt("CODIGOCICLISTA");
						String nombrec = resul.getString("NOMBRECICLISTA");
						Date fechanac = resul.getDate("FECHANACIMIENTO");
						int km = resul.getInt("KM");
						String pobs = resul.getString("POBSALIDA");
						String pobll = resul.getString("POBLLEGADA");
						String edad = dameEdad(fechanac);
						System.out.printf("%-2s %-26s %-2s %-25s %-5s %-2s %-3s / %-3s %n", code, tipoe, codc, nombrec,
								edad, km, pobs, pobll);

					} while (resul.next());
				}
				System.out.println(
						"-------------------------------------------------------------------------------------");

				sentencia = conexion.prepareStatement(
						"SELECT e.codigoetapa, e.tipoetapa, e.ciclistaganador, c.nombreciclista, t.codigotramo, t.nombretramo, t.categoria "
								+ "FROM tramospuertos t " + "INNER JOIN etapas e ON t.numetapa = e.codigoetapa "
								+ "INNER JOIN ciclistas c ON e.ciclistaganador = c.codigociclista "
								+ "INNER JOIN equipos q ON c.codigoequipo = q.codigoequipo "
								+ "WHERE q.codigoequipo = ? AND e.tipoetapa = 'Montaña' ORDER BY 1");
				sentencia.setInt(1, codigoequipo);
				resul = sentencia.executeQuery();

				if (!resul.next()) {
					System.out.println("NO EXISTEN CICLISTAS QUE HAYAN GANADO ALGUNA ETAPA DE MONTAÑA EN ESTE EQUIPO");
				} else {
					System.out.println("LISTA DE CICLISTAS QUE HAN GANADO TRAMOS DE MONTAÑA");
					System.out.println(
							"Etapa / Tipo de etapa         Ciclista (cod/ nombre)       Tramo/ Nombre                Categoria");
					System.out.println(
							"=================================================================================================");

					do { // PONEMOS EL WHILE AL FINAL POR ESO DO-WHILE, YA QUE YA HICIMOS resul.next() al
							// comprobar si existe listado en el if
						int code = resul.getInt("codigoetapa");
						String tipoe = resul.getString("tipoetapa");
						int codc = resul.getInt("ciclistaganador");
						String nombrec = resul.getString("nombreciclista");
						int tramo = resul.getInt("codigotramo");
						String nombrem = resul.getString("nombretramo");
						String categoria = resul.getString("categoria");

						System.out.printf("%-2s %-26s %-2s %-25s %-25s %-20s %n", code, tipoe, codc, nombrec,
								tramo + "/ " + nombrem, categoria);
					} while (resul.next());
				}
				System.out.println(
						"-------------------------------------------------------------------------------------");

			} else {
				System.out.println("El equipo " + codequipo + " no existe");

			}

		} catch (SQLException e) { // e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}
	}

	private static String dameEdad(Date fechanacimiento) {
		//////////// FORMATO DE FECHA localdate////////////////////////////////

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy");
		LocalDate f11 = LocalDate.now();
		String fechaactual = f11.format(formato);

		//////////// FORMATO DE FECHA localdate////////////////////////////////
		//////////// FORMATO DE FECHA QUE RECOGEMOS DEL RESUL//////////////////

		SimpleDateFormat formato2 = new SimpleDateFormat("yyyy");
		String fechanac = formato2.format(fechanacimiento);

		//////////// FORMATO DE FECHA QUE RECOGEMOS DEL RESUL//////////////////

		int edad = (Integer.parseInt(fechaactual)) - ((Integer.parseInt(fechanac)));
		String devuelveEdad = Integer.toString(edad);
		return devuelveEdad;

	}

	private static String dameJefeEquipo(int codigoequipo) {
		String existe = "*no existe*";

		// Preparamos la consulta
		String sql = "select c.nombreciclista from equipos e, ciclistas c\r\n"
				+ "where c.codigoequipo = ? and e.codigoequipo= ? AND c.codigociclista = c.jefeequipo";

		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, codigoequipo);
			sentencia.setInt(2, codigoequipo);

			ResultSet resul = sentencia.executeQuery();

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van visualizando
			if (resul.next()) {
				existe = resul.getString(1);
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

	/*
	 * private static boolean existeEquipo(int codigoequipo) {
	 * boolean existe = false;
	 * 
	 * // Preparamos la consulta
	 * String sql = "SELECT CODIGOEQUIPO FROM EQUIPOS WHERE CODIGOEQUIPO = ?";
	 * 
	 * try {
	 * PreparedStatement sentencia = conexion.prepareStatement(sql);
	 * 
	 * sentencia.setInt(1, codigoequipo);
	 * 
	 * ResultSet resul = sentencia.executeQuery();
	 * 
	 * // Recorremos el resultado para visualizar cada fila
	 * // Se hace un bucle mientras haya registros y se van visualizando
	 * if (resul.next()) {
	 * existe = true;
	 * } else {
	 * System.out.println("\tNO EXISTEN EQUIPOS CON EL COD EQUIPO: " +
	 * codigoequipo);
	 * existe = false;
	 * }
	 * resul.close(); // Cerrar ResultSet
	 * sentencia.close(); // Cerrar Statement
	 * 
	 * } catch (SQLException e) {
	 * // e.printStackTrace();
	 * System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
	 * System.out.printf("Mensaje   : %s %n", e.getMessage());
	 * System.out.printf("SQL estado: %s %n", e.getSQLState());
	 * System.out.printf("Cód error : %s %n", e.getErrorCode());
	 * }
	 * 
	 * return existe;
	 * }
	 */

	private static void ejercicio2(int codigoEquipo) throws SQLException {
		System.out.println("\n\nEJERCICIO 2");
		Statement sentencia = conexion.createStatement();

		// Consultar la tabla "equipos" para verificar si el equipo existe
		ResultSet rs = sentencia.executeQuery("SELECT * FROM equipos WHERE codigoequipo = " + codigoEquipo);
		if (rs.next()) {
			// Mostrar el código y el nombre del equipo
			System.out.println("Equipo: " + rs.getInt("codigoequipo") + ", Nombre: " + rs.getString("nombreequipo"));
		} else {
			// Mostrar mensaje de que el equipo no existe
			System.out.println("El equipo: " + codigoEquipo + ", no existe.");
			return;
		}

		////////////////////////////////////////////////////////////////////////////////////////////
		//crear función almacenada en oracle que muestre una lista de ciclistas
		String sql2 =  "create or replace NONEDITIONABLE FUNCTION FUN_PRUEBA_BORJAGUERRAVALENCIA (\r\n"
				+ "    codigo_equipo INT\r\n"
				+ ") RETURN VARCHAR2 AS\r\n"
				+ "\r\n"
				+ "    resultado CLOB;\r\n"
				+ "    CURSOR c_ciclistas IS\r\n"
				+ "    SELECT DISTINCT NOMBRECICLISTA, TIPO, COLOR FROM CICLISTAS C, LLEVA L, CAMISETAS CA \r\n"
				+ "    WHERE C.CODIGOEQUIPO = codigo_equipo AND L.CODIGOCICLISTA = C.CODIGOCICLISTA AND L.CODIGOCAMISETA = CA.CODIGOCAMISETA;\r\n"
				+ "        \r\n"
				+ "\r\n"
				+ "BEGIN\r\n"
				+ "    \r\n"
				+ "    FOR r_ciclistas IN c_ciclistas LOOP\r\n"
				+ "        resultado := resultado\r\n"
				+ "                     || r_ciclistas.nombreciclista\r\n"
				+ "                     || ' * '\r\n"
				+ "                     || r_ciclistas.tipo\r\n"
				+ "                     || ' * '\r\n"
				+ "                     || r_ciclistas.color\r\n"
				+ "                     || chr(10); -- ch3(13) funciona a veces\r\n"
				+ "    END LOOP;\r\n"
				+ "\r\n"
				+ "    RETURN resultado;\r\n"
				+ "END;";
		
		
		/*
		 * String sql =
		 * ("CREATE OR REPLACE FUNCTION FUN1_BORJAGUERRAVALENCIA (codigo_equipo INT) RETURN CLOB AS\r\n"
		 * + "  resultado CLOB;\r\n" + "BEGIN\r\n"
		 * +
		 * "  SELECT listagg(nombreciclista || ' * ' || tipo || ' * ' || color, chr(13) || chr(10)) WITHIN GROUP (ORDER BY nombreciclista)\r\n"
		 * + "  INTO resultado\r\n" + "  FROM (\r\n" +
		 * "    SELECT c.nombreciclista, ca.tipo, ca.color\r\n"
		 * + "    FROM ciclistas c\r\n" +
		 * "    INNER JOIN lleva l ON c.codigociclista = l.codigociclista\r\n"
		 * + "    INNER JOIN camisetas ca ON l.codigocamiseta = ca.codigocamiseta\r\n"
		 * + "    WHERE c.codigoequipo = codigo_equipo\r\n"
		 * + "    GROUP BY c.nombreciclista, ca.tipo, ca.color\r\n" + "  );\r\n" +
		 * "  RETURN resultado;\r\n"
		 * + "END;");
		 */

		sentencia = conexion.createStatement();
		try {
			sentencia.executeUpdate(sql2); // cambiar para probar, es el mismo resultado con distinta funcion

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("C�d error : %s %n", e.getErrorCode());
		}

		sentencia.close(); // Cerrar Statement

		////////////////////////////////////////////////////////////////////////////////////////////

		// Llamar a la función almacenada que devuelve el nombre y el tipo de camiseta
		// de los ciclistas que han llevado alguna camiseta
		CallableStatement cs = conexion.prepareCall("{? = call FUN_PRUEBA_BORJAGUERRAVALENCIA(?)}");
		cs.registerOutParameter(1, Types.CLOB);
		cs.setInt(2, codigoEquipo);
		cs.execute();
		String resultado = cs.getString(1);

		// Mostrar resultados
		if (resultado == null || resultado.isEmpty()) {
			System.out.println("Ningún ciclista ha llevado camiseta\n");

		} else {
			System.out.println("CICLISTAS QUE HAN LLEVADO CAMISETA:");
			System.out.println(resultado);
		}
	}// ejercicio2

	/**
	 * @throws SQLException
	 */
	private static void ejercicio3() throws SQLException {
		/*
		 * (F) EJERCICIO3: modificar contenido de la bd.
		 * 
		 * Realiza un método java de nombre ejercicio3 () que añada las siguientes
		 * columnas de tipo number(3)
		 * 
		 * que no pueden tener valores nulos, en la tabla CICLISTAS:
		 * 
		 * ✓ Etapasganadas: para que contenga el número de etapas que ha ganado.
		 * 
		 * ✓ Etapascamiseta: para que contenga el número de etapas que ha llevado
		 * camiseta.
		 * 
		 * ✓ Tramosganados: para que contenga el número de tramos que ha ganado.
		 * 
		 * Inserta valores en dichas columnas.
		 * 
		 * Controlar errores para el caso que se ejecute varias veces el método y ya
		 * existan las columnas.
		 */
		System.out.println("\n\nEJERCICIO 3");

		try {

			String sql = "ALTER TABLE CICLISTAS DROP COLUMN etapasganadas";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sql);

			sql = "ALTER TABLE CICLISTAS DROP COLUMN etapascamiseta";
			sentencia = conexion.createStatement();
			sentencia.execute(sql);

			sql = "ALTER TABLE CICLISTAS DROP COLUMN tramosganados";
			sentencia = conexion.createStatement();
			sentencia.execute(sql);
		} catch (SQLSyntaxErrorException e) {
		}

		String sql = "ALTER TABLE ciclistas ADD (etapasganadas NUMBER(3), etapascamiseta NUMBER(3), tramosganados NUMBER(3))";
		Statement stmt = conexion.createStatement();
		stmt.executeUpdate(sql);

		sql = "SELECT * FROM CICLISTAS";
		Statement sentencia2 = conexion.createStatement();
		ResultSet resul = sentencia2.executeQuery(sql);
		while (resul.next()) {
			int codigociclista = resul.getInt(1);
			int etapas_ganadas = 0;
			int camisetas_llevadas = 0;
			int tramos_ganados = 0;

			sql = "SELECT c.codigociclista, c.nombreciclista, COALESCE(COUNT(e.codigoetapa), 0) AS etapas_ganadas FROM ciclistas c, etapas e where c.codigociclista = ? and e.ciclistaganador = ? group by c.codigociclista, c.nombreciclista";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigociclista);
			sentencia.setInt(2, codigociclista);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				etapas_ganadas = rs.getInt(3);
				System.out.println("Cantidad de etapas con camiseta: " + etapas_ganadas);
			}

			sql = "SELECT c.codigociclista, c.nombreciclista, COALESCE(COUNT(l.codigocamiseta), 0) AS lleva_camisetas FROM ciclistas c, lleva l where c.codigociclista = ? and l.codigociclista = ? group by c.codigociclista, c.nombreciclista";
			sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigociclista);
			sentencia.setInt(2, codigociclista);
			rs = sentencia.executeQuery();
			if (rs.next()) {
				camisetas_llevadas = rs.getInt(3);
				System.out.println("Cantidad de camisetas llevadas: " + camisetas_llevadas);
			}

			sql = "SELECT c.codigociclista, c.nombreciclista, COALESCE(COUNT(t.codigotramo), 0) AS etapas_ganadas FROM ciclistas c, tramospuertos t where c.codigociclista = ? and t.ciclistaganador = ? group by c.codigociclista, c.nombreciclista";
			sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigociclista);
			sentencia.setInt(2, codigociclista);
			rs = sentencia.executeQuery();
			if (rs.next()) {
				tramos_ganados = rs.getInt(3);
				System.out.println("Cantidad de tramos ganados: " + tramos_ganados);
			}
			sql = "update ciclistas c SET c.etapasganadas = " + (etapas_ganadas) + ", c.etapascamiseta = "
					+ (camisetas_llevadas) + ", c.tramosganados = " + (tramos_ganados) + " where c.codigociclista = "
					+ codigociclista;
			stmt = conexion.createStatement();
			stmt.executeUpdate(sql);

		}

	}

}// CLASS

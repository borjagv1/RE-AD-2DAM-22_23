package ejerciciosLibro;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class Actividad_2_12 {

	/*
	 * FUNCIÓN PARA ORACLE CREATE OR REPLACE NONEDITIONABLE FUNCTION actividad12 ( d
	 * NUMBER, numemple OUT NUMBER ) RETURN NUMBER AS salariomedio NUMBER; dep
	 * NUMBER; BEGIN SELECT dept_no INTO dep FROM departamentos WHERE dept_no = d;
	 * 
	 * --SI EXISTE Y NO TIENE EMPLEADOS DEBE DEVOLVER 0 COMO SALARIO MEDIO SELECT
	 * COUNT(*) INTO numemple FROM empleados WHERE dept_no = d;
	 * 
	 * IF ( numemple = 0 ) THEN salariomedio := 0; ELSE --NUMERO DE EMPLEADOS DEL
	 * DEPARTAMENTO --SALARIO MEDIO DE LOS EMPLEADOS DEL DEP SELECT COUNT(*),
	 * AVG(salario) INTO numemple, salariomedio FROM empleados WHERE dept_no = d;
	 * 
	 * END IF;
	 * 
	 * RETURN salariomedio; --SI EL DEPARTAMENTO NO EXISTE DEBE DEVOLVER COMO
	 * SALARIO MEDIO EL VALOR -1 Y EL Nº DE EMPLEADOS 0. EXCEPTION WHEN
	 * no_data_found THEN numemple := 0; RETURN -1; END; / -- SELECT D.DEPT_NO,
	 * AVG(SALARIO) FROM EMPLEADOS E, DEPARTAMENTOS D WHERE E.DEPT_NO=40 GROUP BY
	 * D.DEPT_NO;
	 */

	/*
	 * CREATE PROCEDURE `ACTIVIDAD2_12`(IN `d` INT, OUT `numemple` INT, OUT
	 * `salariomedio` FLOAT) NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER
	 * BEGIN SET numemple = 0; SET salariomedio= 0; SELECT COUNT(*) INTO numemple
 	 * FROM empleados WHERE dept_no = d; IF ( numemple = 0 ) THEN SET salariomedio =
	 * 0; ELSE SELECT COUNT(*), AVG(salario) INTO numemple, salariomedio FROM
	 * empleados WHERE dept_no = d; END IF; END;
	 */

	// DROP PROCEDURE IF EXISTS `ACTIVIDAD2_12`

	public static void main(String[] args) {
		crearFuncionMySQL();
		usaMySQL();

		crearFuncionOracle();
		usaOracle();
	}// fin de main

	private static void usaMySQL() {
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			// recuperar parametro de main
			String dep = "10"; // departamento

			// 1 --- DEVUELVE (RETURN) EL salMed FLOAT
			// 2 --- RECIBE EL DEP INT
			// 3 --- nEMP Parámetro salida (OUT) INT
			// CONSTRUIR ORDEN DE LLAMADA
			String sql = " {call actividad2_12 (?, ?, ?) }"; // recibe, 1º salida, 2º salida al ser proced. ?,?,?

			// Preparar la llamada
			CallableStatement llamada = conexion.prepareCall(sql);

			// Dar valor a los argumentos
			llamada.setInt(1, Integer.parseInt(dep)); // PRIMER ?. Lo que recibe (DEP)

			// registrar VALORES DE SALIDA
			llamada.registerOutParameter(2, Types.INTEGER); // el segundo ? parámetro OUT numero de empleados
			llamada.registerOutParameter(3, Types.FLOAT);

			llamada.executeUpdate(); // ejecutar el procedimiento

			// Preparamos la consulta PARA MOSTRAR LOS DATOS.
			Statement sentencia = conexion.createStatement();
			String sql2 = "SELECT * FROM departamentos";
			ResultSet resul = sentencia.executeQuery(sql2);

			// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
			// numero - nombre - ciudad
			// Se hace un bucle mientras haya registros y se van visualizando

			System.out.println("ID NOMBRE DEP    LOCALIDAD        NÚMERO EMPLE SALARIO MEDIO");
			System.out.println("== ============= ================ ============ =============");
			while (resul.next()) {
				llamada.setInt(1, resul.getInt(1)); // Por cada resul, ASIGNAMOS, el nuevo valor de dep
				llamada.executeUpdate(); // EJECUTAR EL PROCEDIMIENTO
				float media = llamada.getFloat(3);
				int contador = llamada.getInt(2);
				System.out.printf("%d %-13s %-16s %-12d %-13.2f %n", resul.getInt(1), resul.getString(2),
						resul.getString(3), contador, media);
			} // %-2d %-13s %-16s %-12d %.2f %n

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			llamada.close();
			conexion.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void crearFuncionMySQL() {
		try {
			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecemos la conexion con la BD MYSQL
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");

			borroProcedSiExiste(conexion);

			String sql = "	  CREATE PROCEDURE `actividad2_12`(IN `d` INT, OUT `numemple` INT, OUT\r\n"
					+ "	  `salariomedio` FLOAT) NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER\r\n"
					+ "	  BEGIN SET numemple = 0; SET salariomedio= 0; SELECT COUNT(*) INTO numemple\r\n"
					+ "	  FROM empleados WHERE dept_no = d; IF ( numemple = 0 ) THEN SET salariomedio =\r\n"
					+ "	  0; ELSE SELECT COUNT(*), AVG(salario) INTO numemple, salariomedio FROM\r\n"
					+ "	  empleados WHERE dept_no = d; END IF; END;";

			// System.out.println(sql);
			Statement sentencia = conexion.createStatement();
			int filas = 0;
			try {
				filas = sentencia.executeUpdate(sql);
				System.out.println("Filas afectadas: " + filas);
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("C�d error : %s %n", e.getErrorCode());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexi�n

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void borroProcedSiExiste(Connection conexion) throws SQLException {
		String sql2 = "DROP PROCEDURE `actividad2_12`;";

		// System.out.println(sql);
		Statement sentencia2 = conexion.createStatement();
		int filas2 = 0;
		try {
			filas2 = sentencia2.executeUpdate(sql2);
			System.out.println("Filas afectadas: " + filas2);
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("C�d error : %s %n", e.getErrorCode());
		}
	}

	private static void usaOracle() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO",
					"EJEMPLO");

			// recuperar parametro de main
			String dep = "10"; // departamento

			// 1 --- DEVUELVE (RETURN) EL salMed FLOAT
			// 2 --- RECIBE EL DEP INT
			// 3 --- nEMP Parámetro salida (OUT) INT
			// CONSTRUIR ORDEN DE LLAMADA
			String sql = " {? = call actividad12 (?, ?) }"; // el return, 1º salida, 2º salida al ser proced. ?,?,?

			// Preparar la llamada
			CallableStatement llamada = conexion.prepareCall(sql);

			// REGISTRAR PARÁMETRO DE RESULTADO el return.
			llamada.registerOutParameter(1, Types.FLOAT);
			// Dar valor a los argumentos
			llamada.setInt(2, Integer.parseInt(dep)); // SEGUNDO ?. LA ENTRADA (DEP)

			// registrar VALORES DE SALIDA
			llamada.registerOutParameter(3, Types.INTEGER); // el segundo ? parámetro OUT numero de empleados

			llamada.executeUpdate(); // ejecutar el procedimiento

			// Preparamos la consulta PARA MOSTRAR LOS DATOS.
			Statement sentencia = conexion.createStatement();
			String sql2 = "SELECT * FROM departamentos";
			ResultSet resul = sentencia.executeQuery(sql2);

			// Recorremos el resultado para visualizar cada fila. Los nº son cada columna -
			// numero - nombre - ciudad
			// Se hace un bucle mientras haya registros y se van visualizando

			System.out.println("ID NOMBRE DEP    LOCALIDAD        NÚMERO EMPLE SALARIO MEDIO");
			System.out.println("== ============= ================ ============ =============");
			while (resul.next()) {
				llamada.setInt(2, resul.getInt(1)); // Por cada resul, ASIGNAMOS, el nuevo valor de dep
				llamada.executeUpdate(); // EJECUTAR EL PROCEDIMIENTO
				float media = llamada.getFloat(1);
				int contador = llamada.getInt(3);
				System.out.printf("%d %-13s %-16s %-12d %-13.2f %n", resul.getInt(1), resul.getString(2),
						resul.getString(3), contador, media);
			} // %-2d %-13s %-16s %-12d %.2f %n

			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			llamada.close();
			conexion.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void crearFuncionOracle() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EJEMPLO",
					"EJEMPLO");

			String sql = "CREATE OR REPLACE NONEDITIONABLE FUNCTION actividad12 (\r\n" + "    d        NUMBER,\r\n"
					+ "    numemple OUT NUMBER\r\n" + ") RETURN NUMBER AS\r\n" + "    salariomedio NUMBER;\r\n"
					+ "    dep          NUMBER;\r\n" + "BEGIN\r\n" + "    SELECT\r\n" + "        dept_no\r\n"
					+ "    INTO dep\r\n" + "    FROM\r\n" + "        departamentos\r\n" + "    WHERE\r\n"
					+ "        dept_no = d;\r\n" + "     \r\n"
					+ "      --SI EXISTE Y NO TIENE EMPLEADOS DEBE DEVOLVER 0 COMO SALARIO MEDIO\r\n" + "    SELECT\r\n"
					+ "        COUNT(*)\r\n" + "    INTO numemple\r\n" + "    FROM\r\n" + "        empleados\r\n"
					+ "    WHERE\r\n" + "        dept_no = d;\r\n" + "\r\n" + "    IF ( numemple = 0 ) THEN\r\n"
					+ "        salariomedio := 0;\r\n" + "    ELSE\r\n"
					+ "       --NUMERO DE EMPLEADOS DEL DEPARTAMENTO\r\n"
					+ "      --SALARIO MEDIO DE LOS EMPLEADOS DEL DEP\r\n" + "        SELECT\r\n"
					+ "            COUNT(*),\r\n" + "            AVG(salario)\r\n" + "        INTO\r\n"
					+ "            numemple,\r\n" + "            salariomedio\r\n" + "        FROM\r\n"
					+ "            empleados\r\n" + "        WHERE\r\n" + "            dept_no = d;\r\n" + "\r\n"
					+ "    END IF;\r\n" + "\r\n" + "    RETURN salariomedio;\r\n"
					+ "     --SI EL DEPARTAMENTO NO EXISTE DEBE DEVOLVER COMO SALARIO MEDIO EL VALOR -1 Y EL Nº DE EMPLEADOS 0.\r\n"
					+ "EXCEPTION\r\n" + "    WHEN no_data_found THEN\r\n" + "        numemple := 0;\r\n"
					+ "        RETURN -1;\r\n" + "END;";

			System.out.println(sql);
			Statement sentencia = conexion.createStatement();
			int filas = 0;
			try {
				filas = sentencia.executeUpdate(sql);
				System.out.println("Filas afectadas: " + filas);
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCI�N:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("C�d error : %s %n", e.getErrorCode());
			}

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexi�n

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

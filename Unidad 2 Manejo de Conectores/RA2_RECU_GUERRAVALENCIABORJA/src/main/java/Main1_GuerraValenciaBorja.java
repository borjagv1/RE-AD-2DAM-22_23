import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main1_GuerraValenciaBorja {
	static Connection conexion = null;
	static PreparedStatement sentencia = null;
	static ResultSet rs, rs2, rs3, rs4, rs5 = null;
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String usuario = "CAFETERIA";
	static String password = "CAFETERIA";

	public static void main(String[] args) {
		// Cargar el driver
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conexion = DriverManager.getConnection(url, usuario, password);
			// Creamos la consulta sql para obtener las categorías
			String sql = "SELECT * FROM categories";

			// Preparamos la sentencia con PreparedStatement
			sentencia = conexion.prepareStatement(sql);

			// Ejecutamos la sentencia y obtenemos el resultado en el ResultSet
			rs = sentencia.executeQuery();

			// Recorremos el ResultSet para visualizar los datos
			while (rs.next()) {
				System.out.println("------------------------------------------------------------------");
				System.out.println("CATEGORÍA: " + rs.getInt("id") + " Nombre: " + rs.getString("name"));
				platos();
				menus();

			} // primer while categorías

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// por si no controlamos cualquier excepción aquí se coge todo
			e.printStackTrace();
		} finally {
			// Cerrar conexión y recursos
			try {
				if (rs != null)
					rs.close();
				System.out.println("\nPrimer resultSet cerrado?: " + rs.isClosed());
				if (rs2 != null)
					rs2.close();
				System.out.println("Segundo resultSet cerrado?: " + rs2.isClosed());
				if (rs != null)
					rs3.close();
				System.out.println("Tercer resultSet cerrado?: " + rs2.isClosed());
				if (rs != null)
					rs4.close();
				System.out.println("Cuarto resultSet cerrado?: " + rs2.isClosed());
				if (sentencia != null)
					sentencia.close();
				System.out.println("Sentencia cerrada?: " + sentencia.isClosed());
				if (conexion != null)
					conexion.close();
				System.out.println("Conexion cerrada?: " + conexion.isClosed());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	} // MAIN

	private static void menus() throws SQLException {
		// Creamos la consulta sql para obtener los menus de cada categoría
		String sql3 = "SELECT P.ID, P.NAME AS MENU_NAME, P.PRICE, DM.ORDEN, PR.NAME AS DISH_NAME FROM PRODUCTS P JOIN DISHES_MENUS DM ON P.ID = DM.MENU_ID JOIN PRODUCTS PR ON DM.DISH_ID = PR.ID WHERE P.CATEGORY_ID = ?";
		// Preparamos la sentencia con PreparedStatement
		sentencia = conexion.prepareStatement(sql3, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		// damos valor a la sentencia preparada
		sentencia.setInt(1, rs.getInt("id"));
		// Ejecutamos la sentencia y obtenemos el resultado en el ResultSet
		rs4 = sentencia.executeQuery();
		// compruebo si hay resultados, si no pongo: << NO HAY MENUS EN LA CATEGORÍA>>
		if (rs4.next()) {
			rs4.previous();
			// Creo la cabecera para que muestre formateado MENUS ID NOMBRE ORDEN PLATO
			System.out.println("\nMENUS ID  NOMBRE                         ORDEN  PLATO");
			System.out.println(
					"      === ============================== ====== =========================================================");
			// Creamos variables para guardar los datos del menú actual
			int MenuIdActual = -1;
			int ordenActual = 1;

			// Recorremos el ResultSet e imprimimos cada fila
			while (rs4.next()) {
				int menuId = rs4.getInt(1);
				String menuName = rs4.getString(2);
				float menuPrice = rs4.getFloat(3);
				int orden = rs4.getInt(4);
				String plato = rs4.getString(5);

				// Si este es un nuevo menú, guardamos los datos y los imprimimos
				if (menuId != MenuIdActual) {
					// Imprimimos los datos del menú actual solo si el ID es distinto del que
					// hubiera anteriormente
					// ASí aseguramos que se imprima una vez el menú que haya pero si muestre todos
					// los platos

					System.out.printf("%-5s %-39s %-1s %-5s%n", "",
							menuId + " " + menuName + " (" + menuPrice + " eur.)", orden,
							plato);
					// Imprimimos los datos del plato

					// Actualizamos los datos del menú actual
					MenuIdActual = menuId;

				}
				// Hago lo mismo con el orden para que se imprima una sola vez
				if (orden != ordenActual) {

					// Imprimimos los datos del plato
					System.out.printf("%-10s %-34s %-1d %-30s%n", "", "", orden, plato);
					ordenActual = orden;
				}
			}

			// Imprimimos los datos del último menú
			// System.out.printf("%-10s %-20s %-10s %-30s\n", "",
			// currentMenuId + " " + currentMenuName + " (" + currentMenuPrice + " €)", "",
			// "");
		} else {
			System.out.println("\n<<NO HAY MENÚS EN LA CATEGORÍA>>");
		}
	}

	private static void platos() throws SQLException {
		// Creamos la consulta sql para obtener los productos de cada categoría
		String sql2 = "SELECT * FROM products WHERE category_id = ? and type = 'plato'";
		// Preparamos la sentencia con PreparedStatement
		sentencia = conexion.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		// Damos valor a los parámetros de la sentencia
		sentencia.setInt(1, rs.getInt("id"));
		// Ejecutamos la sentencia y obtenemos el resultado en el ResultSet
		rs2 = sentencia.executeQuery();
		// compruebo si hay resultados, si no pongo: << NO HAY PLATOS EN LA CATEGORÍA>>
		if (rs2.next()) {
			rs2.previous();
			// Creo la cabecera para que muestre formateado PLATOS ID NOMBRE PVP ALERGENOS
			System.out.println("PLATOS ID  NOMBRE                  PVP ALÉRGENOS");
			System.out.println(
					"       === ==================== ====== =========================================================");
			// Recorremos el ResultSet para visualizar los datos
			while (rs2.next()) {
				System.out.printf("%9s %-24s%-5s%-50s\n", rs2.getInt("ID"), rs2.getString("NAME"),
						rs2.getFloat("PRICE"), dameAllergens(rs2.getInt("ID")));
			} // Segundo while
		} else {
			System.out.println("<<NO HAY PLATOS EN LA CATEGORÍA>>");
		}
	}

	private static Object dameAllergens(int idproducto) {
		String allergens = null;
		StringBuilder cadena = new StringBuilder();
		String sql = "SELECT allergens.name FROM allergens, allergens_products, products WHERE allergens_products.product_id = ? AND allergens.id = allergens_products.allergen_id GROUP BY ALLERGENS.NAME order by allergens.name";
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, idproducto);
			rs3 = sentencia.executeQuery();
			while (rs3.next()) {
				allergens = rs3.getString("name");
				cadena.append(allergens).append(", ");
			}
			if (cadena.length() > 2) {
				cadena.setLength(cadena.length() - 2); // Eliminar la coma y el espacio extra al final de la cadena
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "("+cadena+")";
	}

}// CLass
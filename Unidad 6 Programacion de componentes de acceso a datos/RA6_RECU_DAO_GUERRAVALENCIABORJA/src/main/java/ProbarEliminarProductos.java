import java.util.ArrayList;

import java.util.Scanner;

import dao.AlergenosDAO;
import dao.CategoriasDAO;
import dao.ProductoDAO;
import datos.PlatosMenus;
import datos.Productos;
import factory.DAOFactory;

//PARTIMOS DE LOS DATOS INICIALES DE LA BD NEODATIS, MONGO, MYSQL
//CREAMOS UN MENU CON VARIOS PLATOS
//ACTUALIZAMOS TODO
//Se prueba eliminar un producto de tipo plato
//Se prueba eliminar un producto de tipo menú

public class ProbarEliminarProductos {

	static Scanner sc = new Scanner(System.in);
	
	static DAOFactory bd;
	static CategoriasDAO catDAO;
	static ProductoDAO prodDAO;
	static AlergenosDAO alDAO;

	public static void main(String[] args) {

		int opcion = pintarmenu();
		String mensaje="";
		switch(opcion) {
		case 1:
			mensaje = "Probando ELIMINAR PRODUCTOS CASCADE MYSQL";
			bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			break;
		case 2:
			mensaje = "Probando ELIMINAR PRODUCTOS CASCADE MONGODB";
			bd = DAOFactory.getDAOFactory(DAOFactory.MONGO);
			break;
		case 3:
			mensaje = "Probando ELIMINAR PRODUCTOS CASCADE NEODATIS";
			bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
			break;
		default:
			System.exit(0);
		}
		
		catDAO = bd.getCategoriasDAO();
		prodDAO = bd.getProductoDAO();
		alDAO = bd.getAlergenosDAO();

		System.out.println("====================================================================================");
		System.out.println(mensaje);
		System.out.println("====================================================================================");


		// CREAR MENU 100
		// INSERTAR PRODUCTO 100
		Productos p = new Productos(100, "Menu casero I", 0.0, "menu", 1, 0, "");
		prodDAO.InsertarProducto(p);
		System.out.println("--");
		// INSERTAR PLATOS EN EL MENU 100
		prodDAO.InsertarMenu(100, 1);
		prodDAO.InsertarMenu(100, 4);
		prodDAO.InsertarMenu(100, 33);
		prodDAO.InsertarMenu(100, 5);
		prodDAO.InsertarMenu(100, 3);
		
		// ACTUALIZAR TODO
		prodDAO.ActualizarDatos();
		alDAO.ActualizarDatos();
		catDAO.ActualizarDatos();		
		
		eliminarProductoPlato();
		
		eliminarProductoMenu();
		

		// VERMENU(100);

	}

	private static void eliminarProductoMenu() {
		System.out.println("====================================================================================");
		System.out.println("Probando ELIMINAR PRODUCTOS CASCADE (MENU)");
		//System.out.println("====================================================================================");
		
		VERMENU(100);
		
		System.out.println("\nEliminamos el menu 100: ");
		
		prodDAO.EliminarProductoCascada(100);
		
		VERMENU(100);
		
	}

	private static void eliminarProductoPlato() {
		System.out.println("====================================================================================");
		System.out.println("Probando ELIMINAR PRODUCTOS CASCADE (PLATO)");
		System.out.println("====================================================================================");
		
		System.out.println("\nMOSTRANDO DATOS DEL PRODUCTO 1");
		// VER PRODUCTO 1
		System.out.println(prodDAO.ConsultarProducto(1));

		// VER CATEGORIA DEL PRODUCTO 1, ES 1
		System.out.println("\nMOSTRANDO CATEGORIA DEL PRODUCTO 1");
		System.out.println(catDAO.ConsultarCategoria(1));

		// VERALERGENOS DEL PRODUCTO 1 (1 , 4 , 9)
		System.out.println("\nMOSTRANDO alergenos DEL PRODUCTO 1");
		System.out.println(alDAO.ConsultarAlergeno(1));
		System.out.println(alDAO.ConsultarAlergeno(4));
		System.out.println(alDAO.ConsultarAlergeno(9));
		System.out.println("=================================================");

		System.out.println("\nELIMINANDO EN CASCADA EL PRODUCTO 1 (plato): ");
		prodDAO.EliminarProductoCascada(1);

		System.out.println("=================================================");
		System.out.println("comprobar si el plato 1 se ha eliminado de los menus 100 y 37: ");
		
		VERMENU(100);
		VERMENU(37);
		
		System.out.println("=================================================");

		System.out.println("\nMOSTRANDO DATOS DEL PRODUCTO 1");
		// VER PRODUCTO 1
		System.out.println(prodDAO.ConsultarProducto(1));

		// VER CATEGORIA DEL PRODUCTO 1, ES 1
		System.out.println("\nMOSTRANDO CATEGORIA DEL PRODUCTO 1");
		System.out.println(catDAO.ConsultarCategoria(1));

		// VERALERGENOS DEL PRODUCTO 1 (1 , 4 , 9)
		System.out.println("\nMOSTRANDO alergenos DEL PRODUCTO 1");
		System.out.println(alDAO.ConsultarAlergeno(1));
		System.out.println(alDAO.ConsultarAlergeno(4));
		System.out.println(alDAO.ConsultarAlergeno(9));
		
	}

	private static void VERMENU(int id) {
		System.out.println("====================================================================================");
 
		System.out.println(prodDAO.ConsultarProducto(id));
		
		ArrayList<PlatosMenus> lista;
		lista = prodDAO.ConsultarMenu(id);
		for (PlatosMenus l : lista) {
			System.out.println(l);
		}
	}
	
	private static int pintarmenu() {
		System.out.println("1  MYSQL. ");
		System.out.println("2  MONGODB. ");
		System.out.println("3  NEODATIS.");
		return leerEntero();
	}

	private static int leerEntero() {

		int dep = 0;
		while (dep < 1 || dep > 3) {
			System.out.print("ESCRIBE EL Nº DE LA BASE DE DATOS : ");

			try {
				dep = sc.nextInt();
			} catch (java.util.InputMismatchException ex) {
				System.out.println("\tNúmero incorrecto...");
			}
			sc.nextLine();
		}
		return dep;
	}

}

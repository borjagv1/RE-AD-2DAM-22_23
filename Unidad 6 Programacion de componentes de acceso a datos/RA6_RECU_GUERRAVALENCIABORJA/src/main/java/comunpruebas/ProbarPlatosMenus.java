package comunpruebas;

import java.util.ArrayList;

import dao.AlergenosDAO;
import dao.CategoriasDAO;
import dao.ProductoDAO;
import datos.PlatosMenus;
import datos.Productos;
import factory.DAOFactory;


import java.util.Scanner;


//PARTIMOS DE LOS DATOS INICIALES DE LA BD NEODATIS, MYSQL, MONGO
//CREAMOS el  MENU 100 CON VARIOS PLATOS

//Se prueba eliminar platos al menu
//Se prueba añadir platos al menu
//Se prueba consultar producto y consultar platos de menu.

public class ProbarPlatosMenus {

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
			mensaje = "Probando  MYSQL  PLATOS-MENUS";
			bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			break;
		case 2:
			mensaje = "Probando  MONGODB   PLATOS-MENUS";
			bd = DAOFactory.getDAOFactory(DAOFactory.MONGO);
			break;
		case 3:
			mensaje = "Probando  NEODATIS  PLATOS-MENUS";
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

		System.out.println("PLATOS DEL MENU 100 (1, 4, 33)");
		// CREAR MENU 100
		Productos p = new Productos(100, "Menu casero I", 0.0, "menu", 1, 0, "");
		prodDAO.InsertarProducto(p);
		System.out.println("--");
		// INSERTAR PLATOS EN EL MENU 100
		prodDAO.InsertarMenu(100, 1);
		prodDAO.InsertarMenu(100, 4);
		prodDAO.InsertarMenu(100, 33);

		VERMENU(100);

		System.out.println("====================================================================================");
		System.out.println("ELIMINANDO PLATOS DEL MENU 100: ");

		prodDAO.EliminarPlatoMenu(100, 1);

		VERMENU(100);

		System.out.println("====================================================================================");
		System.out.println("INSERTANDO PLATOS EN EL MENU 100: ");

		// INSERTO
		prodDAO.InsertarMenu(100, 1); 
		prodDAO.InsertarMenu(100, 3);
		prodDAO.InsertarMenu(100, 4);//repe
		prodDAO.InsertarMenu(100, 5);

		VERMENU(100);

		System.out.println("====================================================================================");
		System.out.println("ELIMINANDO PLATOS EN EL MENU 100: ");

		prodDAO.EliminarPlatoMenu(100, 1);
		prodDAO.EliminarPlatoMenu(100, 3);
		
		prodDAO.EliminarPlatoMenu(100, 1);//

		VERMENU(100);
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

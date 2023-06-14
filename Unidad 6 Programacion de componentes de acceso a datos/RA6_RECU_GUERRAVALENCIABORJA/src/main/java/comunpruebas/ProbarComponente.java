package comunpruebas;

import java.util.ArrayList;
import java.util.Scanner;

import dao.*;
import datos.*;

import factory.DAOFactory;

public class ProbarComponente {

	static Scanner sc = new Scanner(System.in);

	static DAOFactory bd;
	static CategoriasDAO catDAO;
	static ProductoDAO prodDAO;
	static AlergenosDAO alDAO;

	public static void main(String[] args) {
		// java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);

		int opcion = pintarmenu();
		String mensaje="";
		switch(opcion) {
		case 1:
			mensaje = "Probando MYSQL";
			bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			break;
		case 2:
			mensaje = "Probando MONGODB";
			bd = DAOFactory.getDAOFactory(DAOFactory.MONGO);
			break;
		case 3:
			mensaje = "Probando NEODATIS";
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

		System.out.println("PRODUCTOS");
		ProbandoProductos();

		System.out.println("====================================================================================");
		System.out.println("ALERGENOS");
		ProbandoAlergenos();
		
		System.out.println("====================================================================================");
		System.out.println("CATEGORIAS");
		ProbandoCategorias();

	}// main

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

	private static void ProbandoCategorias() {
		System.out.println("INSERTANDO CATEGORIAS:");
		Categorias c = new Categorias(1, " NuevaCAt", 0);
		catDAO.InsertarCategoria(c);
		//
		System.out.println("--");
		c = new Categorias(11, " NuevaCAt", 0);
		catDAO.InsertarCategoria(c);
		System.out.println("--");
		// otra vez
		catDAO.InsertarCategoria(c);

		System.out.println("--");
		c = new Categorias(12, " NuevaCAt12", 0);
		catDAO.InsertarCategoria(c);

		System.out.println("====================================================================================");
		System.out.println("ELIMINANDO CATEGORIAS:");
		catDAO.EliminarCategoria(1); // NO PUEDE
		catDAO.EliminarCategoria(2); // NO PUEDE
		catDAO.EliminarCategoria(11); // OK
		catDAO.EliminarCategoria(11); // YA NO EXISTE

		System.out.println("====================================================================================");

		catDAO.ActualizarDatos();

		System.out.println("====================================================================================");

		System.out.println("DATOS DE VARIAS CATEGORIAS DESPUES DE ACTUALIZAR (1, 11, 12)");
		System.out.println(catDAO.ConsultarCategoria(1));
		System.out.println(catDAO.ConsultarCategoria(11));
		System.out.println(catDAO.ConsultarCategoria(12));

		System.out.println("====================================================================================");
		System.out.println("DATOS DE TODOS LAS CATEGORIAS");

		// mostrar
		ArrayList<Categorias> lista = catDAO.TodasLasCategorias();
		for (Categorias l : lista) {
			System.out.println(l);
		}

	}// PROBANDO CATEGORIAS

	private static void ProbandoAlergenos() {
		System.out.println("INSERTANDO ALERGENOS:");
		Alergenos a = new Alergenos(1, " nuevoal", 0, "");
		alDAO.InsertarAlergeno(a);
		//
		System.out.println("--");
		a = new Alergenos(11, " nuevoal", 0, "");
		alDAO.InsertarAlergeno(a);
		System.out.println("--");
		// otra vez
		alDAO.InsertarAlergeno(a);
		
		System.out.println("--");
		a = new Alergenos(12, " nuevoal12", 0, "");
		alDAO.InsertarAlergeno(a);
		System.out.println("--");		

		System.out.println("====================================================================================");

		System.out.println("ELIMINANDO ALERGENOS:");
		alDAO.EliminarAlergeno(1); // NO PUEDE
		alDAO.EliminarAlergeno(11); // OK
		alDAO.EliminarAlergeno(11); // YA NO EXISTE

		// insertar alergeno en productos
		System.out.println("====================================================================================");
		System.out.println("INSERTANDO ALERGENOS en productos que existen y que no existen:");

		System.out.println("NO existe Alergeno 111 NI PRODUCto 555:");
		alDAO.InsertarAlergenoProducto(111, 555); // no existe alergeno 111 NI PRODUC
		System.out.println("\nNO existe Alergeno 111:");
		alDAO.InsertarAlergenoProducto(111, 55); // no existe alergeno 111

		System.out.println("\nINSERTANDO ALERGENO 2 EN PRODUCTO 55:");
		alDAO.InsertarAlergenoProducto(2, 55); // ok
		System.out.println("\nINSERTANDO ALERGENO 2 EN PRODUCTO 55 (ya existe):");
		alDAO.InsertarAlergenoProducto(2, 55); // no, ya existe
		System.out.println("\nINSERTANDO ALERGENO 3 EN PRODUCTO 55:");
		alDAO.InsertarAlergenoProducto(3, 55);

		System.out.println("====================================================================================");

		alDAO.ActualizarDatos();

		System.out.println("====================================================================================");
		System.out.println("DATOS DE ALERGENOS DESPUES DE ACTUALIZAR");

		System.out.println("DATOS DE varios ALERGENOS");
		System.out.println(alDAO.ConsultarAlergeno(3));
		System.out.println(alDAO.ConsultarAlergeno(5));
		System.out.println(alDAO.ConsultarAlergeno(2));

		System.out.println("====================================================================================");
		System.out.println("DATOS DE TODOS LOS ALERGENOS");
		// mostrar
		ArrayList<Alergenos> lista = alDAO.TodosLosAlergenos();
		for (Alergenos l : lista) {
			System.out.println(l);
		}

	}// ProbandoAlergenos

	private static void ProbandoProductos() {
		System.out.println("INSERTANDO PRODUCTOS:");
		// INSERTO PLATO
		Productos pp = new Productos(55, "Plato 55", 10.0, "plato", 2, 0, "");
		prodDAO.InsertarProducto(pp);

		System.out.println("--");
		// INSERTAR PRODUCTO 100 en cat que no existe
		Productos p = new Productos(100, "Menu casero I", 0.0, "menu", 123, 0, "");
		prodDAO.InsertarProducto(p);
		System.out.println("--");

		// INSERTAR PRODUCTO 100
		p = new Productos(100, "Menu casero I", 0.0, "menu", 1, 0, "");
		prodDAO.InsertarProducto(p);
		System.out.println("--");

		// VUELVO A INSERTAR
		System.out.println("INSERTANDO UN ID QUE YA EXISTE:");
		prodDAO.InsertarProducto(p);
		System.out.println("--");

		System.out.println("=========================================");
		System.out.println("INSERTANDO MENU 100 Y SUS PLATOS (33 repetido 456 no existe):");
		// INSERTAR PLATOS EN EL MENU 100
		prodDAO.InsertarMenu(100, 1);
		prodDAO.InsertarMenu(100, 4);
		prodDAO.InsertarMenu(100, 33);
		prodDAO.InsertarMenu(100, 33);// repe
		prodDAO.InsertarMenu(100, 456);// PLATO NO EXISTE

		System.out.println("=========================================");
		System.out.println("INSERTANDO MENU Y PLATOS QUE NO EXISTE SU ID:");
		prodDAO.InsertarMenu(666, 456);

		System.out.println("=======================================");
		System.out.println("MOSTRANDO LOS PLATOS DEL MENÚ 100");
		ArrayList<PlatosMenus> lista = prodDAO.ConsultarMenu(100);
		for (PlatosMenus l : lista) {
			System.out.println(l);
		}

		// actualizar
		prodDAO.ActualizarDatos();

		// mostrar platos
		System.out.println("====================================================================================");
		System.out.println("DATOS DE VARIOS PRODUCTOS DESPUES DE ACTUALIZAR");
		System.out.println(prodDAO.ConsultarProducto(3));
		System.out.println(prodDAO.ConsultarProducto(100));

		System.out.println("====================================================================================");
		System.out.println("DATOS DE TODOS LOS PRODUCTOS");

		// mostrar
		ArrayList<Productos> lista2 = prodDAO.TodosLosProductos();
		for (Productos l : lista2) {
			System.out.println(l);
		}

		System.out.println("====================================================================================");

	}// ProbandoProductos

}// FIN
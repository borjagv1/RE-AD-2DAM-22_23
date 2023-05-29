package creacionbasededatos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.time.LocalDate;

import datos2022.*;


//creo ficheros de objetos para crear la coleccion
public class CrearFichObjetos2022 {

	static File oficinas = new File("Oficinas.dat"); // aleatorio
	static File repventas = new File("Repventas.dat"); // aleatorio
	static File ventas = new File("Ventas.dat"); // sec
	static File productos = new File("Productos.dat"); // sec

	public static void proceso() throws IOException, ClassNotFoundException {
		llenarOficinas();
		listarOficinas();

		llenarRepventas();
		listarRepventas();

		llenarVentas();
		listarVentas();

		llenarProductos();
		listarProductos();

	}

	private static void listarProductos() throws ClassNotFoundException, IOException {
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(productos));

		System.out.println("=================================================");
		System.out.println("Productos ");
		try {
			while (true) { // lectura del fichero
				System.out.println(dataIS.readObject());
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA Productos.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada

	}

	private static void listarVentas() throws ClassNotFoundException, IOException {
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(ventas));

		System.out.println("=================================================");
		System.out.println("Ventas ");
		try {
			while (true) { // lectura del fichero
				System.out.println(dataIS.readObject());
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA Ventas.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada

	}

	private static void listarRepventas() throws ClassNotFoundException, IOException {
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(repventas));

		System.out.println("=================================================");
		System.out.println("Repventas ");
		try {
			while (true) { // lectura del fichero
				System.out.println(dataIS.readObject());
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA Repventas.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
		// TODO Auto-generated method stub

	}

	private static void listarOficinas() throws ClassNotFoundException, IOException {
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(oficinas));

		System.out.println("=================================================");
		System.out.println("Oficinas ");
		try {
			while (true) { // lectura del fichero
				System.out.println(dataIS.readObject());
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA Oficinas.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada

	}

	private static void llenarProductos() throws IOException {
		if (productos.exists()) {
			productos.delete();
		}

		FileOutputStream fileout = new FileOutputStream(productos);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		dataOS.writeObject(new Productos(1, "Diccionario Maria Moliner 2 tomos", 55, 5, 43.00));
		dataOS.writeObject(new Productos(2, "Impresora HP Deskjet F370", 10, 1, 30.65));
		dataOS.writeObject(new Productos(3, "Pen Drive 8 Gigas", 52, 5, 7.00));
		dataOS.writeObject(new Productos(4, "Ratón óptico inalámbrico Logitecht", 14, 2, 15.00));
		dataOS.writeObject(new Productos(5, "El señor de los anillos, 3 DVDs", 8, 2, 25.00));

		dataOS.close();
		fileout.close();

		// TODO Auto-generated method stub

	}

	private static void llenarVentas() throws IOException {
		if (ventas.exists()) {
			ventas.delete();
		}

		FileOutputStream fileout = new FileOutputStream(ventas);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		LocalDate fecha = LocalDate.now();

		dataOS.writeObject(new Ventas(1, fecha, 101, 3, 3));
		dataOS.writeObject(new Ventas(2, fecha.minusDays(17), 104, 2, 2));
		dataOS.writeObject(new Ventas(3, fecha.minusDays(7), 102, 2, 1));
		dataOS.writeObject(new Ventas(4, fecha.minusMonths(4), 101, 1, 5));
		dataOS.writeObject(new Ventas(6, fecha.minusDays(2), 103, 1, 1));
		dataOS.writeObject(new Ventas(7, fecha.minusMonths(1), 102, 2, 1));
		dataOS.writeObject(new Ventas(8, fecha, 101, 5, 5));
		dataOS.writeObject(new Ventas(9, fecha.minusMonths(2), 103, 2, 1));
		dataOS.writeObject(new Ventas(10, fecha, 103, 5, 20)); //stoc negativo
		
		

		dataOS.close();
		fileout.close();

		// TODO Auto-generated method stub

	}

	private static void llenarRepventas() throws IOException {
		if (repventas.exists()) {
			repventas.delete();
		}

		FileOutputStream fileout = new FileOutputStream(repventas);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		// numero_rep, nombre, edad, oficina_rep, director, num_ventas, imp_ventas)
		// las oficinas y directores pueden ser 0, no tienen

		dataOS.writeObject(new RepVentas(110, "Antonio Casado", 41, 11, 101, 2, 707));
		dataOS.writeObject(new RepVentas(106, "José Sánchez", 52, 11, 0, 15, 1612));
		dataOS.writeObject(new RepVentas(109, "Maria Gutierrez", 31, 11, 106, 2, 1999));
		dataOS.writeObject(new RepVentas(103, "Pablo Cruz", 29, 12, 104, 5, 1286));
		dataOS.writeObject(new RepVentas(101, "David Ruiz", 45, 12, 104, 2, 2001));
		dataOS.writeObject(new RepVentas(104, "Juan Agudo", 33, 12, 106, 12, 2200));
		dataOS.writeObject(new RepVentas(105, "Pedro Adams", 37, 13, 104, 23, 2054));
		dataOS.writeObject(new RepVentas(108, "Carlos Figo", 62, 21, 106, 3, 2100));
		dataOS.writeObject(new RepVentas(102, "Sara Agudo", 48, 21, 108, 22, 2789));
		dataOS.writeObject(new RepVentas(107, "Juani Annea", 49, 22, 108, 4, 1090));

		dataOS.writeObject(new RepVentas(200, "Anonimo", 0, 0, 0, 7, 1590));
		dataOS.writeObject(new RepVentas(201, "Anonima", 0, 0, 0, 3, 3500));

		dataOS.close();
		fileout.close();

		// TODO Auto-generated method stub

	}

	private static void llenarOficinas() throws IOException {
		if (oficinas.exists()) {
			oficinas.delete();
		}

		FileOutputStream fileout = new FileOutputStream(oficinas);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		// las que no tienen director, el valor es 0

		dataOS.writeObject(new Oficinas(11, "Alicante", 2, 106, 4900));
		dataOS.writeObject(new Oficinas(12, "Barcelona", 3, 104, 4900));
		dataOS.writeObject(new Oficinas(13, "Valencia", 2, 105, 2303));
		dataOS.writeObject(new Oficinas(21, "Cádiz", 1, 108, 4988));
		dataOS.writeObject(new Oficinas(22, "Sevilla", 1, 108, 1780));
		dataOS.writeObject(new Oficinas(23, "Huelva", 1, 0, 1000));
		dataOS.writeObject(new Oficinas(30, "Guadalajara", 4, 0, 1000));

		dataOS.close();
		fileout.close();

	}

}

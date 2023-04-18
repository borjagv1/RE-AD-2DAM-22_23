package ejercicioClaseFichDepartAleatorio;

//Ejercicio puesto en clase Crear fichero departamentos pidiendo por teclado los datos hasta que se escriba 0.
//Visualizar todos los datos al finalizar
//Int numero departamento (clave = 4bytes)
//String nombre de depart (12 caracteres)
//string localidad (15 caracteres)
//En papas está subido otra clase parecida que se llama CrearFichAleatorioDepartamentos
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CrearFichAleatorio_departamentos {
	static File fichero = new File("AleatorioDepart.dat");
	static RandomAccessFile file;
	static Scanner sc = new Scanner(System.in);
	static long longitud = 4 + 24 + 30; // id = 4 bytes, 12 caracteres = 2bytes = 24, 15 caracteres = 30

	public static void main(String[] args) throws IOException {

		file = new RandomAccessFile(fichero, "rw");
		int dep;
		String nombre, loc;

		while (true) {
			dep = leerEntero("Numero de departamento:", 0, 99);
			if (dep <= 0)
				break;

			if (!ExisteDepartamento(dep)) {

				long posicion = (dep - 1) * longitud;
				file.seek(posicion); // nos posicionamos

				file.writeInt(dep);

				System.out.println("Nombre de departamento");
				nombre = sc.nextLine();// 12

				StringBuffer buffer = new StringBuffer(nombre);
				buffer.setLength(12); // 12 caracteres para el nombre
				file.writeChars(buffer.toString()); // Insertar nombre

				System.out.println("Localidad de departamento:");
				loc = sc.nextLine();// 15

				StringBuffer buffer2 = new StringBuffer(loc);
				buffer2.setLength(15); // 15 caracteres para loc
				file.writeChars(buffer2.toString()); // Insertar loc

				System.out.println("Registro insertado");
			} else {
				System.out.println("\tEl depart existe");
			}

			System.out.println("=======================================================");

		}

		file.close();

		try {
			VisualizarDatos();
		} catch (java.io.EOFException ex) {
			System.out.println("Fin del programa");
		}
	}// Main

	@SuppressWarnings("resource")
	public static boolean ExisteDepartamento(int dep) throws IOException { // Lo pongo en public para poder usarlo en la
																			// calse modificar
		// departamento
		RandomAccessFile raf = new RandomAccessFile(fichero, "r");
		// departamento
		// Variables
		boolean existe = false;
		long pos;

		pos = (dep - 1) * longitud; // nos posicionamos en el dep

		if (pos >= raf.length())
			return existe;
		else {
			raf.seek(pos);// Nos posicionamos
			int id = raf.readInt(); // Obtengo ID
			if (id == dep)
				existe = true;
		}

		return existe;

		//Mi forma
//		boolean existe = false;
//
//		try {
//			RandomAccessFile raf = new RandomAccessFile(fichero, "r");
//			// Variables
//			int pos;
//			pos = (int) ((dep - 1) * longitud); // nos posicionamos en el dep
//
//			try {
//				raf.seek(pos);
//				int depexist = raf.readInt();
//				if (depexist == dep) {
//					existe = true;
//					return existe;
//				}
//				raf.close();
//
//			} catch (IOException e) {
//			}
//
//		} catch (FileNotFoundException e) {
//			System.out.println("Fichero no encontrado para ver si existe Dep");
//		}
//
//		return existe;
	} // Fin ExisteDep

	public static int leerEntero(String mensaje, int min, int max) {
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

	public static void VisualizarDatos() throws IOException {
		@SuppressWarnings("resource")
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		int id;
		char nombredep[] = new char[12], aux1;
		char localidad[] = new char[15], aux2;
		long posicion = 0; // para situarnos al principio
		for (;;) { // recorro el fichero

			file.seek(posicion); // nos posicionamos en posicion
			// System.out.println("Posicion: "+ posicion + ", puntero: " +
			// file.getFilePointer());

			id = file.readInt(); // obtengo id de empleado
			// nombre de departamento
			for (int i = 0; i < nombredep.length; i++) {
				aux1 = file.readChar();
				nombredep[i] = aux1; // los voy guardando en el array
			}

			String nombredepS = new String(nombredep);

			// localidad
			for (int i = 0; i < localidad.length; i++) {
				aux2 = file.readChar();
				localidad[i] = aux2; // los voy guardando en el array
			}
			// convierto a String el array
			String localidadS = new String(localidad);
			if (id > 0)
				System.out.printf("ID: %d, Nombre departamento: %s, Localidad: %s %n", id, nombredepS.trim(),
						localidadS.trim());

			// me posiciono para el sig empleado, cada empleado ocupa 58 bytes
			posicion = posicion + 58;

			// Si he recorrido todos los bytes salgo del for
			if (file.getFilePointer() == file.length())
				break;

		} // fin bucle for

		file.close(); // cerrar fichero

	}// Visualizar datos
}// clase

/*
 * static File fichero = new File("AleatorioDepart.dat"); public static void
 * main(String[] args) throws IOException { // declara el fichero de acceso
 * aleatorio
 * 
 * if(fichero.exists()) { fichero.delete(); }
 * 
 * RandomAccessFile file = new RandomAccessFile(fichero, "rw"); // arrays con
 * los datos int dep[] = { 10, 20, 30, 40 }; // departamentos String nombredep[]
 * = { "VENTAS", "CONTABILIDAD", "DIRECCION", "PRODUCCION" };// apellidos String
 * localidad[] = { "SEVILLA", "MADRID", "BILBAO", "VALENCIA" }; StringBuffer
 * buffer = null; // buffer para almacenar apellido int posicion; int id; int n
 * = nombredep.length;// numero de elementos del array for (int i = 0; i < n;
 * i++) { // recorro los arrays id = dep[i]; posicion = (id - 1) * 58;
 * file.seek(posicion); file.writeInt(id); buffer = new
 * StringBuffer(nombredep[i]); buffer.setLength(12); // nombre dep
 * file.writeChars(buffer.toString());// buffer = new
 * StringBuffer(localidad[i]); buffer.setLength(15); // LOCALIDAD
 * file.writeChars(buffer.toString());// System.out.println("Departamento " + id
 * + " insertado... "); } file.close(); // cerrar fichero VisualizarDatos(); }
 * private static void VisualizarDatos() throws IOException { RandomAccessFile
 * file = new RandomAccessFile(fichero, "r"); int id, posicion; char nombredep[]
 * = new char[12], aux; char localidad[] = new char[15]; posicion = 0; // para
 * situarnos al principio for (;;) { // recorro el fichero
 * 
 * file.seek(posicion); // nos posicionamos en posicion
 * //System.out.println("Posicion: "+ posicion + ", puntero: " + //
 * file.getFilePointer());
 * 
 * id = file.readInt(); // obtengo id de empleado // nombre de departamento for
 * (int i = 0; i < nombredep.length; i++) { aux = file.readChar(); nombredep[i]
 * = aux; // los voy guardando en el array }
 * 
 * String nombredepS = new String(nombredep); // localidad for (int i = 0; i <
 * localidad.length; i++) { aux = file.readChar(); localidad[i] = aux; // los
 * voy guardando en el array } // convierto a String el array String localidadS
 * = new String(localidad); if (id > 0)
 * System.out.printf("ID: %d, Nombre departamento: %s, Localidad: %s %n", id,
 * nombredepS, localidadS);
 * 
 * // Si he recorrido todos los bytes salgo del for if (file.getFilePointer() ==
 * file.length()) break;
 * 
 * // me posiciono para el sig empleado, cada empleado ocupa 36 bytes posicion =
 * posicion + 58; } // fin bucle for
 * 
 * file.close(); // cerrar fichero } }
 * 
 */
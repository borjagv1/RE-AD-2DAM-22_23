package ejercicioFichViajesReservasAleatorios;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//INSERTA DATOS EN LOS FICHEROS PERO DEBEN CREARSE ALEATORIOS.
//CONVERTIR SECUENCIALES VIAJES Y CLIENTES A FICH ALEATORIOS
//A PARTIR DE LOS FICHEROS SECUENCIALES Clientes y Viajes CREAR LOS FICHEROS ALEATORIOS.
//LLenar los ficheros aleatorios a partir de los secuenciales.
//VISUALIZAR LOS DATOS.
//Después realizar el mismo proceso de actualización sobre los aleatorios con acceso aleatorio siempre que sea posible.
/*
 *  Registro Clientes del Fichero aleatorio

private int id;

private String nombre; 18 caracteres

private int viajescontratados; nº viajes contratados

private double importetotal; importe total de los viajes realizados

 

Longitud del registro = ¿

Registro Viajes del Fichero aleatorio

private int id;

private String descripcion;  35 caracteres

private String fechasalida;  10 caracteres

private double pvp;

private int dias;      nº dias que dura el viaje

private int viajeros;  nº de viajeros que hacen el viaje

Longitud del registro = ¿


 */
public class CrearFicherosAleatorios {

	static File viajes = new File("Viajes2.dat");
	static File clientes = new File("Clientes2.dat");
	static File reservas = new File("Reservas2.dat");
	static File ficheroerror = new File("Errores2.txt");

	static RandomAccessFile fileV;
	static RandomAccessFile fileC;

	static int RegistroViajes = 4 + 70 + 20 + 8 + 4 + 4;
	static int RegistroClientes = 4 + 36 + 4 + 8;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		//BORRAMOS FICHEROS ANTES DE CREAR PARA NO TENER PROBLEMAS.
		viajes.delete();
		clientes.delete();
		reservas.delete();
		ficheroerror.delete();
		
		crearViajesAleatorio();
		listarViajes();

		crearClientesAleatorio();
		listarClientes();

		llenarreservasSec();

		// crear fichero de errores
		FileWriter fic = new FileWriter(ficheroerror);
		fic.close();

		System.out.println("Fin creación");

	}

	@SuppressWarnings("resource")
	private static void listarClientes() throws IOException {
		File fichero = new File("ClientesAleatorio.dat");
		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "r");

		int id, viajesContratados, posicion;
		char nombre[] = new char[18], aux;
		double importeTotal;

		posicion = 0; // para situarnos al principio //Si se la pos, por ej 6 -> (6-1) * 36

		for (;;) { // recorro el fichero
			file.seek(posicion); // nos posicionamos en posicion
			id = file.readInt(); // obtengo id de empleado

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < nombre.length; i++) {
				aux = file.readChar();
				nombre[i] = aux; // los voy guardando en el array
			}
			// convierto a String el array
			String nombreS = new String(nombre);

			viajesContratados = file.readInt();
			importeTotal = file.readDouble();

			if (id > 0)
				System.out.println(
						"ID: " + id + " - " + nombreS.trim() + " - " + viajesContratados + " - " + importeTotal);
			// me posiciono para el sig empleado, cada empleado ocupa 36 bytes
			posicion = posicion + 52;

			// Si he recorrido todos los bytes salgo del for
			if (file.getFilePointer() == file.length())
				break;

		}

	}

	@SuppressWarnings("resource")
	private static void crearClientesAleatorio() throws IOException, ClassNotFoundException {
		// primero creo un fichero secuencial de objetos Viajes

		llenarClientesSec();
		// a partir de los datos de ese fichero creo el fichero aleatorio
		File fichero = new File("ClientesAleatorio.dat");
		RandomAccessFile fileC = new RandomAccessFile(fichero, "rw");

		StringBuffer bufferNombre = null;// buffer para almacenar Nombre

		Cliente cli;

		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(clientes));

		try {
			while (true) { // lectura del fichero
				cli = (Cliente) dataIS.readObject();
				// 4 + 36 + 4 + 8;; int id string nombre int viajesContratados double
				// importetotal = 52

				int id = cli.getId();
				long posicion = (id - 1) * 52;
				fileC.seek(posicion); // nos posicionamos

				fileC.writeInt(cli.getId());

				bufferNombre = new StringBuffer(cli.getNombre());
				bufferNombre.setLength(18);
				fileC.writeChars(bufferNombre.toString());

				fileC.writeInt(cli.getViajescontratados());
				fileC.writeDouble(cli.getImportetotal());

				System.out.println("Cliente insertado: " + id);

			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA DEL SECUENCIAL.");
		}

		dataIS.close(); // cerrar stream de entrada

	}// CrearClienteAleat

	@SuppressWarnings("resource")
	private static void listarViajes() throws IOException {
		File fichero = new File("ViajesAleatorio.dat");
		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "r");

		int id, dias, viajeros, posicion;
		char descripcion[] = new char[35], aux;
		char fechaSalida[] = new char[10], aux2;
		double pvp;

		posicion = 0; // para situarnos al principio //Si se la pos, por ej 6 -> (6-1) * 36

		for (;;) { // recorro el fichero
			file.seek(posicion); // nos posicionamos en posicion
			id = file.readInt(); // obtengo id de empleado

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < descripcion.length; i++) {
				aux = file.readChar();
				descripcion[i] = aux; // los voy guardando en el array
			}
			// convierto a String el array
			String descripcionS = new String(descripcion);

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < fechaSalida.length; i++) {
				aux2 = file.readChar();
				fechaSalida[i] = aux2; // los voy guardando en el array
			}
			// convierto a String el array
			String fechaSalidaS = new String(fechaSalida);

			pvp = file.readDouble();
			dias = file.readInt();
			viajeros = file.readInt();

			if (id > 0)
				System.out.println("ID: " + id + " - " + descripcionS.trim() + " - " + fechaSalidaS.trim() + " - " + pvp
						+ " - " + dias + " - " + viajeros);
			// me posiciono para el sig empleado, cada empleado ocupa 36 bytes
			posicion = posicion + 110;

			// Si he recorrido todos los bytes salgo del for
			if (file.getFilePointer() == file.length())
				break;

		}
	}

	@SuppressWarnings("resource")
	private static void crearViajesAleatorio() throws IOException, ClassNotFoundException {
		// primero creo un fichero secuencial de objetos Viajes
		llenarViajesSec();
		// a partir de los datos de ese fichero creo el fichero aleatorio
		File fichero = new File("ViajesAleatorio.dat");
		RandomAccessFile fileV = new RandomAccessFile(fichero, "rw");

		StringBuffer bufferDescripcion = null;// buffer para almacenar Descripcion
		StringBuffer bufferFechaSalida = null;// FechaSalida

		Viaje via;

		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes));

		try {
			while (true) { // lectura del fichero
				via = (Viaje) dataIS.readObject();
				// 4 + 70 + 20 + 8 + 4 + 4; int string string double int int = 110

				int id = via.getId();
				long posicion = (id - 1) * 110;
				fileV.seek(posicion); // nos posicionamos

				fileV.writeInt(via.getId());

				bufferDescripcion = new StringBuffer(via.getDescripcion());
				bufferDescripcion.setLength(35);
				fileV.writeChars(bufferDescripcion.toString());

				bufferFechaSalida = new StringBuffer(via.getFechasalida());
				bufferFechaSalida.setLength(10);
				fileV.writeChars(bufferFechaSalida.toString());

				fileV.writeDouble(via.getPvp());
				fileV.writeInt(via.getDias());
				fileV.writeInt(via.getViajeros());

				System.out.println("Viaje insertado: " + id);

			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA DEL SECUENCIAL.");
		}

		dataIS.close(); // cerrar stream de entrada

	}

	private static void llenarViajesSec() throws IOException {
		// fichero de objetos viajes
		FileOutputStream fileout = new FileOutputStream(viajes);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		// int id, String descripcion, LocalDate fechasalida, double pvp, int dias, int
		// viajeros) {

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate f11 = LocalDate.of(2020, 8, 1);
		LocalDate f22 = LocalDate.of(2020, 8, 10);
		LocalDate f33 = LocalDate.of(2020, 8, 20);
		LocalDate f44 = LocalDate.of(2020, 9, 1);
		LocalDate f55 = LocalDate.of(2020, 9, 15);
		LocalDate f66 = LocalDate.of(2020, 9, 25);

		String f1 = f11.format(formato);
		String f2 = f22.format(formato);
		String f3 = f33.format(formato);
		String f4 = f44.format(formato);
		String f5 = f55.format(formato);
		String f6 = f66.format(formato);

		Viaje v1 = new Viaje(14, "Alemania Oeste", f1, 1500, 7, 0);
		Viaje v2 = new Viaje(16, "China Gran Muralla", f6, 2100, 10, 0);
		Viaje v3 = new Viaje(18, "Croacia, Perla del Adriático", f1, 1100, 7, 0);
		Viaje v4 = new Viaje(20, "Crucero por el mar Mediterráneo", f1, 1340, 11, 0);
		Viaje v5 = new Viaje(22, "Cuba y Miami", f5, 900, 7, 0);
		Viaje v6 = new Viaje(26, "Moscu San Petersburgo", f2, 1900, 7, 0);
		Viaje v7 = new Viaje(28, "Noruega Mágica", f3, 2400, 9, 0);
		Viaje v8 = new Viaje(30, "Paises Bajos", f2, 1100, 7, 0);
		Viaje v9 = new Viaje(32, "Praga, Viena, Budapest", f3, 1500, 7, 0);
		Viaje v10 = new Viaje(34, "Costa Brava", f4, 650, 5, 0);
		Viaje v11 = new Viaje(35, "Costa de Almeria", f5, 600, 7, 0);
		Viaje v12 = new Viaje(45, "Paris romántico", f1, 1200, 7, 0);
		Viaje v13 = new Viaje(36, "Extremadura al completo", f6, 500, 6, 0);
		Viaje v17 = new Viaje(37, "Galicia Costa da Morte", f2, 1100, 10, 0);
		Viaje v14 = new Viaje(38, "Huelva", f3, 800, 7, 0);
		Viaje v15 = new Viaje(39, "Oropesa del Mar con balneario", f4, 1400, 8, 0);
		Viaje v16 = new Viaje(40, "Zaragoza y Teruel", f2, 450, 4, 0);
		Viaje v18 = new Viaje(24, "La Toscana", f4, 800, 5, 0);

		dataOS.writeObject(v1);
		dataOS.writeObject(v2);
		dataOS.writeObject(v3);
		dataOS.writeObject(v4);
		dataOS.writeObject(v5);
		dataOS.writeObject(v6);
		dataOS.writeObject(v7);
		dataOS.writeObject(v8);
		dataOS.writeObject(v9);
		dataOS.writeObject(v10);
		dataOS.writeObject(v11);
		dataOS.writeObject(v12);
		dataOS.writeObject(v13);
		dataOS.writeObject(v14);
		dataOS.writeObject(v15);
		dataOS.writeObject(v16);
		dataOS.writeObject(v17);
		dataOS.writeObject(v18);

		dataOS.close();
	}

	// fichero de objetos clientes
	private static void llenarClientesSec() throws IOException {
		FileOutputStream fileout = new FileOutputStream(clientes);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
		// int id, String nombre, int viajesrealizados, double importetotal

		Cliente c1 = new Cliente(10, "Maria Sanz", 0, 0);
		Cliente c2 = new Cliente(15, "Pedro Martin", 0, 0);
		Cliente c3 = new Cliente(12, "Daniel Sanchez", 0, 0);
		Cliente c4 = new Cliente(18, "Maria Jose Perez", 0, 0);
		Cliente c5 = new Cliente(19, "Jesus Rodriguez", 0, 0);
		Cliente c6 = new Cliente(11, "Fernando Alia", 0, 0);
		Cliente c7 = new Cliente(1, "Alicia Sanz", 0, 0);
		Cliente c8 = new Cliente(2, "Raquel Martinez", 0, 0);
		Cliente c9 = new Cliente(4, "Dora Suela", 0, 0);
		Cliente c10 = new Cliente(5, "Julio Reyes", 0, 0);

		dataOS.writeObject(c1);
		dataOS.writeObject(c2);
		dataOS.writeObject(c3);

		dataOS.writeObject(c4);
		dataOS.writeObject(c5);
		dataOS.writeObject(c6);

		dataOS.writeObject(c7);
		dataOS.writeObject(c8);
		dataOS.writeObject(c9);
		dataOS.writeObject(c10);

		dataOS.close();
	}

	// fichero de tipos de datos primitivos
	private static void llenarreservasSec() throws IOException {
		FileOutputStream fileout = new FileOutputStream(reservas);
		DataOutputStream dataOS = new DataOutputStream(fileout);

		// id viaje idcliente
		dataOS.writeInt(14);
		dataOS.writeInt(10);
		dataOS.writeInt(2); // viaje 14 - f1
		dataOS.writeInt(14);
		dataOS.writeInt(15);
		dataOS.writeInt(2);
		dataOS.writeInt(14);
		dataOS.writeInt(12);
		dataOS.writeInt(1);
		dataOS.writeInt(14);
		dataOS.writeInt(18);
		dataOS.writeInt(4);

		dataOS.writeInt(16);
		dataOS.writeInt(1);
		dataOS.writeInt(2);// viaje 16 - f6
		dataOS.writeInt(16);
		dataOS.writeInt(2);
		dataOS.writeInt(3);
		dataOS.writeInt(16);
		dataOS.writeInt(15);
		dataOS.writeInt(1);
		dataOS.writeInt(16);
		dataOS.writeInt(10);
		dataOS.writeInt(2);
		dataOS.writeInt(16);
		dataOS.writeInt(4);
		dataOS.writeInt(4);

		dataOS.writeInt(20);
		dataOS.writeInt(1);
		dataOS.writeInt(2);// viaje 20 - f1
		dataOS.writeInt(20);
		dataOS.writeInt(2);
		dataOS.writeInt(1);
		dataOS.writeInt(20);
		dataOS.writeInt(11);
		dataOS.writeInt(2);
		dataOS.writeInt(20);
		dataOS.writeInt(4);
		dataOS.writeInt(1);
		dataOS.writeInt(20);
		dataOS.writeInt(5);
		dataOS.writeInt(3);

		dataOS.writeInt(37);
		dataOS.writeInt(2);
		dataOS.writeInt(1);// viaje 37- f2
		dataOS.writeInt(37);
		dataOS.writeInt(1);
		dataOS.writeInt(3);
		dataOS.writeInt(37);
		dataOS.writeInt(10);
		dataOS.writeInt(1);

		dataOS.writeInt(24);
		dataOS.writeInt(2);
		dataOS.writeInt(2);// viaje 24- f4
		dataOS.writeInt(24);
		dataOS.writeInt(1);
		dataOS.writeInt(2);
		dataOS.writeInt(24);
		dataOS.writeInt(11);
		dataOS.writeInt(5);
		dataOS.writeInt(24);
		dataOS.writeInt(4);
		dataOS.writeInt(1);
		dataOS.writeInt(24);
		dataOS.writeInt(5);
		dataOS.writeInt(2);
		dataOS.writeInt(24);
		dataOS.writeInt(10);
		dataOS.writeInt(1);
		dataOS.writeInt(24);
		dataOS.writeInt(15);
		dataOS.writeInt(3);
		dataOS.writeInt(24);
		dataOS.writeInt(12);
		dataOS.writeInt(2);
		dataOS.writeInt(24);
		dataOS.writeInt(18);
		dataOS.writeInt(1);

		dataOS.writeInt(37);
		dataOS.writeInt(5);
		dataOS.writeInt(2);// viaje 37 f2
		dataOS.writeInt(38);
		dataOS.writeInt(5);
		dataOS.writeInt(3);// viaje 38 f3
		dataOS.writeInt(22);
		dataOS.writeInt(5);
		dataOS.writeInt(2);// viaje 22 f5

		// PARA ERRORES. crear registros que no existen
		dataOS.writeInt(138);
		dataOS.writeInt(5);
		dataOS.writeInt(2);// viaje no existe
		dataOS.writeInt(220);
		dataOS.writeInt(55);
		dataOS.writeInt(1); // viaje y cliente no existe
		dataOS.writeInt(30);
		dataOS.writeInt(55);
		dataOS.writeInt(1);// viaje existe, cliente no existe

		dataOS.close();

	}

}// fin

//INSERTA DATOS EN LOS FICHEROS
import java.io.DataOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import datos.*;

public class CrearFicheros {

	static File viajes = new File("Viajes.dat");
	static File clientes = new File("Clientes.dat");
	static File reservas = new File("Reservas.dat");
	static File ficheroerror = new File("Errores.txt");

	public static void main(String[] args) throws IOException {
		llenarViajes();
		llenarClientes();
		llenarreservas();
		
		//crear fichero de errores
		FileWriter fic = new FileWriter(ficheroerror);
		fic.close();	
		
		System.out.println("Fin creación");

	}

	// fichero de objetos viajes
	private static void llenarViajes() throws IOException {
		FileOutputStream fileout = new FileOutputStream(viajes);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		// int id, String descripcion, LocalDate fechasalida, double pvp, int dias, int
		// viajeros) {

		LocalDate f1 = LocalDate.of(2020, 8, 1);
		LocalDate f2 = LocalDate.of(2020, 8, 10);
		LocalDate f3 = LocalDate.of(2020, 8, 20);
		LocalDate f4 = LocalDate.of(2020, 9, 1);
		LocalDate f5 = LocalDate.of(2020, 9, 15);
		LocalDate f6 = LocalDate.of(2020, 9, 25);

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
	private static void llenarClientes() throws IOException {
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
	private static void llenarreservas() throws IOException {
		FileOutputStream fileout = new FileOutputStream(reservas);
		DataOutputStream dataOS = new DataOutputStream(fileout);

		// id viaje idcliente
		dataOS.writeInt(14); dataOS.writeInt(10); dataOS.writeInt(2); // viaje 14 - f1
		dataOS.writeInt(14); dataOS.writeInt(15);dataOS.writeInt(2);
		dataOS.writeInt(14); dataOS.writeInt(12);dataOS.writeInt(1);
		dataOS.writeInt(14); dataOS.writeInt(18);dataOS.writeInt(4);

		dataOS.writeInt(16); dataOS.writeInt(1); dataOS.writeInt(2);// viaje 16 - f6
		dataOS.writeInt(16); dataOS.writeInt(2);dataOS.writeInt(3);
		dataOS.writeInt(16); dataOS.writeInt(15);dataOS.writeInt(1);
		dataOS.writeInt(16); dataOS.writeInt(10);dataOS.writeInt(2);
		dataOS.writeInt(16); dataOS.writeInt(4);dataOS.writeInt(4);

		dataOS.writeInt(20); dataOS.writeInt(1); dataOS.writeInt(2);// viaje 20 - f1
		dataOS.writeInt(20); dataOS.writeInt(2);dataOS.writeInt(1);
		dataOS.writeInt(20); dataOS.writeInt(11);dataOS.writeInt(2);
		dataOS.writeInt(20); dataOS.writeInt(4);dataOS.writeInt(1);
		dataOS.writeInt(20); dataOS.writeInt(5);dataOS.writeInt(3);

		dataOS.writeInt(37); dataOS.writeInt(2); dataOS.writeInt(1);// viaje 37- f2
		dataOS.writeInt(37); dataOS.writeInt(1);dataOS.writeInt(3);
		dataOS.writeInt(37); dataOS.writeInt(10);dataOS.writeInt(1);

		dataOS.writeInt(24); dataOS.writeInt(2); dataOS.writeInt(2);// viaje 24- f4
		dataOS.writeInt(24); dataOS.writeInt(1); dataOS.writeInt(2);
		dataOS.writeInt(24); dataOS.writeInt(11);dataOS.writeInt(5);
		dataOS.writeInt(24); dataOS.writeInt(4);dataOS.writeInt(1);
		dataOS.writeInt(24); dataOS.writeInt(5); dataOS.writeInt(2);
		dataOS.writeInt(24); dataOS.writeInt(10);dataOS.writeInt(1);
		dataOS.writeInt(24); dataOS.writeInt(15);dataOS.writeInt(3);
		dataOS.writeInt(24); dataOS.writeInt(12);dataOS.writeInt(2);
		dataOS.writeInt(24); dataOS.writeInt(18);dataOS.writeInt(1);

		dataOS.writeInt(37); dataOS.writeInt(5); dataOS.writeInt(2);//viaje 37 f2
		dataOS.writeInt(38); dataOS.writeInt(5); dataOS.writeInt(3);//viaje 38 f3
		dataOS.writeInt(22); dataOS.writeInt(5); dataOS.writeInt(2);//viaje 22 f5

		//PARA ERRORES. crear registros que no existen
		dataOS.writeInt(138); dataOS.writeInt(5); dataOS.writeInt(2);//viaje no existe
		dataOS.writeInt(220); dataOS.writeInt(55);dataOS.writeInt(1); //viaje y cliente no existe	
		dataOS.writeInt(30); dataOS.writeInt(55); dataOS.writeInt(1);//viaje existe, cliente no existe
		
			
		dataOS.close();

	}

}// fin

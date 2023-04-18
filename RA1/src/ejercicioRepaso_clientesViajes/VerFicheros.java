package ejercicioRepaso_clientesViajes;

import ejercicioRepaso_clientesViajes.datos.Cliente;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import ejercicioRepaso_clientesViajes.datos.Viaje;

public class VerFicheros {

	static File viajes = new File("Viajes.dat");
	static File clientes = new File("Clientes.dat");
	static File reservas = new File("Reservas.dat");

	public static void main(String[] args) throws IOException {
		listarViajes();
		listarclientes();
		listarreservas();

	}

	private static void listarreservas() throws IOException {
	
		FileInputStream filein = new FileInputStream(reservas);
		DataInputStream dataIS = new DataInputStream(filein);
		System.out.println("==================================================");
		
		try {
			while (true) {
				int viaje = dataIS.readInt();
				int cliente = dataIS.readInt();
				int plazas = dataIS.readInt();
				
				System.out.println("Id viaje: "+ viaje +", cliente: "+ cliente +
						", plazas: "+ plazas);
			}
		} catch (EOFException eo) {
		} 

		dataIS.close();
		filein.close();


	}//

	private static void listarclientes()  throws IOException {

		FileInputStream filein = new FileInputStream(clientes);
		ObjectInputStream dataIS = new ObjectInputStream(filein);
		System.out.println("==================================================");

		try {
			while (true) {
				Cliente d1 = (Cliente) dataIS.readObject();
				System.out.println(d1);
			}
		} catch (EOFException eo) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		dataIS.close();
		filein.close();

	}

	private static void listarViajes() throws IOException {

		FileInputStream filein = new FileInputStream(viajes);
		ObjectInputStream dataIS = new ObjectInputStream(filein);
		System.out.println("==================================================");

		try {
			while (true) {
				Viaje d1 = (Viaje) dataIS.readObject();
				System.out.println(d1);
			}
		} catch (EOFException eo) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		dataIS.close();
		filein.close();

	}

}

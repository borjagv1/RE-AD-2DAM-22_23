package ejercicioRepaso_clientesViajes;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.time.format.DateTimeFormatter;

import ejercicioRepaso_clientesViajes.datos.Cliente;
import ejercicioRepaso_clientesViajes.datos.Viaje;

public class Main_GVBorja {

	static File reservas = new File("Reservas.dat");
	static File viajes = new File("Viajes.dat");
	static File clientes = new File("Clientes.dat");
	static File errores = new File("Errores.txt");// declara fichero
	static long err;

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		//apartado1();
		//apartado2();
		apartado3();

		//ejercicio4_ListadoViajes();

	}

	private static void ejercicio4_ListadoViajes() throws FileNotFoundException, IOException {
		FileInputStream filein = new FileInputStream(viajes);
		ObjectInputStream dataIS = new ObjectInputStream(filein);

		System.out.println("=======================================================");
		System.out.println(" ID DESCRIPCION                     FEC SALIDA VIAJEROS");
		System.out.println("=== =============================== ========== ========");

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			while (true) {
				Viaje v = (Viaje) dataIS.readObject();
				String fecha = v.getFechasalida().format(formato);
				System.out.printf("%3d %-31s %10s %5d %n", v.getId(), v.getDescripcion(), fecha, v.getViajeros());
			}
		} catch (EOFException eo) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		dataIS.close();
		filein.close();
	}

	private static void apartado3() throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println(
				"3) El campo importetotal debe almacenar la suma de lo que valen las reservas que ha realizado el cliente. El importe de cada reserva es igual a la multiplicación del pvp del viaje por el número de plazas.");
		DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas));
		int idViaje, nplazas, idcliente;

		try {
			while (true) {
				idViaje = dataIS.readInt();
				idcliente = dataIS.readInt();// recupera los id
				if (!existeCliente(idcliente))
					break;
				else {
					nplazas = dataIS.readInt(); // recupera las plazas
					double pvp = damePVP(idViaje);

					// int totalplazas = sumaPlazasCli(idcliente);
//					System.out.println("Importe de IDVIAJE" + idViaje + ", idcliente " + idcliente + ", PVP: " + pvp
//							+ ", totalPlazas: " + totalplazas);
					actualizaClientesPVP(idcliente, pvp, nplazas);

				}

			} // while
		} catch (EOFException eo) {
		} // catch
		System.out.println("FIN APARTADO 3");
		dataIS.close(); // cerrar stream
	}

	private static void actualizaClientesPVP(int idcliente, double pvp, int totalplazas)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		Cliente cli; // Defino variable viaje
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(clientes));

		File ficheroaux = new File("auxiliar.dat");
		FileOutputStream fileOut = new FileOutputStream(ficheroaux);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileOut);
		// double total = pvp*totalplazas;
		try {
			while (true) { // lectura del fichero
				cli = (Cliente) dataIS.readObject(); // leer un viaje
				if (cli.getId() == idcliente) {
					double total = cli.getImportetotal();
					cli.setImportetotal(total + (pvp * totalplazas));

				}
				dataOS.writeObject(cli);
			}
		} catch (EOFException eo) {
			// System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
		dataOS.close(); // Cerrar stream de salida

		clientes.delete();
		ficheroaux.renameTo(clientes);
	}

	private static double damePVP(int idViaje) throws FileNotFoundException, IOException, ClassNotFoundException {
		double pvp = 0;
		Viaje viaje; // defino la variable viaje
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes));

		try {
			while (true) { // lectura del fichero
				viaje = (Viaje) dataIS.readObject(); // leer un viaje
				if (viaje.getId() == idViaje) {
					pvp = viaje.getPvp();
					break;
				}

			}
		} catch (EOFException eo) {
			// System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
		return pvp;
	}

	private static void apartado2() throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println(
				"\n2) [2,5 puntos] Actualizar en el fichero Clientes.dat:\n El campo viajescontratados para que almacene el nº de viajes contratados por el cliente. \nCada registro en el fichero de reservas es un viaje contratado.");
		DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas));
		int idViaje, nplazas, idcliente, totalViajes;

		try {
			while (true) {
				idViaje = dataIS.readInt();
				idcliente = dataIS.readInt();// recupera los idcLIENTE
				if (!existeCliente(idcliente))
					break;// quitar porque no llega al final

				else {
					nplazas = dataIS.readInt(); // recupera las plazas
					totalViajes = sumaViajes(idcliente);
					ActualizarClientes(idcliente, totalViajes);

//					System.out.println("Id viaje: "+ idViaje +", cliente: "+ idcliente +
//							", plazas: "+ nplazas + ", TOTAL viajes: " + totalViajes);
				}

			} // WHILE
		} catch (EOFException eo) {
		} // catch
		System.out.println("FIN APARTADO 2");
		dataIS.close(); // cerrar stream
	}

	private static void ActualizarClientes(int idcliente, int totalViajes)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		Cliente cli; // Defino variable viaje
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(clientes));

		File ficheroaux = new File("auxiliar.dat");
		FileOutputStream fileOut = new FileOutputStream(ficheroaux);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileOut);
		try {
			while (true) { // lectura del fichero
				cli = (Cliente) dataIS.readObject(); // leer un viaje
				if (cli.getId() == idcliente) {
					cli.setViajescontratados(totalViajes);
					
				}
				dataOS.writeObject(cli);
			}
		} catch (EOFException eo) {
			// System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
		dataOS.close(); // Cerrar stream de salida

		clientes.delete();
		ficheroaux.renameTo(clientes);
	}

	private static int sumaViajes(int idcliente) throws IOException {
		DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas));

		int total = 0;
		int idviajes;
		int idAux = 0;
		int nplazas;

		try {
			while (true) {
				idviajes = dataIS.readInt();
				idAux = dataIS.readInt();
				nplazas = dataIS.readInt(); // recupera las plazas
				if (idAux == idcliente) {
					total++;
				}
			} // while
		} catch (EOFException eo) {
		} // catch

		return total;
	}

	private static boolean existeCliente(int idcliente) throws ClassNotFoundException, IOException {
		boolean existe = false;
		Cliente cli; // defino la variable viaje
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(clientes));

		try {
			while (true) { // lectura del fichero
				cli = (Cliente) dataIS.readObject(); // leer un viaje
				if (cli.getId() == idcliente) {
					existe = true;
					break;

				}

			}
		} catch (EOFException eo) {
			// System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}
//		if (existe = false) {
//			err++;
//			InsertarError("ERROR " + err + ". EL CLIENTE" + idcliente + ", NO EXISTE");
//		}

		dataIS.close(); // cerrar stream de entrada
		return existe;
	}

	private static void InsertarError(String mensaje) throws IOException {
		FileWriter fic = new FileWriter(errores, true);
		fic.write(mensaje);
		fic.write("\n");
		fic.close();

	}

	@SuppressWarnings("unused")
	private static void apartado1() throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("1) [1,5 puntos] Actualizar en el fichero Viajes.dat:\r\n"
				+ "· El campo viajeros del fichero para que contenga el nº de viajeros que hacen el viaje. \n"
				+ "Este campo será igual a la suma de las plazas reservadas en el viaje correspondiente.");
		DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas));
		int idViaje, nplazas, idcliente, totalPlazas;

		try {
			while (true) {
				idViaje = dataIS.readInt();

				if (!existeViaje(idViaje))
					break;
				else {
					idcliente = dataIS.readInt();// recupera los id
					nplazas = dataIS.readInt(); // recupera las plazas
					totalPlazas = sumaPlazas(idViaje);
//					System.out.println("Id viaje: "+ idViaje +", cliente: "+ idcliente +
//							", plazas: "+ nplazas + ", TOTAL PLAZAS: " + totalPlazas);
					ActualizarViajes(idViaje, totalPlazas);
				}

			} // while
		} catch (EOFException eo) {
		} // catch
		System.out.println("FIN APARTADO 1");
		dataIS.close(); // cerrar stream
	}

	private static void ActualizarViajes(int idViaje, int totalPlazas)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		Viaje viaje; // Defino variable viaje
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes));

		// ObjectOutputStream dataOS = new ObjectOutputStream(new
		// FileOutputStream(viajes));

		File ficheroaux = new File("auxiliar.dat");
		FileOutputStream fileOut = new FileOutputStream(ficheroaux);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileOut);
		try {
			while (true) { // lectura del fichero
				viaje = (Viaje) dataIS.readObject(); // leer un viaje
				if (viaje.getId() == idViaje) {
					viaje.setViajeros(totalPlazas);

				}
				dataOS.writeObject(viaje);
			}
		} catch (EOFException eo) {
			// System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
		dataOS.close(); // Cerrar stream de salida

		viajes.delete();
		ficheroaux.renameTo(viajes);

	}// ACtualizar Viajes

	@SuppressWarnings({ "resource", "unused" })
	private static int sumaPlazas(int idViaje) throws IOException {
		DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas));

		int total = 0;
		int idAux;
		int idcliente = 0;
		int nplazas;

		try {
			while (true) {
				idAux = dataIS.readInt();
				idcliente = dataIS.readInt();// recupera los id
				nplazas = dataIS.readInt(); // recupera las plazas
				if (idAux == idViaje) {
					total = total + nplazas;
				}
			} // while
		} catch (EOFException eo) {
		} // catch

		return total;
	}

	private static boolean existeViaje(int idViaje) throws IOException, ClassNotFoundException {
		boolean existe = false;
		Viaje viaje; // defino la variable viaje
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes));

		try {
			while (true) { // lectura del fichero
				viaje = (Viaje) dataIS.readObject(); // leer un viaje
				if (viaje.getId() == idViaje) {
					existe = true;
					break;

				}

			}
		} catch (EOFException eo) {
			// System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
		return existe;
	}

}

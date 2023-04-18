package ejercicioClaseFichDepartAleatorio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

//MODIFICAR EL NOMBRE DEL DEPARTAMENTO
//LEER N�MERO DE DEPARTAMENTO HASTA QUE SEA 0
//COMPROBAMOS QUE EXISTE - SI EXISTE VISUALIZAR SUS DATOS - LEER EL NOMBRE DEL NUEVO DEPARTAMENTO Y LO ACTUALIZAMOS
//SI NO EXISTE, LEER DE NUEVO.

//Despu�s de modificar el nombre, modificar la localidad en vez de modificar el nombre
public class ModificarDepartamento {
	static File fichero = new File("AleatorioDepart.dat");
	static RandomAccessFile file;
	static Scanner sc = new Scanner(System.in);
	static long longitud = 4 + 24 + 30;

	public static void main(String[] args) throws IOException {
		file = new RandomAccessFile(fichero, "rw");
		int dep;
		@SuppressWarnings("unused")
		String nombre, loc;

		while (true) {
			dep = CrearFichAleatorio_departamentos.leerEntero("Numero de departamento a modificar: ", 0, 99);
			System.out.println("====================================================================================");
			if (dep <= 0)
				break;

			if (CrearFichAleatorio_departamentos.ExisteDepartamento(dep)) {

				long posicion = (dep - 1) * longitud;
				file.seek(posicion); //+4 nos posicionamos para la lectura correcta del nombre y de la localidad

				// Visualizar los datos
				System.out.println("DATOS DEPARTAMENTO: ");
				char nombredep[] = new char[12], aux;
				char localidad[] = new char[15], aux2;
				
				//ID
				int id = file.readInt(); // obtengo id de depart
				
				// nombre de departamento
				for (int i = 0; i < nombredep.length; i++) {
					aux = file.readChar();
					nombredep[i] = aux; // los voy guardando en el array
				}
				// convierto a String el array
				String nombredepS = new String(nombredep);

				// localidad
				for (int i = 0; i < localidad.length; i++) {
					aux2 = file.readChar();
					localidad[i] = aux2; // los voy guardando en el array
				}
				// convierto a String el array
				String localidadS = new String(localidad);
				if (dep > 0)
					System.out.printf("ID: %d, Nombre departamento: %s, Localidad:%s %n", id, nombredepS.trim(),
							localidadS.trim());
				System.out.println("====================================================================================");

				// Nos posicionamos PARA MODIFICAR
				posicion = posicion + 4; // Para la loc es +4 +24 y para el nombre solo +4
				file.seek(posicion);

				// modificar nombre
				System.out.println("Introduce nuevo nombre de departamento: ");
				nombre = sc.nextLine();// 12
				StringBuffer nomBuff = new StringBuffer(nombre);
				nomBuff.setLength(12); // 12 caracteres para el nombre
				file.writeChars(nomBuff.toString()); // modificar nombre

//				// modificar localidad
//				System.out.println("Introduce nuevo nombre localidad");
//				loc = sc.nextLine(); // 15
//				StringBuffer locBuffer = new StringBuffer(loc);
//				locBuffer.setLength(15); // 15 caracteres para  LOC
//				file.writeChars(locBuffer.toString()); // modificar LOC

				System.out.println("\tRegistro modificado");
			} else {
				System.out.println("\tEl departamento no existe.....");
			}

			System.out.println("====================================================================================");

		}

		file.close();

		try {
			CrearFichAleatorio_departamentos.VisualizarDatos();
		} catch (java.io.EOFException ex) {
			System.out.println("Fin del programa");
		}

	}

}

package ejercicioEmpleyDepConFichSecuencialesyAleatorios;

import java.io.DataInputStream;
import java.io.EOFException;
//PARA HACER EL EJERCICIO A MI FORMA. EL OTRO ES COPIA PEGA DEL AÑO PASADO. 
/*
 * a) Actualizar el nombre de departamento en todos los registros del fichero
 * aleatorio, el nombre se obtiene del fichero FichDepartamentos.dat. Si el
 * número de departamento no existe, se pone como nombre ‘No existe’. b)
 * Modificar el salario de los empleados cuyo identificador esté en el fichero
 * FichEmpleadosModif.dat. La modificación consistirá en subir al salario la
 * cantidad que aparece junto al identificador. Si algún identificador no existe
 * visualizar un mensaje indicándolo. c) Una vez realizadas las modificaciones
 * visualizar el fichero aleatorio con todos los datos.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.io.StreamCorruptedException;

//4 + 30 + 20 + 4 + 4 + 30 = empno - apellido - oficio - salario - deptno - nombreDepartamento

//long 8 bytes - float 4 bytes - double 8 bytes - boolean 1 bit
public class Main_GVBorja {

	static int longitud = 4 + 30 + 20 + 4 + 4 + 30; // 92

	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		apartadoA();
		
		aparatdoB();
		
		apartadoC();
		
	}// MAIN

	@SuppressWarnings({ "resource", "unused" })
	private static void aparatdoB() throws FileNotFoundException, IOException {
		System.out.println("\nB) Modificando salario en el fichero de empleados:");
		File fichero = new File("FichEmpleAleatorio.dat");
		// declara el fichero de acceso aleatorio
		RandomAccessFile fileAleatorio = new RandomAccessFile(fichero, "rw");
		//
		int empno, deptno, posicion;
		float salario;
		char apellido[] = new char[15], aux;
		char oficio[] = new char[10], aux2;

		posicion = 0; // para situarnos al principio //Si se la pos, por ej 6 -> (6-1) * 36

		for (;;) { // recorro el fichero
			fileAleatorio.seek(posicion); // nos posicionamos en posicion
			empno = fileAleatorio.readInt(); // obtengo num emp

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < apellido.length; i++) {
				aux = fileAleatorio.readChar();
				apellido[i] = aux; // los voy guardando en el array
			}
			// convierto a String el array
			String apellidoS = new String(apellido);

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < oficio.length; i++) {
				aux2 = fileAleatorio.readChar();
				oficio[i] = aux2; // los voy guardando en el array
			}
			// convierto a String el array
			String oficioS = new String(oficio);

			salario = fileAleatorio.readFloat();

//			// ===========================================================================
			if (existeEmpleSecuencial(empno)) {
				File ficheroSEC = new File("FichEmpleadosModif.dat");
				DataInputStream dataIS = new DataInputStream(new FileInputStream(ficheroSEC));

				int id;
				float salarsubir;

				try {
					while (true) {
						id = dataIS.readInt(); // recupera el nombre
						salarsubir = dataIS.readFloat(); // subida salario

						if (id == empno) {
							System.out.println("\tID: " + empno + ", Sumamos: " + salarsubir + "\n"
									+ "\tSalario Antiguo = " + salario + " Nuevo: " + (salario + salarsubir)
									+ "\n\tEmpleado Modificado: " + empno);
							salario = salario + salarsubir;
							break;
						}if (id == 90){
							System.out.println("\tID: " + id + ", Sumamos: " + salarsubir + "\n"
									+ "\t>>> " + id + " NO EXISTE.... " );
						}
					}
					fileAleatorio.seek(posicion + 54);
					fileAleatorio.writeFloat(salario);

				} catch (EOFException eo) {
				}

				dataIS.close(); // cerrar stream
			}


			deptno = fileAleatorio.readInt();

			// me posiciono para el sig EMPLEADO

			posicion = posicion + longitud;

			// Si he recorrido todos los bytes salgo del for
			if (posicion == fileAleatorio.length())
				break;
			
		} // FOR
		System.out.println("Fin modificación");
	}

	@SuppressWarnings({ "resource", "unused" })
	private static void apartadoA() throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("A) Modificando NOMBRE de departamento en el fichero de empleados:");
		File fichero = new File("FichEmpleAleatorio.dat");
		// declara el fichero de acceso aleatorio
		RandomAccessFile fileAleatorio = new RandomAccessFile(fichero, "rw");
		//
		int empno, deptno, posicion;
		float salario;
		char apellido[] = new char[15], aux;
		char oficio[] = new char[10], aux2;
		// char nombreDepartamento[] = new char[15], aux3;
		String nombredept = "no existe";

		posicion = 0; // para situarnos al principio //Si se la pos, por ej 6 -> (6-1) * 36

		for (;;) { // recorro el fichero
			fileAleatorio.seek(posicion); // nos posicionamos en posicion
			empno = fileAleatorio.readInt(); // obtengo num emp

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < apellido.length; i++) {
				aux = fileAleatorio.readChar();
				apellido[i] = aux; // los voy guardando en el array
			}
			// convierto a String el array
			String apellidoS = new String(apellido);

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < oficio.length; i++) {
				aux2 = fileAleatorio.readChar();
				oficio[i] = aux2; // los voy guardando en el array
			}
			// convierto a String el array
			String oficioS = new String(oficio);

			salario = fileAleatorio.readFloat();

			deptno = fileAleatorio.readInt();

			if (existeDeptno(deptno)) {

				Departamento dept; // defino la variable depart

				File ficherodept = new File("FichDepartamentos.dat");
				ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(ficherodept));
				// try {
				while (true) { // lectura del fichero
					dept = (Departamento) dataIS.readObject(); // leer un dept
					if (deptno == dept.getDep()) {
						nombredept = dept.getNombre();
						break;
					}
				}

				// Ya tenemos el nombre del departamento o "No existe" si no hay //
				// Escribimos en el fichero el nombre del departamento. // Colocamos el puntero
				// justo antes de los bytes reservados para el nombre del // departamento.

				StringBuffer buffer = new StringBuffer(nombredept);
				buffer.setLength(15); // 15 caract para nombre dep
				fileAleatorio.writeChars(buffer.toString()); // Insertar nombredept

			} else {
				StringBuffer buffer = new StringBuffer("no existe");
				buffer.setLength(15); // 15 caract para nombre dep
				fileAleatorio.writeChars(buffer.toString()); // Insertar nombredept

			}

			if (empno > 0) // SI COMENTAMOS ESTA CONDICIóN VEMOS LOS REGISTROS VACÃOS HASTA EL 20 POR ES
							// ES
				// IMPORTANTE VER QUE EL ID SIEMPRE ES MAYOR QUE 0. PARA VER SOLO LOS REGISTROS
				// CON INFORMACIÓN
				System.out.println("\tEMPLEADO: " + empno + ", modificado");

			// me posiciono para el sig EMPLEADO
			posicion = posicion + longitud;

			// Si he recorrido todos los bytes salgo del for
			if (posicion == fileAleatorio.length())
				break;

		} // FOR
		fileAleatorio.close();

	}

	@SuppressWarnings("unused")
	private static boolean existeEmpleSecuencial(int empno) throws IOException {
		boolean existe = false;
		File fichero = new File("FichEmpleadosModif.dat");
		DataInputStream dataIS = new DataInputStream(new FileInputStream(fichero));

		int id;
		float salar;

		try {
			while (true) {
				id = dataIS.readInt(); // recupera el nombre
				if (id == empno) {
					existe = true;
					break;
				}
			}
		} catch (EOFException eo) {
		}

		dataIS.close(); // cerrar stream

		return existe;
	}

	@SuppressWarnings("resource")
	private static void apartadoC() throws IOException {
		System.out.println("\nC) Listado de todos los empleados:");
		File fichero = new File("FichEmpleAleatorio.dat");
		// declara el fichero de acceso aleatorio
		RandomAccessFile fileAleatorio = new RandomAccessFile(fichero, "r");
		//
		int empno, deptno, posicion = 0;
		float salario;
		char apellido[] = new char[15], aux;
		char oficio[] = new char[10], aux2;
		char nombreDepartamento[] = new char[15], aux3;

		for (;;) { // recorro el fichero
			fileAleatorio.seek(posicion); // nos posicionamos en posicion
			empno = fileAleatorio.readInt(); // obtengo id de empleado

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < apellido.length; i++) {
				aux = fileAleatorio.readChar();
				apellido[i] = aux; // los voy guardando en el array
			}
			// convierto a String el array
			String apellidoS = new String(apellido);

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < oficio.length; i++) {
				aux2 = fileAleatorio.readChar();
				oficio[i] = aux2; // los voy guardando en el array
			}
			// convierto a String el array
			String oficioS = new String(oficio);

			salario = fileAleatorio.readFloat();
			deptno = fileAleatorio.readInt();

			// recorro uno a uno los caracteres del apellido
			for (int i = 0; i < nombreDepartamento.length; i++) {
				aux3 = fileAleatorio.readChar();
				nombreDepartamento[i] = aux3; // los voy guardando en el array
			}
			// convierto a String el array
			String nombreDepartamentoS = new String(nombreDepartamento);

			if (empno > 0) // SI COMENTAMOS ESTA CONDICIÃN VEMOS LOS REGISTROS VACÃOS HASTA EL 20 POR ES
							// ES
				// IMPORTANTE VER QUE EL ID SIEMPRE ES MAYOR QUE 0. PARA VER SOLO LOS REGISTROS
				// CON INFORMACIÃN
				System.out.printf("EMPNO: %d, APELLIDO: %s, OFICIO: %s, Salario: %.2f, DEPTNO: %d, NOMBREDEP: %s %n",
						empno, apellidoS.trim(), oficioS.trim(), salario, deptno, nombreDepartamentoS.trim());

			// me posiciono para el sig
			posicion = posicion + longitud;

			// Si he recorrido todos los bytes salgo del for
			if (fileAleatorio.getFilePointer() == fileAleatorio.length())
				break;

		} // FOR

	}

	private static boolean existeDeptno(int deptno) throws FileNotFoundException, IOException, ClassNotFoundException {
		Departamento dept; // defino la variable depart
		boolean existe = false;
		File fichero = new File("FichDepartamentos.dat");
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(fichero));
		try {
			while (true) { // lectura del fichero
				dept = (Departamento) dataIS.readObject(); // leer una Persona
				if (dept.getDep() == deptno)
					existe = true;

			}
		} catch (EOFException eo) {
			// System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada

		return existe;
	}

}

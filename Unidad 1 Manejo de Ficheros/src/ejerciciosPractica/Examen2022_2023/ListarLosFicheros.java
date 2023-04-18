package ejerciciosPractica.Examen2022_2023;


//VISUALIZA EL CONTENIDO DE LOS FICHEROS

import java.io.DataInputStream;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;

import datos.*;
import ejerciciosPractica.Examen2022_2023.datos.Alumno;

public class ListarLosFicheros {

	static File ficheroc = new File("Cursos.dat");
	static File ficheroal = new File("Alumnos.dat"); 
	static File ficheronotas = new File("Notas.dat"); 
	static File ficheronuevos = new File("NuevosAlumnos.dat"); 

	
	static final int longRegCurso = 4 + 50 + 4 + 8;
	static final int longRegAlumno = 4 + 30 + 8 + 4 + 50 + 4;
	

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		
		if (ficheroc.exists()) {
			listadoCursos();
		}else {
			System.out.println("El fichero "+ficheroc.getName()+", no existe...");
		}

		if (ficheronotas.exists()) {
			listarNotas();
		}else {
			System.out.println("El fichero "+ficheronotas.getName()+", no existe...");
		}
		
		if (ficheronuevos.exists()) {
			listarNuevos();
		}else {
			System.out.println("El fichero "+ficheronuevos.getName()+", no existe...");
		}
		
		
		if (ficheroal.exists()) {
			listadoAlumnos();
		}else {
			System.out.println("El fichero "+ficheroal.getName()+", no existe...");
		}
		
		System.out.println("Fin listados");

	}

	private static void listarNuevos() throws IOException, ClassNotFoundException {
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(ficheronuevos));

		System.out.println("===================================LISTADO DE NUEVOS REGISTROS=================");

		try {
			while (true) {
				Alumno al= (Alumno) dataIS.readObject();
				System.out.println(al);
			}
		} catch (EOFException eo) {
		}

		System.out.println(" ");
		dataIS.close();
		
	}

	public static void listadoAlumnos() throws IOException {

		System.out.println("=======================LISTADO DE ALUMNOS ====================================");

		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(ficheroal, "rw");

		long posicion = 0; // para situarnos al principio

		for (;;) { // recorro el fichero
			file.seek(posicion); // nos posicionamos en posicion
			int id = file.readInt(); // obtengo id

			//(int ,String (15) , double, int , String (25) , int ) 
			//int id, String nombre, double notamedia, int idCurso, String nombreCurso, int nasignaturas) {
		
			char nom[] = new char[15], aux;

			for (int i = 0; i < nom.length; i++) {
				aux = file.readChar();
				nom[i] = aux;
			}

			// convierto a String el array
			String noms = new String(nom);

			double media = file.readDouble();
			int cur = file.readInt();

			char nomcur[] = new char[25], aux2;
			for (int i = 0; i < nomcur.length; i++) {
				aux2 = file.readChar();
				nomcur[i] = aux2;
			}

			String nomzs = new String(nomcur);

			int n = file.readInt();

			String cad = String.format(
					"Alumno: %d, %s, N�mero Asig: %2d, Nota media: %,5.2f, Curso: %d, Nombre: %s",
					   id, noms, n, media, cur, nomzs);
			if (id > 0)
				System.out.println(cad);

			// me posiciono para el sig 
			posicion = posicion + longRegAlumno;

			// Si he recorrido todos los bytes salgo del for
			if (file.getFilePointer() == file.length())
				break;

		} // fin bucle for
		file.close(); // cerrar fichero
		System.out.println("========================================================================================");

	}// listado alumnos

	
	// listar cursos
	public static void listadoCursos() throws IOException {
		System.out.println("=======================LISTADO DE CURSOS ====================================");

		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(ficheroc, "rw");

		long posicion = 0; // para situarnos al principio

		for (;;) { // recorro el fichero
			file.seek(posicion); // nos posicionamos en posicion
			int id = file.readInt(); // obtengo id

			
			//int , String , int , double			
			char nomz[] = new char[25], aux2;
			for (int i = 0; i < nomz.length; i++) {
				aux2 = file.readChar();
				nomz[i] = aux2; // los voy guardando en el array
			}
			
			String noms = new String(nomz);
			int n = file.readInt();
			
			double media = file.readDouble();
			String cad = String.format("Curso: %d, %s, N�mero alumnos: %2d, Nota Media: %,5.2f", 
					id, noms, n, media);

			if (id > 0)
				System.out.println(cad);

			posicion = posicion + longRegCurso;

			if (file.getFilePointer() == file.length())
				break;

		} // fin bucle for

		file.close(); // cerrar fichero

		System.out.println("");

	}// listadoZonas

	// listar notas
	private static void listarNotas() throws IOException {
		DataInputStream dataIS = new DataInputStream(new FileInputStream(ficheronotas));

		System.out.println("============================LISTADO DE NOTAS=================");

		try {
			while (true) {

				System.out.printf("(%3d, %-37s, %,8.2f)%n", 
						dataIS.readInt(), dataIS.readUTF(), dataIS.readDouble());

			}
		} catch (EOFException eo) {
		}

		System.out.println(" ");//======================================================================");
		dataIS.close();
	}// listar notas

}// fin

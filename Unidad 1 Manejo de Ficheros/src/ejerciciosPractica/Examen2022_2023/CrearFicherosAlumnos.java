package ejerciciosPractica.Examen2022_2023;


//INSERTA DATOS EN LOS FICHEROS

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import datos.*;
import ejerciciosPractica.Examen2022_2023.datos.Alumno;
import ejerciciosPractica.Examen2022_2023.datos.Curso;
@SuppressWarnings("unused")
public class CrearFicherosAlumnos {	
	static File ficheroc = new File("Cursos.dat");
	static File ficheroal = new File("Alumnos.dat"); 
	static File ficheronotas = new File("Notas.dat"); 
	static File ficheronuevos = new File("NuevosAlumnos.dat"); 

	
	static final int longRegCurso = 4 + 50 + 4 + 8;
	static final int longRegAlumno = 4 + 30 + 8 + 4 + 50 + 4;
		
	public static void main(String[] args) throws IOException {

		ficheroc.delete();
		ficheroal.delete();
		ficheronotas.delete();
		ficheronuevos.delete();
				
		llenarCursos();
		llenarAlumnos();	
		llenarNotas();	
		llenarNuevos();		

		System.out.println("Fin creaci�n");
	}

	//llenar nuevos
	private static void llenarNuevos() throws IOException {
	
		ObjectOutputStream dataOS = new ObjectOutputStream(new FileOutputStream(ficheronuevos));
		
		int cod[] = { 10, 20, 30, 40, 50, 60 };
		String nombre[] = { "Primer Curso DAM", 
				"Segundo Curso DAM", "Primero SMR", "Segundo SMR", 
				"Primero DAW", "Segundo DAW" };
	
		Alumno[] v = new Alumno[6];

		//int id, String nombre, double notamedia, int idCurso, String nombreCurso, int nasignaturas) {
		
		v[0] = new Alumno(1136, "Alonso Ramos", 0, 10, "Primer Curso DAM", 0); //1dam
		v[1] = new Alumno(2044, "Jose Rodriguez", 0, 10, "Primer Curso DAM", 0);//1dam - repe
		
		v[2] = new Alumno(1155, "Marta Henares", 0, 20, "Segundo Curso DAM", 0); //2dam
		
		v[3] = new Alumno(1789, "Josefa Luque", 0, 30, "Primero SMR", 0);  //1smr		
		v[4] = new Alumno(1077, "Manuela Sanchez", 0, 40, "Segundo SMR", 0);//2smr
		
		v[5] = new Alumno(1078, "Manuel Salas", 0, 55, "Curso Videojuegos", 0);//no existe
			
		for (int i = 0; i < v.length; i++) {
			dataOS.writeObject(v[i]);
		}
			
		
		dataOS.close();
		
	}//llenarNuevos


	private static void llenarAlumnos() throws IOException {
		
		Alumno[] v = new Alumno[9];

		
		v[0] = new Alumno(1036, "FEDERICO", 0, 10, "", 0); //1dam
		v[1] = new Alumno(2044, "MANUELA", 0, 10, "", 0);//1dam
		
		v[2] = new Alumno(2155, "ALICIA", 0, 20, "", 0); //2dam
		
		v[3] = new Alumno(5789, "SARA", 0, 30, "", 0);  //1smr
		v[4] = new Alumno(1077, "ENRIQUE", 0, 30, "", 0);//1smr
		v[5] = new Alumno(2029, "PILUCA",0, 30, "", 0);//1smr
		
		v[6] = new Alumno(5050, "MART�N", 0, 79, "", 0); //no existe curso
		
		v[7] = new Alumno(3040, "JOSE ANTONIO", 0, 40, "", 0); //2smr
		
		v[8] = new Alumno(7709, "MIGUEL ANGEL", 0, 55, "", 0); //no existe curso

		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(ficheroal, "rw");

		//(int ,String (15) , double, int , String (25) , int ) 
		
		for (int i = 0; i < v.length; i++) {
			long posicion = (v[i].getId() - 1) * longRegAlumno;

			file.seek(posicion); // nos posicionamos

			//(int ,String (15) , double, int , String (25) , int ) 
			//int id, String nombre, double notamedia, int idCurso, String nombreCurso, int nasignaturas) {
			file.writeInt(v[i].getId()); // id
			
			StringBuffer buffer = new StringBuffer(v[i].getNombre());
			buffer.setLength(15); // 15 caracteres para el nombre
			file.writeChars(buffer.toString());

			file.writeDouble(v[i].getNotamedia()); //
			file.writeInt(v[i].getIdCurso()); //

			StringBuffer buffer2 = new StringBuffer(v[i].getNombreCurso());
			buffer2.setLength(25); // 
			file.writeChars(buffer2.toString());

			file.writeInt(v[i].getNasignaturas()); 
		}

		file.close();

	}// 

	
	//llenar CURSOS
	private static void llenarCursos() throws IOException {
		RandomAccessFile file = new RandomAccessFile(ficheroc, "rw");

		Curso[] z = new Curso[6];		
		int cod[] = { 10, 20, 30, 40, 50, 60 };
		String nombre[] = { "Primer Curso DAM", 
				"Segundo Curso DAM", "Primero SMR", "Segundo SMR", 
				"Primero DAW", "Segundo DAW" };
	
		for (int ii = 0; ii < z.length; ii++) {
			z[ii] = new Curso(cod[ii], nombre[ii], 0, 0);
		}

		for (int i = 0; i < z.length; i++) {
			long posicion = (z[i].getId() - 1) * longRegCurso;

			file.seek(posicion); // nos posicionamos

			//int , String , int , double
			file.writeInt(z[i].getId()); // id

			StringBuffer buffer = new StringBuffer(z[i].getNombrecurso());
			buffer.setLength(25); // 25 caracteres para el nombre
			file.writeChars(buffer.toString());// insertar nombre

			
			file.writeInt(z[i].getNumeroalumnos());
			file.writeDouble(z[i].getNotamedia());
		}

		file.close();
	}// llenar CURSOS

	
	//llenar notas
	private static void llenarNotas() throws IOException {

		DataOutputStream dataOS = new DataOutputStream(new FileOutputStream(ficheronotas));

		// int idalumno, String nombreAsignatura; double nota;             

		//1dam
		dataOS.writeInt(1036);	dataOS.writeUTF("Programaci�n"); dataOS.writeDouble(5.6);
		dataOS.writeInt(1036);	dataOS.writeUTF("Entornos"); dataOS.writeDouble(7);
		dataOS.writeInt(1036);	dataOS.writeUTF("Ingl�s"); dataOS.writeDouble(8);
		dataOS.writeInt(1036);	dataOS.writeUTF("Lenguaje Marcas"); dataOS.writeDouble(6);
		dataOS.writeInt(1036);	dataOS.writeUTF("Bases de  Datos"); dataOS.writeDouble(9);
		
		//
		dataOS.writeInt(2044);	dataOS.writeUTF("Programaci�n"); dataOS.writeDouble(7);
		dataOS.writeInt(2044);	dataOS.writeUTF("Lenguaje Marcas"); dataOS.writeDouble(6.5);
		dataOS.writeInt(2044);	dataOS.writeUTF("Bases de  Datos"); dataOS.writeDouble(7.5);
		
		//2dam	
		dataOS.writeInt(2155);	dataOS.writeUTF("Programaci�n de Servicios y Procesos"); dataOS.writeDouble(8);
		dataOS.writeInt(2155);	dataOS.writeUTF("Acceso a Datos"); dataOS.writeDouble(7);
		dataOS.writeInt(2155);	dataOS.writeUTF("Sistemas de gesti�n empresarial"); dataOS.writeDouble(9);
		dataOS.writeInt(2155);	dataOS.writeUTF("Programaci�n Multimedia"); dataOS.writeDouble(7);
		dataOS.writeInt(2155);	dataOS.writeUTF("Dise�o de Interfaces"); dataOS.writeDouble(5);
		dataOS.writeInt(2155);	dataOS.writeUTF("FOL"); dataOS.writeDouble(5);
		
		//no existe curso
		dataOS.writeInt(5050);	dataOS.writeUTF("Matem�ticas I"); dataOS.writeDouble(7);
		dataOS.writeInt(5050);	dataOS.writeUTF("Inteligencia Artificial"); dataOS.writeDouble(5);
			
		//1smr
		dataOS.writeInt(5789);	dataOS.writeUTF("Aplicaciones Web"); dataOS.writeDouble(8);
		dataOS.writeInt(5789);	dataOS.writeUTF("Montaje y Mantenimiento"); dataOS.writeDouble(8);
		dataOS.writeInt(5789);	dataOS.writeUTF("Sistemas Operativos"); dataOS.writeDouble(8);
		
		dataOS.writeInt(1077);	dataOS.writeUTF("Aplicaciones Web"); dataOS.writeDouble(4);
		dataOS.writeInt(1077);	dataOS.writeUTF("Montaje y Mantenimiento"); dataOS.writeDouble(5);
		dataOS.writeInt(1077);	dataOS.writeUTF("Sistemas Operativos"); dataOS.writeDouble(6);
		dataOS.writeInt(1077);	dataOS.writeUTF("Redes Locales"); dataOS.writeDouble(4);
					
		dataOS.writeInt(2029);	dataOS.writeUTF("Aplicaciones Web"); dataOS.writeDouble(5);
		dataOS.writeInt(2029);	dataOS.writeUTF("Ingles"); dataOS.writeDouble(5);
	
		//2smr
		dataOS.writeInt(3040);	dataOS.writeUTF("Aplicaciones ofimaticas"); dataOS.writeDouble(9);
		dataOS.writeInt(3040);	dataOS.writeUTF("Seguridad"); dataOS.writeDouble(9);
		dataOS.writeInt(3040);	dataOS.writeUTF("Servicios en Red"); dataOS.writeDouble(7);
		
		//no existe curso
    	dataOS.writeInt(7709);	dataOS.writeUTF("Matem�ticas II"); dataOS.writeDouble(6);
		dataOS.writeInt(7709);	dataOS.writeUTF("Inteligencia Artificial"); dataOS.writeDouble(5);
		dataOS.writeInt(7709);	dataOS.writeUTF("Redes Neuronales"); dataOS.writeDouble(6);
			
			
		dataOS.close(); // cerrar stream

	}// llenar notas

}// fin

package ejerciciosPractica.ficherosAleatorios;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

public class CrearFicherosEmpleyDep {

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		// crea el fichero secuencial de objetos Departamento
		CreaDepartamentos();

		// crea el fichero aleatorio de empleados
		CreaEmpleadosAleatorio();

		// crea el fichero con los datos a modificar en el aleatorio
		CreaEmpleModif();

	}// main

	// crea el fichero secuencial de tipos primitivos con los datos a modificar
	private static void CreaEmpleModif() throws IOException {
		File fichero = new File("FichEmpleadosModif.dat");
		DataOutputStream dataOS = new DataOutputStream(new FileOutputStream(fichero));

		// DATOS a modificar
		int empno[] = { 21, 90, 36 };
		Float salario[] = { 1000.0f, 400.0f, 100.0f };
		//

		for (int i = 0; i < empno.length; i++) {
			dataOS.writeInt(empno[i]);
			dataOS.writeFloat(salario[i]);
		}
		dataOS.close(); // cerrar stream
	}

	// crea el fichero aleatorio de empleados a partir de un fichero secuencial
	private static void CreaEmpleadosAleatorio() throws IOException, ClassNotFoundException {

		// primero creo un fichero secuencial de objetos Empleado
		CreaEmpleSecuencial();

		// a partir de los datos de ese fichero creo el fichero aleatorio
		File fichero = new File("FichEmpleAleatorio.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");

		StringBuffer buffer = null;// buffer para almacenar apellido
		StringBuffer buffer2 = null;

		Empleado emple;
		File secuencial = new File("FichEmpleados.dat");
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(secuencial));

		try {
			while (true) { // lectura del fichero
				emple = (Empleado) dataIS.readObject();
				System.out.printf("Nº empleado %d %n", emple.getEmpno());

				// int empno, String apellido, String oficio, Float salario, int deptno
				// 4 + 30 + 20 + 4 + 4 + 30 = 92
				int id = emple.getEmpno();
				long posicion = (id - 1) * 92;
				file.seek(posicion); // nos posicionamos

				file.writeInt(emple.getEmpno());

				buffer = new StringBuffer(emple.getApellido());
				buffer.setLength(15);
				file.writeChars(buffer.toString());

				buffer2 = new StringBuffer(emple.getOficio());
				buffer2.setLength(10);
				file.writeChars(buffer2.toString());

				file.writeFloat(emple.getSalario());

				file.writeInt(emple.getDeptno()); // insertar departamento

				buffer = new StringBuffer(emple.getNombreDepartamento());
				buffer.setLength(15);
				file.writeChars(buffer.toString());

				System.out.println("Empleado Insertado: " + id);

			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA DEL SECUENCIAL.");
		}

		dataIS.close(); // cerrar stream de entrada
		file.close(); // cerrar fichero
	}

	// creo un fichero secuencial de objetos Empleado
	private static void CreaEmpleSecuencial() throws IOException {
		File fichero = new File("FichEmpleados.dat");// declara el fichero
		FileOutputStream fileout = new FileOutputStream(fichero);
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		// int empno, String apellido, String oficio, Float salario, int deptno
		Empleado e1 = new Empleado(36, "SÁNCHEZ", "EMPLEADO", 1040.0f, 10, "");
		Empleado e2 = new Empleado(12, "ARROYO", "VENDEDOR", 1500.0f, 20, "");
		Empleado e3 = new Empleado(21, "SALA", "VENDEDOR", 1625.0f, 20, "");
		Empleado e4 = new Empleado(5, "JIMÉNEZ", "DIRECTOR", 2900.0f, 30, "");
		Empleado e5 = new Empleado(7, "CEREZO", "DIRECTOR", 2885.0f, 30, "");
		Empleado e6 = new Empleado(29, "REY", "PRESIDENTE", 4100.0f, 30, "");
		Empleado e7 = new Empleado(50, "MARTÍN", "EMPLEADO", 2040.00f, 79, "");

		dataOS.writeObject(e1);
		dataOS.writeObject(e2);
		dataOS.writeObject(e3);
		dataOS.writeObject(e4);
		dataOS.writeObject(e5);
		dataOS.writeObject(e6);
		dataOS.writeObject(e7);

		dataOS.close(); // cerrar stream de salida

	}// CreaEmpleSecuencial

	// crea el fichero secuencial de objetos Departamento
	private static void CreaDepartamentos() throws IOException, ClassNotFoundException {

		File fichero = new File("FichDepartamentos.dat");
		FileOutputStream fileout = new FileOutputStream(fichero);
		try (ObjectOutputStream dataOS = new ObjectOutputStream(fileout)) {
			int dep[] = { 10, 20, 30, 40, 50 };
			String nombres[] = { "CONTABILIDAD", "VENTAS", "COMERCIO", "INFORMATICA", "PRODUCCION" };
			String loc[] = { "MADRID", "SEVILLA", "TOLEDO", "BILBAO", "GUADALAJARA" };

			int i;
			Departamento depar;

			for (i = 0; i < dep.length; i++) {
				depar = new Departamento(dep[i], nombres[i], loc[i]);
				dataOS.writeObject(depar);
			}
			dataOS.close();
		}catch(IOException e){
			
		}

		fileout.close();

	}// CreaDepartamentos

}// fin

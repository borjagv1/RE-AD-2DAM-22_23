package ejercicioEmpleyDepConFichSecuencialesyAleatorios;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
//Secuenciales se recomienda abrir y cerrar. aleatorios al final.
public class ModificarAleatorioEmple {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		try {
			File fichero = new File("FichEmpleAleatorio.dat");
			// Creamos una referencia para fichero aleatorio.
			RandomAccessFile fileAleatorio = new RandomAccessFile(fichero, "rw");
			// Ficheros binarios secuenciales.
			File ficheroDepartamentos = new File("FichDepartamentos.dat");
			File ficheroEmpleadosModif = new File("FichEmpleadosModif.dat");

			// El registro consta de: int empno, String apellido, String oficio, Float
			// salario, int deptno
			// Los bytes de cada campo son: 4 + 30 + 20 + 4 + 4 + 30 = 92
			int pos = 0;
			while (true) {
				fileAleatorio.seek((pos * 92) + 58);
				int idDepartamento = fileAleatorio.readInt();
				
				String nombreDepartamento = "No existe";
				if (idDepartamento != 0) {
					System.out.println("Id Departamento: " + idDepartamento);
					// Busco el departamento en el fichero aleatorio;
					try {
						FileInputStream fis = new FileInputStream(ficheroDepartamentos);
						ObjectInputStream ois = new ObjectInputStream(fis);
						Departamento departamento = (Departamento) ois.readObject();
						while (departamento != null) {
							if (departamento.getDep() == idDepartamento) {
								// Hemos encontrado el departamento.
								nombreDepartamento = departamento.getNombre();
								System.out.println("Encontrado nombreDepartamento: " + nombreDepartamento);
								break;
							}
							departamento = (Departamento) ois.readObject();
						}
					} catch (EOFException ex) {
					} catch (ClassNotFoundException ex) {
					}

					// Ya tenemos el nombre del departamento o "No existe" si no hay
					// Escribimos en el fichero el nombre del departamento.
					// Colocamos el puntero justo antes de los bytes reservados para el nombre del
					// departamento.
					fileAleatorio.seek((pos * 92) + 62);
					fileAleatorio.writeBytes(nombreDepartamento);
				}

				// Leemos el ID del empleado.
				fileAleatorio.seek((pos * 92));
				int idEmpleado = fileAleatorio.readInt();
				// Leemos el salario
				fileAleatorio.seek((pos * 92) + 54);
				float salario = fileAleatorio.readFloat();
				//System.out.println("ID Emp: " + idEmpleado + " salario: " + salario);

				// Una vez tenemos el ID del empleado, buscamos en el fichero
				// secuencial FichEmpleadosModif ese ID.
				if (idEmpleado != 0) {
					System.out.println("ID Emp: " + idEmpleado + " salario: " + salario);

					try {
						FileInputStream fis = new FileInputStream(ficheroEmpleadosModif);
						DataInputStream dis = new DataInputStream(fis);
						while (true) {
							int idEmplModif = dis.readInt();
							float subida = dis.readFloat();
							if (idEmpleado == idEmplModif) {
								System.out.println("subida: " + subida + "para el empleado: " + idEmpleado);
								// Realizamos la subida
								salario += subida;
								// Escribimos el nuevo salario en el fichero aleatorio.
								// Posicionamos el puntero antes de los bytes del salario.
								fileAleatorio.seek((pos * 92) + 54);
								fileAleatorio.writeFloat(salario);
							}
						}
					} catch (EOFException ex) {
					}
				}

				// Finalmente incrementamos el contador
				pos++;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Volvemos a leer el fichero aleatorio para mostrarlo.
		int pos = 0;

		try {
			File fichero = new File("FichEmpleAleatorio.dat");
			// Creamos una referencia para fichero aleatorio.
			RandomAccessFile fileAleatorio = new RandomAccessFile(fichero, "rw");

			while (true) { //recorro el fichero secuencialmente
				fileAleatorio.seek(pos * 92);
				int id = fileAleatorio.readInt();
				byte[] bytesApellido = new byte[30];
				fileAleatorio.read(bytesApellido);// NO hay un readString(), sino que se leen utilizando el método
													// read(byte[])
				String apellido = new String(bytesApellido);
				byte[] bytesOficio = new byte[20];
				fileAleatorio.read(bytesOficio);// NO hay un readString(), sino que se leen utilizando el método
												// read(byte[])
				String oficio = new String(bytesOficio);
				float salario = fileAleatorio.readFloat();
				int idDepart = fileAleatorio.readInt();
				byte[] bytesNomDep = new byte[30];
				fileAleatorio.read(bytesNomDep);// NO hay un readString(), sino que se leen utilizando el método
												// read(byte[])
				String nomDep = new String(bytesNomDep);
				
				if (id > 0)
				System.out.println("Registro: " + id + "-" + apellido.trim() + "-" + oficio.trim() + "-" + salario + "-" + idDepart
						+ "-" + nomDep.trim());
				pos++;
			}
		} catch (IOException ex) {

		}

	}

}

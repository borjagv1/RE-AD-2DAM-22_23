package recursosUnidad1.ficherosAleatorios;

import java.io.*;
public class CrearFichAleatorioDepartamentos {
	static File fichero = new File("AleatorioDepart.dat");
	public static void main(String[] args) throws IOException {
		// declara el fichero de acceso aleatorio
		
		if(fichero.exists()) {
			fichero.delete();
		}
		
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		// arrays con los datos
		int dep[] = { 10, 20, 30, 40 }; // departamentos
		String nombredep[] = { "VENTAS", "CONTABILIDAD", "DIRECCION", "PRODUCCION" };// apellidos
		String localidad[] = { "SEVILLA", "MADRID", "BILBAO", "VALENCIA" };
		StringBuffer buffer = null; // buffer para almacenar apellido
		int posicion;
		int id;
		int n = nombredep.length;// numero de elementos del array
		for (int i = 0; i < n; i++) { // recorro los arrays
			id = dep[i];
			posicion = (id - 1) * 58;
			file.seek(posicion);
			
			file.writeInt(id);
			
			buffer = new StringBuffer(nombredep[i]);
			buffer.setLength(12); // nombre dep
			file.writeChars(buffer.toString());// inserción nombre
			
			buffer = new StringBuffer(localidad[i]);
			buffer.setLength(15); // LOCALIDAD
			file.writeChars(buffer.toString());// inserción localidad
			
			System.out.println("Departamento " + id + " insertado... ");
		}
		file.close(); // cerrar fichero
		VisualizarDatos();
	}
	private static void VisualizarDatos() throws IOException {
		@SuppressWarnings("resource")
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		int id, posicion;
		char nombredep[] = new char[12], aux;
		char localidad[] = new char[15];
		posicion = 0; // para situarnos al principio
		for (;;) { // recorro el fichero	
			
			file.seek(posicion); // nos posicionamos en posicion
			//System.out.println("Posicion: "+ posicion + ", puntero: " + 
			//		file.getFilePointer());
 
			id = file.readInt(); // obtengo id de empleado
			// nombre de departamento
			for (int i = 0; i < nombredep.length; i++) {
				aux = file.readChar();
				nombredep[i] = aux; // los voy guardando en el array
			}
		
			String nombredepS = new String(nombredep);
			// localidad
			for (int i = 0; i < localidad.length; i++) {
				aux = file.readChar();
				localidad[i] = aux; // los voy guardando en el array
			}
			// convierto a String el array
			String localidadS = new String(localidad);
			if (id > 0)
				System.out.printf("ID: %d, Nombre departamento: %s, Localidad: %s %n", id, nombredepS, localidadS);
			
		    // Si he recorrido todos los bytes salgo del for
			if (file.getFilePointer() == file.length())
				break;
			
			// me posiciono para el sig empleado, cada empleado ocupa 36 bytes
			posicion = posicion + 58;
		} // fin bucle for
		
		file.close(); // cerrar fichero
	}
}


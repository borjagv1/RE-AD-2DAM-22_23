package recursosUnidad1.ficherosObjetos;


import java.io.*;

public class LeerFichObject {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Persona persona; // defino la variable persona
		File fichero = new File("FichPersona.dat");
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(fichero));

		int i = 1;
		try {
			while (true) { // lectura del fichero
				persona = (Persona) dataIS.readObject(); // leer una Persona
				System.out.print(i + "=>");
				i++;
				System.out.printf("Nombre: %s, edad: %d %n",
						persona.getNombre(),persona.getEdad());

			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
	}
}
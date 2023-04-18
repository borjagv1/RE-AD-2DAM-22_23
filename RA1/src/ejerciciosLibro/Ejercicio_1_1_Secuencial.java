package ejerciciosLibro;

import java.io.File;
//Mostrar los archivos de un directorio con listfiles(). Si no existe el dir que lo muestre
public class Ejercicio_1_1_Secuencial {

	public static void main(String[] args) {
		String dir = "Casa";
		File f = new File(dir);
		File[] listaficheros = f.listFiles();
		if (listaficheros != null) {
			System.out.printf("Ficheros en el directorio actual: %d %n", listaficheros.length);
			for (int i = 0; i < listaficheros.length; i++) {
				File f2 = listaficheros[i];
				System.out.printf("Nombre: %s%n", f2.getName());
			}

		}else {
			System.out.println("No existe el dir");
		}
		

	}
}

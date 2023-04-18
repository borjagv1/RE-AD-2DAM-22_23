package recursosUnidad1.ficheros_claseFile;


import java.io.*;

public class VerDir2 {
	public static void main(String[] args) {
		String dir = ".";
		File f = new File(dir);
		File[] listaficheros= f.listFiles();		
		System.out.printf("Ficheros en el directorio actual: %d %n", listaficheros.length);
		for (int i = 0; i < listaficheros.length; i++) {
			File f2 = listaficheros[i];
			System.out.printf("Nombre: %s, es fichero?: %b, es directorio?: %b %n", 
					f2.getName(), f2.isFile(),f2.isDirectory());
		}
	}
}

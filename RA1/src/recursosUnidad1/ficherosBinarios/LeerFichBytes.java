package recursosUnidad1.ficherosBinarios;

import java.io.*;
public class LeerFichBytes {
  public static void main(String[] args) throws IOException {
   File fichero = new File("C:\\EJERCICIOS\\UNI1\\FichBytes.dat");
   FileInputStream fic = new FileInputStream(fichero);    
   int i;
	
   while ((i = fic.read()) != -1)
	    System.out.println(i);
	
    fic.close();    
  }
}
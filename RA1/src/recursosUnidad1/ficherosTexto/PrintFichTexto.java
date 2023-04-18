package recursosUnidad1.ficherosTexto;

import java.io.*;
public class PrintFichTexto {
  public static void main(String[] args) {
  try{      
      PrintWriter  fichero = new PrintWriter (new FileWriter("FicTexto2.txt"));      
	  for (int i=1; i<11; i++)
	    fichero.println("Fila numero: "+i);	  		
     
      fichero.close(); 
	}
	catch (FileNotFoundException fn ){                      
               System.out.println("No se encuentra el fichero");}
	catch (IOException io) {
               System.out.println("Error de E/S ");}
  }
}

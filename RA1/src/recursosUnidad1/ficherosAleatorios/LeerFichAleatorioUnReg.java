package recursosUnidad1.ficherosAleatorios;

import java.io.*;
public class LeerFichAleatorioUnReg {
  @SuppressWarnings("unused")
public static void main(String[] args) throws IOException {     
   File fichero = new File("AleatorioEmple.dat");
   //declara el fichero de acceso aleatorio
   RandomAccessFile file = new RandomAccessFile(fichero, "r");
   //
   int  id, dep, posicion;    
   Double salario;  
   char apellido[] = new char[10], aux;
   int registro = 7; //Integer.parseInt(args[0]); //recoger id de empleado a consultar      
   
   posicion = (registro -1 ) * 36;
   if(posicion >= file.length() )   
	  System.out.printf("ID: %d, NO EXISTE EMPLEADO...",registro); 
    else{	
	   file.seek(posicion); //nos posicionamos 
	   id=file.readInt(); // obtengo id de empleado	  
	   for (int i = 0; i < apellido.length; i++) { //Se hace siempre para leer cadenas de caracteres
			 aux = file.readChar();//recorro uno a uno los caracteres del apellido 
			 apellido[i] = aux;    //los voy guardando en el array
	   }  	 
	  String apellidoS= new String(apellido);//convierto a String el array
	  dep=file.readInt();//obtengo dep
	  salario=file.readDouble();  //obtengo salario
		  
	  System.out.println("ID: " + registro + ", Apellido: "+ 
	        apellidoS.trim() + 
	         ", Departamento: "+dep + ", Salario: " + salario);        
    }  
   file.close();  //cerrar fichero 
   }
}
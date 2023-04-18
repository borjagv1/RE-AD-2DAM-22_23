package recursosUnidad1;

public class ejemploExcepciones {
public static void main(String[] args) {   
    String cad1="20", cad2="0", mensaje="";
	int nume, denom, cociente;
	int[] arraynum = new int[4];	
	
    try {
          //codigo que puede producir errores
    	try {
		  arraynum[6]=20;
    	}  catch (ArrayIndexOutOfBoundsException ex) {
		    mensaje="Fuera de rango en el array";
		    arraynum[9]=20;
	   }
          nume=Integer.parseInt(cad1);
          denom=Integer.parseInt(cad2);
          cociente=nume/denom;
          mensaje=String.valueOf(cociente);		
    } 
    /*
    catch(NumberFormatException ex){
            mensaje="caracteres no num�ricos";
    } catch(ArithmeticException ex){
            mensaje="Division por cero";
    } 
   */
    
    catch (Exception ex) {
    	 System.err.println("toString           => "+ ex.toString()); 
    	 System.err.println("getMessage         => "+ ex.getMessage()); 
    	 System.err.println("getLocalizedMessage=> "+ ex.getLocalizedMessage()); 
    	 ex.printStackTrace(); 		
	}	
	finally {
		 System.out.println("SE EJECUTA SIEMPRE"); 
	}
    System.out.println(mensaje); 
	
 }//
}//


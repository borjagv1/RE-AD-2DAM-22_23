package recursosUnidad1;

public class ejemploExcepcion {
public static void main(String[] args) {   
    int nume=10, denom=0, cociente;
    
    try {
     cociente=nume/denom;
      System.out.println("Resultado:" +cociente); 	
	}	
	catch(ArithmeticException a) {		
	   System.err.println("getLocalizedMessage():" +
	      a.getLocalizedMessage());
	   System.out.println("getLocalizedMessage():" +
	     a.getLocalizedMessage());
	   System.err.println("getMessage():" +
	      a.getMessage());	  
	}
	/*
	catch (NumberFormatException n) {		
		 System.err.println("Exception");
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		System.out.println("se ejecuta siempre");
	}
	*/
	
    
 }//
}//

package ejerciciosLibro;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Clientes;
import datos.Productos;
import datos.Ventas;

public class Ejercicio_3_5 {

	private static SessionFactory sesion;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").
		setLevel(java.util.logging.Level.SEVERE);
		
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		
		System.out.println("LECTURA DE DATOS: ");

		int idcliente = leerEntero("Introduce idcliente: ", 0, 9999);
		while (idcliente != 0) {		

			Clientes cli = (Clientes) session.get(Clientes.class, (short) idcliente);	
			
			if (cli==null) {			
				System.out.println("El cliente no existe");
			}
			else
			{
				System.out.printf("Nombre Cliente: %s%n",cli.getNombre());
				//Obtenemos ventas del cliente:
				Set<Ventas> listaventas = cli.getVentases();
				int cont = 0;
				float importeTotal = 0;
				for (Ventas vent : listaventas) {
					Productos prod = (Productos) session.get(Productos.class, (short) vent.getProductos().getId());	
					
					float PVP = prod.getPvp().floatValue();
					
					System.out.printf(
							"Venta: IDVENTA: %d FECHAVENTA: %s%n\tProducto: %s%n\tCantidad: %d PVP: %.2f%n\tImporte: %.2f%n%n",
							vent.getIdventa(), vent.getFechaventa(), prod.getDescripcion(), vent.getCantidad(),
							prod.getPvp(), (PVP * vent.getCantidad()));
					
					cont++; 
					importeTotal = (PVP * vent.getCantidad()) + importeTotal;
				
				}
				System.out.println("NÚMERO TOTAL DE VENTAS: " + cont);
				System.out.println("IMPORTE TOTAL DE VENTAS: " + importeTotal);
			}//FINIF
			
			idcliente = leerEntero("\rIntroduce idCliente: ", 0, 9999);

			
		} // FIN WHILE

		System.out.println("\nFIN EJERCICIO");

		
		
		session.close();
		System.exit(0);

	}//MAIN
	
	private static int leerEntero(String mensaje, int min, int max) {
		boolean salir = false;
		int numero = 0;

		do {
			try {
				System.out.print(mensaje);
				numero = sc.nextInt();
				sc.nextLine();
				while (numero < min || numero > max) {
					System.out.print("\tSuperado límite (> " + min + " y < " + max + ")");
					System.out.print("\n\tOtra vez: ");

					numero = sc.nextInt();
					sc.nextLine();
					System.out.println();
				}
				salir = true;

			} catch (InputMismatchException exc) {
				sc.nextLine();
				System.out.print("\n\tIncorrecto, escríbelo de nuevo: ");
			}
		} while (!salir);

		return numero;
	}// LecturaEntero
	
	
}//class

package ejerciciosLibro;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Clientes;

public class Ejercicio_3_5_copy {

	// obtengo la sesion
	private static SessionFactory sesion;

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// código para no mostrar los warnings producidos por hibernate
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);

		// Obtengo las sesión
		sesion = HibernateUtil.getSessionFactory();

		// Comienzo la sesión
		Session session = sesion.openSession();

		short id = 1;

		// Cargo el objeto cliente
		Clientes cliente = (Clientes) session.get(Clientes.class, id);
		// Si el cliente no existe lo mostramos por pantalla
		if (cliente == null) {
			System.out.println("El cliente no existe");
			System.exit(0);
		} else {

			// Obtengo un listado de todas las ventas del cliente
			// Obtenemos las ventas del cliente
			Set<datos.Ventas> ventas = cliente.getVentases();
			// si no hay ventas salimos
			if (ventas.isEmpty()) {
				System.out.println("El cliente no tiene ventas");
				System.exit(0);
			}
			System.out.println("Ventas del cliente " + cliente.getNombre());
			double importeTotal = 0;
			int cantidadTotal = 0;
			// recorremos las ventas con un for each
			for (datos.Ventas venta : ventas) {
				System.out.printf("Venta: %s Fecha venta: %s%n\tProducto: %s%n\tCantidad: %s PVP: %s%n\tImporte: %s%n",
						venta.getIdventa(), venta.getFechaventa(), venta.getProductos().getDescripcion(),
						venta.getCantidad(), venta.getProductos().getPvp(),
						venta.getProductos().getPvp().multiply(BigDecimal.valueOf(venta.getCantidad())));

				importeTotal += venta.getProductos().getPvp().multiply(BigDecimal.valueOf(venta.getCantidad()))
						.doubleValue();
				cantidadTotal++;
			}
			System.out.println("Importe total: " + importeTotal);
			System.out.println("Cantidad total de ventas: " + cantidadTotal);
		}
		// cerramos la sesión
		session.close();
		System.exit(0);
	}

}// class

package ejerciciosLibro;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import datos.Clientes;
import datos.HibernateUtil;
import datos.Ventas;

/* Nos da el ID del cliente. P.EJ ID CLIENTE 1
 * 
 * Ventas del cliente: Nombre de cliente 
 * Venta: idventa, Fecha venta: fecha
		Producto: descripción del producto
		Cantidad: cantidad PVP: pvp
		Importe: cantidad * PVP

 * Venta: idventa, Fecha venta: fecha
		Producto: descripción del producto
		Cantidad: cantidad PVP: pvp
		Importe: cantidad * PVP
Número total de ventas:

Importe Total:
 */
public class Ejercicio_3_7 {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		
		String hql = "from Clientes c where c.id = 1";
		Query q = session.createQuery(hql);
		List<Clientes> lista = q.list();
		// Comprobamos si la lista está vacía utilizando el método isEmpty
		// en lugar de comparar su tamaño con 0
		if (lista.isEmpty()) {
			System.out.println("La lista está vacía");
		} else {
			// Obtenemos el tamaño de la lista utilizando el método size
			// en lugar de acceder a la propiedad size de ArrayList
			System.out.printf("La lista tiene %d elementos%n", lista.size());
			

			for (Clientes clientes : lista) {
				System.out.printf("Ventas del cliente: %s%n", clientes.getNombre());

				// Obtengo el listado de ventas de mi objeto clientes (con id 1)
				Set<Ventas> ventas = clientes.getVentases();
				
				// Obtenemos un flujo de objetos Ventas y lo ordenamos según el ID de la venta en orden ascendente
				List<Ventas> ventasOrdenadas = ventas.stream()
                        .sorted((v1, v2) -> Integer.compare(v1.getIdventa(), v2.getIdventa()))
                        .collect(Collectors.toList());


				int numtotaldeVentas = 0;
				BigDecimal importeTotaldeVentas = new BigDecimal(0);
				// Recorremos la lista de ventas
				for (Ventas v : ventasOrdenadas) {
					BigDecimal importe = (v.getProductos().getPvp().multiply(BigDecimal.valueOf(v.getCantidad())));
					System.out.printf(
							"Venta: %d  Fecha venta: %s%n\t " + "Producto: %s%n\t " + "Cantidad: %d\t "
									+ "PVP: %.2f%n\t " + "Importe: %.2f%n",
							v.getIdventa(), v.getFechaventa(), v.getProductos().getDescripcion(), v.getCantidad(),
							v.getProductos().getPvp(), importe);

					numtotaldeVentas++;
					importeTotaldeVentas = importe.add(importeTotaldeVentas);
				}
				System.out.println("Nº DE VENTAS: " + numtotaldeVentas);
				System.out.println("IMPORTE TOTAL DE VENTAS: " + importeTotaldeVentas);

			} // FOREACH
		} // IFELSE

		session.close();
		System.exit(0);
	}// MAIN

}//CLASS

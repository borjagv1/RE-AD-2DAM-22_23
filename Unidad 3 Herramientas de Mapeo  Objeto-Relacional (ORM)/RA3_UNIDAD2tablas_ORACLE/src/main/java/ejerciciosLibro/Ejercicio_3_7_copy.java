package ejerciciosLibro;


import java.math.BigDecimal;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
public class Ejercicio_3_7_copy {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		try {
			// consulta HQL para obtener el nombre del cliente 1
			String hql = "from Clientes where id = :idCliente";
			Clientes cli = session.createQuery(hql, Clientes.class)
					.setParameter("idCliente", (short) 1).uniqueResult();
			// Mostramos los datos por pantalla y las ventas del cliente
			System.out.println("Ventas del Cliente: " + cli.getNombre());

			//Obtengo las ventas del cliente
			Set<Ventas> ventas = cli.getVentases();
			//Recorro las ventas del cliente
			float importeTotal = 0;
			int numeroVentas = 0;
			for (Ventas v : ventas) {
				System.out.println("Venta: " + v.getIdventa() + " Fecha venta: " + v.getFechaventa());
				System.out.println("\tProducto: " + v.getProductos().getDescripcion());
				System.out.println("\tCantidad: " + v.getCantidad() + " PVP: " + v.getProductos().getPvp());
				System.out.println("\tImporte: " + v.getProductos().getPvp().multiply(new BigDecimal(v.getCantidad())));

				importeTotal += v.getProductos().getPvp().multiply(new BigDecimal(v.getCantidad())).floatValue();
				numeroVentas++;
			}
			System.out.println("Número total de ventas: " + numeroVentas);
			System.out.println("Importe Total: " + importeTotal);
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO" + e.getMessage());
		} finally {
			session.close();
			System.exit(0);
		}
	}
}
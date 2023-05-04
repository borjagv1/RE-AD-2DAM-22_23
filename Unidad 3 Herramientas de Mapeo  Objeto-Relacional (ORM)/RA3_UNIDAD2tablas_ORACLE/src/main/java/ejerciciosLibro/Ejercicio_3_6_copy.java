package ejerciciosLibro;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import datos.Clientes;
import datos.HibernateUtil;
import datos.Productos;
import datos.Ventas;

public class Ejercicio_3_6_copy {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.ALL);

		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// creamos varios objetos productos, varios objetos clientes y varios objetos
			// ventas
			/*
			 * private short id;
			 * private String descripcion;
			 * private Short stockactual;
			 * private Short stockminimo;
			 * private BigDecimal pvp;
			 * private Set<Ventas> ventases = new HashSet<Ventas>(0);
			 */
			Productos p1 = new Productos();
			p1.setId((short) 10);
			p1.setDescripcion("Producto 10");
			p1.setStockactual((short) 37);
			p1.setStockminimo((short) 5);
			p1.setPvp(new BigDecimal(14));

			InsertarProducto(p1);

			Productos p2 = new Productos((short) 20, "Producto 20", (short) 20, (short) 5, (new BigDecimal(48)), null);

			InsertarProducto(p2);

			Productos p3 = new Productos((short) 30, "Producto 30", (short) 30, (short) 5, (new BigDecimal(48)), null);

			InsertarProducto(p3);

			Productos p4 = new Productos((short) 40, "Producto 40", (short) 40, (short) 5, (new BigDecimal(48)), null);

			InsertarProducto(p4);

			// ------------
			/*
			 * private short id;
			 * private String nombre;
			 * private String direccion;
			 * private String poblacion;
			 * private String telef;
			 * private String nif;
			 * private Set<Ventas> ventases = new HashSet<Ventas>(0);
			 */
			Clientes cli1 = new Clientes((short) 10, "Cliente 10", "Dirección 10", "Población 10", "Teléf 10", "NIF 10",
					null);

			InsertarCliente(cli1);

			Clientes cli2 = new Clientes((short) 20, "Cliente 20", "Dirección 20", "Población 20", "Teléf 20", "NIF 20",
					null);

			InsertarCliente(cli2);

			Clientes cli3 = new Clientes((short) 30, "Cliente 30", "Dirección 30", "Población 30", "Teléf 30", "NIF 30",
					null);

			InsertarCliente(cli3);

			Clientes cli4 = new Clientes((short) 40, "Cliente 40", "Dirección 40", "Población 40", "Teléf 40", "NIF 40",
					null);

			InsertarCliente(cli4);

			// ------------
			/*
			 * private int idventa;
			 * private Clientes clientes;
			 * private Productos productos;
			 * private Date fechaventa;
			 * private short cantidad;
			 */
			Ventas v1 = new Ventas(10, cli1, p1, new Date(), (short) 10);
			InsertarVenta(v1); //aquí controlo las excepciones

			Ventas v2 = new Ventas(20, cli2, p2, new Date(), (short) 20);
			InsertarVenta(v2); //aquí controlo las excepciones

			Ventas v3 = new Ventas(30, cli3, p3, new Date(), (short) 30);
			InsertarVenta(v3); //aquí controlo las excepciones

			Ventas v4 = new Ventas(40, cli4, p4, new Date(), (short) 40);
			InsertarVenta(v4); //aquí controlo las excepciones 

		} catch (TransientPropertyValueException tpv) {
			// Manejar la excepción por violación de restricciones
			System.err.println("Error al actualizar los objetos: " + tpv.getMessage());
			tx.rollback();

		} catch (ConstraintViolationException cve) {
			// Manejar la excepción por violación de restricciones
			System.err.println("Error al actualizar los objetos: " + cve.getMessage());
			tx.rollback();
		} catch (HibernateException he) {
			// Manejar cualquier otra excepción de Hibernate
			System.err.println("Error al actualizar los objetos: " + he.getMessage());
			tx.rollback();
		} catch (Exception e) {
			// Manejar cualquier otra excepción
			System.err.println("Error al actualizar los empleados: " + e.getMessage());
			tx.rollback();
		} finally {
			// Cerrar la sesión
			session.close();
		}

	}// main

	private static void InsertarVenta(Ventas v) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		int err = 0;
		//cargo objeto producto, cliente a ver si existen
		Productos p = (Productos) session.get(Productos.class, v.getProductos().getId());
		if (p == null) {
			System.out.println("El producto no existe, no se puede insertar la venta");
			err++;
		}
		Clientes cli = (Clientes) session.get(Clientes.class, v.getClientes().getId());
		if (cli == null) {
			System.out.println("El cliente no existe no se puede insertar la venta");
			err++;
		}

		// controlamos que la cantidad de la venta a insertar no sea mayor que el stockminimo. Y actualizamos el stock
		if ((p.getStockactual() - v.getCantidad()) < p.getStockminimo()) {
			System.out.println(
					"La cantidad de la venta deja el producto por debajo del stock mínimo.");
			err++;
		}
		
		if (err > 0) {
			System.out.println("No se puede insertar la venta");
			return;
		} else {
			System.out.println("Se puede insertar la venta");
			
			p.setStockactual((short) (p.getStockactual() - v.getCantidad()));
			//actualizamos el producto	
			ActualizarProducto(p);

			try {
				session.save(v);
				tx.commit();
				System.out.println("Venta insertada correctamente: " + v.getIdventa());
			} catch (HibernateException he) {
				// Manejar cualquier otra excepción de Hibernate
				System.err.println("Error al insertar la venta: " + he.getMessage());
				tx.rollback();
			} catch (Exception e) {
				// Manejar cualquier otra excepción AQUÍ ES DONDE ENTRA
				System.err.println("Error al insertar la venta: [" + e.getMessage() + "] VENTA " + v.getIdventa() + " ERROR NO CONTROLADO");
				tx.rollback();
			} finally {
				// Cerrar la sesión
				session.close();
			}
		}
	}

	private static void ActualizarProducto(Productos p) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(p);
			tx.commit();
			System.out.println("Producto actualizado correctamente: " + p.getDescripcion() + " stock actual: " + p.getStockactual() + " stock mínimo: " + p.getStockminimo());
		} catch (HibernateException  he) {
			// Manejar cualquier otra excepción de Hibernate
			System.err.println("Error al actualizar el producto: " + he.getMessage());
			tx.rollback();
		} catch (Exception e) {
			// Manejar cualquier otra excepción
			System.err.println("Error al actualizar el producto: " + e.getMessage());
			tx.rollback();
		} finally {
			// Cerrar la sesión
			session.close();
		}
	}

	private static void InsertarCliente(Clientes cli) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(cli);
			tx.commit();
			System.out.println("Cliente insertado correctamente: " + cli.getNombre());
		} catch (HibernateException he) {
			// Manejar cualquier otra excepción de Hibernate
			System.err.println("Error al insertar el cliente: " + he.getMessage());
			tx.rollback();
		} catch (Exception e) {
			// Manejar cualquier otra excepción
			System.err.println("Error al insertar el cliente: " + e.getMessage());
			tx.rollback();
		} finally {
			// Cerrar la sesión
			session.close();
		}
	}

	private static void InsertarProducto(Productos p) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(p);
			tx.commit();
			System.out.println("Producto insertado correctamente: " + p.getDescripcion());
		} catch (HibernateException he) {
			// Manejar cualquier otra excepción de Hibernate
			System.err.println("Error al insertar el producto: " + he.getMessage());
			tx.rollback();
		} catch (Exception e) {
			// Manejar cualquier otra excepción
			System.err.println("Error al insertar el producto: " + e.getMessage());
			tx.rollback();
		} finally {
			// Cerrar la sesión
			session.close();
		}
	}
}// class

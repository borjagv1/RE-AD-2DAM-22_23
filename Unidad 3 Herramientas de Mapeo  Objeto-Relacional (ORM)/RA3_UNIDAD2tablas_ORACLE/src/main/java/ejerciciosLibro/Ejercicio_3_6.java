package ejerciciosLibro;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;

import datos.Clientes;
import datos.HibernateUtil;
import datos.Productos;
import datos.Ventas;

public class Ejercicio_3_6 {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.ALL);

		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		BigDecimal pvp = new BigDecimal(14);

		// PRODUCTO 3
		Productos prod = new Productos();
		prod.setDescripcion("Lapicero láser");
		prod.setId((short) 3);
		prod.setPvp(pvp);
		prod.setStockactual((short) 37);
		prod.setStockminimo((short) 5);

		InsertarProducto(prod);

		// PRODUCTO 9
		prod = new Productos();
		prod.setDescripcion("metro de 3m");
		prod.setId((short) 9);
		prod.setPvp(pvp);
		prod.setStockactual((short) 4);
		prod.setStockminimo((short) 1);

		InsertarProducto(prod);

		Clientes cli = new Clientes();
		// cliente 5
		cli.setDireccion("C/ Ávila 20");
		cli.setId((byte) 5);
		cli.setNif("23499345H");
		cli.setNombre("Manuel García");
		cli.setPoblacion("Guadalajara");
		cli.setTelef("949333242");

		InsertaCliente(cli);

		cli = new Clientes();
		// cliente 6
		cli.setDireccion("C/ Madrid 45");
		cli.setId((byte) 6);
		cli.setNif("12345678P");
		cli.setNombre("Francisco Mendozza");
		cli.setPoblacion("Alcalá de Henares");
		cli.setTelef("913567892");

		InsertaCliente(cli);

		// PARA LA VENTA PIDE REALIZAR UN MÉTODO CON LOS DATOS A INSERTAR
		short cantidad = 10;
		cli = new Clientes();
		cli.setId((short) 6);
		Date fecha = new java.util.Date();
		java.sql.Date fechaventa = new java.sql.Date(fecha.getTime());
		int id = 87;
		prod = new Productos();
		prod.setId((short) 9);

		InsertarVenta(cantidad, cli, fechaventa, id, prod);
		session.close();
		System.exit(0);
	}// MAIN

	private static void InsertarVenta(short cantidad, Clientes cli, java.sql.Date fechaventa, int id, Productos prod) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		
		System.out.println("Venta " + id);
		int err = 0;

		Productos p = (Productos) session.get(Productos.class, (short) prod.getId());
		if (p == null) {
			System.out.println("El PRODUCTO no existe, no se inserta la venta ");
			err++;
		}

		Clientes c = (Clientes) session.get(Clientes.class, (short) cli.getId());
		if (c == null) {
			System.out.println("El CLIENTE no existe, no se inserta la venta ");
			err++;
		}

		// COMPROBAR SI HAY STOCK
		if (p != null)
			if ((p.getStockactual() - cantidad) >= p.getStockminimo()) {
				System.out.println("NO se inserta La venta, NO hay stock");
				err++;
			}

		if (err > 0)
			return;

		// ACTUALIZA STOCK
		p.setStockactual((short) (p.getStockactual() - cantidad));

		Ventas v = new Ventas();
		v.setCantidad(cantidad);
		v.setClientes(cli);
		v.setFechaventa(fechaventa);
		v.setIdventa(id);
		v.setProductos(prod);
		
		session.update(p);
		session.save(v);
		
		try {
			tx.commit();
			System.out.println("Venta Insertada: " + v.getIdventa());
		} catch (javax.persistence.PersistenceException ex) {
			if (ex.getMessage().contains("ConstraintViolationException")) {
				System.out.println(" Venta existente: " + v.getIdventa());
			} else {
				System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
			}
		} // TRY
		session.close();
	}

	private static void InsertaCliente(Clientes cli) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		try {
			session.save(cli); // Hce que la instancia sea persistente.
			try {
				tx.commit();
				System.out.println(cli.getId() + " => Cliente insertado: " + cli.getNombre());

			} catch (javax.persistence.PersistenceException e) {
				if (e.getMessage().contains("ConstraintViolationException")) {
					System.out.println(cli.getId() + " => CLIENTE DUPLICADO " + cli.getNombre());
				} else {
					System.out.printf("MENSAJE: %s%n", e.getMessage());
				}
			}
		} catch (TransientPropertyValueException e) {
			System.out.println("EL CLIENTE NO EXISTE");
			System.out.printf("MENSAJE: %s%n", e.getMessage());
			System.out.printf("Propiedad: %s%n", e.getPropertyName());
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO....");
			e.printStackTrace();
		}

		session.close();

	}

	private static void InsertarProducto(Productos prod) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		session.save(prod);

		try {
			tx.commit();
			System.out.println("Producto Insertado: " + prod.getDescripcion());
		} catch (javax.persistence.PersistenceException ex) {
			if (ex.getMessage().contains("ConstraintViolationException")) {
				System.out.println(prod.getId() + " Producto existente: " + prod.getDescripcion());
			} else {
				System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
			}
		} // TRY
		session.close();
	}// InsertProdu

}// class

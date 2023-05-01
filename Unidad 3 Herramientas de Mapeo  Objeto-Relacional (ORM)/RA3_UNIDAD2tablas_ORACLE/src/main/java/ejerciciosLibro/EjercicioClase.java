package ejerciciosLibro;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.HibernateUtil;
import datos.Tlineasventas;
import datos.TlineasventasId;

public class EjercicioClase {
	/*
	 * 
	 * Con la base de datos tablas TVENTAS. MOSTRAR LOS DATOS DE LA LINEA DE VENTA
	 * CON IDVENTA: V100 Y NUMERO LINEA 1
	 * 
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
		// Obtener la sesión actual
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		// crear la sesión
		Session session = sesion.openSession();
		String idventa = "V100";
		byte numerolinea = 1;

		TlineasventasId tventasid = new TlineasventasId(idventa, numerolinea);
		Tlineasventas tventas = (Tlineasventas) session.get(Tlineasventas.class, (TlineasventasId) tventasid);

		if (tventas == null) {
			System.out.println("NO EXISTE EL NUMEROVENTAS!!");

		} else {
			System.out.println("Cantidad: " + tventas.getCantidad());
			System.out.println("Producto: " + tventas.getTproductos().getDescripcion());
			System.out.println("PVP: " + tventas.getTproductos().getPvp());

			double importe = tventas.getTproductos().getPvp().floatValue();
			System.out.printf("Importe: %-10.2f %n", (importe * tventas.getCantidad()));
		}
		session.close();
		System.exit(0);

	}

}

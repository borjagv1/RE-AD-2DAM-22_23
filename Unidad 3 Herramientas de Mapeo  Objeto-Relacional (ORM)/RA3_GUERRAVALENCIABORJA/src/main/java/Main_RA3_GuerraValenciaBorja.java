import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.id.IdentifierGenerationException;

import datos.Camisetas;
import datos.Ciclistas;
import datos.Etapas;
import datos.HibernateUtil;
import datos.Lleva;
import datos.LlevaId;
import datos.NuevasCamisetas;

public class Main_RA3_GuerraValenciaBorja {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		ejercicio1BGV();

		int etapa = 0;
		int ciclista = 0;
		int camiseta = 0;

		ejercicio2BGV(etapa, ciclista, camiseta);

		ejercicio4BGV();

	}// MAIN

	private static void ejercicio4BGV() {
		System.out.println("select e.codigoetapa, e.km, e.pobsalida, c.nombreciclista FROM Etapas e\r\n"
				+ "JOIN e.ciclistas c\r\n" + "WHERE e.tipoetapa = 'Montaña' and e.pobsalida = e.pobllegada");

		System.out.println(
				"select e.ciclistas.codigociclista, e.ciclistas.nombreciclista, e.codigoetapa, e.tipoetapa, t.codigotramo, t.nombretramo, t.categoria \r\n"
						+ "from Etapas e, Tramospuertos t\r\n"
						+ "where t.pendiente = '10 km al 5,5%' and e.ciclistas.codigociclista = t.ciclistas.codigociclista");

	}

	private static void ejercicio2BGV(int etapa, int ciclista, int camiseta) {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		etapa = 1;
		ciclista = 1;
		camiseta = 30;
		System.out.println("Ejercicio 2 - Insertar en tabla LLEVA");
		System.out.println("========================================================");
		System.out
				.println("Datos a insertar (etapa :" + etapa + " - ciclista: " + ciclista + " - camiseta: " + camiseta);
		BigInteger codci = BigInteger.valueOf(ciclista);
		BigInteger codet = BigInteger.valueOf(etapa);
		BigInteger codcam = BigInteger.valueOf(camiseta);
		int err = 0;

		Etapas etapas = (Etapas) session.get(Etapas.class, (BigInteger) codet);
		if (etapas == null) {
			System.out.println("No existe la etapa " + codet);
			err++;
		}
		Ciclistas ciclistas = (Ciclistas) session.get(Ciclistas.class, (BigInteger) codci);
		if (ciclistas == null) {
			System.out.println("No existe el ciclista " + codci);
			err++;
		}

		Camisetas camisetas = (Camisetas) session.get(Camisetas.class, (BigInteger) codcam);

		if (camisetas == null) {
			System.out.println("No existe la camiseta " + codcam);
			err++;
		}

		LlevaId llevaid = new LlevaId(codcam, codet);

		Lleva lleva = (Lleva) session.get(Lleva.class, llevaid);

		if (lleva != null) {
			System.out.println("REGISTRO EXISTENTE ");
			System.out.println("La camiseta: " + lleva.getCamisetas().getCodigocamiseta()
					+ ", ya ha sido asignada en la etapa: " + lleva.getEtapas().getCodigoetapa() + "\nAl ciclista "
					+ lleva.getCiclistas().getCodigociclista() + ", " + lleva.getCiclistas().getNombreciclista());
			err++;
		} else {
			System.out.println("REGISTRO EXISTENTE");
			System.out.println("Camiseta: " + camisetas.getCodigocamiseta() + ", " + camisetas.getColor());
			System.out.println("Etapa: " + etapas.getCodigoetapa() + ", " + etapas.getTipoetapa());
			System.out.println("Ciclista: " + ciclistas.getCodigociclista() + ", " + ciclistas.getNombreciclista());

			err++;
		}

		if (err > 0) {
			System.out.println("NO SE PUEDE INSERTAR EL REGISTRO");
			return;
		}

		Lleva ll = new Lleva();
		System.out.println("REGISTRO INSERTADO");
		ll.setEtapas(etapas);
		ll.setCamisetas(camisetas);
		ll.setCiclistas(ciclistas);
		try {
			session.save(ll);

			try {
				tx.commit();
				System.out.println("Camiseta: " + camisetas.getCodigocamiseta() + ", " + camisetas.getColor());
				System.out.println("Etapa: " + etapas.getCodigoetapa() + ", " + etapas.getTipoetapa());
				System.out.println("Ciclista: " + ciclistas.getCodigociclista() + ", " + ciclistas.getNombreciclista());

			} catch (javax.persistence.PersistenceException ex) {
				if (ex.getMessage().contains("ConstraintViolationException")) {

				} else {
					System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
				}
			} // TRY
		} catch (TransientPropertyValueException e) {
			System.out.println("EL OBJETO NO EXISTE");
			System.out.printf("MENSAJE: %s%n", e.getMessage());
			System.out.printf("Propiedad: %s%n", e.getPropertyName());
		} catch (org.hibernate.id.IdentifierGenerationException e) {
			System.out.println("EL REGISTRO YA EXISTE....");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO....");
			e.printStackTrace();
		}
		session.close();
		System.exit(0);
	}

	private static void ejercicio1BGV() {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		// Transaction tx = session.beginTransaction();

		List<NuevasCamisetas> ncamisetas = session.createQuery("from NuevasCamisetas", NuevasCamisetas.class).list();

		Camisetas camise = new Camisetas();

		System.out.println("Ejercicio 1 - Insertar Nuevas camisetas");
		System.out.println("=======================================");

		for (NuevasCamisetas nuevasCamisetas : ncamisetas) {
			sesion = HibernateUtil.getSessionFactory();
			session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			camise.setCodigocamiseta(nuevasCamisetas.getCodigocamiseta());
			camise.setTipo(nuevasCamisetas.getTipo());
			camise.setColor(nuevasCamisetas.getColor());
			camise.setImportepremio(nuevasCamisetas.getImportepremio());

			session.save(camise);

			try {
				tx.commit();
				System.out.println("CAMISETA: " + camise.getCodigocamiseta() + ", " + camise.getColor() + " AÑADIDA");
				session.close();
			} catch (javax.persistence.PersistenceException ex) {
				if (ex.getMessage().contains("ConstraintViolationException")) {
					System.out.println(
							"Camiseta: " + camise.getCodigocamiseta() + ", " + camise.getColor() + " YA EXISTE");
				} else {
					System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
				}
			}
		}
	}

}// CLASS

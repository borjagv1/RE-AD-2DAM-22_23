import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.Camisetas;
import datos.Ciclistas;
import datos.Equipos;
import datos.Etapas;
import datos.HibernateUtil;
import datos.Lleva;
import datos.LlevaId;
import datos.NuevasCamisetas;
import datos.ResumenCamisetas;
import datos.ResumenCamisetasId;

/**
 * Main2_RA3_bgv
 */
public class Main2_RA3_bgv {

	private static SessionFactory sesión = HibernateUtil.getSessionFactory();
	private static Session session = sesión.openSession();

	public static void main(String[] args) {

		// ejercicio1BGV();

		// int etapa = 5, ciclista = 15, camiseta = 20;
		// ejercicio2BGV(etapa, ciclista, camiseta);

		// ejercicio3BGV();

		// ejercicio4BGV();

		ejercicio5BGV();

		session.close();

	}

	private static void ejercicio5BGV() {
		//revisarlas, hay que cambiar cosas del tipo c.ciclistas.codigociclista Están solo formateadas de los MYSQL a HQL

		//1 CONSULTA EN HQL:
		// String hql = "SELECT e.codigoetapa, e.km, e.pobsalida, e.pobllegada, c.nombreciclista "
		// 		+ "FROM Etapas e, Ciclistas c "
		// 		+ "WHERE e.ciclistaganador = c.codigociclista "
		// 		+ "AND e.pobllegada LIKE e.pobsalida "
		// 		+ "GROUP BY e.codigoetapa, e.km, e.pobsalida, e.pobllegada, c.nombreciclista";

		//2 CONSULTA EN HQL:
		// String hql = "SELECT c.codigociclista, c.nombreciclista, e.codigoetapa, e.tipoetapa, t.codigotramo, t.nombretramo, t.categoria "
		// 		+ "FROM Ciclistas c, Etapas e, Tramospuertos t "
		// 		+ "WHERE e.codigoetapa = t.numetapa "
		// 		+ "AND t.ciclistaganador = c.codigociclista "
		// 		+ "AND t.pendiente LIKE '%5,5%' "
		// 		+ "GROUP BY c.codigociclista, c.nombreciclista, e.codigoetapa, e.tipoetapa, t.codigotramo, t.nombretramo, t.categoria "
		// 		+ "ORDER BY c.codigociclista";

		//3 CONSULTA EN HQL:
		// String hql = "SELECT c.codigoequipo, eq.nombreequipo, c.nombreciclista, COUNT(ll.numetapa) as numetapas"
		// 		+ " FROM Ciclistas c, Etapas e, Equipos eq, Lleva ll, Camisetas cam"
		// 		+ " WHERE ll.codigocamiseta = cam.codigocamiseta"
		// 		+ " AND cam.color = 'Lunares'"
		// 		+ " AND c.codigoequipo = eq.codigoequipo"
		// 		+ " AND c.codigociclista = ll.codigociclista"
		// 		+ " AND ll.numetapa = e.codigoetapa"
		// 		+ " GROUP BY c.codigoequipo, eq.nombreequipo, c.nombreciclista"
		// 		+ " ORDER BY c.codigoequipo, c.nombreciclista";

		//4 CONSULTA EN HQL:
		// String hql = "SELECT e.codigoequipo, e.nombreequipo, l.codigocamiseta, cam.color, COUNT(l.codigocamiseta) as numveces"
		// 		+ " FROM Equipos e, Lleva l, Camisetas cam"
		// 		+ " WHERE l.codigocamiseta = cam.codigocamiseta"
		// 		+ " AND e.codigoequipo = l.codigoequipo"
		// 		+ " GROUP BY e.codigoequipo, e.nombreequipo, l.codigocamiseta, cam.color"
		// 		+ " ORDER BY e.codigoequipo, l.codigocamiseta";

		/* REVISAR LAS CONSULTAS EN HQL ESTO ESTÁ EN MYSQL (lo de mysql funciona OK solo es pasarlo a HQL)

		 * --Consulta 1
		 * SELECT
		 * E.CODIGOETAPA,
		 * E.KM,
		 * E.POBSALIDA,
		 * E.POBLLEGADA,
		 * C.NOMBRECICLISTA
		 * FROM
		 * CICLISTAS C,
		 * ETAPAS E,
		 * TRAMOSPUERTOS T
		 * WHERE
		 * E.CODIGOETAPA = T.NUMETAPA
		 * AND E.CICLISTAGANADOR = C.CODIGOCICLISTA
		 * AND E.POBLLEGADA LIKE E.POBSALIDA
		 * GROUP BY
		 * E.CODIGOETAPA,
		 * E.KM,
		 * E.POBSALIDA,
		 * E.POBLLEGADA,
		 * C.NOMBRECICLISTA;
		 * 
		 * --Consulta 2
		 * SELECT
		 * C.CODIGOCICLISTA,
		 * C.NOMBRECICLISTA E,
		 * E.CODIGOETAPA,
		 * E.TIPOETAPA,
		 * T.CODIGOTRAMO,
		 * T.NOMBRETRAMO,
		 * T.CATEGORIA
		 * FROM
		 * CICLISTAS C,
		 * ETAPAS E,
		 * TRAMOSPUERTOS T
		 * WHERE
		 * E.CODIGOETAPA = T.NUMETAPA
		 * AND T.CICLISTAGANADOR = C.CODIGOCICLISTA
		 * AND T.PENDIENTE LIKE '%5,5%'
		 * GROUP BY
		 * C.CODIGOCICLISTA,
		 * C.NOMBRECICLISTA,
		 * E.CODIGOETAPA,
		 * E.TIPOETAPA,
		 * T.CODIGOTRAMO,
		 * T.NOMBRETRAMO,
		 * T.CATEGORIA
		 * ORDER BY
		 * C.CODIGOCICLISTA;
		 * 
		 * --CONSULTA 3
		 * SELECT
		 * C.CODIGOEQUIPO,
		 * EQ.NOMBREEQUIPO,
		 * C.NOMBRECICLISTA,
		 * COUNT(LL.NUMETAPA) AS NUMETAPAS
		 * FROM
		 * CICLISTAS C,
		 * ETAPAS E,
		 * EQUIPOS EQ,
		 * LLEVA LL,
		 * CAMISETAS CAM
		 * WHERE
		 * LL.CODIGOCAMISETA = CAM.CODIGOCAMISETA
		 * AND CAM.COLOR = 'Lunares'
		 * AND C.CODIGOEQUIPO = EQ.CODIGOEQUIPO
		 * AND C.CODIGOCICLISTA = LL.CODIGOCICLISTA
		 * AND LL.NUMETAPA = E.CODIGOETAPA
		 * GROUP BY
		 * C.CODIGOEQUIPO,
		 * EQ.NOMBREEQUIPO,
		 * C.NOMBRECICLISTA
		 * ORDER BY
		 * C.CODIGOEQUIPO,
		 * C.NOMBRECICLISTA;
		 * 
		 * --CONSULTA 4
		 * SELECT
		 * E.CODIGOEQUIPO,
		 * E.NOMBREEQUIPO,
		 * L.CODIGOCAMISETA,
		 * CAM.COLOR,
		 * COUNT(L.CODIGOCAMISETA) AS VECES
		 * FROM
		 * LLEVA L,
		 * CICLISTAS C,
		 * EQUIPOS E,
		 * CAMISETAS CAM
		 * WHERE
		 * C.CODIGOEQUIPO = E.CODIGOEQUIPO
		 * AND C.CODIGOCICLISTA = L.CODIGOCICLISTA
		 * AND L.CODIGOCAMISETA = CAM.CODIGOCAMISETA
		 * GROUP BY
		 * E.CODIGOEQUIPO,
		 * E.NOMBREEQUIPO,
		 * L.CODIGOCAMISETA,
		 * CAM.COLOR
		 * ORDER BY
		 * E.CODIGOEQUIPO,
		 * L.CODIGOCAMISETA;
		 */

	}

	private static void ejercicio4BGV() {
		System.out.println("\nEjercicio 4   - LISTADO EJERCICIO 4");
		System.out.println("=======================================");

		List<ResumenCamisetas> resumenCamisetasList = session
				.createQuery("FROM ResumenCamisetas", ResumenCamisetas.class).list();

		// Agrupa los registros de ResumenCamisetas por código de equipo
		Map<BigInteger, List<ResumenCamisetas>> resumenPorEquipo = resumenCamisetasList.stream()
				.collect(Collectors.groupingBy(rc -> rc.getEquipos().getCodigoequipo()));

		// Itera sobre cada equipo y ciclista
		for (Map.Entry<BigInteger, List<ResumenCamisetas>> entry : resumenPorEquipo.entrySet()) {
			BigInteger codigoEquipo = entry.getKey();
			List<ResumenCamisetas> resumenes = entry.getValue();

			// Muestra el nombre del equipo
			String nombreEquipo = resumenes.get(0).getEquipos().getNombreequipo();
			System.out.println("Equipo: " + codigoEquipo + ", " + nombreEquipo);
			System.out.println("--------------------------------------------");

			// Itera sobre cada ciclista del equipo
			String ciclistaAnterior = ""; // ESTO ES SOLO PARA QUE MUESTRE EL LISTADO BIEN
			for (ResumenCamisetas resumen : resumenes) {
				// Obtiene los datos del ciclista y los muestra por pantalla
				Ciclistas ciclista = resumen.getCiclistas();
				Camisetas camiseta = resumen.getCamisetas();
				BigInteger numveces = resumen.getNumveces();
				BigInteger importepremio = resumen.getImportepremio();
				String ciclistaActual = ciclista.getNombreciclista();
				if (!ciclistaActual.equals(ciclistaAnterior)) {
					System.out.printf("%s %s%n", ciclista.getCodigociclista(),
							ciclista.getNombreciclista());
					ciclistaAnterior = ciclistaActual;
				}
				System.out.printf("\t%s, %d veces, %d premio%n", camiseta.getColor(), numveces.intValue(),
						importepremio.intValue());

			}
			System.out.println();
		}

	}

	private static void ejercicio3BGV() {
		System.out.println("\nEjercicio 3 - Insertar en tabla RESUMEN CAMISETAS");
		System.out.println("=======================================");

		Transaction tx = session.beginTransaction();

		String hql = "SELECT c.equipos.codigoequipo, c.codigociclista, l.camisetas.codigocamiseta, COUNT(l.camisetas.codigocamiseta), cam.importepremio FROM Lleva l, Ciclistas c, Camisetas cam WHERE l.ciclistas.codigociclista = c.codigociclista AND l.camisetas.codigocamiseta = cam.codigocamiseta AND cam.importepremio > 0 GROUP BY c.equipos.codigoequipo, c.codigociclista, l.camisetas.codigocamiseta, cam.importepremio ORDER BY c.equipos.codigoequipo, c.codigociclista, l.camisetas.codigocamiseta ";

		// Query<Object[]> query = session.createQuery(hql, Object[].class);
		// List<Object[]> resultados = query.list();

		List<Object[]> resultados = session.createQuery(hql, Object[].class).list();

		String equipoAnterior = ""; // ESTO ES SOLO PARA QUE MUESTRE EL LISTADO BIEN
		for (Object[] resultado : resultados) {
			// Crear una nueva instancia de la clase ResumenCamisetasId

			ResumenCamisetasId id = new ResumenCamisetasId();
			id.setCodigoequipo((BigInteger) resultado[0]);
			id.setCodigociclista((BigInteger) resultado[1]);
			id.setCodigocamiseta((BigInteger) resultado[2]);

			// Crear una nueva instancia de la clase ResumenCamisetas
			ResumenCamisetas resumenCamisetas = new ResumenCamisetas();
			resumenCamisetas.setId(id); // ID

			BigInteger codigoequipo = (BigInteger) resultado[0];
			BigInteger codigociclista = (BigInteger) resultado[1];
			BigInteger codigocamiseta = (BigInteger) resultado[2];

			Equipos equipos = session.get(Equipos.class, codigoequipo); // EQUIPOS
			Ciclistas ciclistas = session.get(Ciclistas.class, codigociclista); // CICLISTAS
			Camisetas camisetas = session.get(Camisetas.class, codigocamiseta); // CAMISETAS

			resumenCamisetas.setEquipos(equipos);
			resumenCamisetas.setCiclistas(ciclistas);
			resumenCamisetas.setCamisetas(camisetas);

			BigInteger numveces = BigInteger.valueOf((long) resultado[3]);
			resumenCamisetas.setNumveces(numveces); // NUMVECES

			resumenCamisetas.setImportepremio(((BigInteger) resultado[4]).multiply(numveces)); // IMPORTE

			// Guardar el objeto ResumenCamisetas en la base de datos
			try {

				session.save(resumenCamisetas);

				String equipoActual = resumenCamisetas.getEquipos().getNombreequipo();
				if (!equipoActual.equals(equipoAnterior)) {
					System.out.printf("Equipo : %s, %s%n", resumenCamisetas.getEquipos().getCodigoequipo(),
							resumenCamisetas.getEquipos().getNombreequipo());
					System.out.println("--------------------------------------------");

					equipoAnterior = equipoActual;
				}
				System.out.printf("\tInsertado : %s, %s %n", resumenCamisetas.getCiclistas().getCodigociclista(),
						resumenCamisetas.getCiclistas().getNombreciclista());
			} catch (javax.persistence.PersistenceException ex) {
				if (ex.getMessage().contains("ConstraintViolationException")) {
					System.out.println("ERROR: Los datos ya existen en la base de datos.");
				} else {
					System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
				}
			}

		}

		try {
			// Confirmar la transacción
			tx.commit();
		} catch (

		javax.persistence.PersistenceException ex) {
			if (ex.getMessage().contains("ConstraintViolationException")) {
				System.out.println("Error: ." + ex.getMessage());
			} else {
				System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
			}
		}
	}

	private static void ejercicio2BGV(int etapa, int ciclista, int camiseta) {
		System.out.println("Ejercicio 2 - Insertar en tabla LLEVA");
		System.out.println("=======================================");

		Transaction tx = session.beginTransaction();

		BigInteger codCamiseta = BigInteger.valueOf(camiseta);
		BigInteger codCiclista = BigInteger.valueOf(ciclista);
		BigInteger codEtapa = BigInteger.valueOf(etapa);

		System.out.println("DATOS A INSERTAR (etapa: " + etapa + " - " + "ciclista: " + ciclista + " - " + "camiseta: "
				+ camiseta + ")");

		boolean existeEtapa = existeEtapa(codEtapa);
		boolean existeCiclista = existeCiclista(codCiclista);
		boolean existeCamiseta = existeCamiseta(codCamiseta);
		boolean camisetaAsignada = estaAsignadaCamiseta(codEtapa, codCamiseta, codCiclista);
		boolean llevaCamiseta = llevaCamisetaEtapa(codCamiseta, codCiclista, codEtapa);
		if (existeCamiseta && existeEtapa && existeCiclista) {
			if (!camisetaAsignada && !llevaCamiseta) {

				InserciónTrasComprobarTodo(tx, codCamiseta, codCiclista, codEtapa);

			}

		} else {
			System.out.println("NO SE PUEDE INSERTAR EL REGISTRO");
			System.exit(0);
			return;
		}

	}

	private static void InserciónTrasComprobarTodo(Transaction tx, BigInteger codCamiseta, BigInteger codCiclista,
			BigInteger codEtapa) {
		// Carga el objeto Ciclistas con el código del ciclista
		Ciclistas ciclistas = session.get(Ciclistas.class, codCiclista);

		// Carga el objeto Etapas con el código de la etapa
		Etapas etapas = session.get(Etapas.class, codEtapa);

		// Carga el objeto Camisetas con el código de la camiseta
		Camisetas camisetas = session.get(Camisetas.class, codCamiseta);
		LlevaId id = new LlevaId();
		id.setNumetapa(codEtapa);
		id.setCodigocamiseta(codCamiseta);
		Lleva lleva = new Lleva();
		lleva.setId(id);
		lleva.setCamisetas(camisetas);
		lleva.setCiclistas(ciclistas);
		lleva.setEtapas(etapas);

		try {
			// Guardar el objeto Lleva en la base de datos
			session.save(lleva);

			System.out.println("REGISTRO INSERTADO.");
			System.out.println("\tCamiseta: " + lleva.getCamisetas().getCodigocamiseta() + ", "
					+ lleva.getCamisetas().getColor() + "\n\tEtapa: " + lleva.getEtapas().getCodigoetapa() + ", "
					+ lleva.getEtapas().getTipoetapa() + "\n\tCiclista: " + codCiclista + ", "
					+ lleva.getCiclistas().getNombreciclista());

			try {
				// Confirmar la transacción
				tx.commit();
			} catch (javax.persistence.PersistenceException ex) {
				if (ex.getMessage().contains("ConstraintViolationException")) {
					System.out.println("Error: ." + ex.getMessage());
				} else {
					System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
				}
			}
		} catch (javax.persistence.PersistenceException ex) {
			if (ex.getMessage().contains("ConstraintViolationException")) {
				System.out.println("ERROR: Los datos ya existen en la base de datos.");
			} else {
				System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
			}
		}
	}

	private static boolean llevaCamisetaEtapa(BigInteger codCamiseta, BigInteger codCiclista, BigInteger codEtapa) {
		boolean existe = false;
		List<Lleva> lLleva = session.createQuery(
				"from Lleva where ciclistas.codigociclista = :codigociclista and etapas.codigoetapa = :codigoetapa",
				Lleva.class).setParameter("codigoetapa", codEtapa).setParameter("codigociclista", codCiclista).list();

		if (!lLleva.isEmpty()) {
			existe = true;
			Object obj = lLleva.get(0);
			Lleva lleva = (Lleva) obj;
			// Esto que hago aquí es para obtener el cod del ciclista y el nombre
			// Cargo los objetos para identificar el ciclista
			LlevaId llevaid = new LlevaId(codEtapa, (lleva.getCamisetas().getCodigocamiseta()));
			Lleva ll = (Lleva) session.get(Lleva.class, llevaid);
			Ciclistas c = (Ciclistas) session.get(Ciclistas.class, (BigInteger) codCiclista);
			Camisetas ca = (Camisetas) session.get(Camisetas.class, (BigInteger) ll.getCamisetas().getCodigocamiseta());

			System.out.println("ERROR NO SE PUEDE INSERTAR.");
			System.out
					.println("\tEl ciclista " + codCiclista + ", " + c.getNombreciclista() + "\n\tya lleva la camiseta "
							+ ca.getCodigocamiseta() + ", " + ca.getColor() + ", en la etapa: " + codEtapa);
		}
		return existe;
	}

	private static boolean estaAsignadaCamiseta(BigInteger codEtapa, BigInteger codCamiseta, BigInteger codCiclista) {
		boolean existe = false;
		List<Lleva> lLleva = session.createQuery(
				"from Lleva where not ciclistas.codigociclista = :codigociclista and etapas.codigoetapa = :codigoetapa and camisetas.codigocamiseta = :codigocamiseta",
				Lleva.class).setParameter("codigocamiseta", codCamiseta).setParameter("codigoetapa", codEtapa)
				.setParameter("codigociclista", codCiclista).list();

		if (!lLleva.isEmpty()) {
			existe = true;
			// Esto que hago aquí es para obtener el cod del ciclista y el nombre
			// Cargo los objetos para identificar el ciclista
			LlevaId llevaid = new LlevaId(codEtapa, codCamiseta);
			Lleva ll = (Lleva) session.get(Lleva.class, llevaid);
			Ciclistas c = (Ciclistas) session.get(Ciclistas.class, (BigInteger) ll.getCiclistas().getCodigociclista());
			if (c != null) {
				System.out.println("REGISTRO EXISTENTE");
				System.out.println("NO se pude insertar.");
				System.out.println("\tLa camiseta " + codCamiseta + ", ya ha sido asignada en la etapa: " + codEtapa
						+ "\n\tAl ciclista " + c.getCodigociclista() + ", " + c.getNombreciclista());
			}
		}
		return existe;
	}

	private static boolean existeCamiseta(BigInteger codigocamiseta) {
		sesión = HibernateUtil.getSessionFactory();
		Session session = sesión.openSession();
		boolean existe = false;

		List<Camisetas> lCamisetas = session
				.createQuery("from Camisetas where codigocamiseta = :codigocamiseta", Camisetas.class)
				.setParameter("codigocamiseta", codigocamiseta).list();

		if (!lCamisetas.isEmpty()) {
			existe = true;
		} else {
			System.out.println("No existe la camiseta: " + codigocamiseta);
		}
		return existe;
	}

	private static boolean existeCiclista(BigInteger codCiclista) {
		sesión = HibernateUtil.getSessionFactory();
		Session session = sesión.openSession();
		boolean existe = false;

		List<Ciclistas> lCiclistas = session
				.createQuery("from Ciclistas where codigociclista = :codigociclista", Ciclistas.class)
				.setParameter("codigociclista", codCiclista).list();

		if (!lCiclistas.isEmpty()) {
			existe = true;
		} else {
			System.out.println("No existe el ciclista: " + codCiclista);
		}
		return existe;
	}

	private static boolean existeEtapa(BigInteger codEtapa) {
		sesión = HibernateUtil.getSessionFactory();
		Session session = sesión.openSession();
		boolean existe = false;

		List<Etapas> lEtapas = session.createQuery("from Etapas where codigoetapa = :codigoetapa", Etapas.class)
				.setParameter("codigoetapa", codEtapa).list();

		if (!lEtapas.isEmpty()) {
			existe = true;
		} else {
			System.out.println("No existe la etapa: " + codEtapa);
		}
		return existe;
	}

	private static void ejercicio1BGV() {

		String hql = "FROM NuevasCamisetas";
		Query<NuevasCamisetas> q = session.createQuery(hql, NuevasCamisetas.class);
		List<NuevasCamisetas> lNuevasCamisetas = q.list();

		// Crear una sola instancia de Camisetas al principio
		Camisetas camisetas = new Camisetas();
		System.out.println("Ejercicio 1 - Insertar Nuevas camisetas");
		System.out.println("=======================================");

		// Iniciar una transacción
		Transaction tx = session.beginTransaction();

		for (NuevasCamisetas nuevasCamisetas : lNuevasCamisetas) {
			Camisetas c = (Camisetas) session.get(Camisetas.class, (BigInteger) nuevasCamisetas.getCodigocamiseta());
			if (c == null) {
				// Asignar los valores de cada nueva camiseta a las propiedades del objeto
				// Camisetas
				camisetas = new Camisetas();
				camisetas.setCodigocamiseta(nuevasCamisetas.getCodigocamiseta());
				camisetas.setTipo(nuevasCamisetas.getTipo());
				camisetas.setColor(nuevasCamisetas.getColor());
				camisetas.setImportepremio(nuevasCamisetas.getImportepremio());
				System.out.println("LA CAMISETA no existe,  se inserta ");
				System.out.println(
						"CAMISETA: " + camisetas.getCodigocamiseta() + ", " + camisetas.getColor() + " AÑADIDA");

				// Guardar el objeto Camisetas en la base de datos
				try {
					// Guardar el objeto Camisetas en la base de datos
					session.save(camisetas);
				} catch (javax.persistence.PersistenceException ex) {
					if (ex.getMessage().contains("ConstraintViolationException")) {
						System.out.println("ERROR: Los datos ya existen en la base de datos.");
					} else {
						System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
					}
				}
			} else {
				System.out.println("Camiseta: " + c.getCodigocamiseta() + ", " + c.getColor() + " YA EXISTE");

			}

		}
		try {
			// Confirmar la transacción
			tx.commit();
		} catch (javax.persistence.PersistenceException ex) {
			if (ex.getMessage().contains("ConstraintViolationException")) {
				System.out.println(
						"Error: Violación de restricción. Alguna de las camisetas ya existe en la base de datos.");
			} else {
				System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
			}
		}
		System.out.println();
	}

}

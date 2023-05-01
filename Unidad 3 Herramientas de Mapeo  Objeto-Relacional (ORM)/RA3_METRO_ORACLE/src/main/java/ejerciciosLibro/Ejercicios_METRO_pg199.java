package ejerciciosLibro;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.HibernateUtil;
import datos.TAccesos;
import datos.TEstaciones;
import datos.TLineaEstacion;
import datos.TLineaEstacionId;
import datos.TLineas;
import datos.TViajes;

public class Ejercicios_METRO_pg199 {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		// ejercicio1_METRO();

//		  EJERCICIO 2

		 // ejercicio2_METRO();

// 		  EJERCICIO 3

//		List<TEstaciones> estaciones = session.createQuery("from TEstaciones").list();
//		for (TEstaciones estacion : estaciones) {
//			ejercicio3_METRO(estacion.getCodEstacion());
//		}

		// EJERCICIO 4

		 ejercicio4_METRO();
		
	}// MAIN

	private static void ejercicio1_METRO() {
		// este ejercicio solo requiere mapear las tablas METRO
	}

	private static void ejercicio2_METRO() {
		/*
		 * ENUNCIADO
		 * 
		 * 2. Crea un método que reciba un número de línea, un número de estación, el
		 * orden y los inserte en la tabla T_LINEA ESTACION. Antes de insertar hay que
		 * comprobar que la linea y la estación existan en las tablas correspondientes,
		 * que no exista el registro en la tabla T_LINEA ESTACION, y que el orden sea
		 * correcto. Visualiza los mensajes de error que correspondan (linea
		 * inexistente, estación inexistente, registro ya existe...)
		 * 
		 */
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		
		// VARIABLES
		int codLin = 5;
		int codEst = 16;
		int orde = 7;

		TLineaEstacionId id = new TLineaEstacionId(codLin, codEst);

		TLineas tlineas = (TLineas) session.get(TLineas.class, (int) codLin);

		TEstaciones testaciones = (TEstaciones) session.get(TEstaciones.class, (int) codEst);

		TLineaEstacion tlineastacion = (TLineaEstacion) session.get(TLineaEstacion.class, (TLineaEstacionId) id);

		// COMPROBACIONES
		if (tlineas == null) {
			System.out.println("cod_linea no existe en T_LINEAS, No se inserta");
			// cod_linea no existe en T_LINEAS
		} else {
			if (testaciones == null) {
				System.out.println("cod_estacion no existe en T_ESTACIONES, No se inserta");
				// cod_estacion no existe en T_ESTACIONES
			} else {
				if (tlineastacion != null) {
					System.out.println("Ya existen los datos en T_LINEA_ESTACION, No se inserta");
					// orden ya existe dentro de T_LINEA_ESTACION
				} else {
					// Visualiza los datos de la linea de ordenes
					String hql = "from TLineaEstacion as tl where tl.orden = :ordenlinest and tl.id.codLinea = :codlineaest";
					Query q = session.createQuery(hql);
					q.setParameter("ordenlinest", (int) orde);
					q.setParameter("codlineaest", (int) codLin);
					TLineaEstacion TLin = (TLineaEstacion) q.uniqueResult();

					TLineaEstacion t = new TLineaEstacion();

					if (TLin == null) {

						t.setId(id);
						t.setOrden(orde);

						session.save(t);

						try {
							tx.commit();
							System.out.println("TLINEA_ESTACION Insertado COD_ESTACION: " + t.getId().getCodEstacion());
							System.out.println("TLINEA_ESTACION Insertado COD_LINEA: " + t.getId().getCodLinea());
							System.out.println("TLINEA_ESTACION Insertado ORDEN: " + t.getOrden());

						} catch (javax.persistence.PersistenceException ex) {
							if (ex.getMessage().contains("ConstraintViolationException")) {
								System.out.println("TLINEA_ESTACION existente: " + t.getTLineas().getCodLinea());
							} else {
								System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
							}
						} // TRY
					} else {
						System.out.printf("YA EXISTE UNA ORDEN: %d %n\tEN LA LINEA: %d %n\tNO SE INSERTA%n", orde,
								codLin);
					}

					session.close();
					System.exit(0);

				} // 3º if
			} // 2º if
		} // 1º if
	}

	private static void ejercicio3_METRO(int codEstacion) {
		/*
		 * 3. Crea un método para actualizar los campos numaccesos, numlineas,
		 * numviajesdestino y numviajesprocedencia de las estaciones de la tabla
		 * T_ESTACIONES. Estas columnas deben contener el número de accesos que tiene la
		 * estación (numaccesos), el número de líneas que pasan por la estación
		 * (numlineas), el número de viajes que la tienen como destino
		 * (numviajesdestino), y el número de viajes que la tienen como procedencia
		 * (numviajesprocedencia).
		 */
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		// Ejecutar consulta a la tabla T_ACCESOS para obtener los accesos de la
		// estación
		List<TAccesos> accesos = session.createQuery("from TAccesos where codEstacion = :codEstacion", TAccesos.class)
				.setParameter("codEstacion", codEstacion).list();

		// ESTO ES LO MISMO////////////////////////////////
		Query q = session.createQuery("from TAccesos where codEstacion = :codEstacion");
		q.setParameter("codEstacion", codEstacion);
		List<TAccesos> accesos2 = q.list();

		// ESTO ES LO MISMO////////////////////////////////

		// Ejecutar consulta a la tabla T_LINEA_ESTACION para obtener las líneas de la
		// estación
		List<TLineaEstacion> lineas = session
				.createQuery("from TLineaEstacion where id.codEstacion = :codEstacion", TLineaEstacion.class)
				.setParameter("codEstacion", codEstacion).list();

		// ESTA PARTE CREO QUE ESTÁ MAL MAPEADA Y NO VA
		// Ejecutar consulta a la tabla T_VIAJES para obtener los viajes que tienen a la
		// estación como destino
		List<TViajes> viajesDestino = session
				.createQuery("FROM TViajes tv WHERE tv.TEstacionesByEstaciondestino.codEstacion = :codEstacion",
						TViajes.class)
				.setParameter("codEstacion", codEstacion).list();

		// Ejecutar consulta a la tabla T_VIAJES para obtener los viajes que tienen a la
		// estación como procedencia
		List<TViajes> viajesProcedencia = session
				.createQuery("from TViajes tv where tv.TEstacionesByEstacionorigen.codEstacion = :codEstacion",
						TViajes.class)
				.setParameter("codEstacion", codEstacion).list();

		// Recuperar objeto de la clase T_ESTACIONES que se desea actualizar
		// utilizando el código de la estación como criterio de búsqueda
		TEstaciones estacion = session.get(TEstaciones.class, codEstacion);

		// Actualizar campos de la estación
		estacion.setNumaccesos(accesos.size());
		estacion.setNumlineas(lineas.size());
		estacion.setNumviajesdestino(viajesDestino.size());
		estacion.setNumviajesprocedencia(viajesProcedencia.size());

		// Guardar objeto actualizado en la base de datos
		session.update(estacion);

		try {
			tx.commit();
			System.out.println("INSERTADO");
		} catch (javax.persistence.PersistenceException ex) {
			if (ex.getMessage().contains("ConstraintViolationException")) {
				System.out.println("TESTACION existente: " + estacion.getCodEstacion());
			} else {
				System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
			}
		} finally {
			session.close();// TRY
		}
	}

	private static void ejercicio4_METRO() {
		/*
		 * 4. Crea un método que visualice por cada estación, el número de líneas que
		 * pasan por ella, el número de accesos que tiene, el número de viajes que
		 * tienen como destino la estación y los viajes con su nombre y su código. Y lo
		 * mismo con los viajes de procedencia. Obtén la siguiente salida por estación:
		 * 
		 * COD ESTACIÓN: XXXXXX NOMBRE ESTACIÓN: XXXX
		 * ----------------------------------------------------------------------------
		 * Números de líneas que pasan: XXXXXX Número de accesos que tiene: XXXXXX
		 * NUM-Viajes-DESTINO: XXXXXXXX COD-VIAJE NOMBRE-VIAJE-DESTINO ----------
		 * --------------------- XXX XXXXXXXXXXXXXXXXXXX
		 * 
		 * NUM-Viajes-PROCEDENCIA: XXXXXXXX NOMBRE-VIAJE-PROCEDENCIA COD-VIAJE
		 * NOMBRE-VIAJE-PROCEDENCIA ---------- -------------------------- XXX
		 * XXXXXXXXXXXXXXXXXXX
		 * ----------------------------------------------------------------------------
		 */
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery("from TEstaciones");
		List<TEstaciones> listaEstaciones = q.list();
		for (TEstaciones tEstaciones : listaEstaciones) {
			int cod = tEstaciones.getCodEstacion();
			System.out.println("COD ESTACION: " + cod + " NOMBRE ESTACION: " + tEstaciones.getNombre());
			System.out.println("----------------------------------------------------------------------------");

			List<TLineaEstacion> numlineas = session
					.createQuery("from TLineaEstacion where id.codEstacion = :codEsta", TLineaEstacion.class)
					.setParameter("codEsta", cod).list();

			List<TAccesos> numaccesos = session
					.createQuery("from TAccesos where codEstacion = :codEsta", TAccesos.class)
					.setParameter("codEsta", cod).list();

			List<TViajes> viajesDestino = session
					.createQuery("FROM TViajes tv WHERE tv.TEstacionesByEstaciondestino.codEstacion = :codEsta",
							TViajes.class)
					.setParameter("codEsta", cod).list();

			List<TViajes> viajesOrigen = session
					.createQuery("from TViajes tv where tv.TEstacionesByEstacionorigen.codEstacion = :codEsta",
							TViajes.class)
					.setParameter("codEsta", cod).list();

			System.out.println("NÚMERO DE LÍNEAS QUE PASAN: " + numlineas.size());
			System.out.println("NÚMERO DE ACCESOS QUE PASAN: " + numaccesos.size());
			System.out.println("NUM-VIAJES-DESTINO: " + viajesDestino.size());
			for (TViajes viajesde : viajesDestino) {
				System.out.println("COD-VIAJE NOMBRE-VIAJE-DESTINO");
				System.out.println("--------- ----------------------------");
				System.out.printf("%d\t\t%s%n", viajesde.getTEstacionesByEstaciondestino().getCodEstacion(),
						viajesde.getTEstacionesByEstaciondestino().getNombre());

				for (TViajes viajesor : viajesOrigen) {
					System.out.println("COD-VIAJE NOMBRE-VIAJE-ORIGEN");
					System.out.println("--------- ----------------------------");
					System.out.printf("%d\t\t%s%n", viajesor.getTEstacionesByEstacionorigen().getCodEstacion(),
							viajesde.getTEstacionesByEstacionorigen().getNombre());

				}
			}
			System.out.println("----------------------------------------------------------------------------\n");
		} // FOR-EACH
	}// fin ejercicio 4

}// CLASS

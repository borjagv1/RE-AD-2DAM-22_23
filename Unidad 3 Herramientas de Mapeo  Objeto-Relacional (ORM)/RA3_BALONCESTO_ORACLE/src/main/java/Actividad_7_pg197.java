import java.math.BigDecimal;
import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;

import datos.Estadisticas;
import datos.EstadisticasId;
import datos.HibernateUtil;
import datos.Jugadores;

public class Actividad_7_pg197 {
	/*
	 * Realiza un programa Java que inserte estad�sticas para el jugador 123. Los
	 * datos a insertar son los siguientes: temporada 05/06: puntos por partido 7,
	 * rebotes 5; temporada 06/07 puntos por partido 10, tapones 3. Los valores no
	 * indicados tendr�n valor 0. Transforma despu�s el programa para que todos los
	 * valores a insertar se introduzcan a partir de los argumentos de main().
	 * Controlar posibles errores, n�mero de argumentos correctos, que el jugador
	 * exista, que la estad�stica no exista, etc.
	 */

	private static SessionFactory sesion;
	private static Session session;

	public static void main(String[] args) {
		sesion = HibernateUtil.getSessionFactory();
		session = sesion.openSession();

		// insertar: temporada 05/06: puntos por partido 7,
		// rebotes 5; temporada 06/07 puntos por partido 10, tapones 3. Los valores no
		// indicados tendr�n valor 0.
		
		String temporada = "05/06";
		int jugador = 123;
		
		BigInteger b = BigInteger.valueOf(jugador);

		EstadisticasId eid = new EstadisticasId(temporada, b);
		Jugadores jug = new Jugadores(b);

//		float puntosporpartido = 7;
//		float rebotes = 5;
//		float tapones = 0;
//		float asistencias = 0;
		BigDecimal puntosporpartido = new BigDecimal(7); 
		BigDecimal rebotes = new BigDecimal(5); 
		BigDecimal tapones = new BigDecimal(0); 
		BigDecimal asistencias = new BigDecimal(0); 
		
		InsertaEstadistica(eid, jug, puntosporpartido, rebotes, asistencias, tapones);
		//

		eid = new EstadisticasId("06/07", b);
		puntosporpartido = new BigDecimal(10);
		tapones = new BigDecimal(3);
		rebotes = new BigDecimal(0);
		asistencias = new BigDecimal(0);
		InsertaEstadistica(eid, jug, puntosporpartido, rebotes, asistencias, tapones);

		//

		session.close();
		System.exit(0);
	}// MAin

	private static void InsertaEstadistica(EstadisticasId eid, Jugadores jugador, BigDecimal puntosporpartido, BigDecimal rebotes,
			BigDecimal asistencias, BigDecimal tapones) {
		session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		System.out.println("Estadística Temporada: " + eid.getTemporada());
		// int er = 0; // Controlamos si hay algun error y vamos sumando. Si es distinto
		// de 0,
		// Es que algo va mal.
		Estadisticas esta = new Estadisticas(eid, jugador, puntosporpartido, asistencias, tapones, rebotes);
		try {
			session.save(esta); // Hce que la instancia sea persistente.
			try {
				tx.commit();
				System.out.println(esta.getId().getTemporada() + " => ESTADÍSTICA INSERTADA: ");

			} catch (javax.persistence.PersistenceException e) {
				if (e.getMessage().contains("ConstraintViolationException")) {
					System.out.println(esta.getId().getTemporada() + " => ESTADÍSTICA DUPLICADA ");
				} else {
					System.out.printf("MENSAJE: %s%n", e.getMessage());
				}
			}
		} catch (TransientPropertyValueException e) {
			System.out.println("EL JUGADOR NO EXISTE");
			System.out.printf("MENSAJE: %s%n", e.getMessage());
			System.out.printf("Propiedad: %s%n", e.getPropertyName());
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO....");
			e.printStackTrace();
		}

		/*
		 * } else { System.out.printf("MENSAJE: %s%n",e.getMessage()); } } } catch
		 * (TransientPropertyValueException e) {
		 * System.out.println("LA ESTADISTICA NO EXISTE");
		 * System.out.printf("MENSAJE: %s%n", e.getMessage());
		 * System.out.printf("Propiedad: %s%n", e.getPropertyName()); } catch (Exception
		 * e) { System.out.println("ERROR NO CONTROLADO...."); e.printStackTrace(); }
		 */

		session.close();
		// System.exit(0);

	}
}

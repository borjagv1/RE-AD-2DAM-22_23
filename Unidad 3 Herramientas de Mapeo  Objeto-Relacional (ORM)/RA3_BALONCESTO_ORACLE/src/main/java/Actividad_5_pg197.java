import java.math.BigInteger;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Estadisticas;
import datos.HibernateUtil;
import datos.Jugadores;

public class Actividad_5_pg197 {

	private static SessionFactory sesion;

	public static void main(String[] args) {	
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").
		setLevel(java.util.logging.Level.SEVERE);
		
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		
		int cod = 227;
		BigInteger codigoJugador = BigInteger.valueOf(cod);
		
		Jugadores jug = (Jugadores) session.get(Jugadores.class, (BigInteger) codigoJugador);	
		
		if (jug==null) {			
			System.out.println("El jugador no existe");
		}else {
			System.out.printf("DATOS DEL JUGADOR: %d%nNombre : %s%nEquipo : %s%n", jug.getCodigo(), jug.getNombre(), jug.getEquipos().getNombre());
			System.out.println("TEMPORADA	PTOS	ASIS	TAP	REB");
			System.out.println("========================================================================");
			//Obtenemos las estadísticas de un jugador.
			Set<Estadisticas> listaEstadisticas = jug.getEstadisticases();
			
			
			//listaEstadisticas.stream().sorted();
			
			int cont = 0;
			for (Estadisticas est : listaEstadisticas) {
				
				System.out.printf("%s\t\t%.2f\t%.2f\t%.2f\t%.2f\t%n", est.getId().getTemporada(),
						est.getPuntosPorPartido(), est.getAsistenciasPorPartido(), est.getTaponesPorPartido(),
						est.getRebotesPorPartido());
				
				cont++;
			}
			System.out.println("========================================================================");
			System.out.println("Num Registros: " + cont);

			System.out.println("========================================================================");
			
		}
		
		session.close();
		System.exit(0);
		
	}//MAIN

}

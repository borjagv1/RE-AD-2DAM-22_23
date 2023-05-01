import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import datos.Equipos;
import datos.HibernateUtil;
import datos.Jugadores;

public class Actividad_6_pg197 {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		String hql = "from Equipos";
		Query q = session.createQuery(hql);

		List<Equipos> listaEquipos = q.list();
		
		// Obtenemos un flujo de objetos Equipos y lo ordenamos según el nombre de los equipos en orden ascendente
		List<Equipos> listaEquiposOrdenados = listaEquipos.stream()
                .sorted((v1, v2) -> (v1.getNombre().compareTo(v2.getNombre())))
                .collect(Collectors.toList());
		
		
		

		// Comprobamos si la lista está vacía utilizando el método isEmpty
		// en lugar de comparar su tamaño con 0
		if (listaEquiposOrdenados.isEmpty()) {
			System.out.println("La lista está vacía");
		} else {
			// Obtenemos el tamaño de la lista utilizando el método size
			// en lugar de acceder a la propiedad size de ArrayList
			System.out.printf("La lista tiene %d equipos: %n", listaEquiposOrdenados.size());
			System.out.println("=======================================================");
			for (Equipos equipos : listaEquiposOrdenados) {
				System.out.printf("Equipo: %s%n", equipos.getNombre());

				Set<Jugadores> listaJugadores = equipos.getJugadoreses();
				
				// Ordenamos la lista de jugadores según su código en orden ascendente
			    List<Jugadores> listaJugadoresOrdenados = listaJugadores.stream()
			                    .sorted((j1, j2) -> (j1.getCodigo().compareTo(j2.getCodigo())))
			                    .collect(Collectors.toList());

				for (Jugadores jugadores : listaJugadoresOrdenados) {
					// select avg(e.puntosPorPartido) from Estadisticas e where e.id.jugador=120
					hql = "select avg(e.puntosPorPartido) from Estadisticas e where e.id.jugador = "
							+ jugadores.getCodigo();
					q = session.createQuery(hql);
					
					Double media = (Double) q.uniqueResult();

					System.out.printf("%d, %s: %.2f%n", jugadores.getCodigo(), jugadores.getNombre(), media);
				}
				System.out.println("========================================================");
			}
		}
		session.close();
		System.exit(0);
	}// main

}// CLASS

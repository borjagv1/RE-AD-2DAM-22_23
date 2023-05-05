import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import datos.Departamentos;
import datos.HibernateUtil;

public class ListaDepartamentos {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		//Query q = session.createQuery("from Departamentos");
		//List<Departamentos> lista = q.list();

		List<Departamentos> lista = session.createQuery("from Departamentos", Departamentos.class).list();
		
		System.out.printf("Número de registros: %d%n", lista.size());
		
		//solo usando for-each
		
		for (Departamentos departamentos : lista) {
			System.out.printf("%d, %s%n", departamentos.getDeptNo(), departamentos.getDnombre());
		}

		// Utilizamos una expresión lambda y el método forEach para recorrer la lista
		//lista.forEach(depar -> System.out.printf("%d, %s%n", depar.getDeptNo(), depar.getDnombre()));


		session.close();
		System.exit(0);
	}
}

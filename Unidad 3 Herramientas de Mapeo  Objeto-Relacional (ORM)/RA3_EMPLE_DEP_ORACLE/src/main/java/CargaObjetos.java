import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import datos.Departamentos;
import datos.HibernateUtil;

public class CargaObjetos {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		// Visualizo los datos del departamento 20
		Departamentos dep = new Departamentos();
		try {
			//dep = (Departamentos) session.load(Departamentos.class, (byte) 28);
			dep = (Departamentos) session.load("datos.Departamentos", (byte) 28);
			
			System.out.printf("Nombre Dep: %s%n", dep.getDnombre());
			System.out.printf("Localidad: %s%n", dep.getLoc());
		} catch (ObjectNotFoundException o) {
			System.out.println("NO EXISTE EL DEPARTAMENTO!!");
		}

		session.close();
		System.exit(0);

	}

}

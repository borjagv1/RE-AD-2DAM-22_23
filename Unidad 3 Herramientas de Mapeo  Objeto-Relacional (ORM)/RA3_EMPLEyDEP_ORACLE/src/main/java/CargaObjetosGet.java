
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import datos.Departamentos;

public class CargaObjetosGet {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		Departamentos dep = (Departamentos) session.get(Departamentos.class, (byte) 10);
		if (dep == null) {
			System.out.println("El departamento no existe");
		} else {
			System.out.printf("Nombre Dep: %s%n", dep.getDnombre());
			System.out.printf("Localidad: %s%n", dep.getLoc());
		}

		session.close();
		System.exit(0);

	}

}

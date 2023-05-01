import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Empleados;



public class ListaEmpleados20 {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		Query q = session.createQuery("from Empleados as e where e.departamentos.deptNo = 20");
		List<Empleados> lista = q.list();
		

		for (Empleados emp : lista) {
			System.out.printf("%s, %.2f %n", emp.getApellido(), emp.getSalario());
		}
		session.close();
		System.exit(0);
	}

}

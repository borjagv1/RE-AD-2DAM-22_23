import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Empleados;
import datos.HibernateUtil;



public class ListaEmpleados20 {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		//Query q = session.createQuery("from Empleados as e where e.departamentos.deptNo = 20");
		//List<Empleados> lista = q.list();
		String hql = "from Empleados as e where e.departamentos.deptNo = 20";
		List <Empleados> lista = session.createQuery(hql, Empleados.class).list();
		

		for (Empleados emp : lista) {
			System.out.printf("%s, %.2f %s%n", emp.getApellido(), emp.getSalario(), emp.getDepartamentos().getDeptNo());
		}
		session.close();
		System.exit(0);
	}

}

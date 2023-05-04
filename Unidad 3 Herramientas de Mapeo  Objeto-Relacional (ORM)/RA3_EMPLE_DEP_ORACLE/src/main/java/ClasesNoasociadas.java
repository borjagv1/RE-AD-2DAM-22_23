import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Departamentos;
import datos.Empleados;
import datos.HibernateUtil;

public class ClasesNoasociadas {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		{
			String hql = "from Empleados e, Departamentos d "
					+ "where e.departamentos.deptNo = d.deptNo order by apellido";
////////////////////////////////////////CON FOR - EACH///////////////////////////////////////////////////////////////////////

			Query cons = session.createQuery(hql);
			List<Object[]> lista = cons.list();
			for (Object[] par : lista) {
				Empleados em = (Empleados) par[0];
				Departamentos de = (Departamentos) par[1];
				System.out.printf("%s, %.2f, %s, %s %n", em.getApellido(), em.getSalario(), de.getDnombre(),
						de.getLoc());
			}
			System.out.println("===============================================");
////////////////////////////////////////CON FOR - EACH///////////////////////////////////////////////////////////////////////

			System.out.println("===============================================");
			cons = session.createQuery(hql);
			lista = cons.list();

			Iterator q = lista.iterator();
			while (q.hasNext()) {
				Object[] par = (Object[]) q.next();
				Empleados em = (Empleados) par[0];
				Departamentos de = (Departamentos) par[1];
				System.out.printf("%s, %.2f, %s, %s %n", em.getApellido(), em.getSalario(), de.getDnombre(),
						de.getLoc());
			}
			System.out.println("===============================================");
		}

		{
			// MOSTRAR SALARIO MEDIO DE LOS EMPLEADOS
			String hql = "select avg(em.salario) from Empleados as em";
			Query cons = session.createQuery(hql);
			Double suma = (Double) cons.uniqueResult();
			System.out.printf("Salario medio: %.2f%n", suma);
		}
		// mostrar el salario medio y el numero de empleados

		System.out.println("===============================================");
		String hql = "select avg(salario), count(empNo) from Empleados ";
		Query cons = session.createQuery(hql);
		Object[] resultado = (Object[]) cons.uniqueResult();

		System.out.printf("Salario medio: %.2f%n", resultado[0]);
		System.out.printf("N�mero de empleados: %d%n", resultado[1]);

		System.out.println("===============================================");
		// mostrar el salario medio y el numero de empleados
		// por departamento
		hql = "select e.departamentos.deptNo, avg(salario), " + " count(empNo) from Empleados e "
				+ " group by e.departamentos.deptNo ";

		//////////////////////////////////////// CON FOR -
		//////////////////////////////////////// EACH///////////////////////////////////////////////////////////////////////

		cons = session.createQuery(hql);
		List<Object[]> list = cons.list();

		for (Object[] par : list) {
			Byte depar = (Byte) par[0];
			Double media = (Double) par[1];
			Long cuenta = (Long) par[2];
			System.out.printf("Dep: %d, Media: %.2f, N� emp: %d %n", depar, media, cuenta);
		}
		System.out.println("===============================================");
////////////////////////////////////////CON FOR - EACH///////////////////////////////////////////////////////////////////////
		System.out.println("===============================================");

		cons = session.createQuery(hql);
		List lista = cons.list();

		Iterator iter = lista.iterator();

		while (iter.hasNext()) {
			Object[] par = (Object[]) iter.next();
			Byte depar = (Byte) par[0];
			Double media = (Double) par[1];
			Long cuenta = (Long) par[2];
			System.out.printf("Dep: %d, Media: %.2f, N� emp: %d %n", depar, media, cuenta);
		}
		System.out.println("===============================================");

		session.close();
		System.exit(0);

	}

}

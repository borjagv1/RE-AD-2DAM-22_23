import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datos.Departamentos;

import datos.HibernateUtil;

public class EjemploUniqueResult {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		// Visualiza los datos del departamento 10
		String hql = "from Departamentos as dep where dep.deptNo = 10";
		//Query q = session.createQuery(hql);
		//Departamentos dep = (Departamentos) q.uniqueResult();
		Departamentos dep = session.createQuery(hql, Departamentos.class).uniqueResult();
		System.out.printf("%d, %s, %s%n", dep.getDeptNo(), dep.getLoc(), dep.getDnombre());

		// Visualiza los datos del departamento de nombre VENTAS
		hql = "from Departamentos as dep where dep.dnombre = 'sldknfgn' "; //'VENTAS'
		//q = session.createQuery(hql);
		//dep = (Departamentos) q.uniqueResult();
		dep = session.createQuery(hql, Departamentos.class).uniqueResult();
		if (dep == null)
			System.out.println("No existe el departamento");
		else
		System.out.printf("%d, %s, %s%n", dep.getDeptNo(), dep.getLoc(), dep.getDnombre());
	

		session.close();
		System.exit(0);

	}

}

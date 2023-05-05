package ejerciciosLibro;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import datos.Departamentos;
import datos.Empleados;
import datos.HibernateUtil;

public class Ejercicio_3_7_1raParte_copy {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		try {
			// Consulta HQL para obtener los datos del departamento 20 y los apellidos de
			// sus empleados
			String hql = "from Departamentos where deptNo = :numeroDepartamento";
			Departamentos dep = session.createQuery(hql, Departamentos.class)
					.setParameter("numeroDepartamento", (byte) 20).uniqueResult();

			// Mostramos los datos por pantalla

			System.out.println("Departamento: " + dep.getDeptNo() + " " + dep.getDnombre() + " " + dep.getLoc());

			//Listado de apellidos de los empleados del departamento 20.
			Set<Empleados> empleados = dep.getEmpleadoses();
			System.out.println("Empleados: ");
			for (Empleados emp : empleados) {
				System.out.println(emp.getApellido());
			}

		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO" + e.getMessage());
		} finally {
			session.close();
			System.exit(0);
		}
	}
}

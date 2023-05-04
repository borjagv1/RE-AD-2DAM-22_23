package ejerciciosLibro;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaUpdate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import datos.Departamentos;
import datos.Empleados;
import datos.HibernateUtil;

public class Ejercicio_3_7_1raParte {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		String hql = "from Departamentos d where d.deptNo = 20";
		Query q = session.createQuery(hql);
		List<Departamentos> lista = q.list();
		// Comprobamos si la lista está vacía utilizando el método isEmpty
		// en lugar de comparar su tamaño con 0
		if (lista.isEmpty()) {
			System.out.println("La lista está vacía");
		} else {
			// Obtenemos el tamaño de la lista utilizando el método size
			// en lugar de acceder a la propiedad size de ArrayList
			System.out.printf("La lista tiene %d elementos%n", lista.size());
			lista.forEach(departamentos -> { //Expresión lammbda pero es un foreach normal
				System.out.printf("NºDepartamento: %d%nNombre: %s%nLocalidad: %s%n", departamentos.getDeptNo(),
						departamentos.getDnombre(), departamentos.getLoc());

				// Obtenemos la lista de empleados que pertenecen al departamento 20
				Set<Empleados> empleados = departamentos.getEmpleadoses();

				// Imprimimos el apellido de cada empleado que pertenece al departamento 20
				empleados.forEach(emp -> { //Expresión lammbda pero es un foreach normal
					System.out.printf(" - %s%n", emp.getApellido());
				});

			});

			session.close();
			System.exit(0);
		}

	}
}

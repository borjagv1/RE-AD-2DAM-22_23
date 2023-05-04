package ejerciciosLibro;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import datos.Departamentos;
import datos.Empleados;
import datos.HibernateUtil;

public class Ejercicio_3_6 {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		int subida = 200;
		BigDecimal sub = BigDecimal.valueOf(subida);
		Departamentos dep = (Departamentos) session.get(Departamentos.class, (byte) 10);

		if (dep == null) {
			System.out.println("El DEPARTEMENTO no existe");
		} else {

			System.out.println("Nombre Dep: " + dep.getDnombre());
			System.out.println("Número de departamento: " + dep.getDeptNo());

			// Obtengo empleados del Dep 10 para modificar su salario
			Set<Empleados> listaemple = dep.getEmpleadoses();// obtenemos empleados

			for (Empleados emp : listaemple) {
				System.out.println("Sumamos " + subida + " al empleado " + emp.getEmpNo() + " con salario actual: "
						+ emp.getSalario());
				emp.setSalario(sub.add(emp.getSalario()));

				session.update(emp); // modifica el objeto
				//

				System.out.println("Subido " + subida + "al empleado " + emp.getEmpNo() + " con salario NUEVO: "
						+ emp.getSalario());

			} // FOREACH
			try {
				tx.commit();
			} catch (javax.persistence.PersistenceException ex) {
				if (ex.getMessage().contains("ConstraintViolationException")) {
					System.out.println("");
				} else {
					System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
				}
			} // TRY
		} // IFELSE

	}// MAIN

}// CLASS

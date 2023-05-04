package ejerciciosLibro;

import java.math.BigDecimal;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import datos.Departamentos;
import datos.Empleados;
import datos.HibernateUtil;

public class Ejercicio_3_6_copy {

	private static SessionFactory sesion;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

		sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// Cargo todos los empleados del departamento con deptNo = 10;
			Departamentos dep = (Departamentos) session.get(Departamentos.class, (byte) 10);

			// Si el departamento no existe lo mostramos por pantalla
			if (dep == null) {
				System.out.println("El departamento no existe");
			} else {
				System.out.println("Empleados del departamento " + dep.getDnombre());
				// Obtengo un listado de todos los empleados del departamento
				Set<Empleados> empleados = dep.getEmpleadoses();
				// Si no hay empleados mostramos que no tiene empleados este departamento
				if (empleados.isEmpty()) {
					System.out.println("El departamento no tiene empleados");
				} else {
					// Subimos el salario a todos los empleados del departamento 10
					for (Empleados empleado : empleados) {
						System.out.printf("Empleado: %s%n\tSalario ANTES: %s%n", empleado.getApellido(),
								empleado.getSalario());
						// Sumamos BIgDecimal más double
						empleado.setSalario(empleado.getSalario().add(new BigDecimal(200)));

						// Actualizamos el empleado
						session.update(empleado);

					}
					// Actualizamos todos los empleados en una sola transacción
					tx.commit();

					// Mostramos el apellido del empleado y su nuevo salario
					for (Empleados empleado : empleados) {
						System.out.printf("Empleado: %s%n\tNUEVO Salario: %s%n", empleado.getApellido(),
								empleado.getSalario());
					}
				}

			}
		} catch (ConstraintViolationException cve) {
			// Manejar la excepción por violación de restricciones
			System.err.println("Error al actualizar los empleados: " + cve.getMessage());
			tx.rollback();
		} catch (HibernateException he) {
			// Manejar cualquier otra excepción de Hibernate
			System.err.println("Error al actualizar los empleados: " + he.getMessage());
			tx.rollback();
		} catch (Exception e) {
			// Manejar cualquier otra excepción
			System.err.println("Error al actualizar los empleados: " + e.getMessage());
			tx.rollback();
		} finally {
			// Cerrar la sesión
			session.close();
		}

	}
}
import java.math.BigDecimal;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import datos.Departamentos;
import datos.Empleados;

public class ModificarEmpleado {

	public static void main(String[] args) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		Empleados em = new Empleados();
		try {
			em = (Empleados) session.load(Empleados.class, (short) 7369);
			System.out.printf("Modificación empleado: %d%n", em.getEmpNo());
			System.out.printf("Salario antiguo: %.2f%n", em.getSalario());
			System.out.printf("Departamento antiguo: %s%n", em.getDepartamentos().getDnombre());

			float NuevoSalario = em.getSalario().floatValue() + 1000;
			BigDecimal bd = new BigDecimal(NuevoSalario);
			em.setSalario(bd);

			Departamentos dep = (Departamentos) session.get(Departamentos.class, (byte) 30);
			if (dep == null) {
				System.out.println("El departamento NO existe");
			} else {
				em.setDepartamentos(dep);

				session.update(em); // modifica el objeto
				tx.commit();
				
				System.out.printf("Salario nuevo: %.2f%n", em.getSalario());
				System.out.printf("Departamento nuevo: %d%n", em.getDepartamentos().getDnombre());
			}

		} catch (ObjectNotFoundException o) {
			System.out.println("NO EXISTE EL EMPLEADO...");
		} catch (ConstraintViolationException c) {
			System.out.println("NO SE PUEDE ASIGNAR UN DEPARTAMENTO QUE NO EXISTE.....");
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO....");
			e.printStackTrace();
		}
		session.close();
		System.exit(0);
	}

}

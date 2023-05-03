import java.util.Set;

import datos.Departamentos;
import datos.Empleados;

import org.hibernate.Session;

public class ListadoDep {
	public static void main(String[] args) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			System.out.println("==============================");
			System.out.println("DATOS DEL DEPARTAMENTO 10.");

			Departamentos dep = session.get(Departamentos.class, (byte) 10);
			System.out.println("Nombre Dep: " + dep.getDnombre());
			System.out.println("Localidad: " + dep.getLoc());

			System.out.println("==============================");
			System.out.println("EMPLEADOS DEL DEPARTAMENTO 10.");

			Set<Empleados> ListaEmpleados = dep.getEmpleadoses();

			System.out.printf("Número de empleados: %d %n", ListaEmpleados.size());
			ListaEmpleados.forEach(
					empleado -> System.out.printf("%s * %.2f %n", empleado.getApellido(), empleado.getSalario()));
			// Lo mismo
			for (Empleados emple : ListaEmpleados) {
				System.out.printf("%s * %.2f %n", emple.getApellido(), emple.getSalario());
			}
			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
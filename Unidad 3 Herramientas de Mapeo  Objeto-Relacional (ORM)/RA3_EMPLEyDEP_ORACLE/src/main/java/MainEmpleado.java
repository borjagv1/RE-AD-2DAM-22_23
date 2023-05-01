import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import datos.Departamentos;
import datos.Empleados;

public class MainEmpleado {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").
		setLevel(java.util.logging.Level.SEVERE);
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();

		System.out.println("Inserto un EMPLEADO EN EL DEPARTAMENTO 10.");
		
		BigDecimal salario = new BigDecimal(1500.0);

		BigDecimal comision = new BigDecimal(10.0);
		
		
//		Float salario = 1500f;// inicializo el salario
//		Float comision = new 10f; // inicializo la comisi�n

		Empleados em = new Empleados(); // creo un objeto empleados
		em.setEmpNo((short) 4455); // el numero de empleado es 4455
		em.setApellido("PEPE");
		em.setDir((short) 7499); // el director es el numero de empleado 7499
		em.setOficio("VENDEDOR");
		em.setSalario(salario);
		em.setComision(comision);

		Departamentos d = new Departamentos(); // creo un objeto Departamentos
		d.setDeptNo((byte) 19); // el n�mero de dep es 10
		em.setDepartamentos(d);

		// fecha de alta
		java.util.Date hoy = new java.util.Date();
		java.sql.Date fecha = new java.sql.Date(hoy.getTime());
		em.setFechaAlt(fecha);

		try {
			session.save(em);
			try {
				tx.commit();
			}catch (javax.persistence.PersistenceException e) {
				if(e.getMessage().contains("ConstraintViolationException")) {
					System.out.println("EMPLEADO DUPLICADO");
				}else {
					System.out.printf("MENSAJE %s%n", e.getMessage());
				}
			}
//			} catch (ConstraintViolationException e) {
//				System.out.println("EMPLEADO DUPLICADO");
//				System.out.printf("MENSAJE: %s%n", e.getMessage());
//				System.out.printf("COD ERROR: %d%n", e.getErrorCode());
//				System.out.printf("ERROR SQL: %s%n", e.getSQLException().getMessage());
//			}
		} catch (TransientPropertyValueException e) {
			System.out.println("EL departamento NO EXISTE");
			System.out.printf("MENSAJE: %s%n", e.getMessage());
			System.out.printf("Propiedad: %s%n", e.getPropertyName());
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO....");
			System.out.println("Empleado ya existe o departamento no existe");
			System.out.printf("MENSAJE: %s%n", e.getMessage());

			
			//e.printStackTrace();
		}

		session.close();
		System.exit(0);
	}
}

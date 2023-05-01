import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import datos.Departamentos;

public class Main {
	public static void main(String[] args) {
		//Obtener la sesión actual
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		//crear la sesión
		Session session = sesion.openSession();
		//Crear una transacción de la sesión
		Transaction tx = session.beginTransaction();

		System.out.println("Inserto una fila en la tabla DEPARTAMENTOS.");

		Departamentos dep = new Departamentos();
		dep.setDeptNo((byte) 60);
		dep.setDnombre("MARKETING");
		dep.setLoc("GUADALAJARA");

		session.save(dep);
		try {
			tx.commit();
		}catch (javax.persistence.PersistenceException e) {
			if(e.getMessage().contains("ConstraintViolationException")) {
				System.out.println("DEPARTAMENTO DUPLICADO");
			}else {
				System.out.printf("MENSAJE %s%n", e.getMessage());
			}
		}
//		} catch (ConstraintViolationException e) {
//			System.out.println("DEPARTAMENTO DUPLICADO");
//			System.out.printf("MENSAJE: %s%n",e.getMessage());
//			System.out.printf("COD ERROR: %d%n",e.getErrorCode());		
//			System.out.printf("ERROR SQL: %s%n" , 
//                  e.getSQLException().getMessage());
//		}

		session.close();//Cerrar sesión
		System.exit(0);
	}
}

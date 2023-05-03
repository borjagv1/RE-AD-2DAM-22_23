package ejerciciosLibro;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import datos.Empleados;
import datos.HibernateUtil;

public class Ejercicio_3_4 {

    private static SessionFactory sesion;

    public static void main(String[] args) {

        // Obtener la sesión actual
        sesion = HibernateUtil.getSessionFactory();

        // crear la sesión
        Session session = sesion.openSession(); //Abrir y cerrar por método

        // Crear una transacción de la sesión
        Transaction tx = session.beginTransaction();
        int empNo = 7369;
        // Cargamos el objeto Empleado con empNo 7369
        Empleados emple = (Empleados) session.get(Empleados.class, empNo);
        //Si session.get devuelve null es porque el empleado no existe
        //Si no, mostramos los datos
        if (emple == null) {
            System.out.println("El empleado no existe");
        } else {
            System.out.printf("Nombre Empleado: %s%n", emple.getApellido());
            System.out.printf("Oficio: %s%n", emple.getOficio());
            
        }

        try {
            // Confirmar la transacción
            tx.commit();
        } catch (javax.persistence.PersistenceException ex) {
            if (ex.getMessage().contains("ConstraintViolationException")) {
                System.out.println(
                        "Error: Violación de restricción. Alguna de las camisetas ya existe en la base de datos.");
            } else {
                System.out.printf("HA OCURRIDO UN ERROR. MENSAJE: %s%n", ex.getMessage());
            }
        }
        
    }
}

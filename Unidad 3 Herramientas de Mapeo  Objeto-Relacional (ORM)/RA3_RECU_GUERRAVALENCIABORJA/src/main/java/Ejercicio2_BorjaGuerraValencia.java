import java.math.BigInteger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import datos.HibernateUtil;

public class Ejercicio2_BorjaGuerraValencia {
    private static SessionFactory sesion;

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        try {

            ejercicio2(BigInteger.valueOf(22));
            ejercicio2(BigInteger.valueOf(1));
            ejercicio2(BigInteger.valueOf(2));
            ejercicio2(BigInteger.valueOf(3));

        } catch (

        TransientPropertyValueException e) {
            System.err.println("Error al actualizar los objetos: " + e.getMessage() + " NO EXISTE");
            tx.rollback();
        } catch (ConstraintViolationException cve) {
            System.err.println("Error al actualizar los objetos: " + cve.getMessage() + " DUPLICADO");
            tx.rollback();
        } catch (HibernateException he) {
            System.err.println("Error al actualizar los objetos: " + he.getMessage());
            tx.rollback();
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            System.err.println("ERROR NO CONTROLADO al actualizar los objetos : " + e.getMessage());
            tx.rollback();
        } finally {
            // Cerrar la sesión
            session.close();
        }
    }

    private static void ejercicio2(BigInteger id) {
        Session session = sesion.openSession();
        String hql = "SELECT a.id, a.name FROM Allergens a WHERE a.id = :id";
        java.util.List<Object[]> listadoAllergen = session.createQuery(hql, Object[].class).setParameter("id", id)
                .list();
        if (listadoAllergen.isEmpty()) {
            System.out.println("<< NO EXISTE EL ALÉRGENO CON ID: " + id + " >>\n");
        } else {
            // Mostramos el alérgeno a.id y el nombre a.name
            for (Object[] allergen : listadoAllergen) {
                System.out.println("\nALÉRGENO: " + allergen[0] + ", Nombre: " + allergen[1] + "\n");
            }

            String hql2 = "SELECT p.id, p.name FROM Products p JOIN p.allergensProductses a WHERE a.allergens.id = :id";
            java.util.List<Object[]> listadoPlatos = session.createQuery(hql2, Object[].class).setParameter("id", id)
                    .list();
            if (listadoPlatos.isEmpty()) {
                System.out.println("<<NO EXISTEN PLATOS CON EL ALÉRGENO>>");
            } else {
                System.out.println("PLATOS: ");
                for (Object[] plato : listadoPlatos) {
                    System.out.println("Plato: " + plato[0] + ", Nombre: " + plato[1]);
                }
            }
        }

    }
}

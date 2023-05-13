import java.math.BigInteger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import datos.HibernateUtil;
import datos.ResumenMenus;
import datos.ResumenMenusId;

public class Ejercicio4_BorjaGuerraValencia {
    private static SessionFactory sesion;

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");// Para controlar que no haya errores por pantalla de Hibernate
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);// Para controlar que no haya error por pantalla de Hibernate

        sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();

        try {

            String hql = "SELECT pr.id, pr.name AS menuName, pr.price, dm.orden, p.id, p.name AS dishName FROM Products p  JOIN p.dishesMenusesForDishId dm JOIN dm.productsByMenuId pr";
            java.util.List<Object[]> listadoMenus = session.createQuery(hql, Object[].class).list();
            
            for (Object[] menu : listadoMenus) {
                
                ResumenMenus resumenMenus = new ResumenMenus();
                
                ResumenMenusId resumenMenusId = new ResumenMenusId();

              
                resumenMenusId.setIdMenu((BigInteger) menu[0]);
                resumenMenusId.setIdPlato((BigInteger) menu[4]);
           
                resumenMenus.setId(resumenMenusId);
                resumenMenus.setNombreMenu((String) menu[1]);
                resumenMenus.setOrden(BigInteger.valueOf((long) menu[3]));
                resumenMenus.setNombrePlato((String) menu[5]);
                resumenMenus.setAlergenos(dameAllergens(session, menu[4]));
                
                session.save(resumenMenus);
                
            }

            try {
                tx.commit();
                System.out.println("Guardado LOS RESUMEN_MENU: ");

            } catch (javax.persistence.PersistenceException e) {
                if (e.getMessage().contains("ConstraintViolationException")) {
                    System.out.println("EL OBJETO A INSERTAR YA ESTÁ EN LA BBDD --> DUPLICADO ");
                } else {
                    System.out.printf("MENSAJE: %s%n", e.getMessage());
                }
            }
        } catch (ConstraintViolationException cve) {//CONTROL DE ERRORES
            System.err.println("Error al actualizar los objetos: " + cve.getMessage() + " DUPLICADO");
            tx.rollback();

        } catch (TransientPropertyValueException e) {//CONTROL DE ERRORES
            System.err.println("Error al actualizar los objetos: " + e.getMessage() + " NO EXISTE");
            tx.rollback();
        } catch (HibernateException he) {//CONTROL DE ERRORES
            System.err.println("Error al actualizar los objetos: " + he.getMessage());
            tx.rollback();
        } catch (Exception e) {//CONTROL DE ERRORES
            // Manejar cualquier otra excepción
            System.err.println("ERROR NO CONTROLADO al actualizar los objetos : " + e.getMessage());
            tx.rollback();
        } finally {//CONTROL DE ERRORES
            // Cerrar la sesión
            session.close();
        }
    }

    private static String dameAllergens(Session session, Object object) {
        StringBuilder cadena = new StringBuilder();
        String hql3 = "select a.name FROM Allergens a WHERE a.id in (select ap.allergens.id FROM AllergensProducts ap WHERE ap.products.id = :id)";
        java.util.List<String> listadoAllergens = session.createQuery(hql3, String.class)
                .setParameter("id", object).list();
        for (String allergen : listadoAllergens) {
            cadena.append(allergen + ", ");
        }
        if (cadena.length() > 2) {
            cadena.setLength(cadena.length() - 2); // ESTO LO USO PARA Eliminar la coma y el espacio extra al final de
                                                   // la cadena
        }
        return "(" + cadena + ")";
    }
}

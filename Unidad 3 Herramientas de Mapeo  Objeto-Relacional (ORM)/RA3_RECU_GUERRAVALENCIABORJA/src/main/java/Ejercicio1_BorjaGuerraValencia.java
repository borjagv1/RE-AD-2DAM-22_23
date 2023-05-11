
import java.math.BigInteger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import datos.HibernateUtil;

public class Ejercicio1_BorjaGuerraValencia {

    private static SessionFactory sesion;

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();

        try {
            System.out.println("-----------------------------------------------------------------");
            String hql = "SELECT c.id, c.name FROM Categories c order by 1";

            java.util.List<Object[]> listadoCategorias = session.createQuery(hql, Object[].class).list();

            for (Object[] categoria : listadoCategorias) {
                System.out.println("ID: " + categoria[0] + " Nombre: " + categoria[1]);
                String hql2 = "select p.id, p.name, p.price FROM Products p WHERE p.categories.id = :id and p.type = 'plato' order by p.id";
                java.util.List<Object[]> listadoPlatos = session.createQuery(hql2, Object[].class)
                        .setParameter("id", categoria[0]).list();
                if (listadoPlatos.isEmpty()) {
                    System.out.println("<<NO EXISTEN PLATOS EN LA CATEGORÍA>>");
                } else {

                    System.out.println("PLATOS ID  NOMBRE                  PVP ALÉRGENOS");
                    System.out.println(
                            "       === ==================== ====== =========================================================");
                    for (Object[] plato : listadoPlatos) {
                        System.out.printf("%10s %-21s %-5s %-50s\n", plato[0], plato[1],
                                String.format("%.2f", plato[2]), dameAllergens(session, plato[0]));
                    }
                } 
                String hql4 = "SELECT pr.id, pr.name AS dishName, pr.price, dm.orden, p.name AS menuName FROM Products p  JOIN p.dishesMenusesForDishId dm JOIN dm.productsByMenuId pr  WHERE pr.categories.id = :id";
                java.util.List<Object[]> listadoMenus = session.createQuery(hql4, Object[].class)
                        .setParameter("id", categoria[0]).list();
                if (listadoMenus.isEmpty()) {
                    System.out.println("<<NO EXISTEN MENUS EN LA CATEGORÍA>>");
                } else {
                    System.out.println("\nMENUS ID  NOMBRE                         ORDEN  PLATO");
                    System.out.println(
                            "      === ============================== ====== =========================================================");

                    // Creamos variables para guardar los datos del menú actual
                    BigInteger MenuIdActual = BigInteger.valueOf(-1);
                    long ordenActual = 1;
                    for (Object[] menu : listadoMenus) {
                        BigInteger menuId = (BigInteger) menu[0];
                        String menuName = menu[1].toString();
                        Double menuPrice = Double.parseDouble(menu[2].toString());
                        // float menuPrice = Float.parseFloat((String) menu[2]);
                        long orden = (long) menu[3];
                        String plato = menu[4].toString();

                        if (!menuId.equals(MenuIdActual)) {
                            System.out.printf("%-5s %-39s %-1s %-5s%n", "",
                                    menuId + " " + menuName + " (" + menuPrice + " eur.)", orden,
                                    plato);

                            MenuIdActual = menuId;
                        }
                    }
                    
                    for (Object[] menu : listadoMenus) {
                        BigInteger menuId = (BigInteger) menu[0];
                        String menuName = menu[1].toString();
                        Double menuPrice = Double.parseDouble(menu[2].toString());
                        long orden = (long) menu[3];
                        String plato = menu[4].toString();

                        if (menuId.equals(MenuIdActual) && orden != ordenActual) {
                            System.out.printf("%-10s %-34s %-1d %-30s%n", "", "", orden, plato);
                            ordenActual = orden;
                        }
                    }
                    // muestro resultados formateados
                    // System.out.printf("%10s %-31s %-6s %-20s %-15s\n", menu[0], menu[1], menu[2],
                    // menu[3], menu[4]);

                } // LISTADO MENUS

            }

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

    private static String dameAllergens(Session session, Object object) {
        String allergens = "";
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


/*
 * EJERCICIO3: Ejercicio3ApellidosNombre.java.
 * 
 * Disponemos también de las tablas ORDERS en donde se registran los pedidos de
 * productos, la clave es order_id. Se dispone también de la tabla
 * ORDERS_PRODUCTS donde se registran los detalles del pedido. Los campos son:
 * el id del producto (producto_id) y la cantidad (quantity). La clave primaria
 * estará formada por el order_id + product_id. Se muestra a continuación la
 * relación de estas tablas con la tabla PRODUCTS.
 * 
 * Se pide realizar una clase java para insertar pedidos de productos. Diseña
 * dentro de esa clase un método de nombre ejercicio3(array1, array2) que recibe
 * dos arrays de enteros del mismo tamaño, uno contiene los id de productos a
 * insertar y el otro la cantidad solicitada del producto. La fecha del pedido
 * es la fecha actual. El proceso tiene que insertar pedidos. El número de
 * pedido será una unidad más al último insertado. Se debe insertar en las
 * tablas ORDERS y ORDERS PRODUCTS. Ejemplos de llamadas para insertar 3 pedidos
 * con varias líneas de pedido son:
 * 
 * int[] idproductol = (1, 5, 7 };
 * 
 * int[] cantidad1 = {1, 2, 1 };
 * 
 * ejercicio3(idproductol, cantidadi);
 * 
 * int[] idproducto2 = { 3, 4);
 * 
 * ejercicio3(idproducto2, cantidad2);
 * 
 * int[] idproducto3 ( 8, 100, 9, 7, 11 );
 * 
 * int[] cantidad3= (1, 1, 2, 2, 3 ); ejercicio3(idproducto3, cantidad3);
 * 
 * I
 * 
 * La salida del proceso de la inserción será la siguiente, ojo con el producto
 * 100 que no existe, se debe mostrar un mensaje indicándolo. Ejemplo de salida
 * al insertar los pedidos anteriores:
 * 
 * Pedido: 1
 * 
 * Linea de pedido añadida: (1, 1, 1) 
 * 
 * Linea de pedido añadida: (1, 5, 2)
 * 
 * Línea de pedido añadida: (1, 7, 1)
 * 
 * Pedido: 2
 * 
 * Linea de pedido añadida: (2, 3, 2)
 * 
 * Linea de pedido añadida: (2, 4, 3)
 * 
 * Pedido: 3
 * 
 * Linea de pedido añadida: (3, 8, 1)
 * 
 * Problemas al insertar linea de pedido con el producto 100, No se insertará.
 * 
 * Linea de pedido añadida: (3, 9, 2)
 * 
 * Linea de pedido añadida: (3, 7, 2
 */
import java.math.BigInteger;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import datos.HibernateUtil;
import datos.Orders;
import datos.OrdersProducts;
import datos.OrdersProductsId;
import datos.Products;

public class Ejercicio3_BorjaGuerraValencia {

    private static SessionFactory sesion;

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        sesion = HibernateUtil.getSessionFactory();

        int[] idproducto1 = { 1, 5, 7 };
        int[] cantidad1 = { 1, 2, 1 };
        ejercicio3(idproducto1, cantidad1);

        int[] idproducto2 = { 3, 4 };
        int[] cantidad2 = { 2, 3 };
        ejercicio3(idproducto2, cantidad2);

        int[] idproducto3 = { 8, 100, 9, 7, 11 };
        int[] cantidad3 = { 1, 1, 2, 2, 3 };
        ejercicio3(idproducto3, cantidad3);

    }

    private static void ejercicio3(int[] idproducto, int[] cantidad) {
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();

        try {
            // Obtener el último número de pedido
            BigInteger ultimoIDPedido = (BigInteger) session.createQuery("SELECT MAX(id) FROM Orders").uniqueResult();

            // Crear el nuevo número de pedido y la fecha actual del pedido
            // Esto es como hacer un IF para comprobar si es null y si lo es, poner el valor y si no sumarle 1
            BigInteger orderId = ultimoIDPedido != null ? ultimoIDPedido.add(BigInteger.ONE) : BigInteger.ONE;
            Date currentDate = new Date();

           // Creo los objetos Orders y Products fuera del For para poder poner los mensajes al hacer tx.commit()
            Orders order = new Orders(orderId, currentDate);
            Products product = null;
            OrdersProducts ordersProduct = null;

            // Insertar los pedidos y las líneas de pedido correspondientes
            System.out.println("Pedido: " + order.getId());

            for (int i = 0; i < idproducto.length; i++) {
                product = session.get(Products.class, BigInteger.valueOf(idproducto[i]));
                // Compruebo que product no sea null, si lo es, no inserto la línea de pedido
                if (product == null) {
                    System.out.println("Problemas al insertar linea de pedido con el producto " + idproducto[i]
                            + ", No se insertará.");
                    continue;
                }
                 ordersProduct = new OrdersProducts(new OrdersProductsId(order.getId(), product.getId()),
                        order, product,
                        cantidad[i]);

                order.getOrdersProductses().add(ordersProduct);
                product.getOrdersProductses().add(ordersProduct);

                session.saveOrUpdate(order);
                session.saveOrUpdate(ordersProduct);
                //Muestro por pantalla de forma ordenada los pedidos y las líneas de pedido insertadas
                System.out.println("Linea de pedido añadida: " + ordersProduct.getId().getOrderId() + ", "
                        + ordersProduct.getId().getProductId() + ", " + ordersProduct.getQuantity());

            }

            try {
                tx.commit();                

            } catch (javax.persistence.PersistenceException e) {
                if (e.getMessage().contains("ConstraintViolationException")) {
                    System.out.println("EL OBJETO A INSERTAR YA ESTÁ EN LA BBDD --> DUPLICADO ");
                } else {
                    System.out.printf("MENSAJE: %s%n", e.getMessage());
                }
            }
        } catch (HibernateException he) {// CONTROL DE ERRORES
            System.err.println("Error al actualizar los objetos: " + he.getMessage());
            tx.rollback();
        } catch (Exception e) {// CONTROL DE ERRORES
                               // Manejar cualquier otra excepción
            System.err.println("ERROR NO CONTROLADO al actualizar los objetos : " + e.getMessage());
            tx.rollback();
        } finally {// CONTROL DE ERRORES
                   // Cerrar la sesión
            session.close();
        }
    }

}

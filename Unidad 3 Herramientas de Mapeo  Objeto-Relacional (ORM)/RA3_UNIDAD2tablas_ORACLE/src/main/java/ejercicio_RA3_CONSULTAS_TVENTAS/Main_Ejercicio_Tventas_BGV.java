package ejercicio_RA3_CONSULTAS_TVENTAS;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import datos.HibernateUtil;
import datos.Tventas;

public class Main_Ejercicio_Tventas_BGV {
    private static SessionFactory sesion;

    public static void main(String[] args) {
        // código para ocultar los warnings de Hibernate
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        try {
            
            Listado1(session);
            Listado2(session);

        } catch (TransientPropertyValueException tpv) {
            // Manejar la excepción por violación de restricciones
            System.err.println("Error al actualizar los objetos: " + tpv.getMessage() + " NO EXISTE");
            tx.rollback();
        } catch (ConstraintViolationException cve) {
            // Manejar la excepción por violación de restricciones
            System.err.println("Error al actualizar los objetos: " + cve.getMessage() + " DUPLICADO");
            tx.rollback();
        } catch (HibernateException he) {
            // Manejar cualquier otra excepción de Hibernate
            System.err.println("Error al actualizar los objetos: " + he.getMessage());
            tx.rollback();
        } catch (javax.persistence.PersistenceException e) { // Ya controlada por constraint más abajo
            // Manejar la excepción por violación de restricciones
            System.err.println("Error al actualizar los objetos: " + e.getMessage() + " DUPLICADO");
            tx.rollback();
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            System.err.println("Error no controlado de Hibernate es posible que en la consulta: " + e.getMessage());
            tx.rollback();
        } finally {
            // Cerrar la sesión
            session.close();
        }

    }

    private static void Listado2(Session session) {
        // consulta HQl para obtener el nombre del cliente con más numero de ventas en
        // la tabla TVENTAS
        String hql = "select v.tclientes.id , count(v.idventa) from Tventas v  group by v.tclientes.id  order by count(v.idventa) desc";
        // saco el listado de los clientes con más ventas
        List<Object[]> listado = session.createQuery(hql, Object[].class).list();
        // recorro el listado
        long max = 0;
        for (Object[] filaActual : listado) {
            // saco el id del cliente
            String idcliente = (String) filaActual[0];
            // saco el numero de ventas
            Long numventas = (Long) filaActual[1];
            // saco el nombre del cliente
            String nombrecliente = (String) session
                    .createQuery("select c.nombre from Tclientes c where c.idcliente = :idcliente")
                    .setParameter("idcliente", idcliente).uniqueResult();
            // saco el idventa
            List<Tventas> ventas = session
                    .createQuery("from Tventas v where v.tclientes.idcliente = :idcliente",
                            Tventas.class)
                    .setParameter("idcliente", idcliente).list();
            // recorro el listado de idventas
            for (Tventas idventa : ventas) {
                // saco el idventa
                String idventa2 = idventa.getIdventa();
                // saco la fecha de la venta formateada
                Date fechaVenta = (Date) session
                        .createQuery(
                                "select v.fechaventa from Tventas v where v.tclientes.idcliente = :idcliente and v.idventa = :idventa")
                        .setParameter("idcliente", idcliente).setParameter("idventa", idventa2).uniqueResult();
                // formateo fecha de venta dddd-MM-yyyy
                String fechaVentaFormateada = String.format("%td-%tm-%tY", fechaVenta, fechaVenta, fechaVenta);
                // saco el numero de ventas
                // Compruebo que sea solo el máximo
                if (numventas > max) { // Si deseo los dos pues quito el if
                    max = numventas;
                    System.out.println(
                            "===============================DETALLES DE VENTAS DE ESOS CLIENTES=======================");
                    System.out.println(
                            "=========================================================================================");
                    System.out.println("ID CLIENTE: " + idcliente + "\nNombre: " + nombrecliente + "\n");
                    System.out.println("ID VENTA: " + idventa2);
                    System.out.println("FECHA VENTA: " + fechaVentaFormateada + "\n");
                    // Cabecera:
                    // Linea Descripcion Producto Cantidad Pvp Importe
                    // ------ -------------------------- -------- -------- ----------
                    System.out.printf("%-6s %-26s %8s %8s %10s\n", "Linea", "Descripcion", "Cantidad", "Pvp",
                            "Importe");
                    System.out.printf("%-6s %-26s %8s %8s %10s\n", "------", "--------------------------",
                            "--------", "--------", "----------");
                    // saco el listado de las lineas de ventas
                    String hql2 = "select l.id.numerolinea, l.tproductos.descripcion, l.cantidad, l.tproductos.pvp, (l.cantidad * l.tproductos.pvp)AS importe from Tlineasventas l, Tventas v where l.tventas.idventa = :VentaID group by l.id, l.tproductos.descripcion, l.cantidad, l.tproductos.pvp order by l.id asc";
                    List<Object[]> listado2 = session.createQuery(hql2, Object[].class)
                            .setParameter("VentaID", idventa2).list();
                    // recorro el listado de las lineas de ventas
                    int numeroTotalLineas = 0;
                    BigDecimal totalVenta = new BigDecimal(0);
                    for (Object[] lineaActual : listado2) {
                        Short numeroLinea = (short) lineaActual[0];
                        String descripción = (String) lineaActual[1];
                        Short cantidad = (Short) lineaActual[2];
                        BigDecimal pvp = (BigDecimal) lineaActual[3];
                        BigDecimal importe = (BigDecimal) lineaActual[4];
                        System.out.printf("%-6s %-26s %8s %8s %10s\n", numeroLinea, descripción, cantidad, pvp,
                                importe);
                        numeroTotalLineas++;
                        totalVenta = totalVenta.add(importe);
                    }
                    System.out.println("Numero total de lineas: " + numeroTotalLineas);

                    System.out.println("Total venta: " + totalVenta);
                }
            }

        }
    }

    private static void Listado1(Session session) {
        // consulta HQl para obtener el nombre del cliente con más numero de ventas en
        // la tabla TVENTAS
        String hql = "select v.tclientes.id , count(v.idventa)  from Tventas v  group by v.tclientes.id  order by count(v.idventa) desc";
        // saco el listado de los clientes con más ventas
        List<Object[]> listado = session.createQuery(hql, Object[].class).list();
        // recorro el listado
        long max = 0;

        for (Object[] filaActual : listado) {
            // saco el id del cliente
            String idcliente = (String) filaActual[0];
            // saco el numero de ventas
            Long numventas = (Long) filaActual[1];
            // saco el nombre del cliente
            String nombrecliente = (String) session
                    .createQuery("select c.nombre from Tclientes c where c.idcliente = :idcliente")
                    .setParameter("idcliente", idcliente).uniqueResult();
            // saco el numero de ventas
            // Compruebo que sea solo el máximo
            if (numventas > max) { // Si deseo los dos pues quito el if
                max = numventas;
                System.out.println(
                        "===============================CLIENTES CON MÁS VENTAS=====================================");
                System.out.println(
                        "=========================================================================================");
                System.out.println("El cliente con más ventas es: " + nombrecliente + "\ncon un total de: "
                        + numventas + " ventas");
                System.out.println(
                        "=========================================================================================");
            }
        }
    }
}

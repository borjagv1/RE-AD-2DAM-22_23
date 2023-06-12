
package MisBeans;

import java.beans.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mjesus
 */
public class Pedido implements Serializable, PropertyChangeListener {

    private int numeropedido;
    private Producto producto;
    private Date fecha;
    private int cantidad;
    //private  BaseDatos bd ;

    public Pedido() {

    }

    public Pedido(int numeropedido, Producto producto,
            Date fecha, int cantidad) {
        this.numeropedido = numeropedido;
        this.producto = producto;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public int getNumeropedido() {
        return numeropedido;
    }

    public void setNumeropedido(int numeropedido) {
        this.numeropedido = numeropedido;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.printf("Stock anterior: %d%n", evt.getOldValue());
        System.out.printf("Stock actual: %d%n", evt.getNewValue());

        System.out.printf("REALIZAR PEDIDO EN PRODUCTO:%s%n",
                producto.getDescripcion());

        //insertar pedido  en la tabla               
        // BaseDatos.insertaPedido(producto, cantidad);        
    }

}


package MisBeans;

import java.io.Serializable;

public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    private int numeroventa;
    private int idproducto;
    private java.sql.Date fechaventa;
    private int cantidad;

    public Venta() {
    }

    public Venta(int numeroventa, int idproducto,
            java.sql.Date fechaventa, int cantidad) {
        this.numeroventa = numeroventa;
        this.idproducto = idproducto;
        this.fechaventa = fechaventa;
        this.cantidad = cantidad;
    }

    public int getNumeroventa() {
        return this.numeroventa;
    }

    public void setNumeroventa(int numeroventa) {
        this.numeroventa = numeroventa;
    }

    public int getIdproducto() {
        return this.idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public java.sql.Date getFechaventa() {
        return this.fechaventa;
    }

    public void setFechaventa(java.sql.Date fechaventa) {
        this.fechaventa = fechaventa;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

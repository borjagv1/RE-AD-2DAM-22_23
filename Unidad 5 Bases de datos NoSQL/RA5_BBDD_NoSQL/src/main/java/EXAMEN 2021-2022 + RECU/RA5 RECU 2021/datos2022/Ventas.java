package datos2022;

import java.io.Serializable;
import java.time.LocalDate;

public class Ventas implements Serializable {

	private int idventa;
	LocalDate fechaventa;
	private int numero_rep;
	private int idproducto;
	private int cantidad;

	public Ventas(int idventa, LocalDate fechaventa, int numero_rep, int idproducto, int cantidad) {
		super();
		this.idventa = idventa;
		this.fechaventa = fechaventa;
		this.numero_rep = numero_rep;
		this.idproducto = idproducto;
		this.cantidad = cantidad;
	}

	public Ventas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdventa() {
		return idventa;
	}

	public void setIdventa(int idventa) {
		this.idventa = idventa;
	}

	public LocalDate getFechaventa() {
		return fechaventa;
	}

	public void setFechaventa(LocalDate fechaventa) {
		this.fechaventa = fechaventa;
	}

	public int getNumero_rep() {
		return numero_rep;
	}

	public void setNumero_rep(int numero_rep) {
		this.numero_rep = numero_rep;
	}

	public int getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Ventas [idventa=" + idventa + ", fechaventa=" + fechaventa + ", numero_rep=" + numero_rep
				+ ", idproducto=" + idproducto + ", cantidad=" + cantidad + "]";
	}

}

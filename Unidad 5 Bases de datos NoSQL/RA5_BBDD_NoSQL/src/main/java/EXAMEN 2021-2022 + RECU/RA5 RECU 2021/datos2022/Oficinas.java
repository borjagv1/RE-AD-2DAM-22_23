package datos2022;

import java.io.Serializable;

public class Oficinas implements Serializable {
	private int oficina ;   
	private String ciudad ;  
	private int cod_region  ; 
	private int director ;        
	private double total_ventas;
	
	public Oficinas(int oficina, String ciudad, int cod_region, int director, double total_ventas) {
		super();
		this.oficina = oficina;
		this.ciudad = ciudad;
		this.cod_region = cod_region;
		this.director = director;
		this.total_ventas = total_ventas;
	}
	public Oficinas() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getOficina() {
		return oficina;
	}
	public void setOficina(int oficina) {
		this.oficina = oficina;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public int getCod_region() {
		return cod_region;
	}
	public void setCod_region(int cod_region) {
		this.cod_region = cod_region;
	}
	public int getDirector() {
		return director;
	}
	public void setDirector(int director) {
		this.director = director;
	}
	public double getTotal_ventas() {
		return total_ventas;
	}
	public void setTotal_ventas(double total_ventas) {
		this.total_ventas = total_ventas;
	}
	@Override
	public String toString() {
		return "Oficinas [oficina=" + oficina + ", ciudad=" + ciudad + ", cod_region=" + cod_region + ", director="
				+ director + ", total_ventas=" + total_ventas + "]";
	}
	
	

}

package ejercicioFichViajesReservasAleatorios;

import java.io.Serializable;

public class Viaje implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String descripcion;
	private String fechasalida;
	private double pvp;
	private int dias;     // nº dias que dura el viaje
	private int viajeros; // nº de viajeros que hacen el viaje
	
	public Viaje() {
	
	}

	public Viaje(int id, String descripcion, String f1, double pvp, int dias, int viajeros) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fechasalida = f1;
		this.pvp = pvp;
		this.dias = dias;
		this.viajeros = viajeros;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechasalida() {
		return fechasalida;
	}

	public void setFechasalida(String fechasalida) {
		this.fechasalida = fechasalida;
	}

	public double getPvp() {
		return pvp;
	}

	public void setPvp(double pvp) {
		this.pvp = pvp;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public int getViajeros() {
		return viajeros;
	}

	public void setViajeros(int viajeros) {
		this.viajeros = viajeros;
	}

	@Override
	public String toString() {
		return "Viaje: " + id + ", " + descripcion + ", " + fechasalida + ", " + pvp
				+ ", " + dias + ", " + viajeros ;
	}
	

}

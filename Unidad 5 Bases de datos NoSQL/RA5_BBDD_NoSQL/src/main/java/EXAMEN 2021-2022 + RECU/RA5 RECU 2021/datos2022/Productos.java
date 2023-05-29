package datos2022;

import java.io.Serializable;

public class Productos implements Serializable {
	
 private int	id ;
 private String  descripcion ;
	  private int	  stockactual ;
	  private int	  stockminimo ;
	  private double pvp   ;
	public Productos(int id, String descripcion, int stockactual, int stockminimo, double pvp) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.stockactual = stockactual;
		this.stockminimo = stockminimo;
		this.pvp = pvp;
	}
	public Productos() {
		super();
		// TODO Auto-generated constructor stub
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
	public int getStockactual() {
		return stockactual;
	}
	public void setStockactual(int stockactual) {
		this.stockactual = stockactual;
	}
	public int getStockminimo() {
		return stockminimo;
	}
	public void setStockminimo(int stockminimo) {
		this.stockminimo = stockminimo;
	}
	public double getPvp() {
		return pvp;
	}
	public void setPvp(double pvp) {
		this.pvp = pvp;
	}
	@Override
	public String toString() {
		return "Productos [id=" + id + ", descripcion=" + descripcion + ", stockactual=" + stockactual
				+ ", stockminimo=" + stockminimo + ", pvp=" + pvp + "]";
	}
	  
	  

}

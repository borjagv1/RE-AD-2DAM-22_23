package datos;

public class Alergenos {
	private  int id;
	private String nombre; 
	private int numproductos; 
	private String nombreproductos;
	
	public Alergenos(int id, String nombre, int numproductos, String nombreproductos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numproductos = numproductos;
		this.nombreproductos = nombreproductos;
	}
	public Alergenos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumproductos() {
		return numproductos;
	}
	public void setNumproductos(int numproductos) {
		this.numproductos = numproductos;
	}
	public String getNombreproductos() {
		return nombreproductos;
	}
	public void setNombreproductos(String nombreproductos) {
		this.nombreproductos = nombreproductos;
	}
	@Override
	public String toString() {
		return "Alergenos: (" + id + ", " + nombre + ")\n\tNºProd: " + numproductos + 
				"\n\tNombre: "
				+ nombreproductos;
	}
	
	
	
}

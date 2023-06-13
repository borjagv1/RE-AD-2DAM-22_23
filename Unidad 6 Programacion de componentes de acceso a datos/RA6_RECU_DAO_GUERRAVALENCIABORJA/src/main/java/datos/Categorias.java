package datos;

public class Categorias {

	private int id;
	private String nombre;
	private int numproductos;

	public Categorias(int id, String nombre, int numproductos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numproductos = numproductos;
	}

	public Categorias() {
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

	@Override
	public String toString() {
		return "Categoria: (" + id + ", " + nombre + ", " + numproductos + ")";
	}

	
}

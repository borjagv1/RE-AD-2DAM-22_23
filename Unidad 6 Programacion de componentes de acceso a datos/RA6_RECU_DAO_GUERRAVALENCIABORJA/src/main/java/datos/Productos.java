package datos;

public class Productos {

	private int id;
	private String nombre;
	private Double pvp;
	private String tipo;
	private int idcategoria;
	private int numalergenos;
	private String nombrealergenos;

	public Productos(int id, String nombre, Double pvp, String tipo, 
			int idcategoria, int numalergenos,
			String nombrealergenos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pvp = pvp;
		this.tipo = tipo;
		this.idcategoria = idcategoria;
		this.numalergenos = numalergenos;
		this.nombrealergenos = nombrealergenos;
	}

	public Productos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Double getPvp() {
		return pvp;
	}

	public String getTipo() {
		return tipo;
	}

	public int getIdcategoria() {
		return idcategoria;
	}

	public int getNumalergenos() {
		return numalergenos;
	}

	public String getNombrealergenos() {
		return nombrealergenos;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPvp(Double pvp) {
		this.pvp = pvp;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
	}

	public void setNumalergenos(int numalergenos) {
		this.numalergenos = numalergenos;
	}

	public void setNombrealergenos(String nombrealergenos) {
		this.nombrealergenos = nombrealergenos;
	}

	@Override
	public String toString() {
		return "Producto (" + id + ", " + nombre + ", " + pvp + "," + tipo + ", "+
				 idcategoria + ")\n\tNº Alérgenos: " + numalergenos + 
				 "\n\tNombres: " + nombrealergenos ;
	}

}

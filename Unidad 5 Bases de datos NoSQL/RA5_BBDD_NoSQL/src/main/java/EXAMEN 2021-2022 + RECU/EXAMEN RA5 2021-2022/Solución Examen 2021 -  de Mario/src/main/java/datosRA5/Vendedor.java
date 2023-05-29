package datosRA5;
 
public class Vendedor  {	
	private int id; // id del vendedor, valor de 1 a 9999
	private String nombre; // nombre del vendedor 15 caracteres
	private double importeTotal; // importe total de las ventas del vendedor,
								// inicialmente 0
	private int codZona; // código de la zona, valor de 1 a 99
	private String nombreZona; // nombre de la zona, 15 caracteres
	private int nventas; // número de ventas del vendedor, inicialmente 0

	
	public Vendedor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendedor(int id, String nombre, double importeTotal, int codZona, String nombreZona, int nventas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.importeTotal = importeTotal;
		this.codZona = codZona;
		this.nombreZona = nombreZona;
		this.nventas = nventas;
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

	public double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public int getCodZona() {
		return codZona;
	}

	public void setCodZona(int codZona) {
		this.codZona = codZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public int getNventas() {
		return nventas;
	}

	public void setNventas(int nventas) {
		this.nventas = nventas;
	}

	@Override
	public String toString() {

		String cad = String.format("Vendedor: %d, %s, Número Ventas: %3d, Importe ventas: %,9.2f, Zona: %d, Nombre: %s",
				id, nombre,nventas,
				importeTotal, codZona, nombreZona);

		return cad;
	}

}

package datosRA5;


public class Zona  {

	private int id; // identificación de la zona, campo clave
	private String nombrezona; // 15 caracteres
	private String provincia; // 20 caracteres
	private int ventas;	   //número de ventas en la zona, inicialmente es 0  
    private double importeTotal;  //importe total de las ventas en la zona, 
        

	public Zona() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Zona(int id, String nombrezona, String provincia, int ventas,
										double importeTotal) {
									super();
									this.id = id;
									this.nombrezona = nombrezona;
									this.provincia = provincia;
									this.ventas = ventas;
									this.importeTotal = importeTotal;
								}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombrezona() {
		return nombrezona;
	}

	public void setNombrezona(String nombrezona) {
		this.nombrezona = nombrezona;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	

	public int getVentas() {
		return ventas;
	}



	public void setVentas(int ventas) {
		this.ventas = ventas;
	}



	public double getImporteTotal() {
		return importeTotal;
	}



	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}



	@Override
	public String toString() {
		return String.format("Zona: %d, Nombre de Zona: %15s, Nº ventas: %2d, Importe Total: %,9.2f",
				id, nombrezona, ventas,importeTotal);
	}

}// fin Zona
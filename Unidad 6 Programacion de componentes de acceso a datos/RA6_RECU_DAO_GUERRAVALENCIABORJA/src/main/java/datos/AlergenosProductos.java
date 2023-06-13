package datos;

public class AlergenosProductos {

	private int idproduct;
	private int  idalergeno;
	public AlergenosProductos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AlergenosProductos(int idproduct, int idalergeno) {
		super();
		this.idproduct = idproduct;
		this.idalergeno = idalergeno;
	}
	public int getIdproduct() {
		return idproduct;
	}
	public int getIdalergeno() {
		return idalergeno;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}
	public void setIdalergeno(int idalergeno) {
		this.idalergeno = idalergeno;
	}
	@Override
	public String toString() {
		return "AlergenosProductos (" + idproduct + ", " + idalergeno + ")";
	}
	
	
	
}

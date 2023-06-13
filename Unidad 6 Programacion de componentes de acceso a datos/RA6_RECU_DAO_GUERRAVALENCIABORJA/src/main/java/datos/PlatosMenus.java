package datos;

public class PlatosMenus {

	private int idmenu;
	private int idplato;	
	private int orden;
	
	
	public PlatosMenus(int id_menu, int id_plato, int orden) {
		super();
		this.idmenu = id_menu;
		this.idplato = id_plato;
		this.orden = orden;
	}
	
	
	public PlatosMenus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getIdmenu() {
		return idmenu;
	}


	public int getIdplato() {
		return idplato;
	}


	public void setIdmenu(int idmenu) {
		this.idmenu = idmenu;
	}


	public void setIdplato(int idplato) {
		this.idplato = idplato;
	}


	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}


	@Override
	public String toString() {
		return "PlatosMenus (" + idmenu + ", " + idplato + ", " + orden + ")";
	}
	
	
	
}

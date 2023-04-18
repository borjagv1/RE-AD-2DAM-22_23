package ejercicioEmpleyDepConFichSecuencialesyAleatorios;



import java.io.Serializable;

@SuppressWarnings("serial")
public class Departamento implements Serializable {
	private int dep;
	private String nombre;
	private String loc;	

	public Departamento(int dep, String nombre,  String loc) {
		this.nombre = nombre;
		this.dep = dep;
		this.loc = loc;
	}

	public Departamento() {
		this.nombre = null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public int getDep() {
		return dep;
	}

	public void setDep(int dep) {
		this.dep = dep;
	}

	
}// fin Departamento
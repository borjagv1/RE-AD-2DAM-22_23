package ejercicioClaseCrearProvincias_JAXB;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"nombre", "cp"})

//Xmlprop order nombre cp
public class Localidad {
	@Override
	public String toString() {
		return "Localidad [nombre=" + nombre + ", cp=" + cp + "]";
	}

	String nombre;
	int cp;
	//GETTERS & SETTERS
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}
	//CONSTRUCTORES
	public Localidad(String nombre, int cp) {
		super();
		this.nombre = nombre;
		this.cp = cp;
	}

	public Localidad() {
		
	}
}

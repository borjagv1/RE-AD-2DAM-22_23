package ejercicioClaseCrearProvincias_JAXB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name="provincia")
@XmlType(propOrder = {"nombre", "listaLocalidad"})
//rootelement name = provinvia
//proporder = nombre Loc
public class Provincia {
	private ArrayList<Localidad> listaLocalidad;
	String nombre;

	// CONSTRUCTORES

	Provincia() {
		// super();
	}

	public Provincia(ArrayList<Localidad> listaLocalidad, String nombre) {
		super();
		this.listaLocalidad = listaLocalidad;
		this.nombre = nombre;
	}
	//GETTERS AND SETTERS
	// Wrapper, envoltura alrededor la representaci�n XML
	@XmlElementWrapper(name = "MiListaLocalidades") //
	@XmlElement(name = "Localidad")
	public ArrayList<Localidad> getListaLocalidad() {
		return listaLocalidad;
	}

	public void setListaLocalidad(ArrayList<Localidad> listaLocalidad) {
		this.listaLocalidad = listaLocalidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

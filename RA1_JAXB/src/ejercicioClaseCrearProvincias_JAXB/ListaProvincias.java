package ejercicioClaseCrearProvincias_JAXB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name="listaProvincias")
@XmlType(propOrder = {"zonaTerritorial", "listaProvincia"})

public class ListaProvincias {
	String zonaTerritorial;
	private ArrayList<Provincia> listaProvincia;
	
	
	public ListaProvincias() {
		
	}
	public ListaProvincias(String zonaTerritorial, ArrayList<Provincia> listaProvincia) {
		super();
		this.zonaTerritorial = zonaTerritorial;
		this.listaProvincia = listaProvincia;
	}
	public String getZonaTerritorial() {
		return zonaTerritorial;
	}
	public void setZonaTerritorial(String zonaTerritorial) {
		this.zonaTerritorial = zonaTerritorial;
	}
	public ArrayList<Provincia> getListaProvincia() {
		return listaProvincia;
	}
	public void setListaProvincia(ArrayList<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}
	
}

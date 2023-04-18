package ejercicioClaseCrearProvincias_JAXB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class Main {
	private static final String MIARCHIVO_XML = "./provincia.xml";

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		// Se crea la lista de localidades
		ArrayList<Localidad> localidadLista = new ArrayList<Localidad>();
		// ArrayList<Localidad> localidadLista2 = new ArrayList<Localidad>();
		ArrayList<Provincia> provinciaLista = new ArrayList<Provincia>();//
		ArrayList<ListaProvincias> territorioLista = new ArrayList<ListaProvincias>();

		// Creamos TRES Localidades y los añadimos
		Localidad localidad1 = new Localidad("AZUQUECA", 19200);
		localidadLista.add(localidad1);
		Localidad localidad2 = new Localidad("VILLANUEVA DE LA TORRE", 19209);
		localidadLista.add(localidad2);
		Localidad localidad3 = new Localidad("ALOVERA", 19208);
		localidadLista.add(localidad3);

		// Se crea La PROVINCIA y se le asigna la lista de lcoalidades
		Provincia miprovincia = new Provincia();
		miprovincia.setNombre("GUADALAJARA");
		miprovincia.setListaLocalidad(localidadLista);
		provinciaLista.add(miprovincia);

		Provincia miprovincia2 = new Provincia();//
		miprovincia2.setNombre("MADRID");
		miprovincia2.setListaLocalidad(localidadLista);
		provinciaLista.add(miprovincia2);

		Provincia miprovincia3 = new Provincia();//
		miprovincia3.setNombre("TOLEDO");
		miprovincia3.setListaLocalidad(localidadLista);
		provinciaLista.add(miprovincia3);

		
		//CREAMOS EL TERRITORIO Y METEMOS LAS PROVINCIAS
		ListaProvincias territorio = new ListaProvincias();
		territorio.setZonaTerritorial("CLM");
		territorio.setListaProvincia(provinciaLista);

		// Creamos el contexto indicando la clase raíz
		JAXBContext context = JAXBContext.newInstance(ListaProvincias.class);

		// Creamos el Marshaller, convierte el java bean en una cadena XML
		Marshaller m = context.createMarshaller();

		// Formateamos el xml para que quede bien
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Lo visualizamos con system out
		m.marshal(territorio, System.out);
		

		// m.marshal(miprovincia3, System.out);//

		// Escribimos en el archivo
		//m.marshal(territorio, new File(MIARCHIVO_XML));
		m.marshal(territorio, new File("listaprovincias.xml"));
	

// m.marshal(miprovincia3, new File(MIARCHIVO_XML));

		// Visualizamos ahora los datos del documento XML creado
		System.out.println("------------ Leo el XML ---------");

		// Se crea Unmarshaller en el cotexto de la clase Libreria
		Unmarshaller unmars = context.createUnmarshaller();

		// Utilizamos el m�todo unmarshal, para obtener datos de un Reader
		//ListaProvincias list = (ListaProvincias) unmars.unmarshal(new FileReader(MIARCHIVO_XML));
		ListaProvincias list = (ListaProvincias) unmars.unmarshal(new FileReader("listaprovincias.xml"));


		// Recuperamos el array list y visualizamos
		System.out.println("Zona territorial: " + list.getZonaTerritorial());
		System.out.println("Provincias del territorio: " + list.getListaProvincia().size());

		ArrayList<Provincia> lista = list.getListaProvincia();
		for (Provincia prov : lista) {
			System.out.println("\tNombres: " + prov.getNombre() + " , nº de localidades: " + prov.getListaLocalidad().size());
			
			System.out.println("Nombres: " + prov.getNombre());
			System.out.println("Nº de localidades: " + prov.getListaLocalidad().size());
			
			ArrayList<Localidad> listaloc = prov.getListaLocalidad();
			
			for(Localidad l : listaloc) {
				System.out.println("\t" + l);
			}

		}
	}

}

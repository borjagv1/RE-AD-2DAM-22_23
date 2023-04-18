package clasesjaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType; 

@XmlType(propOrder = {"nombre", "autor", "editorial", "isbn"})

public class Libro { 
    private String nombre;
    private String autor;
    private String editorial;
    private String isbn;
    public Libro(String nombre, String autor, String editorial, String isbn) {
		super();
		this.nombre = nombre;
		this.autor = autor;
		this.editorial = editorial;
		this.isbn = isbn;
	}   
	public Libro() {}
	
	@XmlElement(name= "Cambiado_el_nombre_atributo") //Siempre encima del metodo get
    public String getNombre() { return nombre; }
	
    public String getAutor() { return autor;  }
    public String getEditorial() {return editorial; }
    public String getIsbn() { return isbn;}
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setEditorial(String editorial) { this.editorial = editorial;  }
    public void setIsbn(String isbn) {  this.isbn = isbn;  }
}

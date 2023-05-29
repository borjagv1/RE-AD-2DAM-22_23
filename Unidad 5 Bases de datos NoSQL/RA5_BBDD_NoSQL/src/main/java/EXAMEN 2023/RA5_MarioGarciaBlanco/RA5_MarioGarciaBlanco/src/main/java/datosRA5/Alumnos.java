
package datosRA5;

/**
 *
 * @author mjrm2
 */
public class Alumnos {    
    private int num_alumno;
    private int cod_curso ;  
    private String nombre ;
    private String direccion;
    private String tlf ;
    private double nota_media;
    private String nombrecurso;

    public Alumnos() {
    }

    public Alumnos(int num_alumno, int cod_curso, String nombre, String direccion, String tlf, double nota_media,
			String nombrecurso) {
		super();
		this.num_alumno = num_alumno;
		this.cod_curso = cod_curso;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tlf = tlf;
		this.nota_media = nota_media;
		this.nombrecurso = nombrecurso;
	}

	public Alumnos(int num_alumno, int cod_curso, 
    		String nombre, String direccion, String tlf, double nota_media) {
        this.num_alumno = num_alumno;
        this.cod_curso = cod_curso;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tlf = tlf;
        this.nota_media = nota_media;
    }

      
    public int getNum_alumno() {
        return num_alumno;
    }

    public void setNum_alumno(int num_alumno) {
        this.num_alumno = num_alumno;
    }

    public int getCod_curso() {
        return cod_curso;
    }

    public void setCod_curso(int cod_curso) {
        this.cod_curso = cod_curso;
    }

    public String getNombrecurso() {
        return nombrecurso;
    }

    public void setNombrecurso(String nombrecurso) {
        this.nombrecurso = nombrecurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public double getNota_media() {
        return nota_media;
    }

    public void setNota_media(double nota_media) {
        this.nota_media = nota_media;
    }

    @Override
    public String toString() {
        return "Alumnos[" + num_alumno + ", " + cod_curso + ", " + nombrecurso + 
                ", " + nombre + ", " + direccion + ", " + tlf + ", " + nota_media + ']';
    }
    
    
    
    
}

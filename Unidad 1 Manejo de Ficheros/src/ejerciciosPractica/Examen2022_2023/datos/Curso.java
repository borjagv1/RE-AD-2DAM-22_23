package ejerciciosPractica.Examen2022_2023.datos;

public class Curso {

	private int id; // identificaci�n del curso, campo clave (de 1 a 99)
	private String nombrecurso; // 25 caracteres
	private int numeroalumnos; // n�mero de alumnos en el curso, inicialmente es 0
	private double notamedia;

	public Curso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Curso(int id, String nombrecurso, int numeroalumnos, double notamedia) {
		super();
		this.id = id;
		this.nombrecurso = nombrecurso;
		this.numeroalumnos = numeroalumnos;
		this.notamedia = notamedia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombrecurso() {
		return nombrecurso;
	}

	public void setNombrecurso(String nombrecurso) {
		this.nombrecurso = nombrecurso;
	}

	public int getNumeroalumnos() {
		return numeroalumnos;
	}

	public void setNumeroalumnos(int numeroalumnos) {
		this.numeroalumnos = numeroalumnos;
	}

	public double getNotamedia() {
		return notamedia;
	}

	public void setNotamedia(double notamedia) {
		this.notamedia = notamedia;
	}

	@Override
	public String toString() {
		return "Curso [" + id + ", " + nombrecurso + ", Alumnos=" + numeroalumnos + ", Media="
				+ notamedia + "]";
	}

}

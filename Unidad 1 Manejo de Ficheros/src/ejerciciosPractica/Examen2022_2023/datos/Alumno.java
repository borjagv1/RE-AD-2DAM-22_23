package ejerciciosPractica.Examen2022_2023.datos;

import java.io.Serializable;

public class Alumno implements Serializable{
	private int id; // id del alumno, valor de 1 a 9999
	private String nombre; // nombre del alumno 15 caracteres
	private double notamedia; // nota media del alumno, inicialmente 0
	private int idCurso; // identificaci�n del curso, valor de 1 a 99
	private String nombreCurso; // nombre del curso, 25 caracteres
	private int nasignaturas;

	public Alumno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Alumno(int id, String nombre, double notamedia, int idCurso, String nombreCurso, int nasignaturas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.notamedia = notamedia;
		this.idCurso = idCurso;
		this.nombreCurso = nombreCurso;
		this.nasignaturas = nasignaturas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getNotamedia() {
		return notamedia;
	}

	public void setNotamedia(double notamedia) {
		this.notamedia = notamedia;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public int getNasignaturas() {
		return nasignaturas;
	}

	public void setNasignaturas(int nasignaturas) {
		this.nasignaturas = nasignaturas;
	}

	@Override
	public String toString() {
		return "Alumno [" + id + ", " + nombre + ", Media: " + notamedia + ", " + idCurso
				+ ", " + nombreCurso + ", Asignat: " + nasignaturas + "]";
	}

	
	
}

package dao;

import java.util.ArrayList;

import datos.Alumnos;

public interface AlumnosDAO {

	public int InsertarAlumno(Alumnos alum);
	public int EliminarAlumno(int codigo);
	public int ModificarAlumno(int codigo, Alumnos nuevo);  

    //Calcula el campo nota_media para cada alumno
    public boolean ActualizarDatos(); 

	public Alumnos ConsultarAlumno(int codigo);           
	public ArrayList <Alumnos> TodosLosAlumnos();
    

}

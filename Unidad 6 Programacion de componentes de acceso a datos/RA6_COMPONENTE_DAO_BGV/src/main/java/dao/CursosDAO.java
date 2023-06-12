package dao;

import java.util.ArrayList;

import datos.Cursos;

public interface CursosDAO {

	public int InsertarCurso(Cursos cur);
	public int EliminarCurso(int codigo);
	public int ModificarCurso(int codigo, Cursos nuevo);
  
	//actualiza los campos num_alumnos y nota_media de Cursos
    public boolean ActualizarDatos();
    
	public Cursos ConsultarCurso(int codigo);           
	public ArrayList <Cursos> TodosLosCursos();          

}

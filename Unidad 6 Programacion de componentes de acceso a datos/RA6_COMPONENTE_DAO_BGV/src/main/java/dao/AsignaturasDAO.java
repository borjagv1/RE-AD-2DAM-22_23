package dao;

import java.util.ArrayList;

import datos.Asignaturas;

public interface AsignaturasDAO {
    public int InsertarAsignatura(Asignaturas asig);
	public int EliminarAsignatura(int asig);
	public int ModificarAsignatura(int asig, Asignaturas nuevo);  

    public boolean ActualizarAsignatura(); 

	public Asignaturas ConsultarAsignatura(int codigo);           
	public ArrayList <Asignaturas> TodasLasAsignaturas();
}

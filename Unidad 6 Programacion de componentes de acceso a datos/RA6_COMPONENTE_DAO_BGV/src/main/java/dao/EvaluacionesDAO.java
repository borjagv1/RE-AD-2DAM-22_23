package dao;

import java.util.ArrayList;

import datos.*;

public interface EvaluacionesDAO {
    
    //todos los datos de las evaluaciones
    public ArrayList<Evaluaciones> DatosTodasLasEvaluaciones();

    //Notas de una evaluación, recibe una evaluación
    public ArrayList<Evaluaciones> DatosUnaEvaluacion(int eva);

    //Datos de un alumno en una evaluacion concreta, recibe una evaluación y un 
    //un número de alumno
    public ArrayList<Evaluaciones> EvaluacionesAlumno(int eva, int codigo);


}

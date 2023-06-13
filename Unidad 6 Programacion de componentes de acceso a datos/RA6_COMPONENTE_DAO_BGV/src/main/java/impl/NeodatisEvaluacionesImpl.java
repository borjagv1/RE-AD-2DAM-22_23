package impl;

import java.util.ArrayList;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;

import dao.EvaluacionesDAO;
import datos.Evaluaciones;
import factory.NeodatisDAOFactory;

public class NeodatisEvaluacionesImpl implements EvaluacionesDAO {
    static ODB odb;

    public NeodatisEvaluacionesImpl() {
        odb = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public ArrayList<Evaluaciones> DatosTodasLasEvaluaciones() {
        ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();

        Objects<Evaluaciones> objects = odb.getObjects(Evaluaciones.class);
        while (objects.hasNext()) {
            Evaluaciones eva = objects.next();
            evaluaciones.add(eva);
        }

        return evaluaciones;
    }

    @Override
    public ArrayList<Evaluaciones> DatosUnaEvaluacion(int eva) {
        ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();

        Objects<Evaluaciones> objects = odb.getObjects(Evaluaciones.class);
        while (objects.hasNext()) {
            Evaluaciones ev = objects.next();
            if (ev.getCod_evaluacion() == eva) {
                evaluaciones.add(ev);
                break;
            }
        }

        if (evaluaciones.isEmpty()) {
            System.out.printf("Evaluación: %d No existe%n", eva);
        }

        return evaluaciones;
    }

    @Override
    public ArrayList<Evaluaciones> EvaluacionesAlumno(int eva, int codigo) {
        ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();

        Objects<Evaluaciones> objects = odb.getObjects(Evaluaciones.class);
        while (objects.hasNext()) {
            Evaluaciones ev = objects.next();
            if (ev.getCod_evaluacion() == eva && ev.getNum_alumno() == codigo) {
                evaluaciones.add(ev);
                break;
            }
        }

        if (evaluaciones.isEmpty()) {
            System.out.println("No existe evaluación " + eva + " para el alumno" + codigo);
        }

        return evaluaciones;
    }

}

import dao.AlumnosDAO;
import dao.EvaluacionesDAO;
import datos.Alumnos;
import datos.Evaluaciones;
import factory.DAOFactory;
import impl.NeodatisEvaluacionesImpl;

public class NEODATIS_DAO6_PRUEBA {

    static EvaluacionesDAO evaDAO;
    static AlumnosDAO aluDAO;

    public static void main(String[] args) {
        // NEODATIS
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        // CONSULTAR TODAS LAS EVALUACIONES
        evaDAO = bd.getEvaluacionesDAO();
        for (Evaluaciones e : evaDAO.DatosTodasLasEvaluaciones()) {
            System.out.println(e.getCod_evaluacion() + " " + e.getNum_alumno() + " " + e.getCod_asig()
                    + " " + e.getNota() + " " + e.getNombreasig());
        }

        // CONSULTAR UNA EVALUACION
        System.out.println("CONSULTAR UNA EVALUACION");
        evaDAO = bd.getEvaluacionesDAO();
        for (Evaluaciones e : evaDAO.DatosUnaEvaluacion(1)) {
            System.out.println(e.getCod_evaluacion() + " " + e.getNum_alumno() + " " + e.getCod_asig()
                    + " " + e.getNota() + " " + e.getNombreasig());
        }

        // CONSULTAR EVALUACIONES DE UN ALUMNO
        System.out.println("CONSULTAR EVALUACIONES DE UN ALUMNO");
        evaDAO = bd.getEvaluacionesDAO();
        for (Evaluaciones e : evaDAO.EvaluacionesAlumno(1, 1010)) {
            System.out.println(e.getCod_evaluacion() + " " + e.getNum_alumno() + " " + e.getCod_asig()
                    + " " + e.getNota() + " " + e.getNombreasig());
        }

        // COnsultar todos los alumnos
        /*
         * private int num_alumno;
         * private int cod_curso ;
         * private String nombrecurso;
         * private String nombre ;
         * private String direccion;
         * private String tlf ;
         * private double nota_media;
         */
        System.out.println("ACTUALIZAR LOS ALUMNOS");
        aluDAO = bd.getAlumnosDAO();
        aluDAO.ActualizarDatos();

        System.out.println("CONSULTAR TODOS LOS ALUMNOS");
        aluDAO = bd.getAlumnosDAO();
        for (Alumnos a : aluDAO.TodosLosAlumnos()) {
            System.out.println(a.getNum_alumno() + " " + a.getCod_curso() + " " + a.getNombrecurso()
                    + " " + a.getNombre() + " " + a.getDireccion() + " " + a.getTlf() + " " + a.getNota_media());
        }
       // .................

    }
}

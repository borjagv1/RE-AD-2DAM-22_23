import dao.AlumnosDAO;
import dao.CursosDAO;
import dao.EvaluacionesDAO;
import datos.Cursos;
import datos.Evaluaciones;
import factory.DAOFactory;

public class MONGODB_DAO_PRUEBA {
    static EvaluacionesDAO evaDAO;
    static AlumnosDAO aluDAO;
    static CursosDAO curDAO;
    public static void main(String[] args) {
        // MONGODB
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MONGODB);
        // consultar todas las evaluaciones
        evaDAO = bd.getEvaluacionesDAO();
        for (Evaluaciones e : evaDAO.DatosTodasLasEvaluaciones()) {
            System.out.println(e.getCod_evaluacion() + " " + e.getNum_alumno() + " " + e.getCod_asig() + " "
                    + e.getNota() + " " + e.getNombreasig());
        }
        // consultar una evaluacion
        System.out.println("CONSULTAR UNA EVALUACION");
        evaDAO = bd.getEvaluacionesDAO();
        for (Evaluaciones e : evaDAO.DatosUnaEvaluacion(1)) {
            System.out.println(e.getCod_evaluacion() + " " + e.getNum_alumno() + " " + e.getCod_asig() + " "
                    + e.getNota() + " " + e.getNombreasig());
        }
        // consultar evaluaciones de un alumno
        System.out.println("CONSULTAR EVALUACIONES DE UN ALUMNO");
        evaDAO = bd.getEvaluacionesDAO();
        for (Evaluaciones e : evaDAO.EvaluacionesAlumno(2, 1010)) {
            System.out.println(e.getCod_evaluacion() + " " + e.getNum_alumno() + " " + e.getCod_asig() + " "
                    + e.getNota() + " " + e.getNombreasig());
        }

        // CURSOS
        // ACTUALIZAR NOTA MEDIA DE los cursos
        System.out.println("ACTUALIZAR NOTA MEDIA DE LOS CURSOS");
        curDAO = bd.getCursosDAO();
        curDAO.ActualizarDatos();
        // consultar todos los cursos
        System.out.println("CONSULTAR TODOS LOS CURSOS");
        curDAO = bd.getCursosDAO();
        for (Cursos c : curDAO.TodosLosCursos()) {
            System.out.println(c.getCod_curso() + " " + c.getDenominacion() + " " + c.getNota_media() + " "
                    + c.getNum_alumnos());
        }
        
    }
}

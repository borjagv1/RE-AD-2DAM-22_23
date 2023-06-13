import dao.AlumnosDAO;
import dao.AsignaturasDAO;
import dao.CursosDAO;
import dao.EvaluacionesDAO;
import datos.Alumnos;
import factory.DAOFactory;

/**
 * SQLDB_DAO6_PRUEBA
 */
public class SQLDB_DAO6_PRUEBA {
    static AlumnosDAO alumDAO;
    static CursosDAO curDAO;
    static AsignaturasDAO asigDAO;
    static EvaluacionesDAO evaDAO;

    public static void main(String[] args) {
        // MYSQL
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        // ALUMNOS
        // INSERTAR ALUMNO (no debe dejar, ya existe)
        alumDAO = bd.getAlumnosDAO();
        Alumnos alum1 = new Alumnos(1013, 10, "Juan", "Calle 1", "111111111", 8.5);
        alumDAO.InsertarAlumno(alum1);

        // ELIMINAR ALUMNO
        System.out.println("Elimino alumno 1013 => " + alumDAO.EliminarAlumno(1013));
        alumDAO = bd.getAlumnosDAO();

        // MODIFICAR ALUMNO
        Alumnos alum2 = new Alumnos(1013, 10, "Jose", "Calle 2", "222222222", 8.5);
        System.out.println("Modifico alumno 1013 => " + alumDAO.ModificarAlumno(1013, alum2));

        // Actualizar nota media
        System.out.println("Actualizo nota media alumnos => " + alumDAO.ActualizarDatos());

        // CONSULTAR ALUMNO
        alumDAO = bd.getAlumnosDAO();
        Alumnos alum = alumDAO.ConsultarAlumno(1013);

        System.out.println(alum.getCod_curso() + " " + " " + alum.getNombre() + " " + alum.getDireccion() + " "
                + alum.getTlf() + " " + alum.getNota_media());

        // CONSULTAR TODOS LOS ALUMNOS
        alumDAO = bd.getAlumnosDAO();
        for (Alumnos a : alumDAO.TodosLosAlumnos()) {
            System.out.println(a.getNum_alumno() + " " + a.getCod_curso() + " " + a.getNombre() + " "
                    + a.getDireccion() + " " + a.getTlf() + " " + a.getNota_media());
        }
    }
}
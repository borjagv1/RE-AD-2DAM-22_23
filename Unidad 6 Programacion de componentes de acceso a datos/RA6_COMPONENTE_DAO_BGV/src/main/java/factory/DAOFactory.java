package factory;

import dao.AlumnosDAO;
import dao.AsignaturasDAO;
import dao.CursosDAO;
import dao.EvaluacionesDAO;

public abstract class DAOFactory {
    // Bases de datos soportadas Neodatis, MongoDB, MySQL
    public static final int MYSQL = 1;
    public static final int NEODATIS = 2;
    public static final int MONGODB = 3;

    public abstract AlumnosDAO getAlumnosDAO();

    public abstract AsignaturasDAO geAsignaturasDAO();

    public abstract CursosDAO getCursosDAO();

    public abstract EvaluacionesDAO getEvaluacionesDAO();

    public static DAOFactory getDAOFactory(int bd) {
        switch (bd) {
            case MYSQL:
                return new SqlDbDAOFactory(MYSQL);
            case NEODATIS:
                return new NeodatisDAOFactory();
            case MONGODB:
                return new MongoDbDAOFactory();
           default:
                return null;
        }
    }
}

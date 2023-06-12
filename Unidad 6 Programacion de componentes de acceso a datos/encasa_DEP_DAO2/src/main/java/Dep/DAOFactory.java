package Dep;

public abstract class DAOFactory {
    // Bases de datos soportadas
    public static final int MYSQL = 1;
    public static final int NEODATIS = 2;

    // Método para obtener un objeto DAO
    public abstract DepartamentoDao getDepartamentoDAO();

    // elegimos BD
    public static DAOFactory getDAOFactory(int bd) {
        switch (bd) {
        case MYSQL:
            return new SqlDbDAOFactory();
        case NEODATIS:
            return new NeodatisDAOFactory();
        default:
            return null;
        }
    }
}

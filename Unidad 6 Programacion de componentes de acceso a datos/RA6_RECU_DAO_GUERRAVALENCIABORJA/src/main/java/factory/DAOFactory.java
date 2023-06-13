package factory;

import dao.AlergenosDAO;
import dao.CategoriasDAO;
import dao.ProductoDAO;

public abstract class DAOFactory {
    // Bases de datos soportadas Neodatis, MongoDB, MySQL
    public static final int MYSQL = 1;
    public static final int NEODATIS = 2;
    public static final int MONGODB = 3;

    // creo los metodos abstractos para cada DAO que quiero crear
    // AlergenosDAO, CategoriasDAO, ProductoDAO
    public abstract AlergenosDAO getAlergenosDAO();

    public abstract CategoriasDAO getCategoriasDAO();
    
    public abstract ProductoDAO getProductosDAO();

    // creo el metodo getDAOFactory para trabajar con las diferentes BD
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

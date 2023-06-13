package factory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import dao.AlergenosDAO;
import dao.CategoriasDAO;
import dao.ProductoDAO;
import impl.MongoDbAlergenosImpl;
import impl.MongoDbCategoriasImpl;
import impl.MongoDbProductosImpl;

public class MongoDbDAOFactory extends DAOFactory {
    static MongoClient cliente;
    static MongoDatabase db = null;

    public static MongoDatabase crearConexion() {
        if (db == null) {
            cliente = new MongoClient();
            db = cliente.getDatabase("ra6recu");
        }
        return db;
    }

    public MongoDbDAOFactory() {
    }

    @Override
    public AlergenosDAO getAlergenosDAO() {
        return new MongoDbAlergenosImpl();
    }

    @Override
    public CategoriasDAO getCategoriasDAO() {
        return new MongoDbCategoriasImpl();
    }

    @Override
    public ProductoDAO getProductosDAO() {
        return new MongoDbProductosImpl();
    }

}

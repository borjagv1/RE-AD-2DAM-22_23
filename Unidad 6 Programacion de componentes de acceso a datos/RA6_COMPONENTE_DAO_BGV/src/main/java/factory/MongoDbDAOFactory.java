package factory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import dao.AlumnosDAO;
import dao.AsignaturasDAO;
import dao.CursosDAO;
import dao.EvaluacionesDAO;
import impl.MongoDbAlumnosImpl;
import impl.MongoDbAsignaturasImpl;
import impl.MongoDbCursosImpl;
import impl.MongoDbEvaluacionesImpl;

public class MongoDbDAOFactory extends DAOFactory {
    // Crear conexion a la base de datos MONGO
    // Para acceder a la Base de datos Mongo crea el cliente de esta manera:
    // MongoClient cliente = new MongoClient();
    // MongoDatabase db = cliente.getDatabase("ra6");
    static MongoClient cliente;
    static MongoDatabase db = null;

    public static MongoDatabase crearConexion() {
        if (db == null) {
            cliente = new MongoClient();
            db = cliente.getDatabase("ra6");
        }
        return db;
    }

    public MongoDbDAOFactory() {
    }

    @Override
    public AlumnosDAO getAlumnosDAO() {
        return new MongoDbAlumnosImpl();
    }

    @Override
    public AsignaturasDAO geAsignaturasDAO() {
        return new MongoDbAsignaturasImpl();
    }

    @Override
    public CursosDAO getCursosDAO() {
        return new MongoDbCursosImpl();
    }

    @Override
    public EvaluacionesDAO getEvaluacionesDAO() {
        return new MongoDbEvaluacionesImpl();
    }

}

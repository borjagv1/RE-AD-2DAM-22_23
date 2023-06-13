package factory;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import dao.AlergenosDAO;
import dao.CategoriasDAO;
import dao.ProductoDAO;
import impl.NeodatisAlergenosImpl;
import impl.NeodatisCategoriasImpl;
import impl.NeodatisProductosImpl;

public class NeodatisDAOFactory extends DAOFactory {
    static ODB bd = null;

    public NeodatisDAOFactory() {
    }

    public static ODB crearConexion() {
        if (bd == null) {
            String fichero = "BDCAFETERIARECU.neo";
            bd = ODBFactory.open(fichero);// Abrir BD
        }
        return bd;
    }

    public static void cerrarConexion() {
        bd.close();
    }

    @Override
    public AlergenosDAO getAlergenosDAO() {
        return new NeodatisAlergenosImpl();
    }

    @Override
    public CategoriasDAO getCategoriasDAO() {
        return new NeodatisCategoriasImpl();
    }

    @Override
    public ProductoDAO getProductosDAO() {
        return new NeodatisProductosImpl();
    }

}

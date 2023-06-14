import dao.AlergenosDAO;
import dao.CategoriasDAO;
import dao.ProductoDAO;
import factory.DAOFactory;

public class pruebaNEODATIS {
    static AlergenosDAO alerDAO;
    static CategoriasDAO catDAO;
    static ProductoDAO proDAO;
    
    public static void main(String[] args) {
        // NEODATIS
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        Alergenos(bd);
        Categorias(bd);
        Productos(bd);

    }

    private static void Productos(DAOFactory bd) {
    }

    private static void Categorias(DAOFactory bd) {
    }

    private static void Alergenos(DAOFactory bd) {
    }
}

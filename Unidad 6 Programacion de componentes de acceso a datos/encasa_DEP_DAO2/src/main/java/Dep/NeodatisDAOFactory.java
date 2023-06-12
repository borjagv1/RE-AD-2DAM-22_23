package Dep;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

public class NeodatisDAOFactory extends DAOFactory {
    // Definimos los parámetros de conexion a la base de datos
    static ODB odb = null;

    public NeodatisDAOFactory() {
    }
    // Creamos la conexion si no esta creada y la bbdd es Departamentos.BD
    public static ODB crearConexion() {
        if (odb == null) {
            try {
                odb = ODBFactory.open("Departamento.BD");
            } catch (Exception e) {
                System.out.println("Error al abrir la base de datos");
            }
        }
        return odb;
    }
    @Override
    public DepartamentoDao getDepartamentoDAO() {
        return new NeodatisDepartamentoImpl();
    }

}

package factory;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import dao.AlumnosDAO;
import dao.AsignaturasDAO;
import dao.CursosDAO;
import dao.EvaluacionesDAO;
import impl.NeodatisAlumnosImpl;
import impl.NeodatisAsignaturasImpl;
import impl.NeodatisCursosImpl;
import impl.NeodatisEvaluacionesImpl;

public class NeodatisDAOFactory extends DAOFactory {
    static ODB odb = null;

    public NeodatisDAOFactory() {
    }

    public static ODB crearConexion() {
        if (odb == null) {
            odb = ODBFactory.open("BDALUMCURSOS.neo");
        }
        return odb;
    }

    public static void cerrarConexion() {
        odb.close();
    }

    @Override
    public AlumnosDAO getAlumnosDAO() {
        return new NeodatisAlumnosImpl();
    }

    @Override
    public AsignaturasDAO geAsignaturasDAO() {
        return new NeodatisAsignaturasImpl();
    }

    @Override
    public CursosDAO getCursosDAO() {
        return new NeodatisCursosImpl();
    }

    @Override
    public EvaluacionesDAO getEvaluacionesDAO() {
        return new NeodatisEvaluacionesImpl();
    }

}

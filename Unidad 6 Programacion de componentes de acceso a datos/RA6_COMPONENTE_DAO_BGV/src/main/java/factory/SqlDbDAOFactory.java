package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.AlumnosDAO;
import dao.AsignaturasDAO;
import dao.CursosDAO;
import dao.EvaluacionesDAO;
import impl.SqlDbAlumnosImpl;
import impl.SqlDbAsignaturasImpl;
import impl.SqlDbCursosImpl;
import impl.SqlDbEvaluacionesImpl;

public class SqlDbDAOFactory extends DAOFactory {

    static Connection conexion = null;
    static String DRIVER = "";
    static String URLDB = "";
    static String USUARIO = "ra6";
    static String CLAVE = "ra6";

    public SqlDbDAOFactory(int BDSQL) {
        if (BDSQL == DAOFactory.MYSQL) {
            DRIVER = "com.mysql.jdbc.Driver";
            URLDB = "jdbc:mysql://localhost/ra6";
            //System.out.println("mysql");
        }      
    }

    public SqlDbDAOFactory() {
    }

    // crear la conexion
    public static Connection crearConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER); // Cargar el driver
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SqlDbDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                conexion = DriverManager.getConnection(URLDB, USUARIO, CLAVE);
            } catch (SQLException ex) {
                System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
                System.out.printf("Mensaje   : %s %n", ex.getMessage());
                System.out.printf("SQL estado: %s %n", ex.getSQLState());
                System.out.printf("Cód error : %s %n", ex.getErrorCode());
            }
        }
        return conexion;
    }
    public static void cerrarConexion() {
        try {
            // cerrar la conexion
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("HA OCURRIDO UNA EXCEPCIÓN al cerrar la conexión:%n");
            System.out.printf("Mensaje   : %s %n", ex.getMessage());
            System.out.printf("SQL estado: %s %n", ex.getSQLState());
            System.out.printf("Cód error : %s %n", ex.getErrorCode());
        }
    }



    @Override
    public AlumnosDAO getAlumnosDAO() {
        return new SqlDbAlumnosImpl();
    }

    @Override
    public AsignaturasDAO geAsignaturasDAO() {
        return new SqlDbAsignaturasImpl();
        }

    @Override
    public CursosDAO getCursosDAO() {
        return new SqlDbCursosImpl();
    }

    @Override
    public EvaluacionesDAO getEvaluacionesDAO() {
        return new SqlDbEvaluacionesImpl();    
    }

}

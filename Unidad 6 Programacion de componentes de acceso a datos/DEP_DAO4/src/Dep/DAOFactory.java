package Dep;

public abstract class DAOFactory {
  // Bases de datos soportadas
  public static final int MYSQL = 1;  
  public static final int NEODATIS = 2;
  public static final int ORACLE = 3;  
  public static final int SQLITE = 4;  
 
  public abstract DepartamentoDAO getDepartamentoDAO();
  public abstract EmpleadoDAO getEmpleadoDAO(); 
  
  public static DAOFactory getDAOFactory(int bd) {  
    switch (bd) {
      case MYSQL:          
           return new SqlDbDAOFactory(MYSQL);     
      case NEODATIS:       
            return new NeodatisDAOFactory();
      case ORACLE    : 
            return new SqlDbDAOFactory(ORACLE);
      case SQLITE    :           
            return new SqlDbDAOFactory(SQLITE);
      default           : 
          return null;
    }
  }
}

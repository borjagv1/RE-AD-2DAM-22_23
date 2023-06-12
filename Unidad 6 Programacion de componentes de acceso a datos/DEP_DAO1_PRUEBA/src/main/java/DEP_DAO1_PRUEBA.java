import Dep.Departamento;
import Dep.DepartamentoImpl;
import Dep.DepartamentoDao;

public class DEP_DAO1_PRUEBA {
    public static void main(String[] args) {
        DepartamentoDao depDAO = new DepartamentoImpl();

      //INSERTAR
      Departamento dep1 = new Departamento(17, "NÓMINAS", "SEVILLA");
      depDAO.InsertarDep(dep1);
       
      //CONSULTAR
      Departamento dep2 = depDAO.ConsultarDep(17);
      System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n",
                   dep2.getDeptno(),dep2.getDnombre(), dep2.getLoc());
        
      //MODIFICAR 
      dep2.setDnombre("nuevonom");
      dep2.setLoc("nuevaloc");
      depDAO.ModificarDep(17, dep2);
                    
      //ELIMINAR
      depDAO.EliminarDep(17);      
        
    }
}

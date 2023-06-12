package Dep;

import java.util.ArrayList;

public interface DepartamentoDAO {    
    public boolean InsertarDep(Departamento dep);
    public boolean EliminarDep(int deptno); 
    public boolean ModificarDep(int deptno, Departamento dep);
    public Departamento ConsultarDep(int deptno);   
    public ArrayList ObtenerDepartamentos();
}

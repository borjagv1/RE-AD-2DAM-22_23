package Dep;

public interface DepartamentoDao {
    public boolean InsertarDep(Departamento dep);

    public boolean ModificarDep(int deptno, Departamento dep);

    public boolean EliminarDep(int deptno);

    public Departamento ConsultarDep(int deptno);
}

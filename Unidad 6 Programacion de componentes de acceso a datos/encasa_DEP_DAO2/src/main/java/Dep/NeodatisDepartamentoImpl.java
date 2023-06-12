package Dep;

import org.neodatis.odb.ODB;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class NeodatisDepartamentoImpl implements DepartamentoDao {
    static ODB bd;

    public NeodatisDepartamentoImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarDep(Departamento dep) {
        // Metodo para insertar en BBDD neodatis
        bd.store(dep);
        bd.commit();
        System.out.println("Departamento: " + dep.getDeptno() + ", insertado correctamente");

        return true;
    }

    @Override
    public boolean ModificarDep(int deptno, Departamento dep) {
        // Método para modificar en BBDD neodatis
        boolean valor = false;
        IQuery query = new CriteriaQuery(Departamento.class,
                org.neodatis.odb.core.query.criteria.Where.equal("deptno", deptno));
        org.neodatis.odb.Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento dep2 = objetos.getFirst();
            dep2.setDnombre(dep.getDnombre());
            dep2.setLoc(dep.getLoc());
            bd.store(dep2);
            bd.commit();
            valor = true;
            bd.commit();
            System.out.println("Departamento: " + dep.getDeptno() + ", modificado correctamente: " + dep.getDnombre()
                    + " " + dep.getLoc());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No existe el departamento: " + deptno);
        }

        return valor;
    }

    @Override
    public boolean EliminarDep(int deptno) {
        boolean valor = false;
        // Método para eliminar en BBDD neodatis un objeto departamento por su deptno
        IQuery query = new CriteriaQuery(Departamento.class,
                org.neodatis.odb.core.query.criteria.Where.equal("deptno", deptno));
        org.neodatis.odb.Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento dep = objetos.getFirst();
            bd.delete(dep);
            bd.commit();
            valor = true;
            System.out.println("Departamento: " + dep.getDeptno() + ", eliminado correctamente");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No existe el departamento: " + deptno);
        }
        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        // Método para consultar en BBDD neodatis un objeto departamento por su deptno
        IQuery query = new CriteriaQuery(Departamento.class,
                org.neodatis.odb.core.query.criteria.Where.equal("deptno", deptno));
        org.neodatis.odb.Objects<Departamento> objetos = bd.getObjects(query);
        Departamento dep = new Departamento();

        if (objetos != null) {
            try {
                dep = objetos.getFirst();
                System.out.println("Departamento: " + dep.getDeptno() + ", consultado correctamente");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No existe el departamento: " + deptno);
                dep.setDeptno(deptno);
                dep.setDnombre("No existe");
                dep.setLoc("No existe");
            }
        }
        return dep;
    }

}

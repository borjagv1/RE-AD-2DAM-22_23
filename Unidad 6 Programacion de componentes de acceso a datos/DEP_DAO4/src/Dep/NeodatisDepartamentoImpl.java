/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.util.ArrayList;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author mjesus
 */
public class NeodatisDepartamentoImpl implements DepartamentoDAO {

    static ODB bd;

    public NeodatisDepartamentoImpl() {
        
        bd = NeodatisDAOFactory.crearConexion();
        
    }

    @Override
    public boolean InsertarDep(Departamento dep) {
         boolean valor = false;
        if (existeDepartamento(dep.getDeptno())) {
             System.out.printf("Departamento: %d Ya existe, no se insertará %n", dep.getDeptno());
        } else {
            bd.store(dep);
            bd.commit();
            valor=true;
              System.out.printf("Departamento: %d Insertado %n",dep.getDeptno());
        }
        return valor;
       
    }

    @Override
    public boolean EliminarDep(int deptno) {       
        boolean valor = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento depart = (Departamento) objetos.getFirst();
            bd.delete(depart);         
            bd.commit();
            valor = true;
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Departamento a eliminar: %d No existe%n",deptno);
        }

        return valor;
    }

    @Override
    public boolean ModificarDep(int deptno, Departamento dep) {
        boolean valor = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento depart = (Departamento) objetos.getFirst();
            depart.setDnombre(dep.getDnombre());
            depart.setLoc(dep.getLoc());
            bd.store(depart); // actualiza el objeto 
            valor = true;
            bd.commit();
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Departamento: %d No existe%n",deptno);
        }

        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        Departamento dep = new Departamento();
        if (objetos != null) {
            try {
                dep = (Departamento) objetos.getFirst();
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Departamento: %d No existe%n",deptno);
                dep.setDnombre("no existe");
                dep.setDeptno(deptno);
                dep.setLoc("no existe");
            }
        }
        return dep;
    }
   
     @Override
     public ArrayList ObtenerDepartamentos(){
        ArrayList departamentos = new ArrayList();
        Objects dep = bd.getObjects(Departamento.class);
        
         while(dep.hasNext()) {
             departamentos.add(dep.next());           
            }        
        return departamentos;        
    }

    private boolean existeDepartamento(int deptno) {
      boolean existe = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        while (objetos.hasNext()) {
            Departamento de = (Departamento) objetos.next();
            if (de.getDeptno() == deptno) {
                existe = true;
            }
        }
        return existe;
    }
}

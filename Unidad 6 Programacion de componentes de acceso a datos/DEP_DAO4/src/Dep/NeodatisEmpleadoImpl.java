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
class NeodatisEmpleadoImpl implements EmpleadoDAO {

    static ODB bd;

    public NeodatisEmpleadoImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarEmp(Empleado emp) {
        //comprobar si existe el empleado
		
        //comprobar si departamento existe
        //comprobar si dir existe
        if (!ExisteDepartamento(emp.getDeptno())) {
            System.out.printf("Departamento: %d INEXISTENTE%n", emp.getDeptno());
            return false;
        }
        if (!existeEmpleado(emp.getDir())) {
            System.out.printf("Empleado director: %d EXISTENTE%n", emp.getDir());
            return false;
        }
				
        boolean valor = false;
        if (existeEmpleado(emp.getEmpno())) {
             System.out.printf("Empleado: %d Ya existe, no se insertará %n", emp.getEmpno());
        } else {
            bd.store(emp);
            bd.commit();
            valor=true;
            System.out.printf("Empleado: %d Insertado %n", emp.getEmpno());
        }
        return valor;
    }

    @Override
    public boolean EliminarEmp(int empno) {
        boolean valor = false;
        //comprobar si es director.
        if (empleadosAcargo(empno) == 0) {
            IQuery query = new CriteriaQuery(Empleado.class, Where.equal("empno", empno));
            Objects<Empleado> objetos = bd.getObjects(query);
            try {
                Empleado depart = (Empleado) objetos.getFirst();
                bd.delete(depart);
                bd.commit();
                valor = true;
                System.out.printf("Empleado %d eliminado %n", empno);
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Empleado a eliminar: %d No existe%n", empno);
            }
        }
        return valor;
    }

    @Override
    public boolean ModificarEmp(int empno, Empleado emp) {

	  if (!ExisteDepartamento(emp.getDeptno())) {
            System.out.printf("Departamento: %d INEXISTENTE%n", emp.getDeptno());
            return false;
        }
        boolean valor = false;
        //comprobar si dir existe
        if (existeEmpleado(emp.getDir())) {
            IQuery query = new CriteriaQuery(Empleado.class, Where.equal("empno", empno));
            Objects<Empleado> objetos = bd.getObjects(query);
            try {
                Empleado emple = (Empleado) objetos.getFirst();

                emple.setApellido(emp.getApellido());
                emple.setComision(emp.getComision());
                emple.setDeptno(emp.getDeptno());
                emple.setDir(emp.getDir());
                emple.setEmpno(emp.getEmpno());
                emple.setFechaAlt(emp.getFechaAlt());
                emple.setOficio(emp.getOficio());
                emple.setSalario(emp.getSalario());

                bd.store(emple); // actualiza el objeto 
                valor = true;
                bd.commit();
                System.out.printf("Empleado %d modificado%n", empno);
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Empleado: %d No existe%n", empno);
            }
        } else {
            System.out.printf("El director %d no existe, no se modificará el Empleado %d %n", emp.getDir(), empno);
            return valor;
        }

        return valor;
    }

	 private boolean ExisteDepartamento(int deptno) {
        boolean existe = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        while (objetos.hasNext()) {
            Departamento emp = (Departamento) objetos.next();
            if (emp.getDeptno() == deptno) {
                existe = true;
            }
        }
        return existe;
    }
    @Override
    public Empleado ConsultarEmp(int empno) {
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("empno", empno));
        Objects<Empleado> objetos = bd.getObjects(query);
        Empleado dep = new Empleado();
        if (objetos != null) {
            try {
                dep = (Empleado) objetos.getFirst();
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Empleado: %d No existe%n", empno);
                dep.setApellido("no existe");
                dep.setEmpno(empno);
                dep.setOficio("no existe");
                dep.setSalario(0f);
                dep.setDeptno(0);
            }
        }
        return dep;
    }

    @Override
    public ArrayList ObtenerEmpleados() {
        ArrayList empleados = new ArrayList();
        Objects dep = bd.getObjects(Empleado.class);

        while (dep.hasNext()) {
            empleados.add(dep.next());
        }
        return empleados;
    }

    @Override
    public ArrayList ObtenerEmpleadosDep(int deptno) {
        ArrayList empleados = new ArrayList();
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("deptno", deptno));
        Objects dep = bd.getObjects(query);

        while (dep.hasNext()) {
            empleados.add(dep.next());
        }
        return empleados;

    }

    private int empleadosAcargo(int empno) {
        int c = 0;
        Objects dep = bd.getObjects(Empleado.class);
        while (dep.hasNext()) {
            Empleado emp = (Empleado) dep.next();
            if (emp.getDir() == empno) {
                c++;
            }
        }
        return c;
    }

    private boolean existeEmpleado(int empno) {
        boolean existe = false;
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("empno", empno));
        Objects<Empleado> objetos = bd.getObjects(query);
        while (objetos.hasNext()) {
            Empleado emp = (Empleado) objetos.next();
            if (emp.getEmpno() == empno) {
                existe = true;
            }
        }

        return existe;
    }

}

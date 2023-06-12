/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dep_dao4_prueba;

import Dep.Departamento;
import Dep.Empleado;
import java.sql.Date;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;


/**
 *
 * @author mjesus
 */
public class LlenaBDNeodatis {

    public static void main(String[] args) {
        ODB bd = ODBFactory.open("Departamento.BD");

        Departamento d1 = new Departamento(10, "CONTABILIDAD", "SEVILLA");
        Departamento d2 = new Departamento(20, "INVESTIGACIÓN", "MADRID");
        Departamento d3 = new Departamento(30, "VENTAS", "BARCELONA");
        Departamento d4 = new Departamento(40, "PRODUCCIÓN", "BILBAO");

        bd.store(d1);
        bd.store(d2);
        bd.store(d3);
        bd.store(d4);

        // EMPLEADOS        
        //Obtener la fecha actual 
        java.sql.Date fec = getCurrentDate();

        //(int empno, String apellido, String oficio, int dir, Date fechaAlt,
        //Float salario, Float comision, int deptno) 
        
        Empleado e1 = new Empleado(7369, "SÁNCHEZ", "EMPLEADO",
                7566, fec, 1040.0f, 0.0f, 10);
        Empleado e2 = new Empleado(7499, "ARROYO", "VENDEDOR",
                7782, fec, 1500.0f, 390.0f, 20);
        Empleado e3 = new Empleado(7521, "SALA", "VENDEDOR",
                7782, fec, 1625.0f, 650.0f, 20);
        Empleado e4 = new Empleado(7566, "JIMÉNEZ", "DIRECTOR",
                7839, fec, 2900.0f, 0.0f, 30);
        Empleado e5 = new Empleado(7782, "CEREZO", "DIRECTOR",
                7839, fec, 2885.0f, 0.0f, 30);
        Empleado e6 = new Empleado(7839, "REY", "PRESIDENTE",
                0, fec, 4100.0f, 0.0f, 30);

        bd.store(e1);
        bd.store(e2);
        bd.store(e3);
        bd.store(e4);
        bd.store(e5);
        bd.store(e6);
        
        bd.commit();
        
        
        bd.close();
        
    }

    //Obtener la fecha actual 

    private static java.sql.Date getCurrentDate() {
        java.util.Date hoy = new java.util.Date();
        return new java.sql.Date(hoy.getTime());
    }

}

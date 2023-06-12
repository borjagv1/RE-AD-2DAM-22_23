/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.util.ArrayList;

/**
 *
 * @author mjesus
 */

public interface EmpleadoDAO {    
    public boolean InsertarEmp(Empleado emp);
    public boolean EliminarEmp(int empno); 
    public boolean ModificarEmp(int empno,  Empleado emp);
    public Empleado ConsultarEmp(int empno);   
    public ArrayList ObtenerEmpleados();
    public ArrayList ObtenerEmpleadosDep(int deptno);
}


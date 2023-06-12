/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dep_dao4_prueba;

import Dep.DAOFactory;
import Dep.Departamento;
import Dep.DepartamentoDAO;
import Dep.Empleado;
import Dep.EmpleadoDAO;
import java.util.ArrayList;

/**
 *
 * @author mjesus
 */
public class Actividad6_4 {

    static EmpleadoDAO empDAO;
    static DepartamentoDAO depDAO;

    public static void main(String[] args) {

        //NEODATIS
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        empDAO = bd.getEmpleadoDAO();
        depDAO=bd.getDepartamentoDAO();

        //consulta
        Empleado emp = empDAO.ConsultarEmp(7499);
        VerEmpleado(emp);

        //inserción       
        emp = new Empleado(7001, "RAMOS", "INFORMÁTICO",
                7499, getCurrentDate(), 2040.0f, 0.0f, 20);
        empDAO.InsertarEmp(emp);

        //insercion de uno que existe
        empDAO.InsertarEmp(emp);

        //MODIFICACIÓN
        emp = new Empleado(7001, "MARTIN", "PROFESORA",
                7839, getCurrentDate(), 2040.0f, 0.0f, 20);
        empDAO.ModificarEmp(7001, emp);
        VerEmpleado(empDAO.ConsultarEmp(7001));

        //modificar empleado con dir que no existe
        emp = new Empleado(7001, "MARTINnn", "PROFESORAaa",
                79, getCurrentDate(), 3040.0f, 0.0f, 20);
        System.out.println("Modifico empleado 7001 => " + empDAO.ModificarEmp(7001, emp));
        VerEmpleado(empDAO.ConsultarEmp(7001));
        ListarTodos();

        //BORADO
        empDAO.EliminarEmp(7001);
        emp = empDAO.ConsultarEmp(7001);
        VerEmpleado(emp);

        //BORADO no me debe dejar
        empDAO.EliminarEmp(7839);
        emp = empDAO.ConsultarEmp(7839);
        VerEmpleado(emp);
        ListarTodos();

        //departamento, inserto uno que exista:
        Departamento d1 = new Departamento(10, "CONTABILIDAD", "SEVILLA");
        System.out.println("Inserto departamento 10 => "+ depDAO.InsertarDep(d1)  );
              
        System.out.println("----------------------------------MYSQL--------------");

        //MYSQL--------------------------------------------
        bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        empDAO = bd.getEmpleadoDAO();

        emp = empDAO.ConsultarEmp(7499);
        VerEmpleado(emp);

        //inserción       
        emp = new Empleado(7000, "RAMOS", "INFORMÁTICO",
                7499, getCurrentDate(), 2040.0f, 0.0f, 20);
        empDAO.InsertarEmp(emp);
        VerEmpleado(emp);

        //BORRADO no me debe dejar
        System.out.println("ELIMINO 7839 => " + empDAO.EliminarEmp(7839));
        emp = empDAO.ConsultarEmp(7839);
        VerEmpleado(emp);

        //modificar empleado con dir que no existe
        emp = new Empleado(7934, "Muñoz", "INFORMÁTICO",
                99, getCurrentDate(), 2040.0f, 0.0f, 20);
        System.out.println("Modifico empleado 7934 => " + empDAO.ModificarEmp(7934, emp));
        VerEmpleado(empDAO.ConsultarEmp(7934));

    }

    private static void VerEmpleado(Empleado emp) {
        System.out.printf("Empleado: %d, Apellido: %s, Oficio: %s, Salario: %.2f, "
                + "Departamento: %d, Director %d %n", emp.getEmpno(), emp.getApellido(),
                emp.getOficio(), emp.getSalario(), emp.getDeptno(), emp.getDir());

    }

    private static java.sql.Date getCurrentDate() {
        java.util.Date hoy = new java.util.Date();
        return new java.sql.Date(hoy.getTime());
    }

    //listar todos los EMPLEADOS
    private static void ListarTodos() {
        ArrayList lista = empDAO.ObtenerEmpleados();
        System.out.println("---------------------------------------------------");
        System.out.println("LISTA DE EMPLEADOS:");
        for (int i = 0; i < lista.size(); i++) {
            Empleado emp = (Empleado) lista.get(i);
            VerEmpleado(emp);
        }
        System.out.println("---------------------------------------------------");

    }
}

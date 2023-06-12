
package dep_dao4_prueba;

import Dep.DAOFactory;
import Dep.Departamento;
import Dep.DepartamentoDAO;
import Dep.Empleado;
import Dep.EmpleadoDAO;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mjesus
 */
public class Ejercicio6_7 {

    static DAOFactory bd = null;

    public static void main(String[] args) {
        System.out.print("Introduce el departamento de los empleados a consultar: ");
        Scanner sc = new Scanner(System.in);
        int entero = sc.nextInt();

        System.out.printf("EMPLEADOS DEL DEPARTAMENTO: %d %n", entero);
        //NEODATIS
        bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        Proceso(entero);

        //MYSQL
        bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        Proceso(entero);

    }

    private static void Proceso(int entero) {
        EmpleadoDAO empleDAO = bd.getEmpleadoDAO();
        ArrayList lista = empleDAO.ObtenerEmpleadosDep(entero);
        System.out.println("-----------------------------------------------");

        DepartamentoDAO depDAO = bd.getDepartamentoDAO();
        VerDepartamento(depDAO.ConsultarDep(entero));
        System.out.println("Lista de Empleados");
        ListarEmpleados(lista);
        
    }

    //LISTA EL ARRAY
    private static void ListarEmpleados(ArrayList lista) {
        if (lista != null) {
            for (int i = 0; i < lista.size(); i++) {
                Empleado d = (Empleado) lista.get(i);
                System.out.printf("Empno: %d, Apellido: %s, Oficio: %s, Director: %d%n",
                        d.getEmpno(), d.getApellido(), d.getOficio(), d.getDir());

                EmpleadoDAO dirDAO = bd.getEmpleadoDAO();
                Empleado director = dirDAO.ConsultarEmp(d.getDir());
                System.out.printf("       Director %d, Apellido: %s%n",
                        director.getEmpno(), director.getApellido());
            }
        } else {
            System.out.println("NO HAY empleados");
        }
    }

    private static void VerDepartamento(Departamento dep) {
        System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n", dep.getDeptno(),
                dep.getDnombre(), dep.getLoc());
    }

}

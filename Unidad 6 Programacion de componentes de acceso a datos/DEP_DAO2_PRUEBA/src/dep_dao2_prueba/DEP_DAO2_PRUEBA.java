package dep_dao2_prueba;

import Dep.DAOFactory;
import Dep.Departamento;
import Dep.DepartamentoDAO;
import java.util.Scanner;

public class DEP_DAO2_PRUEBA {

    public static void main(String[] args) {
        System.out.println("------------------------------");
        System.out.println("PRUEBA MYSQL");

        pruebamysql();
        System.out.println("------------------------------");
        System.out.println("PRUEBA NEODATIS");

        pruebaneodatis();

    }

    public static void pruebaneodatis() {
        
        
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();

        //crear departamento
        Departamento dep = new Departamento(17, "NÓMINAS", "SEVILLA");
        depDAO.InsertarDep(dep);
        
        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar departamentos leidos por teclado
        while (entero > 0) {
            System.out.println("Escribe Departamento (0 sale): ");
            entero = sc.nextInt();
            dep = depDAO.ConsultarDep(entero);
            System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n", dep.getDeptno(),
                    dep.getDnombre(), dep.getLoc());
        }
    }

    public static void pruebamysql() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();

        //crear departamento
        Departamento dep = new Departamento(17, "NÓMINAS", "SEVILLA");
        depDAO.InsertarDep(dep);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar departamentos leidos por teclado
        while (entero > 0) {
            System.out.println("Escribe Departamento (0 sale): ");
            entero = sc.nextInt();
            dep = depDAO.ConsultarDep(entero);
            System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n", dep.getDeptno(),
                    dep.getDnombre(), dep.getLoc());
        }
    }

}


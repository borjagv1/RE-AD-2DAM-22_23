
/**
 * encasa_DEP_DA2_PRUEBA_sqlDB
 */

import java.util.Scanner;

import Dep.*;

public class encasa_DEP_DAO2_PRUEBA_Neodatis {

    public static void main(String[] args) {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        DepartamentoDao depDao = bd.getDepartamentoDAO();
        // Crear departamento
        Departamento dep = new Departamento(1, "Informatica", "Madrid");
        depDao.InsertarDep(dep);

        // Visualizar departamentos leídos por teclado:

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el numero de departamento: ");

        int entero = sc.nextInt();

        while (entero != 0) {
            dep = depDao.ConsultarDep(entero);
            System.out.println("Numero de departamento: " + dep.getDeptno() + " Nombre: " + dep.getDnombre()
                    + " Localidad: " + dep.getLoc());
            System.out.println("Introduce el numero de departamento: ");
            entero = sc.nextInt();
        }
        sc.close();
    }
}

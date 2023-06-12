/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dep_dao4_prueba;

import Dep.DAOFactory;
import Dep.Departamento;
import Dep.DepartamentoDAO;
import java.util.ArrayList;

/**
 *
 * @author mjesus
 */
//muestra los departamentos
public class Ejercicio6_6 {

    public static void main(String[] args) {

        //NEODATIS
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();
        ArrayList lista = depDAO.ObtenerDepartamentos();
        System.out.println("-----------------------------------------------");
        System.out.println("Departamento en Neodatis");
        ListarDepartamentos(lista);

        //MYSQL
        bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        depDAO = bd.getDepartamentoDAO();
        lista = depDAO.ObtenerDepartamentos();
        System.out.println("-----------------------------------------------");
        System.out.println("Departamento en MySQL");
        ListarDepartamentos(lista);

    }

    //LISTA EL ARRAY
    private static void ListarDepartamentos(ArrayList lista) {
        if (lista != null) {
            for (int i = 0; i < lista.size(); i++) {
                Departamento d = (Departamento) lista.get(i);
                System.out.printf("Dep: %s, Nombre: %s, Localidad: %s%n",
                        d.getDeptno(), d.getDnombre(), d.getLoc());
            }
        } else {
            System.out.println("NO HAY DEPARTAMENTOS");
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlDbDAOFactory extends DAOFactory {

    static Connection conexion=null;
    static String DRIVER = "";
    static String URLDB = "";
    static String USUARIO = "unidad6";
    static String CLAVE = "unidad6";
   
    public SqlDbDAOFactory(int BDSQL) {
        if (BDSQL == DAOFactory.MYSQL) {
            DRIVER = "com.mysql.jdbc.Driver";
            URLDB = "jdbc:mysql://localhost/unidad6";            
            System.out.println("mysql");
        }
        if (BDSQL == DAOFactory.ORACLE) {
            DRIVER = "oracle.jdbc.driver.OracleDriver";
            URLDB = "jdbc:oracle:thin:@localhost:1521:XE";
            System.out.println("oracle");
        }
         if (BDSQL == DAOFactory.SQLITE) {
            DRIVER = "org.sqlite.JDBC";
            URLDB = "jdbc:sqlite:D:/DB/SQLITE/ejemplo.db";
            USUARIO = "";
            CLAVE = "";
            System.out.println("sqlite");
        }      
    }

    // crear la conexion
    public static Connection crearConexion() {
    if (conexion == null) {
       try {
           Class.forName(DRIVER); // Cargar el driver
       } catch (ClassNotFoundException ex) {
             Logger.getLogger(SqlDbDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
       }

       try {
           conexion = DriverManager.getConnection(URLDB, USUARIO, CLAVE);
       } catch (SQLException ex) {
           System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
           System.out.printf("Mensaje   : %s %n", ex.getMessage());
           System.out.printf("SQL estado: %s %n", ex.getSQLState());
           System.out.printf("Cód error : %s %n", ex.getErrorCode());
       }
    }
    return conexion;
}

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new SqlDbDepartamentoImpl();
    }
}

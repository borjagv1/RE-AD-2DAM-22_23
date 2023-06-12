/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mjesus
 */
public class SqlDbDepartamentoImpl implements DepartamentoDAO {

    Connection conexion;

    public SqlDbDepartamentoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    public boolean InsertarDep(Departamento dep) {
        boolean valor = false;
        String sql = "INSERT INTO departamentos VALUES(?, ?, ?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, dep.getDeptno());
            sentencia.setString(2, dep.getDnombre());
            sentencia.setString(3, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d insertado%n", dep.getDeptno());
            }
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public boolean EliminarDep(int deptno) {
        boolean valor = false;
        String sql = "DELETE FROM departamentos WHERE dept_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d eliminado%n", deptno);
            }
            sentencia.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public boolean ModificarDep(int num, Departamento dep) {
        boolean valor = false;
        String sql = "UPDATE departamentos SET dnombre= ?, loc = ? WHERE dept_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(3, num);
            sentencia.setString(1, dep.getDnombre());
            sentencia.setString(2, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d modificado%n", num);
            }
            sentencia.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        String sql = "SELECT dept_no, dnombre, loc FROM departamentos WHERE dept_no =  ?";
        PreparedStatement sentencia;
        Departamento dep = new Departamento();
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                dep.setDeptno(rs.getInt("dept_no"));
                dep.setDnombre(rs.getString("dnombre"));
                dep.setLoc(rs.getString("loc"));
            } else {
                System.out.printf("Departamento: %d No existe%n", deptno);
            }

            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return dep;
    }

    private void MensajeExcepcion(SQLException e) {
        System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
        System.out.printf("Mensaje   : %s %n", e.getMessage());
        System.out.printf("SQL estado: %s %n", e.getSQLState());
        System.out.printf("Cód error : %s %n", e.getErrorCode());
    }

    @Override
    public ArrayList<Departamento> TodosDepartamentos() {
        ArrayList lista = new ArrayList();
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            String sql = "SELECT * FROM departamentos";
            ResultSet resul = sentencia.executeQuery(sql);
            
            while (resul.next()) {
                Departamento dep = new Departamento(resul.getInt(1), resul.getString(2), resul.getString(3));
                lista.add(dep);
            }

            resul.close(); // Cerrar ResultSet
            sentencia.close(); // Cerrar Statement

        } catch (SQLException ex) {
            MensajeExcepcion(ex);
        }

        return lista;

    }
}

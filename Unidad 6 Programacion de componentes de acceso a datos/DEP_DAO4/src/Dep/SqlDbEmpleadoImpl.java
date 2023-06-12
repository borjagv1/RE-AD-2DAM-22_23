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

/**
 *
 * @author mjesus
 */
class SqlDbEmpleadoImpl implements EmpleadoDAO {

    Connection conexion;

    public SqlDbEmpleadoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarEmp(Empleado emp) {
        boolean valor = false;

        String sql = "INSERT INTO empleados "
                + "(emp_no, apellido, oficio, salario, dept_no, dir, comision, fecha_alt) VALUES "
                + "( ? , ?, ?, ?, ?, ?, ? , ? )";

        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, emp.getEmpno());
            sentencia.setString(2, emp.getApellido());
            sentencia.setString(3, emp.getOficio());
            sentencia.setFloat(4, emp.getSalario());
            sentencia.setInt(5, emp.getDeptno());
            sentencia.setInt(6, emp.getDir());
            sentencia.setFloat(7, emp.getComision());
            sentencia.setDate(8, emp.getFechaAlt());

            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d insertado%n", emp.getEmpno());
            }
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;

    }

    @Override
    public boolean EliminarEmp(int empno) {
        boolean valor = false;       
        if (empleadosAcargo(empno) == 0) {
            String sql = "DELETE FROM empleados WHERE emp_no = ? ";
            PreparedStatement sentencia;
            try {
                sentencia = conexion.prepareStatement(sql);
                sentencia.setInt(1, empno);
                int filas = sentencia.executeUpdate();
                //System.out.printf("Filas eliminadas: %d%n", filas);
                if (filas > 0) {
                    valor = true;
                    System.out.printf("Empleado %d eliminado%n", empno);
                }
                sentencia.close();
            } catch (SQLException e) {
                MensajeExcepcion(e);
            }
        }
        return valor;
    }

    @Override
    public boolean ModificarEmp(int empno, Empleado emp) {
        boolean valor = false;
        //comprobar si el director existe
        if(!existeDirector(emp.getDir())){
              System.out.printf("El director %d no existe, no se modificará el Empleado %d %n", emp.getDir(),empno);
              return valor;
        }
        String sql = "UPDATE empleados SET apellido= ?, oficio = ? , salario = ? ,"
                + "dept_no = ? , dir = ?, comision = ?, fecha_alt = ? "
                + "WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(8, empno);
            sentencia.setString(1, emp.getApellido());
            sentencia.setString(2, emp.getOficio());
            sentencia.setFloat(3, emp.getSalario());
            sentencia.setInt(4, emp.getDeptno());
            sentencia.setInt(5, emp.getDir());
            sentencia.setFloat(6, emp.getComision());
            sentencia.setDate(7, emp.getFechaAlt());

            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d modificado%n", empno);
            }
            sentencia.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public Empleado ConsultarEmp(int empno) {
        Empleado emp = new Empleado();
        String sql = "SELECT * from empleados WHERE emp_no = ?";
        PreparedStatement sentencia;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, empno);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                emp.setApellido(rs.getString("apellido"));
                emp.setComision(rs.getFloat("comision"));
                emp.setDeptno(rs.getInt("dept_no"));
                emp.setDir(rs.getInt("dir"));
                emp.setEmpno(rs.getInt("emp_no"));
                emp.setFechaAlt(rs.getDate("fecha_alt"));
                emp.setOficio(rs.getString("oficio"));
                emp.setSalario(rs.getFloat("salario"));

            } else {
                System.out.printf("Empleado: %d No existe%n", empno);
            }
            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }

        return emp;
    }

    private void MensajeExcepcion(SQLException e) {
        System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
        System.out.printf("Mensaje   : %s %n", e.getMessage());
        System.out.printf("SQL estado: %s %n", e.getSQLState());
        System.out.printf("Cód error : %s %n", e.getErrorCode());
    }

    @Override
    public ArrayList ObtenerEmpleados() {
        ArrayList empleados = new ArrayList();
        try {
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT * FROM empleados";
            ResultSet resul = sentencia.executeQuery(sql);
            while (resul.next()) {
                Empleado d = new Empleado(
                        resul.getInt("emp_no"),
                        resul.getString("apellido"),
                        resul.getString("oficio"),
                        resul.getInt("dir"),
                        resul.getDate("fecha_alt"),
                        resul.getFloat("salario"),
                        resul.getFloat("comision"),
                        resul.getInt("dept_no"));
                empleados.add(d);
            }
            sentencia.close();
            resul.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return empleados;
    }

    @Override
    public ArrayList ObtenerEmpleadosDep(int deptno) {
        String sql = "SELECT * from empleados WHERE dept_no = ?";
        PreparedStatement sentencia;
        ArrayList empleados = new ArrayList();
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            ResultSet resul = sentencia.executeQuery();
            while (resul.next()) {
                Empleado d = new Empleado(
                        resul.getInt("emp_no"),
                        resul.getString("apellido"),
                        resul.getString("oficio"),
                        resul.getInt("dir"),
                        resul.getDate("fecha_alt"),
                        resul.getFloat("salario"),
                        resul.getFloat("comision"),
                        resul.getInt("dept_no"));
                empleados.add(d);
            }
            sentencia.close();
            resul.close();
        } catch (SQLException e) {
            MensajeExcepcion(e);
        }
        return empleados;

    }
    //comprueba si el empleado tiene empleados a cargo
    private int empleadosAcargo(int empno) {
        int n = 0;
        String sql = "SELECT COUNT(emp_no) FROM EMPLEADOS WHERE dir= ?";
        PreparedStatement sentencia;
        ResultSet rs ;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, empno);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                n = rs.getInt(1);
            }

            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            System.out.println("HA OCURRIDO UNA EXCEPCIÓN:");
            System.out.println("Mensaje:    " + e.getMessage());
            System.out.println("SQL estado: " + e.getSQLState());
            System.out.println("Cód error:  " + e.getErrorCode());
        }

        return n;

    }

    private boolean existeDirector(int dir) {     
       boolean existe=false;
        String sql = "SELECT * from empleados WHERE emp_no = ?";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, dir);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
               existe=true;
            } else {
                System.out.printf("Director: %d No existe%n", dir);
            }
            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            MensajeExcepcion(e);
        }

        return existe;
    }

}

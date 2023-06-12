package Dep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlDbDepartamentoImpl implements DepartamentoDao {
    // Definimos los parámetros de conexion a la base de datos
    Connection conexion;

    public SqlDbDepartamentoImpl() {
        // Creamos la conexion a partir del sqlDbDAOFactory
        conexion = SqlDbDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarDep(Departamento dep) {
        // Método para insertar en BBDD mysql
        boolean valor = false;
        // Creamos la sentencia
        String sql = "INSERT INTO DEPARTAMENTOS VALUES(?,?,?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            // Asignamos los valores a la sentencia
            sentencia.setInt(1, dep.getDeptno());
            sentencia.setString(2, dep.getDnombre());
            sentencia.setString(3, dep.getLoc());
            // Ejecutamos la sentencia
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                valor = true;
                System.out.println("Departamento: " + dep.getDeptno() + ", insertado correctamente");
            }
            // Cerramos la sentencia
            sentencia.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar el departamento: " + dep.getDeptno());
        }

        return valor;

    }

    @Override
    public boolean ModificarDep(int deptno, Departamento dep) {
        boolean valor = false;
        // Método para modificar en BBDD mysql
        String sql = "UPDATE DEPARTAMENTOS SET DNOMBRE=?, LOC=? WHERE DEPT_NO=?";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            // Asignamos los valores a la sentencia
            sentencia.setString(1, dep.getDnombre());
            sentencia.setString(2, dep.getLoc());
            sentencia.setInt(3, deptno);
            // Ejecutamos la sentencia
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                valor = true;
                System.out.println("Departamento: " + dep.getDeptno() + ", modificado correctamente: "
                        + dep.getDnombre() + " " + dep.getLoc());
            }
            // Cerramos la sentencia
            sentencia.close();
        } catch (SQLException e) {
            System.out.println("Error al modificar el departamento: " + deptno);
        }

        return valor;
    }

    @Override
    public boolean EliminarDep(int deptno) {
        boolean valor = false;
        // Método para eliminar en BBDD mysql un objeto departamento por su deptno
        String sql = "DELETE FROM DEPARTAMENTOS WHERE DEPT_NO=?";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            // Asignamos los valores a la sentencia
            sentencia.setInt(1, deptno);
            // Ejecutamos la sentencia
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                valor = true;
                System.out.println("Departamento: " + deptno + ", eliminado correctamente");
            }
            // Cerramos la sentencia
            sentencia.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar el departamento: " + deptno);
        }

        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        // Método para consultar en BBDD mysql un objeto departamento por su deptno
        String sql = "SELECT * FROM DEPARTAMENTOS WHERE DEPT_NO=?";
        PreparedStatement sentencia;
        Departamento dep = new Departamento();
        try {
            sentencia = conexion.prepareStatement(sql);
            // Asignamos los valores a la sentencia
            sentencia.setInt(1, deptno);
            // Ejecutamos la sentencia
            ResultSet result = sentencia.executeQuery();
            // Recorremos el resultado
            if (result.next()) {
                dep = new Departamento(sentencia.getResultSet().getInt(1), sentencia.getResultSet().getString(2),
                        sentencia.getResultSet().getString(3));
            } else {
                System.out.println("No existe el departamento: " + deptno);
            }
            // Liberamos recursos
            result.close();
            // Cerramos la sentencia
            sentencia.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar el departamento: " + deptno);
            System.out.println("Mensaje error: " + e.getMessage());
            System.out.println("Estado SQL: " + e.getSQLState());
            System.out.println("Código del error: " + e.getErrorCode());
        }

        return dep;
    }

}

package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.CategoriasDAO;
import datos.Categorias;
import factory.SqlDbDAOFactory;

public class SqlDbCategoriasImpl implements CategoriasDAO {
    // Crear la conexión
    Connection conexion;

    public SqlDbCategoriasImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    @Override
    public int InsertarCategoria(Categorias c) {
        int resultado = 0;
   
        /*
         * Devuelven 0 => Si la operación se realizó correctamente.
         * Devuelven 1 => Si la operación no se pudo realizar.
         */
        /*
         * En Categorías:
         * • No se pueden insertar categorías con el mismo id.
         * • No se puede eliminar una categoría si tiene cursos.
         */
        // Comprobar si existe la categoría
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, c.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Ya existe una categoría con ese id: " + c.getId() + " No se insertará...");
                resultado = 1;
            } else {
                sql = "INSERT INTO categorias (id, nombre, numproductos) VALUES (?, ?, ?)";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, c.getId());
                ps.setString(2, c.getNombre());
                ps.setInt(3, c.getNumproductos());
                int filas = ps.executeUpdate();
                if (filas > 0) {
                    System.out.println("Categoría: " + c.getId() + " insertada correctamente");
                    resultado = 0;
                } else {
                    System.out.println("Error al insertar la categoría con id: " + c.getId());
                    resultado = 1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar la categoría con id: " + c.getId());
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public int EliminarCategoria(int id) {
        int resultado = 0;
        // Compruebo si existe la categoría
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Compruebo si tiene productos asociados
                sql = "SELECT * FROM productos WHERE idcategoria = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("La categoría con id: " + id + " tiene productos asociados. No se eliminará...");
                    resultado = 1;
                } else {
                    // Elimino la categoría
                    sql = "DELETE FROM categorias WHERE id = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, id);
                    int filas = ps.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Categoría: " + id + " eliminada correctamente");
                        resultado = 0;
                    } else {
                        System.out.println("Error al eliminar la categoría con id: " + id);
                        resultado = 1;
                    }
                }
            } else {
                System.out.println("No existe una categoría con ese id: " + id + " No se eliminará...");
                resultado = 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar la categoría con id: " + id);
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        // Actualiza el campo numproductos de la tabla categorias
        boolean resultado = false;
        String sql = "SELECT * FROM categorias";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                sql = "SELECT * FROM productos WHERE idcategoria = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs2 = ps.executeQuery();
                int numproductos = 0;
                while (rs2.next()) {
                    numproductos++;
                }
                sql = "UPDATE categorias SET numproductos = ? WHERE id = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, numproductos);
                ps.setInt(2, id);
                int filas = ps.executeUpdate();
                if (filas > 0) {
                    System.out.println("Categoría: " + id + " actualizada correctamente");
                    resultado = true;
                } else {
                    System.out.println("Error al actualizar la categoría con id: " + id);
                    resultado = false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar las categorías");
            resultado = false;
        }

        return resultado;
    }

    @Override
    public Categorias ConsultarCategoria(int id) {
        Categorias categorias = new Categorias();
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categorias.setId(rs.getInt("id"));
                categorias.setNombre(rs.getString("nombre"));
                categorias.setNumproductos(rs.getInt("numproductos"));
            } else {
                System.out.println("No existe una categoría con ese id: " + id);
                categorias = null;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la categoría con id: " + id);
        }
        return categorias;
    }

    @Override
    public ArrayList<Categorias> TodasLasCategorias() {
        ArrayList<Categorias> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categorias c = new Categorias();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setNumproductos(rs.getInt("numproductos"));
                categorias.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las categorías");
        }
        return categorias;
    }

}

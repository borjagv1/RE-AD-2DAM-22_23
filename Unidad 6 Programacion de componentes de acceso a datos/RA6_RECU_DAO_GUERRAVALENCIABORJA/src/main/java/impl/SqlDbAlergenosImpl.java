package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.AlergenosDAO;
import datos.Alergenos;
import factory.SqlDbDAOFactory;

public class SqlDbAlergenosImpl implements AlergenosDAO {
    // Crear la conexión
    Connection conexion;

    public SqlDbAlergenosImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    @Override
    public int InsertarAlergeno(Alergenos c) {
        int resultado = 0;
        // Comprueba si el alérgeno ya existe en la base de datos
        String sql = "SELECT * FROM alergenos WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, c.getId());
            if (statement.executeQuery().next()) {
                System.out.println("Ya existe el alérgeno con id: " + c.getId() + ". No se insertará.");
                resultado = 1;
            } else {
                // Inserta el alérgeno en la base de datos.
                sql = "INSERT INTO alergenos (id, nombre, numproductos, nombreproductos) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertStatement = conexion.prepareStatement(sql)) {
                    insertStatement.setInt(1, c.getId());
                    insertStatement.setString(2, c.getNombre());
                    insertStatement.setInt(3, c.getNumproductos());
                    insertStatement.setString(4, c.getNombreproductos());
                    int filas = insertStatement.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Se ha insertado el alérgeno con id: " + c.getId());
                        resultado = 0;
                    } else {
                        System.out.println("No se ha podido insertar el alérgeno con id: " + c.getId());
                        resultado = 1;
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public int EliminarAlergeno(int id) {
        int resultado = 0;
        // Comprueba si el alérgeno existe en la base de datos
        String sql = "SELECT * FROM alergenos WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            if (statement.executeQuery().next()) {
                // COmpruebo si el alergeno tiene productos
                sql = "SELECT * FROM alergenosproductos WHERE idalergeno = ?";
                try (PreparedStatement statement2 = conexion.prepareStatement(sql)) {
                    statement2.setInt(1, id);
                    if (statement2.executeQuery().next()) {
                        System.out
                                .println("El alérgeno con id: " + id + " tiene productos asociados. No se eliminará.");
                        resultado = 1;
                    } else {
                        // Elimina el alérgeno de la base de datos.
                        sql = "DELETE FROM alergenos WHERE id = ?";
                        try (PreparedStatement deleteStatement = conexion.prepareStatement(sql)) {
                            deleteStatement.setInt(1, id);
                            int filas = deleteStatement.executeUpdate();
                            if (filas > 0) {
                                System.out.println("Se ha eliminado el alérgeno con id: " + id);
                                resultado = 0;
                            } else {
                                System.out.println("No se ha podido eliminar el alérgeno con id: " + id);
                                resultado = 1;
                            }
                        }
                    }
                }
            } else {
                System.out.println("No existe el alérgeno con id: " + id + ". No se eliminará.");
                resultado = 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        boolean resultado = false;

        // Actualiza el campo numproductos de la tabla alergenos con la cuenta de
        // productos.
        String sql4 = "SELECT a.id, COUNT(ap.idproducto) AS numproductos FROM alergenosproductos ap INNER JOIN alergenos a ON ap.idalergeno = a.id GROUP BY a.id";
        try (PreparedStatement sentencia4 = conexion.prepareStatement(sql4)) {
            ResultSet resultSet = sentencia4.executeQuery();
            while (resultSet.next()) {
                int idAlergeno = resultSet.getInt("id");
                int numProductos = resultSet.getInt("numproductos");

                String sql6 = "UPDATE alergenos SET numproductos = ? WHERE id = ?";
                try (PreparedStatement sentencia6 = conexion.prepareStatement(sql6)) {
                    sentencia6.setInt(1, numProductos);
                    sentencia6.setInt(2, idAlergeno);
                    int filas = sentencia6.executeUpdate();
                    if (filas > 0) {
                        resultado = true;
                    } else {
                        resultado = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    resultado = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }

        // Actualiza el campo nombreproductos de la tabla alergenos con los nombres de
        // productos.
        String sql5 = "SELECT a.id, GROUP_CONCAT(p.nombre SEPARATOR ',') AS nombreproductos FROM alergenosproductos ap INNER JOIN productos p ON ap.idproducto = p.id INNER JOIN alergenos a ON ap.idalergeno = a.id GROUP BY a.id";
        try (PreparedStatement sentencia5 = conexion.prepareStatement(sql5)) {
            ResultSet resultSet = sentencia5.executeQuery();
            while (resultSet.next()) {
                int idAlergeno = resultSet.getInt("id");
                String nombreProductos = resultSet.getString("nombreproductos");

                String sql6 = "UPDATE alergenos SET nombreproductos = ? WHERE id = ?";
                try (PreparedStatement sentencia6 = conexion.prepareStatement(sql6)) {
                    sentencia6.setString(1, nombreProductos);
                    sentencia6.setInt(2, idAlergeno);
                    int filas = sentencia6.executeUpdate();
                    if (filas > 0) {
                        resultado = true;
                    } else {
                        resultado = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    resultado = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }

        return resultado;
    }

    @Override
    public boolean InsertarAlergenoProducto(int idalergeno, int idproducto) {
        boolean resultado = false;
        // Comprueba si existe el alérgeno en la tabla alergenos.
        String alergenoQuery = "SELECT * FROM alergenos WHERE id = ?";
        try (PreparedStatement alergenoStatement = conexion.prepareStatement(alergenoQuery)) {
            alergenoStatement.setInt(1, idalergeno);
            ResultSet alergenoResultSet = alergenoStatement.executeQuery();
            boolean existeAlergeno = alergenoResultSet.next();
            if (!existeAlergeno) {
                System.out.println("NO SE PUDO INSERTAR, ALÉRGENOPRODUCTO, ALÉRGENO " + idalergeno + " NO EXISTE...");
                resultado = false;
                return resultado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }

        // Comprueba si existe el producto en la tabla productos.
        String productoQuery = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement productoStatement = conexion.prepareStatement(productoQuery)) {
            productoStatement.setInt(1, idproducto);
            ResultSet productoResultSet = productoStatement.executeQuery();
            boolean existeProducto = productoResultSet.next();
            if (!existeProducto) {
                System.out.println("NO SE PUDO INSERTAR, ALÉRGENOPRODUCTO, PRODUCTO " + idproducto + " NO EXISTE...");
                resultado = false;
                return resultado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }

        // Comprueba si el registro ya existe en la tabla alergenosproductos.
        String alergenoProductoQuery = "SELECT * FROM alergenosproductos WHERE idalergeno = ? AND idproducto = ?";
        try (PreparedStatement alergenoProductoStatement = conexion.prepareStatement(alergenoProductoQuery)) {
            alergenoProductoStatement.setInt(1, idalergeno);
            alergenoProductoStatement.setInt(2, idproducto);
            ResultSet alergenoProductoResultSet = alergenoProductoStatement.executeQuery();
            boolean existeAlergenoProducto = alergenoProductoResultSet.next();
            if (existeAlergenoProducto) {
                System.out.println("NO SE PUDO INSERTAR, ALÉRGENO " + idalergeno + " Y PRODUCTO " + idproducto
                        + " YA EXISTE EN LA TABLA ALERGENOS_PRODUCTOS...");
                resultado = false;
                return resultado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }

        // Inserta el registro en la tabla alergenos_productos.
        String insertQuery = "INSERT INTO alergenosproductos (idalergeno, idproducto) VALUES (?, ?)";
        try (PreparedStatement insertStatement = conexion.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, idalergeno);
            insertStatement.setInt(2, idproducto);
            int rowsInserted = insertStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("ALÉRGENO " + idalergeno + " Y PRODUCTO " + idproducto
                        + ", INSERTADO CORRECTAMENTE EN LA TABLA ALERGENOS_PRODUCTOS...");
                resultado = true;
            } else {
                resultado = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }

        return resultado;
    }

    @Override
    public Alergenos ConsultarAlergeno(int id) {
        Alergenos alergeno = new Alergenos();
        String sql = "SELECT * FROM alergenos WHERE id = ?";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                alergeno.setId(resultado.getInt("id"));
                alergeno.setNombre(resultado.getString("nombre"));
                alergeno.setNumproductos(resultado.getInt("numproductos"));
                alergeno.setNombreproductos(resultado.getString("nombreproductos"));
            } else {
                System.out.println("No existe el alérgeno con id " + id);
                alergeno = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alergeno;
    }

    @Override
    public ArrayList<Alergenos> TodosLosAlergenos() {
        ArrayList<Alergenos> alergenos = new ArrayList<Alergenos>();
        String sql = "SELECT * FROM alergenos";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Alergenos alergeno = new Alergenos();
                alergeno.setId(resultado.getInt("id"));
                alergeno.setNombre(resultado.getString("nombre"));
                alergeno.setNumproductos(resultado.getInt("numproductos"));
                alergeno.setNombreproductos(resultado.getString("nombreproductos"));
                alergenos.add(alergeno);
            }
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alergenos;
    }
}

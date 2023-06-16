package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.ProductoDAO;
import datos.PlatosMenus;
import datos.Productos;
import factory.SqlDbDAOFactory;

public class SqlDbProductosImpl implements ProductoDAO {
    // Crear la conexión
    Connection conexion;

    public SqlDbProductosImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    @Override
    public int InsertarProducto(Productos p) {

        /*
         * En Productos:
         * • No se pueden insertar productos con el mismo id.
         * • No se pueden insertar un producto en una categoría que no exista.
         * • No se pueden insertar registros en platosmenus con el mismo id_menu e
         * id_plato. El campo orden en los platos de un menú se debe generar de forma
         * consecutiva empezando en 1.
         */
        /*
         * Devuelven 0 => Si la operación se realizó correctamente.
         * Devuelven 1 => Si la operación no se pudo realizar.
         */
        int resultado = 0;
        // Comprobar si existe el producto en mysql
        String sql = "SELECT * FROM productos WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Ya existe un producto con ese id: " + p.getId() + " No se insertará...");
                resultado = 1;
            } else {
                // Comprobar si existe la categoría
                sql = "SELECT * FROM categorias WHERE id = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, p.getIdcategoria());
                rs = ps.executeQuery();
                if (!rs.next()) {
                    System.out.println(
                            "No existe una categoría con ese id: " + p.getIdcategoria() + " No se insertará...");
                    resultado = 1;
                } else {

                    sql = "INSERT INTO productos (id, nombre, pvp, tipo, idcategoria, numalergenos, nombrealergenos) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, p.getId());
                    ps.setString(2, p.getNombre());
                    ps.setDouble(3, p.getPvp());
                    ps.setString(4, p.getTipo());
                    ps.setInt(5, p.getIdcategoria());
                    ps.setInt(6, p.getNumalergenos());
                    ps.setString(7, p.getNombrealergenos());
                    int filas = ps.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Producto: " + p.getId() + " insertado correctamente");
                        resultado = 0;
                    } else {
                        System.out.println("Error al insertar el producto con id: " + p.getId());
                        resultado = 1;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el producto con id: " + p.getId());
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        boolean resultado = false;
        // Actualizar de la tabla productos el campo numalergenos con el número de
        // alérgenos que tiene el producto.
        String sql = "SELECT * FROM productos";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int numalergenos = 0;
                sql = "SELECT * FROM alergenosproductos WHERE idproducto = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    numalergenos++;
                }
                sql = "UPDATE productos SET numalergenos = ? WHERE id = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, numalergenos);
                ps.setInt(2, id);
                int filas = ps.executeUpdate();
                if (filas > 0) {
                    System.out.println("Producto: " + id + " actualizado correctamente");
                    resultado = true;
                } else {
                    System.out.println("Error al actualizar el producto con id: " + id);
                    resultado = false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar los productos");
            resultado = false;
        }
        // ACTUALIZAR EL CAMPO NOMBREALÉRGENOS CON EL NOMBRE DE LOS ALÉRGENOS QUE TIENE
        // EL PRODUCTO.
        sql = "SELECT * FROM productos";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int numalergenos = rs.getInt("numalergenos");
                String nombrealergenos = "";
                if (numalergenos > 0) {
                    sql = "SELECT * FROM alergenosproductos WHERE idproducto = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, id);
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        int idalergeno = rs2.getInt("idalergeno");
                        sql = "SELECT * FROM alergenos WHERE id = ?";
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, idalergeno);
                        ResultSet rs3 = ps.executeQuery();
                        if (rs3.next()) {
                            String nombre = rs3.getString("nombre");
                            nombrealergenos += nombre + ",";
                        }
                    }
                    if (nombrealergenos.length() > 1) {
                        nombrealergenos = nombrealergenos.substring(0, nombrealergenos.length() - 1);
                    }
                }
                sql = "UPDATE productos SET nombrealergenos = ? WHERE id = ?";
                ps = conexion.prepareStatement(sql);
                ps.setString(1, nombrealergenos);
                ps.setInt(2, id);
                int filas = ps.executeUpdate();
                if (filas > 0) {
                    System.out.println("Producto: " + id + " actualizado correctamente");
                    resultado = true;
                } else {
                    System.out.println("Error al actualizar el producto con id: " + id);
                    resultado = false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar los productos");
            resultado = false;
        }
        // actualizar pvp de cada producto del tipo que coincida con "menu" compruebo
        // que existe
        sql = "SELECT * FROM productos WHERE tipo = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, "menu");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // CAlcular el pvp del menú que es la suma de los pvp de los platos que lo
                // componen
                int id = rs.getInt("id");
                double pvp = 0;
                sql = "SELECT * FROM platosmenus WHERE idmenu = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    int idplato = rs2.getInt("idplato");
                    sql = "SELECT * FROM productos WHERE id = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, idplato);
                    ResultSet rs3 = ps.executeQuery();
                    if (rs3.next()) {
                        double pvpplato = rs3.getDouble("pvp");
                        pvp += pvpplato;
                    }
                }
                sql = "UPDATE productos SET pvp = ? WHERE id = ?";
                ps = conexion.prepareStatement(sql);
                ps.setDouble(1, pvp);

                ps.setInt(2, id);
                int filas = ps.executeUpdate();
                if (filas > 0) {
                    System.out.println("Producto: " + id + " actualizado correctamente");
                    resultado = true;
                } else {
                    System.out.println("Error al actualizar el producto con id: " + id);
                    resultado = false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar los productos");
            resultado = false;
        }

        return resultado;
    }

    @Override
    public boolean InsertarMenu(int id_menu, int id_plato) {
        /*
         * //Insertar menus, id_menu + id_plato no se deben repetir
         * //el orden debe empieza en 1 y se suma 1 para los platos de un menú
         */
        boolean resultado = false;
        // Comprobar si existe el menú
        String sql = "SELECT * FROM productos WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id_menu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Comprobar si existe el plato
                sql = "SELECT * FROM productos WHERE id = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id_plato);
                rs = ps.executeQuery();
                if (rs.next()) {
                    // Comprobar si existe el plato en el menú
                    sql = "SELECT * FROM platosmenus WHERE idmenu = ? AND idplato = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, id_menu);
                    ps.setInt(2, id_plato);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        // Insertar el plato en el menú
                        // el orden debe empieza en 1 y se suma 1 para los platos de un menú
                        sql = "SELECT * FROM platosmenus WHERE idmenu = ?";
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, id_menu);
                        rs = ps.executeQuery();
                        int orden = 1;
                        while (rs.next()) {
                            orden++;
                        }
                        sql = "INSERT INTO platosmenus (idmenu, idplato, orden) VALUES (?, ?, ?)";
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, id_menu);
                        ps.setInt(2, id_plato);
                        ps.setInt(3, orden);
                        int filas = ps.executeUpdate();

                        if (filas > 0) {
                            System.out
                                    .println("Plato: " + id_plato + " insertado correctamente en el menú: " + id_menu);
                            resultado = true;
                        } else {
                            System.out.println("Error al insertar el plato: " + id_plato + " en el menú: " + id_menu);
                            resultado = false;
                        }
                    } else {
                        System.out.println("El plato: " + id_plato + " ya existe en el menú: " + id_menu);
                        resultado = false;
                    }
                } else {
                    System.out.println("El plato: " + id_plato + " no existe");
                    resultado = false;
                }
            } else {
                System.out.println("El menú: " + id_menu + " no existe");
                resultado = false;
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el menú");
            resultado = false;
        }

        return resultado;
    }

    @Override
    public ArrayList<PlatosMenus> ConsultarMenu(int id_menu) {
        ArrayList<PlatosMenus> platos = new ArrayList<PlatosMenus>();
        String sql = "SELECT * FROM platosmenus WHERE idmenu = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id_menu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idplato = rs.getInt("idplato");
                int orden = rs.getInt("orden");
                PlatosMenus plato = new PlatosMenus(id_menu, idplato, orden);
                platos.add(plato);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el menú");
        }
        return platos;
    }

    @Override
    public Productos ConsultarProducto(int id) {
        Productos producto = null;
        String sql = "SELECT * FROM productos WHERE id = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                double pvp = rs.getDouble("pvp");
                int idcategoria = rs.getInt("idcategoria");
                int numalergenos = rs.getInt("numalergenos");
                String nombrealergenos = rs.getString("nombrealergenos");

                producto = new Productos(id, nombre, pvp, tipo, idcategoria, numalergenos, nombrealergenos);
            } else {
                System.out.println("El producto: " + id + " no existe");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el producto");
        }
        return producto;
    }

    @Override
    public ArrayList<Productos> TodosLosProductos() {
        ArrayList<Productos> productos = new ArrayList<Productos>();
        String sql = "SELECT * FROM productos";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                double pvp = rs.getDouble("pvp");
                int idcategoria = rs.getInt("idcategoria");
                int numalergenos = rs.getInt("numalergenos");
                String nombrealergenos = rs.getString("nombrealergenos");

                Productos producto = new Productos(id, nombre, pvp, tipo, idcategoria, numalergenos, nombrealergenos);
                productos.add(producto);
            }
            if (productos.isEmpty()) {
                System.out.println("No hay productos en la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los productos");
        }
        return productos;
    }

    @Override
    public boolean EliminarPlatoMenu(int id_menu, int id_plato) {
        boolean resultado = false;
        String sql = "SELECT * FROM platosmenus WHERE idmenu = ? AND idplato = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id_menu);
            ps.setInt(2, id_plato);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Eliminar el plato del menú
                sql = "DELETE FROM platosmenus WHERE idmenu = ? AND idplato = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id_menu);
                ps.setInt(2, id_plato);
                int filas = ps.executeUpdate();
                if (filas > 0) {
                    System.out.println("Plato: " + id_plato + " eliminado correctamente del menú: " + id_menu);
                    resultado = true;
                } else {
                    System.out.println("Error al eliminar el plato: " + id_plato + " del menú: " + id_menu);
                    resultado = false;
                }

                sql = "SELECT * FROM platosmenus WHERE idmenu = ?";
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id_menu);
                rs = ps.executeQuery();
                int orden = 1;
                double pvp = 0;
                while (rs.next()) {
                    int idplato = rs.getInt("idplato");
                    sql = "UPDATE platosmenus SET orden = ? WHERE idmenu = ? AND idplato = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, orden);
                    ps.setInt(2, id_menu);
                    ps.setInt(3, idplato);
                    filas = ps.executeUpdate();
                    if (filas > 0) {
                        System.out.println(
                                "Plato: " + idplato + " del menú: " + id_menu + " actualizado ORDEN correctamente");
                        resultado = true;
                    } else {
                        System.out.println("Error al actualizar el plato: " + idplato + " del menú: " + id_menu);
                        resultado = false;
                    }
                    // Se debe calcular el nuevo PVP del menu
                    sql = "SELECT * FROM productos WHERE id = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, idplato);
                    ResultSet rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        double pvpplato = rs2.getDouble("pvp");
                        pvp = pvp + pvpplato;
                        // ACTUALIZAR EL PVP DEL MENU (TABLA PRODUCTOS) CON EL NUEVO PVP
                        sql = "UPDATE productos SET pvp = ? WHERE id = ?";
                        ps = conexion.prepareStatement(sql);
                        ps.setDouble(1, pvp);
                        ps.setInt(2, id_menu);
                        filas = ps.executeUpdate();
                        if (filas > 0) {
                            System.out.println("PVP del menú: " + id_menu + " actualizado correctamente");
                            resultado = true;
                        } else {
                            System.out.println("Error al actualizar el PVP del menú: " + id_menu);
                            resultado = false;
                        }

                    } else {
                        System.out.println("El plato: " + idplato + " no existe");
                        resultado = false;
                    }
                    orden++;
                }
            } else {
                System.out.println(
                        "El plato: " + id_plato + " no existe en el menú: " + id_menu + " y no se puede eliminar");
                resultado = false;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el plato del menú");
            resultado = false;
        }
        return resultado;
    }

    @Override
    public boolean EliminarProductoCascada(int id) {
        boolean resultado = false;

        String sql = "SELECT * FROM productos WHERE id = ?";
        try {
            PreparedStatement ps;
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Compruebo si es un plato o un menú
                String tipo = rs.getString("tipo");
                if (tipo.equals("plato")) {
                    double pvp = rs.getDouble("pvp");
                    // Es un plato

                    sql = "SELECT * FROM platosmenus WHERE idplato = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, id);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int idmenu = rs.getInt("idmenu");
                        int orden = rs.getInt("orden");
                        // Eliminar el plato del menú
                        sql = "DELETE FROM platosmenus WHERE idmenu = ? AND idplato = ?";
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, idmenu);
                        ps.setInt(2, id);
                        int filas = ps.executeUpdate();
                        if (filas > 0) {
                            System.out.println("Plato: " + id + " eliminado correctamente del menú: " + idmenu);
                            resultado = true;
                        } else {
                            System.out.println(
                                    "Error al eliminar el plato: " + id + " del menú: " + idmenu
                                            + " y no se puede eliminar");
                            resultado = false;
                        }
                        // Actualizar el orden de los platos en platosmenus
                        sql = "SELECT * FROM platosmenus WHERE idmenu = ?";
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, idmenu);
                        rs = ps.executeQuery();
                        int orden2 = 1;
                        while (rs.next()) {
                            int idplato = rs.getInt("idplato");
                            sql = "UPDATE platosmenus SET orden = ? WHERE idmenu = ? AND idplato = ?";
                            ps = conexion.prepareStatement(sql);
                            ps.setInt(1, orden2);
                            ps.setInt(2, idmenu);
                            ps.setInt(3, idplato);
                            filas = ps.executeUpdate();
                            if (filas > 0) {
                                System.out.println("Plato: " + idplato + " del menú: " + idmenu
                                        + " actualizado ORDEN correctamente");
                                resultado = true;
                            } else {
                                System.out.println("Error al actualizar el plato: " + idplato + " del menú: " + idmenu);
                                resultado = false;
                            }
                            // calculo el nuevo PVP del menu
                            sql = "SELECT * FROM productos WHERE id = ?";
                            ps = conexion.prepareStatement(sql);
                            ps.setInt(1, idplato);
                            ResultSet rs2 = ps.executeQuery();
                            if (rs2.next()) {
                                double pvpplato = rs2.getDouble("pvp");
                                pvp = pvp + pvpplato;
                                // ACTUALIZAR EL PVP DEL MENU (TABLA PRODUCTOS) CON EL NUEVO PVP
                                sql = "UPDATE productos SET pvp = ? WHERE id = ?";
                                ps = conexion.prepareStatement(sql);
                                ps.setDouble(1, pvp);
                                ps.setInt(2, idmenu);
                                filas = ps.executeUpdate();
                                if (filas > 0) {
                                    System.out.println("PVP del menú: " + idmenu + " actualizado correctamente");
                                    resultado = true;
                                } else {
                                    System.out.println("Error al actualizar el PVP del menú: " + idmenu);
                                    resultado = false;
                                }

                            } else {
                                System.out.println("El plato: " + idplato + " no existe");
                                resultado = false;
                            }
                            orden2++;
                        }
                    }
                } else {
                    // Es un menú
                    String sql2 = "SELECT * FROM platosmenus WHERE idmenu = ?";
                    ps = conexion.prepareStatement(sql2);
                    ps.setInt(1, id);
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        int idplato = rs2.getInt("idplato");
                        sql = "DELETE FROM platosmenus WHERE idmenu = ? AND idplato = ?";
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, id);
                        ps.setInt(2, idplato);
                        int filas = ps.executeUpdate();
                        if (filas > 0) {
                            System.out.println("Plato: " + idplato + " eliminado correctamente del menú: " + id);
                            resultado = true;
                        } else {
                            System.out.println(
                                    "Error al eliminar el plato: " + idplato + " del menú: " + id
                                            + " y no se puede eliminar");
                            resultado = false;
                        }
                    }
                    // 1. Eliminar el producto de la tabla productos
                    sql = "DELETE FROM productos WHERE id = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, id);
                    int filas = ps.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Producto: " + id + " eliminado correctamente");
                        resultado = true;
                    } else {
                        System.out.println("Error al eliminar el producto: " + id + " y no se puede eliminar");
                        resultado = false;
                    }
                    // 2. Eliminar el producto de la tabla alergenos
                    sql = "DELETE FROM alergenos WHERE idproducto = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, id);
                    filas = ps.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Producto: " + id + " eliminado correctamente de la tabla alergenos");
                        resultado = true;
                    } else {
                        System.out.println("Error al eliminar el producto: " + id + " de la tabla alergenos");
                        resultado = false;
                    }
                    // 3. Eliminar el producto de la tabla categorias
                    sql = "DELETE FROM categorias WHERE idproducto = ?";
                    ps = conexion.prepareStatement(sql);
                    ps.setInt(1, id);
                    filas = ps.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Producto: " + id + " eliminado correctamente de la tabla categorias");
                        resultado = true;
                    } else {
                        System.out.println("Error al eliminar el producto: " + id + " de la tabla categorias");
                        resultado = false;
                    }
                }

            } else {
                System.out.println("El producto: " + id + " no existe");
                resultado = false;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto");
            resultado = false;
        }

        return resultado;

    }
}

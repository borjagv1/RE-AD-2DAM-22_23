package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

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
         * private int id;
         * private String nombre;
         * private Double pvp;
         * private String tipo;
         * private int idcategoria;
         * private int numalergenos;
         * private String nombrealergenos;
         */
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

}

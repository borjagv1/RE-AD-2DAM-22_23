
import java.util.ArrayList;

import dao.AlergenosDAO;
import dao.CategoriasDAO;
import dao.ProductoDAO;
import datos.Alergenos;
import datos.Categorias;
import datos.PlatosMenus;
import datos.Productos;
import factory.DAOFactory;

public class pruebaMONGODB {
    static AlergenosDAO alerDAO;
    static CategoriasDAO catDAO;
    static ProductoDAO proDAO;

    public static void main(String[] args) {
        // MONGODB
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MONGO);
        Alergenos(bd);
        Categorias(bd);
        Productos(bd);

    }

    private static void Productos(DAOFactory bd) {
        // Productos
        proDAO = bd.getProductoDAO();
        // INSERTAR PRODUCTO (no debe dejar, ya existe)
        /*
         * private int id;
         * private String nombre;
         * private Double pvp;
         * private String tipo;
         * private int idcategoria;
         * private int numalergenos;
         * private String nombrealergenos;
         * 
         */
        // Insertar producto (no debe dejar, ya existe)
        Productos pro1 = new Productos(1, "Raviolis", 12.5, "plato", 1, 5, "Gluten");
        proDAO.InsertarProducto(pro1);
        // Insertar producto (debe dejar, no existe)
        Productos pro2 = new Productos(100, "Calamares", 15.5, "plato", 1, 5, "Gluten");
        proDAO.InsertarProducto(pro2);
        // Insertar producto (no debe dejar, no existe categoria)
        Productos pro3 = new Productos(300, "Calamares", 15.5, "plato", 100, 5, "Gluten");
        proDAO.InsertarProducto(pro3);
        
        // ACTUALIZAR PRODUCTO
        // Actualizar producto (debe dejar, existe)
        proDAO.ActualizarDatos();

        // InsertarMenu
        // InsertarMenu (no debe dejar, no existe menu)
        proDAO.InsertarMenu(1, 1);
        // InsertarMenu (debe dejar, existen)
        proDAO.InsertarMenu(37, 2);
        // InsertarMenu (no debe dejar, no existe plato)
        proDAO.InsertarMenu(38, 100);

        // CONSULTARMENU
        // ConsultarMenu (no debe dejar, no existe)
        ArrayList<PlatosMenus> menu = proDAO.ConsultarMenu(1);
        if (menu != null) {
            for (PlatosMenus m : menu) {
                System.out.println(m.getIdmenu() + " " + m.getIdplato() + " " + m.getOrden());
            }
        }
        // ConsultarMenu (debe dejar, existe)
        ArrayList<PlatosMenus> menu2 = proDAO.ConsultarMenu(37);
        if (menu2 != null) {
            for (PlatosMenus m : menu2) {
                System.out.println(m.getIdmenu() + " " + m.getIdplato() + " " + m.getOrden());
            }
        }

        // CONSULTAR PRODUCTO
        // Consultar producto (debe dejar, existe)
        Productos pro = proDAO.ConsultarProducto(1);
        if (pro != null) {
            System.out.println(pro.getId() + " " + pro.getNombre() + " " + pro.getPvp() + " " + pro.getTipo() + " "
                    + pro.getIdcategoria() + " " + pro.getNumalergenos() + " " + pro.getNombrealergenos());
        }
        // Consultar producto (no debe dejar, no existe)
        Productos pro4 = proDAO.ConsultarProducto(100);
        if (pro4 != null) {
            System.out.println(pro4.getId() + " " + pro4.getNombre() + " " + pro4.getPvp() + " " + pro4.getTipo() + " "
                    + pro4.getIdcategoria() + " " + pro4.getNumalergenos() + " " + pro4.getNombrealergenos());
        }

        // CONSULTAR TODOS LOS PRODUCTOS
        // Consultar todos los productos (debe dejar, existen)
        ArrayList<Productos> productos = proDAO.TodosLosProductos();
        for (Productos p : productos) {
            System.out.println(p.getId() + " " + p.getNombre() + " " + p.getPvp() + " " + p.getTipo() + " "
                    + p.getIdcategoria() + " " + p.getNumalergenos() + " " + p.getNombrealergenos());
        }
    }

    private static void Categorias(DAOFactory bd) {
        // Categorias
        catDAO = bd.getCategoriasDAO();
        // INSERTAR CATEGORIA (no debe dejar, ya existe)
        Categorias cat1 = new Categorias(1, "Entrantes", 0);
        catDAO.InsertarCategoria(cat1);
        // INSERTAR CATEGORIA (debe dejar, no existe)
        Categorias cat2 = new Categorias(12, "Postres", 0);
        catDAO.InsertarCategoria(cat2);

        // Eliminar
        catDAO.EliminarCategoria(12);
        // Eliminar (no debe dejar, no existe)
        catDAO.EliminarCategoria(155);
        // Eliminar (no debe dejar, tiene productos asociados)
        catDAO.EliminarCategoria(1);

        // Actualizar datos
        catDAO.ActualizarDatos();

        // Consultar categoria
        Categorias cat = catDAO.ConsultarCategoria(1);
        if (cat != null) {

            System.out.println(cat.getId() + " " + cat.getNombre() + " " + cat.getNumproductos());
        }
        // COnsultar todas las categorias
        ArrayList<Categorias> categorias = catDAO.TodasLasCategorias();
        for (Categorias c : categorias) {
            System.out.println(c.getId() + " " + c.getNombre() + " " + c.getNumproductos());
        }
    }

    private static void Alergenos(DAOFactory bd) {
        // Alergenos
        alerDAO = bd.getAlergenosDAO();
        // INSERTAR ALERGENO (no debe dejar, ya existe)
        Alergenos aler1 = new Alergenos(1, "Gluten", 0, "");
        alerDAO.InsertarAlergeno(aler1);
        // INSERTAR ALERGENO (debe dejar, no existe)
        Alergenos aler2 = new Alergenos(12, "Lactosa", 0, "");
        alerDAO.InsertarAlergeno(aler2);
        Alergenos aler3 = new Alergenos(10, "Frutas y verduras", 5,
                "Raviolis,Calamares,Filete de Buey,Churros,Fruta variada");
        alerDAO.InsertarAlergeno(aler3);
        // Eliminar
        alerDAO.EliminarAlergeno(12);
        // Eliminar (no debe dejar, no existe)
        alerDAO.EliminarAlergeno(155);
        // Eliminar (no debe dejar, tiene productos asociados)
        alerDAO.EliminarAlergeno(10);

        // Actualizar datos
        alerDAO.ActualizarDatos();

        // Insertar alergenoProducto
        alerDAO.InsertarAlergenoProducto(1, 1);

        // Consultar alergeno
        Alergenos aler = alerDAO.ConsultarAlergeno(1);
        if (aler != null) {

            System.out.println(aler.getId() + " " + aler.getNombre() + " " + aler.getNumproductos() + " "
                    + aler.getNombreproductos());
        }
        // Consultar todos los alergenos
        ArrayList<Alergenos> alergenos = alerDAO.TodosLosAlergenos();
        for (Alergenos a : alergenos) {
            System.out.println(a.getId() + " " + a.getNombre() + " " + a.getNumproductos() + " "
                    + a.getNombreproductos());
        }
    }
}

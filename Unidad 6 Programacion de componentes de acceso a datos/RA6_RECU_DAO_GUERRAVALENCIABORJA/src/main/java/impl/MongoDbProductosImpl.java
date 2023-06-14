package impl;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// FILTERS, UPDATES, Aggregates, Accumulators   
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import dao.ProductoDAO;
import datos.PlatosMenus;
import datos.Productos;
import factory.MongoDbDAOFactory;

public class MongoDbProductosImpl implements ProductoDAO {
    static MongoDatabase db;
    static MongoCollection<Document> coleccionAlergenos;
    static MongoCollection<Document> coleccionProductos;
    static MongoCollection<Document> coleccionCategorias;
    static MongoCollection<Document> coleccionAlergenosProductos;
    static MongoCollection<Document> coleccionPlatosMenus;

    public MongoDbProductosImpl() {
        db = MongoDbDAOFactory.crearConexion();
        coleccionAlergenos = db.getCollection("alergenos");
        coleccionProductos = db.getCollection("productos");
        coleccionCategorias = db.getCollection("categorias");
        coleccionAlergenosProductos = db.getCollection("alergenosproductos");
        coleccionPlatosMenus = db.getCollection("platosmenus");
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
        // Comprobar si existe el producto
        boolean existeProducto = coleccionProductos.find(eq("_id", p.getId())).first() != null;
        if (existeProducto) {
            System.out.println("Ya existe un producto con ese id: " + p.getId() + " No se insertará...");
            resultado = 1;
        } else {
            // Comprobar si existe la categoría
            boolean existeCategoria = coleccionCategorias.find(eq("_id", p.getIdcategoria())).first() != null;
            if (existeCategoria) {
                Document producto = new Document("_id", p.getId()).append("tipo", p.getTipo())
                        .append("numalergenos", p.getNumalergenos()).append("nombrealergenos", p.getNombrealergenos())
                        .append("idcategoria", p.getIdcategoria()).append("pvp", p.getPvp())
                        .append("nombre", p.getNombre());
                coleccionProductos.insertOne(producto);
                System.out.println("Producto: " + p.getId() + " insertado correctamente");
                resultado = 0;
            } else {
                System.out.println("No existe la categoría con id: " + p.getIdcategoria() + " No se insertará...");
                resultado = 1;
            }
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        boolean resultado = false;
        // //actualiza campos numalergenos y nombrealergenos para cada producto plato
        coleccionProductos.updateMany(exists("numalergenos", true), set("numalergenos", 0));
        for (Document productos : coleccionProductos.find()) {
            int idproducto = productos.getInteger("_id");
            int numalergenos = (int) coleccionAlergenosProductos.countDocuments(eq("id_product", idproducto));
            coleccionProductos.updateOne(eq("_id", idproducto), set("numalergenos", numalergenos));

        }
        // ACtualizo la colección productos para insertar el nombre de cada alergeno

        coleccionProductos.updateMany(exists("nombrealergenos", true), set("nombrealergenos", ""));

        for (Document productos : coleccionProductos.find()) {
            int idproducto = productos.getInteger("_id");
            String nombreAlergenos = "";
            StringBuilder sb = new StringBuilder();
            for (Document alergenosproductos : coleccionAlergenosProductos.find(eq("id_product", idproducto))) {
                int idalergeno = alergenosproductos.getInteger("id_alergeno");

                for (Document alergenos : coleccionAlergenos.find(eq("_id", idalergeno))) {
                    String nombrealergeno = alergenos.getString("nombre");
                    sb.append(nombrealergeno).append(",");
                }
                nombreAlergenos = sb.toString();
                nombreAlergenos = nombreAlergenos.substring(0, nombreAlergenos.length() - 1);
            }
            coleccionProductos.updateOne(eq("_id", idproducto), set("nombrealergenos", nombreAlergenos));
        }
        // actualizar pvp de cada producto del tipo que coincida con "menu" compruebo
        // que existe

        for (Document productos : coleccionProductos.find(eq("tipo", "menu"))) {
            int idproducto = productos.getInteger("_id");
            double pvp = 0;
            for (Document platosmenus : coleccionPlatosMenus.find(eq("id_menu", idproducto))) {
                int idplato = platosmenus.getInteger("id_plato");
                for (Document platos : coleccionProductos.find(eq("_id", idplato))) {
                    double pvpplato = platos.getDouble("pvp");
                    pvp = pvp + pvpplato;
                }
            }
            coleccionProductos.updateOne(eq("_id", idproducto), set("pvp", pvp));
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
        // Comprobar si existe el menu
        boolean existeMenu = coleccionProductos.find(eq("_id", id_menu)).first() != null;
        if (existeMenu) {
            // Comprobar si existe el plato
            boolean existePlato = coleccionProductos.find(eq("_id", id_plato)).first() != null;
            if (existePlato) {
                // Comprobar si existe el plato en el menu
                boolean existePlatoEnMenu = coleccionPlatosMenus
                        .find(and(eq("id_menu", id_menu), eq("id_plato", id_plato))).first() != null;
                if (existePlatoEnMenu) {
                    System.out.println(
                            "Ya existe el plato: " + id_plato + " en el menu: " + id_menu + " No se insertará...");
                    resultado = false;
                } else {
                    // INSERTO los datos en la colección platosmenus
                    // el orden debe empieza en 1 y se suma 1 para los platos de un menú
                    int orden = (int) coleccionPlatosMenus.countDocuments(eq("id_menu", id_menu)) + 1;
                    Document platosmenus = new Document("id_menu", id_menu).append("id_plato", id_plato).append("orden",
                            orden);
                    coleccionPlatosMenus.insertOne(platosmenus);
                    System.out.println("Plato: " + id_plato + " insertado correctamente en el menu: " + id_menu);
                }
            } else {
                System.out.println("No existe el plato con id: " + id_plato + " No se insertará...");
                resultado = false;
            }
        } else {
            System.out.println("No existe el menu con id: " + id_menu + " No se insertará...");
            resultado = false;
        }

        return resultado;
    }

    @Override
    public ArrayList<PlatosMenus> ConsultarMenu(int id_menu) {
        // Obtener platos de un menu
        ArrayList<PlatosMenus> platos = new ArrayList<PlatosMenus>();
        // Compruebo que existe el menu
        boolean existeMenu = coleccionProductos.find(eq("_id", id_menu)).first() != null;
        if (existeMenu) {
            for (Document platosmenus : coleccionPlatosMenus.find(eq("id_menu", id_menu))) {
                int idplato = platosmenus.getInteger("id_plato");
                int orden = platosmenus.getInteger("orden");

                PlatosMenus plato = new PlatosMenus(id_menu, idplato, orden);
                platos.add(plato);

            }
        } else {
            platos = null;
        }
        return platos;
    }

    @Override
    public Productos ConsultarProducto(int id) {
        Productos producto = null;
        // Compruebo que existe el producto
        boolean existeProducto = coleccionProductos.find(eq("_id", id)).first() != null;
        if (existeProducto) {
            for (Document productos : coleccionProductos.find(eq("_id", id))) {
                int idproducto = productos.getInteger("_id");
                String nombre = productos.getString("nombre");
                String tipo = productos.getString("tipo");
                int idcategoria = productos.getInteger("idcategoria");
                double pvp = productos.getDouble("pvp");
                int numalergenos = productos.getInteger("numalergenos");
                String nombrealergenos = productos.getString("nombrealergenos");

                producto = new Productos(idproducto, nombre, pvp, tipo, idcategoria, numalergenos, nombrealergenos);

            }
        } else {
            System.out.println("No existe el producto con id: " + id);
        }
        return producto;
    }

    @Override
    public ArrayList<Productos> TodosLosProductos() {
        ArrayList<Productos> productos = new ArrayList<Productos>();
        for (Document producto : coleccionProductos.find()) {
            int idproducto = producto.getInteger("_id");
            String nombre = producto.getString("nombre");
            String tipo = producto.getString("tipo");
            int idcategoria = producto.getInteger("idcategoria");
            double pvp = producto.getDouble("pvp");
            int numalergenos = producto.getInteger("numalergenos");
            String nombrealergenos = producto.getString("nombrealergenos");
            Productos producto2 = new Productos(idproducto, nombre, pvp, tipo, idcategoria, numalergenos,
                    nombrealergenos);
            productos.add(producto2);
        }
        if (productos.isEmpty()) {
            System.out.println("No hay productos en la base de datos");
        }
        return productos;
    }

}

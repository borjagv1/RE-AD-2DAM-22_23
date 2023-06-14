package impl;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

// FILTERS, UPDATES, Aggregates, Accumulators   
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import dao.CategoriasDAO;
import datos.Categorias;
import factory.MongoDbDAOFactory;

public class MongoDbCategoriasImpl implements CategoriasDAO {
    static MongoDatabase db;
    static MongoCollection<Document> coleccionAlergenos;
    static MongoCollection<Document> coleccionProductos;
    static MongoCollection<Document> coleccionCategorias;
    static MongoCollection<Document> coleccionAlergenosProductos;

    public MongoDbCategoriasImpl() {
        db = MongoDbDAOFactory.crearConexion();
        coleccionAlergenos = db.getCollection("alergenos");
        coleccionProductos = db.getCollection("productos");
        coleccionCategorias = db.getCollection("categorias");
        coleccionAlergenosProductos = db.getCollection("alergenosproductos");
    }

    @Override
    public int InsertarCategoria(Categorias c) {
       
        /*
         * Devuelven 0 => Si la operación se realizó correctamente.
         * Devuelven 1 => Si la operación no se pudo realizar.
         */
        /*
         * En Categorías:
         * • No se pueden insertar categorías con el mismo id.
         * • No se puede eliminar una categoría si tiene cursos.
         */
        int resultado = 0;
        boolean existeCategoria = coleccionCategorias.find(eq("_id", c.getId())).first() != null;
        if (!existeCategoria) {
            Document categoria = new Document("_id", c.getId())
                    .append("nombre", c.getNombre())
                    .append("numproductos", c.getNumproductos());
            coleccionCategorias.insertOne(categoria);
            resultado = 0;
            System.out.println("Se ha insertado la categoría con id: " + c.getId());
        } else {
            System.out.println("Ya existe una categoría con ese id: " + c.getId() + " No se insertará...");
            resultado = 1;
        }
        return resultado;
    }

    @Override
    public int EliminarCategoria(int id) {
        int resultado = 0;
        boolean existeCategoria = coleccionCategorias.find(eq("_id", id)).first() != null;
        if (existeCategoria) {

            Document categoria = coleccionCategorias.find(eq("_id", id)).first();
            int numProductos = categoria.getInteger("numproductos");
            if (numProductos == 0) {
                coleccionCategorias.deleteOne(eq("_id", id));
                resultado = 0;
                System.out.println("Se ha eliminado la categoría con id: " + id);
            } else {
                System.out.println(
                        "No se puede eliminar la categoría con id: " + id + " porque tiene productos asociados.");
                resultado = 1;
            }
        } else {
            System.out.println("No existe una categoría con ese id: " + id + " No se eliminará...");
            resultado = 1;
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        boolean resultado = false;
        coleccionCategorias.updateMany(exists("numproductos", true), set("numproductos", 0));

        for (Document categorias : coleccionCategorias.find()) {
            int idcategoria = categorias.getInteger("_id");
            int numproductos = (int) coleccionProductos.countDocuments(eq("idcategoria", idcategoria));
            UpdateResult updateResult = coleccionCategorias.updateOne(eq("_id", idcategoria),
                    set("numproductos", numproductos));
            if (updateResult.getModifiedCount() > 0) {
                resultado = true;
            } else {
                resultado = false;
            }
        }
        return resultado;
    }

    @Override
    public Categorias ConsultarCategoria(int id) {
        Categorias categoria = null;
        Document categoriaDoc = coleccionCategorias.find(eq("_id", id)).first();
        if (categoriaDoc != null) {
            categoria = new Categorias(categoriaDoc.getInteger("_id"), categoriaDoc.getString("nombre"),
                    categoriaDoc.getInteger("numproductos"));
        } else {
            System.out.println("No existe la categoría con id: " + id);
        }
        return categoria;
    }

    @Override
    public ArrayList<Categorias> TodasLasCategorias() {
        ArrayList<Categorias> listaCategorias = new ArrayList<Categorias>();
        for (Document categorias : coleccionCategorias.find()) {
            int idcategoria = categorias.getInteger("_id");
            String nombrecategoria = categorias.getString("nombre");
            int numproductos = categorias.getInteger("numproductos");
            Categorias categoria = new Categorias(idcategoria, nombrecategoria, numproductos);
            listaCategorias.add(categoria);
        }
        if (listaCategorias.isEmpty()) {
            System.out.println("No hay categorías en la base de datos.");
        }
        return listaCategorias;
    }

}

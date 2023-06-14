package impl;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

// FILTERS, UPDATES, Aggregates, Accumulators   
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import dao.AlergenosDAO;
import datos.Alergenos;
import factory.MongoDbDAOFactory;

public class MongoDbAlergenosImpl implements AlergenosDAO {
    static MongoDatabase db;
    static MongoCollection<Document> coleccionAlergenos;
    static MongoCollection<Document> coleccionProductos;
    static MongoCollection<Document> coleccionCategorias;
    static MongoCollection<Document> coleccionAlergenosProductos;

    public MongoDbAlergenosImpl() {
        db = MongoDbDAOFactory.crearConexion();
        coleccionAlergenos = db.getCollection("alergenos");
        coleccionProductos = db.getCollection("productos");
        coleccionCategorias = db.getCollection("categorias");
        coleccionAlergenosProductos = db.getCollection("alergenosproductos");
    }

    @Override
    public int InsertarAlergeno(Alergenos c) {
        /*
         * private int id;
         * private String nombre;
         * private int numproductos; // inicialmente 0
         * private String nombreproductos; // inicialmente vacío
         */
        /*
         * Devuelven 0 => Si la operación se realizó correctamente.
         * Devuelven 1 => Si la operación no se pudo realizar.
         */
        /*
         * En Alérgenos:
         * • No se pueden insertar alérgenos con el mismo id.
         * • No se puede eliminar un alérgeno si tiene productos.
         */
        // Método que inserta un alérgeno en la base de datos MongoDB.
        int resultado = 0;
        // Insertar el alérgeno en la base de datos.
        // Si no se pudo insertar, resultado = 1.
        // Compruebo si el alérgeno ya existe en la base de datos.
        // Si existe, resultado = 1.

        // Busco si existe el alérgeno así no repito el id.
        boolean existeAlergeno = coleccionAlergenos.find(eq("_id", c.getId())).first() != null;
        if (existeAlergeno) {
            System.out.println("Ya existe el alérgeno con id: " + c.getId() + " no se insertará...");
            resultado = 1;
        } else {
            // Inserto el alérgeno.
            Document alergeno = new Document("_id", c.getId())
                    .append("nombre", c.getNombre())
                    .append("numproductos", c.getNumproductos())
                    .append("nombreproductos", c.getNombreproductos());
            coleccionAlergenos.insertOne(alergeno);
            System.out.println("Alérgeno insertado correctamente.");
            resultado = 0;
        }
        return resultado;
    }

    @Override
    public int EliminarAlergeno(int id) {
        /*
         * Devuelven 0 => Si la operación se realizó correctamente.
         * Devuelven 1 => Si la operación no se pudo realizar.
         */
        /*
         * En Alérgenos:
         * • No se pueden insertar alérgenos con el mismo id.
         * • No se puede eliminar un alérgeno si tiene productos.
         */
        // Método que elimina un alérgeno de la base de datos MongoDB.
        int resultado = 0;
        // Comprobamos si el alérgeno existe.
        // Si no existe, resultado = 1.
        // Si existe, comprobamos si tiene productos.
        // Si tiene productos, resultado = 1.
        // Si no tiene productos, eliminamos el alérgeno.
        boolean existeAlergeno = coleccionAlergenos.find(eq("_id", id)).first() != null;
        if (!existeAlergeno) {
            System.out.println("NO SE PUDO ELIMINAR, ALÉRGENO " + id + ", NO EXISTE...");
            resultado = 1;
        } else {
            // Comprobamos si tiene productos.
            Document alergeno = coleccionAlergenos.find(eq("_id", id)).first();
            int numproductos = alergeno.getInteger("numproductos");
            if (numproductos > 0) {
                System.out.println("NO SE PUEDE ELIMINAR, ALERGENO " + id + ", TIENE PRODUCTOS ...");
                resultado = 1;
            } else {
                // Eliminamos el alérgeno.
                coleccionAlergenos.deleteOne(eq("_id", id));
                System.out.println("ALÉRGENO " + id + " ELIMINADO CORRECTAMENTE.");
                resultado = 0;
            }
        }

        return resultado;
    }

    @Override
    public boolean ActualizarDatos() {
        // actualiza el campo numproductos y nombreproductos
        // de la colección alergenos con los datos de la colección productos
        // Devuelve true si se ha actualizado correctamente.
        // Devuelve false si no se ha podido actualizar.
        boolean resultado = false;
        coleccionAlergenos.updateMany(exists("numproductos", true), set("numproductos", 0));
        // Actualizo el campo numproductos de la colección alergenos.
        for (Document alergenos : coleccionAlergenos.find()) {
            int idalergeno = alergenos.getInteger("_id");
            int numproductos = (int) coleccionAlergenosProductos.countDocuments(eq("id_alergeno", idalergeno));
            UpdateResult updateResult = coleccionAlergenos.updateOne(eq("_id", idalergeno),
                    set("numproductos", numproductos));
            if (updateResult.getModifiedCount() > 0) {
                resultado = true;
            } else {
                resultado = false;
            }
        }
        // Actualizo el campo nombreproductos de la colección alergenos.
        coleccionAlergenos.updateMany(exists("nombreproductos", true), set("nombreproductos", ""));
        for (Document alergenos : coleccionAlergenos.find()) {
            int idalergeno = alergenos.getInteger("_id");
            String nombreProductos = "";
            StringBuilder sb = new StringBuilder();
            for (Document alergenosproductos : coleccionAlergenosProductos.find(eq("id_alergeno", idalergeno))) {
                int idproducto = alergenosproductos.getInteger("id_product");

                for (Document productos : coleccionProductos.find(eq("_id", idproducto))) {
                    String nombreproducto = productos.getString("nombre");
                    sb.append(nombreproducto).append(",");
                }
                nombreProductos = sb.toString();
                nombreProductos = nombreProductos.substring(0, nombreProductos.length() - 1);
            }
            UpdateResult updateResult = coleccionAlergenos.updateOne(eq("_id", idalergeno),
                    set("nombreproductos", nombreProductos));
            if (updateResult.getModifiedCount() > 0) {
                resultado = true;
            } else {
                resultado = false;
            }
        }

        return resultado;
    }

    @Override
    public boolean InsertarAlergenoProducto(int idalergeno, int idproducto) {
        /*
         * AlergenosProductos
         * 
         * "id_alergeno": 1,
         * "id_product": 1
         * }
         */
        // Método que inserta un nuevo registro en la tabla alergenosproductos.
        // Devuelve true si se ha insertado correctamente.
        // Devuelve false si no se ha podido insertar.
        // No se puede insertar un id_product que ya tenga el id_alergeno.
        boolean resultado = false;
        // Comprobamos si existe el alérgeno.
        boolean existeAlergeno = coleccionAlergenos.find(eq("_id", idalergeno)).first() != null;
        // Comprobamos si existe el producto.
        boolean existeProducto = coleccionProductos.find(eq("_id", idproducto)).first() != null;
        // Comprobamos si existe el registro en la tabla alergenosproductos.
        boolean existeAlergenoProducto = coleccionAlergenosProductos
                .find(and(eq("id_alergeno", idalergeno), eq("id_product", idproducto))).first() != null;
        if (!existeAlergeno) {
            System.out.println("NO SE PUDO INSERTAR, ALÉRGENOPRODUCTO, ALÉRGENO " + idalergeno + ", NO EXISTE...");
            resultado = false;
        } else if (!existeProducto) {
            System.out.println("NO SE PUDO INSERTAR, ALÉRGENOPRODUCTO, PRODUCTO" + idproducto + ", NO EXISTE...");
            resultado = false;
        } else if (existeAlergenoProducto) {
            System.out.println("NO SE PUDO INSERTAR, ALÉRGENO " + idalergeno + " Y PRODUCTO " + idproducto
                    + ", YA EXISTE EN LA TABLA ALERGENOSPRODUCTOS...");
            resultado = false;
        } else {
            // Insertamos el registro en la tabla alergenosproductos.
            Document alergenosproductos = new Document("id_alergeno", idalergeno).append("id_product", idproducto);
            coleccionAlergenosProductos.insertOne(alergenosproductos);
            System.out.println("ALÉRGENO " + idalergeno + " Y PRODUCTO " + idproducto
                    + ", INSERTADO CORRECTAMENTE EN LA TABLA ALERGENOSPRODUCTOS...");
            resultado = true;
        }
        return resultado;
    }

    @Override
    public Alergenos ConsultarAlergeno(int id) {
        Alergenos alergeno = null;
        // Método que devuelve un objeto Alergenos con los datos del alérgeno cuyo id se
        // pasa como parámetro.
        // Si no existe el alérgeno devuelve un mensaje indicandolo.
        // Si existe el alérgeno devuelve un objeto Alergenos con los datos del
        // alérgeno.
        Document alergenos = coleccionAlergenos.find(eq("_id", id)).first();
        if (alergenos == null) {
            System.out.println("NO EXISTE EL ALÉRGENO " + id + "...");
        } else {
            int idalergeno = alergenos.getInteger("_id");
            String nombre = alergenos.getString("nombre");
            int numproductos = alergenos.getInteger("numproductos");
            String nombreproductos = alergenos.getString("nombreproductos");
            alergeno = new Alergenos(idalergeno, nombre, numproductos, nombreproductos);
        }
        return alergeno;
    }

    @Override
    public ArrayList<Alergenos> TodosLosAlergenos() {
        ArrayList<Alergenos> listaAlergenos = new ArrayList<>();
        // Método que devuelve un ArrayList de objetos Alergenos con todos los alérgenos
        // de la colección.
        // Si no existen alérgenos devuelve un mensaje indicandolo.
        // Si existen alérgenos devuelve un ArrayList de objetos Alergenos con todos los
        // alérgenos.
        for (Document alergeno : coleccionAlergenos.find()) {
            int idalergeno = alergeno.getInteger("_id");
            String nombre = alergeno.getString("nombre");
            int numproductos = alergeno.getInteger("numproductos");
            String nombreproductos = alergeno.getString("nombreproductos");
            Alergenos alergeno1 = new Alergenos(idalergeno, nombre, numproductos, nombreproductos);
            listaAlergenos.add(alergeno1);
        }
        if (listaAlergenos.isEmpty()) {
            System.out.println("NO EXISTEN ALÉRGENOS...");
        }
        return listaAlergenos;
    }

}

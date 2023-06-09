
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.List;

public class Main_BorjaGuerraValencia {

    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("BD_RECU_GUERRAVALENCIABORJA");
        MongoCollection<Document> coleccionAlergenos = db.getCollection("alergenos");
        MongoCollection<Document> coleccionAlergenosProductos = db.getCollection("alergenosproductos");
        MongoCollection<Document> coleccionCategorias = db.getCollection("categorias");
        MongoCollection<Document> coleccionPlatosMenus = db.getCollection("platosmenus");
        MongoCollection<Document> coleccionProductos = db.getCollection("productos");
        MongoCollection<Document> coleccionMenusyPrecios = db.getCollection("menusyprecios");

        // Actualizar numproductos de categorias
        actualizarColeccionCategorias_numProductos(coleccionCategorias,
        coleccionProductos);

        // Actualizo en la coleccion productos el campo numalergenos
        actualizarColeccionProductos_numProductos_nombreAlergenos(coleccionAlergenos,
        coleccionAlergenosProductos,
        coleccionProductos);
        // Actualizo alergenos para poner el campo numproductos
        actualizarColeccionAlergenos_numProductos_nombreProductos(coleccionAlergenos,
        coleccionAlergenosProductos,
        coleccionProductos);
        // Crear una colección nueva e insertar datos.
        crearNuevaColeccion_menusyprecios(db, coleccionPlatosMenus,
        coleccionProductos);

        // ConsultaAlergeno
        consultaAlergeno(coleccionAlergenos, coleccionAlergenosProductos,
        coleccionProductos, 1);
        consultaAlergeno(coleccionAlergenos, coleccionAlergenosProductos,
        coleccionProductos, 11);
        consultaAlergeno(coleccionAlergenos, coleccionAlergenosProductos,
        coleccionProductos, 5);

        // ConsultaCategoria
        consultaCategoria(db, coleccionAlergenos, coleccionAlergenosProductos,
                coleccionProductos, coleccionCategorias, coleccionPlatosMenus, 111);
        consultaCategoria(db, coleccionAlergenos, coleccionAlergenosProductos, coleccionProductos, coleccionCategorias,
                coleccionPlatosMenus, 3);
        consultaCategoria(db, coleccionAlergenos, coleccionAlergenosProductos, coleccionProductos, coleccionCategorias,
                coleccionPlatosMenus, 5);
        consultaCategoria(db, coleccionAlergenos, coleccionAlergenosProductos, coleccionProductos, coleccionCategorias,
                coleccionPlatosMenus, 7);
        consultaCategoria(db, coleccionAlergenos, coleccionAlergenosProductos, coleccionProductos, coleccionCategorias,
                coleccionPlatosMenus, 6);

        // Cerrar la conexión
        cliente.close();

    }// main

    private static void consultaCategoria(MongoDatabase db, MongoCollection<Document> coleccionAlergenos,
            MongoCollection<Document> coleccionAlergenosProductos, MongoCollection<Document> coleccionProductos,
            MongoCollection<Document> coleccionCategorias, MongoCollection<Document> coleccionPlatosMenus,
            int idcategoria) {

        System.out.println("---------------------------------------------------------------");
        boolean existecategorias = coleccionCategorias.find(eq("_id", idcategoria)).first() != null;
        if (!existecategorias) {
            System.out.println("LA CATEGORIA " + idcategoria + " NO EXISTE");

        } else {

            for (Document categorias : coleccionCategorias.find(eq("_id", idcategoria))) {
                System.out.println("CATEGORIA: " + idcategoria + ", " + categorias.get("nombre"));
                // PLATOS
                boolean existeplatos = coleccionProductos.find(and(eq("tipo", "plato"), eq("idcategoria", idcategoria)))
                        .first() != null;
                if (!existeplatos) {
                    System.out.println("\t<<NO HAY PLATOS EN LA CATEGORIA>>");
                } else {
                    System.out.printf("PLATOS: %-1s %-18s %-7s %-5s\n", "ID", "NOMBRE", "PVP", "Nº y ALERGENOS");
                    System.out.println("       === ================== ======= ========================");

                    for (Document platos : coleccionProductos
                            .find(and(eq("tipo", "plato"), eq("idcategoria", idcategoria)))) {
                        System.out.printf("        %-1s %-18s %-7s %-5s %-20s\n",
                                platos.get("_id"), platos.get("nombre"), platos.get("pvp"),
                                platos.get("numalergenos"), platos.get("nombrealergenos"));
                    }
                }
                System.out.println("---------------------------------------------------------------");
                // MENUS
                boolean existemenus = coleccionProductos
                        .find(and(eq("tipo", "menu"), eq("idcategoria", idcategoria)))
                        .first() != null;
                if (!existemenus) {
                    System.out.println("\t<<NO HAY MENUS EN LA CATEGORIA>>");
                } else {

                    System.out.printf("MENUS:  %-1s %-18s %-7s %-5s\n", "ID", "NOMBRE", "PVP", "Nº y ALERGENOS");
                    System.out.println("       === ================== ======= ========================");

                    for (Document menus : coleccionProductos
                            .find(and(eq("tipo", "menu"), eq("idcategoria", idcategoria)))) {
                        System.out.printf("        %-1s %-18s %-7s %-5s %-20s\n",
                                menus.get("_id"), menus.get("nombre"), menus.get("pvp"),
                                menus.get("numalergenos"), menus.get("nombrealergenos"));

                    }

                }
            }

        }
    }

    private static void consultaAlergeno(MongoCollection<Document> coleccionAlergenos,
            MongoCollection<Document> coleccionAlergenosProductos, MongoCollection<Document> coleccionProductos,
            int idAlergeno) {

        System.out.println("---------------------------------------------------------------");
        boolean existealergenos = coleccionAlergenos.find(eq("_id", idAlergeno)).first() != null;
        if (!existealergenos) {
            System.out.println("EL ALERGENO " + idAlergeno + " NO EXISTE");

        } else {
            for (Document alergenos : coleccionAlergenos.find(eq("_id", idAlergeno))) {
                System.out.println(
                        "ALERGENO: " + idAlergeno + ", Nombre: " + alergenos.getString("nombre") + "\nPRODUCTOS: "
                                + alergenos.getString("nombreproductos"));
            }
        }
        System.out.println("---------------------------------------------------------------");

    }

    private static void crearNuevaColeccion_menusyprecios(MongoDatabase db,
            MongoCollection<Document> coleccionPlatosMenus,
            MongoCollection<Document> coleccionProductos) {
        MongoCollection<Document> coleccionMenusyPrecios = db.getCollection("menusyprecios");
        coleccionMenusyPrecios.drop();
        String nombremenuAnterior = "";
        boolean menuInsertado = false;
        List<String> nombreplatosList = new ArrayList<>();
        for (Document platosmenus : coleccionPlatosMenus.find()) {
            int idmenu = platosmenus.getInteger("id_menu");
            String nombremenu = "";
            String nombreplatos = "";
            double preciomenu = 0;
            int idcategoria = 0;
            for (Document menus : coleccionProductos.find(eq("_id", idmenu))) {
                nombremenu = menus.getString("nombre");
                idcategoria = menus.getInteger("idcategoria");
            }
            if (!nombremenu.equals(nombremenuAnterior)) {
                menuInsertado = false;
            }
            for (Document platos : coleccionPlatosMenus.find(eq("id_menu", idmenu))) {
                int idplato = platos.getInteger("id_plato");
                String control7 = "";
                for (Document productos : coleccionProductos.find(eq("_id", idplato))) {
                    // Inserto los platos separados por comas:
                    // compruebo que no se repita el nombre del plato
                    if (nombreplatosList.contains(productos.getString("nombre"))) {
                        control7 = "repetido";
                    } else {
                        String nombreplato = productos.getString("nombre");
                        nombreplatosList.add(nombreplato);
                        // nombreplatos = productos.getString("nombre");
                        preciomenu += productos.getDouble("pvp");
                    }
                    control7 = productos.getString("nombre");
                }
                nombreplatos = String.join(",", nombreplatosList);

            }
            if (!menuInsertado) {
                Document doc = new Document();
                doc.append("nombredelmenu", nombremenu);
                doc.append("nombreplatos", nombreplatos);
                doc.append("preciomenu", preciomenu);
                doc.append("idcategoria", idcategoria);
                coleccionMenusyPrecios.insertOne(doc);
                menuInsertado = true;
                nombremenuAnterior = nombremenu;

            }
            nombreplatosList.clear();
            nombreplatos = "";
        }
        System.out.println("====================================================================");
    }

    private static void actualizarColeccionAlergenos_numProductos_nombreProductos(
            MongoCollection<Document> coleccionAlergenos,
            MongoCollection<Document> coleccionAlergenosProductos, MongoCollection<Document> coleccionProductos) {
        System.out.println("====================================================================");

        coleccionAlergenos.updateMany(exists("numproductos", true), set("numproductos", 0));
        int control4 = 0;
        for (Document alergenos : coleccionAlergenos.find()) {
            int idalergeno = alergenos.getInteger("_id");
            int numproductos = (int) coleccionAlergenosProductos.countDocuments(eq("id_alergeno", idalergeno));
            coleccionAlergenos.updateOne(eq("_id", idalergeno), set("numproductos", numproductos));
            if (control4 != idalergeno) {
                System.out.println("Actualizando alergeno " + idalergeno + " numproductos: " + numproductos);
                control4 = idalergeno;
            }
        }
        // Actualizo la colección alergenos para poner el campo nombreproductos
        System.out.println("====================================================================");
        coleccionAlergenos.updateMany(exists("nombreproductos", true), set("nombreproductos", ""));
        int control5 = 0;
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
            coleccionAlergenos.updateOne(eq("_id", idalergeno), set("nombreproductos", nombreProductos));
            if (control5 != idalergeno) {
                System.out.println("Actualizando alergeno " + idalergeno + " nombreproductos: " + nombreProductos);
                control5 = idalergeno;
            }
        }
    }

    private static void actualizarColeccionProductos_numProductos_nombreAlergenos(
            MongoCollection<Document> coleccionAlergenos,
            MongoCollection<Document> coleccionAlergenosProductos, MongoCollection<Document> coleccionProductos) {
        System.out.println("====================================================================");

        coleccionProductos.updateMany(exists("numalergenos", true), set("numalergenos", 0));
        int control = 0;
        for (Document productos : coleccionProductos.find()) {
            int idproducto = productos.getInteger("_id");
            int numalergenos = (int) coleccionAlergenosProductos.countDocuments(eq("id_product", idproducto));
            coleccionProductos.updateOne(eq("_id", idproducto), set("numalergenos", numalergenos));
            if (control != idproducto) {
                System.out.println("Actualizando producto " + idproducto + " numalergenos: " + numalergenos);
                control = idproducto;
            }
        }
        // ACtualizo la colección productos para insertar el nombre de cada alergeno
        System.out.println("====================================================================");

        coleccionProductos.updateMany(exists("nombrealergenos", true), set("nombrealergenos", ""));
        int control3 = 0;
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
            if (control3 != idproducto) {
                System.out.println("Actualizando producto " + idproducto + " nombrealergenos: " + nombreAlergenos);
                control3 = idproducto;
            }
        }
    }

    private static void actualizarColeccionCategorias_numProductos(MongoCollection<Document> coleccionCategorias,
            MongoCollection<Document> coleccionProductos) {
        coleccionCategorias.updateMany(exists("numproductos", true), set("numproductos", 0));
        int control2 = 0;
        System.out.println("====================================================================");

        for (Document categorias : coleccionCategorias.find()) {
            int idcategoria = categorias.getInteger("_id");
            int numproductos = (int) coleccionProductos.countDocuments(eq("idcategoria", idcategoria));
            coleccionCategorias.updateOne(eq("_id", idcategoria), set("numproductos", numproductos));
            if (control2 != idcategoria) {
                System.out.println("Actualizando categoria " + idcategoria + " numproductos");
                control2 = idcategoria;
            }
        }
    }

}// class

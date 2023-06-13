import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import datos.*;

public class RECULLenarBDNeodatis22_23 {

	static MongoClient cliente; 
	static MongoDatabase db; 
	static MongoCollection<Document> productos;
	static MongoCollection<Document> categorias;
	static MongoCollection<Document> alergenos;
	static MongoCollection<Document> alergenosproductos;
	static MongoCollection<Document> platosmenus;

	static ODB odb;

	public static void main(String[] args) {

		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);

		cliente = new MongoClient();
		db = cliente.getDatabase("ra6recu");
		productos = db.getCollection("productos");
		categorias = db.getCollection("categorias");
		alergenos = db.getCollection("alergenos");
		alergenosproductos = db.getCollection("alergenosproductos");
		platosmenus = db.getCollection("platosmenus");

		File fichero = new File("BDCAFETERIARECU.neo");

		if (fichero.exists()) {
			System.out.println("Ya existe BD, la borro");
			fichero.delete();
		}

		odb = ODBFactory.open("BDCAFETERIARECU.neo");// Abrir BD

		llenarCategorias();
		llenarAlergenos();
		llenarProductos();
		llenarAlergenosProductos();
		llenarPlatosmenus();

		odb.close();

	}

	private static void llenarPlatosmenus() {
		List<Document> consulta = platosmenus.find().into(new ArrayList<Document>());

		for (int i = 0; i < consulta.size(); i++) {

			Document doc = consulta.get(i);

			PlatosMenus p = new PlatosMenus(doc.getInteger("id_menu"), doc.getInteger("id_plato"),
					doc.getInteger("orden"));
			odb.store(p);

		} // for

	}// llenarPlatosmenus

	private static void llenarAlergenosProductos() {
		List<Document> consulta = alergenosproductos.find().into(new ArrayList<Document>());

		for (int i = 0; i < consulta.size(); i++) {
			Document doc = consulta.get(i);

			AlergenosProductos a = new AlergenosProductos(doc.getInteger("id_product"), doc.getInteger("id_alergeno"));
			odb.store(a);
			System.out.println("alergenosproductos insertado: " + doc.getInteger("id_product"));

		} // for

	}// llenarAlergenosProductos

	private static void llenarProductos() {
		List<Document> consulta = productos.find().into(new ArrayList<Document>());

		for (int i = 0; i < consulta.size(); i++) {
			Document doc = consulta.get(i);

			Productos p = new Productos(doc.getInteger("_id"), 
					doc.getString("nombre"), doc.getDouble("pvp"),
					doc.getString("tipo"), doc.getInteger("idcategoria"), 0, "");

			odb.store(p);
			System.out.println("Producto insertado: " + doc.getInteger("_id"));

		} // for

	}// llenarProductos

	private static void llenarAlergenos() {
		List<Document> consulta = alergenos.find().into(new ArrayList<Document>());

		for (int i = 0; i < consulta.size(); i++) {
			Document doc = consulta.get(i);
			Alergenos a = new Alergenos(doc.getInteger("_id"), doc.getString("nombre"), 0, "");
			odb.store(a);
			System.out.println("alergeno insertado: " + doc.getInteger("_id"));

		} // for

	}// llenarAlergenos

	private static void llenarCategorias() {

		List<Document> consulta = categorias.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			Document doc = consulta.get(i);
			Categorias c = new Categorias(doc.getInteger("_id"), doc.getString("nombre"), 0);
			odb.store(c);
			System.out.println("Categoria insertada: " + doc.getInteger("_id"));

		} // for

	}

}// fin

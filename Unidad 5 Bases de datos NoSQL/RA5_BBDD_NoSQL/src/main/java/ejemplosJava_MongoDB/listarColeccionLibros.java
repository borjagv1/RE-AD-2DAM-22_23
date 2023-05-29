package ejemplosJava_MongoDB;

import java.util.*;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class listarColeccionLibros {

	public static void main(String[] args) {
		MongoClient cliente = MongoClients.create();
		MongoDatabase db = cliente.getDatabase("mibasedatos");
		MongoCollection<Document> coleccion = db.getCollection("libros");

		List<Document> consulta = coleccion.find().into(new ArrayList<Document>());
		
		// Visualizar los datos elemento a elemento
		System.out.println(" - ----------------------------------------");
		System.out.println(" - VISUALIZA COLECCION libros --");

		for (int i = 0; i < consulta.size(); i++) {
			Document amig = consulta.get(i);
			System.out.println("  " + amig.getDouble("codigo") + " - " + amig.get("nombre") + " - " + amig.get("pvp") + " - "
					+ amig.get("editorial") + " - " + amig.get("temas"));
			
			if (amig.get("temas") != null) {
				String coma = "";

				@SuppressWarnings("unchecked")
				ArrayList<String> temas = (ArrayList<String>) amig.get("temas");
				
				System.out.print("\t" + temas.size() + " Temas: ");
				for (String na : temas) {
					System.out.print(coma + na);
					coma = ", ";
				}

				System.out.println();
			}
		}

		// -------------------------------------
			
		String cad = "UML";
		LibrosTema(coleccion, cad);
		
		cad = "Base de datos";
		LibrosTema(coleccion, cad);
		
		cad = "Neodatis";
		LibrosTema(coleccion, cad);
		
		cliente.close();

	}

	public static void LibrosTema(MongoCollection<Document> coleccion, String cad) {
		System.out.println(" - ----------------------------------------");
		System.out.println(" - VISUALIZA LIBROS CON EL TEMA "+cad +" --");
		
		MongoCursor<Document> docs = coleccion.find(Filters.eq("temas", cad)).iterator();
		while (docs.hasNext()) {
			Document doc = docs.next();
			// System.out.println(docu2.toJson());
			System.out.println("  " + doc.get("codigo") + " - " + doc.get("nombre") + " - " + doc.get("pvp") + " - "
					+ doc.get("editorial"));

		}
		docs.close();
	}

}

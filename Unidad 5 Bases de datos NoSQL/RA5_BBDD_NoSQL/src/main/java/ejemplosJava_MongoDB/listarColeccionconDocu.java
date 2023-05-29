package ejemplosJava_MongoDB;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class listarColeccionconDocu {

	public static void main(String[] args) {

		MongoClient cliente = MongoClients.create();
		MongoDatabase db = cliente.getDatabase("mibasedatos");
		MongoCollection<Document> coleccion = db.getCollection("amigos");

		List<Document> consulta = coleccion.find().into(new ArrayList<Document>());
		// Visualizar los datos elemento a elemento
		System.out.println(" - ----------------------------------------");
		System.out.println(" - VISUALIZA DATOS ELEMENTO A ELEMENTO (amigos) --");

		for (int i = 0; i < consulta.size(); i++) {
			Document amig = consulta.get(i);
			System.out.println("  " + amig.getString("nombre") + " - " + amig.get("tel�fono") + " - "
					+ amig.get("curso") + " - " + amig.get("nota"));
			
			//SI EL CAMPO CURSO ES UN Document visualizo los cursos 
			try {
				Document datosCurso = (Document) amig.get("curso");
				System.out.println("       Cursos: " + datosCurso.get("curso1") + ", " + datosCurso.get("curso2"));
			} catch (java.lang.ClassCastException ex) {
			}

		}

		cliente.close();

	}// main

}

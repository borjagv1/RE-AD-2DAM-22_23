package ejemplosJava_MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.bson.Document; // mongo-java-driver-322

public class EjemplosLibroAD {

	public static void main(String[] args) {

		// Nos conectamos a la BD
		// MongoClient cliente = new MongoClient("localhost",27017);
		MongoClient cliente = MongoClients.create();
		MongoDatabase db = cliente.getDatabase("mibasedatos");
		MongoCollection<Document> coleccion = db.getCollection("amigos");

		// Visualizar los datos en formato cadena
		System.out.println(" - ----------------------------------------");
		List<Document> consulta = coleccion.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			System.out.println(" - " + consulta.get(i).toString());
		}

		// Visualizar los datos elemento a elemento
		System.out.println(" - ----------------------------------------");
		for (int i = 0; i < consulta.size(); i++) {
			Document amig = consulta.get(i);
			System.out.println(" - " + amig.getString("nombre") + " - " + amig.get("teléfono") + " - "
					+ amig.get("curso") + " - " + amig.get("nota"));

		}

		// insertar un documento en amigos
		Document amigo = new Document();
		amigo.put("nombre", "Pepito2");
		amigo.put("teléfono", 555);
		amigo.put("curso", "2DAM2");
		amigo.put("fecha", new Date());
		coleccion.insertOne(amigo);

		//insertar otro documento en amigos
		Document amigo2 = new Document("nombre", "Pedro").append("teléfono", 1234).append("curso",
				new Document("curso1", "1DAM").append("curso2", "2DAM"));

		coleccion.insertOne(amigo2);

		

		// insertar varios documentos de una lista
		List< Document> listadocs = new ArrayList<Document>();
		for (int i = 0; i < 4; i++) {
			listadocs.add(new Document("Valor de i", i));
		}
		coleccion.insertMany(listadocs);

		// Visualizar utilizando un cursor
		System.out.println(" - ---Utilizo cursor----------------------");
		MongoCursor<Document> cursor = coleccion.find().iterator();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			System.out.println(doc.toJson());
		}
		cursor.close();

		// Búsquedas de documentos
		System.out.println(" - ---Busqueda de un documento ----------------------");
		Document doc = coleccion.find(Filters.eq("nombre", "Ana")).first();
		try {
			System.out.println("Localizado: " + doc.toJson());
		} catch (NullPointerException e) {
			System.out.println("No se encuentra.");
		}

		// Salida ordenada utilizo cursor
		System.out.println(" - ----Ordenados desc por nombre (curso 1DAM)---------------");
		MongoCursor<Document> docs = coleccion.find(eq("curso", "1DAM")).sort(descending("nombre")).iterator();
		while (docs.hasNext()) {
			Document docu = docs.next();
			System.out.println(docu.toJson());
		}
		docs.close();

		// Salida ordenada pero utilizo una lista
		List<Document> consulta3 = coleccion.find(and(eq("curso", "1DAM"), eq("nota", 8)))
				.into(new ArrayList<Document>());
		for (int i = 0; i < consulta3.size(); i++) {
			System.out.println(" - " + consulta3.get(i).toString());
		}

		

		// Utilizando proyección
		System.out.println(" - ---Nombre y nota de 1DAM -----------------");
		MongoCursor<Document> docs3 = coleccion.find(eq("curso", "1DAM")).sort(ascending("nombre"))
				.projection(include("nombre", "nota")).iterator();
		while (docs3.hasNext()) {
			Document docu = docs3.next();
			System.out.println(docu.toJson());
		}
		docs3.close();

		// Nota media
		System.out.println(" - ---Nota media por curso -----------------");
		MongoCursor<Document> docs6 = coleccion
				.aggregate(Arrays.asList(group("$curso", sum("sumanota", "$nota"), avg("medianota", "$nota"))))
				.iterator();
		while (docs6.hasNext()) {
			Document docu = docs6.next();
			System.out.println(docu.toJson());
		}
		docs6.close();

		// Enviamos la salida a un nuevo doc
		System.out.println(" - ---Utilizamos out -----------------");
		MongoCursor<Document> docs7 = coleccion.aggregate(
				Arrays.asList(group("$curso", sum("sumanota", "$nota"), avg("medianota", "$nota")), out("mediascurso")))
				.iterator();
		while (docs7.hasNext()) {
			Document docu = docs7.next();
			System.out.println(docu.toJson());
		}
		docs7.close();

		// Actualizar documentos
		// Subir nota a uno
		coleccion.updateOne(eq("nombre", "Ana"), set("nota", 5));

		// Subir la nota a varios
		UpdateResult updateResult = coleccion.updateMany(eq("curso", "1DAM"), inc("nota", 3));
		System.out.println("Se han modificado: " + updateResult.getModifiedCount());
		System.out.println("Se han seleccionado: " + updateResult.getMatchedCount());

		// Subir la nota a todos
		UpdateResult updateResult2 = coleccion.updateMany(exists("_id"), inc("nota", 2));
		System.out.println("Se han modificado: " + updateResult2.getModifiedCount());

		coleccion.updateOne(eq("nombre", "Marleni"), set("población", "Toledo"));

		coleccion.updateMany(eq("curso", "1DAM"), unset("población"));

		// Borro un documento
		DeleteResult del = coleccion.deleteOne(eq("nombre", "Ana"));
		System.out.println("Se han borrado: " + del.getDeletedCount());
		// Borro todos
		del = coleccion.deleteMany(exists("_id"));
		System.out.println("Se han borrado: " + del.getDeletedCount());

		// Crear Colección:
		MongoClient cliente2 = MongoClients.create();
		MongoDatabase db2 = cliente2.getDatabase("mibasedatos");
		db2.createCollection("nuevacoleccion2");
		MongoCollection<Document> cnueva = db2.getCollection("nuevacoleccion2");
		// La borro
		cnueva.drop();

		// Listar las colecciones de la BD
		System.out.println("Listado de colecciones: ");
		MongoIterable<String> colecciones = db.listCollectionNames();
		Iterator<String> col = colecciones.iterator();
		while (col.hasNext())
			System.out.println(col.next());

		// También se pueden listar así:
		for (String name : db.listCollectionNames()) {
			System.out.println(name);
		}

		//Crear base de datos
		MongoClient clienn= MongoClients.create();
		MongoDatabase dbn = clienn.getDatabase("nueva");
		MongoCollection<Document> clnue = dbn.getCollection("colecnueva");
		Document doc1 = new Document("nombre", "Pedro").append("teléfono", 1234).append("curso","2DAM");
		clnue.insertOne(doc1);

		for (String name: clienn.listDatabaseNames()) {
		    System.out.println(name);
		}
		
		

	}

}//FIN

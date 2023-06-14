/*
 * CREACION DE LAS COLECCIONES DE LA BD
 * A PARTIR DE FICHEROS JSON.
 */
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;

import org.bson.Document;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;

public class CREAR_MONGO_RECULlenarBDMongo22_23 {

	static MongoClient cliente = new MongoClient();
	static MongoDatabase db = cliente.getDatabase("ra6recu");	
	
	public static void main(String[] args) throws ParseException, IOException {

		CrearColeccion("productos.json","productos");
		CrearColeccion("categorias.json","categorias");
		CrearColeccion("alergenos.json","alergenos");
		CrearColeccion("alergenosproductos.json","alergenosproductos");
		CrearColeccion("platosmenus.json","platosmenus");

		cliente.close();

	}//main

	// Lee datos de un fichero en Json los almaceno en una coleccion nueva
	private static void CrearColeccion(String nombrefichero, String nombrecoleccion) throws ParseException, IOException {
		
		MongoCollection<Document> coleccion = db.getCollection(nombrecoleccion);
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection(nombrecoleccion);
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection(nombrecoleccion);
		}		
		
		Path path = Paths.get(nombrefichero);
		
		try {
		String jsonString = Files.readString(path);

		JSONParser parser = new JSONParser();
		JSONArray jsonarray = (JSONArray) parser.parse(jsonString);

		List<InsertOneModel<Document>> docs = new ArrayList<>();

		System.out.println("---------------------------------------------------------------");
		System.out.println("COLECCION: " + nombrecoleccion);
		
		for (int i = 0; i < jsonarray.size(); i++) {
			Object line = jsonarray.get(i);
			docs.add(new InsertOneModel<>(Document.parse(line.toString())));			
			System.out.println("Añadiendo: " + line.toString());
		}

		coleccion.bulkWrite(docs, new BulkWriteOptions().ordered(true));

		
		} catch(java.nio.file.NoSuchFileException ex) {
			System.out.println("ERRRRROR, el fichero no existe: "+ ex.getMessage());
		}
	}//

}//fin

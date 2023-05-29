package ejemplosJava_MongoDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class pruebasficheros {

	public static void main(String[] args) {

		crearficherojson();
		leerficheroyguardardatos();

	}

	// Lee datos de un fichero en Json los almaceno en una colecci�n nueva
	private static void leerficheroyguardardatos() {
		try {
			FileReader fr = new FileReader("./amigos.json");
			BufferedReader bf = new BufferedReader(fr);

			MongoClient cliente = MongoClients.create();
			MongoDatabase db = cliente.getDatabase("mibasedatos");
			MongoCollection<Document> coleccion = db.getCollection("amigosfile");

			String cadenajson;
			while ((cadenajson = bf.readLine()) != null) {
				System.out.println(cadenajson);
				Document docu = new Document(org.bson.Document.parse(cadenajson));
				coleccion.insertOne(docu);
			}
			bf.close();

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// Crear un fichero de texto con los datos de una colecci�n
	public static void crearficherojson() {

		MongoClient cliente = MongoClients.create();
		MongoDatabase db = cliente.getDatabase("mibasedatos");
		MongoCollection<Document> coleccion = db.getCollection("amigos");

		File fiche = new File("./amigos.json");
		FileWriter fic;
		try {

			fic = new FileWriter(fiche);
			BufferedWriter fichero = new BufferedWriter(fic);
			// Recorro la colecci�n amigos:
			System.out.println(" - ----------------------------------------");
			List<Document> consulta = coleccion.find().into(new ArrayList<Document>());
			for (int i = 0; i < consulta.size(); i++) {
				System.out.println(" Grabo elemento  " + i + ", " + consulta.get(i).toString());
				fichero.write(consulta.get(i).toJson());
				fichero.newLine();
			}

			fichero.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}

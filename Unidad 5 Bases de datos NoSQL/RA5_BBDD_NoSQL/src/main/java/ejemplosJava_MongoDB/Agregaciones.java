package ejemplosJava_MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;

import static com.mongodb.client.model.Filters.*;

import java.util.Arrays;

import org.bson.Document;

public class Agregaciones {

	public static void main(String[] args) {
		MongoClient cliente = MongoClients.create() ;
		MongoDatabase db = cliente.getDatabase("mibasedatos");
		MongoCollection<Document> coleccion = db.getCollection("amigos");

		MediaPorCursoTodosCursos(coleccion);
		
		MediadeUncurso(coleccion, "1DAM");
		MediadeUncurso(coleccion, "XX");

		AlumnosdeUnCurso(coleccion, "1DAM");

		cliente.close();
	}//main

	// alumnos de un curso
	private static void AlumnosdeUnCurso(MongoCollection<Document> coleccion, String curso) {
		System.out.println("------ALUMNOS DEL CURSO: " + curso + "---------------");
		MongoCursor<Document> docs4 = coleccion.aggregate(Arrays.asList(match(eq("curso", curso)))).iterator();
		while (docs4.hasNext()) {
			Document docu = docs4.next();
			System.out.println("\tNombre: " + docu.get("nombre") + ", Telefono: " + docu.get("teléfono"));
		}
		docs4.close();

	}

	// Media de un curso
	private static void MediadeUncurso(MongoCollection<Document> coleccion, String curso) {
		System.out.println("------Nota media del curso: " + curso + "------------------");
		MongoCursor<Document> docs6 = coleccion
				.aggregate(Arrays.asList(match(eq("curso", curso)), group("$curso", avg("medianota", "$nota"))))
				.iterator();
		if (docs6.hasNext()) {
			Document docu = docs6.next();
			System.out.println(" Curso: " + curso);
			System.out.println("\tMedia: " + docu.get("medianota"));
		} else {
			System.out.println("\tEL CURSO NO EXISTE: ");
		}
		docs6.close();
	}

	// media por curso
	private static void MediaPorCursoTodosCursos(MongoCollection<Document> coleccion) {
		System.out.println(" - ---Nota media por curso -----------------");
		MongoCursor<Document> docs6 = coleccion
				.aggregate(Arrays.asList(group("$curso", sum("sumanota", "$nota"), avg("medianota", "$nota"))))
				.iterator();
		while (docs6.hasNext()) {
			Document docu = docs6.next();
			System.out.println(docu.toJson());
			System.out.println(" Curso: " + docu.getString("_id"));
			System.out.println("\tMedia: " + docu.get("medianota"));
			System.out.println("\tSuma: " + docu.get("sumanota"));
		}
		docs6.close();
	}

}//FIN

package impl;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import dao.EvaluacionesDAO;
import datos.Evaluaciones;
import factory.MongoDbDAOFactory;
import static com.mongodb.client.model.Filters.eq;

public class MongoDbEvaluacionesImpl implements EvaluacionesDAO {
	static MongoDatabase db;
	static MongoCollection<Document> alumnos;
	static MongoCollection<Document> cursos;
	static MongoCollection<Document> evaluaciones;

	public MongoDbEvaluacionesImpl() {
		db = MongoDbDAOFactory.crearConexion();
		alumnos = db.getCollection("alumnos");
		cursos = db.getCollection("cursos");
		evaluaciones = db.getCollection("evaluaciones");
	}

	@Override
	public ArrayList<Evaluaciones> DatosTodasLasEvaluaciones() {
		ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();

		try {
			// Obtener la colección "evaluaciones" en MongoDB
			MongoCollection<Document> collection = db.getCollection("evaluaciones");

			// Realizar la consulta para obtener todos los documentos en la colección
			FindIterable<Document> documents = collection.find();

			// Recorrer los documentos y agregar cada evaluación al ArrayList
			for (Document document : documents) {
				Evaluaciones eva = new Evaluaciones(
						document.getInteger("cod_evaluacion"),
						document.getInteger("num_alumno"),
						document.getInteger("cod_asig"),
						document.getDouble("nota"),
						document.getString("nombreasig"));
				evaluaciones.add(eva);
			}
		} catch (MongoException e) {
			// mensaje de error
			System.out.println("Error al obtener las evaluaciones");
		}

		return evaluaciones;
	}

	@Override
	public ArrayList<Evaluaciones> DatosUnaEvaluacion(int eva) {
		ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();

		try {
			// Obtener la colección "evaluaciones" en MongoDB
			MongoCollection<Document> collection = db.getCollection("evaluaciones");

			// Realizar la consulta para encontrar el documento que cumple con el filtro
			FindIterable<Document> documents = collection.find(eq("cod_evaluacion", eva));

			// Recorrer los documentos y agregar la evaluación al ArrayList
			for (Document document : documents) {
				Evaluaciones ev = new Evaluaciones(
						document.getInteger("cod_evaluacion"),
						document.getInteger("num_alumno"),
						document.getInteger("cod_asig"),
						document.getDouble("nota"),
						document.getString("nombreasig"));
				evaluaciones.add(ev);
			}

			if (evaluaciones.isEmpty()) {
				System.out.printf("Evaluación: %d No existe%n", eva);
			}
		} catch (MongoException e) {
			System.out.println("Error al obtener la evaluación");
		}

		return evaluaciones;
	}

	@Override
	public ArrayList<Evaluaciones> EvaluacionesAlumno(int eva, int codigo) {
		ArrayList<Evaluaciones> evaluaciones = new ArrayList<Evaluaciones>();

		try {
			// Obtener la colección "evaluaciones" en MongoDB
			MongoCollection<Document> collection = db.getCollection("evaluaciones");

			// Crear el filtro para buscar las evaluaciones con el código de evaluación y
			// número de alumno dados
			Bson filter = Filters.and(
					Filters.eq("cod_evaluacion", eva),
					Filters.eq("num_alumno", codigo));

			// Realizar la consulta para encontrar los documentos que cumplen con el filtro
			FindIterable<Document> documents = collection.find(filter);

			// Recorrer los documentos y agregar las evaluaciones al ArrayList
			for (Document document : documents) {
				Evaluaciones ev = new Evaluaciones(
						document.getInteger("cod_evaluacion"),
						document.getInteger("num_alumno"),
						document.getInteger("cod_asig"),
						document.getDouble("nota"),
						document.getString("nombreasig"));
				evaluaciones.add(ev);
			}

			if (evaluaciones.isEmpty()) {
				System.out.printf("Evaluación: %d No existe%n", eva);
			}
		} catch (MongoException e) {
			System.out.println("Error al obtener la evaluación");
		}

		return evaluaciones;
	}

}

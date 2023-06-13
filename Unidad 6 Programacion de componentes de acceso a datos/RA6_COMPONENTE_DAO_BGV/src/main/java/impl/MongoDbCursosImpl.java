package impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
// eq
import static com.mongodb.client.model.Filters.*;

import dao.CursosDAO;
import datos.Cursos;
import factory.MongoDbDAOFactory;

public class MongoDbCursosImpl implements CursosDAO {
	static MongoDatabase db;
	static MongoCollection<Document> alumnos;
	static MongoCollection<Document> cursos;
	static MongoCollection<Document> evaluaciones;

	public MongoDbCursosImpl() {
		db = MongoDbDAOFactory.crearConexion();
		alumnos = db.getCollection("alumnos");
		cursos = db.getCollection("cursos");
		evaluaciones = db.getCollection("evaluaciones");
	}

	@Override
	public int InsertarCurso(Cursos cur) {
		int resultado = 0;

		try {
			// Obtener la colección "cursos" en MongoDB
			MongoCollection<Document> collection = db.getCollection("cursos");

			// Crear el filtro para buscar el curso por el código
			Bson filter = Filters.eq("cod_curso", cur.getCod_curso());

			// Realizar la consulta para encontrar el documento que cumple con el filtro
			Document document = collection.find(filter).first();

			// Si existe el curso, devolver 1
			if (document != null) {
				resultado = 1;
			} else {
				// Si no existe, insertar el curso
				Document newCourse = new Document()
						.append("cod_curso", cur.getCod_curso())
						.append("denominacion", cur.getDenominacion())
						.append("num_alumnos", cur.getNum_alumnos())
						.append("nota_media", cur.getNota_media());

				collection.insertOne(newCourse);

				resultado = 0;
			}
		} catch (MongoException e) {
			System.out.println("Error al insertar el curso");
			resultado = -1;
		}

		return resultado;
	}

	public int EliminarCurso(int codigo) {
		int resultado = 0;

		try {
			// Obtener la colección "cursos" en MongoDB
			MongoCollection<Document> collection = db.getCollection("cursos");

			// Crear el filtro para buscar el curso por el código
			Bson filter = Filters.eq("cod_curso", codigo);

			// Realizar la consulta para encontrar el documento que cumple con el filtro
			Document document = collection.find(filter).first();

			// Si existe el curso
			if (document != null) {
				int numAlumnos = document.getInteger("num_alumnos");

				// Si tiene alumnos, devolver 2
				if (numAlumnos > 0) {
					resultado = 2;
				} else {
					// Si no tiene alumnos, eliminar el curso
					DeleteResult deleteResult = collection.deleteOne(filter);

					if (deleteResult.getDeletedCount() > 0) {
						resultado = 0;
					} else {
						resultado = -1;
					}
				}
			} else {
				// Si no existe, devolver 1
				resultado = 1;
			}
		} catch (MongoException e) {
			System.out.println("No se ha podido eliminar el curso");
			resultado = -1;
		}

		return resultado;
	}

	public int ModificarCurso(int codigo, Cursos nuevo) {
		int resultado = 0;

		try {
			// Obtener la colección "cursos" en MongoDB
			MongoCollection<Document> collection = db.getCollection("cursos");

			// Crear el filtro para buscar el curso por el código
			Bson filter = Filters.eq("cod_curso", codigo);

			// Realizar la consulta para encontrar el documento que cumple con el filtro
			Document document = collection.find(filter).first();

			// Si existe el curso, modificarlo
			if (document != null) {
				UpdateResult updateResult = collection.updateOne(filter,
						new Document("$set", new Document()
								.append("denominacion", nuevo.getDenominacion())
								.append("num_alumnos", nuevo.getNum_alumnos())
								.append("nota_media", nuevo.getNota_media())));

				if (updateResult.getModifiedCount() > 0) {
					resultado = 0;
				} else {
					resultado = -1;
				}
			} else {
				// Si no existe, devolver 1
				resultado = 1;
			}
		} catch (MongoException e) {
			System.out.println("No se ha podido modificar el curso");
			resultado = -1;
		}

		return resultado;
	}

	@Override
	public boolean ActualizarDatos() {
		boolean resultado = false;

		try {
			// Obtener la colección "alumnos" en MongoDB
			MongoCollection<Document> alumnos = db.getCollection("alumnos");

			// Obtener la colección "evaluaciones" en MongoDB
			MongoCollection<Document> evaluaciones = db.getCollection("evaluaciones");

			// Obtener la colección "cursos" en MongoDB
			MongoCollection<Document> cursos = db.getCollection("cursos");

			// Actualizar el campo "num_alumnos" en la colección "cursos"
			// primero pongo a 0 todos los num_alumnos de cada curso
			cursos.updateMany(new Document(), new Document("$set", new Document("num_alumnos", 0)));
			for (Document alumno : alumnos.find()) {
				int cod_curso = alumno.getInteger("cod_curso");
				cursos.updateOne(new Document("_id", cod_curso),
						new Document("$inc", new Document("num_alumnos", 1)));

				notaMediaCursos(cod_curso);
			}
			// Obtener los datos de nota_media en los cursos a partir de la colección
			// alumnos y evaluaciones
			// COnsulto todos los cursos
			// Primero pongo a 0 todas las notas medias de cada curso
			cursos.updateMany(new Document(), new Document("$set", new Document("nota_media", 0.0)));
			// Sumamos de cada evaluacion la nota de cada alumno y dividimos entre el numero
			// de notas

			resultado = true;
		} catch (MongoException e) {
			System.out.println("No se ha podido actualizar los datos");
			resultado = false;
		}
		return resultado;

	}

	private static void notaMediaCursos(int id) {
		int con = 0;
		double sumn = 0, notam = 0;
		List<Document> consulta = alumnos.find(eq("cod_curso", id)).into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			con++;
			sumn = sumn + consulta.get(i).getDouble("nota_media");
		}
		if (sumn <= 0 || con <= 0) {
			notam = 0;
		} else {
			notam = sumn / con;
		}
		System.out.println("Nota media: " + notam);
		double media = Math.round(notam * 100.0) / 100.0;
		cursos.updateOne(new Document("_id", id), new Document("$set", new Document("nota_media", media)));
	}

	@Override
	public Cursos ConsultarCurso(int codigo) {
		MongoCollection<Document> cursosCollection = db.getCollection("cursos");

		Document cursoDocument = cursosCollection.find(Filters.eq("cod_curso", codigo)).first();

		if (cursoDocument != null) {
			Cursos curso = new Cursos();
			curso.setCod_curso(cursoDocument.getInteger("_id"));
			curso.setDenominacion(cursoDocument.getString("denominacion"));
			curso.setNum_alumnos(cursoDocument.getInteger("num_alumnos"));
			curso.setNota_media(cursoDocument.getDouble("nota_media"));
			return curso;
		} else {
			System.out.println("No existe el curso " + codigo);
			return null;
		}
	}

	@Override
	public ArrayList<Cursos> TodosLosCursos() {
		MongoCollection<Document> cursosCollection = db.getCollection("cursos");

		ArrayList<Cursos> cursos = new ArrayList<>();

		FindIterable<Document> cursoDocuments = cursosCollection.find();

		for (Document cursoDocument : cursoDocuments) {
			Cursos curso = new Cursos();
			curso.setCod_curso(cursoDocument.getInteger("_id"));
			curso.setDenominacion(cursoDocument.getString("denominacion"));
			curso.setNum_alumnos(cursoDocument.getInteger("num_alumnos"));
			curso.setNota_media(cursoDocument.getDouble("nota_media"));
			cursos.add(curso);
		}

		return cursos;
	}

}

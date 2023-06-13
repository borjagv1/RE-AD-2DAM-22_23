package impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.result.UpdateResult;

import dao.AlumnosDAO;
import datos.Alumnos;
import factory.MongoDbDAOFactory;

public class MongoDbAlumnosImpl implements AlumnosDAO {
	static MongoDatabase db;
	static MongoCollection<Document> alumnos;
	static MongoCollection<Document> cursos;
	static MongoCollection<Document> evaluaciones;

	public MongoDbAlumnosImpl() {
		db = MongoDbDAOFactory.crearConexion();
		alumnos = db.getCollection("alumnos");
		cursos = db.getCollection("cursos");
		evaluaciones = db.getCollection("evaluaciones");
	}

	@Override
	public int InsertarAlumno(Alumnos alum) {
		int resultado = 0;
		// Comprobar que no existe el alumno
		Document filtro = new Document("num_alumno", alum.getNum_alumno());
		MongoCollection<Document> coleccion = db.getCollection("alumnos");
		Document alumnoExistente = coleccion.find(filtro).first();

		if (alumnoExistente != null) {
			resultado = 1;
			System.out.println("El alumno ya existe: " + alum.getNum_alumno());
		} else {
			// Si no existe, insertar el alumno
			Document nuevoAlumno = new Document()
					.append("num_alumno", alum.getNum_alumno())
					.append("cod_curso", alum.getCod_curso())
					.append("nombre", alum.getNombre())
					.append("direccion", alum.getDireccion())
					.append("tlf", alum.getTlf())
					.append("nota_media", alum.getNota_media());

			try {
				coleccion.insertOne(nuevoAlumno);
				resultado = 0;
			} catch (MongoException e) {
				System.out.println("Error al insertar el alumno: " + e.getMessage());
				resultado = -1;
			}
		}
		return resultado;
	}

	@Override
	public int EliminarAlumno(int codigo) {
		int resultado = 0;
		// Comprobar que existe el alumno
		Document filtro = new Document("num_alumno", codigo);
		MongoCollection<Document> coleccion = db.getCollection("alumnos");
		Document alumno = coleccion.find(filtro).first();

		if (alumno != null) {
			// Si tiene notas, devolver 2
			if (alumno.getDouble("nota_media") != 0) {
				resultado = 2;
				System.out.println("El alumno tiene notas: " + codigo);
			} else {
				// Si no tiene notas, eliminar el alumno
				coleccion.deleteOne(filtro);
				resultado = 0;
			}
		} else {
			// Si no existe, devolver 1
			resultado = 1;
		}

		return resultado;
	}

	@Override
	public int ModificarAlumno(int codigo, Alumnos nuevo) {
		int resultado = 0;
		// Comprobar que existe el alumno
		Document filtro = new Document("num_alumno", codigo);
		MongoCollection<Document> coleccion = db.getCollection("alumnos");
		Document alumno = coleccion.find(filtro).first();

		if (alumno != null) {
			// Modificar el alumno
			Document datosModificados = new Document()
					.append("cod_curso", nuevo.getCod_curso())
					.append("nombre", nuevo.getNombre())
					.append("direccion", nuevo.getDireccion())
					.append("tlf", nuevo.getTlf())
					.append("nota_media", nuevo.getNota_media());

			Document actualizacion = new Document("$set", datosModificados);
			coleccion.updateOne(filtro, actualizacion);

			resultado = 0;
		} else {
			// Si no existe, devolver 1
			resultado = 1;
			System.out.println("El alumno no existe: " + codigo);
		}

		return resultado;
	}

	@Override
public boolean ActualizarDatos() {
    // Calcula el campo nota_media para cada alumno a partir de la colección evaluaciones
    boolean resultado = false;
    MongoCollection<Document> coleccionEvaluaciones = db.getCollection("evaluaciones");
    AggregateIterable<Document> aggregation = coleccionEvaluaciones.aggregate(Arrays.asList(
        Aggregates.group("$num_alumno", Accumulators.avg("nota_media", "$nota"))
    ));
    
    MongoCollection<Document> coleccionAlumnos = db.getCollection("alumnos");
    for (Document document : aggregation) {
        int num_alumno = document.getInteger("_id");
        double nota_media = document.getDouble("nota_media");
        
        Document filtro = new Document("num_alumno", num_alumno);
        Document actualizacion = new Document("$set", new Document("nota_media", nota_media));
        UpdateResult updateResult = coleccionAlumnos.updateOne(filtro, actualizacion);
        
        if (updateResult.getModifiedCount() > 0) {
            resultado = true;
        } else {
            resultado = false;
        }
    }
    
    return resultado;
}

	@Override
public Alumnos ConsultarAlumno(int codigo) {
    Alumnos alumno = null;
    Document filtro = new Document("num_alumno", codigo);
    MongoCollection<Document> coleccion = db.getCollection("alumnos");
    Document documento = coleccion.find(filtro).first();
    
    if (documento != null) {
        alumno = new Alumnos();
        alumno.setNum_alumno(documento.getInteger("num_alumno"));
        alumno.setCod_curso(documento.getInteger("cod_curso"));
        alumno.setNombre(documento.getString("nombre"));
        alumno.setDireccion(documento.getString("direccion"));
        alumno.setTlf(documento.getString("tlf"));
        alumno.setNota_media(documento.getDouble("nota_media"));
    } else {
        // Si no existe, devolver un mensaje
        System.out.println("No existe el alumno con código " + codigo);
    }
    
    return alumno;
}

@Override
public ArrayList<Alumnos> TodosLosAlumnos() {
    ArrayList<Alumnos> lista = new ArrayList<>();
    MongoCollection<Document> coleccion = db.getCollection("alumnos");
    FindIterable<Document> documentos = coleccion.find();
    
    for (Document documento : documentos) {
        Alumnos alumno = new Alumnos();
        alumno.setNum_alumno(documento.getInteger("num_alumno"));
        alumno.setCod_curso(documento.getInteger("cod_curso"));
        alumno.setNombre(documento.getString("nombre"));
        alumno.setDireccion(documento.getString("direccion"));
        alumno.setTlf(documento.getString("tlf"));
        alumno.setNota_media(documento.getDouble("nota_media"));
        lista.add(alumno);
    }
    
    return lista;
}

}

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.inc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Ejercicios {
	static MongoClient cliente = new MongoClient();
	static MongoDatabase db = cliente.getDatabase("MarioGarciaBlanco");
	static Scanner sc = new Scanner(System.in);
	static MongoCollection<Document> alumnos = db.getCollection("alumnos");
	static MongoCollection<Document> asignaturas = db.getCollection("asignaturas");
	static MongoCollection<Document> cursos = db.getCollection("cursos");
	static MongoCollection<Document> evaluaciones = db.getCollection("evaluaciones");

	public static void main(String[] args) {
		System.out.println("Introduce el numero del ej: (1 o 2)");
		int ej = sc.nextInt();
		switch (ej) {
		case 1:
			ActualizarCampos();
			break;
		case 2:
			Listado();
			break;
		default:
			System.out.println("Error");
			break;
		}
	}

	private static void Listado() {
		while (true) {
			System.out.print("Introduce un id de alumno: ");
			int id = sc.nextInt();
			if(id == 0) {
				break;
			}
			Document consulta = alumnos.find(eq("_id", id)).first();
			try {
				double media = Math.round(consulta.getDouble("nota_media") * 100.0) / 100.0;
				System.out.printf("Alumno: %s , %s\nCurso: %s , %s     Nota media: %.2f %n", id,
						consulta.getString("nombre"), consulta.getInteger("cod_curso"),
						consulta.getString("nombre_curso"), media);
			} catch (NullPointerException e) {
				System.out.println("NO EXISTE EL ID DEL ALUMNO");
				System.exit(0);
			}

			List<Document> consultaeva1 = evaluaciones.find(and(eq("num_alumno", id), eq("cod_evaluacion", 1)))
					.into(new ArrayList<Document>());
			if (consultaeva1.size() != 0) {
				System.out.println("NOTAS PRIMERA EVALUACION");
				System.out.println("ASIGNATURA             NOTA");
				System.out.println("===================== =====");
				for (int i = 0; i < consultaeva1.size(); i++) {
					System.out.printf("%-21s %5.2f %n", consultaeva1.get(i).getString("nombreasig"),
							consultaeva1.get(i).getDouble("nota"));
				}
			} else {
				System.out.println("LA CONSULTA NO HA DEVUELTO DATOS");
			}
			List<Document> consultaeva2 = evaluaciones.find(and(eq("num_alumno", id), eq("cod_evaluacion", 2)))
					.into(new ArrayList<Document>());
			if (consultaeva2.size() != 0) {
				System.out.println("NOTAS SEGUNDA EVALUACION");
				System.out.println("ASIGNATURA             NOTA");
				System.out.println("===================== =====");
				for (int i = 0; i < consultaeva2.size(); i++) {
					System.out.printf("%-21s %5.2f %n", consultaeva2.get(i).getString("nombreasig"),
							consultaeva2.get(i).getDouble("nota"));
				}
			} else {
				System.out.println("LA CONSULTA NO HA DEVUELTO DATOS");
			}
			List<Document> consultaeva3 = evaluaciones.find(and(eq("num_alumno", id), eq("cod_evaluacion", 3)))
					.into(new ArrayList<Document>());
			if (consultaeva3.size() != 0) {
				System.out.println("NOTAS TERCERA EVALUACION");
				System.out.println("ASIGNATURA             NOTA");
				System.out.println("===================== =====");
				for (int i = 0; i < consultaeva3.size(); i++) {
					System.out.printf("%-21s %5.2f %n", consultaeva3.get(i).getString("nombreasig"),
							consultaeva3.get(i).getDouble("nota"));
				}
			} else {
				System.out.println("LA CONSULTA NO HA DEVUELTO DATOS");
			}
			System.out.printf("\nNota media total: %5.2f %n", consulta.getDouble("nota_media"));
		}
	}

	private static void ActualizarCampos() {
		actualizarAlumnos();
		actualizarEvaluaciones();
		actualizarCursos();
	}

	private static void actualizarCursos() {
		System.out.println("Actualizando cursos: ");
		List<Document> consulta = cursos.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			int id = consulta.get(i).getInteger("_id");
			numAlumnos(id);
			notaMediaCursos(id);
			System.out.println("Curso Actualizado:" + id + ", Nº Alumnos: " + consulta.get(i).getInteger("num_alumnos")
					+ ", Media: " + consulta.get(i).getDouble("nota_media"));
		}
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
		cursos.updateOne(eq("_id", id), set("nota_media", media));
	}

	private static void numAlumnos(int id) {
		int i = 0;
		List<Document> consulta = alumnos.find(eq("cod_curso", id)).into(new ArrayList<Document>());
		for (i = 0; i < consulta.size(); i++) {
			cursos.updateOne(eq("_id", id), inc("num_alumnos", 1));
		}
		System.out.println("NumAlumnos: " + i);
	}

	private static void actualizarEvaluaciones() {
		System.out.println("Actualizando evaluaciones: ");
		List<Document> consulta = evaluaciones.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			int id = consulta.get(i).getInteger("cod_evaluacion");
			int coda = consulta.get(i).getInteger("cod_asig");
			int num_alum = consulta.get(i).getInteger("num_alumno");
			sacarAsig(id, coda, num_alum);
		}
	}

	private static void sacarAsig(int id, int coda, int num_alum) {
		Document consulta = asignaturas.find(eq("_id", coda)).first();
		String nombrea = consulta.getString("nombre");
		System.out.println("Num_alumno" + num_alum + " Cod Asig: " + coda + " Nombre Asig: " + nombrea);
		evaluaciones.updateOne(and(eq("cod_evaluacion", id), eq("num_alumno", num_alum), eq("cod_asig", coda)),
				set("nombreasig", nombrea));
	}

	private static void actualizarAlumnos() {
		System.out.println("Actualizando alumnos: ");
		List<Document> consulta = alumnos.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			int id = consulta.get(i).getInteger("_id");
			int idc = consulta.get(i).getInteger("cod_curso");
			sacarNotas(id);
			sacarCurso(idc, id);
			System.out.println();
		}

	}

	private static void sacarCurso(int idc, int id) {
		System.out.println("Actualizando nombre de curso: ");
		Document consulta = cursos.find(eq("_id", idc)).first();
		String nombrec = consulta.getString("denominacion");
		System.out.println("Alumno: " + id + " Nombre Curso:" + nombrec);
		alumnos.updateOne(eq("_id", id), set("nombre_curso", nombrec));
	}

	private static void sacarNotas(int id) {
		System.out.println("Actualizando Nota media: ");
		double sumn = 0, notam = 0;
		int con = 0;
		List<Document> consulta = evaluaciones.find(eq("num_alumno", id)).into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			con++;
			sumn = sumn + consulta.get(i).getDouble("nota");
		}
		if (sumn <= 0 || con <= 0) {
			notam = 0;
		} else {
			notam = sumn / con;
		}
		System.out.println("Id: " + id + " Media: " + notam);
		double media = Math.round(notam * 100.0) / 100.0;
		alumnos.updateOne(eq("_id", id), set("nota_media", media));
	}

}

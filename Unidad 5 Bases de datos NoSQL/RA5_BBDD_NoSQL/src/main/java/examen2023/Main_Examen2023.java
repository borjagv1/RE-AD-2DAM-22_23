package examen2023;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
/*COLECCION ALUMNOS:
int _id; // identificación del alumno, campo clave
int cod_curso; // código del curso
String nombre; // nombre de alumno
String direccion;
String tlf;
double nota_media; // inicialmente 0
String nombre_curso; // nombre del curso, inicialmente vacio
Colección cursos: contiene los cursos que hay. Cada documento representa un curso y se identifica por el campo _id. Los campos son:
int _id; // código del curso, id del curso, campo clave
String denominacion; // nombre del curso
int num_alumnos; // número de alumnos en el curso, inicialmente 0
double nota_media; // nota media del curso, inicialmente 0
Colección asignaturas: contiene las asignaturas que hay, cada documento representa una asignatura y se identifica por el campo _id. Los campos son:
_id //identificación de la asignatura, campo clave
String nombre; //nombre de la asignatura
Colección evaluaciones: contiene las notas de los alumnos en las evaluaciones. Cada documento representa una nota de un alumno en una asignatura y evaluación. El campo _id se genera automáticamente. Los campos que contiene son los siguientes:
_id //identificación del documento, generado al crearlo
int cod_evaluacion; // 1, 2 o 3 , dependiendo de la evaluación que sea
int num_alumno; //identificación del alumno
int cod_asig; //identificación de la asignatura
double nota; //nota
String nombreasig; //nombre de la asignatura, inicialmente está vacia */

public class Main_Examen2023 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("bgv_examen2023");
        MongoCollection<Document> coleccionAlumnos = db.getCollection("alumnos");
        MongoCollection<Document> coleccionAsignaturas = db.getCollection("asignaturas");
        MongoCollection<Document> coleccionCursos = db.getCollection("cursos");
        MongoCollection<Document> coleccionEvaluaciones = db.getCollection("evaluaciones");

        actualizarAlumnos_notaMedia_y_nombreAsignatura(coleccionAlumnos,
        coleccionCursos, coleccionEvaluaciones);

        actualizarEvaluaciones_nombreAsignatura(coleccionAsignaturas, coleccionEvaluaciones);

        actualizarCursos_numAlumnos_y_notaMedia(coleccionAlumnos, coleccionCursos);

        consultas_alumnos_notas_evaluaciones(coleccionAlumnos, coleccionEvaluaciones);

        cliente.close();

    }

    private static void consultas_alumnos_notas_evaluaciones(MongoCollection<Document> coleccionAlumnos,
            MongoCollection<Document> coleccionEvaluaciones) {
        // Proceso repetitivo en el que se pide por teclado el ID de alumno y se
        // muestran las notas de las asignaturas en cada evaluación. Si el id del alumno
        // no existe en la colección de alumnos se mostrará un mensaje indicándolo.
        // El proceso termina cuando se introduce un id = 0
        System.out.println("===================================================================");
        int id = leerEntero("Introduce id del alumno: ", 0, 9999);
        System.out.println("-------------------------------------------------------------------");
        while (id != 0) {
            Document alumno = coleccionAlumnos.find(eq("_id", id)).first();
            if (alumno == null) {
                System.out.println("\t<< NO EXISTE EL ALUMNO CON ID " + id + " >>");
            } else {
                // Formateo la nota media del curso a 2 decimales y con , en vez de .
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                String mediaFormateada = decimalFormat.format(alumno.getDouble("nota_media"));
                System.out.println("ID: " + alumno.getInteger("_id") + " Nombre: " + alumno.getString("nombre")
                        + "\nCurso: " + alumno.getInteger("cod_curso") + " Nombre curso: "
                        + alumno.getString("nombre_curso") + " Nota media curso: " + mediaFormateada);
                System.out.println("-------------------------------------------------------------------");
                boolean existeAlumnoEnEvaluaciones = coleccionEvaluaciones.find(eq("num_alumno", id)).first() != null;
                if (existeAlumnoEnEvaluaciones) {
                    System.out.println("NOTAS PRIMERA EVALUACIÓN:");
                    // ASIGNATURA NOTA ===================== =====================
                    System.out.printf("\t%-15s %-15s%n", "ASIGNATURA", "NOTA");
                    System.out.printf("\t%-15s %-15s%n", "==========", "====");
                    for (Document evaluacion : coleccionEvaluaciones
                            .find(and(eq("num_alumno", id), eq("cod_evaluacion", 1)))) {

                        System.out.printf("\t%-15s %-15s%n", evaluacion.getString("nombreasig"),
                                evaluacion.getDouble("nota"));

                    }
                    System.out.println("NOTAS SEGUNDA EVALUACIÓN:");
                    // ASIGNATURA NOTA ===================== =====================
                    System.out.printf("\t%-15s %-15s%n", "ASIGNATURA", "NOTA");
                    System.out.printf("\t%-15s %-15s%n", "==========", "====");
                    for (Document evaluacion : coleccionEvaluaciones
                            .find(and(eq("num_alumno", id), eq("cod_evaluacion", 2)))) {

                        System.out.printf("\t%-15s %-15s%n", evaluacion.getString("nombreasig"),
                                evaluacion.getDouble("nota"));

                    }
                    System.out.println("NOTAS TERCERA EVALUACIÓN:");
                    // ASIGNATURA NOTA ===================== =====================
                    System.out.printf("\t%-15s %-15s%n", "ASIGNATURA", "NOTA");
                    System.out.printf("\t%-15s %-15s%n", "==========", "====");
                    for (Document evaluacion : coleccionEvaluaciones
                            .find(and(eq("num_alumno", id), eq("cod_evaluacion", 3)))) {

                        System.out.printf("\t%-15s %-15s%n", evaluacion.getString("nombreasig"),
                                evaluacion.getDouble("nota"));

                    }
                } else {
                    System.out.println("\t<< EL ALUMNO NO TIENE NOTAS >>");
                }
            }
            System.out.println("===================================================================");
            id = leerEntero("Introduce id del alumno: ", 0, 9999);

        }
    }

    private static void actualizarCursos_numAlumnos_y_notaMedia(MongoCollection<Document> coleccionAlumnos,
            MongoCollection<Document> coleccionCursos) {
        // Actualizar en la colección de cursos: El campo num_alumnos para que ALMACENE
        // EL NUMERO DE ALUMNOS EN EL CURSO
        System.out.println("Actualización del número de alumnos en el curso");
        // primero pongo a 0 el número de alumnos en el curso
        coleccionCursos.updateMany(exists("num_alumnos", true), set("num_alumnos", 0));
        // ahora calculo el número de alumnos en el curso
        for (Document alumno : coleccionAlumnos.find()) {
            int cod_curso = alumno.getInteger("cod_curso");
            coleccionCursos.updateOne(eq("_id", cod_curso), inc("num_alumnos", 1));

            // int num_alumnos = coleccionAlumnos.find(eq("cod_curso", cod_curso)).into(new
            // ArrayList<>()).size();
            // coleccionCursos.updateOne(eq("_id", cod_curso), set("num_alumnos",
            // num_alumnos));

            // muestro un mensaje con el número de alumnos en el curso
            System.out.println("ID: " + cod_curso + " Número de alumnos: " + coleccionAlumnos
                    .find(eq("cod_curso", cod_curso)).into(new ArrayList<>()).size());
        }

        // calculo la nota media de cada curso: que será igual a la suma de las notas
        // medias de los alumnos del curso dividido entre el número de alumnos del
        // curso.
        System.out.println("Actualización de la nota media del curso");
        // primero pongo a 0 la nota media del curso
        coleccionCursos.updateMany(exists("nota_media", true), set("nota_media", 0.0));
        // ahora calculo la nota media del curso
        int control2 = 0;
        for (Document alumno : coleccionAlumnos.find()) {
            int cod_curso = alumno.getInteger("cod_curso");
            // calcular la nota media del curso que es la suma de las notas medias de los
            // alumnos del curso dividido entre el número de alumnos del curso.
            double media = coleccionAlumnos.aggregate(
                    Arrays.asList(
                            match(eq("cod_curso", cod_curso)),
                            group("$cod_curso", avg("nota_media", "$nota_media"))))
                    .first().getDouble("nota_media");

            // formateo double media para que solo tenga 2 decimales y ponga , en vez de .
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String mediaFormateada = decimalFormat.format(media);
            // si el curso es distinto al anterior, muestro su nota media
            if (control2 != cod_curso) {
                System.out.println("ID: " + cod_curso + " Nota media: " + mediaFormateada);

                coleccionCursos.updateOne(eq("_id", cod_curso), set("nota_media", media));
                control2 = cod_curso;
            }

        }
    }

    private static void actualizarEvaluaciones_nombreAsignatura(MongoCollection<Document> coleccionAsignaturas,
            MongoCollection<Document> coleccionEvaluaciones) {
        // Actualizar en la colección de evaluaciones: El campo nombreasig para que
        // contenga el nombre de la asignatura.
        System.out.println("Actualización de nombre asignatura");
        // primero pongo a null el nombre de la asignatura
        coleccionEvaluaciones.updateMany(exists("nombreasig", true), set("nombreasig", ""));
        for (Document eval : coleccionEvaluaciones.find()) {
            int cod_asig = eval.getInteger("cod_asig");
            int cod_eval = eval.getInteger("cod_evaluacion");
            String nombre_asig = coleccionAsignaturas.find(eq("_id", cod_asig)).first().getString("nombre");
            int num_alumno = eval.getInteger("num_alumno");
            // Actualizar nombre de asignatura en la coleccion de evaluaciones
            // por cada alumno, actualizo el nombre de la asignatura
            coleccionEvaluaciones.updateOne(and(eq("cod_asig", cod_asig), eq("cod_evaluacion", cod_eval)),
                    set("nombreasig", nombre_asig));

            System.out.println("Alumno: " + num_alumno + " Cod_Asig: " + eval.getInteger("cod_asig")
                    + " Nombre asignatura: " + nombre_asig);
        }
        // lo mismo para la evaluación 2:

    }

    private static void actualizarAlumnos_notaMedia_y_nombreAsignatura(MongoCollection<Document> coleccionAlumnos,
            MongoCollection<Document> coleccionCursos, MongoCollection<Document> coleccionEvaluaciones) {
        // Actualizar en la colección de alumnos:
        System.out.println("Actualización de la colección de alumnos");
        // • El campo nota_media para que contenga la nota media total del alumno que
        // será igual a la suma de las notas que tiene dividido entre el número de
        // notas.
        // primero pongo a 0 todas las notas medias
        System.out.println("Actualizando las notas medias de los alumnos");
        coleccionAlumnos.updateMany(exists("nota_media", true), set("nota_media", 0.0));
        int control = 0;
        // ahora calculo la nota media de cada alumno
        for (Document eval : coleccionEvaluaciones.find()) {
            int num_alumno = eval.getInteger("num_alumno");
            // calcular la nota media del alumno que es la suma de las notas que tiene
            // dividido entre el número de notas
            double media = coleccionEvaluaciones.aggregate(
                    Arrays.asList(
                            match(eq("num_alumno", num_alumno)),
                            group("$num_alumno", avg("nota", "$nota"))))
                    .first().getDouble("nota");

            // formateo double media para que solo tenga 2 decimales y ponga , en vez de .
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String mediaFormateada = decimalFormat.format(media);
            // si el alumno es distinto al anterior, muestro su nota media
            if (control != num_alumno) {
                System.out.println("ID: " + num_alumno + " Nota media: " + mediaFormateada);

                coleccionAlumnos.updateOne(eq("_id", num_alumno), set("nota_media", media));
                control = num_alumno;
            }

        }
        System.out.println("Actualización de nombre curso");
        // El campo nombre_curso para que contenga el nombre del curso del alumno.
        for (Document alumno : coleccionAlumnos.find()) {
            int cod_curso = alumno.getInteger("cod_curso");
            String nombre_curso = coleccionCursos.find(eq("_id", cod_curso)).first().getString("denominacion");
            coleccionAlumnos.updateOne(eq("cod_curso", cod_curso), set("nombre_curso", nombre_curso));
            System.out.println("ID: " + alumno.getInteger("_id") + " Nombre curso: " + nombre_curso);
        }
    }

    private static int leerEntero(String mensaje, int min, int max) {
        boolean salir = false;
        int numero = 0;

        do {
            try {
                System.out.print(mensaje);
                numero = sc.nextInt();
                sc.nextLine();
                while (numero < min || numero > max) {
                    System.out.print("\tSuperado l mite (> " + min + " y < " + max + ")");
                    System.out.print("\n\tOtra vez: ");

                    numero = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                }
                salir = true;

            } catch (InputMismatchException exc) {
                sc.nextLine();
                System.out.print("\n\tIncorrecto, escríbelo de nuevo: ");
            }
        } while (!salir);

        return numero;
    }// LecturaEntero

}

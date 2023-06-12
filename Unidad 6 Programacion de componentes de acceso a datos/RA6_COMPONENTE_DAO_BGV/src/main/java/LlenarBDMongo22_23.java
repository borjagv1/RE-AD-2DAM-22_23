
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import datos.Alumnos;
import datos.Asignaturas;
import datos.Cursos;
import datos.Evaluaciones;
import java.util.ArrayList;

/**
 *
 * @author mjrm2
 */
public class LlenarBDMongo22_23 {

    static MongoClient cliente = new MongoClient();
    static MongoDatabase db = cliente.getDatabase("ra6");
   
    public static void main(String[] args) {        
        llenarCursos();
        llenarAlumnos();
        llenarAsignaturas();
        llenarEvaluaciones();

        System.out.println("Fin creación de la BD......");
        cliente.close();
    }

    private static void llenarCursos() {

        MongoCollection<Document> coleccion = db.getCollection("cursos");
        // creo la colección. Si existe la borro y la creo de nuevo.
        try {
            db.createCollection("cursos");
        } catch (MongoCommandException ex) {
            coleccion.drop();
            db.createCollection("cursos");
        }

        ArrayList<Cursos> lista = new ArrayList();

        lista.add(new Cursos(10, "1ESO", 0, 0.0));
        lista.add(new Cursos(11, "2ESO", 0, 0.0));
        lista.add(new Cursos(12, "3ESO", 0, 0.0));
        lista.add(new Cursos(20, "4ESO", 0, 0.0));
        lista.add(new Cursos(21, "FPB", 0, 0.0));
        lista.add(new Cursos(30, "1BACH", 0, 0.0));
        lista.add(new Cursos(31, "2BACH", 0, 0.0));
        lista.add(new Cursos(50, "1DAM", 0, 0.0));
        lista.add(new Cursos(51, "2DAM", 0, 0.0));
        lista.add(new Cursos(52, "1SMR", 0, 0.0));
        lista.add(new Cursos(53, "2SMR", 0, 0.0));
        lista.add(new Cursos(54, "1ASIR", 0, 0.0));
        lista.add(new Cursos(55, "2ASIR", 0, 0.0));

        //int cod_curso, String denominacion, int num_alumnos, double nota_media)
        for (Cursos c : lista) {
            Document res = new Document();
            res.put("_id", c.getCod_curso());
            res.put("denominacion", c.getDenominacion());
            res.put("num_alumnos", c.getNum_alumnos());
            res.put("nota_media", c.getNota_media());

            coleccion.insertOne(res);
            System.out.println("Curso grabado: " + c);
        }

    }//llenarCursos

    private static void llenarAlumnos() {
        MongoCollection<Document> coleccion = db.getCollection("alumnos");
        // creo la colección. Si existe la borro y la creo de nuevo.
        try {
            db.createCollection("alumnos");
        } catch (MongoCommandException ex) {
            coleccion.drop();
            db.createCollection("alumnos");
        }

        ArrayList<Alumnos> lista = new ArrayList();

        //23 Alumnos: 
        lista.add(new Alumnos(1010, 10, "JUAN GOMEZ MAR", "TALAVERA", "645666777", 0.0));
        lista.add(new Alumnos(1011, 11,  "MARIA GOMEZ ABRIL", "TALAVERA", "788999000", 0.0));
        lista.add(new Alumnos(1012, 12,  "ALBERTO SÁNCHEZ MORENO", "TOLEDO", "639009988", 0.0));
        lista.add(new Alumnos(1020, 20,  "PILAR RAMOS BERT", "TALAVERA", "64444456", 0.0));
        lista.add(new Alumnos(1021, 21,  "HILDA GARCÍA ROMERO", "TALAVERA", "645789098", 0.0));
        lista.add(new Alumnos(1030, 30,  "CARLOS RAMOS MARTÍN", "OROPESA", "644554433", 0.0));
        lista.add(new Alumnos(1031, 31,  "PABLO SOLIS CARRETERO", "CALERUELA", "678876543", 0.0));
        lista.add(new Alumnos(1112, 12,  "JUANA GIL TRABADO", "TALAVERA", "925555555", 0.0));
        lista.add(new Alumnos(1120, 20,  "MARTA SERRANO SUELA", "TALAVERA", "63344996", 0.0));
        lista.add(new Alumnos(1121, 21,  "ANTONI DE LAS HERAS", "NAVALCÁN", "678097654", 0.0));
        lista.add(new Alumnos(1130, 30,  "FERNANDO CORREGIDOR", "TALAVERA", "654332244", 0.0));
        lista.add(new Alumnos(1131, 31,  "JOSE MARÍA MANZANO", "GAMONAL", "645009988", 0.0));
        lista.add(new Alumnos(1212, 12,  "RAMÓN GARCÍA PEREZ", "TALAVERA", "639009988", 0.0));
        lista.add(new Alumnos(1220, 20,  "MENODORA PANIAGUA", "TALAVERA", "73344996", 0.0));
        lista.add(new Alumnos(1221, 21,  "IVAN CARRASCO SOLA", "TALAVERA", "777888999", 0.0));
        lista.add(new Alumnos(1231, 31,  "FÁTIMA GARCÍA SÁNCHEZ", "TALAVERA", "654009906", 0.0));
        lista.add(new Alumnos(1321, 21,  "ALICIA MANZANO PEREZ", "OROPESA", "234234567", 0.0));
        lista.add(new Alumnos(1331, 31,  "JUAN PEDRO RIERA GRAU", "OROPESA", "565443322", 0.0));
        lista.add(new Alumnos(1521, 21,  "CRISTINA SABROSO FRAILE", "TALAVERA", "639765432", 0.0));
        lista.add(new Alumnos(1630, 30,  "ANTONIA GOMEZ SANCHEZ", "TALAVERA", "645789099", 0.0));
        lista.add(new Alumnos(2010, 10,  "ALBERTO RAMOS PEREZ", "TOLEDO", "657777888", 0.0));
        lista.add(new Alumnos(2011, 11,  "ALICIA PEREZ AMOR", "TALAVERA", "636777888", 0.0));
        lista.add(new Alumnos(3010, 10,  "ANA MORENO GARCIA", "TALAVERA", "925323456", 0.0));

        //(int num_alumno, int cod_curso, String nombre, String direccion, String tlf, double nota_media) {
        for (Alumnos c : lista) {
            Document res = new Document();
            res.put("_id", c.getNum_alumno());
            res.put("cod_curso", c.getCod_curso());
            res.put("nombre", c.getNombre());
            res.put("direccion", c.getDireccion());
            res.put("tlf", c.getTlf());
            res.put("nota_media", c.getNota_media());

            coleccion.insertOne(res);
            System.out.println("Alumno grabado: " + c);
        }

    }

    private static void llenarAsignaturas() {
        MongoCollection<Document> coleccion = db.getCollection("asignaturas");
        // creo la colección. Si existe la borro y la creo de nuevo.
        try {
            db.createCollection("asignaturas");
        } catch (MongoCommandException ex) {
            coleccion.drop();
            db.createCollection("asignaturas");
        }

        ArrayList<Asignaturas> lista = new ArrayList();

        //24 Asignaturas: 
        lista.add(new Asignaturas(1, "CIENCIAS1ESO"));
        lista.add(new Asignaturas(2, "MATES1ESO"));
        lista.add(new Asignaturas(3, "LENGUA 1"));
        lista.add(new Asignaturas(4, "VOCABULARIO 1"));
        lista.add(new Asignaturas(5, "CIENCIAS2ESO"));
        lista.add(new Asignaturas(6, "MATES2ESO"));
        lista.add(new Asignaturas(7, "LENGUA II"));
        lista.add(new Asignaturas(8, "TRADUCCIÓN"));
        lista.add(new Asignaturas(9, "BIOLOGÍA 3"));
        lista.add(new Asignaturas(10, "APLICADAS I"));
        lista.add(new Asignaturas(11, "LENGUA III"));
        lista.add(new Asignaturas(12, "BIOLOGÍA 4"));
        lista.add(new Asignaturas(13, "ESTADISTICAS"));
        lista.add(new Asignaturas(14, "ANALISIS LINGÜISTICO"));
        lista.add(new Asignaturas(15, "MONTAJES"));
        lista.add(new Asignaturas(16, "INSTALACIONES"));
        lista.add(new Asignaturas(17, "CIENCIASBACH1"));
        lista.add(new Asignaturas(18, "LITERATURA"));
        lista.add(new Asignaturas(19, "APLICADAS BACH"));
        lista.add(new Asignaturas(20, "LATINGRIEGO"));
        lista.add(new Asignaturas(21, "ANATOMIA"));
        lista.add(new Asignaturas(22, "LITERATURA II"));
        lista.add(new Asignaturas(23, "GEOMETRÍA"));
        lista.add(new Asignaturas(24, "ESTADÍSTICAS II"));

        for (Asignaturas c : lista) {
            Document res = new Document();
            res.put("_id", c.getCod_asig());
            res.put("nombre", c.getNombre());

            coleccion.insertOne(res);
            System.out.println("Asignatura grabada: " + c);
        }

    }

    private static void llenarEvaluaciones() {
        MongoCollection<Document> coleccion = db.getCollection("evaluaciones");
        // creo la colección. Si existe la borro y la creo de nuevo.
        try {
            db.createCollection("evaluaciones");
        } catch (MongoCommandException ex) {
            coleccion.drop();
            db.createCollection("evaluaciones");
        }

        ArrayList<Evaluaciones> lista = new ArrayList();

        //179 Evaluaciones: 
        lista.add(new Evaluaciones(1, 1010, 1, 4.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(1, 1010, 2, 7.0, "MATES1ESO"));
        lista.add(new Evaluaciones(1, 1010, 3, 5.0, "LENGUA 1"));
        lista.add(new Evaluaciones(1, 1010, 4, 6.0, "VOCABULARIO 1"));
        lista.add(new Evaluaciones(1, 1011, 5, 5.0, "CIENCIAS2ESO"));
        lista.add(new Evaluaciones(1, 1011, 6, 6.0, "MATES2ESO"));
        lista.add(new Evaluaciones(1, 1011, 7, 7.0, "LENGUA II"));
        lista.add(new Evaluaciones(1, 1011, 8, 6.0, "TRADUCCIÓN"));
        lista.add(new Evaluaciones(1, 1012, 9, 9.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(1, 1012, 10, 8.0, "APLICADAS I"));
        lista.add(new Evaluaciones(1, 1012, 11, 10.0, "LENGUA III"));
        lista.add(new Evaluaciones(1, 1020, 12, 7.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(1, 1020, 13, 8.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(1, 1020, 14, 6.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(1, 1021, 15, 7.0, "MONTAJES"));
        lista.add(new Evaluaciones(1, 1021, 16, 8.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(1, 1030, 17, 6.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(1, 1030, 18, 5.0, "LITERATURA"));
        lista.add(new Evaluaciones(1, 1030, 19, 8.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(1, 1030, 20, 5.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(1, 1031, 21, 5.0, "ANATOMIA"));
        lista.add(new Evaluaciones(1, 1031, 22, 9.0, "LITERATURA II"));
        lista.add(new Evaluaciones(1, 1031, 23, 9.0, "GEOMETRÍA"));
        lista.add(new Evaluaciones(1, 1031, 24, 6.0, "ESTADÍSTICAS II"));
        lista.add(new Evaluaciones(1, 1112, 9, 7.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(1, 1112, 10, 8.0, "APLICADAS I"));
        lista.add(new Evaluaciones(1, 1112, 11, 8.0, "LENGUA III"));
        lista.add(new Evaluaciones(1, 1120, 12, 8.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(1, 1120, 13, 5.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(1, 1120, 14, 4.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(1, 1121, 15, 6.0, "MONTAJES"));
        lista.add(new Evaluaciones(1, 1121, 16, 6.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(1, 1130, 17, 9.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(1, 1130, 18, 9.0, "LITERATURA"));
        lista.add(new Evaluaciones(1, 1130, 19, 8.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(1, 1130, 20, 8.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(1, 1212, 9, 3.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(1, 1212, 10, 2.0, "APLICADAS I"));
        lista.add(new Evaluaciones(1, 1212, 11, 1.0, "LENGUA III"));
        lista.add(new Evaluaciones(1, 1220, 12, 5.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(1, 1220, 13, 4.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(1, 1220, 14, 7.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(1, 1221, 15, 9.0, "MONTAJES"));
        lista.add(new Evaluaciones(1, 1221, 16, 8.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(1, 1321, 15, 5.0, "MONTAJES"));
        lista.add(new Evaluaciones(1, 1321, 16, 4.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(1, 1521, 15, 7.0, "MONTAJES"));
        lista.add(new Evaluaciones(1, 1521, 16, 3.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(1, 1630, 17, 5.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(1, 1630, 18, 6.0, "LITERATURA"));
        lista.add(new Evaluaciones(1, 1630, 19, 7.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(1, 1630, 20, 7.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(1, 2010, 1, 8.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(1, 2010, 2, 9.0, "MATES1ESO"));
        lista.add(new Evaluaciones(1, 2010, 3, 9.0, "LENGUA 1"));
        lista.add(new Evaluaciones(1, 2010, 4, 6.0, "VOCABULARIO 1"));
        lista.add(new Evaluaciones(1, 2011, 5, 4.0, "CIENCIAS2ESO"));
        lista.add(new Evaluaciones(1, 2011, 6, 3.0, "MATES2ESO"));
        lista.add(new Evaluaciones(1, 2011, 7, 4.0, "LENGUA II"));
        lista.add(new Evaluaciones(1, 2011, 8, 3.0, "TRADUCCIÓN"));
        lista.add(new Evaluaciones(1, 3010, 1, 3.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(1, 3010, 2, 4.0, "MATES1ESO"));
        lista.add(new Evaluaciones(1, 3010, 3, 6.0, "LENGUA 1"));
        lista.add(new Evaluaciones(1, 3010, 4, 3.0, "VOCABULARIO 1"));
        lista.add(new Evaluaciones(2, 1010, 1, 7.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(2, 1010, 2, 8.0, "MATES1ESO"));
        lista.add(new Evaluaciones(2, 1010, 3, 4.0, "LENGUA 1"));
        lista.add(new Evaluaciones(2, 1010, 4, 5.0, "VOCABULARIO 1"));
        lista.add(new Evaluaciones(2, 1011, 5, 7.0, "CIENCIAS2ESO"));
        lista.add(new Evaluaciones(2, 1011, 6, 4.0, "MATES2ESO"));
        lista.add(new Evaluaciones(2, 1011, 7, 7.0, "LENGUA II"));
        lista.add(new Evaluaciones(2, 1011, 8, 8.0, "TRADUCCIÓN"));
        lista.add(new Evaluaciones(2, 1012, 9, 8.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(2, 1012, 10, 9.0, "APLICADAS I"));
        lista.add(new Evaluaciones(2, 1012, 11, 10.0, "LENGUA III"));
        lista.add(new Evaluaciones(2, 1020, 12, 9.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(2, 1020, 13, 9.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(2, 1020, 14, 8.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(2, 1021, 15, 8.0, "MONTAJES"));
        lista.add(new Evaluaciones(2, 1021, 16, 8.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(2, 1030, 17, 6.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(2, 1030, 18, 7.0, "LITERATURA"));
        lista.add(new Evaluaciones(2, 1030, 19, 6.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(2, 1030, 20, 4.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(2, 1031, 21, 5.0, "ANATOMIA"));
        lista.add(new Evaluaciones(2, 1031, 22, 9.0, "LITERATURA II"));
        lista.add(new Evaluaciones(2, 1031, 23, 9.0, "GEOMETRÍA"));
        lista.add(new Evaluaciones(2, 1031, 24, 6.0, "ESTADÍSTICAS II"));
        lista.add(new Evaluaciones(2, 1112, 9, 6.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(2, 1112, 10, 9.0, "APLICADAS I"));
        lista.add(new Evaluaciones(2, 1112, 11, 7.0, "LENGUA III"));
        lista.add(new Evaluaciones(2, 1120, 12, 8.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(2, 1120, 13, 7.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(2, 1120, 14, 5.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(2, 1121, 15, 5.0, "MONTAJES"));
        lista.add(new Evaluaciones(2, 1121, 16, 5.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(2, 1130, 17, 8.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(2, 1130, 18, 8.0, "LITERATURA"));
        lista.add(new Evaluaciones(2, 1130, 19, 9.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(2, 1130, 20, 9.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(2, 1212, 9, 4.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(2, 1212, 10, 3.0, "APLICADAS I"));
        lista.add(new Evaluaciones(2, 1212, 11, 1.0, "LENGUA III"));
        lista.add(new Evaluaciones(2, 1220, 12, 6.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(2, 1220, 13, 6.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(2, 1220, 14, 5.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(2, 1221, 15, 7.0, "MONTAJES"));
        lista.add(new Evaluaciones(2, 1221, 16, 4.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(2, 1321, 15, 3.0, "MONTAJES"));
        lista.add(new Evaluaciones(2, 1321, 16, 2.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(2, 1521, 15, 3.0, "MONTAJES"));
        lista.add(new Evaluaciones(2, 1521, 16, 3.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(2, 1630, 17, 8.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(2, 1630, 18, 6.0, "LITERATURA"));
        lista.add(new Evaluaciones(2, 1630, 19, 7.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(2, 1630, 20, 4.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(2, 2010, 1, 7.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(2, 2010, 2, 9.0, "MATES1ESO"));
        lista.add(new Evaluaciones(2, 2010, 3, 8.0, "LENGUA 1"));
        lista.add(new Evaluaciones(2, 2010, 4, 7.0, "VOCABULARIO 1"));
        lista.add(new Evaluaciones(2, 2011, 5, 4.0, "CIENCIAS2ESO"));
        lista.add(new Evaluaciones(2, 2011, 6, 5.0, "MATES2ESO"));
        lista.add(new Evaluaciones(2, 2011, 7, 7.0, "LENGUA II"));
        lista.add(new Evaluaciones(2, 2011, 8, 2.0, "TRADUCCIÓN"));
        lista.add(new Evaluaciones(2, 3010, 1, 2.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(2, 3010, 2, 3.0, "MATES1ESO"));
        lista.add(new Evaluaciones(2, 3010, 3, 1.0, "LENGUA 1"));
        lista.add(new Evaluaciones(2, 3010, 4, 4.0, "VOCABULARIO 1"));
        lista.add(new Evaluaciones(3, 1010, 1, 6.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(3, 1010, 2, 6.0, "MATES1ESO"));
        lista.add(new Evaluaciones(3, 1010, 3, 5.0, "LENGUA 1"));
        lista.add(new Evaluaciones(3, 1011, 5, 5.0, "CIENCIAS2ESO"));
        lista.add(new Evaluaciones(3, 1011, 6, 5.0, "MATES2ESO"));
        lista.add(new Evaluaciones(3, 1011, 7, 8.0, "LENGUA II"));
        lista.add(new Evaluaciones(3, 1011, 8, 5.0, "TRADUCCIÓN"));
        lista.add(new Evaluaciones(3, 1012, 9, 9.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(3, 1012, 10, 9.0, "APLICADAS I"));
        lista.add(new Evaluaciones(3, 1012, 11, 8.0, "LENGUA III"));
        lista.add(new Evaluaciones(3, 1020, 12, 8.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(3, 1020, 13, 8.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(3, 1020, 14, 7.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(3, 1021, 15, 6.0, "MONTAJES"));
        lista.add(new Evaluaciones(3, 1021, 16, 6.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(3, 1030, 17, 8.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(3, 1030, 18, 9.0, "LITERATURA"));
        lista.add(new Evaluaciones(3, 1030, 19, 4.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(3, 1030, 20, 3.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(3, 1112, 9, 7.0, "BIOLOGÍA 3"));
        lista.add(new Evaluaciones(3, 1112, 10, 8.0, "APLICADAS I"));
        lista.add(new Evaluaciones(3, 1112, 11, 7.0, "LENGUA III"));
        lista.add(new Evaluaciones(3, 1120, 12, 9.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(3, 1120, 13, 6.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(3, 1120, 14, 5.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(3, 1121, 15, 7.0, "MONTAJES"));
        lista.add(new Evaluaciones(3, 1121, 16, 7.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(3, 1130, 17, 6.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(3, 1130, 18, 7.0, "LITERATURA"));
        lista.add(new Evaluaciones(3, 1130, 19, 7.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(3, 1130, 20, 6.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(3, 1220, 12, 6.0, "BIOLOGÍA 4"));
        lista.add(new Evaluaciones(3, 1220, 13, 7.0, "ESTADISTICAS"));
        lista.add(new Evaluaciones(3, 1220, 14, 5.0, "ANALISIS LINGÜISTICO"));
        lista.add(new Evaluaciones(3, 1221, 15, 9.0, "MONTAJES"));
        lista.add(new Evaluaciones(3, 1221, 16, 9.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(3, 1321, 15, 2.0, "MONTAJES"));
        lista.add(new Evaluaciones(3, 1321, 16, 4.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(3, 1521, 15, 4.0, "MONTAJES"));
        lista.add(new Evaluaciones(3, 1521, 16, 6.0, "INSTALACIONES"));
        lista.add(new Evaluaciones(3, 1630, 17, 5.0, "CIENCIASBACH1"));
        lista.add(new Evaluaciones(3, 1630, 18, 6.0, "LITERATURA"));
        lista.add(new Evaluaciones(3, 1630, 19, 8.0, "APLICADAS BACH"));
        lista.add(new Evaluaciones(3, 1630, 20, 6.0, "LATINGRIEGO"));
        lista.add(new Evaluaciones(3, 2010, 1, 9.0, "CIENCIAS1ESO"));
        lista.add(new Evaluaciones(3, 2010, 2, 7.0, "MATES1ESO"));
        lista.add(new Evaluaciones(3, 2010, 3, 5.0, "LENGUA 1"));
        lista.add(new Evaluaciones(3, 2010, 4, 3.0, "VOCABULARIO 1"));
        lista.add(new Evaluaciones(3, 2011, 5, 4.0, "CIENCIAS2ESO"));
        lista.add(new Evaluaciones(3, 2011, 6, 5.0, "MATES2ESO"));
        lista.add(new Evaluaciones(3, 2011, 7, 2.0, "LENGUA II"));

        //(int cod_evaluacion, int num_alumno, int cod_asig, double nota, String nombreasig) {
        for (Evaluaciones c : lista) {
            Document res = new Document();
            res.put("cod_evaluacion", c.getCod_evaluacion());
            res.put("num_alumno", c.getNum_alumno());
            res.put("cod_asig", c.getCod_asig());
            res.put("nota", c.getNota());
            res.put("nombreasig", c.getNombreasig());
            coleccion.insertOne(res);
            System.out.println("Evaluacion grabada: " + c);
        }

    }

}

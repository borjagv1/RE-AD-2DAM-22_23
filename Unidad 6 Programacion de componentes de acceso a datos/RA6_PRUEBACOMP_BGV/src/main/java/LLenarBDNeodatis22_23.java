
import datos.*;
import java.io.File;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author mjrm2
 */
public class LLenarBDNeodatis22_23 {

    static ODB bd;

    public static void main(String[] args) {

        String fichero = "BDALUMCURSOS.neo";
        File f = new File(fichero);
        if (f.exists()) {
            f.delete();
            System.out.println("Fichero Eliminado");
        }

        bd = ODBFactory.open(fichero);// Abrir BD

        //13 Cursos: 
        bd.store(new Cursos(10, "1ESO", 0, 0.0));
        bd.store(new Cursos(11, "2ESO", 0, 0.0));
        bd.store(new Cursos(12, "3ESO", 0, 0.0));
        bd.store(new Cursos(20, "4ESO", 0, 0.0));
        bd.store(new Cursos(21, "FPB", 0, 0.0));
        bd.store(new Cursos(30, "1BACH", 0, 0.0));
        bd.store(new Cursos(31, "2BACH", 0, 0.0));
        bd.store(new Cursos(50, "1DAM", 0, 0.0));
        bd.store(new Cursos(51, "2DAM", 0, 0.0));
        bd.store(new Cursos(52, "1SMR", 0, 0.0));
        bd.store(new Cursos(53, "2SMR", 0, 0.0));
        bd.store(new Cursos(54, "1ASIR", 0, 0.0));
        bd.store(new Cursos(55, "2ASIR", 0, 0.0));

        //23 Alumnos: 
        bd.store(new Alumnos(1010, 10, "1ESO", "JUAN GOMEZ MAR", "TALAVERA", "645666777", 0.0));
        bd.store(new Alumnos(1011, 11, "2ESO", "MARIA GOMEZ ABRIL", "TALAVERA", "788999000", 0.0));
        bd.store(new Alumnos(1012, 12, "3ESO", "ALBERTO SÁNCHEZ MORENO", "TOLEDO", "639009988", 0.0));
        bd.store(new Alumnos(1020, 20, "4ESO", "PILAR RAMOS BERT", "TALAVERA", "64444456", 0.0));
        bd.store(new Alumnos(1021, 21, "FPB", "HILDA GARCÍA ROMERO", "TALAVERA", "645789098", 0.0));
        bd.store(new Alumnos(1030, 30, "1BACH", "CARLOS RAMOS MARTÍN", "OROPESA", "644554433", 0.0));
        bd.store(new Alumnos(1031, 31, "2BACH", "PABLO SOLIS CARRETERO", "CALERUELA", "678876543", 0.0));
        bd.store(new Alumnos(1112, 12, "3ESO", "JUANA GIL TRABADO", "TALAVERA", "925555555", 0.0));
        bd.store(new Alumnos(1120, 20, "4ESO", "MARTA SERRANO SUELA", "TALAVERA", "63344996", 0.0));
        bd.store(new Alumnos(1121, 21, "FPB", "ANTONI DE LAS HERAS", "NAVALCÁN", "678097654", 0.0));
        bd.store(new Alumnos(1130, 30, "1BACH", "FERNANDO CORREGIDOR", "TALAVERA", "654332244", 0.0));
        bd.store(new Alumnos(1131, 31, "2BACH", "JOSE MARÍA MANZANO", "GAMONAL", "645009988", 0.0));
        bd.store(new Alumnos(1212, 12, "3ESO", "RAMÓN GARCÍA PEREZ", "TALAVERA", "639009988", 0.0));
        bd.store(new Alumnos(1220, 20, "4ESO", "MENODORA PANIAGUA", "TALAVERA", "73344996", 0.0));
        bd.store(new Alumnos(1221, 21, "FPB", "IVAN CARRASCO SOLA", "TALAVERA", "777888999", 0.0));
        bd.store(new Alumnos(1231, 31, "2BACH", "FÁTIMA GARCÍA SÁNCHEZ", "TALAVERA", "654009906", 0.0));
        bd.store(new Alumnos(1321, 21, "FPB", "ALICIA MANZANO PEREZ", "OROPESA", "234234567", 0.0));
        bd.store(new Alumnos(1331, 31, "2BACH", "JUAN PEDRO RIERA GRAU", "OROPESA", "565443322", 0.0));
        bd.store(new Alumnos(1521, 21, "FPB", "CRISTINA SABROSO FRAILE", "TALAVERA", "639765432", 0.0));
        bd.store(new Alumnos(1630, 30, "1BACH", "ANTONIA GOMEZ SANCHEZ", "TALAVERA", "645789099", 0.0));
        bd.store(new Alumnos(2010, 10, "1ESO", "ALBERTO RAMOS PEREZ", "TOLEDO", "657777888", 0.0));
        bd.store(new Alumnos(2011, 11, "2ESO", "ALICIA PEREZ AMOR", "TALAVERA", "636777888", 0.0));
        bd.store(new Alumnos(3010, 10, "1ESO", "ANA MORENO GARCIA", "TALAVERA", "925323456", 0.0));

        //24 Asignaturas: 
        bd.store(new Asignaturas(1, "CIENCIAS1ESO"));
        bd.store(new Asignaturas(2, "MATES1ESO"));
        bd.store(new Asignaturas(3, "LENGUA 1"));
        bd.store(new Asignaturas(4, "VOCABULARIO 1"));
        bd.store(new Asignaturas(5, "CIENCIAS2ESO"));
        bd.store(new Asignaturas(6, "MATES2ESO"));
        bd.store(new Asignaturas(7, "LENGUA II"));
        bd.store(new Asignaturas(8, "TRADUCCIÓN"));
        bd.store(new Asignaturas(9, "BIOLOGÍA 3"));
        bd.store(new Asignaturas(10, "APLICADAS I"));
        bd.store(new Asignaturas(11, "LENGUA III"));
        bd.store(new Asignaturas(12, "BIOLOGÍA 4"));
        bd.store(new Asignaturas(13, "ESTADISTICAS"));
        bd.store(new Asignaturas(14, "ANALISIS LINGÜISTICO"));
        bd.store(new Asignaturas(15, "MONTAJES"));
        bd.store(new Asignaturas(16, "INSTALACIONES"));
        bd.store(new Asignaturas(17, "CIENCIASBACH1"));
        bd.store(new Asignaturas(18, "LITERATURA"));
        bd.store(new Asignaturas(19, "APLICADAS BACH"));
        bd.store(new Asignaturas(20, "LATINGRIEGO"));
        bd.store(new Asignaturas(21, "ANATOMIA"));
        bd.store(new Asignaturas(22, "LITERATURA II"));
        bd.store(new Asignaturas(23, "GEOMETRÍA"));
        bd.store(new Asignaturas(24, "ESTADÍSTICAS II"));

        //179 Evaluaciones: 
        bd.store(new Evaluaciones(1, 1010, 1, 4.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(1, 1010, 2, 7.0, "MATES1ESO"));
        bd.store(new Evaluaciones(1, 1010, 3, 5.0, "LENGUA 1"));
        bd.store(new Evaluaciones(1, 1010, 4, 6.0, "VOCABULARIO 1"));
        bd.store(new Evaluaciones(1, 1011, 5, 5.0, "CIENCIAS2ESO"));
        bd.store(new Evaluaciones(1, 1011, 6, 6.0, "MATES2ESO"));
        bd.store(new Evaluaciones(1, 1011, 7, 7.0, "LENGUA II"));
        bd.store(new Evaluaciones(1, 1011, 8, 6.0, "TRADUCCIÓN"));
        bd.store(new Evaluaciones(1, 1012, 9, 9.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(1, 1012, 10, 8.0, "APLICADAS I"));
        bd.store(new Evaluaciones(1, 1012, 11, 10.0, "LENGUA III"));
        bd.store(new Evaluaciones(1, 1020, 12, 7.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(1, 1020, 13, 8.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(1, 1020, 14, 6.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(1, 1021, 15, 7.0, "MONTAJES"));
        bd.store(new Evaluaciones(1, 1021, 16, 8.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(1, 1030, 17, 6.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(1, 1030, 18, 5.0, "LITERATURA"));
        bd.store(new Evaluaciones(1, 1030, 19, 8.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(1, 1030, 20, 5.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(1, 1031, 21, 5.0, "ANATOMIA"));
        bd.store(new Evaluaciones(1, 1031, 22, 9.0, "LITERATURA II"));
        bd.store(new Evaluaciones(1, 1031, 23, 9.0, "GEOMETRÍA"));
        bd.store(new Evaluaciones(1, 1031, 24, 6.0, "ESTADÍSTICAS II"));
        bd.store(new Evaluaciones(1, 1112, 9, 7.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(1, 1112, 10, 8.0, "APLICADAS I"));
        bd.store(new Evaluaciones(1, 1112, 11, 8.0, "LENGUA III"));
        bd.store(new Evaluaciones(1, 1120, 12, 8.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(1, 1120, 13, 5.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(1, 1120, 14, 4.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(1, 1121, 15, 6.0, "MONTAJES"));
        bd.store(new Evaluaciones(1, 1121, 16, 6.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(1, 1130, 17, 9.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(1, 1130, 18, 9.0, "LITERATURA"));
        bd.store(new Evaluaciones(1, 1130, 19, 8.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(1, 1130, 20, 8.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(1, 1212, 9, 3.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(1, 1212, 10, 2.0, "APLICADAS I"));
        bd.store(new Evaluaciones(1, 1212, 11, 1.0, "LENGUA III"));
        bd.store(new Evaluaciones(1, 1220, 12, 5.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(1, 1220, 13, 4.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(1, 1220, 14, 7.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(1, 1221, 15, 9.0, "MONTAJES"));
        bd.store(new Evaluaciones(1, 1221, 16, 8.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(1, 1321, 15, 5.0, "MONTAJES"));
        bd.store(new Evaluaciones(1, 1321, 16, 4.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(1, 1521, 15, 7.0, "MONTAJES"));
        bd.store(new Evaluaciones(1, 1521, 16, 3.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(1, 1630, 17, 5.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(1, 1630, 18, 6.0, "LITERATURA"));
        bd.store(new Evaluaciones(1, 1630, 19, 7.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(1, 1630, 20, 7.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(1, 2010, 1, 8.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(1, 2010, 2, 9.0, "MATES1ESO"));
        bd.store(new Evaluaciones(1, 2010, 3, 9.0, "LENGUA 1"));
        bd.store(new Evaluaciones(1, 2010, 4, 6.0, "VOCABULARIO 1"));
        bd.store(new Evaluaciones(1, 2011, 5, 4.0, "CIENCIAS2ESO"));
        bd.store(new Evaluaciones(1, 2011, 6, 3.0, "MATES2ESO"));
        bd.store(new Evaluaciones(1, 2011, 7, 4.0, "LENGUA II"));
        bd.store(new Evaluaciones(1, 2011, 8, 3.0, "TRADUCCIÓN"));
        bd.store(new Evaluaciones(1, 3010, 1, 3.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(1, 3010, 2, 4.0, "MATES1ESO"));
        bd.store(new Evaluaciones(1, 3010, 3, 6.0, "LENGUA 1"));
        bd.store(new Evaluaciones(1, 3010, 4, 3.0, "VOCABULARIO 1"));
        bd.store(new Evaluaciones(2, 1010, 1, 7.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(2, 1010, 2, 8.0, "MATES1ESO"));
        bd.store(new Evaluaciones(2, 1010, 3, 4.0, "LENGUA 1"));
        bd.store(new Evaluaciones(2, 1010, 4, 5.0, "VOCABULARIO 1"));
        bd.store(new Evaluaciones(2, 1011, 5, 7.0, "CIENCIAS2ESO"));
        bd.store(new Evaluaciones(2, 1011, 6, 4.0, "MATES2ESO"));
        bd.store(new Evaluaciones(2, 1011, 7, 7.0, "LENGUA II"));
        bd.store(new Evaluaciones(2, 1011, 8, 8.0, "TRADUCCIÓN"));
        bd.store(new Evaluaciones(2, 1012, 9, 8.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(2, 1012, 10, 9.0, "APLICADAS I"));
        bd.store(new Evaluaciones(2, 1012, 11, 10.0, "LENGUA III"));
        bd.store(new Evaluaciones(2, 1020, 12, 9.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(2, 1020, 13, 9.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(2, 1020, 14, 8.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(2, 1021, 15, 8.0, "MONTAJES"));
        bd.store(new Evaluaciones(2, 1021, 16, 8.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(2, 1030, 17, 6.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(2, 1030, 18, 7.0, "LITERATURA"));
        bd.store(new Evaluaciones(2, 1030, 19, 6.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(2, 1030, 20, 4.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(2, 1031, 21, 5.0, "ANATOMIA"));
        bd.store(new Evaluaciones(2, 1031, 22, 9.0, "LITERATURA II"));
        bd.store(new Evaluaciones(2, 1031, 23, 9.0, "GEOMETRÍA"));
        bd.store(new Evaluaciones(2, 1031, 24, 6.0, "ESTADÍSTICAS II"));
        bd.store(new Evaluaciones(2, 1112, 9, 6.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(2, 1112, 10, 9.0, "APLICADAS I"));
        bd.store(new Evaluaciones(2, 1112, 11, 7.0, "LENGUA III"));
        bd.store(new Evaluaciones(2, 1120, 12, 8.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(2, 1120, 13, 7.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(2, 1120, 14, 5.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(2, 1121, 15, 5.0, "MONTAJES"));
        bd.store(new Evaluaciones(2, 1121, 16, 5.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(2, 1130, 17, 8.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(2, 1130, 18, 8.0, "LITERATURA"));
        bd.store(new Evaluaciones(2, 1130, 19, 9.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(2, 1130, 20, 9.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(2, 1212, 9, 4.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(2, 1212, 10, 3.0, "APLICADAS I"));
        bd.store(new Evaluaciones(2, 1212, 11, 1.0, "LENGUA III"));
        bd.store(new Evaluaciones(2, 1220, 12, 6.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(2, 1220, 13, 6.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(2, 1220, 14, 5.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(2, 1221, 15, 7.0, "MONTAJES"));
        bd.store(new Evaluaciones(2, 1221, 16, 4.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(2, 1321, 15, 3.0, "MONTAJES"));
        bd.store(new Evaluaciones(2, 1321, 16, 2.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(2, 1521, 15, 3.0, "MONTAJES"));
        bd.store(new Evaluaciones(2, 1521, 16, 3.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(2, 1630, 17, 8.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(2, 1630, 18, 6.0, "LITERATURA"));
        bd.store(new Evaluaciones(2, 1630, 19, 7.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(2, 1630, 20, 4.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(2, 2010, 1, 7.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(2, 2010, 2, 9.0, "MATES1ESO"));
        bd.store(new Evaluaciones(2, 2010, 3, 8.0, "LENGUA 1"));
        bd.store(new Evaluaciones(2, 2010, 4, 7.0, "VOCABULARIO 1"));
        bd.store(new Evaluaciones(2, 2011, 5, 4.0, "CIENCIAS2ESO"));
        bd.store(new Evaluaciones(2, 2011, 6, 5.0, "MATES2ESO"));
        bd.store(new Evaluaciones(2, 2011, 7, 7.0, "LENGUA II"));
        bd.store(new Evaluaciones(2, 2011, 8, 2.0, "TRADUCCIÓN"));
        bd.store(new Evaluaciones(2, 3010, 1, 2.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(2, 3010, 2, 3.0, "MATES1ESO"));
        bd.store(new Evaluaciones(2, 3010, 3, 1.0, "LENGUA 1"));
        bd.store(new Evaluaciones(2, 3010, 4, 4.0, "VOCABULARIO 1"));
        bd.store(new Evaluaciones(3, 1010, 1, 6.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(3, 1010, 2, 6.0, "MATES1ESO"));
        bd.store(new Evaluaciones(3, 1010, 3, 5.0, "LENGUA 1"));
        bd.store(new Evaluaciones(3, 1011, 5, 5.0, "CIENCIAS2ESO"));
        bd.store(new Evaluaciones(3, 1011, 6, 5.0, "MATES2ESO"));
        bd.store(new Evaluaciones(3, 1011, 7, 8.0, "LENGUA II"));
        bd.store(new Evaluaciones(3, 1011, 8, 5.0, "TRADUCCIÓN"));
        bd.store(new Evaluaciones(3, 1012, 9, 9.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(3, 1012, 10, 9.0, "APLICADAS I"));
        bd.store(new Evaluaciones(3, 1012, 11, 8.0, "LENGUA III"));
        bd.store(new Evaluaciones(3, 1020, 12, 8.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(3, 1020, 13, 8.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(3, 1020, 14, 7.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(3, 1021, 15, 6.0, "MONTAJES"));
        bd.store(new Evaluaciones(3, 1021, 16, 6.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(3, 1030, 17, 8.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(3, 1030, 18, 9.0, "LITERATURA"));
        bd.store(new Evaluaciones(3, 1030, 19, 4.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(3, 1030, 20, 3.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(3, 1112, 9, 7.0, "BIOLOGÍA 3"));
        bd.store(new Evaluaciones(3, 1112, 10, 8.0, "APLICADAS I"));
        bd.store(new Evaluaciones(3, 1112, 11, 7.0, "LENGUA III"));
        bd.store(new Evaluaciones(3, 1120, 12, 9.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(3, 1120, 13, 6.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(3, 1120, 14, 5.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(3, 1121, 15, 7.0, "MONTAJES"));
        bd.store(new Evaluaciones(3, 1121, 16, 7.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(3, 1130, 17, 6.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(3, 1130, 18, 7.0, "LITERATURA"));
        bd.store(new Evaluaciones(3, 1130, 19, 7.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(3, 1130, 20, 6.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(3, 1220, 12, 6.0, "BIOLOGÍA 4"));
        bd.store(new Evaluaciones(3, 1220, 13, 7.0, "ESTADISTICAS"));
        bd.store(new Evaluaciones(3, 1220, 14, 5.0, "ANALISIS LINGÜISTICO"));
        bd.store(new Evaluaciones(3, 1221, 15, 9.0, "MONTAJES"));
        bd.store(new Evaluaciones(3, 1221, 16, 9.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(3, 1321, 15, 2.0, "MONTAJES"));
        bd.store(new Evaluaciones(3, 1321, 16, 4.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(3, 1521, 15, 4.0, "MONTAJES"));
        bd.store(new Evaluaciones(3, 1521, 16, 6.0, "INSTALACIONES"));
        bd.store(new Evaluaciones(3, 1630, 17, 5.0, "CIENCIASBACH1"));
        bd.store(new Evaluaciones(3, 1630, 18, 6.0, "LITERATURA"));
        bd.store(new Evaluaciones(3, 1630, 19, 8.0, "APLICADAS BACH"));
        bd.store(new Evaluaciones(3, 1630, 20, 6.0, "LATINGRIEGO"));
        bd.store(new Evaluaciones(3, 2010, 1, 9.0, "CIENCIAS1ESO"));
        bd.store(new Evaluaciones(3, 2010, 2, 7.0, "MATES1ESO"));
        bd.store(new Evaluaciones(3, 2010, 3, 5.0, "LENGUA 1"));
        bd.store(new Evaluaciones(3, 2010, 4, 3.0, "VOCABULARIO 1"));
        bd.store(new Evaluaciones(3, 2011, 5, 4.0, "CIENCIAS2ESO"));
        bd.store(new Evaluaciones(3, 2011, 6, 5.0, "MATES2ESO"));
        bd.store(new Evaluaciones(3, 2011, 7, 2.0, "LENGUA II"));

        bd.commit();
        
        VerCursos();
        VerAlumnos();
        VerAsignaturas();
        VerEvaluaciones() ;
        
        bd.close();
    }
    
     private static void VerCursos() {
        //recuperamos todos los objetos
        Objects<Cursos> objects = bd.getObjects(Cursos.class);
        System.out.println("=====================================================");
        System.out.printf("%d Cursos: %n", objects.size());

        while (objects.hasNext()) {
            Cursos al = objects.next();
           System.out.println(al);
        }
    }
    
      private static void VerAlumnos() {
        //recuperamos todos los objetos
        Objects<Alumnos> objects = bd.getObjects(Alumnos.class);
        System.out.println("=====================================================");
        System.out.printf("%d Alumnos: %n", objects.size());

        while (objects.hasNext()) {
            Alumnos al = objects.next();
            System.out.println(al);
        }
    }//VerAlumnos

    private static void VerAsignaturas() {
        //recuperamos todos los objetos
        Objects<Asignaturas> objects = bd.getObjects(Asignaturas.class);
        System.out.println("=====================================================");
        System.out.printf("%d Asignaturas: %n", objects.size());

        while (objects.hasNext()) {
            Asignaturas al = objects.next();
            System.out.println(al);
        }
    }

    private static void VerEvaluaciones() {
        Objects<Evaluaciones> objects = bd.getObjects(Evaluaciones.class);
        System.out.println("=====================================================");
        System.out.printf("%d Evaluaciones: %n", objects.size());

        while (objects.hasNext()) {
            Evaluaciones al = objects.next();
            System.out.println(al);
        }
    }

}

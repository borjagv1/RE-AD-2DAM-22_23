package ejerciciosLibro;

import java.io.File;

public class Ejercicio_1_1 {
    public static void main(String[] args) throws Exception {
        String dir = "C:\\xampp"; // directorio actual o args[0];
        File f = new File(dir);
        String[] archivos = f.list();
        if (archivos == null) {
            System.out.println(dir + " No es un directorio válido");
        } else {
            System.out.println("Archivos en el directorio actual: " + dir + " " + archivos.length);
            for (int i = 0; i < archivos.length; i++) {
                System.out.println("Nombre: " + archivos[i]); 
            } 
        }

    }
}

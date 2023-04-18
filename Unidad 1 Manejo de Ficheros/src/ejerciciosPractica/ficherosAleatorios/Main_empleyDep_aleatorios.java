package ejerciciosPractica.ficherosAleatorios;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;

public class Main_empleyDep_aleatorios {

    static int longitud = 4 + 30 + 20 + 4 + 4 + 30; // 92

    public static void main(String[] args) {
        Apartado_A();
        Apartado_B();
        Apartado_C_VisualizarFicheroAleatorio();
    }

    private static void Apartado_B() {
        String fichAleatorio = "FichEmpleAleatorio.dat";
        // Nombre del fichero de modificaciones
        String fichModif = "FichEmpleadosModif.dat";
        try {
            // Abrir el fichero de acceso aleatorio en modo lectura/escritura
            RandomAccessFile raf = new RandomAccessFile(fichAleatorio, "rw");
            // Abrir el fichero de modificaciones en modo lectura
            DataInputStream dis = new DataInputStream(new FileInputStream(fichModif));
            // Mientras haya datos en el fichero de modificaciones
            while (dis.available() > 0) {
                // Leer el identificador y la cantidad del empleado a modificar
                int id = dis.readInt();
                float cantidad = dis.readFloat();
                // Calcular la posición del registro correspondiente al identificador
                int pos = (id - 1) * longitud;
                try {
                    // Posicionar el puntero del fichero de acceso aleatorio en la posición
                    // calculada
                    raf.seek(pos);
                    raf.skipBytes(4 + 30 + 20);
                    // Leer el salario del empleado desde el fichero de acceso aleatorio
                    float salario = raf.readFloat();
                    // Sumar la cantidad al salario y escribir el nuevo salario en el fichero de
                    // acceso aleatorio
                    salario += cantidad;
                    raf.seek(pos + 54);
                    raf.writeFloat(salario);
                    System.out.println("Se ha modificado el salario del empleado con id " + id);
                } catch (IOException e) {
                    // Si se produce una excepción al leer o escribir los datos, mostrar un mensaje
                    // indicando que el identificador no existe
                    System.out.println("No existe el empleado con id " + id);
                }
            }
            // Cerrar los ficheros
            raf.close();
            dis.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado alguno de los ficheros");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida");
        }

    }

    private static void Apartado_A() {
        RandomAccessFile raf = null;
        int empno, depto, posicion = 0;
        char apellido[] = new char[15], aux;
        char oficio[] = new char[10], aux2;
        String nombreDepartamento;
        try {
            // Abrimos el fichero FichEmpleAleatorio.dat con un RandomAccessFile en modo
            // lectura y escritura ("rw")
            raf = new RandomAccessFile("FichEmpleAleatorio.dat", "rw");

            // Recorremos el fichero FichEmpleAleatorio.dat leyendo los datos de cada
            // empleado
            while (true) {
                raf.seek(posicion); // Nos posicionamos en el byte donde empieza el atributo empno
                empno = raf.readInt();
                // recorro uno a uno los caracteres del apellido
                for (int i = 0; i < apellido.length; i++) {
                    aux = raf.readChar();
                    apellido[i] = aux; // los voy guardando en el array
                }
                // convierto a String el array
                @SuppressWarnings("unused")
                String apellidoS = new String(apellido);

                // recorro uno a uno los caracteres del oficio
                for (int i = 0; i < oficio.length; i++) {
                    aux2 = raf.readChar();
                    oficio[i] = aux2; // los voy guardando en el array
                }
                // convierto a String el array
                @SuppressWarnings("unused")
                String oficioS = new String(oficio);

                float salario = raf.readFloat();

                depto = raf.readInt();

                // Buscamos el nombre de departamento correspondiente al número de departamento
                // del empleado en el fichero FichDepartamentos.dat
                nombreDepartamento = buscarNombreDepartamento(depto);

                // Si existe, lo escribimos en el fichero. Si no existe, escribimos "No existe"
                if (nombreDepartamento != null) {
                    StringBuffer buffer = new StringBuffer(nombreDepartamento);
                    buffer.setLength(15); // 15 caracteres para nombre dep
                    raf.writeChars(buffer.toString()); // Insertar nombredept
                } else {
                    StringBuffer buffer = new StringBuffer("no existe");
                    buffer.setLength(15); // 15 caracteres para nombre dep
                    raf.writeChars(buffer.toString()); // Insertar nombredept
                }

                if (empno > 0)
                    // SI COMENTAMOS ESTA CONDICIóN VEMOS LOS REGISTROS VACÍOS HASTA EL 20 POR ESo
                    // ES
                    // IMPORTANTE VER QUE EL ID SIEMPRE ES MAYOR QUE 0. PARA VER SOLO LOS REGISTROS
                    // CON INFORMACIÓN
                    System.out.println("\tEMPLEADOS MODIFICADOS: ");

                // me posiciono para el sig EMPLEADO
                posicion = posicion + longitud;

                // Si he recorrido todos los bytes salgo del for
                if (posicion == raf.length())
                    break;

            } // while
            raf.close();

        } catch (EOFException e) {
            // Fin de fichero
        } catch (IOException e) {
            // Error de entrada/salida
            e.printStackTrace();
        } finally {
            // Cerramos el fichero FichEmpleAleatorio.dat
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                // Error al cerrar el fichero
                e.printStackTrace();
            }
        }
    }

    // Método que busca el nombre de un departamento dado su número en el fichero
    // FichDepartamentos.dat
    public static String buscarNombreDepartamento(int deptno) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Departamento dep = null;
        String nombreDepartamento = null;

        try {
            // Abrimos el fichero FichDepartamentos.dat con un FileInputStream y un
            // ObjectInputStream
            fis = new FileInputStream("FichDepartamentos.dat");
            ois = new ObjectInputStream(fis);

            // Leemos los objetos Departamento del fichero hasta encontrar el que coincide
            // con el número buscado o llegar al final
            while (true) {
                dep = (Departamento) ois.readObject();
                if (dep.getDep() == deptno) {
                    // Si encontramos el departamento buscado, guardamos su nombre y salimos del
                    // bucle
                    nombreDepartamento = dep.getNombre();
                    break;
                }
            }

        } catch (EOFException e) {
            // Fin de fichero
        } catch (IOException | ClassNotFoundException e) {
            // Error de entrada/salida o de clase no encontrada
            e.printStackTrace();
        } finally {
            // Cerramos el fichero FichDepartamentos.dat
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                // Error al cerrar el fichero
                e.printStackTrace();
            }
        }

        // Devolvemos el nombre del departamento encontrado o null si no existe
        return nombreDepartamento;
    }

    public static void Apartado_C_VisualizarFicheroAleatorio() {
        RandomAccessFile raf = null;
        int empno, deptno, posicion = 0;
        char apellido[] = new char[15], aux;
        char oficio[] = new char[10], aux2;
        char nombreDepartamento[] = new char[15], aux3;

        float salario;

        try {
            // Abrimos el fichero FichEmpleAleatorio.dat con un RandomAccessFile en modo
            // lectura ("r")
            raf = new RandomAccessFile("FichEmpleAleatorio.dat", "r");

            // Recorremos el fichero FichEmpleAleatorio.dat leyendo los datos de cada
            // empleado
            while (true) {
                raf.seek(posicion);
                empno = raf.readInt();
                // recorro uno a uno los caracteres del apellido
                for (int i = 0; i < apellido.length; i++) {
                    aux = raf.readChar();
                    apellido[i] = aux; // los voy guardando en el array
                }
                // convierto a String el array
                String apellidoS = new String(apellido);

                // recorro uno a uno los caracteres del apellido
                for (int i = 0; i < oficio.length; i++) {
                    aux2 = raf.readChar();
                    oficio[i] = aux2; // los voy guardando en el array
                }
                // convierto a String el array
                String oficioS = new String(oficio);

                salario = raf.readFloat();

                deptno = raf.readInt();

                // recorro uno a uno los caracteres del apellido
                for (int i = 0; i < nombreDepartamento.length; i++) {
                    aux3 = raf.readChar();
                    nombreDepartamento[i] = aux3; // los voy guardando en el array
                }
                // convierto a String el array
                String nombreDepartamentoS = new String(nombreDepartamento);

                if (empno > 0) // SI COMENTAMOS ESTA CONDICIÃN VEMOS LOS REGISTROS VACÃOS HASTA EL 20 POR ES
                    // ES
                    // IMPORTANTE VER QUE EL ID SIEMPRE ES MAYOR QUE 0. PARA VER SOLO LOS REGISTROS
                    // CON INFORMACIÓN
                    // Imprimimos los datos del empleado
                    System.out.printf(
                            "EMPNO: %d, APELLIDO: %s, OFICIO: %s, Salario: %.2f, DEPTNO: %d, NOMBREDEP: %s %n",
                            empno, apellidoS.trim(), oficioS.trim(), salario, deptno, nombreDepartamentoS.trim());

                // me posiciono para el sig
                posicion = posicion + longitud;

                // Si he recorrido todos los bytes salgo del for
                if (raf.getFilePointer() == raf.length())
                    break;

            }

        } catch (EOFException e) {
            // Fin de fichero
        } catch (IOException e) {
            // Error de entrada/salida
            e.printStackTrace();
        } finally {
            // Cerramos el fichero FichEmpleAleatorio.dat
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                // Error al cerrar el fichero
                e.printStackTrace();
            }
        }
    }
}

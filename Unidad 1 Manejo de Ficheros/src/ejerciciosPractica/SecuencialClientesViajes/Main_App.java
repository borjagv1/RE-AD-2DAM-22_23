import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import datos.Cliente;
import datos.Viaje;

@SuppressWarnings("unused")
public class Main_App {

    static File viajes = new File("viajes.dat");
    static File clientes = new File("clientes.dat");
    static File reservas = new File("reservas.dat");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        Apartado_1();
        Apartado_2();
        Apartado_3();
        Apartado_4();
        Apartado_5();
        Apartado_6();

    }

    private static void Apartado_6() {
        int id;
        // proceso repetitivo para leer clientes hasta que el id sea 0
        System.out.print("Introduce un ID de cliente (0 para salir): ");
        id = sc.nextInt();
        while (id != 0) {
            double totalPVP = 0;
            // si el cliente existe
            if (existeCliente(id)) {
                // mostrar los datos del cliente
                try (ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(clientes))) {
                    Cliente cliente;
                    try {
                        while (true) {
                            cliente = (Cliente) dataIS.readObject();

                            if (cliente.getId() == id) {
                                if (cliente.getViajescontratados() == 0) {
                                    System.out.println("\tNO HA CONTRATADO NINGÚN VIAJE");
                                    System.out.println("==================================================");
                                    break;
                                } else {
                                    System.out.println(cliente.getNombre() + ", viajes contratados: "
                                            + cliente.getViajescontratados());
                                    // mostrar los viajes reservados por el cliente
                                    try (DataInputStream dataIS2 = new DataInputStream(new FileInputStream(reservas))) {
                                        int idViaje = 0, idCliente = 0, plazas;
                                        try {
                                            System.out.println(
                                                    "===================================================================");
                                            System.out.printf("%-3s %-31s %-16s %-3s %-6s%n", "ID", "DESCRIPCIÓN",
                                                    "FEC SALIDA", "PVP", "PLAZAS");
                                            System.out.println(
                                                    "=== =============================== ========== ========= ======");
                                            while (true) {
                                                idViaje = dataIS2.readInt();
                                                idCliente = dataIS2.readInt();
                                                plazas = dataIS2.readInt();
                                                if (idCliente == id) {
                                                    try (ObjectInputStream dataIS3 = new ObjectInputStream(
                                                            new FileInputStream(viajes))) {
                                                        Viaje viaje;
                                                        try {
                                                            while (true) {
                                                                viaje = (Viaje) dataIS3.readObject();
                                                                if (viaje.getId() == idViaje) {
                                                                    System.out.printf("%-3d %-31s %-11s %-11.2f %-6d%n",
                                                                            viaje.getId(),
                                                                            viaje.getDescripcion(),
                                                                            viaje.getFechasalida()
                                                                                    .format(DateTimeFormatter
                                                                                            .ofPattern("dd-MM-yyyy")),
                                                                            viaje.getPvp(), plazas);
                                                                    totalPVP += viaje.getPvp() * plazas;
                                                                }
                                                            }
                                                        } catch (EOFException eofe) {
                                                        } catch (ClassNotFoundException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                            
                                        } catch (EOFException eofe) {
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        System.out.println("\tTotal PVP: " + totalPVP);
                                        System.out.println("===================================================================");
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (EOFException eofe) {
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                System.out.print("\nIntroduce un ID de cliente (0 para salir): ");
                id = sc.nextInt();
            } else {
                System.out.println("\tNO EXISTE EL ID DEL CLIENTE");
                System.out.println("==================================================");
                System.out.print("\nIntroduce un ID de cliente (0 para salir): ");
                id = sc.nextInt();
            }

        }
        System.out.println("FIN APARTADO 6");
    }

    private static boolean existeCliente(int id) {
        boolean clienteExiste = false;
        try {
            try (ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(clientes))) {
                Cliente cliente;

                try {
                    while (true) {
                        cliente = (Cliente) dataIS.readObject();
                        if (cliente.getId() == id) {
                            clienteExiste = true;
                        }
                    }
                } catch (EOFException eofe) {
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clienteExiste;
    }

    private static void Apartado_5() {
        // SI EL ID ES DISTINTO DE 0, BUSCAR EL VIAJE EN EL FICHERO VIAJES.DAT
        // SI EL VIAJE NO EXISTE MOSTRAR MENSAJE "EL VIAJE NO EXISTE"
        // SI EL VIAJE EXISTE MOSTRAR LOS DATOS DEL VIAJE
        // SI EL VIAJE EXISTE MOSTRAR LOS DATOS DE LOS CLIENTES QUE HAN RESERVADO EL
        // VIAJE
        // SI EL VIAJE EXISTE MOSTRAR EL NÚMERO DE PLAZAS RESERVADAS
        int id;
        System.out.print("Introduce un ID de viaje (0 para salir): ");
        id = sc.nextInt();

        while (id != 0) {
            int cont = 0;

            boolean existe = existeViaje(id);

            if (existe) {
                try (ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes))) {
                    Viaje viaje;
                    try {
                        while (true) {
                            viaje = (Viaje) dataIS.readObject();
                            if (viaje.getId() == id) {
                                System.out.print(viaje.getDescripcion());
                                System.out.println(", viajeros: " + viaje.getViajeros());
                                try (DataInputStream dataIS2 = new DataInputStream(new FileInputStream(reservas))) {
                                    int idViaje = 0, idCliente = 0, plazas;
                                    try {
                                        System.out.printf("%-3s %-20s %-6s%n", "ID", "NOMBRE",
                                                "PLAZAS");
                                        System.out.println("=== ==================== ======");
                                        while (true) {
                                            idViaje = dataIS2.readInt();
                                            idCliente = dataIS2.readInt();
                                            plazas = dataIS2.readInt();
                                            if (idViaje == id) {
                                                try (ObjectInputStream dataIS3 = new ObjectInputStream(
                                                        new FileInputStream(clientes))) {
                                                    Cliente cliente;
                                                    try {
                                                        while (true) {
                                                            cliente = (Cliente) dataIS3.readObject();
                                                            if (cliente.getId() == idCliente) {
                                                                System.out.printf("%-3d %-20s %-6d%n", cliente.getId(),
                                                                        cliente.getNombre(), plazas);
                                                                cont++;
                                                            }
                                                        }

                                                    } catch (EOFException eofe) {
                                                    } catch (ClassNotFoundException e) {
                                                        e.printStackTrace();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    } catch (EOFException eofe) {
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    } catch (EOFException eofe) {
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("numero de clientes: " + cont);
                System.out.println("=================================");
                System.out.print("Introduce un ID de viaje (0 para salir): ");
                id = sc.nextInt();
            } else {
                System.out.println("El viaje no existe");
                System.out.print("Introduce un ID de viaje (0 para salir): ");
                id = sc.nextInt();
            }
        }
        System.out.println("FIN APARTADO 5");
    }

    private static boolean existeViaje(int id) {
        boolean viajeExiste = false;
        try (ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes))) {
            Viaje viaje;
            try {
                while (true) {
                    viaje = (Viaje) dataIS.readObject();
                    if (viaje.getId() == id) {
                        viajeExiste = true;
                    }
                }
            } catch (EOFException eofe) {
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return viajeExiste;
    }

    private static void Apartado_4() {
        // crear el objeto ObjectInputStream para leer el fichero viajes.dat
        try {
            try (ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes))) {
                // leer fichero viajes creando un bucle while
                try {
                    System.out.printf("%-3s %-31s %-10s %-8s\n", "ID", "DESCRIPCIÓN", "FEC SALIDA", "VIAJEROS", "");
                    System.out.println("=== =============================== ========== ========");

                    while (true) {
                        Viaje viaje = (Viaje) dataIS.readObject();
                        // mostrar texto con formato usando ID, descripción, fecha de salida, número de
                        // VIAJEROS
                        System.out.printf("%-3s %-31s %-13s %-8s\n",
                                viaje.getId(), viaje.getDescripcion(), viaje.getFechasalida(), viaje.getViajeros());
                    }
                } catch (EOFException eofe) {
                    System.out.println("FIN APARTADO 4");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void Apartado_3() {
        // Creamos un objeto File para el fichero Errores.txt
        File ficheroErrores = new File("Errores.txt");
        // Creamos un objeto FileWriter y un objeto BufferedWriter para escribir en el
        // fichero
        FileWriter fw;
        try {
            fw = new FileWriter(ficheroErrores);

            BufferedWriter br = new BufferedWriter(fw);
            // Creamos una variable int para llevar la cuenta de los errores
            int numError = 0;

            // Creamos un objeto DataInputStream para leer el fichero
            // Reservas.dat
            try (DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas))) {
                int idViaje = 0, idCliente = 0, plazas;

                while (true) {
                    idViaje = dataIS.readInt();
                    idCliente = dataIS.readInt();
                    plazas = dataIS.readInt();
                    // Creamos una variable booleana para indicar si el viaje existe o no
                    boolean viajeExiste = false;
                    // Creamos un objeto ObjectInputStream para leer el fichero
                    // Viajes.dat
                    try (ObjectInputStream dataIS2 = new ObjectInputStream(new FileInputStream(viajes))) {
                        Viaje viaje;
                        try {
                            while (true) {
                                viaje = (Viaje) dataIS2.readObject();
                                if (viaje.getId() == idViaje) {
                                    viajeExiste = true;
                                    break;
                                }
                            }
                        } catch (IOException e) {
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Si el viaje no existe, escribimos un mensaje de error en el fichero
                    // Errores.txt
                    if (!viajeExiste) {
                        // Incrementamos el número de error
                        numError++;
                        // Escribimos el mensaje con el formato: "ERROR " + numError + ". EL VIAJE " +
                        // idViaje + ", no existe\n"

                        br.write("ERROR " + numError + ". EL VIAJE " + idViaje + ", no existe\n");
                        br.flush();
                    }
                    // Creamos una variable booleana para indicar si el cliente existe o no
                    boolean clienteExiste = false;
                    // Creamos un objeto ObjectInputStream para leer el fichero Cliente.dat
                    try (ObjectInputStream dataIS3 = new ObjectInputStream(new FileInputStream(clientes))) {
                        Cliente cliente;
                        try {
                            while (true) {
                                cliente = (Cliente) dataIS3.readObject();
                                if (cliente.getId() == idCliente) {
                                    clienteExiste = true;
                                    break;
                                }
                            }
                        } catch (IOException e) {
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Si el cliente no existe, escribimos un mensaje de error en el fichero
                    // Errores.txt
                    if (!clienteExiste) {
                        // Incrementamos el número de error
                        numError++;
                        // Escribimos el mensaje con el formato: "ERROR " + numError + ". EL CLIENTE " +
                        // idCliente + ", no existe\n"
                        br.write("ERROR " + numError + ". EL CLIENTE " + idCliente + ", no existe\n");
                        br.flush();
                    }

                } // While
            } catch (EOFException eofe) { // Añadimos este bloque catch para capturar la excepción EOFException
                br.close(); // Cerramos el objeto br dentro del bloque catch
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("FICHERO ERRORES ACTUALIZADO");
        System.out.println("FIN APARTADO 3");
    }

    private static void Apartado_2() {
        /*
         * 2) [2,5 puntos] Actualizar en el fichero Clientes.dat:
         *  El campo viajescontratados para que almacene el nº de viajes contratados
         * por el cliente. Cada registro en el fichero de reservas es un viaje
         * contratado.
         *  El campo importetotal debe almacenar la suma de lo que valen las reservas
         * que ha realizado el cliente. El importe de cada reserva es igual a la
         * multiplicación del pvp del viaje por el número de plazas.
         */
        try {
            DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas));

            int idViaje, idCliente, plazas, totalClientes;
            try {
                while (true) {
                    idViaje = dataIS.readInt();
                    idCliente = dataIS.readInt();
                    plazas = dataIS.readInt();
                    totalClientes = sumaClientes(idCliente);

                    actualizarClientes(idCliente, totalClientes);
                    System.out.println("Cliente: " + idCliente + " Total viajes contratados: " + totalClientes);
                }
            } catch (IOException e) {
                System.out.println("Archivo clientes.dat actualizado");

            }
            dataIS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Apartado 2 finalizado");
    }

    private static void actualizarClientes(int idCliente, int totalClientes) {
        Cliente cli;
        try (ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(clientes))) {
            File ficheroClientesAux = new File("clientesAux.dat");
            ObjectOutputStream dataOSAux = new ObjectOutputStream(new FileOutputStream(ficheroClientesAux));
            try {
                while (true) {
                    cli = (Cliente) dataIS.readObject();
                    if (cli.getId() == idCliente) {

                        cli.setViajescontratados(totalClientes);

                        double importeTotal = 0;
                        cli.setImportetotal(importeTotal += calculaImporteTotal(idCliente));

                    }
                    dataOSAux.writeObject(cli);

                }
            } catch (IOException | ClassNotFoundException e) {

            }
            dataIS.close();
            dataOSAux.close();
            clientes.delete();
            ficheroClientesAux.renameTo(clientes);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Archivo clientes.dat actualizado");
        }

    }

    private static double calculaImporteTotal(int idCliente) {
        // leer fichero viajes, fichero clientes y fichero reservas
        // recorrer fichero reservas y buscar el idCliente
        // recorrer fichero viajes y buscar el idViaje
        // recorrer fichero clientes y buscar el idCliente
        // calcular el importe total
        // actualizar el campo importetotal del cliente
        Viaje v;
        Cliente cli;
        int idViaje, idCliAUX, plazas;
        double importeTotal = 0;

        try (DataInputStream dataISReservas = new DataInputStream(new FileInputStream(reservas))) {
            try {
                while (true) {
                    idViaje = dataISReservas.readInt();
                    idCliAUX = dataISReservas.readInt();
                    plazas = dataISReservas.readInt();
                    if (idCliAUX == idCliente) {
                        try (ObjectInputStream dataISViajes = new ObjectInputStream(new FileInputStream(viajes))) {
                            try {
                                while (true) {
                                    v = (Viaje) dataISViajes.readObject();
                                    if (v.getId() == idViaje) {
                                        importeTotal += (v.getPvp() * plazas);
                                    }
                                }
                            } catch (IOException | ClassNotFoundException e) {
                            }
                            dataISViajes.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
            }
            dataISReservas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return importeTotal;

    }

    private static int sumaClientes(int idCliente) {
        // declaramos fichero reservas.dat
        int idViaje, idCliAUX, plazas, totalClientes = 0;

        try {
            DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas));
            try {
                while (true) {
                    idViaje = dataIS.readInt();
                    idCliAUX = dataIS.readInt();
                    plazas = dataIS.readInt();
                    if (idCliAUX == idCliente) {
                        totalClientes++;
                    }
                }

            } catch (IOException e) {

            }
            dataIS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalClientes;
    }

    private static void Apartado_1() throws FileNotFoundException {
        /*
         * 1) [1,5 puntos] Actualizar en el fichero Viajes.dat:
         *  El campo viajeros del fichero para que contenga el nº de viajeros que hacen
         * el viaje. Este campo será igual a la suma de las plazas reservadas en el
         * viaje correspondiente.
         */
        try (DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas))) {
            int idViaje, idCliente, plazas, totalPlazas;
            try {
                while (true) {
                    idViaje = dataIS.readInt();
                    idCliente = dataIS.readInt();
                    plazas = dataIS.readInt();
                    totalPlazas = sumaPlazas(idViaje);

                    actualizarViaje(idViaje, totalPlazas);
                    System.out.println("Viaje: " + idViaje + " Total plazas: " + totalPlazas + "\n\t cliente: "
                            + idCliente + " Plazas reservadas: " + plazas);

                }
            } catch (IOException e) {
                System.out.println("Archivo viajes.dat actualizado");

            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {

            e.printStackTrace();
        }
        System.out.println("FIN APARTADO 1");
    }

    private static void actualizarViaje(int idViaje, int totalPlazas) throws FileNotFoundException, IOException {

        ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(viajes));
        File ficheroAux = new File("viajesAux.dat");

        ObjectOutputStream dataOS = new ObjectOutputStream(new FileOutputStream(ficheroAux));
        Viaje v;
        try {
            while (true) {
                v = (Viaje) dataIS.readObject();
                if (v.getId() == idViaje) {
                    v.setViajeros(totalPlazas);
                }
                dataOS.writeObject(v);
            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
        dataIS.close();
        dataOS.close();
        viajes.delete();
        ficheroAux.renameTo(viajes);

    }// Actualizar viajes

    private static int sumaPlazas(int idViaje) throws FileNotFoundException {
        int total = 0;
        int idCliente = 0;
        try (DataInputStream dataIS = new DataInputStream(new FileInputStream(reservas))) {
            int idV, plazas;
            try {
                while (true) {
                    idV = dataIS.readInt();
                    idCliente = dataIS.readInt();
                    plazas = dataIS.readInt();
                    if (idV == idViaje) {
                        total += plazas;
                    }

                }
            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return total;
    }

}

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import datos.Cliente;
import datos.Viaje;

@SuppressWarnings("unused")
public class Main_App {

    static File viajes = new File("viajes.dat");
    static File clientes = new File("clientes.dat");
    static File reservas = new File("reservas.dat");

    public static void main(String[] args) throws FileNotFoundException {
        Apartado_1();
        Apartado_2();

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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
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

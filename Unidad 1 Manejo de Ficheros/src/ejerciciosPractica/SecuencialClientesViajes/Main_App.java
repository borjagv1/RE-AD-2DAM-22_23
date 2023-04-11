import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import datos.Viaje;

public class Main_App {

    static File viajes = new File("viajes.dat");
    static File clientes = new File("clientes.dat");
    static File reservas = new File("reservas.dat");

    public static void main(String[] args) throws FileNotFoundException {
        Apartado_1();

        

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
        // TODO Auto-generated method stub

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
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        dataIS.close();
        dataOS.close();
        viajes.delete();
        ficheroAux.renameTo(viajes);

    }//Actualizar viajes

    private static int sumaPlazas(int idViaje) throws FileNotFoundException {
        int total = 0;
        int idCliente;
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
                System.out.println("FIN Archivo reservas");

            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return total;
    }

}

package examen2022;

/*public class Vendedor  {	
	private int id; // id del vendedor, valor de 1 a 9999
	private String nombre; // nombre del vendedor 15 caracteres
	private double importeTotal; // importe total de las ventas del vendedor,
								// inicialmente 0
	private int codZona; // c�digo de la zona, valor de 1 a 99
	private String nombreZona; // nombre de la zona, 15 caracteres
	private int nventas; // n�mero de ventas del vendedor, inicialmente 0 
    
    public class Zona  {

	private int id; // identificación de la zona, campo clave
	private String nombrezona; // 15 caracteres
	private String provincia; // 20 caracteres
	private int ventas;	   //n�mero de ventas en la zona, inicialmente es 0  
    private double importeTotal;  //importe total de las ventas en la zona,
    
    // Campos de la clase Venta
    _id //identificación del documento, generado al crearlo
    int zona; //id de la zona
    int vendedor; //id del vendedor
    double importeventa ; //importe de la venta*/
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.mongodb.client.model.Sorts.*;

public class Main_Examen2022 {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("GuerraValenciaBorja");
        MongoCollection<Document> coleccionZonas = db.getCollection("zonas");
        MongoCollection<Document> coleccionVendedores = db.getCollection("vendedores");
        MongoCollection<Document> coleccionVentas = db.getCollection("ventas");

         ActualizarZonas(coleccionZonas, coleccionVendedores, coleccionVentas);
         ActualizarVendedores(coleccionVendedores, coleccionVentas, coleccionZonas);
         ListarZonas(coleccionZonas, coleccionVendedores, coleccionVentas);

    }

    private static void ListarZonas(MongoCollection<Document> coleccionZonas,
            MongoCollection<Document> coleccionVendedores, MongoCollection<Document> coleccionVentas) {

        // Proceso repetitivo en el que se pide por teclado el ID de una zona y se
        // muestran los vendedores de la zona y las ventas realizadas en la zona. Si la
        // zona no existe en la colección de zonas se mostrará un mensaje indicándolo.
        // El proceso termina cuando se introduce un 0 como ID de zona.

        System.out.println("===================================================================");
        int id = leerEntero("Introduce el ID de una zona: ", 0, 9999);
        System.out.println("===================================================================");
        while (id != 0) {
            Document zona = coleccionZonas.find(eq("_id", id)).first();
            if (zona == null) {
                System.out.println("NO EXISTE EL ID DE LA ZONA");
            } else {
                // MUestro datos de la zona: id, nombrezona, provincia, ventas, importeTotal

                // formateo el importetotal para que salga con , para decimales y . para los
                // miles
                Double importeTotal = ((Number) zona.get("importeTotal")).doubleValue();
                String importeTotalFormateado = String.format("%,.2f", importeTotal);
                System.out.println("===================================================================");

                System.out.println("Zona: " + zona.get("_id") + ", " + zona.get("nombrezona") + ", "
                        + zona.get("provincia") + "\n Numero de Ventas: " + zona.get("ventas") + " Importe total: "
                        + importeTotalFormateado);
                System.out.println("===================================================================");
                // Mostrar vendedores de la zona SI NO TIENE VENDEDORES MUESTRO UN MENSAJE:
                // id NOMBRE IMPORTETOTAL NVENTAS
                // Compruebo que haya vendedores en la zona

                boolean hayVendedores = coleccionVendedores.find(eq("codZona", id)).first() != null;

                if (!hayVendedores) {
                    System.out.println("\t<< NO HAY VENDEDORES EN LA ZONA>> ");

                } else {
                    System.out.println("LISTADO DE VENDEDORES EN LA ZONA: " + zona.get("nombrezona"));
                    // cabecera: ID NOMBRE IMPORTE TOTAL Nº VENTAS ==== ===============
                    // ============= =========
                    System.out.printf("\t%-10s %-15s %-15s %-15s\n", "ID", "NOMBRE", "IMPORTE TOTAL", "Nº VENTAS");
                    System.out.printf("\t%-10s %-15s %-15s %-15s\n", "===", "==============", "==============",
                            "==============");
                    for (Document vendedor : coleccionVendedores.find(eq("codZona", id))) {
                        Double importeTotalaFormatear = (Double) vendedor.get("importeTotal");
                        String importeTotalFormateado2 = String.format("%,.2f", importeTotalaFormatear);

                        System.out.printf("\t%-10s %-20s %-22s %-15s\n", vendedor.get("_id"), vendedor.get("nombre"),
                                importeTotalFormateado2, vendedor.get("nventas"));

                    }
                }
                System.out.println();
                // AHORA MUESTRO LAS VENTAS DE LA ZONA: ID, NOMBREVENDEDOR IMPORTEVENTA
                // Compruebo que haya ventas en la zona
                boolean hayVentas = coleccionVentas.find(eq("zona", id)).first() != null;
                if (!hayVentas) {
                    System.out.println("\t<< NO HAY VENTAS EN LA ZONA>> ");
                } else {
                    System.out.println("LISTADO DE VENTAS EN LA ZONA: " + zona.get("nombrezona"));
                    for (Document venta : coleccionVentas.find(eq("zona", id))) {
                        Document vendedor = coleccionVendedores.find(eq("_id", venta.get("vendedor"))).first();
                        // compruebo que el vendedor exista si no, escribo, no existe
                        if (vendedor == null) {
                            // Cabecera ID NOMBRE VENDEDOR IMPORTE VENTA ==== =============== =============
                            System.out.printf("\t%-10s %-15s %-15s\n", "ID", "NOMBRE VENDEDOR", "IMPORTE VENTA");
                            System.out.printf("\t%-10s %-15s %-15s\n", "===", "==============", "==============");
                            System.out.printf("\t%-10s %-15s %-15s\n", venta.get("vendedor"), "no existe",
                                    venta.get("importeventa"));
                        } else {
                            System.out.printf("\t%-10s %-15s %-15s\n", "ID", "NOMBRE VENDEDOR", "IMPORTE VENTA");
                            System.out.printf("\t%-10s %-15s %-15s\n", "===", "==============", "==============");
                            System.out.printf("\t%-10s %-15s %-15s\n", venta.get("vendedor"), vendedor.get("nombre"),
                                    venta.get("importeventa"));

                        }

                    }
                    System.out.println("===================================================================");
                }
            }
            System.out.println("===================================================================");
            id = leerEntero("Introduce el ID de una zona: ", 0, 9999);
            System.out.println("===================================================================");
        }
    }

    private static void ActualizarVendedores(MongoCollection<Document> coleccionVendedores,
            MongoCollection<Document> coleccionVentas, MongoCollection<Document> coleccionZonas) {

        int err = 0;
        // 2) [6 puntos] Actualizar en la colección vendedores:
        // • El campo nombreZona para que almacene el nombre de la zona del vendedor. Si
        // el código de zona no existe, se pone como nombre ‘*No existe*’.
        // • El campo nventas para que almacene el nº de ventas que ha realizado el
        // vendedor. Cada documento en la colección de ventas es una venta.
        // • El campo importeTotal, a este campo se deben sumar los importeventa del
        // vendedor que se encuentran en la colección de ventas

        // Inicializo a *No existe* el campo nombreZona de la colección vendedores
        coleccionVendedores.updateMany(exists("nombreZona", true), set("nombreZona", "*No existe*"));
        // Pongo 0 en el campo nventas e importeTotal de la colección vendedores
        coleccionVendedores.updateMany(exists("importeTotal", true), set("importeTotal", 0));
        coleccionVendedores.updateMany(exists("nventas", true), set("nventas", 0)); 
        for (Document ventas : coleccionVentas.find().sort(ascending("vendedor"))) {
            int vendedor = ventas.getInteger("vendedor");
            int zona = ventas.getInteger("zona");
            // ¿Existe zona en coleccionZonas?
            boolean existeZona = coleccionZonas.find(eq("_id", zona)).first() != null;
            // ¿Existe vendedor en coleccionVendedores?
            boolean existeVendedor = coleccionVendedores.find(eq("_id", vendedor)).first() != null;
            Double importeVenta = ventas.getDouble("importeventa");

            System.out.println("Actualizando Vendedor: " + vendedor);

            // compruebo que el vendedor existe
            if (existeVendedor == false) {
                System.out.println("\tError en venta: " + ventas.get("zona") + " / "
                        + ventas.get("vendedor") + " / " + ventas.get("importeventa") + " vendedor incorrecto");
                err++;
            }
            // compruebo que la zona existe
            if (existeZona == false) {
                System.out.println("\tError en venta: " + ventas.get("zona") + " / "
                        + ventas.get("vendedor") + " / " + ventas.get("importeventa") + " zona incorrecta");
                err++;
            }
            // compruebo que el importe de la venta es correcto
            if (importeVenta <= 0) {
                System.out.println("\tError en venta: " + ventas.get("zona") + " / "
                        + ventas.get("vendedor") + " / " + ventas.get("importeventa") + " importe incorrecto");
                err++;
            }
            // si no hay errores actualizo los campos
            if (err == 0) {
                String zonaNombre = coleccionZonas.find(eq("_id", zona)).first().getString("nombrezona");

                coleccionVendedores.updateOne(eq("_id", vendedor), set("nombreZona", zonaNombre));

                // El campo nventas para que almacene el nº de ventas que ha realizado el
                // vendedor. Cada documento en la colección de ventas es una venta.

                coleccionVendedores.updateOne(eq("_id", vendedor), inc("nventas", 1));

                // El campo importeTotal, a este campo se deben sumar los importeventa del
                // vendedor que se encuentran en la colección de ventas

                coleccionVendedores.updateOne(eq("_id", vendedor), inc("importeTotal", importeVenta));
            }
            err = 0;
        }
        System.out.println("Fin de la actualización de vendedores");
        System.out.println("=====================================================================================");
    }

    private static void ActualizarZonas(MongoCollection<Document> coleccionZonas,
            MongoCollection<Document> coleccionVendedores,
            MongoCollection<Document> coleccionVentas) {

        int err = 0;

        // Actualizar en la colección de zonas:
        // El campo ventas para que contenga el nº de ventas que se han realizado en la
        // zona. Cada documento en la colección ventas es una venta.

        // Inicializo a 0 el campo ventas de la colección zonas y el campo importeTotal
        coleccionZonas.updateMany(exists("ventas", true), set("ventas", 0));
        coleccionZonas.updateMany(exists("importeTotal", true), set("importeTotal", 0));
        System.out.println("=====================================================================================");
        for (Document ventas : coleccionVentas.find().sort(ascending("zona"))) {
            int zona = ventas.getInteger("zona");
            Double importeVenta = ventas.getDouble("importeventa");
            int vendedor = ventas.getInteger("vendedor");
            boolean existeVendedor = coleccionVendedores.find(eq("_id", vendedor)).first() != null;
            boolean existeZona = coleccionZonas.find(eq("_id", zona)).first() != null;

            System.out.println("Actualizando Zona: " + zona);

            // compruebo que importeVenta no sea <= 0
            if (importeVenta <= 0) {
                System.out.println("\tError en venta: " + ventas.get("zona") + " / "
                        + ventas.get("vendedor") + " / " + ventas.get("importeventa") + " importe INCORRECTO");
                err++;
            }

            if (existeVendedor == false) {
                System.out.println("\tError en venta: " + ventas.get("zona") + " / "
                        + ventas.get("vendedor") + " / " + ventas.get("importeventa") + " vendedor INCORRECTO");
                err++;
            }
            if (existeZona == false) {
                System.out.println("\tError en venta: " + ventas.get("zona") + " / "
                        + ventas.get("vendedor") + " / " + ventas.get("importeventa") + " zona INEXISTENTE");
                err++;
            }
            if (err == 0) {
                coleccionZonas.updateOne(eq("_id", zona), inc("importeTotal", importeVenta));
                coleccionZonas.updateOne(eq("_id", zona), inc("ventas", 1));
            }
            err = 0;
        }
        System.out.println("Fin de la actualización de zonas");
        System.out.println("=====================================================================================");
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

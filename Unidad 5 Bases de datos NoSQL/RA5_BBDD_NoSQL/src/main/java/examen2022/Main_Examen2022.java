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
import static com.mongodb.client.model.Sorts.*;

public class Main_Examen2022 {
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("GuerraValenciaBorja");
        MongoCollection<Document> coleccionZonas = db.getCollection("zonas");
        MongoCollection<Document> coleccionVendedores = db.getCollection("vendedores");
        MongoCollection<Document> coleccionVentas = db.getCollection("ventas");
        
        ActualizarZonas(coleccionZonas, coleccionVendedores, coleccionVentas);
        ActualizarVendedores(coleccionVendedores, coleccionVentas, coleccionZonas);
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
}

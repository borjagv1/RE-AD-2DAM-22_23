package ejerciciosMongo_ClientesVentas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

/*
/* BDARTICULOSCLIEN 

drop table IF EXISTS clientes,articulos,ventas;

CREATE TABLE Clientes(CODCLI VARCHAR(4)NOT NULL,NOMBRE VARCHAR(30),POBLACION VARCHAR(30),PRIMARY KEY(CODCLI));

CREATE TABLE ARTICULOS(CODART VARCHAR(4)NOT NULL,DENOMINACION VARCHAR(30),PVP FLOAT,STOCK INT,PRIMARY KEY(CODART));

create TABLE VENTAS(NUMVENTA VARCHAR(4)NOT NULL,CODCLI VARCHAR(4),CODART VARCHAR(4),FECHA DATE,UNIDADES INT,PRIMARY KEY(NUMVENTA));

ALTER TABLE VENTAS ADD FOREIGN KEY(CODCLI)REFERENCES Clientes(CODCLI)ON UPDATE RESTRICT ON DELETE RESTRICT;ALTER TABLE VENTAS ADD FOREIGN KEY(CODART)REFERENCES ARTICULOS(CODART)ON UPDATE RESTRICT ON DELETE RESTRICT;

/* INSERTAR CLIENTES 
INSERT INTO CLIENTES VALUES('CL1','Producciones límite','Talavera');INSERT INTO CLIENTES VALUES('CL2','La alberca S.A.','Talavera');INSERT INTO CLIENTES VALUES('CL3','Juán García','Madrid');INSERT INTO CLIENTES VALUES('CL4','Piscinas Moreno S.L.','Madrid');

/* INSERTAR ARTCULOS 
INSERT INTO articulos VALUES('AR1','Cloro multiacción 4K',15,10);INSERT INTO articulos VALUES('AR2','Lona Piscina 5x8',450,3);INSERT INTO articulos VALUES('AR3','Limpiafondos manual',35,5);INSERT INTO articulos VALUES('AR4','Elevador PH 6K',17,10);INSERT INTO articulos VALUES('AR5','Cloro Polvo Choque 6 K',25,15);

 * /* INSERTAR VENTAS 
NUMVENTA  , CODCLI  , CODART , FECHA ,  UNIDADES 

INSERT INTO ventas values('VE1','CL1','AR1',trunc(sysdate)-12,2);INSERT INTO ventas values('VE2','CL1','AR1',trunc(sysdate)-11,1);INSERT INTO ventas values('VE3','CL1','AR2',trunc(sysdate)-5,1);

INSERT INTO ventas values('VE4','CL2','AR1',trunc(sysdate)-15,2);INSERT INTO ventas values('VE5','CL2','AR3',trunc(sysdate)-15,2);INSERT INTO ventas values('VE6','CL2','AR5',trunc(sysdate)-10,3);INSERT INTO ventas values('VE7','CL2','AR4',trunc(sysdate)-3,1);

INSERT INTO ventas values('VE8','CL3','AR2',trunc(sysdate)-15,1);INSERT INTO ventas values('VE9','CL3','AR3',trunc(sysdate)-15,2);INSERT INTO ventas values('VE10','CL3','AR5',trunc(sysdate)-10,1);INSERT INTO ventas values('VE11','CL3','AR1',trunc(sysdate)-3,3);

INSERT INTO ventas values('VE12','CL4','AR4',trunc(sysdate)-5,2);INSERT INTO ventas values('VE13','CL4','AR2',trunc(sysdate)-3,1);
 */
public class Ejercicio1_CrearBD_Consultas {

    public static void main(String[] args) throws ParseException {
        com.mongodb.client.MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("mibasedatos");
        //MongoCollection<Document> coleccionClientes = db.getCollection("clientes");

        System.out.println("Ejercicio 1");
        // NOS CONECTAMOS A BBDD MYSQL Y AÑADIMOS LOS DATOS A MONGODB:
        // 1. Creo la conexión:
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conexión con la BD MYSQL
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdarticulosclien",
                    "bdarticulosclien", "bdarticulosclien");

            borrarColecciones(db);

            // Insertamos los datos en MongoDB
            insertarClientes(db, conexion);
            insertarArticulos(db, conexion);
            insertarVentas(db, conexion);

            mostrarDatosVentas(db, conexion);

            // Cerramos la conexión
            conexion.close();

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void mostrarDatosVentas(MongoDatabase db, Connection conexion) {
        MongoCollection<Document> coleccionVentas = db.getCollection("ventas");
        MongoCollection<Document> coleccionClientes = db.getCollection("clientes");
        MongoCollection<Document> coleccionArticulos = db.getCollection("articulos");

        // Mostrar datos de ventas:
        // Numventa, codcliente, nombre, codarticulo, denominación, fecha venta,
        // unidades, importe.
        // Importe es el PVP * UNIDADES
        List<Document> ventas = coleccionVentas.find().sort(ascending("_id")).into(new ArrayList<Document>());
        // Mostrar resultados campo por campo:
        // Cabecera:
        System.out.printf("%-10s %-10s %-20s %-12s %-23s %-10s %-10s %-10s\n",
                "NumVenta", "CodCliente", "Nombre", "CodArticulo", "Denominación", "Fecha", "Unidades", "Importe");
        System.out.printf("%-10s %-10s %-20s %-12s %-23s %-10s %-10s %-10s\n",
                "--------", "----------", "-------------------", "-----------", "----------------------", "----------",
                "----------", "----------");
        for (Document venta : ventas) {
            String numVenta = venta.getString("_id");
            String codCliente = venta.getString("clientes");
            String codArticulo = venta.getString("articulos");
            String fecha = venta.getString("fecha");
            String unidades = venta.getString("unidades");
            
            // Buscamos los datos del cliente y del artículo:
            Document cliente = coleccionClientes.find(eq("_id", codCliente)).first();
            Document articulo = coleccionArticulos.find(eq("_id", codArticulo)).first();

            // Compruebo que existen los datos:
            if (cliente != null && articulo != null) {
                String nombre = (String) cliente.get("nombre");
                String denominacion = (String) articulo.get("denominacion");
                String pvp = (String) articulo.get("pvp");

                // Calculamos el importe:
                double importe = Double.parseDouble(pvp) * Double.parseDouble(unidades);

                // Mostramos los datos formateados como una tabla:
                System.out.printf("%-10s %-10s %-20s %-12s %-23s %-19s %-6s %-10s\n",
                        numVenta, codCliente, nombre, codArticulo, denominacion, fecha, unidades, importe);

                // System.out.println("NumVenta: " + numVenta + " CodCliente: " + codCliente + " Nombre: " + nombre
                //         + " CodArticulo: " + codArticulo + " Denominación: " + denominacion + " Fecha: " + fecha
                //         + " Unidades: " + unidades + " Importe: " + importe);
            } else {
                System.out.println("No se han encontrado los datos del cliente o del artículo");
            }
            
        }

        

    }

    private static void borrarColecciones(MongoDatabase db) {
        // Borramos las colecciones si existen
        // Controlamos la excepción por si no existen las colecciones aunque mongo ya lo
        // hace por nosotros
        try {
            if (db.getCollection("clientes").countDocuments() == 0
                    && db.getCollection("articulos").countDocuments() == 0
                    && db.getCollection("ventas").countDocuments() == 0) {
                System.out.println("Las colecciones no existen NO SE PODRÁN ELIMINAR");
            } else {
                System.out.println("HACEMOS UN DROP DE LAS COLECCIONES");
            }
            db.getCollection("clientes").drop();
            System.out.println(
                    "La colección: " + db.getCollection("clientes").getNamespace().getCollectionName()
                            + " se ha eliminado correctamente.");
            db.getCollection("articulos").drop();
            System.out.println(
                    "La colección: " + db.getCollection("articulos").getNamespace().getCollectionName()
                            + " se ha eliminado correctamente.");
            db.getCollection("ventas").drop();
            System.out.println(
                    "La colección: " + db.getCollection("ventas").getNamespace().getCollectionName()
                            + " se ha eliminado correctamente.");

        } catch (MongoCommandException e) {
            if (e.getErrorCode() == 26) {
                System.out.println("La colección no existe. No se puede eliminar.");
            } else {
                System.out.println("Error al eliminar la colección: " + e.getMessage());
            }
        }
    }

    private static void insertarVentas(MongoDatabase db, Connection conexion) throws ParseException {
        MongoCollection<Document> coleccion = db.getCollection("ventas");

        // Preparamos la consulta
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            String sql = "SELECT * FROM VENTAS";
            ResultSet resul = sentencia.executeQuery(sql);

            // Obtengo los nombres de las columnas en minúsculas con RESULTSETMETADATA
            ResultSetMetaData rsmd = resul.getMetaData();
            // int numColumnas = rsmd.getColumnCount();
            // Mostrar resultados
            // for (int i = 1; i <= numColumnas; i++) {
            while (resul.next()) {
                // Recuperar datos por nombre de columna
                String numventa = resul.getString(1);
                String codcli = resul.getString(2);
                String codart = resul.getString(3);
                String fechastr = resul.getString(4);

                // Creo un objeto SimpleDateFormat con el formato de fecha que viene de la BBDD
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
                // Creo un objeto SimpleDateFormat con el formato deseado
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
                // Convierte la cadena de texto a un objeto de tipo Date
                Date fecha = formatoEntrada.parse(fechastr);
                // Formatea la fecha en el nuevo formato
                String fechaFormateada = formatoFecha.format(fecha);

                String unidades = resul.getString(5);
                System.out.printf("%s %s %s %s %s\n", numventa + " - ", codcli + " - ", codart + " - ",
                        fechaFormateada + " - ", unidades);
                // Insertamos en MongoDB

                Document doc = new Document();
                doc.append("_id", resul.getString(1)); // _id es el campo clave
                // i++;
                doc.append("clientes", resul.getString(2));
                doc.append("articulos", resul.getString(3));
                doc.append(rsmd.getColumnLabel(4).toLowerCase(), fechaFormateada);
                doc.append(rsmd.getColumnLabel(5).toLowerCase(), resul.getString(5));

                try {
                    coleccion.insertOne(doc);
                    System.out.println("Insertado en MongoDB: " + doc.toJson() + "\n");
                    // Excepción de clave duplicada
                } catch (MongoWriteException e) {
                    System.out.println(
                            "\n\tError al insertar, el documento ya existe: \n\t> MENSAJE DE ERROR: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertarArticulos(MongoDatabase db, Connection conexion) {
        MongoCollection<Document> coleccion = db.getCollection("articulos");

        // Preparamos la consulta
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            String sql = "SELECT * FROM ARTICULOS";
            ResultSet resul = sentencia.executeQuery(sql);

            // Obtengo los nombres de las columnas en minúsculas con RESULTSETMETADATA
            ResultSetMetaData rsmd = resul.getMetaData();
            // int numColumnas = rsmd.getColumnCount();
            // Mostrar resultados
            // for (int i = 1; i <= numColumnas; i++) {
            while (resul.next()) {
                // Recuperar datos por nombre de columna
                String codart = resul.getString(1);
                String denominacion = resul.getString(2);
                String pvp = resul.getString(3);
                String stock = resul.getString(4);
                System.out.printf("%s %s %s %s\n", codart + " - ", denominacion + " - ", pvp + " - ", stock);
                // Insertamos en MongoDB

                Document doc = new Document();
                doc.append("_id", resul.getString(1)); // _id es el campo clave
                // i++;
                doc.append(rsmd.getColumnLabel(2).toLowerCase(), resul.getString(2));
                // i++;
                doc.append(rsmd.getColumnLabel(3).toLowerCase(), resul.getString(3));
                doc.append(rsmd.getColumnLabel(4).toLowerCase(), resul.getString(4));
                // i--;
                // i--;
                // meter en un try catch la inserción
                try {
                    coleccion.insertOne(doc);
                    System.out.println("Insertado en MongoDB: " + doc.toJson() + "\n");
                    // Excepción de clave duplicada
                } catch (MongoWriteException e) {
                    System.out.println(
                            "\n\tError al insertar, el documento ya existe: \n\t> MENSAJE DE ERROR: " + e.getMessage());

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertarClientes(MongoDatabase db, Connection conexion) {
        MongoCollection<Document> coleccion = db.getCollection("clientes");

        // Preparamos la consulta
        try {
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT * FROM CLIENTES";
            ResultSet resul = sentencia.executeQuery(sql);

            // Obtengo los nombres de las columnas en minúsculas con RESULTSETMETADATA
            ResultSetMetaData rsmd = resul.getMetaData();
            // int numColumnas = rsmd.getColumnCount();
            // Mostrar resultados
            // for (int i = 1; i <= numColumnas; i++) {
            while (resul.next()) {
                // Recuperar datos por nombre de columna
                String codcli = resul.getString(1);
                String nombre = resul.getString(2);
                String poblacion = resul.getString(3);
                System.out.printf("%s %s %s\n", codcli + " - ", nombre + " - ", poblacion);
                // Insertamos en MongoDB

                Document doc = new Document();
                doc.append("_id", resul.getString(1)); // _id es el campo clave
                // i++;
                doc.append(rsmd.getColumnLabel(2).toLowerCase(), resul.getString(2));
                // i++;
                doc.append(rsmd.getColumnLabel(3).toLowerCase(), resul.getString(3));
                // i--;
                // i--;
                try {
                    coleccion.insertOne(doc);
                    System.out.println("Insertado en MongoDB: " + doc.toJson() + "\n");
                    // Excepción de clave duplicada
                } catch (MongoWriteException e) {
                    System.out.println(
                            "\n\tError al insertar, el documento ya existe: \n\t> MENSAJE DE ERROR: " + e.getMessage());
                }
            }
            // } fin bucle for
            resul.close(); // Cerrar ResultSet
            sentencia.close(); // Cerrar Statement

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
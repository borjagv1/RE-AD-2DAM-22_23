package ejerciciosMongo_ClientesVentas;

import org.bson.Document;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

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

public class Ejercicio2_ModificaBD {
    public static void main(String[] args) {
        System.out.println("Ejercicio 2 - ModificaBD");

        com.mongodb.client.MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("mibasedatos");
        MongoCollection<Document> coleccionClientes = db.getCollection("clientes");
        MongoCollection<Document> coleccionVentas = db.getCollection("ventas");
        MongoCollection<Document> coleccionArticulos = db.getCollection("articulos");

        // Apartado 1:
        // A la colección artículos añade un campo numérico para que almacene el número
        // de ventas del artículo, y otro para que almacene la suma de unidades; y los
        // inicializas en 0
        coleccionArticulos.updateMany(exists("numVentas", false), set("numVentas", 0));
        coleccionArticulos.updateMany(exists("sumaUnidades", false), set("sumaUnidades", 0));

        // A la colección clientes añade un campo numérico para que almacene número
        // de ventas del cliente, lo inicializas en 0.
        coleccionClientes.updateMany(exists("numVentas", false), set("numVentas", 0));

        // Apartado 2:
        // Modifica la colección artículos los valores de los campos añadidos
        // anteriormente para que almacenen el número de ventas y la suma de las
        // unidades vendidas.

        apartado1_modArticulos(coleccionVentas, coleccionArticulos);

        // Modifica la colección clientes para que almacene en el campo añadido
        // anteriormente el número de ventas del cliente.

        apartado2_modClientes(coleccionClientes, coleccionVentas);

        // Apartado 3:
        apartado3_mostrarDatos_clientesVentas(coleccionClientes, coleccionVentas, coleccionArticulos);
    }

    private static void apartado3_mostrarDatos_clientesVentas(MongoCollection<Document> coleccionClientes,
            MongoCollection<Document> coleccionVentas, MongoCollection<Document> coleccionArticulos) {
        System.out.println("Datos de la colección clientes:");
        System.out.println("---------------------------------------------------------------------------");

        for (Document clientesDocument : coleccionClientes.find()) {
            // Cabecera:
            System.out.printf("\n%-30s %-30s %-30s\n", "NOMBRE:", "POBLACIÓN:", "NUMVENTAS:");
            System.out.printf("%-30s %-30s %-30s\n", "-------", "----------", "----------");
            System.out.printf("%-30s %-30s %-30s\n", clientesDocument.getString("nombre"),
                    clientesDocument.getString("poblacion"), clientesDocument.getInteger("numVentas"));

            // Mostrar las ventas de cada cliente:
            // Cabecera: NUMVENTA CODARTI DENOMINACIÓN FECHAVENTA UNIDADES IMPORTEVENTA
            System.out.println("---------------------------------------------------------------------------");

            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n", "NUMVENTA:", "CODARTI:", "DENOMINACIÓN:",
                    "FECHAVENTA:", "UNIDADES:", "IMPORTEVENTA:");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n", "--------", "--------", "-------------",
                    "----------", "---------", "------------");
            for (Document ventasDocument : coleccionVentas.find(eq("clientes", clientesDocument.getString("_id")))) {
                // Obtengo la denominación del artículo:
                String denominacion = coleccionArticulos.find(eq("_id", ventasDocument.getString("articulos"))).first()
                        .getString("denominacion");
                // Obtengo el importe de cada venta:
                String importe = coleccionArticulos.find(eq("_id", ventasDocument.getString("articulos"))).first()
                        .getString("pvp");
                String unidades = ventasDocument.getString("unidades");

                Double importeTotalVenta = Double.parseDouble(importe) * Double.parseDouble(unidades);

                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n", ventasDocument.getString("_id"),
                        ventasDocument.getString("articulos"), denominacion,
                        ventasDocument.getString("fecha"), ventasDocument.getString("unidades"), importeTotalVenta);
            }

        }
    }

    private static void apartado2_modClientes(MongoCollection<Document> coleccionClientes,
            MongoCollection<Document> coleccionVentas) {
        // A la colección clientes añade un campo numérico para que almacene número
        // de ventas del cliente, lo inicializas en 0.
        coleccionClientes.updateMany(exists("numVentas", true), set("numVentas", 0));

        for (Document venta : coleccionVentas.find()) {
            String codCli = venta.getString("clientes");
            System.out.println("codCli: " + codCli);
            coleccionClientes.updateOne(eq("_id", codCli), inc("numVentas", 1), new UpdateOptions().upsert(false));
        }
    }

    private static void apartado1_modArticulos(MongoCollection<Document> coleccionVentas,
            MongoCollection<Document> coleccionArticulos) {
        coleccionArticulos.updateMany(exists("numVentas", true), set("numVentas", 0));
        coleccionArticulos.updateMany(exists("sumaUnidades", true), set("sumaUnidades", 0));

        for (Document venta : coleccionVentas.find()) {
            String codArt = venta.getString("articulos");
            String unidades = venta.getString("unidades");
            System.out.println("codArt: " + codArt + " unidades: " + unidades);
            coleccionArticulos.updateOne(eq("_id", codArt), inc("numVentas", 1));
            coleccionArticulos.updateOne(eq("_id", codArt), inc("sumaUnidades", Integer.parseInt(unidades)));
        }
    }
}

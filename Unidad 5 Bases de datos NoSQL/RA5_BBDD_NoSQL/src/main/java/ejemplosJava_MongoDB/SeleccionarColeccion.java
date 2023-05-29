package ejemplosJava_MongoDB;

//En el siguiente ejemplo se muestra como seleccionar una colección y como buscar el primer documento de la misma:

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class SeleccionarColeccion {
         public static void main( String args[] ) {
             MongoClient clienteMongo = MongoClients.create();
             MongoDatabase baseDatos = clienteMongo.getDatabase("mibasedatos");
             MongoCollection<Document> coleccion =
             baseDatos.getCollection("amigos");
             Document primero = coleccion.find().first();
             System.out.println(primero.toJson());
             clienteMongo.close();
        }
}
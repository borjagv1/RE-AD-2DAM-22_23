package ejemplosJava_MongoDB;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.Arrays;
import java.util.Iterator;
import org.bson.Document;


//En el siguiente ejemplo se muestra como realizar una consulta a una base de datos dada.
public class ConsultaMongoDB {
      public static void main( String args[] ) {
          MongoClient clienteMongo = MongoClients.create();
          MongoDatabase baseDatos = clienteMongo.getDatabase("mibasedatos");
          MongoCollection<Document> coleccion = baseDatos.getCollection("amigos");
         //Obtener un objecto iterable de tipo cursor
          FindIterable<Document> iterDoc = coleccion.find(new Document("nombre", "Ana"));
           int i = 1;
           //Iniciamos el iterador
           Iterator it = iterDoc.iterator();
           while (it.hasNext()) {
               System.out.println(it.next());
               i++;}
        clienteMongo.close();
      }
}
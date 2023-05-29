package ejemplosJava_MongoDB;


import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

//En el siguiente ejemplo se muestra la conexión a una base de datos Mongo llamada mibasedatos:

public class ConexionBDMongoDb {
    
      public static void main( String args[] ) {
           MongoClient clienteMongo = MongoClients.create();
           MongoDatabase baseDatos = clienteMongo.getDatabase("mibasedatos");
           for (String name : baseDatos.listCollectionNames()) {
                System.out.println(name);
           }clienteMongo.close();
}
}

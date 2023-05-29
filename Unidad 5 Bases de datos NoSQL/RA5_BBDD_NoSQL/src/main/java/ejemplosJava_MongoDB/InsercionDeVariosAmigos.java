package ejemplosJava_MongoDB;


//En el siguiente ejemplo insertamos los amigos que se añadieron inicialmente (desde la linea de comandos de Mongo)
//se añaden 3 documentos (3 amigos)

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.bson.Document;

public class InsercionDeVariosAmigos {
     public static void main( String args[] ) {
        MongoClient clienteMongo = MongoClients.create();
        MongoDatabase baseDatos = clienteMongo.getDatabase("mibasedatos");
        MongoCollection<Document> coleccion = baseDatos.getCollection("amigos");
        
        Document amigo1 = new Document("nombre", "Ana")
          .append("curso", "1DAM")
          .append("nota",7);
          
        coleccion.insertOne(amigo1);
      
        Document amigo2 = new Document("nombre", "Marleni")
          .append("curso", "1DAM")
          .append("nota",8);
      
        coleccion.insertOne(amigo2);
            
          Document persona = new Document("nombre", "Sergio")
          .append("curso", "1DAM")
         .append("nota",6);
        coleccion.insertOne(persona);     
      
      
        clienteMongo.close();
   }
}
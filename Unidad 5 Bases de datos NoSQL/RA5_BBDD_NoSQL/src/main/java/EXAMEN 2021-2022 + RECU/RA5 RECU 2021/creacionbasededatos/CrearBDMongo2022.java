package creacionbasededatos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import datos2022.*;


public class CrearBDMongo2022 {

	static MongoClient cliente = new MongoClient();
	static MongoDatabase db = cliente.getDatabase("RECUApellidosNombre");

	// los datos se recogen de estos ficheros de objetos
	static File oficinas = new File("Oficinas.dat"); // aleatorio
	static File repventas = new File("Repventas.dat"); // aleatorio
	static File ventas = new File("Ventas.dat"); // sec
	static File productos = new File("Productos.dat"); // sec

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
	
		CrearFichObjetos2022.proceso();
		
		llenarOficinas();
		llenarVentas();
		llenarRepventas();
		llenarProductos();

		System.out.println("Fin creación de la BD Mongo......");
		cliente.close();

	}

	private static void llenarProductos() throws FileNotFoundException, IOException, ClassNotFoundException {
		MongoCollection<Document> coleccion = db.getCollection("productos");
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection("productos");
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection("productos");
		}

		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(productos));


		try {
			while (true) { // lectura del fichero
				Productos n = (Productos) dataIS.readObject(); //

				Document doc = new Document();
				
//(int id, String descripcion, int stockactual, int stockminimo, double pvp) {
				doc.put("_id", n.getId());
				doc.put("descripcion", n.getDescripcion());
				doc.put("stockactual", n.getStockactual());
				doc.put("stockminimo", n.getStockminimo());
				doc.put("pvp", n.getPvp());
				
				coleccion.insertOne(doc);
				System.out.println("RepVenta grabado: " + n);
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA RepVentas.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
		// TODO Auto-generated method stub

	}

	private static void llenarRepventas() throws FileNotFoundException, IOException, ClassNotFoundException {
		MongoCollection<Document> coleccion = db.getCollection("repventas");
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection("repventas");
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection("repventas");
		}

		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(repventas));

//(int numero_rep, String nombre, int edad, int oficina_rep, int director, int num_ventas,
//float imp_ventas)

		try {
			while (true) { // lectura del fichero
				RepVentas n = (RepVentas) dataIS.readObject(); //

				Document doc = new Document();

				doc.put("_id", n.getNumero_rep());
				doc.put("nombre", n.getNombre());
				doc.put("edad", n.getEdad());
				doc.put("oficina_rep", n.getOficina_rep());
				doc.put("director", n.getDirector());
				doc.put("num_ventas", n.getNum_ventas());
				doc.put("imp_ventas", n.getImp_ventas());

				coleccion.insertOne(doc);
				System.out.println("RepVenta grabado: " + n);
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA RepVentas.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada

	}

	private static void llenarVentas() throws FileNotFoundException, IOException, ClassNotFoundException {
		MongoCollection<Document> coleccion = db.getCollection("ventas");
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection("ventas");
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection("ventas");
		}

		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(ventas));

		try {
			while (true) { // lectura del fichero
				Ventas n = (Ventas) dataIS.readObject(); //

				LocalDate localDate = n.getFechaventa();
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String fecha = localDate.format(formato);

				Document doc = new Document();
//int idventa, LocalDate fechaventa, int numero_rep, int idproducto, int cantidad)			
				doc.put("_id", n.getIdventa());
				doc.put("fechaventa", fecha);
				doc.put("numero_rep", n.getNumero_rep());
				doc.put("idproducto", n.getIdproducto());
				doc.put("cantidad", n.getCantidad());

				coleccion.insertOne(doc);
				System.out.println("Venta grabada: " + n);
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA Oficinas.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada

	}

	private static void llenarOficinas() throws FileNotFoundException, IOException, ClassNotFoundException {
		MongoCollection<Document> coleccion = db.getCollection("oficinas");
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection("oficinas");
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection("oficinas");
		}

		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(oficinas));

		try {
			while (true) { // lectura del fichero
				Oficinas n = (Oficinas) dataIS.readObject(); //

				Document doc = new Document();
				// int oficina, String ciudad, int cod_region, int director, float total_ventas)
				// {

				doc.put("_id", n.getOficina());
				doc.put("ciudad", n.getCiudad());
				doc.put("cod_region", n.getCod_region());
				doc.put("director", n.getDirector());
				doc.put("total_ventas", n.getTotal_ventas());

				coleccion.insertOne(doc);
				System.out.println("Oficina grabada: " + n);
			}
		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA Oficinas.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada

	}

}

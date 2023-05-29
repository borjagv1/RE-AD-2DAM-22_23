

//INSERTA DATOS EN LAS COLECCIONES

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import datosRA5.*;

public class CrearBD_RA5_22 {

	static MongoClient cliente = new MongoClient();
	static MongoDatabase db = cliente.getDatabase("ApellidosNombre");

	
	public static void main(String[] args)  {
	
		llenarVendedores();
		llenarZonas();	
		llenarVentas();		

		System.out.println("Fin creación de la BD......");
		cliente.close();
	}

	private static void llenarVendedores()  {
		
		MongoCollection<Document> coleccion = db.getCollection("vendedores");
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection("vendedores");
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection("vendedores");
		}
		Vendedor[] v = new Vendedor[9];

		v[0] = new Vendedor(1036, "FEDERICO", 2040.0f, 10, "", 0);
		v[1] = new Vendedor(2044, "MANUELA", 5040.0f, 10, "", 0);
		v[2] = new Vendedor(2155, "ALICIA", 16005.45f, 20, "", 0);
		v[3] = new Vendedor(5789, "SARA", 2900.0f, 30, "", 0);
		v[4] = new Vendedor(1077, "ENRIQUE", 7885.56f, 30, "", 0);
		v[5] = new Vendedor(2029, "PILUCA", 4100.0f, 30, "", 0);
		v[6] = new Vendedor(5050, "MARTÍN", 1040.60f, 79, "", 0);
		v[7] = new Vendedor(3040, "JOSE ANTONIO", 140040.60f, 40, "", 0);
		v[8] = new Vendedor(7709, "MIGUEL ANGEL", 800.40f, 55, "", 0);

		for (int i = 0; i < v.length; i++) {			
			//int id, String nombre, double importeTotal, int codZona, String nombreZona, int nventas
			
				Document res = new Document();
				res.put("_id",v[i].getId());
				res.put("nombre",v[i].getNombre());
				res.put("importeTotal",v[i].getImporteTotal());
				res.put("codZona", v[i].getCodZona());
				res.put("nombreZona", v[i].getNombreZona());
				res.put("nventas", v[i].getNventas());
				
				coleccion.insertOne(res);			
				System.out.println("Vendedor grabado: " + v[i].getId()+"/"+v[i].getNombre());
			}
			System.out.println();
	
	}// llenar vendedores

	
	//llenar zonas
	private static void llenarZonas()  {
		MongoCollection<Document> coleccion = db.getCollection("zonas");
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection("zonas");
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection("zonas");
		}		
	
		Zona[] z = new Zona[6];

		int cod[] = { 10, 20, 30, 40, 50, 60 };
		String nombrezona[] = { "ALCARRIA", "ARAÑUELO", "ALTO TAJO", "SERRANIA", "MANCHUELA", "LA JARA" };
		String prov[] = { "GUADALAJARA", "CÁCERES", "GUADALAJARA", "CUENCA", "ALBACETE", "TOLEDO" };

		for (int ii = 0; ii < z.length; ii++) {
			z[ii] = new Zona(cod[ii], nombrezona[ii], prov[ii], 0, 0);
		}

		for (int i = 0; i < z.length; i++) {			
			Document res = new Document();
			res.put("_id",z[i].getId());
			res.put("nombrezona",z[i].getNombrezona());
			res.put("provincia",z[i].getProvincia());
			res.put("ventas", z[i].getVentas());
			res.put("importeTotal", z[i].getImporteTotal());
		
			coleccion.insertOne(res);			
			System.out.println("Zona grabada: " + z[i].getId()+"/"+z[i].getNombrezona());		
		}
		System.out.println();
	}// llenar zonas

	
	//llenar Ventas
	private static void llenarVentas()  {

		MongoCollection<Document> coleccion = db.getCollection("ventas");
		// creo la colección. Si existe la borro y la creo de nuevo.
		try {
			db.createCollection("ventas");
		} catch (MongoCommandException ex) {
			coleccion.drop();
			db.createCollection("ventas");
		}	
		
		int z[] = { 10, 60, 90, 9, 50, 20, 30, 11, 20 };
		int v[] = { 1036, 90, 5789, 7709, 5050, 2044, 2044, 1111,5050 };
		double cantidad[] = { 1000.34, 0, 100.25, 1500.32, 0, 600.33, 2000.0, 500.44, 2000.40 };
		//

		for (int i = 0; i < z.length; i++) {
			Document res = new Document();
			res.put("zona",z[i]);
			res.put("vendedor",v[i]);
			res.put("importeventa",cantidad[i]);
			coleccion.insertOne(res);			
			System.out.println("Venta Insertada: " + z[i]+"/"+v[i]);	
		
		}
	
	}// llenar ventas

}// fin

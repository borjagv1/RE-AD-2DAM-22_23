import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Mondongo {

	static MongoClient cliente = new MongoClient();
	static MongoDatabase db = cliente.getDatabase("PracticaMondongo");
	static Scanner sc = new Scanner(System.in);
	static MongoCollection<Document> vendedores = db.getCollection("vendedores");
	static MongoCollection<Document> ventas = db.getCollection("ventas");
	static MongoCollection<Document> zonas = db.getCollection("zonas");

	public static void main(String[] args) {
		System.out.println("Introduce el numero del ej: ");
		int ej = sc.nextInt();
		switch (ej) {
		case 1:
			actualizarZonas();
			actualizarVendedores();
			break;
		case 2:
			listadoVentas();
			break;
		default:
			System.out.println("Error");
			break;
		}
	}

	private static void listadoVentas() {
		boolean ezona = true;
		System.out.println("===================================================================");
		System.out.print("Introduce un id de zona: ");
		int idzona = sc.nextInt();
		System.out.println("===================================================================");
		List<Document> consulta = zonas.find(eq("_id", idzona)).into(new ArrayList<Document>());
		try {
			System.out.println("ZONA: " + idzona + ", " + consulta.get(0).getString("nombrezona") + ", "
					+ consulta.get(0).getString("provincia") + "\nNúmero ventas: "
					+ consulta.get(0).getInteger("ventas") + "    Importe Total: "
					+ consulta.get(0).getDouble("importeTotal"));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("La zona no existe");
			System.exit(0);
		}
		System.out.println("===================================================================");
		List<Document> consulta2 = vendedores.find(eq("codZona", idzona)).into(new ArrayList<Document>());
		if (consulta2.size() == 0) {
			System.out.println("<<LA ZONA NO TIENE VENDEDORES>>");
		} else {
			System.out.println("LISTADO DE VENDEDORES: ");
			System.out.println(" ID  NOMBRE          IMPORTE TOTAL Nº VENTAS");
			System.out.println("==== =============== ============= =========");
			for (int i = 0; i < consulta2.size(); i++) {
				System.out.printf("%-4s %-15s %-13s %-9s %n", consulta2.get(i).getInteger("_id"),
						consulta2.get(i).getString("nombre"), consulta2.get(i).getDouble("importeTotal"),
						consulta2.get(i).getInteger("nventas"));
			}
		}
		List<Document> consulta3 = ventas.find(eq("zona", idzona)).into(new ArrayList<Document>());
		if (consulta3.size() == 0) {
			System.out.println("<<LA ZONA NO TIENE VENTAS>>");
		} else {
			System.out.println("LISTADO DE VENTAS: ");
			System.out.println(" ID  NOMBRE VENDEDOR IMPORTE VENTA");
			System.out.println("==== =============== =============");
			for (int i = 0; i < consulta3.size(); i++) {
				int idv = consulta3.get(i).getInteger("vendedor");
				String nombre = nombreVendedor(idv);
				System.out.printf("%-4s %-15s %-13s %n", idv, nombre, 
						consulta3.get(i).getDouble("importeventa"));
			}
		}

	}

	private static String nombreVendedor(int idv) {
		String nombre = "No existe";
		List<Document> consulta = vendedores.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			if (consulta.get(i).getInteger("_id") == idv) {
				nombre = consulta.get(i).getString("nombre");
				return nombre;
			}
		}
		return nombre;
		
	}

	private static void actualizarVendedores() {
		vendedores.updateMany(Filters.exists("_id"), set("nombreZona", "*No existe*"));
		vendedores.updateMany(Filters.exists("_id"), set("nventas", 0));
		List<Document> consulta = vendedores.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			int codzona = (Integer) consulta.get(i).getInteger("codZona");
			int id = (Integer) consulta.get(i).get("_id");
			nombrezona(codzona, id);
			numVentas(id);
			sumImporte(id);
		}

	}

	private static void sumImporte(int id) {
		List<Document> consulta = ventas.find(eq("vendedor", id)).into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			double impv = consulta.get(i).getDouble("importeventa");
			int idzona = consulta.get(i).getInteger("zona");
			if (impv > 0 && comprobarZonas(idzona) == true) {
				vendedores.updateOne(eq("_id", id), inc("importeTotal", impv));
			}
		}
	}

	private static void numVentas(int id) {
		List<Document> consulta = ventas.find(eq("vendedor", id)).into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			double impv = consulta.get(i).getDouble("importeventa");
			int idzona = consulta.get(i).getInteger("zona");
			if (impv > 0 && comprobarZonas(idzona) == true) {
				vendedores.updateOne(eq("_id", id), inc("nventas", 1));
			}
		}

	}

	private static boolean comprobarZonas(int id) {
		List<Document> consulta = zonas.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			if (consulta.get(i).getInteger("_id") == id) {
				return true;
			}
		}
		return false;

	}

	private static void nombrezona(int codzona, int id) {
		List<Document> consulta = zonas.find(eq("_id", codzona)).into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			String nombre = (String) consulta.get(i).get("nombrezona");
			if (nombre == null) {
				vendedores.updateOne(eq("_id", id), set("nombreZona", "*No existe*"));
			} else {
				vendedores.updateOne(eq("_id", id), set("nombreZona", nombre));
			}
		}

	}

	private static void actualizarZonas() {
		zonas.updateMany(Filters.exists("_id"), set("ventas", 0));
		zonas.updateMany(Filters.exists("_id"), set("importeTotal", 0.00));
		List<Document> consulta = zonas.find().into(new ArrayList<Document>());
		for (int i = 0; i < consulta.size(); i++) {
			int id = (Integer) consulta.get(i).get("_id");
			List<Document> consulta3 = ventas.find(eq("zona", id)).into(new ArrayList<Document>());
			for (int j = 0; j < consulta3.size(); j++) {
				double impv = consulta3.get(j).getDouble("importeventa");
				if (impv > 0) {
					zonas.updateOne(eq("_id", id), inc("ventas", 1));
				}
			}
			for (int k = 0; k < consulta3.size(); k++) {
				double impv = consulta3.get(k).getDouble("importeventa");
				if (impv > 0) {
					zonas.updateOne(eq("_id", id), inc("importeTotal", impv));
				}
			}
		}
	}

}

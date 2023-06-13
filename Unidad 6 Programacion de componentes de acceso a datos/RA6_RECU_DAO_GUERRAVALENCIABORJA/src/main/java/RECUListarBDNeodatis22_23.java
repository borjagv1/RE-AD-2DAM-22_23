import java.io.File;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

import datos.*;

public class RECUListarBDNeodatis22_23 {

	static ODB odb;
	
	public static void main(String[] args) {
		File fichero = new File("BDCAFETERIARECU.neo");

		if (!fichero.exists()) {
			System.out.println("NO EXISTE BD");
			System.exit(0);
		}

		odb = ODBFactory.open("BDCAFETERIARECU.neo");// Abrir BD

		listarProductos();
		listarAlergenos();
		listarCategorias();
		
		odb.close();
		
	}//main
		


	private static void listarCategorias() {
		// recuperamos todos los objetos
		Objects <Categorias> objects = odb.getObjects(Categorias.class);
		System.out.println("===========================================================================");
		System.out.println("LISTADO DE CATEGORIAS: ");
		System.out.printf("%d Registros: %n", objects.size());

		while (objects.hasNext()) {
			Categorias p = objects.next();
			System.out.println(p);
		}
		
	}



	private static void listarAlergenos() {
		// recuperamos todos los objetos
		Objects <Alergenos> objects = odb.getObjects(Alergenos.class);
		System.out.println("===========================================================================");
		System.out.println("LISTADO DE ALERGENOS: ");
		System.out.printf("%d Registros: %n", objects.size());

		while (objects.hasNext()) {
			Alergenos p = objects.next();
			System.out.println(p);
		}
		
	}



	private static void listarProductos() {
		// recuperamos todos los objetos
		Objects <Productos> objects = odb.getObjects(Productos.class);
		System.out.println("===========================================================================");
		System.out.println("LISTADO DE PRODUCTOS: ");
		System.out.printf("%d Registros: %n", objects.size());

		while (objects.hasNext()) {
			Productos p = objects.next();
			System.out.println(p);
		}
	}
	
	}//fin

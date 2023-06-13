package dao;

import java.util.ArrayList;

import datos.*;

public interface ProductoDAO {

	public int InsertarProducto(Productos p);

	// actualiza campos numalergenos y nombrealergenos para cada producto plato
	// actualizar pvp para los menus
	public boolean ActualizarDatos();

	// Insertar menus, id_menu + id_plato no se deben repetir
	// el orden debe empieza en 1 y se suma 1 para los platos de un menú
	public boolean InsertarMenu(int id_menu, int id_plato);

    //Obtener platos de un menu
	public ArrayList<PlatosMenus> ConsultarMenu(int id_menu);

	public Productos ConsultarProducto(int id);
	public ArrayList<Productos> TodosLosProductos();

}

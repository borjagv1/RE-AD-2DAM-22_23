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
	
	// RECIBE UN ID DE MENU Y UN ID DE PLATO, Y ELIMINA ESE PLATO DEL MENU, DEVUELVE TRUE SI LA OPERACIÓN SE REALIZO CORRECTAMENTE Y FALSE SI NO.
	// COMPROBAR QUE ID_MENU E ID_PLATO EXISTEN EN PlatosMenus, si no mostrar mensaje, si se elimina, mostrar mensaje.
	// Si se elimina el plato del menu, modificar el orden de los platos del menu, tienen que seguir el orden 1,2,3,4...
	// Se debe calcular el nuevo PVP del menu
	// MOstrar mensajes en el metodo de lo que va ocurriendo
	public boolean EliminarPlatoMenu(int id_menu, int id_plato);
	public boolean EliminarProductoCascada(int id);

}

package dao;

import java.util.ArrayList;

import datos.*;

public interface AlergenosDAO {
	public int InsertarAlergeno(Alergenos c);

	public int EliminarAlergeno(int id);

	// actualiza el campo numproductos y nombreproductos
	public boolean ActualizarDatos();

	// asigna al producto alergeno/os siempre y cuando no lo tenga
	public boolean InsertarAlergenoProducto(int idalergeno, int idproducto);

	public Alergenos ConsultarAlergeno(int id);

	public ArrayList<Alergenos> TodosLosAlergenos();

}

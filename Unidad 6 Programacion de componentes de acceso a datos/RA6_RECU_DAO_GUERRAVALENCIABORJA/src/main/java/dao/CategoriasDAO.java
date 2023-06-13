package dao;

import java.util.ArrayList;

import datos.*;

public interface CategoriasDAO {
	public int InsertarCategoria(Categorias c);

	public int EliminarCategoria(int id);

	// actualiza el campo numproductos
	public boolean ActualizarDatos();

	public Categorias ConsultarCategoria(int id);

	public ArrayList<Categorias> TodasLasCategorias();

}

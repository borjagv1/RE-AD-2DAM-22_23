package impl;

import java.util.ArrayList;

import org.neodatis.odb.ODB;

import dao.CategoriasDAO;
import datos.Categorias;
import factory.NeodatisDAOFactory;

public class NeodatisCategoriasImpl implements CategoriasDAO{
     static ODB bd = null;

	public NeodatisCategoriasImpl() {
		bd = NeodatisDAOFactory.crearConexion();
	}
    @Override
    public int InsertarCategoria(Categorias c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'InsertarCategoria'");
    }

    @Override
    public int EliminarCategoria(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'EliminarCategoria'");
    }

    @Override
    public boolean ActualizarDatos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ActualizarDatos'");
    }

    @Override
    public Categorias ConsultarCategoria(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ConsultarCategoria'");
    }

    @Override
    public ArrayList<Categorias> TodasLasCategorias() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'TodasLasCategorias'");
    }

}
